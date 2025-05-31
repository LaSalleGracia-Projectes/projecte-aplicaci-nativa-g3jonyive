package com.connectyourcoach.connectyourcoach.repositories

import com.connectyourcoach.connectyourcoach.models.ErrorResponse
import com.connectyourcoach.connectyourcoach.network.NetworkUtils.httpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.delete
import io.ktor.client.request.put
import io.ktor.client.request.headers
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.isSuccess
import io.ktor.http.contentType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.connectyourcoach.connectyourcoach.models.CustomException
import kotlinx.coroutines.IO

class BaseRepository {
    suspend inline fun <reified T> getData(
        url: String,
        token: String? = null,
        crossinline onSuccessResponse: (T) -> Unit,
        crossinline onErrorResponse: (ErrorResponse) -> Unit,
        crossinline onFinish: () -> Unit = {}
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = httpClient.get(url) {
                    token?.let {
                        headers {
                            append("Authorization", it)
                        }
                    }
                }

                if (response.status.isSuccess()) {
                    val data = response.body<T>()
                    onSuccessResponse(data)
                } else {
                    if (response.status.value == 502) throw Exception("Could not connect to the server")
                    val errorResponse = response.body<ErrorResponse>()
                    onErrorResponse(errorResponse)
                }
            } catch (e: Exception) {
                val errorResponse = ErrorResponse(
                    error = "Connection Error",
                    details = e.message ?: "Failed to connect",
                    exception = CustomException.FailedToConnectException
                )
                onErrorResponse(errorResponse)
            } finally {
                onFinish()
            }
        }
    }

    suspend inline fun <reified T> postData(
        url: String,
        token: String? = null,
        body: T? = null,
        crossinline onSuccessResponse: (T) -> Unit,
        crossinline onErrorResponse: (ErrorResponse) -> Unit,
        crossinline onFinish: () -> Unit = {}
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = httpClient.post(url) {
                    token?.let {
                        headers {
                            append("Authorization", it)
                        }
                    }
                    body?.let {
                        contentType(ContentType.Application.Json)
                        setBody(it)
                    }
                }

                if (response.status.isSuccess()) {
                    val data = response.body<T>()
                    onSuccessResponse(data)
                } else {
                    if (response.status.value == 502) throw Exception("Could not connect to the server")
                    val errorResponse = response.body<ErrorResponse>()
                    onErrorResponse(errorResponse)
                }
            } catch (e: Exception) {
                val errorResponse = ErrorResponse(
                    error = "Connection Error",
                    details = e.message ?: "Failed to connect",
                    exception = CustomException.FailedToConnectException
                )
                onErrorResponse(errorResponse)
            } finally {
                onFinish()
            }
        }
    }

    suspend inline fun <reified T> deleteData(
        url: String,
        token: String? = null,
        body: T? = null,
        crossinline onSuccessResponse: (T?) -> Unit,
        crossinline onErrorResponse: (ErrorResponse) -> Unit,
        crossinline onFinish: () -> Unit = {}
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = httpClient.delete(url) {
                    token?.let {
                        headers {
                            append("Authorization", it)
                        }
                    }
                    body?.let {
                        contentType(ContentType.Application.Json)
                        setBody(it)
                    }
                }

                if (response.status.isSuccess()) {
                    if (response.status.value == 204) {
                        onSuccessResponse(null)
                        return@launch
                    }
                    val data = response.body<T>()
                    onSuccessResponse(data)
                } else {
                    if (response.status.value == 502) throw Exception("Could not connect to the server")
                    val errorResponse = response.body<ErrorResponse>()
                    onErrorResponse(errorResponse)
                }
            } catch (e: Exception) {
                val errorResponse = ErrorResponse(
                    error = "Connection Error",
                    details = e.message ?: "Failed to connect",
                    exception = CustomException.FailedToConnectException
                )
                onErrorResponse(errorResponse)
            } finally {
                onFinish()
            }
        }
    }

    suspend inline fun <reified T> putData(
        url: String,
        token: String? = null,
        body: T? = null,
        crossinline onSuccessResponse: (T) -> Unit,
        crossinline onErrorResponse: (ErrorResponse) -> Unit,
        crossinline onFinish: () -> Unit = {}
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = httpClient.put(url) {
                    token?.let {
                        headers {
                            append("Authorization", it)
                        }
                    }
                    body?.let {
                        contentType(ContentType.Application.Json)
                        setBody(it)
                    }
                }

                if (response.status.isSuccess()) {
                    val data = response.body<T>()
                    onSuccessResponse(data)
                } else {
                    if (response.status.value == 502) throw Exception("Could not connect to the server")
                    val errorResponse = response.body<ErrorResponse>()
                    onErrorResponse(errorResponse)
                }
            } catch (e: Exception) {
                val errorResponse = ErrorResponse(
                    error = "Connection Error",
                    details = e.message ?: "Failed to connect",
                    exception = CustomException.FailedToConnectException
                )
                onErrorResponse(errorResponse)
            } finally {
                onFinish()
            }
        }
    }
}
