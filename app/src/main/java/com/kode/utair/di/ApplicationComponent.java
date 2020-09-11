package com.kode.utair.di;

import com.kode.utair.di.modules.AndroidModule;
import com.kode.utair.di.modules.ApplicationModule;
import com.kode.utair.di.modules.DataModule;
import com.kode.utair.presentation.mvp.presenters.CityWeatherForecastPresenter;
import com.kode.utair.presentation.mvp.presenters.MainPresenter;
import com.kode.utair.presentation.mvp.presenters.WeatherForecastPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = { AndroidModule.class, ApplicationModule.class, DataModule.class })
public interface ApplicationComponent {

    void inject(MainPresenter presenter);

    void inject(WeatherForecastPresenter presenter);

    void inject(CityWeatherForecastPresenter presenter);

}
