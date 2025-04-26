package com.connectyourcoach.connectyourcoach

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.connectyourcoach.connectyourcoach.auth.GoogleAuthHelper
import com.connectyourcoach.connectyourcoach.views.TablonView

class MainActivity : ComponentActivity() {
    companion object {
        lateinit var googleAuthHelper: GoogleAuthHelper
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        googleAuthHelper = GoogleAuthHelper()
        setContent {
            App()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        googleAuthHelper.handleActivityResult(requestCode, resultCode, data)
    }
}