package com.ivos.presentation.navigation

import androidx.navigation.NavDestination
import androidx.navigation.navOptions
import com.ivos.presentation.navigation.navCore.NavigationComposable
import com.ivos.presentation.navigation.navCore.NavigationIntent
import com.ivos.presentation.navigation.navCore.NavigationIntent.NavigateTo

internal fun List<NavigationComposable>.findRoute(
    destination: NavDestination,
    title: (Int) -> Unit,
) {
    this.find {
        it.route == destination.route || destination.route?.contains(it.route) ?: false
    }?.label?.let {
        title.invoke(it)
    }
}

internal fun NavigateTo.buildNavOptions() = navOptions {
    popUpTo(this@buildNavOptions.popUpToRoute) {
        inclusive = this@buildNavOptions.inclusive
    }
}
