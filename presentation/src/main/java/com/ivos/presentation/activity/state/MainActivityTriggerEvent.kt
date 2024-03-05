package com.ivos.presentation.activity.state

import androidx.annotation.StringRes

sealed class MainActivityTriggerEvent {

    data class ChangeTitleName(@StringRes val titleName: Int) : MainActivityTriggerEvent()

    data class ChangeTopAppbarVisibility(val visible: Boolean) : MainActivityTriggerEvent()

    data class ChangeNavIconVisibility(val visible: Boolean) : MainActivityTriggerEvent()

    data class ChangeBottomNavVisibility(val visible: Boolean) : MainActivityTriggerEvent()
}
