package org.connectyourcoach.projectkmp

import android.os.Build
import com.connectyourcoach.connectyourcoach.Platform

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

fun getPlatform(): Platform = AndroidPlatform()