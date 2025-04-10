plugins {
    // Plugins bàsics
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false

    // Plugins per Compose Multiplatform
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false

    // Firebase i serveis de Google
    alias(libs.plugins.googleServices) apply false
}