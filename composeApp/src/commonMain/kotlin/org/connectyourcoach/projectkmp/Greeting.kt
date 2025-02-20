package org.connectyourcoach.projectkmp

import org.connectyourcoach.projectkmp.getPlatform

class Greeting {
    private val platform = getPlatform()

    fun greet(): String {
        return "Hello, ${platform.name}!"
    }
}