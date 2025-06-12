package com.connectyourcoach.connectyourcoach.views

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.connectyourcoach.connectyourcoach.viewmodels.PostViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// Definir la paleta de colores personalizada
private val DarkBlue = Color(0xFF173040)
private val Turquoise = Color(0xFF038C8C)
private val MintGreen = Color(0xFF5FD9AC)
private val LightGray = Color(0xFFD9D8D2)
private val Beige = Color(0xFFBFBCB4)

// Definir el tema con la paleta de colores
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
fun PostView(viewModel: PostViewModel, paddingValues: PaddingValues) {
    val post by viewModel.post
    val user by viewModel.user
    val isLiked by viewModel.isLiked
    val scrollState = rememberScrollState()
    val showDialog = remember { mutableStateOf(false) }
    val isPaid by viewModel.isPaid

    if (showDialog.value) {
        PagoTarjeta(
            showDialog = showDialog.value,
            onDismiss = { showDialog.value = false },
            setPayed = {
                viewModel.setPaid()
            }
        )
    }

    MaterialTheme(colors = customColors) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
                .padding(paddingValues)
                .verticalScroll(scrollState), // <- Aquí añadimos scroll
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            post?.let { post ->
                AsyncImage(
                    model = post.photo,
                    contentDescription = post.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colors.primary)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = post.title,
                    style = MaterialTheme.typography.h4,
                    color = MaterialTheme.colors.surface,
                    modifier = Modifier.padding(16.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "By ${user?.username ?: "Unknown User"}",
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.onPrimary,
                        modifier = Modifier.padding(16.dp)
                    )
                    Text(
                        text = "On ${post.created_at}",
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.onBackground,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                if (post.updated_at != post.created_at) {
                    Text(
                        text = "Updated on ${post.updated_at}",
                        style = MaterialTheme.typography.body2,
                        color = Beige,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                Text(
                    text = "Price: ${post.price}",
                    style = MaterialTheme.typography.body2,
                    color = MintGreen,
                    modifier = Modifier.padding(16.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    Spacer(modifier = Modifier.weight(1f))
                    LikeButton(
                        isLiked = isLiked,
                        onToggleLike = {
                            viewModel.onLike()
                        }
                    )
                    Spacer(modifier = Modifier.weight(0.5f))
                    Text(
                        text = "${viewModel.likes.value} Likes",
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.onPrimary,
                        modifier = Modifier.padding(16.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Button(
                        onClick = { showDialog.value = true },
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                        enabled = !isPaid
                    ) {
                        Text(if (!isPaid) "Pagar" else "Comprado")
                    }
                    Spacer(modifier = Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = post.description,
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onPrimary,
                    modifier = Modifier.padding(16.dp)
                )
            } ?: run {
                Text(
                    text = "Post not found",
                    color = MaterialTheme.colors.error
                )
            }
        }
    }
}

@Composable
fun LikeButton(
    isLiked: Boolean,
    onToggleLike: () -> Unit
) {
    val scale = remember { Animatable(1f) }

    LaunchedEffect(isLiked) {
        scale.snapTo(1f)
        scale.animateTo(
            targetValue = 1.3f,
            animationSpec = tween(durationMillis = 100, easing = LinearOutSlowInEasing)
        )
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 100, easing = FastOutLinearInEasing)
        )
    }

    IconButton(onClick = onToggleLike) {
        Icon(
            imageVector = if (isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
            contentDescription = if (isLiked) "Unlike" else "Like",
            tint = if (isLiked) Color.Red else Color.Gray,
            modifier = Modifier.scale(scale.value)
        )
    }
}


@Composable
fun PagoTarjeta(showDialog: Boolean, onDismiss: () -> Unit, setPayed: () -> Unit) {
    var cardNumber by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }

    var month by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }
    var isMonthValid by remember { mutableStateOf(true) }

    var paymentConfirmed by remember { mutableStateOf(false) }
    var isProcessing by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    fun isValid(): Boolean {
        val monthInt = month.toIntOrNull()
        val yearValid = year.length == 2
        val monthValid = month.length == 2 && monthInt in 1..12

        return cardNumber.length == 16 &&
                cvv.length == 3 &&
                monthValid && yearValid
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text("Pago con Tarjeta") },
            text = {
                when {
                    isProcessing -> {
                        CircularProgressIndicator(color = MaterialTheme.colors.secondary)
                    }
                    paymentConfirmed -> {
                        Text("✅ ¡Pago realizado con éxito!")
                        setPayed()
                    }
                    else -> {
                        Column {
                            Text("Introduce los datos de tu tarjeta:")
                            Spacer(modifier = Modifier.height(8.dp))

                            OutlinedTextField(
                                value = cardNumber,
                                onValueChange = {
                                    cardNumber = it.filter { c -> c.isDigit() }.take(16)
                                },
                                label = { Text("Número de tarjeta") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier.fillMaxWidth()
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                OutlinedTextField(
                                    value = month,
                                    onValueChange = {
                                        val digits = it.filter { c -> c.isDigit() }.take(2)
                                        month = digits
                                        isMonthValid = if (digits.length == 2) {
                                            val m = digits.toIntOrNull()
                                            m != null && m in 1..12
                                        } else true
                                    },
                                    label = { Text("Mes") },
                                    placeholder = { Text("MM") },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    isError = !isMonthValid,
                                    modifier = Modifier.weight(1f)
                                )

                                OutlinedTextField(
                                    value = year,
                                    onValueChange = {
                                        year = it.filter { c -> c.isDigit() }.take(2)
                                    },
                                    label = { Text("Año") },
                                    placeholder = { Text("AA") },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    modifier = Modifier.weight(1f)
                                )
                            }

                            if (!isMonthValid) {
                                Text(
                                    text = "El mes debe estar entre 01 y 12",
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            OutlinedTextField(
                                value = cvv,
                                onValueChange = {
                                    cvv = it.filter { c -> c.isDigit() }.take(3)
                                },
                                label = { Text("CVV") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            },
            confirmButton = {
                when {
                    paymentConfirmed -> {
                        TextButton(onClick = onDismiss) {
                            Text("Cerrar")
                        }
                    }
                    isProcessing -> {
                        TextButton(onClick = {}, enabled = false) {
                            Text("Procesando...")
                        }
                    }
                    else -> {
                        TextButton(
                            onClick = {
                                isProcessing = true
                                scope.launch {
                                    delay(2000) // Simular procesamiento
                                    paymentConfirmed = true
                                    isProcessing = false
                                }
                            },
                            enabled = isValid()
                        ) {
                            Text("Pagar ahora")
                        }
                    }
                }
            }
        )
    }
}