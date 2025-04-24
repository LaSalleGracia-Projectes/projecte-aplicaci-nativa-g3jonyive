package com.connectyourcoach.connectyourcoach.components.tablon

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.connectyourcoach.connectyourcoach.models.Post
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun PostCard(post: Post, backgroundColor: Color, onClick: (String) -> Unit) {
    var showDialog by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .background(backgroundColor)
            .padding(8.dp)
            .clickable { onClick(post.id) },
        elevation = 4.dp,
        backgroundColor = MaterialTheme.colors.onSurface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.background.copy(alpha = 0.8f))
                .padding(16.dp)
        ) {
            Text(
                text = truncatedContent(post.title),
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.onPrimary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = truncatedContent(post.description),
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onPrimary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "User ID: ${post.user_id}",
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Price: ${post.price}€",
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.secondary
                )
                Button(
                    onClick = { showDialog = true },
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Text("Pagar")
                }
            }
        }
    }

    if (showDialog) {
        PagoTarjeta(showDialog = showDialog, onDismiss = { showDialog = false })
    }
}

fun truncatedContent(content: String): String {
    return if (content.length > 30) {
        content.substring(0, 30) + "..."
    } else {
        content
    }
}
@Composable
fun PagoTarjeta(showDialog: Boolean, onDismiss: () -> Unit) {
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
                                    delay(2000L)
                                    isProcessing = false
                                    paymentConfirmed = true
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