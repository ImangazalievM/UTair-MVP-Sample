package com.utair

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.utair.data.global.network.UTairApiService
import com.utair.data.global.network.WeatherApiService
import com.utair.di.ApplicationComponent
import com.utair.di.DaggerApplicationComponent
import com.utair.di.modules.AndroidModule
import com.utair.di.modules.ApplicationModule
import com.utair.di.modules.DataModule
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
                .dataModule(DataModule(UTairApiService.BASE_URL, WeatherApiService.BASE_URL))
                .androidModule(AndroidModule(applicationContext))
                .applicationModule(ApplicationModule())
                .build()
    }

    companion object {

        private lateinit var applicationComponent: ApplicationComponent

        @JvmStatic
        lateinit var context: Context

        @JvmStatic
        fun component(): ApplicationComponent {
            return applicationComponent
        }
    }

    init {
        context = this
    }

}