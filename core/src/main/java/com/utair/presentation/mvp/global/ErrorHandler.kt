package com.utair.presentation.mvp.global

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.utair.core.BuildConfig
import javax.inject.Inject

class ErrorHandler @Inject constructor(
        private val context: Context
) {

    fun handle(throwable: Throwable) {
        if (BuildConfig.DEBUG) {
            Log.e("UTair", throwable.message, throwable)
            Toast.makeText(context, throwable.message, Toast.LENGTH_SHORT).show()
        }
    }

}