package com.connectyourcoach.connectyourcoach.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import com.connectyourcoach.connectyourcoach.components.scaffold.BaseScaffold
import com.connectyourcoach.connectyourcoach.components.scaffold.TablonTopBar
import com.connectyourcoach.connectyourcoach.components.tablon.PostCard
import com.connectyourcoach.connectyourcoach.components.tablon.TablonSearchBar
import com.connectyourcoach.connectyourcoach.viewmodels.TablonViewModel

@Composable
fun TablonView(viewModel: TablonViewModel, paddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        val posts by viewModel.posts
        val query by viewModel.query

        TablonSearchBar(
            query = viewModel.query.value,
            onSearch = { viewModel.onSearch(it) }
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn {
            items(posts) { post ->
                PostCard(post = post)
            }
        }
    }
}
