package com.connectyourcoach.connectyourcoach.viewmodels

import androidx.compose.runtime.*

class AddCardViewModel {
    var title by mutableStateOf("")
        private set

    var content by mutableStateOf("")
        private set

    var price by mutableStateOf("")
        private set

    fun updateTitle(newTitle: String) {
        title = newTitle
    }

    fun updateContent(newContent: String) {
        content = newContent
    }

    fun updatePrice(newPrice: String) {
        if (newPrice.matches(Regex("^\\d*(\\.\\d{0,2})?$"))) {
            price = newPrice
        }
    }

    fun clear() {
        title = ""
        content = ""
        price = ""
    }

    fun isFormValid(): Boolean {
        return title.isNotBlank() && content.isNotBlank() && price.isNotBlank()
    }
}
