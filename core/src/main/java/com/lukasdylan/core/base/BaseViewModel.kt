package com.lukasdylan.core.base

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.lukasdylan.core.utility.DispatcherProviders
import com.lukasdylan.core.utility.ErrorWrapper
import com.lukasdylan.core.utility.NavigationScreen
import com.lukasdylan.core.utility.SingleLiveEvent
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import kotlin.coroutines.CoroutineContext

open class BaseViewModel(protected val dispatcherProviders: DispatcherProviders) : ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = SupervisorJob() + dispatcherProviders.UI + CoroutineExceptionHandler { _, throwable ->
            AnkoLogger("Football News 2").error { "Error on class ${this.javaClass.simpleName} caused by ${throwable.message}" }
        }

    private val _errorSnackBarEvent = SingleLiveEvent<ErrorWrapper>()
    val errorSnackBarEvent: LiveData<ErrorWrapper> = _errorSnackBarEvent

    private val _loadingVisibility = MutableLiveData<Boolean>()
    val loadingVisibility: LiveData<Int> = Transformations.map(_loadingVisibility) {
        if (it == null || it == false) View.GONE else View.VISIBLE
    }

    private val _navigationScreenEvent = SingleLiveEvent<NavigationScreen>()
    val navigationScreenEvent: LiveData<NavigationScreen> = _navigationScreenEvent

    override fun onCleared() {
        coroutineContext.cancelChildren()
        super.onCleared()
    }

    protected fun setLoading(isLoading: Boolean) {
        _loadingVisibility.value = isLoading
    }

    protected fun setNavigationScreen(navigationScreen: NavigationScreen) {
        _navigationScreenEvent.value = navigationScreen
    }

    protected fun setErrorSnackBar(errorWrapper: ErrorWrapper) {
        _errorSnackBarEvent.postValue(errorWrapper)
    }
}