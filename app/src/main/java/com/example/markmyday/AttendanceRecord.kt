package com.example.markmyday

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "attendance_record")
data class Attendance(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val dayMarked: String // Format: YYYY-MM-DD
)