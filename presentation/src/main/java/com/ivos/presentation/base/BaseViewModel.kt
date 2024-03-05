package com.ivos.presentation.base

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.barteksc.pdfviewer.BuildConfig
import com.ivos.common.errors.Failure
import com.ivos.common.errors.ThrowableWrapper
import com.ivos.common.network.NoNetworkInterceptor.Companion.NO_NETWORK_RESPONSE_CODE
import com.ivos.common.network.SetUpNetworkModule.Companion.ServerNotAvailableException
import com.ivos.presentation.navigation.navCore.AppNavigator
import com.ivos.presentation.features.serverNotAvailable.ServerNotAvailableScreenNav
import com.ivos.presentation.features.noNetwork.NoNetworkScreenNav
import io.ktor.client.plugins.ResponseException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

abstract class BaseViewModel<EVENT> : ViewModel()  {

    @field:Inject
    protected lateinit var appNavigator: AppNavigator

    private val _errorFlow = MutableSharedFlow<Failure>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )
    val errorFlow: SharedFlow<Failure> = _errorFlow

    fun getAppNavigatorFlow() = appNavigator.navigationFlow

    private val handler = CoroutineExceptionHandler { _, exception ->
        handleError(ThrowableWrapper(exception))
    }

    internal open fun onTriggerEvent(eventType: EVENT) {
        //By default, this function nothing does
    }

    internal fun executeSuspend(
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        function: suspend () -> Unit,
    ) {
        launchScope(dispatcher, function)
    }

    internal fun executeSuspendForUiUpdate(
        dispatcher: CoroutineDispatcher = Dispatchers.Main.immediate,
        function: suspend () -> Unit,
    ) {
        launchScope(dispatcher, function)
    }

    internal fun handleError(error: Failure) {
        if (error is ThrowableWrapper) {
            if (BuildConfig.DEBUG) {
                Log.e(this::class.simpleName, "handleError: ", error.throwable)
            }

            if (!BuildConfig.DEBUG) {
                //FirebaseCrashlytics.getInstance().recordException(error.throwable)
            }

            when (error.throwable) {
                is ServerNotAvailableException -> {
                    appNavigator.navigate(ServerNotAvailableScreenNav)
                }

                is ResponseException -> {
                    checkResponseCode(error.throwable)
                }

                else -> {
                    _errorFlow.tryEmit(error)
                }
            }
        }
    }

    private fun checkResponseCode(throwable: Throwable) {
        (throwable as? ResponseException)?.response?.status?.value?.let {
            if (it == NO_NETWORK_RESPONSE_CODE)
                appNavigator.navigate(NoNetworkScreenNav)
        }
    }

    internal fun <V, T : StateFlow<V>> MutableStateFlow<V>.updateErrorState(value: V) {
        update { value }
    }

    private fun launchScope(
        dispatcher: CoroutineDispatcher,
        function: suspend () -> Unit
    ) {
        viewModelScope.launch(handler) {
            withContext(dispatcher) {
                function.invoke()
            }
        }
    }
}
