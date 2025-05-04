package com.connectyourcoach.connectyourcoach.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.connectyourcoach.connectyourcoach.components.AvatarIcon
import com.connectyourcoach.connectyourcoach.viewmodels.AddChatViewModel

@Composable
fun AddChatView(
    paddingValues: PaddingValues,
    viewModel: AddChatViewModel,
    onFinish: () -> Unit,
) {
    val users by viewModel.users
    val loading by viewModel.loading
    val phone by viewModel.phone

    Column (
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
    ){
        OutlinedTextField(
            value = phone,
            onValueChange = { viewModel.onSearchUser(it) },
            label = { Text("Phone") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        if (loading) {
            Text(
                text = "Loading...",
                modifier = Modifier.padding(16.dp)
            )
            return
        } else if (users.isEmpty()) {
            Text(
                text = "No users found",
                modifier = Modifier.padding(16.dp)
            )
            return@Column
        }

        LazyColumn (
            modifier = Modifier.fillMaxSize()
        ){
            items(users) { user ->
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .clickable {
                            viewModel.onSelectUser(user)
                            onFinish()
                        },
                ) {
                    AvatarIcon(user.profile_picture ?: "")
                    Text(
                        text = user.username,
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}