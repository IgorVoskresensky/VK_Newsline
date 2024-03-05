package com.ivos.presentation.navigation.navScreens

import androidx.annotation.StringRes
import com.ivos.presentation.R
import com.ivos.presentation.navigation.navCore.NavigationComposable

sealed class LaunchAppNavigation(
    override val route: String,
    @StringRes override val label: Int = R.string.empty,
) : NavigationComposable {

    data object SplashNavScreen : LaunchAppNavigation(
        route = "SplashScreen",
    )
}
