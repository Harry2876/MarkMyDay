package com.example.markmyday

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Attendance(
    @PrimaryKey val id: Int,
    val name: String,
    val isMarked: Boolean
)