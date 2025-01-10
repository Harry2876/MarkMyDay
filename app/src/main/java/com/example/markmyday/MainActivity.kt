package com.example.markmyday

import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var attendanceDao: AttendanceDao
    private lateinit var progressBar: ProgressBar
    private lateinit var tvAttendanceProgress: TextView
    private lateinit var tvAttendanceDays: TextView
    private val totalDays = 87 // Example: Total working days in a semester

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize UI components
        progressBar = findViewById(R.id.progressBar)
        tvAttendanceProgress = findViewById(R.id.tvAttendanceProgress)
        tvAttendanceDays = findViewById(R.id.tvAttendanceDays)
        val btnMarkAttendance: Button = findViewById(R.id.btnMarkAttendance)
        val btnRemoveAttendance: Button = findViewById(R.id.btnRemoveAttendance)

        // Initialize Database
        val database = AttendanceDatabase.getDatabase(this)
        attendanceDao = database.attendanceDao()

        // Schedule daily reminders
        scheduleDailyNotifications()

        // Set click listeners
        btnMarkAttendance.setOnClickListener { markAttendance() }
        btnRemoveAttendance.setOnClickListener { removeAttendance() }

        // Update UI
        updateAttendanceUI()
    }

    private fun markAttendance() {
        val currentDate = getCurrentDate()
        lifecycleScope.launch {
            val attendance = Attendance(dayMarked = currentDate)
            withContext(Dispatchers.IO) {
                attendanceDao.insert(attendance)
            }
            updateAttendanceUI()
        }
    }

    private fun removeAttendance() {
        val currentDate = getCurrentDate()
        lifecycleScope.launch {
            val attendanceList = withContext(Dispatchers.IO) { attendanceDao.getAllMarkedDays() }
            val attendance = attendanceList.find { it.dayMarked == currentDate }
            attendance?.let {
                withContext(Dispatchers.IO) { attendanceDao.delete(it) }
            }
            updateAttendanceUI()
        }
    }
    private fun updateAttendanceUI() {
        lifecycleScope.launch {
            val markedDaysCount = withContext(Dispatchers.IO) {
                attendanceDao.getMarkedDaysCount()
            }
            progressBar.progress = markedDaysCount
            tvAttendanceDays.text = "Marked Days: $markedDaysCount / $totalDays"
            tvAttendanceProgress.text = "${(markedDaysCount.toFloat() / totalDays * 100).toInt()}% com.example.markmyday.Attendance"
        }
    }

    private fun scheduleDailyNotifications() {
        val workRequest = PeriodicWorkRequestBuilder<NotificationWorker>(1, TimeUnit.DAYS).build()
        WorkManager.getInstance(this).enqueue(workRequest)
    }

    private fun getCurrentDate(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return sdf.format(Date())
    }
}