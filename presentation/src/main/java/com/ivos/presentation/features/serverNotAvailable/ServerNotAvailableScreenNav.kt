package com.ivos.presentation.features.serverNotAvailable

import com.ivos.presentation.navigation.navCore.NavigationDirection
import com.ivos.presentation.navigation.navCore.NavigationIntent
import com.ivos.presentation.navigation.navScreens.BlockingNavigation.ServerNotAvailableNavScreen

object ServerNotAvailableScreenNav : NavigationDirection {

    override fun navigationIntent(): NavigationIntent {
        return NavigationIntent.NavigateTo(
            route = ServerNotAvailableNavScreen.route,
            blockBackNavigation = true,
        )
    }
}
