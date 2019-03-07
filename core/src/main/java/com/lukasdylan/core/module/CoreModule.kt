package com.lukasdylan.core.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.lukasdylan.core.BuildConfig
import com.lukasdylan.core.utility.DispatcherProviderImpl
import com.lukasdylan.core.utility.DispatcherProviders
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.module
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

private val dispatcherModule = module {
    single<DispatcherProviders> { DispatcherProviderImpl() }
}

private val baseNetworkModule = module {
    single { setupOkHttpClient(androidApplication()) }
}

private val preferencesModule = module {
    single { setupSharedPreferences(androidApplication()) }
}

private val moshiConverterFactoryModule = module {
    single { moshiConverterFactory() }
}

private fun setupSharedPreferences(application: Application): SharedPreferences =
    application.getSharedPreferences("Football News 2", Context.MODE_PRIVATE)

private fun setupOkHttpClient(application: Application): OkHttpClient {
    val cache = Cache(application.cacheDir, 30 * 1024 * 1024)
    val logger = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BASIC
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
    }
    return OkHttpClient().newBuilder().apply {
        retryOnConnectionFailure(true)
        cache(cache)
        addInterceptor(logger)
        connectTimeout(15, TimeUnit.SECONDS)
        readTimeout(15, TimeUnit.SECONDS)
        writeTimeout(15, TimeUnit.SECONDS)
    }.build()
}

private fun moshiConverterFactory(): MoshiConverterFactory {
    val moshiKotlinJsonAdapterFactory = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    return MoshiConverterFactory.create(moshiKotlinJsonAdapterFactory)
}

val coreModules = listOf(baseNetworkModule, preferencesModule, moshiConverterFactoryModule, dispatcherModule)