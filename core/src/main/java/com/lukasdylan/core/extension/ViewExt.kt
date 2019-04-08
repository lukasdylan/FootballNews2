package com.lukasdylan.core.extension

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.widget.TextViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.snackbar.Snackbar
import com.lukasdylan.core.R
import com.lukasdylan.core.module.GlideApp
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import org.jetbrains.anko.findOptional

fun ViewGroup.showErrorSnackBar(errorMessage: String?) {
    errorMessage?.let {
        val snackBar = Snackbar.make(this, it, Snackbar.LENGTH_LONG)
        snackBar.view.setBackgroundColor(ContextCompat.getColor(context, android.R.color.holo_red_dark))
        val textView = snackBar.view.findOptional(com.google.android.material.R.id.snackbar_text) as? TextView
        textView?.let { tv -> TextViewCompat.setTextAppearance(tv, R.style.TextAppearance_AppCompat_Snackbar) }
        snackBar.show()
    }
}

fun ViewGroup.showSuccessSnackBar(message: String?) {
    message?.let {
        val snackBar = Snackbar.make(this, it, Snackbar.LENGTH_LONG)
        snackBar.view.setBackgroundColor(ContextCompat.getColor(context, android.R.color.holo_green_light))
        val textView = snackBar.view.findOptional(com.google.android.material.R.id.snackbar_text) as? TextView
        textView?.let { tv -> TextViewCompat.setTextAppearance(tv, R.style.TextAppearance_AppCompat_Snackbar) }
        snackBar.show()
    }
}

fun ViewGroup.showNormalSnackBar(message: String?) {
    message?.let {
        val snackBar = Snackbar.make(this, it, Snackbar.LENGTH_LONG)
        val textView = snackBar.view.findOptional(com.google.android.material.R.id.snackbar_text) as? TextView
        textView?.let { tv -> TextViewCompat.setTextAppearance(tv, R.style.TextAppearance_AppCompat_Snackbar) }
        snackBar.show()
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

fun ImageView.loadImagesFromUrl(
    imageUrl: String?,
    placeholder: Int = R.drawable.placeholder_circle_background,
    mode: GlideTransformationMode = GlideTransformationMode.FULL_IMAGE
) {
    val drawableCrossFadeFactory = DrawableCrossFadeFactory.Builder()
    drawableCrossFadeFactory.setCrossFadeEnabled(true)
    val transformation = getTransformationByMode(mode)
    GlideApp.with(context)
        .load(imageUrl)
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        .priority(Priority.IMMEDIATE)
        .error(R.drawable.no_image_icon)
        .placeholder(placeholder)
        .transforms(*transformation)
        .transition(DrawableTransitionOptions.with(drawableCrossFadeFactory.build()))
        .into(this)
}

fun ImageView.loadImagesFromResources(
    imageResources: Int,
    placeholder: Int = R.drawable.placeholder_circle_background,
    mode: GlideTransformationMode = GlideTransformationMode.FULL_IMAGE
) {
    val drawableCrossFadeFactory = DrawableCrossFadeFactory.Builder()
    drawableCrossFadeFactory.setCrossFadeEnabled(true)
    val transformation = getTransformationByMode(mode)
    GlideApp.with(context)
        .load(imageResources)
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        .priority(Priority.IMMEDIATE)
        .error(R.drawable.no_image_icon)
        .placeholder(placeholder)
        .transforms(*transformation)
        .transition(DrawableTransitionOptions.with(drawableCrossFadeFactory.build()))
        .into(this)
}

fun AppBarLayout.liftableWithRecyclerView(recyclerView: RecyclerView) {
    setLiftable(true)
    recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            setLifted(recyclerView.canScrollVertically(-1))
        }
    })
}