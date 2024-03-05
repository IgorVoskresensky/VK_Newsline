package com.ivos.common.network

import io.ktor.http.Url

data class BaseUrl(val baseUrl: String) {

    companion object {
        fun BaseUrl.createBaseUrl(): String {
            return Url(baseUrl).run { host + encodedPath }.dropLast(1)
        }
    }
}
