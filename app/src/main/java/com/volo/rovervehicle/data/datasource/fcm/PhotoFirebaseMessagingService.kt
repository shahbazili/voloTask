package com.volo.rovervehicle.data.datasource.fcm

import android.Manifest
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.volo.rovervehicle.ui.MainActivity
import com.volo.voloandroidtask.R

private const val NOTIFICATION_ID = 0
private const val CHANNEL_ID = "New_Channel"

class PhotoFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        /*save token on server*/
        super.onNewToken(token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // Handle the received message
        if (remoteMessage.data.isNotEmpty()) {
            // Extract the payload data
            val title = remoteMessage.data["title"]
            val message = remoteMessage.data["message"]
            val photo = remoteMessage.data["photo"]
            val tabId = remoteMessage.data["type"]

            // Process the notification and open the specific tab with the payload data
            processNotification(title, message, photo,tabId)
        }
    }

    private fun processNotification(title: String?, message: String?, photo: String?,type: String?) {
        // Handle the notification payload, open the specific tab, and display the data
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("type", type) // Set the desired tab index as an extra
        intent.putExtra("photo", photo)


        // Create a PendingIntent with the intent
        val pendingIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        // Build the notification
        val builder: NotificationCompat.Builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.mipmap.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        // Show the notification
        val notificationManager = NotificationManagerCompat.from(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        notificationManager.notify(NOTIFICATION_ID, builder.build())
    }
}