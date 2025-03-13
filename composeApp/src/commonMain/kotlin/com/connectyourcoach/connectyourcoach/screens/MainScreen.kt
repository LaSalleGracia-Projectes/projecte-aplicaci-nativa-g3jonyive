package com.connectyourcoach.connectyourcoach.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.connectyourcoach.connectyourcoach.components.scaffold.BaseScaffold
import com.connectyourcoach.connectyourcoach.components.scaffold.TablonTopBar
import com.connectyourcoach.connectyourcoach.viewmodels.TablonViewModel
import com.connectyourcoach.connectyourcoach.views.TablonView

class MainScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val viewModel by remember { mutableStateOf(TablonViewModel()) }

        BaseScaffold(
            navigator = navigator,
            topBar = { TablonTopBar(onMoreOptions = {  }) },
        ) { paddingValues ->
            TablonView(
                viewModel = viewModel,
                paddingValues = paddingValues
            )
        }
    }
}
