package com.example.myapplication.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    var fullName by mutableStateOf("Captain Smollett")
    var position by mutableStateOf("Старший разработчик")
    var resumeUrl by mutableStateOf("https://example.com/resume.pdf")
    var avatarUri by mutableStateOf("")

    fun updateAvatarUri(uri: String) {
        avatarUri = uri
        // Save to SharedPreferences or database
    }
}
