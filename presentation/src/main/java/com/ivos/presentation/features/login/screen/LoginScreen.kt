package com.ivos.presentation.features.login.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.ivos.presentation.features.login.state.LoginUiData
import com.ivos.presentation.features.login.state.LoginUiState
import com.ivos.presentation.features.login.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
) {
    
    val state = viewModel.uiState.collectAsState()

    LoginScreenHandler(
        state = state.value,
    )
}

@Composable
private fun LoginScreenHandler(
    state: LoginUiState,
) {
    when(state) {
        is LoginUiState.Content -> LoginScreenContent(state.data)
        is LoginUiState.Error -> Unit
        is LoginUiState.Loading -> CircularProgressIndicator()
    }
}

@Composable
fun LoginScreenContent(
    data: LoginUiData
) {
}
