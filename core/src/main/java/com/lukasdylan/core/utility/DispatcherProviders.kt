package com.lukasdylan.core.utility

import androidx.annotation.VisibleForTesting
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi

interface DispatcherProviders {
    val UI: CoroutineDispatcher
    val IO: CoroutineDispatcher
    val CommonPool: CoroutineDispatcher
}

class DispatcherProviderImpl : DispatcherProviders {
    override val CommonPool: CoroutineDispatcher
        get() = Dispatchers.Default
    override val UI: CoroutineDispatcher
        get() = Dispatchers.Main
    override val IO: CoroutineDispatcher
        get() = Dispatchers.IO
}

@ExperimentalCoroutinesApi
@VisibleForTesting
class TestDispatcherProvider : DispatcherProviders {
    override val UI: CoroutineDispatcher
        get() = Dispatchers.Unconfined
    override val IO: CoroutineDispatcher
        get() = Dispatchers.Unconfined
    override val CommonPool: CoroutineDispatcher
        get() = Dispatchers.Unconfined
}