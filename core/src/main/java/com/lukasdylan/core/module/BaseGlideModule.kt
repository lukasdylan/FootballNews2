package com.lukasdylan.core.module

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.model.GlideUrl
import java.io.InputStream
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit


@GlideModule
class BaseGlideModule : AppGlideModule() {

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        val okHttpClient = OkHttpClient.Builder().also {
            it.connectTimeout(30, TimeUnit.SECONDS)
            it.readTimeout(30, TimeUnit.SECONDS)
        }.build()
        registry.replace(GlideUrl::class.java, InputStream::class.java, OkHttpUrlLoader.Factory(okHttpClient))
    }
}