package com.adamkobus.android.vm.demo.model.datetime

import android.icu.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DemoDateFormatter @Inject constructor() {
    private val timestampFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault())

    fun formatUnixEpochTimestamp(timestamp: Long): String = timestampFormat.format(timestamp)
}
