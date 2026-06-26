package com.vantedge.app.data.viewmodel

import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vantedge.app.data.engine.DocxBuilder
import com.vantedge.app.data.engine.EngineResult
import com.vantedge.app.data.engine.GeneratorEngine
import com.vantedge.app.data.network.AiGateway
import com.vantedge.app.data.model.ApplicationRecord
import com.vantedge.app.data.model.UserProfile
import com.vantedge.app.data.storage.HistoryStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class GeneratorViewModel(
    private val historyStore: HistoryStore,
    private val aiGateway: AiGateway
) : ViewModel() {

    private val engine = GeneratorEngine(aiGateway)

    private val _uiState = MutableStateFlow<GeneratorUiState>(GeneratorUiState.Idle)
    val uiState: StateFlow<GeneratorUiState> = _uiState

    var lastCv: String = ""
    var lastCoverLetter: String = ""
    var currentMode: String = "html"

    var savedJobTitle: String = ""
    var savedCompany: String = ""
    var savedJobDescription: String = ""

    var prefilledJobTitle: String = ""
    var prefilledCompany: String = ""
    var prefilledJobDescription: String = ""

    fun resetState() {
        _uiState.value = GeneratorUiState.Idle
    }

    fun generate(
        profile: UserProfile,
        jobTitle: String,
        company: String,
        jobDescription: String,
        mode: String = "html",
        designId: String = "modern",
        schemeId: String = "navy",
        context: Context? = null
    ) {
        currentMode = mode
        _uiState.value = GeneratorUiState.Loading
        Log.d("GeneratorViewModel", "generate: ENTRY mode=$mode")

        viewModelScope.launch(Dispatchers.IO) {
            var cvResult: EngineResult? = null
            var coverResult: EngineResult? = null

            val cvLatch = java.util.concurrent.CountDownLatch(1)

            if (mode == "docx") {
                engine.generateCvDocx(profile, jobDescription) { result ->
                    cvResult = result
                    cvLatch.countDown()
                }
            } else {
                engine.generateCv(
                    profile = profile,
                    jobDescription = jobDescription,
                    designId = designId,
                    schemeId = schemeId,
                    jobTitle = jobTitle,
                    company = company
                ) { result ->
                    cvResult = result
                    cvLatch.countDown()
                }
            }

            cvLatch.await()

            val cvSuccess = when (val r = cvResult) {
                is EngineResult.Success -> {
                    Log.d("GeneratorViewModel", "generate: CV success | length=${r.data.length}")
                    r.data
                }
                is EngineResult.Failure -> {
                    Log.e("GeneratorViewModel", "generate: CV failure type=${r.type} detail=${r.detail}")
                    withContext(Dispatchers.Main) {
                        _uiState.value = GeneratorUiState.Error("CV: ${r.type} - ${r.detail ?: "unknown"}")
                    }
                    return@launch
                }
                null -> {
                    Log.e("GeneratorViewModel", "generate: CV result was never set | EXIT Error")
                    withContext(Dispatchers.Main) {
                        _uiState.value = GeneratorUiState.Error("CV generation failed. No result from engine.")
                    }
                    return@launch
                }
            }

            if (mode == "docx") {
                if (context != null) {
                    saveDocxToDevice(context, cvSuccess, jobTitle)
                }

                lastCv = cvSuccess
                lastCoverLetter = ""

                val record = ApplicationRecord(
                    jobTitle = jobTitle,
                    company = company,
                    cv = cvSuccess,
                    coverLetter = ""
                )
                historyStore.addRecord(record)

                Log.d("GeneratorViewModel", "generate: DOCX success | cvLength=${cvSuccess.length}")
                withContext(Dispatchers.Main) {
                    _uiState.value = GeneratorUiState.Success(cvSuccess, "")
                }
                return@launch
            }

            val coverLatch = java.util.concurrent.CountDownLatch(1)

            engine.generateCoverLetter(
                profile = profile,
                jobDescription = jobDescription,
                designId = designId,
                schemeId = schemeId,
                jobTitle = jobTitle,
                company = company
            ) { result ->
                coverResult = result
                coverLatch.countDown()
            }

            coverLatch.await()

            val coverSuccess = when (val r = coverResult) {
                is EngineResult.Success -> {
                    Log.d("GeneratorViewModel", "generate: Cover success | length=${r.data.length}")
                    r.data
                }
                is EngineResult.Failure -> {
                    Log.e("GeneratorViewModel", "generate: Cover failure type=${r.type} detail=${r.detail}")
                    withContext(Dispatchers.Main) {
                        _uiState.value = GeneratorUiState.Error("Cover letter: ${r.type} - ${r.detail ?: "unknown"}")
                    }
                    return@launch
                }
                null -> {
                    Log.e("GeneratorViewModel", "generate: Cover result was never set | EXIT Error")
                    withContext(Dispatchers.Main) {
                        _uiState.value = GeneratorUiState.Error("Cover letter generation failed. No result from engine.")
                    }
                    return@launch
                }
            }

            lastCv = cvSuccess
            lastCoverLetter = coverSuccess
            Log.d("GeneratorViewModel", "generate: storing | cvLength=${cvSuccess.length} | coverLength=${coverSuccess.length}")

            val record = ApplicationRecord(
                jobTitle = jobTitle,
                company = company,
                cv = cvSuccess,
                coverLetter = coverSuccess
            )
            historyStore.addRecord(record)

            Log.d("GeneratorViewModel", "generate: EXIT Success | cvStartsWith< ${cvSuccess.trimStart().startsWith("<")}")
            withContext(Dispatchers.Main) {
                _uiState.value = GeneratorUiState.Success(cvSuccess, coverSuccess)
            }
        }
    }

    private fun saveDocxToDevice(context: Context, content: String, jobTitle: String) {
        try {
            val fileName = "VantEdge_CV_${jobTitle.replace(" ", "_")}_${System.currentTimeMillis()}.docx"
            val docxBytes = DocxBuilder.build(content)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val values = ContentValues().apply {
                    put(MediaStore.Downloads.DISPLAY_NAME, fileName)
                    put(MediaStore.Downloads.MIME_TYPE, "application/vnd.openxmlformats-officedocument.wordprocessingml.document")
                    put(MediaStore.Downloads.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
                }
                val uri = context.contentResolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values)
                uri?.let {
                    context.contentResolver.openOutputStream(it)?.use { stream ->
                        stream.write(docxBytes)
                    }
                }
            } else {
                val downloads = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                File(downloads, fileName).writeBytes(docxBytes)
            }
        } catch (e: Exception) {
            android.util.Log.e("GeneratorViewModel", "DOCX save failed: ${e.message}")
        }
    }
}