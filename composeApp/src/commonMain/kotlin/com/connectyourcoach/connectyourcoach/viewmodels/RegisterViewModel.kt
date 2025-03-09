package com.connectyourcoach.connectyourcoach.views

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    var fullname by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var phoneNumber by mutableStateOf("")

    fun onRegister(fullname: String, email: String, password: String, phoneNumber: String) {
        viewModelScope.launch {
            try {
                // Intentem registrar l'usuari a Firebase
                val result = Firebase.auth.createUserWithEmailAndPassword(email, password)

                // Guardem la informació addicional de l'usuari
                this@RegisterViewModel.fullname = fullname
                this@RegisterViewModel.email = email
                this@RegisterViewModel.phoneNumber = phoneNumber

                // Aquí pots fer alguna cosa si la creació de l'usuari és exitosa, com actualitzar la UI
                println("Usuari creat amb èxit!")
            } catch (e: Exception) {
                // Maneig d'errors si alguna cosa surt malament
                println("Error: ${e.message}")
            }
        }
    }
}