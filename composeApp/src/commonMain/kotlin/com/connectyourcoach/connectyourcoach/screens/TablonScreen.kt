package com.connectyourcoach.connectyourcoach.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.connectyourcoach.connectyourcoach.components.scaffold.BaseScaffold
import com.connectyourcoach.connectyourcoach.components.scaffold.TablonTopBar
import com.connectyourcoach.connectyourcoach.viewmodels.TablonViewModel
import com.connectyourcoach.connectyourcoach.views.TablonView

class TablonScreen : Screen {

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
                paddingValues = paddingValues,
                onClickPost = { postId ->
                    print("Post ID: $postId")
                }
            )
        }
    }
}
