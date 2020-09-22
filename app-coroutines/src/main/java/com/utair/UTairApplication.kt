package com.utair

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.utair.data.global.network.ApiConstants
import com.utair.di.*
import com.utair.global.appinitializer.AppInitializer
import toothpick.Toothpick
import toothpick.configuration.Configuration
import java.util.*

class UTairApplication : Application() {

    val appLaunchCode = UUID.randomUUID().toString()

    override fun onCreate() {
        super.onCreate()

        initDi()
        getGlobal<AppInitializer>()
    }

    private fun initDi() {
        if (BuildConfig.DEBUG) {
            Toothpick.setConfiguration(Configuration.forDevelopment().preventMultipleRootScopes())
        } else {
            Toothpick.setConfiguration(Configuration.forProduction())
        }

        Toothpick.openScope(DI.APP_SCOPE)
                .installModules(
                        AppModule(this),
                        AppNavigationModule(),
                        DomainModule(),
                        DataModule(
                                utairApiBaseUrl = ApiConstants.CITIES_BASE_URL,
                                weatherApiBaseUrl = ApiConstants.OPEN_WEATHER_BASE_URL
                        )
                )
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

}