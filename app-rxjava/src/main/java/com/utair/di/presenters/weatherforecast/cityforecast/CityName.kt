package com.utair.di.presenters.weatherforecast.cityforecast

import java.lang.annotation.Documented
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import javax.inject.Qualifier

@Documented
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
annotation class CityName