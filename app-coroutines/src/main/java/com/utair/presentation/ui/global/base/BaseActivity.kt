package com.utair.presentation.ui.global.base

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import com.utair.DI
import com.utair.UTairApplication
import com.utair.di.get
import com.utair.di.getGlobal
import com.utair.di.objectScopeName
import com.utair.presentation.ui.global.navigation.UTairNavigationFactory
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import me.aartikov.alligator.*
import timber.log.Timber
import toothpick.Scope
import toothpick.Toothpick

abstract class BaseActivity : MvpAppCompatActivity() {

    companion object {
        private const val STATE_LAUNCH_FLAG = "state_launch_flag"
        private const val STATE_SCOPE_NAME = "state_scope_name"
        private const val STATE_SCOPE_WAS_CLOSED = "state_scope_was_closed"
    }

    private val appScope = Toothpick.openScope(DI.APP_SCOPE)
    protected val navigator by lazy { appScope.get<AndroidNavigator>() }
    protected val navigationContextBinder by lazy { appScope.get<NavigationContextBinder>() }
    protected val screenResolver by lazy { appScope.get<ScreenResolver>() }

    protected lateinit var scope: Scope
    lateinit var scopeName: String
    protected open val scopeModuleInstaller: (Scope) -> Unit = {}

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val savedAppCode = savedInstanceState?.getString(STATE_LAUNCH_FLAG)
        //False - if fragment was restored without new app process (for example: activity rotation)
        val isNewInAppProcess = savedAppCode != getAppLaunchCode()
        val scopeWasClosed = savedInstanceState?.getBoolean(STATE_SCOPE_WAS_CLOSED) ?: true
        val scopeIsNotInitialized = isNewInAppProcess || scopeWasClosed
        scopeName = savedInstanceState?.getString(STATE_SCOPE_NAME) ?: objectScopeName()
        scope = Toothpick.openScopes(DI.APP_SCOPE, scopeName)
                .apply {
                    if (scopeIsNotInitialized) {
                        Timber.d("Init new UI scope: $scopeName")
                        scopeModuleInstaller(this)
                    } else {
                        Timber.d("Get exist UI scope: $scopeName")
                    }
                }

        super.onCreate(savedInstanceState)
        //setContentView(layoutRes)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()

        bindNavigationContext()
    }

    override fun onPause() {
        unbindNavigationContext()
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()

        if (needCloseScope()) {
            //destroy this fragment with scope
            Timber.d("Destroy UI scope: $scopeName")
            Toothpick.closeScope(scope.name)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString(STATE_SCOPE_NAME, scopeName)
        outState.putString(STATE_LAUNCH_FLAG, getAppLaunchCode())
        outState.putBoolean(STATE_SCOPE_WAS_CLOSED, needCloseScope()) //save it but will be used only if destroyed
    }

    fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    //these methods are created to customize it in child classes if it needed
    protected open fun bindNavigationContext() {
        navigationContextBinder.bind(createNavigationContext())
    }

    protected open fun createNavigationContext(): NavigationContext {
        return NavigationContext.Builder(this, getGlobal<UTairNavigationFactory>())
                .screenResultListener { screenClass: Class<out Screen>, result: ScreenResult? ->
                    onScreenResult(screenClass, result)
                }
                .build()
    }

    protected open fun unbindNavigationContext() {
        navigationContextBinder.unbind(this)
    }

    /**
     * @param result Can be null if a screen has finished without no result.
     */
    protected open fun onScreenResult(screenClass: Class<out Screen>, result: ScreenResult?) {

    }

    private fun getAppLaunchCode() : String = (applicationContext as UTairApplication).appLaunchCode

    //It will be valid only for 'onDestroy()' method
    private fun needCloseScope(): Boolean =
            when {
                isChangingConfigurations -> false
                isFinishing -> true
                else -> false
            }

}