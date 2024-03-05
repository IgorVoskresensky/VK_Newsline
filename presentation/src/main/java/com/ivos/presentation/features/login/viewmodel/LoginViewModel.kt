package com.ivos.presentation.features.login.viewmodel

import com.ivos.common.utils.EMPTY_STRING
import com.ivos.presentation.base.BaseViewModel
import com.ivos.presentation.features.login.state.LoginScreenTriggerEvent
import com.ivos.presentation.features.login.state.LoginUiData
import com.ivos.presentation.features.login.state.LoginUiState
import com.ivos.presentation.features.login.state.LoginUiState.Content
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : BaseViewModel<LoginScreenTriggerEvent>() {

    private val state = Content(LoginUiData()) //remove
    private val _uiState = MutableStateFlow<LoginUiState>(state /*replace with UiProgress*/)
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private val phoneStateFlow = MutableStateFlow(EMPTY_STRING)
    private val nameStateFlow = MutableStateFlow(EMPTY_STRING)

    init {
        getCachedPhone()
        getCachedName()
        combineDataAndEmit()
    }

    override fun onTriggerEvent(eventType: LoginScreenTriggerEvent) {
        when (eventType) {
            is LoginScreenTriggerEvent.NavigateNext -> {}
        }
    }

    private fun getCachedPhone() {
        phoneStateFlow.value = "89119998877"
    }

    private fun getCachedName() {
        nameStateFlow.value = "Igor"
    }

    private fun combineDataAndEmit() {
        executeSuspend {
            combine(
                phoneStateFlow,
                nameStateFlow,
            ) { phone, name ->
                Content(
                    data = LoginUiData(
                        phone = phone,
                        name = name,
                    ),
                )
            }.collect {
                _uiState.value = it
            }
        }
    }
}
