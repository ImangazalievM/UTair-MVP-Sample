package com.utair.di

import android.content.Context
import com.utair.presentation.mvp.global.ErrorHandler
import toothpick.config.Module

class AppModule(context: Context) : Module() {

    init {
        bind(Context::class).toInstance(context)

        bind(ErrorHandler::class)
                .toProviderInstance { ErrorHandler(getGlobal()) }
    }

}