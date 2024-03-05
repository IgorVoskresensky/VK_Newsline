package com.ivos.common.network

import android.util.Log
import io.ktor.client.plugins.logging.Logger

class KtorLogger : Logger {

    override fun log(message: String) {
        Log.d(this::class.simpleName, message)
    }
}
