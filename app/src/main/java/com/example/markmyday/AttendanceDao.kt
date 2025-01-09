package com.example.markmyday

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface AttendanceDao {
    @Query("SELECT * FROM attendance")
    fun getAll(): Flow<List<Attendance>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(attendance: Attendance)

    @Update
    suspend fun update(attendance: Attendance)
}