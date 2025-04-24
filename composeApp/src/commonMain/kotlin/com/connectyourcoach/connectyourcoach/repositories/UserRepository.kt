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
    val URL = "$BASE_URL/user"
    val baseRepository = BaseRepository()

    suspend fun getUsers(
        onSuccessResponse: (List<User>) -> Unit,
        onErrorResponse: (ErrorResponse) -> Unit
    ) {
        baseRepository.getData<List<User>>(
            url = URL,
            onSuccessResponse = onSuccessResponse,
            onErrorResponse = onErrorResponse
        )
    }

    suspend fun getUserByNickname(
        nickname: String,
        onSuccessResponse: (User) -> Unit,
        onErrorResponse: (ErrorResponse) -> Unit
    ) {
        val URL = "$URL/$nickname"
        baseRepository.getData<User>(
            url = URL,
            onSuccessResponse = onSuccessResponse,
            onErrorResponse = onErrorResponse
        )
    }

    suspend fun createUser(
        user: User,
        onSuccessResponse: (User) -> Unit,
        onErrorResponse: (ErrorResponse) -> Unit
    ) {
        baseRepository.postData<User>(
            url = URL,
            body = user,
            onSuccessResponse = onSuccessResponse,
            onErrorResponse = onErrorResponse
        )
    }

    suspend fun deleteUser(
        nickname: String,
        token: String,
        onSuccessResponse: (User?) -> Unit,
        onErrorResponse: (ErrorResponse) -> Unit
    ) {
        val URL = "$URL/$nickname"
        baseRepository.deleteData<User>(
            url = URL,
            token = token,
            onSuccessResponse = onSuccessResponse,
            onErrorResponse = onErrorResponse
        )
    }

    suspend fun updateUser(
        user: User,
        token: String,
        onSuccessResponse: (User) -> Unit,
        onErrorResponse: (ErrorResponse) -> Unit
    ) {
        val URL = "$URL/${user.username}"
        baseRepository.putData<User>(
            url = URL,
            token = token,
            body = user,
            onSuccessResponse = onSuccessResponse,
            onErrorResponse = onErrorResponse
        )
    }
}