package com.connectyourcoach.connectyourcoach

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import com.connectyourcoach.connectyourcoach.cameragallery.CameraGallery
import com.connectyourcoach.connectyourcoach.nav.NavigationWrapper

@Composable
fun App() {
    MaterialTheme {
        CameraGallery()
    }
}