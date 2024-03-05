package com.ivos.presentation.features.splash.navigation

import com.ivos.presentation.navigation.navCore.NavigationDirection
import com.ivos.presentation.navigation.navCore.NavigationIntent
import com.ivos.presentation.navigation.navScreens.BottomNavigation
import com.ivos.presentation.navigation.navScreens.BottomNavigation.LoginNavScreen
import com.ivos.presentation.navigation.navScreens.LaunchAppNavigation
import com.ivos.presentation.navigation.navScreens.LaunchAppNavigation.SplashNavScreen

object SplashScreenToLoginScreenNav : NavigationDirection {

    override fun navigationIntent(): NavigationIntent {
        return NavigationIntent.NavigateTo(
            route = LoginNavScreen.route,
            popUpToRoute = SplashNavScreen.route,
            inclusive = true,
            blockBackNavigation = false,
        )
    }
}
