import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.googleServices)
    alias(libs.plugins.kotlinxSerialization)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    jvm("desktop")

    sourceSets {
        val commonMain by getting {
            dependencies {
                // KotlinX Coroutines
                implementation(libs.kotlinx.coroutines.core.v173)

                // Compose common UI
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.ui)
                implementation(compose.components.resources)
                implementation(compose.components.uiToolingPreview)

                // Lifecycle & ViewModel
                implementation(libs.androidx.lifecycle.viewmodel)
                implementation(libs.androidx.lifecycle.runtime.compose)

                // Image loading
                implementation(libs.coil.compose)
                implementation(libs.coil.network.ktor)

                // Networking
                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.content.negotiation)
                implementation(libs.ktor.serialization.kotlinx.json)

                // DateTime
                implementation(libs.kotlinx.datetime)

                // Dependency Injection
                implementation(libs.koin.core)
                implementation(libs.koin.compose)

                // Navigation
                implementation(libs.voyager.transitions)
                implementation(libs.voyager.navigator)
                implementation(libs.voyager.tabNavigator)

                // Camera / Image loading multiplatform
                implementation(libs.kamel.core)
                implementation(libs.kamel.image)
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(compose.preview)
                implementation(libs.androidx.activity.compose)
                implementation(libs.ktor.client.okhttp)

                // Camera & lifecycle android
                implementation(libs.koin.android)
                implementation(libs.compose.ui.tooling)

                // Firebase Android SDK BOM (for Google services, analytics, etc)
                implementation(platform(libs.android.firebase.bom))

                // Google Play services Auth (only on Android)
                implementation(libs.play.services.auth)
            }
        }

        val iosMain by getting {
            dependencies {
                implementation(libs.ktor.client.darwin)
            }
        }

        val desktopMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation(libs.kotlinx.coroutines.swing)
                implementation(libs.ktor.client.okhttp)

                // Compose Desktop tooling
                implementation(libs.compose.ui.tooling.preview)
                implementation(libs.compose.ui.tooling)
            }
        }
    }
}

android {
    namespace = "com.connectyourcoach.connectyourcoach"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.connectyourcoach.connectyourcoach"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    implementation(libs.androidx.ui.android)
    implementation(libs.androidx.ui.text.android)
    implementation(libs.androidx.material3.android)

    // Firebase Android SDK (some extra core stuff if needed)
    implementation(libs.firebase.common.ktx)
    implementation(libs.firebase.storage.ktx)

    debugImplementation(compose.uiTooling)
}

compose.desktop {
    application {
        mainClass = "com.connectyourcoach.connectyourcoach.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.connectyourcoach.connectyourcoach"
            packageVersion = "1.0.0"

            macOS {
                iconFile.set(project.file("resources/AppIcon.icns"))
            }
            windows {
                iconFile.set(project.file("resources/AppIcon.ico"))
            }
        }
    }
}

