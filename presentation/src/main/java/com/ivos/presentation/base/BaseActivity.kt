package com.ivos.presentation.base

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import com.ivos.presentation.activity.viewmodel.MainActivityViewModel
import com.ivos.presentation.activity.viewmodel.NoNetworkViewModel

abstract class BaseActivity : ComponentActivity() {

    protected val viewModel: MainActivityViewModel by viewModels()
    private val nwVn: NoNetworkViewModel by viewModels()

    abstract fun initUi()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        initUi()
        nwVn.registerNetworkCallback()
    }
}
