package com.kode.utair.di.modules

import android.content.Context
import com.kode.utair.domain.commons.ResourceManager
import com.kode.utair.presentation.utils.AndroidResourceManager
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