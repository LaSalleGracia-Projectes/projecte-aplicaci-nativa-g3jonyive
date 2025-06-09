package com.connectyourcoach.connectyourcoach.repositories

import com.connectyourcoach.connectyourcoach.BASE_URL
import com.connectyourcoach.connectyourcoach.models.ErrorResponse
import com.connectyourcoach.connectyourcoach.models.Like
import com.connectyourcoach.connectyourcoach.models.Post
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class TablonRepository {
    private val baseUrl = "$BASE_URL/post"
    private val baseRepository = BaseRepository()

    suspend fun getPosts(
        onSuccessResponse: (List<Post>) -> Unit,
        onErrorResponse: (ErrorResponse) -> Unit,
        onFinish: () -> Unit = {}
    ) {
        baseRepository.getData<List<Post>>(
            url = baseUrl,
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
        val url = "$baseUrl/$id"
        baseRepository.getData<Post>(
            url = url,
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
            url = baseUrl,
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
        token: String,
        onSuccessResponse: (Post) -> Unit,
        onErrorResponse: (ErrorResponse) -> Unit,
        onFinish: () -> Unit = {}
    ) {
        baseRepository.postData<Post>(
            url = baseUrl,
            body = post,
            token = token,
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
        val url = "$baseUrl/$id"
        baseRepository.deleteData<Post>(
            url = url,
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
        val url = "$baseUrl/${post.id}"
        baseRepository.putData<Post>(
            url = url,
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
        val url = "$baseUrl/like/$id"
        baseRepository.getData<Int>(
            url = url,
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
        val url = "$baseUrl/like/$id"
        baseRepository.postData<Like?>(
            url = url,
            token = token,
            onSuccessResponse = {
                onSuccessResponse(it != null)
            },
            onErrorResponse = onErrorResponse,
            onFinish = onFinish
        )
    }

    private val client = HttpClient(CIO)

    suspend fun getPostsByUserIdSuspend(userId: String): List<Post> = withContext(Dispatchers.IO) {
        try {
            val response: HttpResponse = client.get(baseUrl) {
                parameter("userId", userId)
                accept(ContentType.Application.Json)
            }
            if (response.status == HttpStatusCode.OK) {
                val posts: List<Post> = response.body()
                posts
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}
