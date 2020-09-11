package com.kode.utair

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.kode.utair.data.network.CityApiService
import com.kode.utair.data.network.WeatherApiService
import com.kode.utair.di.ApplicationComponent
import com.kode.utair.di.DaggerApplicationComponent
import com.kode.utair.di.modules.AndroidModule
import com.kode.utair.di.modules.ApplicationModule
import com.kode.utair.di.modules.DataModule
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
                .dataModule(DataModule(CityApiService.BASE_URL, WeatherApiService.BASE_URL))
                .androidModule(AndroidModule(applicationContext))
                .applicationModule(ApplicationModule())
                .build()
    }

    companion object {

        private var applicationComponent: ApplicationComponent? = null

        @JvmStatic
        lateinit var context: Context

        @JvmStatic
        fun component(): ApplicationComponent? {
            return applicationComponent
        }
    }

    init {
        context = this
    }

}