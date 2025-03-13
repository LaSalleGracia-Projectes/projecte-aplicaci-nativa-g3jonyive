package com.connectyourcoach.connectyourcoach.repositories

import com.connectyourcoach.connectyourcoach.models.Post

class TablonRepository {
    private var posts: List<Post> = emptyList()

    // TODO: Implement database connection
    init {
        for (i in 0..10) {
            posts += Post(
                id = "$i",
                title = "Title $i",
                description = "Description $i",
                user_id = i,
                created_at = "Date $i",
                price = i * 100f,
                updated_at = "Date $i",
                photo = "https://sillaoficina365.es/modules/dbblog/views/img/post/147-espacio-oficina-por-persona.webp"
            )
        }
    }

    fun getPosts() : List<Post> = posts

    fun searchPosts(query: String) : List<Post> {
        return posts.filter { post ->
            post.title.contains(query, ignoreCase = true) ||
            post.description.contains(query, ignoreCase = true)
        }
    }

    fun getPostById(id: String) : Post? {
        return posts.find { post -> post.id == id }
    }
}