package com.utair.di.modules

import com.utair.presentation.ui.global.navigation.UTairNavigationFactory
import dagger.Module
import dagger.Provides
import me.aartikov.alligator.AndroidNavigator
import me.aartikov.alligator.NavigationContextBinder
import me.aartikov.alligator.Navigator
import me.aartikov.alligator.ScreenResolver
import javax.inject.Singleton

@Module
class NavigationModule {

    private val androidNavigator = AndroidNavigator(UTairNavigationFactory())

    @Provides
    @Singleton
    fun provideNavigator(): Navigator {
        return androidNavigator
    }

    @Provides
    @Singleton
    fun provideNavigationContextBinder(): NavigationContextBinder {
        return androidNavigator
    }

    @Provides
    @Singleton
    fun provideScreenResolver(): ScreenResolver {
        return androidNavigator.screenResolver
    }

}