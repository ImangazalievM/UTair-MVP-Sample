package com.utair.global.appinitializer

import android.content.Context
import javax.inject.Inject

class AppInitializer @Inject constructor(
        private val context: Context,
        private val appInitializersProvider: AppInitializersProvider
) {

    private var isInitialized: Boolean = false

    fun initialize() {
        if (isInitialized) return

        appInitializersProvider.getInitializers().forEach {
            it.initialize(context)
        }
        isInitialized = true
    }

}
