package com.ivos.presentation.activity.viewmodel

import android.net.ConnectivityManager
import android.net.LinkProperties
import android.net.Network
import android.net.NetworkCapabilities
import android.util.Log
import com.ivos.common.utils.AppContextProvider
import com.ivos.presentation.base.BaseViewModel
import com.ivos.presentation.navigation.navCore.PopNavStack
import com.ivos.presentation.features.noNetwork.NoNetworkScreenNav
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NoNetworkViewModel @Inject constructor(
    private val appContextProvider: AppContextProvider,
) : BaseViewModel<Nothing>() {

    private val connectivityManager by lazy {
        appContextProvider.getConnectivityManager()
    }

    private val networkCallback by lazy {
        object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                appNavigator.navigate(PopNavStack)
                Log.d(TAG, "onAvailable()")
            }

            override fun onLost(network: Network) {
                appNavigator.navigate(NoNetworkScreenNav)
                Log.d(TAG, "onLost()")
            }

            override fun onCapabilitiesChanged(
                network: Network,
                networkCapabilities: NetworkCapabilities
            ) {
                Log.w(
                    TAG,
                    "The default network changed capabilities: $networkCapabilities"
                )
            }

            override fun onLinkPropertiesChanged(
                network: Network,
                linkProperties: LinkProperties,
            ) {
                Log.w(
                    TAG,
                    "The default network changed link properties: $linkProperties"
                )
            }
        }
    }

    fun registerNetworkCallback() {
        connectivityManager.registerDefaultNetworkCallback(networkCallback)
    }

    override fun onCleared() {
        super.onCleared()
        clearNetworkCallback()
    }

    private fun clearNetworkCallback() {
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }

    private companion object {
        private const val TAG = "MvnoNetwork"
    }
}
