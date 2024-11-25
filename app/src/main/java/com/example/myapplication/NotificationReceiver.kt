package com.example.myapplication

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.myapplication.R

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val message = intent.getStringExtra("NOTIFICATION_MESSAGE") ?: "No message"

        // Create Intent to launch MainActivity when the notification is tapped
        val launchIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        // Create a PendingIntent for MainActivity
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            launchIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // If running on Android O or above, create a notification channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "notification_channel"
            val channelName = "General Notifications"
            val importance = android.app.NotificationManager.IMPORTANCE_DEFAULT
            val notificationChannel = android.app.NotificationChannel(channelId, channelName, importance).apply {
                description = "Channel for general notifications"
            }
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as android.app.NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
        }

        // Build the notification
        val builder = NotificationCompat.Builder(context, "notification_channel")
            .setSmallIcon(R.drawable.ic_notification)  // Notification icon
            .setContentTitle("Reminder")
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)  // Set PendingIntent for opening MainActivity
            .setAutoCancel(true)  // Dismiss notification when tapped

        // Send the notification
        with(NotificationManagerCompat.from(context)) {
            notify(1, builder.build())  // Notification ID: 1
        }
    }
}
