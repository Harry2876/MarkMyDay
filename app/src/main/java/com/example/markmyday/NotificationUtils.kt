import android.Manifest
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.markmyday.MainActivity
import com.example.markmyday.R

@RequiresApi(Build.VERSION_CODES.O)
fun showAttendanceNotification(context: Context, attendanceId: Int) {
    val channelId = "attendance_channel"
    val channelName = "Attendance Notifications"
    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
    notificationManager.createNotificationChannel(channel)

    val intent = Intent(context, MainActivity::class.java).apply {
        putExtra("attendanceId", attendanceId)
    }
    val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

    val notification = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(R.drawable.ic_notification)
        .setContentTitle("Mark Attendance")
        .setContentText("Tap to mark attendance")
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .addAction(R.drawable.ic_mark, "Mark", pendingIntent)
        .build()


    if (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.POST_NOTIFICATIONS
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        // Request the missing permissions
        ActivityCompat.requestPermissions(
            (context as Activity),
            arrayOf(Manifest.permission.POST_NOTIFICATIONS),
            1
        )
        return
    }
    NotificationManagerCompat.from(context).notify(attendanceId, notification)
}