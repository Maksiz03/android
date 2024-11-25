package com.example.myapplication.viewmodel

import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class ProfileViewModel : ViewModel() {
    var fullName by mutableStateOf("John Doe")
    var position by mutableStateOf("Developer")
    var resumeUrl by mutableStateOf("") // Assuming you have a resume URL field
    var notificationTime by mutableStateOf("08:00 AM") // Default notification time
    var notificationMessage by mutableStateOf("This is your notification") // Default notification message
}
