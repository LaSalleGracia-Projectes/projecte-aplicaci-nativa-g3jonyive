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
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class BaseRepository {
    inline fun <reified T> getData(
        url: String,
        crossinline onSuccessResponse: (T) -> Unit,
        crossinline onErrorResponse: (ErrorResponse) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = httpClient.get(url)

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
            }
        }
    }

    inline fun <reified T> postData(
        url: String,
        body: T? = null,
        crossinline onSuccessResponse: (T) -> Unit,
        crossinline onErrorResponse: (ErrorResponse) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = httpClient.post(url) {
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
            }
        }
    }
}