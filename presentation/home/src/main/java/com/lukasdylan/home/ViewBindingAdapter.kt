package com.lukasdylan.home

import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.text.PrecomputedTextCompat
import androidx.core.widget.TextViewCompat
import androidx.databinding.BindingAdapter
import com.lukasdylan.core.extension.GlideTransformationMode
import com.lukasdylan.core.extension.loadImagesFromUrl

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