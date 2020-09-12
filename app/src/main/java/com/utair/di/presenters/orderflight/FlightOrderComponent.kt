package com.utair.di.presenters.orderflight

import com.utair.di.ApplicationComponent
import com.utair.di.scopes.Presenter
import com.utair.presentation.mvp.flightorder.FlightOrderPresenter
import dagger.Component
import javax.inject.Singleton

@Presenter
@Component(dependencies = [ApplicationComponent::class])
interface FlightOrderComponent {

    fun getFlightOrderPresenter(): FlightOrderPresenter

}