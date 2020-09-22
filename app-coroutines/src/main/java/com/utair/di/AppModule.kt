package com.utair.di

import android.content.Context
import com.utair.global.appinitializer.AppInitializer
import com.utair.global.appinitializer.AppInitializerElement
import com.utair.global.appinitializer.AppInitializersProvider
import com.utair.global.appinitializer.initialzers.AppConfigurationInitializer
import com.utair.presentation.mvp.global.ErrorHandler
import toothpick.config.Module

class AppModule(context: Context) : Module() {

    init {
        bind(Context::class).toInstance(context)

        bind(ErrorHandler::class)
                .toProviderInstance { ErrorHandler(getGlobal()) }

        val appInitializersProvider = object : AppInitializersProvider {
            override fun getInitializers(): List<AppInitializerElement> {
                val appConfigurationInitializer = AppConfigurationInitializer()
                return listOf(appConfigurationInitializer)
            }
        }
        bind(AppInitializer::class)
                .toInstance(AppInitializer(context, appInitializersProvider))
    }

}