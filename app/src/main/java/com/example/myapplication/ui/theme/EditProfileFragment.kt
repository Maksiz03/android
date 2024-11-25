package com.example.myapplication.ui.theme

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.myapplication.NotificationReceiver
import com.example.myapplication.R
import com.example.myapplication.ui.theme.EditProfileFragment.TimeUtils.convertTimeToMillis
import com.example.myapplication.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class EditProfileFragment : Fragment() {

    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_profile, container, false)

        val editTextFullName: EditText = view.findViewById(R.id.editTextFullName)
        val editTextPosition: EditText = view.findViewById(R.id.editTextPosition)
        val editTextResumeUrl: EditText = view.findViewById(R.id.editTextResumeUrl)
        val editTextNotificationTime: EditText = view.findViewById(R.id.editTextNotificationTime)
        val editTextNotificationMessage: EditText = view.findViewById(R.id.editTextNotificationMessage)
        val buttonSelectImage: Button = view.findViewById(R.id.buttonSelectImage)
        val buttonSave: Button = view.findViewById(R.id.buttonSave)

        // Set initial values from the ViewModel
        editTextFullName.setText(viewModel.fullName)
        editTextPosition.setText(viewModel.position)
        editTextResumeUrl.setText(viewModel.resumeUrl)
        editTextNotificationTime.setText(viewModel.notificationTime)
        editTextNotificationMessage.setText(viewModel.notificationMessage)

        buttonSave.setOnClickListener {
            // Update ViewModel with new values
            viewModel.fullName = editTextFullName.text.toString()
            viewModel.position = editTextPosition.text.toString()
            viewModel.resumeUrl = editTextResumeUrl.text.toString()
            viewModel.notificationTime = editTextNotificationTime.text.toString()
            viewModel.notificationMessage = editTextNotificationMessage.text.toString()

            // Schedule notification
            scheduleNotification(viewModel.notificationTime, viewModel.notificationMessage)

            // Optionally, navigate back or show a confirmation message
            // For example, using Navigation component:
            // findNavController().popBackStack()
        }

        buttonSelectImage.setOnClickListener {
            // Implement image selection logic here
        }

        return view
    }

    private fun scheduleNotification(time: String, message: String) {
        val intent = Intent(requireContext(), NotificationReceiver::class.java).apply {
            putExtra("NOTIFICATION_MESSAGE", message)
        }
        val pendingIntent = PendingIntent.getBroadcast(requireContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager

        // Convert the time from string to milliseconds
        val notificationTimeMillis = convertTimeToMillis(time)

        // Schedule the alarm
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, notificationTimeMillis, pendingIntent)
    }

    object TimeUtils {
        fun convertTimeToMillis(time: String): Long {
            return try {
                val format = SimpleDateFormat("hh:mm a", Locale.getDefault())
                val date = format.parse(time) ?: return System.currentTimeMillis()

                val calendar = Calendar.getInstance().apply {
                    set(Calendar.HOUR_OF_DAY, date.hours)
                    set(Calendar.MINUTE, date.minutes)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)

                    // Если время уже прошло, устанавливаем на следующий день
                    if (timeInMillis < System.currentTimeMillis()) {
                        add(Calendar.DAY_OF_MONTH, 1)
                    }
                }
                calendar.timeInMillis
            } catch (e: Exception) {
                System.currentTimeMillis() // Возвращаем текущее время в случае ошибки
            }
        }
    }



}
