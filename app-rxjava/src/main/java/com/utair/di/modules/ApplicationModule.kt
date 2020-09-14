package com.utair.di.modules

import android.content.Context
import com.utair.di.qualifiers.JobScheduler
import com.utair.domain.global.ResourceManager
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

}