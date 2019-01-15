package com.lukasdylan.core.utility

import android.content.Context
import androidx.annotation.StringRes
import com.lukasdylan.core.R
import com.lukasdylan.core.extension.isInternetConnected

sealed class ErrorWrapper {

    companion object {
        operator fun invoke(errorMessage: String?): ErrorWrapper = StringError(errorMessage)
        operator fun invoke(@StringRes resourceId: Int, value: Any? = null): ErrorWrapper =
            ResourceError(resourceId, value)

        operator fun invoke(throwable: Throwable?): ErrorWrapper = ThrowableError(throwable)
    }

    abstract fun getMessage(context: Context): String?

    private data class StringError(private val errorMessage: String?) : ErrorWrapper() {
        override fun getMessage(context: Context): String? = errorMessage
    }

    private data class ResourceError(
        @StringRes private val resourceId: Int,
        private val value: Any? = null
    ) : ErrorWrapper() {
        override fun getMessage(context: Context): String? = if (value != null) {
            context.resources?.getString(resourceId, value)
        } else {
            context.resources?.getString(resourceId)
        }
    }

    private data class ThrowableError(private val throwable: Throwable?) : ErrorWrapper() {
        override fun getMessage(context: Context): String? = if (!context.isInternetConnected()) {
            context.resources?.getString(R.string.msg_no_internet_connection)
        } else {
            throwable?.message
        }
    }
}