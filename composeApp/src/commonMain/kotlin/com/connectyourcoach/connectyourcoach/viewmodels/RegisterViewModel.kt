package com.connectyourcoach.connectyourcoach.views

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    private val _fullname = MutableLiveData("")
    val fullname: LiveData<String> get() = _fullname

    private val _email = MutableLiveData("")
    val email: LiveData<String> get() = _email

    private val _phoneNumber = MutableLiveData("")
    val phoneNumber: LiveData<String> get() = _phoneNumber

    fun onRegister(fullname: String, email: String, password: String, phoneNumber: String) {
        viewModelScope.launch {
            try {
                // Intentem registrar l'usuari a Firebase
                val result = Firebase.auth.createUserWithEmailAndPassword(email, password)

                // Guardem la informació addicional de l'usuari
                _fullname.value = fullname
                _email.value = email
                _phoneNumber.value = phoneNumber

                // Aquí pots fer alguna cosa si la creació de l'usuari és exitosa, com actualitzar la UI
                println("Usuari creat amb èxit!")
            } catch (e: Exception) {
                // Maneig d'errors si alguna cosa surt malament
                println("Error: ${e.message}")
            }
        }
    }

    // Funció per actualitzar la informació de l'usuari (si cal)
    fun updateUserDetails(fullname: String, phoneNumber: String) {
        _fullname.value = fullname
        _phoneNumber.value = phoneNumber
    }
}
