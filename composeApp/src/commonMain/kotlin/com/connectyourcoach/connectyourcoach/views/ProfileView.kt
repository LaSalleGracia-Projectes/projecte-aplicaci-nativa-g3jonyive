import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProfileView(viewModel: ProfileViewModel = viewModel()) {
    val userProfile by viewModel.userProfile

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (userProfile?.photoUrl?.isNotEmpty() == true) {
            AsyncImage(
                model = userProfile!!.photoUrl,
                contentDescription = "Profile photo",
                modifier = Modifier.size(128.dp).clip(CircleShape)
            )
        } else {
            Box(
                modifier = Modifier.size(128.dp).background(Color.Gray, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(userProfile?.name?.firstOrNull()?.uppercase() ?: "?", fontSize = 48.sp, color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(userProfile?.name ?: "", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text(userProfile?.email ?: "")
        Text(userProfile?.phone ?: "")
    }
}
