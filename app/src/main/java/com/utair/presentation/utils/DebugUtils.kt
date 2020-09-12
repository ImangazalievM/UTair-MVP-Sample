package com.utair.presentation.utils

import android.widget.Toast
import com.utair.UTairApplication

object DebugUtils {

    private const val ERRORS_ENABLED = true

    fun showDebugErrorMessage(throwable: Throwable) {
        if (ERRORS_ENABLED) {
            throwable.printStackTrace()
            Toast.makeText(UTairApplication.context, throwable.message, Toast.LENGTH_SHORT).show()
        }
    }

}