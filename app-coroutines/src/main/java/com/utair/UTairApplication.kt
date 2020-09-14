package com.utair

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.utair.data.global.network.ApiConstants
import com.utair.di.AppModule
import com.utair.di.AppNavigationModule
import com.utair.di.DataModule
import com.utair.di.DomainModule
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump
import toothpick.Toothpick
import toothpick.configuration.Configuration
import java.util.*

class UTairApplication : Application() {

    val appLaunchCode = UUID.randomUUID().toString()

    override fun onCreate() {
        super.onCreate()

        initDi()

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

        val calligraphyInterceptor = CalligraphyInterceptor(CalligraphyConfig.Builder().build())
        val viewPumpConfig = ViewPump.builder()
                .addInterceptor(calligraphyInterceptor)
                .build()
        ViewPump.init(viewPumpConfig)
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

}