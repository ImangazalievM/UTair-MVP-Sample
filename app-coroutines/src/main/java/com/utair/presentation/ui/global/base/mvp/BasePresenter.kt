package com.utair.presentation.ui.global.base.mvp

import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

open class BasePresenter<V : MvpView> : MvpPresenter<V>(), CoroutineScope {

    private val parentJob = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + parentJob

    override fun onDestroy() {
        parentJob.cancel()
    }

}

