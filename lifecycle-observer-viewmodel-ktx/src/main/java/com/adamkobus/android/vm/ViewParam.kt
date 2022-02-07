package com.adamkobus.android.vm

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map

/**
 * Represents additional data that has to be provided by the view to initialize ViewModel.
 *
 * Example:
 * - data provided via Bundle to the launched fragment
 * - argument provided via NavBackStackEntry when using Jetpack's Navigation
 *
 * Use [observe] or [collect] to wait for a value to be provided by the view.
 */
class ViewParam<T> {

    private val _currentValue = MutableStateFlow<ViewParamState<T>>(ViewParamState.Missing())

    /**
     * Will emit a value once it become available
     */
    fun observe() = _currentValue.filter {
        it is ViewParamState.Present
    }.map {
        it.getValue()
    }

    /**
     * Will deliver values produced [observe] and deliver them to [consumer]
     */
    suspend fun collect(consumer: suspend (T) -> Unit) = observe().collect {
        consumer(it)
    }

    fun bind(value: T) {
        _currentValue.value = ViewParamState.Present(value)
    }
}

sealed class ViewParamState<T>(val getValue: () -> T) {
    class Missing<T> : ViewParamState<T>({ throw IllegalStateException("Tried to access the value when it's missing") })

    class Present<T>(val value: T) : ViewParamState<T>({ value }) {
        override fun equals(other: Any?): Boolean {
            return other is Present<*> && other.value == value
        }

        override fun hashCode(): Int {
            return value?.hashCode() ?: 0
        }
    }
}
