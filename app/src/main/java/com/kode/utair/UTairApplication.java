package com.kode.utair;

import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatDelegate;

import com.kode.utair.data.network.CityApiService;
import com.kode.utair.data.network.WeatherApiService;
import com.kode.utair.di.ApplicationComponent;
import com.kode.utair.di.DaggerApplicationComponent;
import com.kode.utair.di.modules.AndroidModule;
import com.kode.utair.di.modules.ApplicationModule;
import com.kode.utair.di.modules.DataModule;

public class UTairApplication extends Application {

    private static ApplicationComponent applicationComponent;
    private static Context context;

    public UTairApplication() {
        this.context = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();


        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        applicationComponent = prepareComponent();
    }

    public static Context getContext() {
        return context;
    }

    public ApplicationComponent prepareComponent() {
        return DaggerApplicationComponent.builder()
                .dataModule(new DataModule(CityApiService.BASE_URL, WeatherApiService.BASE_URL))
                .androidModule(new AndroidModule(getApplicationContext()))
                .applicationModule(new ApplicationModule())
                .build();
    }

    public static ApplicationComponent component() {
        return applicationComponent;
    }

}
