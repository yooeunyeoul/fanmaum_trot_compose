package com.trotfan.trot

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_MUTABLE
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.trotfan.trot.ui.MainActivity

class MyFirebaseMessagingService : FirebaseMessagingService() {
    private val TAG = "FirebaseService"
    override fun onNewToken(token: String) {
        Log.d(TAG, "token:${token}")
        super.onNewToken(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.d(TAG, message.toString())
        try {
            val notification = message.notification
            val title = notification?.title
            val body = notification?.body
            Log.d(TAG, "message title :${title}")
            Log.d(TAG, "message body :${body}")
        } catch (e: java.lang.Exception) {
            Log.d(TAG, "Error Parsing")
        }


//        message.run {
//            val from = from ?: ""
//            val title = notification?.title ?: ""
//            val body = notification?.body ?: ""
//            val data = data
//            Log.d(TAG, "message received :${message}")
//            Log.d(TAG, "from:${from}, title:${title}, body:${body}, data:${data}")
//            sendNotification(title, body)
//        }


    }

    private fun sendNotification(title: String, body: String) {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP and Intent.FLAG_ACTIVITY_NEW_TASK
        }
        val pendingIntent = PendingIntent.getActivities(this, 0, arrayOf(intent), FLAG_MUTABLE)

        val channelId = "fanmaum_channel"
        val channelName = "fanmaum_name"
        val defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setAutoCancel(true)
            .setSound(defaultSound)
            .setContentText(body)
            .setContentTitle(title)
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.icon_bell)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel =
            NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
        notificationManager.createNotificationChannel(channel)
        notificationManager.notify(0, notificationBuilder.build())

    }

    fun getFirebaseToken(): String? {
        return FirebaseMessaging.getInstance().token.result
    }
}