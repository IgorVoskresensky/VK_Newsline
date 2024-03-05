package com.ivos.presentation.navigation.navCore

import kotlinx.coroutines.flow.MutableSharedFlow

interface AppNavigator {

    val navigationFlow: MutableSharedFlow<NavigationIntent>

    fun navigate(navigationDirection: NavigationDirection)
}
