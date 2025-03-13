package com.connectyourcoach.connectyourcoach.repositories

import com.connectyourcoach.connectyourcoach.models.Post

class TablonRepository {
    private var posts: List<Post> = emptyList()

    private val customTitles: List<String> = listOf(
        "Explorando nuevas ideas 🚀",
        "Algo interesante está por venir...",
        "No vas a creer esto 🤯",
        "Un pequeño experimento 👀",
        "Aprendí algo nuevo hoy 🤓",
        "Reflexiones de medianoche 🌙",
        "Esto puede cambiarlo todo",
        "¿Y si intentamos esto?",
        "Notas rápidas sobre algo importante",
        "Pensamientos sueltos del día",
        "Probando cosas nuevas 🔥",
        "Un concepto en proceso...",
        "Todavía no tiene nombre 🤔",
        "Algo en lo que he estado trabajando...",
        "Simplemente una idea 💡",
        "Esto podría ser útil para alguien",
        "Un pequeño recordatorio para mí mismo",
        "Lo mejor que he encontrado hoy",
        "Un resumen de lo que aprendí",
        "Esto podría convertirse en algo grande"
    )

    // TODO: Implement database connection
    init {
        for (i in 0..customTitles.size) {
            posts += Post(
                id = "$i",
                title = customTitles.getOrNull(i) ?: "Post $i",
                description = "$i: A veces, una idea comienza como un simple pensamiento suelto. No siempre tiene un propósito definido, pero con el tiempo, puede evolucionar en algo más grande. En este post, quiero compartir una reflexión, un concepto o quizás solo una nota rápida sobre algo que me ha parecido interesante. Tal vez sea una idea que merece más desarrollo, o quizás simplemente un pequeño apunte para no olvidar. Sea como sea, aquí está: una pieza más en este rompecabezas de pensamientos y aprendizajes diarios.",
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