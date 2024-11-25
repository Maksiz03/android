package com.example.myapplication.ui.screens

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import android.app.TimePickerDialog
import android.widget.TimePicker
import com.example.myapplication.R
import com.example.myapplication.viewmodel.ProfileViewModel
import com.example.myapplication.NotificationReceiver
import java.util.*
import com.example.myapplication.ui.theme.EditProfileFragment.TimeUtils.convertTimeToMillis

@Composable
fun ProfileScreen(viewModel: ProfileViewModel) {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }

    // Функция для проверки и запроса разрешения на точные будильники (API 23+)
    fun checkAndRequestExactAlarmPermission(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            if (!alarmManager.canScheduleExactAlarms()) {
                Toast.makeText(context, "Please enable exact alarms in settings.", Toast.LENGTH_SHORT).show()
                val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
                context.startActivity(intent)
            }
        }
    }

    // Функция для планирования уведомления
    fun scheduleNotification(context: Context, time: String, message: String) {
        checkAndRequestExactAlarmPermission(context)

        val intent = Intent(context, NotificationReceiver::class.java).apply {
            putExtra("NOTIFICATION_MESSAGE", message)
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val notificationTimeMillis = convertTimeToMillis(time)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                notificationTimeMillis,
                pendingIntent
            )
        } else {
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                notificationTimeMillis,
                pendingIntent
            )
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
        ) {
            Text(text = "Full Name: ${viewModel.fullName}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Position: ${viewModel.position}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Notification Time: ${viewModel.notificationTime}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Notification Message: ${viewModel.notificationMessage}", style = MaterialTheme.typography.bodyLarge)

            Image(
                painter = painterResource(id = R.drawable.ic_avatar_placeholder),
                contentDescription = stringResource(id = R.string.avatar_description),
                modifier = Modifier
                    .size(100.dp)
                    .padding(top = 16.dp)
            )
        }

        IconButton(
            onClick = { showDialog = true },
            modifier = Modifier.align(Alignment.TopEnd)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_edit),
                contentDescription = stringResource(id = R.string.edit_profile)
            )
        }

        if (showDialog) {
            EditProfileDialog(
                viewModel = viewModel,
                onDismiss = { showDialog = false },
                scheduleNotification = { time, message ->
                    scheduleNotification(context, time, message)
                }
            )
        }
    }
}

@Composable
fun EditProfileDialog(
    viewModel: ProfileViewModel,
    onDismiss: () -> Unit,
    scheduleNotification: (String, String) -> Unit
) {
    val context = LocalContext.current
    var newName by remember { mutableStateOf(viewModel.fullName) }
    var newPosition by remember { mutableStateOf(viewModel.position) }
    var newNotificationTime by remember { mutableStateOf(viewModel.notificationTime) }
    var newNotificationMessage by remember { mutableStateOf(viewModel.notificationMessage) }
    var timeError by remember { mutableStateOf(false) }

    val timePattern = "^([01]?[0-9]|2[0-3]):([0-5][0-9])$".toRegex()

    // Валидация времени
    val validateTime = { time: String -> timePattern.matches(time) }

    // Вызов TimePickerDialog внутри composable
    val openTimePicker = {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE) // используем composable контекст для этого вызова

        TimePickerDialog(
            context,
            { _: TimePicker, selectedHour: Int, selectedMinute: Int ->
                val formattedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
                newNotificationTime = formattedTime
                timeError = !validateTime(formattedTime)  // Проверка на правильность формата
            },
            hour, minute, true
        ).show()
    }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            TextButton(onClick = {
                if (validateTime(newNotificationTime)) {
                    viewModel.fullName = newName
                    viewModel.position = newPosition
                    viewModel.notificationTime = newNotificationTime
                    viewModel.notificationMessage = newNotificationMessage

                    // Планируем уведомление
                    scheduleNotification(newNotificationTime, newNotificationMessage)

                    onDismiss()
                } else {
                    timeError = true // Показываем ошибку, если время неверное
                }
            }) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Cancel")
            }
        },
        title = { Text("Edit Profile") },
        text = {
            Column {
                OutlinedTextField(
                    value = newName,
                    onValueChange = { newName = it },
                    label = { Text("Full Name") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = newPosition,
                    onValueChange = { newPosition = it },
                    label = { Text("Position") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = newNotificationTime,
                    onValueChange = { newNotificationTime = it },
                    label = { Text("Notification Time") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    isError = timeError
                )
                if (timeError) {
                    Text(
                        text = "Invalid time format. Use HH:mm",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                OutlinedTextField(
                    value = newNotificationMessage,
                    onValueChange = { newNotificationMessage = it },
                    label = { Text("Notification Message") },
                    modifier = Modifier.fillMaxWidth()
                )

                Button(onClick = { openTimePicker() }, modifier = Modifier.padding(top = 8.dp)) {
                    Text("Select Time")
                }
            }
        }
    )
}
