package com.example.markmyday

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AttendanceViewModel(private val attendanceDao: AttendanceDao) : ViewModel() {
    private val _attendanceList = MutableStateFlow<List<Attendance>>(emptyList())
    val attendanceList: StateFlow<List<Attendance>> = _attendanceList

    init {
        viewModelScope.launch {
            attendanceDao.getAll().collect { list ->
                _attendanceList.value = list
            }
        }
    }

    fun markAttendance(attendance: Attendance) {
        viewModelScope.launch {
            attendanceDao.update(attendance.copy(isMarked = true))
        }
    }

    fun undoAttendance(attendance: Attendance) {
        viewModelScope.launch {
            attendanceDao.update(attendance.copy(isMarked = false))
        }
    }
}