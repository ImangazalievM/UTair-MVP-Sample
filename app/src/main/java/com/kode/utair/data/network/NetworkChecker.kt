package com.kode.utair.data.network

import android.content.Context
import android.net.ConnectivityManager
import javax.inject.Inject

class NetworkChecker @Inject constructor(private val context: Context) {

    val isConnected: Boolean
        get() {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo = cm.activeNetworkInfo
            return netInfo != null && netInfo.isConnectedOrConnecting
        }

}