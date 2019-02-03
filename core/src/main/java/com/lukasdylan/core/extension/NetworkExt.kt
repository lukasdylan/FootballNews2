package com.lukasdylan.core.extension

import com.lukasdylan.core.R
import com.lukasdylan.core.utility.ErrorWrapper
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import ru.gildor.coroutines.retrofit.Result
import ru.gildor.coroutines.retrofit.getOrNull

inline fun <T : Any> Result<T>.onSuccess(crossinline handler: (data: T) -> Unit): Result<T> {
    (this as? Result.Ok)?.getOrNull()?.let { handler(it) }
    return this
}

inline fun <T : Any> Result<T>.onFailed(crossinline handler: (errorWrapper: ErrorWrapper) -> Unit): Result<T> {
    //combination between onError and onException
    when (this) {
        is Result.Error -> onError(handler)
        is Result.Exception -> onException(handler)
    }
    return this
}

inline fun <T : Any> Result<T>.onError(crossinline handler: (errorWrapper: ErrorWrapper) -> Unit): Result<T> {
    (this as? Result.Error)?.let {
        val errorMessage = it.response.message() ?: it.exception.message()
        if (!errorMessage.isNullOrBlank()) {
            handler(ErrorWrapper.invoke(errorMessage.capitalize()))
        } else {
            handler(ErrorWrapper.invoke(R.string.msg_failed_to_load))
        }
    }
    return this
}

inline fun <T : Any> Result<T>.onException(crossinline handler: (errorWrapper: ErrorWrapper) -> Unit): Result<T> {
    (this as? Result.Exception)?.exception?.let { handler(ErrorWrapper.invoke(it)) }
    return this
}

inline fun <reified T> initRetrofit(baseUrl: String, okHttpClient: OkHttpClient): T {
    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(moshiConverterFactory())
        .build()
    return retrofit.create(T::class.java)
}