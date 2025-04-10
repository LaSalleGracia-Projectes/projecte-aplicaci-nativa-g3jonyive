package com.connectyourcoach.connectyourcoach.di

import com.connectyourcoach.connectyourcoach.views.MediaPicker
import org.koin.core.module.Module
import org.koin.dsl.module

object AppModule {
    val module: Module = module {
        single { MediaPicker() }
        // Aquí pots afegir més dependències
    }
}