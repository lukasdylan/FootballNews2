package com.lukasdylan.core.extension

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.lukasdylan.core.widget.RoundedBottomSheetFragment

fun Context.isInternetConnected(): Boolean {
    return (getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo?.isConnected
        ?: false
}

inline fun Fragment.checkPermissions(
    permissions: Array<out String>,
    requestCode: Int,
    crossinline noPermissionNeedHandler: () -> Unit
) {
    context?.let { context ->
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && permissions.any {
                checkSelfPermission(context, it) != PackageManager.PERMISSION_GRANTED
            }) {
            requestPermissions(permissions, requestCode)
        } else {
            noPermissionNeedHandler()
        }
    }
}

fun AppCompatActivity.showBottomSheetFragment(
    fragment: RoundedBottomSheetFragment,
    params: Array<Pair<String, Any?>>? = null
) {
    fragment
        .also { it.arguments = bundleOf(*params.orEmpty()) }
        .show(supportFragmentManager, fragment::class.java.simpleName)
}

fun Context.openDeepLinkActivity(
    scheme: String,
    host: String,
    bundle: Bundle = Bundle.EMPTY,
    intentNotFoundHandler: (() -> Unit)? = null
) {
    getDeepLinkIntent("$scheme://$host")?.run {
        putExtras(bundle)
        startActivity(this)
    } ?: kotlin.run {
        intentNotFoundHandler?.invoke()
    }
}

private fun Context.getDeepLinkIntent(url: String): Intent? {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    val allResolvedActivities = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
    return allResolvedActivities.firstOrNull {
        it.activityInfo?.isFromApplication(packageName) ?: false
    }?.activityInfo?.toIntent(url)
}

private fun ActivityInfo.isFromApplication(pkgName: String): Boolean = pkgName == packageName

private fun ActivityInfo.toIntent(url: String) = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
    addCategory(Intent.CATEGORY_DEFAULT)
    component = ComponentName(applicationInfo.packageName, name)
}