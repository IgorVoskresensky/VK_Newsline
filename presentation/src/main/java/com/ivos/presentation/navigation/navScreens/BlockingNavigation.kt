package com.ivos.presentation.navigation.navScreens

import androidx.annotation.StringRes
import com.ivos.presentation.R
import com.ivos.presentation.navigation.navCore.NavigationComposable

sealed class BlockingNavigation(
    override val route: String,
    @StringRes override val label: Int,
) : NavigationComposable {

    data object NoNetworkNavScreen : BlockingNavigation(
        route = "NoNetworkScreen",
        label = R.string.empty,
    )

    data object ServerNotAvailableNavScreen : BlockingNavigation(
        route = "ServerNotAvailableScreen",
        label = R.string.empty,
    )
}
