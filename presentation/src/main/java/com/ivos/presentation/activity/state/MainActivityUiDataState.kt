package com.ivos.presentation.activity.state

import com.ivos.presentation.R
import com.ivos.presentation.base.BaseUiData

data class MainActivityUiDataState(
    val toolbarState: ToolbarState = ToolbarState(),
    val bottomNavState: BottomNavigationState = BottomNavigationState(),
) : BaseUiData

data class ToolbarState(
    val titleName: Int = R.string.empty,
    val topAppbarVisibility: Boolean = false,
    val navIconVisibility: Boolean = false,
)

data class BottomNavigationState(
    val showBottomNavigation: Boolean = true,
)
