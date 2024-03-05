package com.ivos.presentation.features.noNetwork

import com.ivos.presentation.navigation.navCore.NavigationDirection
import com.ivos.presentation.navigation.navCore.NavigationIntent
import com.ivos.presentation.navigation.navScreens.BlockingNavigation.NoNetworkNavScreen

object NoNetworkScreenNav : NavigationDirection {

    override fun navigationIntent(): NavigationIntent {
        return NavigationIntent.NavigateTo(
            route = NoNetworkNavScreen.route,
            blockBackNavigation = true,
        )
    }
}
