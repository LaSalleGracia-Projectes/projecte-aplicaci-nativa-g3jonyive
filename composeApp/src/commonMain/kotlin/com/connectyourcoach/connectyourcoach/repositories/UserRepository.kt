package com.connectyourcoach.connectyourcoach.repositories

import com.connectyourcoach.connectyourcoach.BASE_URL
import com.connectyourcoach.connectyourcoach.models.ErrorResponse
import com.connectyourcoach.connectyourcoach.models.User

class UserRepository {
    val URL = "$BASE_URL/user"
    val baseRepository = BaseRepository()

    suspend fun getUsers(
        onSuccessResponse: (List<User>) -> Unit,
        onErrorResponse: (ErrorResponse) -> Unit,
        onFinish: () -> Unit = {}
    ) {
        baseRepository.getData<List<User>>(
            url = URL,
            onSuccessResponse = onSuccessResponse,
            onErrorResponse = onErrorResponse,
            onFinish = onFinish
        )
    }

    suspend fun getUserByNicknameOrUID(
        nickname: String,
        onSuccessResponse: (User) -> Unit,
        onErrorResponse: (ErrorResponse) -> Unit,
        onFinish: () -> Unit = {}
    ) {
        val URL = "$URL/$nickname"
        baseRepository.getData<User>(
            url = URL,
            onSuccessResponse = onSuccessResponse,
            onErrorResponse = onErrorResponse,
            onFinish = onFinish
        )
    }

    suspend fun createUser(
        user: User,
        onSuccessResponse: (User) -> Unit,
        onErrorResponse: (ErrorResponse) -> Unit,
        onFinish: () -> Unit = {}
    ) {
        baseRepository.postData<User>(
            url = URL,
            body = user,
            onSuccessResponse = onSuccessResponse,
            onErrorResponse = onErrorResponse,
            onFinish = onFinish
        )
    }

    suspend fun deleteUser(
        nickname: String,
        token: String,
        onSuccessResponse: (User?) -> Unit,
        onErrorResponse: (ErrorResponse) -> Unit,
        onFinish: () -> Unit = {}
    ) {
        val URL = "$URL/$nickname"
        baseRepository.deleteData<User>(
            url = URL,
            token = token,
            onSuccessResponse = onSuccessResponse,
            onErrorResponse = onErrorResponse,
            onFinish = onFinish
        )
    }

    suspend fun updateUser(
        user: User,
        token: String,
        onSuccessResponse: (User) -> Unit,
        onErrorResponse: (ErrorResponse) -> Unit,
        onFinish: () -> Unit = {}
    ) {
        val URL = "$URL/${user.username}"
        baseRepository.putData<User>(
            url = URL,
            token = token,
            body = user,
            onSuccessResponse = onSuccessResponse,
            onErrorResponse = onErrorResponse,
            onFinish = onFinish
        )
    }

    suspend fun uploadPhoto(
        nickname: String,
        photo: ByteArray,
        token: String,
        onSuccessResponse: (User) -> Unit,
        onErrorResponse: (ErrorResponse) -> Unit,
        onFinish: () -> Unit = {}
    ) {
        val URL = "$URL/$nickname/image"
        baseRepository.uploadImageFile<User>(
            url = URL,
            token = token,
            onSuccessResponse = onSuccessResponse,
            onErrorResponse = onErrorResponse,
            onFinish = onFinish,
            imageBytes = photo
        )
    }
}