package com.connectyourcoach.connectyourcoach.repositories

import com.connectyourcoach.connectyourcoach.BASE_URL
import com.connectyourcoach.connectyourcoach.models.ErrorResponse
import com.connectyourcoach.connectyourcoach.models.Like
import com.connectyourcoach.connectyourcoach.models.Post

class TablonRepository {
    val URL = "$BASE_URL/post"
    val baseRepository = BaseRepository()

    suspend fun getPosts(
        onSuccessResponse: (List<Post>) -> Unit,
        onErrorResponse: (ErrorResponse) -> Unit,
        onFinish: () -> Unit = {}
    ) {
        baseRepository.getData<List<Post>>(
            url = URL,
            onSuccessResponse = onSuccessResponse,
            onErrorResponse = onErrorResponse,
            onFinish = onFinish
        )
    }

    suspend fun getPostById(
        id: String,
        onSuccessResponse: (Post) -> Unit,
        onErrorResponse: (ErrorResponse) -> Unit,
        onFinish: () -> Unit = {}
    ) {
        val URL = "$URL/$id"
        baseRepository.getData<Post>(
            url = URL,
            onSuccessResponse = onSuccessResponse,
            onErrorResponse = onErrorResponse,
            onFinish = onFinish
        )
    }

    suspend fun searchPosts(
        query: String,
        onSuccessResponse: (List<Post>) -> Unit,
        onErrorResponse: (ErrorResponse) -> Unit,
        onFinish: () -> Unit = {}
    ) {
        baseRepository.getData<List<Post>>(
            url = URL,
            onSuccessResponse = { posts ->
                val filteredPosts = posts.filter { post ->
                    post.title.contains(query, ignoreCase = true) ||
                            post.description.contains(query, ignoreCase = true)
                }
                onSuccessResponse(filteredPosts)
            },
            onErrorResponse = onErrorResponse,
            onFinish = onFinish
        )
    }

    suspend fun createPost(
        post: Post,
        onSuccessResponse: (Post) -> Unit,
        onErrorResponse: (ErrorResponse) -> Unit,
        onFinish: () -> Unit = {}
    ) {
        baseRepository.postData<Post>(
            url = URL,
            body = post,
            onSuccessResponse = onSuccessResponse,
            onErrorResponse = onErrorResponse,
            onFinish = onFinish
        )
    }

    suspend fun deletePost(
        id: String,
        token: String,
        onSuccessResponse: (Post?) -> Unit,
        onErrorResponse: (ErrorResponse) -> Unit,
        onFinish: () -> Unit = {}
    ) {
        val URL = "$URL/$id"
        baseRepository.deleteData<Post>(
            url = URL,
            token = token,
            onSuccessResponse = onSuccessResponse,
            onErrorResponse = onErrorResponse,
            onFinish = onFinish
        )
    }

    suspend fun updatePost(
        post: Post,
        token: String,
        onSuccessResponse: (Post) -> Unit,
        onErrorResponse: (ErrorResponse) -> Unit,
        onFinish: () -> Unit = {}
    ) {
        val URL = "$URL/${post.id}"
        baseRepository.putData<Post>(
            url = URL,
            token = token,
            body = post,
            onSuccessResponse = onSuccessResponse,
            onErrorResponse = onErrorResponse,
            onFinish = onFinish
        )
    }

    suspend fun getPostLikes(
        id: String,
        token: String,
        onSuccessResponse: (Int) -> Unit,
        onErrorResponse: (ErrorResponse) -> Unit,
        onFinish: () -> Unit = {}
    ) {
        val URL = "$URL/like/$id"
        baseRepository.getData<Int>(
            url = URL,
            token = token,
            onSuccessResponse = onSuccessResponse,
            onErrorResponse = onErrorResponse,
            onFinish = onFinish
        )
    }

    suspend fun likePost(
        id: String,
        token: String,
        onSuccessResponse: (Boolean) -> Unit,
        onErrorResponse: (ErrorResponse) -> Unit,
        onFinish: () -> Unit = {}
    ) {
        val URL = "$URL/like/$id"
        baseRepository.postData<Like?>(
            url = URL,
            token = token,
            onSuccessResponse = {
                onSuccessResponse(it != null)
            },
            onErrorResponse = onErrorResponse,
            onFinish = onFinish
        )
    }
}