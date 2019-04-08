package com.lukasdylan.core.utility

import android.content.Context
import androidx.lifecycle.LiveData
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.content.Intent
import android.content.BroadcastReceiver
import android.content.IntentFilter

@Suppress("DEPRECATION")
class ConnectionAwareLiveData(private val context: Context) : LiveData<Boolean>() {

    override fun onActive() {
        super.onActive()
        context.registerReceiver(connectionReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    override fun onInactive() {
        super.onInactive()
        context.unregisterReceiver(connectionReceiver)
    }

    private val connectionReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.extras?.let {
                val activeNetwork = it.get(ConnectivityManager.EXTRA_NETWORK_TYPE) as? NetworkInfo
                postValue(activeNetwork?.isConnected ?: false)
            } ?: kotlin.run {
                postValue(false)
            }
        }
    }
}