package com.ivos.presentation.activity.viewmodel

import com.ivos.presentation.activity.state.MainActivityTriggerEvent
import com.ivos.presentation.activity.state.MainActivityTriggerEvent.ChangeBottomNavVisibility
import com.ivos.presentation.activity.state.MainActivityTriggerEvent.ChangeNavIconVisibility
import com.ivos.presentation.activity.state.MainActivityTriggerEvent.ChangeTitleName
import com.ivos.presentation.activity.state.MainActivityTriggerEvent.ChangeTopAppbarVisibility
import com.ivos.presentation.activity.state.MainActivityUiDataState
import com.ivos.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor() : BaseViewModel<MainActivityTriggerEvent>() {

    private val _mainActivityStateFlow = MutableStateFlow(MainActivityUiDataState())
    val mainActivityStateFlow: StateFlow<MainActivityUiDataState> = _mainActivityStateFlow.asStateFlow()

    override fun onTriggerEvent(eventType: MainActivityTriggerEvent) {
        when (eventType) {
            is ChangeTitleName -> {
                _mainActivityStateFlow.update { data ->
                    data.copy(
                        toolbarState = data.toolbarState.copy(
                            titleName = eventType.titleName
                        )
                    )
                }
            }

            is ChangeNavIconVisibility -> {
                _mainActivityStateFlow.update { data ->
                    data.copy(
                        toolbarState = data.toolbarState.copy(
                            navIconVisibility = eventType.visible
                        )
                    )
                }
            }

            is ChangeTopAppbarVisibility -> {
                _mainActivityStateFlow.update { data ->
                    data.copy(
                        toolbarState = data.toolbarState.copy(
                            topAppbarVisibility = eventType.visible
                        )
                    )
                }
            }

            is ChangeBottomNavVisibility -> {
                _mainActivityStateFlow.update { data ->
                    data.copy(
                        bottomNavState = data.bottomNavState.copy(
                            showBottomNavigation = eventType.visible
                        )
                    )
                }
            }
        }
    }
}
