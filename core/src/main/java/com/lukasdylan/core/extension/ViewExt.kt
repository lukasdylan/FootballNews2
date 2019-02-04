package com.lukasdylan.core.extension

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.snackbar.Snackbar
import com.lukasdylan.core.R
import com.lukasdylan.core.module.GlideApp
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import org.jetbrains.anko.findOptional

fun ViewGroup.showErrorSnackBar(errorMessage: String?) {
    errorMessage?.let {
        val snackbar = Snackbar.make(this, it, Snackbar.LENGTH_LONG)
        snackbar.view.setBackgroundColor(ContextCompat.getColor(context, android.R.color.holo_red_dark))
        val textView = snackbar.view.findOptional(com.google.android.material.R.id.snackbar_text) as? TextView
        textView?.setTextColor(ContextCompat.getColor(context, android.R.color.white))
        snackbar.show()
    }
}

fun ViewGroup.showSuccessSnackBar(message: String?) {
    message?.let {
        val snackbar = Snackbar.make(this, it, Snackbar.LENGTH_LONG)
        snackbar.view.setBackgroundColor(ContextCompat.getColor(context, android.R.color.holo_green_light))
        val textView = snackbar.view.findOptional(com.google.android.material.R.id.snackbar_text) as? TextView
        textView?.setTextColor(ContextCompat.getColor(context, android.R.color.white))
        snackbar.show()
    }
}

fun ViewGroup.showNormalSnackBar(message: String?) {
    message?.let { Snackbar.make(this, it, Snackbar.LENGTH_LONG).show() }
}

fun ImageView.loadImageByUrl(imageUrl: String?, isRoundedCorners: Boolean = false) {
    if (imageUrl.isNullOrBlank()) {
        loadImage(R.drawable.no_image_icon)
    } else {
        if (isRoundedCorners) {
            loadRoundedCornerImage(imageUrl)
        } else {
            loadImage(imageUrl)
        }
    }
}

fun ShimmerFrameLayout.onAnimateListener(isLoading: Boolean) {
    visibility = if (!isLoading) {
        if (isShimmerStarted) {
            stopShimmer()
        }
        View.GONE
    } else {
        if (!isShimmerStarted) {
            startShimmer()
        }
        View.VISIBLE
    }
}

fun ImageView.loadImage(any: Any) {
    if (any is String || any is @DrawableRes Int) {
        val drawableCrossFadeFactory = DrawableCrossFadeFactory.Builder()
        drawableCrossFadeFactory.setCrossFadeEnabled(true)
        GlideApp.with(context)
            .load(any)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .priority(Priority.IMMEDIATE)
            .error(R.drawable.no_image_icon)
            .placeholder(R.color.lighter_gray)
            .transition(DrawableTransitionOptions.with(drawableCrossFadeFactory.build()))
            .into(this)
    }
}

fun Toolbar.titleTextView(): TextView? {
    return try {
        val field = this.javaClass.getDeclaredField("mTitleTextView")
        field.isAccessible = true
        field.get(this) as? TextView
    } catch (ex: Exception) {
        AnkoLogger("Football News 2").error { ex.localizedMessage }
        null
    }
}

fun ImageView.loadRoundedCornerImage(any: Any) {
    if (any is String || any is @DrawableRes Int) {
        val drawableCrossFadeFactory = DrawableCrossFadeFactory.Builder()
        drawableCrossFadeFactory.setCrossFadeEnabled(true)
        GlideApp.with(context)
            .load(any)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .priority(Priority.IMMEDIATE)
            .error(R.drawable.no_image_icon)
            .placeholder(R.color.lighter_gray)
            .transform(RoundedCorners(16))
            .transition(DrawableTransitionOptions.with(drawableCrossFadeFactory.build()))
            .into(this)
    }
}