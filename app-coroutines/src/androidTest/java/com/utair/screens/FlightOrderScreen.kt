package com.utair.screens

import com.agoda.kakao.text.KButton
import com.kaspersky.kaspresso.screens.KScreen
import com.utair.R
import com.utair.presentation.ui.flightorder.MainActivity

object FlightOrderScreen : KScreen<FlightOrderScreen>() {

    override val layoutId: Int? = R.layout.activity_main
    override val viewClass: Class<*>? = MainActivity::class.java

    val departCityButton = KButton { withId(R.id.departCityButton) }
    val arriveCityButton = KButton { withId(R.id.arriveCityButton) }
    val swapCitiesButton = KButton { withId(R.id.swapCitiesButton) }
    val departDateButton = KButton { withId(R.id.departDateButton) }
    val returnDateButton = KButton { withId(R.id.returnDateButton) }
    val addReturnDateButton = KButton { withId(R.id.addReturnDateButton) }

}