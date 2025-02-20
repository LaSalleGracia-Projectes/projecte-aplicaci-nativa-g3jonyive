package org.connectyourcoach.projectkmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform