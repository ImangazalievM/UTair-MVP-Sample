package com.utair.presentation.ui.global.base

import android.content.Context
import android.widget.Toast
import com.utair.UTairApplication.Companion.component
import com.utair.presentation.ui.global.base.MvpAppCompatActivity
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import me.aartikov.alligator.NavigationContext
import me.aartikov.alligator.NavigationContextBinder
import me.aartikov.alligator.R
import me.aartikov.alligator.exceptions.ActivityResolvingException
import me.aartikov.alligator.exceptions.NavigationException

open class BaseMvpActivity : MvpAppCompatActivity() {

    protected val appComponent by lazy { component() }
    protected val navigationContextBinder by lazy {
        appComponent.getNavigationContextBinder()
    }
    protected val screenResolver by lazy {
        appComponent.getScreenResolver()
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase))
    }

    override fun onResumeFragments() {
        super.onResumeFragments()

        bindNavigationContext()
    }

    override fun onPause() {
        unbindNavigationContext()

        super.onPause()
    }


    //these methods are created to customize it in child classes if it needed
    protected open fun bindNavigationContext() {
        navigationContextBinder.bind(createNavigationContext())
    }

    protected open fun createNavigationContext(): NavigationContext {
        val factory = appComponent.getNavigationFactory()
        return NavigationContext.Builder(this, factory)
                .build()
    }

    protected open fun unbindNavigationContext() {
        navigationContextBinder.unbind(this)
    }

}