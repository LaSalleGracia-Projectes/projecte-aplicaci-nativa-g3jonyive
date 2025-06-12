package com.connectyourcoach.connectyourcoach.repositories

import com.connectyourcoach.connectyourcoach.models.ErrorResponse
import com.connectyourcoach.connectyourcoach.network.NetworkUtils.httpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.isSuccess
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import com.connectyourcoach.connectyourcoach.models.CustomException
import io.ktor.client.request.delete
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import io.ktor.util.InternalAPI
import io.ktor.utils.io.ByteReadChannel

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

    @OptIn(InternalAPI::class)
    suspend inline fun <reified T> uploadImageFile(
        url: String,
        imageBytes: ByteArray,
        token: String? = null,
        crossinline onSuccessResponse: (T) -> Unit,
        crossinline onErrorResponse: (ErrorResponse) -> Unit,
        crossinline onFinish: () -> Unit = {}
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = httpClient.submitFormWithBinaryData(
                    url = url,
                    formData = formData {
                        append(
                            key = "image",
                            value = imageBytes,
                            headers = Headers.build {
                                append(HttpHeaders.ContentType, "image/jpeg")
                                append(HttpHeaders.ContentDisposition, "filename=imagen.jpeg")
                            }
                        )
                    }
                ) {
                    method = HttpMethod.Post
                    token?.let {
                        headers {
                            append(HttpHeaders.Authorization, it)
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
}