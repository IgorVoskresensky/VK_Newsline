package com.ivos.presentation.navigation.navCore

object PopNavStack : NavigationDirection {

    override fun navigationIntent(): NavigationIntent {
        return NavigationIntent.NavigateBack(
            blockBackNavigation = false,
        )
    }
}
