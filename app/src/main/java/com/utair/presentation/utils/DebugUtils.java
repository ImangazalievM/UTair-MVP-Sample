package com.utair.presentation.utils;

import android.widget.Toast;

import com.utair.UTairApplication;

public class DebugUtils {

    private final static boolean ERRORS_ENABLED = true;

    public static void showDebugErrorMessage(Throwable throwable) {
        if (ERRORS_ENABLED) {
            throwable.printStackTrace();
            Toast.makeText(UTairApplication.context, throwable.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}
