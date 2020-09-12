package com.utair.di.modules

import android.content.Context
import com.utair.domain.global.ResourceManager
import com.utair.presentation.utils.AndroidResourceManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AndroidModule(
        private val context: Context
) {

    @Provides
    @Singleton
    fun provideApplicationContext(): Context  = context

    @Provides
    @Singleton
    fun provideResourceManager(resourceManager: AndroidResourceManager): ResourceManager {
        return resourceManager
    }

}