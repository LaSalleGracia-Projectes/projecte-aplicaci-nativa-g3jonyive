package org.auth.def

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    fun onRegister(email: String, password: String) {
        viewModelScope.launch {
            try {
                // Intentamos registrar el usuario
                Firebase.auth.createUserWithEmailAndPassword(email, password)
                // Aquí puedes hacer algo si la creación del usuario es exitosa
            } catch (e: Exception) {
                // Manejo de errores, si ocurre algún problema
                println("Error: ${e.message}")
            }
        }
    }
}