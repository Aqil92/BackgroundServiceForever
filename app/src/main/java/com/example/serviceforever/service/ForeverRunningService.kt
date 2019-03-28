package com.example.serviceforever.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import java.util.*


class ForeverRunningService : Service() {

    companion object {
        val TAG="ServiceForever"
    }

    override   fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            startMyOwnForeground()
        }
          else
              startForeground(1, Notification())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun startMyOwnForeground() {
        val NOTIFICATION_CHANNEL_ID = "example.permanence"
        val channelName = "Background Service"
        val chan = NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE)
        chan.lightColor = Color.BLUE
        chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE

        val manager = (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?)!!
        manager.createNotificationChannel(chan)

        val notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
        val notification = notificationBuilder.setOngoing(true)
            .setContentTitle("App is running in background")
            .setPriority(NotificationManager.IMPORTANCE_MIN)
            .setCategory(Notification.CATEGORY_SERVICE)
            .build()
        startForeground(2, notification)
    }

    override fun onTaskRemoved(rootIntent: Intent) {
        Log.v(TAG,"onTaskRemoved ")

        sendBroadCast(rootIntent)

    }

    fun sendBroadCast(rootIntent: Intent){
        val broadcastIntent = Intent()
        broadcastIntent.action = "restartservice"
        broadcastIntent.setClass(this, Restarted::class.java)
        this.sendBroadcast(broadcastIntent)
        super.onTaskRemoved(rootIntent)
    }


    override   fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        startTimer()
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        Log.v(TAG,"onDestroy Service")
        val broadcastIntent = Intent()
        broadcastIntent.action = "restartservice"
        broadcastIntent.setClass(this, Restarted::class.java)
        this.sendBroadcast(broadcastIntent)
        sendBroadCast(broadcastIntent)
    }

    fun startTimer() {
        var timerTask: TimerTask? = null
      var  timer = Timer()
        timerTask = object : TimerTask() {
            override fun run() {
              Log.v(TAG,"startTimer")
            }
        }
        timer!!.schedule(timerTask, 101, 2000) //
    }


}