package com.ivos.presentation.features.splash.state

sealed class SplashScreenTriggerEvent {

    data object NavToLoginScreenTrigger : SplashScreenTriggerEvent()

}
