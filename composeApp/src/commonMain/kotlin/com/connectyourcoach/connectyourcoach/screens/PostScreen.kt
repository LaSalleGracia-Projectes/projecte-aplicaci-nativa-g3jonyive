package com.connectyourcoach.connectyourcoach.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.connectyourcoach.connectyourcoach.components.scaffold.BaseScaffold
import com.connectyourcoach.connectyourcoach.components.scaffold.PostTopBar.PostTopBar
import com.connectyourcoach.connectyourcoach.viewmodels.PostViewModel
import com.connectyourcoach.connectyourcoach.views.PostView

class PostScreen(private val postId: String) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val viewModel by remember { mutableStateOf(PostViewModel(postId)) }

        BaseScaffold(
            navigator = navigator,
            topBar = { PostTopBar(
                    title = viewModel.post.value?.title ?: "Post",
                    onBack = { navigator?.pop() },
                ) },
        ) { paddingValues ->
            PostView(
                viewModel = viewModel,
                paddingValues = paddingValues,
            )
        }
    }
}