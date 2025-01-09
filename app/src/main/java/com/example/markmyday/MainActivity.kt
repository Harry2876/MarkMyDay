package com.example.markmyday

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.room.Room


class MainActivity : ComponentActivity() {
    private val viewModel: AttendanceViewModel by viewModels {
        AttendanceViewModelFactory(
            Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, "attendance-database"
            ).build().attendanceDao()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MarkMyDayTheme {
                Surface(color = MaterialTheme.colors.background) {
                    AttendanceTrackerScreen(viewModel)
                }
            }
        }
    }
}