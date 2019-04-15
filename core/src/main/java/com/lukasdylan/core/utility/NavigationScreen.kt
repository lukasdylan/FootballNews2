package com.lukasdylan.core.utility

import android.os.Bundle
import androidx.core.os.bundleOf

data class NavigationScreen(val navigationId: Int, val params: Array<Pair<String, Any?>>? = null) {

    fun toBundle(): Bundle = bundleOf(*params.orEmpty())
}