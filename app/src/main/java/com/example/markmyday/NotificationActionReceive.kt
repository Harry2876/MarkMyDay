package com.example.markmyday

import android.content.Context
import android.content.Intent
import androidx.work.impl.utils.ForceStopRunnable.BroadcastReceiver
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NotificationActionReceiver : android.content.BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "com.example.markmyday.ACTION_MARK_ATTENDANCE") {
            val database = AttendanceDatabase.getDatabase(context)
            val attendanceDao = database.attendanceDao()
            val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

            GlobalScope.launch {
                val attendance = Attendance(dayMarked = currentDate)
                withContext(Dispatchers.IO) {
                    attendanceDao.insert(attendance)
                }
            }
        }
    }
}