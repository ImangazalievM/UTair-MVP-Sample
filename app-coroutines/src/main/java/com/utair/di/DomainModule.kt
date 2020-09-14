package com.utair.di

import com.utair.domain.flightorder.FlightOrderDataValidator
import com.utair.domain.global.ResourceManager
import com.utair.presentation.mvp.global.AndroidResourceManager
import toothpick.config.Module

class DomainModule : Module() {

    init {
        bind(FlightOrderDataValidator::class)
                .toProviderInstance { FlightOrderDataValidator(getGlobal()) }

        bind(ResourceManager::class)
                .toProviderInstance { AndroidResourceManager(getGlobal()) }
    }

}