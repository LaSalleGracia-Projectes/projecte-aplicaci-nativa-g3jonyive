package com.connectyourcoach.connectyourcoach.repositories

import com.connectyourcoach.connectyourcoach.BASE_URL
import com.connectyourcoach.connectyourcoach.models.ErrorResponse
import com.connectyourcoach.connectyourcoach.models.User
import com.connectyourcoach.connectyourcoach.network.NetworkUtils.httpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch

class UserRepository {
    val URL = "$BASE_URL/users"
    val baseRepository = BaseRepository()

    fun getUsers(
        onSuccessResponse: (List<User>) -> Unit,
        onErrorResponse: (ErrorResponse) -> Unit
    ) {
        baseRepository.getData<List<User>>(
            url = URL,
            onSuccessResponse = { response ->
                onSuccessResponse(response)
            },
            onErrorResponse = { errorResponse ->
                onErrorResponse(errorResponse)
            }
        )
    }

    fun getUserByUUID(
        UUID: String,
        onSuccessResponse: (User) -> Unit,
        onErrorResponse: (ErrorResponse) -> Unit
    ) {
        val URL = "$BASE_URL/user/$UUID"
        baseRepository.getData<User>(
            url = URL,
            onSuccessResponse = { response ->
                onSuccessResponse(response)
            },
            onErrorResponse = { errorResponse ->
                onErrorResponse(errorResponse)
            }
        )
    }

    fun getUserByNickname(
        nickname: String,
        onSuccessResponse: (User) -> Unit,
        onErrorResponse: (ErrorResponse) -> Unit
    ) {
        val URL = "$BASE_URL/user/$nickname"
        baseRepository.getData<User>(
            url = URL,
            onSuccessResponse = { response ->
                onSuccessResponse(response)
            },
            onErrorResponse = { errorResponse ->
                onErrorResponse(errorResponse)
            }
        )
    }
}