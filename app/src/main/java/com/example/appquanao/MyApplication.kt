package com.example.appquanao

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build

private const val CHANNEL_ID = "MINHSHOP"

class MyApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        createNotioficationChanel()
    }

    private fun createNotioficationChanel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel.
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val mChannel = NotificationChannel(CHANNEL_ID, name, importance)
            mChannel.description = descriptionText
            // Register the channel with the system. You can't change the importance
            // or other notification behaviors after this.
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }
    }
}