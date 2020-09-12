package com.utair.presentation.ui.global.base

import com.utair.UTairApplication

abstract class BaseFragment : MvpAppCompatFragment() {

    protected val appComponent by lazy { UTairApplication.component() }
    protected val screenResolver by lazy {
        appComponent.getScreenResolver()
    }

}