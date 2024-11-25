package com.example.myapplication

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        // Создаем канал уведомлений только если версия Android Oreo (API 26) или выше
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Channel Name" // Название канала
            val descriptionText = "Channel Description" // Описание канала
            val importance = NotificationManager.IMPORTANCE_DEFAULT // Уровень важности
            val channel = NotificationChannel("your_channel_id", name, importance).apply {
                description = descriptionText
            }
            // Получаем NotificationManager и создаем канал
            val notificationManager: NotificationManager =
                getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }
}
