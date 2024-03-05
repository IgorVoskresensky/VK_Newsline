package com.ivos.presentation.navigation.navScreens

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.ui.graphics.vector.ImageVector
import com.ivos.presentation.R
import com.ivos.presentation.navigation.navCore.NavigationComposable

sealed class BottomNavigation(
    override val route: String,
    @StringRes override val label: Int,
    val icon: ImageVector,
) : NavigationComposable {

    data object LoginNavScreen : BottomNavigation(
        route = "LoginScreen",
        label = R.string.login,
        icon = Icons.Filled.AccountBox,
    )
}
