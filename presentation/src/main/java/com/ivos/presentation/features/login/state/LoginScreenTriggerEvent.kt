package com.ivos.presentation.features.login.state

sealed interface LoginScreenTriggerEvent {

    data object NavigateNext : LoginScreenTriggerEvent
}
