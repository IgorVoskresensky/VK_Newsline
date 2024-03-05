package com.ivos.presentation.navigation.navGraph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ivos.presentation.navigation.navScreens.BlockingNavigation
import com.ivos.presentation.navigation.navScreens.BlockingNavigation.NoNetworkNavScreen
import com.ivos.presentation.navigation.navScreens.BlockingNavigation.ServerNotAvailableNavScreen

val blockingScreens by lazy {
    listOf(
        NoNetworkNavScreen,
        ServerNotAvailableNavScreen,
    )
}

fun NavGraphBuilder.blockingNavigationScreens() {
    composable(NoNetworkNavScreen.route) {
        /*NoNetworkScreen()*/
    }

    composable(ServerNotAvailableNavScreen.route) {
        /*ServerNotAvailableScreen()*/
    }
}
