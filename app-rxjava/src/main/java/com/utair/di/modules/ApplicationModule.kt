package com.utair.di.modules

import android.content.Context
import com.utair.di.qualifiers.JobScheduler
import com.utair.domain.global.ResourceManager
import com.utair.global.appinitializer.AppInitializer
import com.utair.global.appinitializer.AppInitializerElement
import com.utair.global.appinitializer.AppInitializersProvider
import com.utair.global.appinitializer.initialzers.AppConfigurationInitializer
import com.utair.presentation.mvp.global.AndroidResourceManager
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import javax.inject.Singleton

@Module
class ApplicationModule(
        private val context: Context
) {

    @Provides
    @Singleton
    fun provideApplicationContext(): Context = context

    @Provides
    @Singleton
    fun provideResourceManager(resourceManager: AndroidResourceManager): ResourceManager {
        return resourceManager
    }

    @Provides
    @Singleton
    @JobScheduler
    fun provideJobScheduler(): Scheduler {
        return Schedulers.computation()
    }

    @Provides
    @Singleton
    fun provideAppInitializer(
            context: Context,
            appConfigurationInitializer: AppConfigurationInitializer
    ): AppInitializer {
        val appInitializersProvider = object : AppInitializersProvider {
            override fun getInitializers(): List<AppInitializerElement> {
                return listOf(appConfigurationInitializer)
            }
        }
        return AppInitializer(context, appInitializersProvider)
    }

}