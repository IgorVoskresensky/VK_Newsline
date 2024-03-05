package com.ivos.presentation.navigation.navCore

import com.ivos.presentation.navigation.navCore.NavigationIntent.NavigateBack
import com.ivos.presentation.navigation.navCore.NavigationIntent.NavigateTo
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

class AppNavigatorImpl @Inject constructor() : AppNavigator {

    override val navigationFlow: MutableSharedFlow<NavigationIntent> = MutableSharedFlow(
        replay = 0,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
        extraBufferCapacity = 1,
    )

    override fun navigate(navigationDirection: NavigationDirection) {
        when (navigationDirection.navigationIntent()) {
            is NavigateBack -> {
                navigateBack(navigationDirection.navigationIntent())
            }

            is NavigateTo -> {
                navigateTo(navigationDirection.navigationIntent())
            }
        }
    }

    private fun navigateBack(navigateBack: NavigationIntent) {
        navigationFlow.tryEmit(navigateBack)
    }

    private fun navigateTo(navigateTo: NavigationIntent) {
        navigationFlow.tryEmit(navigateTo)
    }
}
