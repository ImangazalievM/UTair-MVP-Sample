package com.utair.presentation.mvp.global

import android.content.Context
import android.util.Log
import android.widget.Toast
import timber.log.Timber
import javax.inject.Inject

class ErrorHandler @Inject constructor(
        private val context: Context
) {

    fun handle(throwable: Throwable) {
        Timber.e(throwable)
        Toast.makeText(context, throwable.message, Toast.LENGTH_SHORT).show()
    }

}