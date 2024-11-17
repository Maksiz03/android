package com.example.myapplication.repository

import android.content.Context
import com.example.myapplication.model.UserProfile

class ProfileRepository(private val context: Context) {

    private val sharedPreferences = context.getSharedPreferences("user_profile", Context.MODE_PRIVATE)

    fun saveProfile(userProfile: UserProfile) {
        with(sharedPreferences.edit()) {
            putString("fullName", userProfile.fullName)
            putString("avatarUri", userProfile.avatarUri)
            putString("resumeUrl", userProfile.resumeUrl)
            apply()
        }
    }

    fun getProfile(): UserProfile {
        return UserProfile(
            fullName = sharedPreferences.getString("fullName", "") ?: "",
            avatarUri = sharedPreferences.getString("avatarUri", "") ?: "",
            resumeUrl = sharedPreferences.getString("resumeUrl", "") ?: ""
        )
    }
}
