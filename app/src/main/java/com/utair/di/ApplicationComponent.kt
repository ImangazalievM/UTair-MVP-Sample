package com.utair.di

import com.utair.di.modules.ApplicationModule
import com.utair.di.modules.DataModule
import com.utair.di.modules.NavigationModule
import com.utair.domain.flightorder.MainInteractor
import com.utair.domain.global.ResourceManager
import com.utair.domain.global.repositories.IWeatherRepository
import com.utair.presentation.ui.global.navigation.UTairNavigationFactory
import dagger.Component
import me.aartikov.alligator.NavigationContextBinder
import me.aartikov.alligator.Navigator
import me.aartikov.alligator.ScreenResolver
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, NavigationModule::class, DataModule::class])
interface ApplicationComponent {

    fun getMainInteractor(): MainInteractor

    fun getResourceManager(): ResourceManager

    fun getNavigator(): Navigator

    fun getNavigationFactory(): UTairNavigationFactory

    fun getScreenResolver(): ScreenResolver

    fun getNavigationContextBinder() : NavigationContextBinder


    fun getWeatherRepository(): IWeatherRepository

}