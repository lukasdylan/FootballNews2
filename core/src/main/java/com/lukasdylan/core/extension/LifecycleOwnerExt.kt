package com.lukasdylan.core.extension

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

inline fun <T> LifecycleOwner.observeValue(source: LiveData<T>, crossinline function: (T) -> Unit) {
    source.observe(this, Observer { value ->
        value?.let { function(it) }
    })
}

inline fun <T> LifecycleOwner.observeNoValue(source: LiveData<T>, crossinline function: () -> Unit) {
    source.observe(this, Observer {
        function()
    })
}

inline fun <T> LifecycleOwner.observeNullValue(source: LiveData<T>, crossinline function: (T) -> Unit) {
    source.observe(this, Observer {
        function(it)
    })
}