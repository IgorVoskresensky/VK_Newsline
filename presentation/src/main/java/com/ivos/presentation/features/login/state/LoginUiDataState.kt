package com.ivos.presentation.features.login.state

import com.ivos.common.errors.Failure
import com.ivos.common.utils.EMPTY_STRING
import com.ivos.presentation.base.BaseUiData

data class LoginUiData(
    val phone: String = EMPTY_STRING,
    val name: String = EMPTY_STRING,
) : BaseUiData

sealed interface LoginUiState{

    data class Content(val data: LoginUiData) : LoginUiState
    data class Error(val failure: Failure) : LoginUiState
    data object Loading : LoginUiState
}
