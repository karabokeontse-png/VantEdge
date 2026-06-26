package com.vantedge.app.data.infrastructure

import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import java.io.File

class MediaStoreExporter(private val context: Context) {

    fun save(jobTitle: String, docxBytes: ByteArray): Result<String> = runCatching {
        val sanitised = Regex("[^a-zA-Z0-9_ -]").replace(jobTitle, "").replace(" ", "_")
        val fileName = "VantEdge_CV_${sanitised}_${System.currentTimeMillis()}.docx"

        if (Build.VERSION.SDK_INT >= 29) {
            val values = ContentValues().apply {
                put("_display_name", fileName)
                put("mime_type", "application/vnd.openxmlformats-officedocument.wordprocessingml.document")
                put("relative_path", Environment.DIRECTORY_DOWNLOADS)
            }
            val uri = context.contentResolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values)
                ?: throw Exception("Failed to create MediaStore entry")
            context.contentResolver.openOutputStream(uri)?.use { stream ->
                stream.write(docxBytes)
            } ?: throw Exception("Failed to open output stream")
        } else {
            val downloads = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            File(downloads, fileName).writeBytes(docxBytes)
        }

        fileName
    }
}
