package ru.mirea.istornikov.notificationapp

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.NotificationCompat

class MainActivity : AppCompatActivity() {

    val CHANNEL_ID = "com.mirea.asd.notification.ANDROID"
    var notificationID = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun notification(view : View){
        val notificationManger: NotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        var resultIntent: Intent = Intent(this,MainActivity::class.java)
        val resultPendingIntent = PendingIntent.getActivity(this,0,resultIntent,PendingIntent.FLAG_UPDATE_CURRENT)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            var notificationChannel: NotificationChannel = NotificationChannel(CHANNEL_ID,"My notifications",NotificationManager.IMPORTANCE_DEFAULT)
            notificationChannel.description = "Channel description"
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = (Color.RED)
            notificationChannel.enableVibration(true)
            notificationChannel.vibrationPattern = (longArrayOf(0,1000,500,1000))
            notificationManger.createNotificationChannel(notificationChannel)
        }

        var builder: NotificationCompat.Builder = NotificationCompat.Builder(this,CHANNEL_ID)
        builder.setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Title")
            .setContentText("Notification text for Mirea")
            .setWhen(System.currentTimeMillis())
            .setProgress(100,50,false)
            .setContentIntent(resultPendingIntent)
        var notification: Notification = builder.build()
        notificationManger.notify(notificationID++,notification)
    }
}