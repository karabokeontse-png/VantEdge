package com.vantedge.app.data.storage

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.vantedge.app.data.model.ApplicationRecord
import com.vantedge.app.data.model.CycleState
import com.vantedge.app.data.model.GenerationCycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class HistoryStore(
    private val cycleDao: CycleDao
) {
    private val gson: Gson = GsonBuilder()
        .registerTypeAdapter(CycleState::class.java, CycleStateSerializer())
        .registerTypeAdapter(CycleState::class.java, CycleStateDeserializer())
        .create()

    // TypeToken gives Gson full generic type fidelity — fixes List<RelevancyItem> erasure
    private val cycleType = object : TypeToken<GenerationCycle>() {}.type

    private val scope = CoroutineScope(Dispatchers.IO)
    private val mutex = Mutex()

    // ─── LEGACY ───
    private val _historyFlow = MutableStateFlow<List<ApplicationRecord>>(emptyList())
    val historyFlow: StateFlow<List<ApplicationRecord>> = _historyFlow

    suspend fun addRecord(record: ApplicationRecord) {
        mutex.withLock {
            val updated = _historyFlow.value.toMutableList()
            updated.add(0, record)
            _historyFlow.value = updated
        }
    }

    fun updateRecord(updated: ApplicationRecord) {
        _historyFlow.value = _historyFlow.value.map {
            if (it.id == updated.id) updated else it
        }
    }

    fun deleteRecord(id: Long) {
        _historyFlow.value = _historyFlow.value.filter { it.id != id }
    }

    fun clearAll() {
        _historyFlow.value = emptyList()
    }

    // ─── CYCLES ───
    private val _cyclesFlow = MutableStateFlow<List<GenerationCycle>>(emptyList())
    val cyclesFlow: StateFlow<List<GenerationCycle>> = _cyclesFlow

    init {
        cycleDao.getAllCycles()
            .onEach { entities ->
                _cyclesFlow.value = entities.mapNotNull { entity ->
                    try {
                        gson.fromJson(entity.cycleJson, cycleType) // ← fixed
                    } catch (e: Exception) {
                        null
                    }
                }
            }
            .launchIn(scope)
    }

    suspend fun saveCycle(cycle: GenerationCycle) {
        val entity = CycleEntity(
            id = cycle.id,
            jobTitle = cycle.jobTitle,
            company = cycle.company,
            createdAt = cycle.createdAt,
            isCommitted = cycle.isCommitted,
            isVisibleInHistory = cycle.isVisibleInHistory,
            version = cycle.version,
            cycleJson = gson.toJson(cycle, cycleType) // ← also typed on save for symmetry
        )
        cycleDao.insertCycle(entity)
        val updated = _cyclesFlow.value.toMutableList()
        val existing = updated.indexOfFirst { it.id == cycle.id }
        if (existing >= 0) updated[existing] = cycle else updated.add(0, cycle)
        _cyclesFlow.value = updated
    }

    suspend fun getAllCycles(): List<GenerationCycle> = _cyclesFlow.value

    fun getCycleById(id: String): GenerationCycle? =
        _cyclesFlow.value.firstOrNull { it.id == id }

    suspend fun getCycleByIdSuspend(id: String): GenerationCycle? {
        getCycleById(id)?.let { return it }
        return try {
            val entity = cycleDao.getCycleById(id) ?: return null
            gson.fromJson(entity.cycleJson, cycleType) // ← fixed
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getCyclesForJob(jobTitle: String, company: String): List<GenerationCycle> =
        _cyclesFlow.value.filter {
            it.jobTitle.equals(jobTitle, ignoreCase = true) &&
            it.company.equals(company, ignoreCase = true)
        }

    fun deleteCycle(id: String) {
        scope.launch {
            cycleDao.deleteCycleById(id)
            _cyclesFlow.value = _cyclesFlow.value.filter { it.id != id }
        }
    }
}