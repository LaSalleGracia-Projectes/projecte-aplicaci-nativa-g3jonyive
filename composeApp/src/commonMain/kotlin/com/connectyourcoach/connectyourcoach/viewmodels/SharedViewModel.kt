package com.connectyourcoach.connectyourcoach.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    var registerViewModel = mutableStateOf(RegisterViewModel())
}
