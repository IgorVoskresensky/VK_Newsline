package com.ivos.presentation.navigation.navGraph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ivos.presentation.navigation.navScreens.LaunchAppNavigation
import com.ivos.presentation.navigation.navScreens.LaunchAppNavigation.SplashNavScreen

object LaunchAppScreensNav {

    val launchAppNavScreens by lazy {
        listOf(
            SplashNavScreen,
        )
    }

    fun NavGraphBuilder.launchAppScreens() {
        composable(SplashNavScreen.route) {
            /*SplashScreen()*/
        }
    }
}
