package com.utair.presentation.ui.global.navigation

import android.app.Activity
import androidx.fragment.app.DialogFragment
import com.utair.presentation.ui.weatherforecast.WeatherForecastActivity
import me.aartikov.alligator.Screen
import me.aartikov.alligator.ScreenResult
import me.aartikov.alligator.navigationfactories.RegistryNavigationFactory
import javax.inject.Inject

class UTairNavigationFactory @Inject constructor() : RegistryNavigationFactory() {

    init {
        bindScreen<WeatherForecastScreen, WeatherForecastActivity>()
    }

    inline fun <reified S : Screen, reified A : Activity> bindScreen() =
            registerActivity(S::class.java, A::class.java)

    inline fun <reified S : Screen, reified R : ScreenResult, reified A : Activity> bindScreenWithResult() =
            registerActivityForResult(S::class.java, A::class.java, R::class.java)

    inline fun <reified S : Screen, reified D : DialogFragment> bindDialog() = registerDialogFragment(S::class.java, D::class.java)

    inline fun <reified S : Screen, reified D : DialogFragment, reified R : ScreenResult> bindResultDialog() =
            registerDialogFragmentForResult(S::class.java, D::class.java, R::class.java)

}