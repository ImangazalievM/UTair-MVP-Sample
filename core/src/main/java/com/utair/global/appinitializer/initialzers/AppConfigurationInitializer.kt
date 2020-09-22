package com.utair.global.appinitializer.initialzers

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump
import com.utair.global.appinitializer.AppInitializerElement
import javax.inject.Inject

class AppConfigurationInitializer @Inject constructor() : AppInitializerElement {

    override fun initialize(context: Context) {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

        val calligraphyInterceptor = CalligraphyInterceptor(CalligraphyConfig.Builder().build())
        val viewPumpConfig = ViewPump.builder()
                .addInterceptor(calligraphyInterceptor)
                .build()
        ViewPump.init(viewPumpConfig)
    }

}
