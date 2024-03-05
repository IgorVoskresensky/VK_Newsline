package com.ivos.presentation.navigation.navGraph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ivos.presentation.features.login.screen.LoginScreen
import com.ivos.presentation.navigation.navScreens.BottomNavigation.LoginNavScreen

object BottomScreensNav {

    val bottomNavScreens by lazy {
        listOf(
            LoginNavScreen,
        )
    }

    fun NavGraphBuilder.bottomNavigationScreens() {
        composable(LoginNavScreen.route) { from ->
            LoginScreen()
        }
    }
}
