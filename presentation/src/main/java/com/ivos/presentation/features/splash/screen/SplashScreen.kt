package com.ivos.presentation.features.splash.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.ivos.presentation.features.splash.state.SplashScreenTriggerEvent
import com.ivos.presentation.features.splash.state.SplashScreenTriggerEvent.NavToLoginScreenTrigger
import com.ivos.presentation.features.splash.viewmodel.SplashViewModel

@Composable
fun SplashScreen(viewModel: SplashViewModel = hiltViewModel()) {
    Text(text = "STUB! Splash screen")

    // TODO: Delete this later
    LaunchedEffect(key1 = viewModel) {
        viewModel.onTriggerEvent(NavToLoginScreenTrigger)
    }
}
