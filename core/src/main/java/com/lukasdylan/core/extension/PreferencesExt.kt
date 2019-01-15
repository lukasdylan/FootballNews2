package com.lukasdylan.core.extension

import android.content.SharedPreferences
import androidx.core.content.edit

inline fun <reified T : Any> SharedPreferences.saveObject(key: String, value: T) {
    edit {
        putString(key, moshiAdapter<T>().toJson(value))
    }
}

inline fun <reified T : Any> SharedPreferences.getObject(key: String): T? {
    val json = getString(key, "")
    return if (json.isNullOrBlank()) {
        null
    } else {
        moshiAdapter<T>().fromJson(json)
    }
}
