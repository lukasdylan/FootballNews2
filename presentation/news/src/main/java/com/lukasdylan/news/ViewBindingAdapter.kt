package com.lukasdylan.news

import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.text.PrecomputedTextCompat
import androidx.core.widget.TextViewCompat
import androidx.databinding.BindingAdapter
import com.lukasdylan.core.extension.loadImageByUrl

@BindingAdapter("asyncText")
internal fun TextView.asyncText(text: String?) {
    text?.let {
        val params = TextViewCompat.getTextMetricsParams(this)
        (this as AppCompatTextView).setTextFuture(PrecomputedTextCompat.getTextFuture(it, params, null))
    }
}

@BindingAdapter("imageUrl")
internal fun ImageView.imageUrl(url: String?) {
    loadImageByUrl(url, true)
}