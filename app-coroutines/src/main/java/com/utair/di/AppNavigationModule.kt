package com.utair.di

import com.utair.presentation.ui.global.navigation.UTairNavigationFactory
import me.aartikov.alligator.AndroidNavigator
import me.aartikov.alligator.NavigationContextBinder
import me.aartikov.alligator.Navigator
import me.aartikov.alligator.ScreenResolver
import toothpick.config.Module

class AppNavigationModule : Module() {

    init {
        val androidNavigator = AndroidNavigator(UTairNavigationFactory())

        bind(Navigator::class.java).toInstance(androidNavigator)
        bind(AndroidNavigator::class.java).toInstance(androidNavigator)
        bind(NavigationContextBinder::class.java).toInstance(androidNavigator)
        bind(ScreenResolver::class.java).toInstance(androidNavigator.screenResolver)
    }

}