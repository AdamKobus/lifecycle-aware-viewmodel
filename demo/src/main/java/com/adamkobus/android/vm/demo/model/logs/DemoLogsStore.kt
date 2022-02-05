package com.adamkobus.android.vm.demo.model.logs

import androidx.lifecycle.Lifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DemoLogsStore @Inject constructor() {

    private var currentId: Int = 0

    private val _logs = MutableStateFlow<List<DemoLog>>(emptyList())
    val logs: Flow<List<DemoLog>>
        get() = _logs

    fun addLog(event: Lifecycle.Event, log: String) {
        _logs.value = _logs.value + DemoLog(id = nextId(), createdAtUtc = System.currentTimeMillis(), event = event, message = log)
    }

    fun findLogById(id: Int) = _logs.value.find { it.id == id }

    private fun nextId(): Int {
        synchronized(this) {
            return currentId++
        }
    }
}
