package com.ivos.presentation.features.splash.viewmodel

import com.ivos.presentation.base.BaseViewModel
import com.ivos.presentation.features.splash.navigation.SplashScreenToLoginScreenNav
import com.ivos.presentation.features.splash.state.SplashScreenTriggerEvent
import com.ivos.presentation.features.splash.state.SplashScreenTriggerEvent.NavToLoginScreenTrigger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor() : BaseViewModel<SplashScreenTriggerEvent>() {

    override fun onTriggerEvent(eventType: SplashScreenTriggerEvent) {
        if (eventType is NavToLoginScreenTrigger) {
            testNavToLogin()
        }
    }

    private fun testNavToLogin() {
        executeSuspend {
            delay(2000)
            appNavigator.navigate(SplashScreenToLoginScreenNav)
        }
    }
}
