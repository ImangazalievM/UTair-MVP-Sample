package com.utair.presentation.ui.global.navigation

import me.aartikov.alligator.Screen
import java.io.Serializable

class WeatherForecastScreen(
        var departCity: String,
        var arriveCity: String
) : Screen, Serializable