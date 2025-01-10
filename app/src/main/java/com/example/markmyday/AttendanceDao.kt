package com.example.markmyday

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AttendanceDao {
    @Insert
    suspend fun insert(attendance: Attendance)

    @Delete
    suspend fun delete(attendance: Attendance)

    @Query("SELECT * FROM attendance_record")
    suspend fun getAllMarkedDays(): List<Attendance>

    @Query("SELECT COUNT(*) FROM attendance_record")
    suspend fun getMarkedDaysCount(): Int
}