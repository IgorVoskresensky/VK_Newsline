package com.ivos.common.network

import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

class NoNetworkInterceptor(
    private val networkManager: NetworkManager,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return if (networkManager.isNetworkAvailable())
            chain.proceed(chain.request())
        else {
            buildNoNetworkResponse(chain)
        }
    }

    private fun buildNoNetworkResponse(chain: Interceptor.Chain) = Response.Builder()
        .code(NO_NETWORK_RESPONSE_CODE)
        .body(BODY.toResponseBody(CONTENT_TYPE.toMediaType()))
        .protocol(Protocol.HTTP_2)
        .message(RESPONSE_MESSAGE)
        .request(chain.request())
        .build()

    companion object {
        const val NO_NETWORK_RESPONSE_CODE = 999
        private const val BODY = """{"message":"No internet connection!"}"""
        private const val RESPONSE_MESSAGE = "Network not available!"
        private const val CONTENT_TYPE = "application/json"
    }
}
