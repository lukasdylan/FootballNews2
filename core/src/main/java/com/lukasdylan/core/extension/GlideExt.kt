package com.lukasdylan.core.extension

import android.graphics.Bitmap
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners


enum class GlideTransformationMode {
    CIRCLE_IMAGE,
    FULL_IMAGE,
    FULL_CENTER_CROP_IMAGE,
    ROUNDED_CENTER_CROP_IMAGE,
    ROUNDED_FIT_CENTER_IMAGE
}

internal fun getTransformationByMode(mode: GlideTransformationMode): Array<Transformation<Bitmap>> {
    return when (mode) {
        GlideTransformationMode.CIRCLE_IMAGE -> arrayOf(CircleCrop())
        GlideTransformationMode.FULL_IMAGE -> arrayOf(FitCenter())
        GlideTransformationMode.FULL_CENTER_CROP_IMAGE -> arrayOf(CenterCrop())
        GlideTransformationMode.ROUNDED_CENTER_CROP_IMAGE -> arrayOf(CenterCrop(), RoundedCorners(24))
        GlideTransformationMode.ROUNDED_FIT_CENTER_IMAGE -> arrayOf(RoundedCorners(24))
    }
}