package com.ivos.presentation.navigation.navCore

import com.ivos.common.utils.EMPTY_STRING

sealed class NavigationIntent {

    data class NavigateBack(
        val route: String = EMPTY_STRING,
        val inclusive: Boolean = false,
        val blockBackNavigation: Boolean = false,
    ) : NavigationIntent()

    data class NavigateTo(
        val route: String = EMPTY_STRING,
        val popUpToRoute: String = EMPTY_STRING,
        val inclusive: Boolean = false,
        val blockBackNavigation: Boolean = false,
    ) : NavigationIntent()
}
