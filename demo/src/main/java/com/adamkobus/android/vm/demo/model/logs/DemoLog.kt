package com.adamkobus.android.vm.demo.model.logs

import androidx.lifecycle.Lifecycle

/**
 * [createdAtUtc] Unix epoch format
 */
data class DemoLog(
    val id: Int,
    val createdAtUtc: Long,
    val event: Lifecycle.Event,
    val message: String
)
