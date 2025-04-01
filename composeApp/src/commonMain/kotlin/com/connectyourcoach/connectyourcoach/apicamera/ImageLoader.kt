package com.connectyourcoach.connectyourcoach.apicamera

import io.ktor.client.HttpClient
import kotlin.random.Random

class ImageLoader(private val httpClient: HttpClient) {
    suspend fun getRandomAvatar(
        style: String = "personas",
        size: Int = 200,
        format: String = "png"
    ): String {
        // Genera una seed aleatòria diferent cada vegada
        val randomSeed = List(10) {
            Random.nextInt(0, 10).toString()
        }.joinToString("")

        return buildString {
            append("https://api.dicebear.com/7.x/$style/$format")
            append("?size=$size")
            append("&seed=$randomSeed")  // Seed aleatòria
            append("&flip=true")
            // Pots afegir més paràmetres aquí segons necessitis
        }
    }
}