package com.connectyourcoach.connectyourcoach.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.connectyourcoach.connectyourcoach.viewmodels.AddCardViewModel

private val DarkBlue = Color(0xFF173040)
private val Turquoise = Color(0xFF038C8C)
private val MintGreen = Color(0xFF5FD9AC)
private val LightGray = Color(0xFFD9D8D2)

private val customColors = darkColors(
    primary = Turquoise,
    secondary = MintGreen,
    background = DarkBlue,
    surface = LightGray,
    onPrimary = Color.White,
    onSecondary = DarkBlue,
    onBackground = LightGray,
    onSurface = DarkBlue
)

@Composable
fun AddCardView(
    paddingValues: PaddingValues,
    viewModel: AddCardViewModel,
    onSubmit: () -> Unit
) {
    var title by viewModel.title
    var content by viewModel.description
    var price by viewModel.price

    MaterialTheme(colors = customColors) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colors.background)
        ) {
            Card(
                shape = RoundedCornerShape(16.dp),
                backgroundColor = MaterialTheme.colors.surface,
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    if (viewModel.isLoading.value) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(24.dp)
                                .align(Alignment.CenterHorizontally),
                            color = MaterialTheme.colors.primary
                        )
                        return@Column
                    }

                    Text(
                        text = "New Post",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.onSurface
                    )

                    OutlinedTextField(
                        value = title,
                        onValueChange = { viewModel.updateTitle(it) },
                        label = { Text("Title") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done
                        ),
                    )
                    if (viewModel.titleError.value.isNotEmpty()) {
                        Text(
                            text = viewModel.titleError.value,
                            color = Color.Red,
                            fontSize = 12.sp
                        )
                    }

                    OutlinedTextField(
                        value = content,
                        onValueChange = { viewModel.updateDescription(it) },
                        label = { Text("Content") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done
                        ),
                    )
                    if (viewModel.descriptionError.value.isNotEmpty()) {
                        Text(
                            text = viewModel.descriptionError.value,
                            color = Color.Red,
                            fontSize = 12.sp
                        )
                    }

                    OutlinedTextField(
                        value = price,
                        onValueChange = { viewModel.updatePrice(it) },
                        label = { Text("Price (€)") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
                    )
                    if (viewModel.priceError.value.isNotEmpty()) {
                        Text(
                            text = viewModel.priceError.value,
                            color = Color.Red,
                            fontSize = 12.sp
                        )
                    }

                    if (viewModel.error.value.isNotEmpty()) {
                        Text(
                            text = viewModel.error.value,
                            color = Color.Red,
                            fontSize = 12.sp
                        )
                    }

                    Button(
                        onClick = { viewModel.onClickAddPost(onSubmit) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("New Post", fontSize = 16.sp)
                    }
                }
            }
        }
    }
}
