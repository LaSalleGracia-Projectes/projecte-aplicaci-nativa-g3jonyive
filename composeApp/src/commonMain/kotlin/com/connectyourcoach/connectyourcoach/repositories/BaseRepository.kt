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

class BaseRepository {
    inline fun <reified T> getData(
        url: String,
        crossinline onSuccessResponse: (T) -> Unit,
        crossinline onErrorResponse: (ErrorResponse) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = httpClient.get(url)

            if (response.status.isSuccess()) {
                val data = response.body<T>()
                onSuccessResponse(data)
            } else {
                val errorResponse = response.body<ErrorResponse>()
                onErrorResponse(errorResponse)
            }
        }
    }
}