package com.utair

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.utair.data.global.network.ApiConstants
import com.utair.di.ApplicationComponent
import com.utair.di.DaggerApplicationComponent
import com.utair.di.modules.ApplicationModule
import com.utair.di.modules.DataModule
import com.utair.di.modules.NavigationModule
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump

class UTairApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

        val calligraphyInterceptor = CalligraphyInterceptor(CalligraphyConfig.Builder().build())
        val viewPumpConfig = ViewPump.builder()
                .addInterceptor(calligraphyInterceptor)
                .build()
        ViewPump.init(viewPumpConfig)

        applicationComponent = prepareComponent()
    }

    fun prepareComponent(): ApplicationComponent {
        return DaggerApplicationComponent.builder()
                .dataModule(DataModule(
                        utairApiBaseUrl = ApiConstants.CITIES_BASE_URL,
                        weatherApiBaseUrl = ApiConstants.OPEN_WEATHER_BASE_URL
                ))
                .applicationModule(ApplicationModule(applicationContext))
                .navigationModule(NavigationModule())
                .build()
    }

    companion object {

        private lateinit var applicationComponent: ApplicationComponent

        @JvmStatic
        fun component(): ApplicationComponent {
            return applicationComponent
        }
    }

}