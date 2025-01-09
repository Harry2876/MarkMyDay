import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

data class Attendance(val name: String, var isMarked: Boolean = false)

class AttendanceViewModel : ViewModel() {
    private val _attendanceList = MutableStateFlow(
        listOf(
            Attendance(name = "Alice"),
            Attendance(name = "Bob"),
            Attendance(name = "Charlie")
        )
    )
    val attendanceList: StateFlow<List<Attendance>> = _attendanceList

    fun markAttendance(attendance: Attendance) {
        _attendanceList.update { currentList ->
            currentList.map {
                if (it == attendance) it.copy(isMarked = true) else it
            }
        }
    }

    fun undoAttendance(attendance: Attendance) {
        _attendanceList.update { currentList ->
            currentList.map {
                if (it == attendance) it.copy(isMarked = false) else it
            }
        }
    }
}