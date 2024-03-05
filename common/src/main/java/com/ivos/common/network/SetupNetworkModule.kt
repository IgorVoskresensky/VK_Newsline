package com.ivos.common.network

import com.ivos.common.network.BaseUrl.Companion.createBaseUrl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.client.statement.HttpResponse
import io.ktor.http.Url
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@[Module InstallIn(SingletonComponent::class)]
class SetUpNetworkModule {

    @[Provides Singleton]
    fun provideMvnoKtor(
        baseUrl: BaseUrl,
        networkManager: NetworkManager,
    ): HttpClient {
        return HttpClient(OkHttp) {
            expectSuccess = true

            install(Logging) {
                logger = KtorLogger()
                level = LogLevel.ALL
            }

            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }

            HttpResponseValidator {
                handleResponseExceptionWithRequest { exception, _ ->
                    val clientException = exception as? ClientRequestException
                        ?: return@handleResponseExceptionWithRequest

                    val exceptionResponse = clientException.response

                    if (exceptionResponse.status.value in SERVER_NOT_AVAILABLE) {
                        throw ServerNotAvailableException(httpResponse = exceptionResponse)
                    }
                }
            }

            defaultRequest {
                url {
                    protocol = Url(baseUrl.baseUrl).protocol
                    host = baseUrl.createBaseUrl()
                }

                header(ACCEPT, APP_JSON)
                header(CONTENT_TYPE, APP_JSON)
                header(USER_AGENT, USER_AGENT_VALUE)
            }

            engine {
                config {
                    connectTimeout(1, TimeUnit.MINUTES)
                    readTimeout(30, TimeUnit.SECONDS)
                    writeTimeout(30, TimeUnit.SECONDS)
                }

                addInterceptor(NoNetworkInterceptor(networkManager))
            }
        }
    }

    @[Provides Singleton]
    fun provideBaseUrl() = BaseUrl(baseUrl = "null")

    companion object {
        const val CONTENT_TYPE = "Content-Type"
        private const val APP_JSON = "application/json"
        private const val ACCEPT = "Accept"
        private const val AUTH = "Authorization"
        private const val USER_AGENT = "User-Agent"
        private const val USER_AGENT_VALUE = ""
        private val SERVER_NOT_AVAILABLE = 500..526

        data class ServerNotAvailableException(val httpResponse: HttpResponse) : Exception()
    }
}
