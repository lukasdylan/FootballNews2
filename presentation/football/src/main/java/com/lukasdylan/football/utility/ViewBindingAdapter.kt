package com.lukasdylan.football.utility

import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.text.PrecomputedTextCompat
import androidx.core.widget.TextViewCompat
import androidx.databinding.BindingAdapter
import com.lukasdylan.core.extension.GlideTransformationMode
import com.lukasdylan.core.extension.loadImagesFromUrl
import com.lukasdylan.football.R

@BindingAdapter("asyncText")
internal fun TextView.asyncText(text: String?) {
    text?.let {
        val params = TextViewCompat.getTextMetricsParams(this)
        (this as AppCompatTextView).setTextFuture(PrecomputedTextCompat.getTextFuture(it, params, null))
    }
}

@BindingAdapter("imageUrl", "placeholder", "mode", requireAll = false)
internal fun ImageView.imageUrl(url: String?, placeholder: Int, mode: GlideTransformationMode) {
    loadImagesFromUrl(url, placeholder, mode)
}

@BindingAdapter("circleImageUrl")
internal fun ImageView.circleImageUrl(url: String?) {
    loadImagesFromUrl(url, R.drawable.placeholder_circle_background, GlideTransformationMode.CIRCLE_IMAGE)
}