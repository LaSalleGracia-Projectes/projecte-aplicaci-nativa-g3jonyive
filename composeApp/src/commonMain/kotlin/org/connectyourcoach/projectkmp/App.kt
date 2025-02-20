package org.connectyourcoach.projectkmp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.AsyncImage
import org.jetbrains.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone

import androidx.compose.ui.graphics.Color

import androidx.compose.ui.unit.dp



@OptIn(ExperimentalCoilApi::class)
@Composable
@Preview
fun App() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(70.dp))

        AsyncImage(
            model = "https://definicion.de/wp-content/uploads/2019/07/perfil-de-usuario.png",
            contentDescription = "Foto de perfil",
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape)
                .border(4.dp, Color.Black, CircleShape)
        )

        Spacer(modifier = Modifier.height(24.dp))
        Text(text = "AuraTurqesa")
        Spacer(modifier = Modifier.height(150.dp))

        Box(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(start = 32.dp)) {
                ProfileRow(Icons.Default.AccountCircle, "Sergi Saravia Terricabras")
                ProfileRow(Icons.Default.Email, "sergi.saravia@gracia.lasalle.cat")
                ProfileRow(Icons.Default.Phone, "(+34) 634 534 089")
                ProfileRow(Icons.Default.DateRange, "11/03/2003")
            }
        }
    }
}

@Composable
fun ProfileRow(icon: ImageVector, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, contentDescription = null)
        Spacer(modifier = Modifier.width(15.dp))
        Text(text = text)
    }
}

@Preview
@Composable
fun AppPreview() {
    App()
}
