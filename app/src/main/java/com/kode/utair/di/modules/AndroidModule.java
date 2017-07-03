package com.kode.utair.di.modules;


import android.content.Context;

import com.kode.utair.domain.commons.ResourceManager;
import com.kode.utair.presentation.utils.AndroidResourceManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AndroidModule {

    private final Context context;

    public AndroidModule(Context context) {
        this.context = context.getApplicationContext();
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return context;
    }

    @Provides
    @Singleton
    ResourceManager provideResourceManager(AndroidResourceManager androidResourceManager) {
        return androidResourceManager;
    }

}
