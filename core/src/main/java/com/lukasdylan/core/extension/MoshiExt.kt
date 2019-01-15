package com.lukasdylan.core.extension

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

fun moshi(): Moshi = Moshi.Builder().build()

fun moshiKotlinJsonAdapterFactory(): Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

fun moshiConverterFactory(): MoshiConverterFactory = MoshiConverterFactory.create(moshiKotlinJsonAdapterFactory())

inline fun <reified T: Any> moshiAdapter(): JsonAdapter<T> = moshi().adapter(T::class.java)