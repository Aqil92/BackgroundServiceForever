package com.example.serviceforever.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class Restarted : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Log.v(ForeverRunningService.TAG,"service trying to stop")
        val startService = context.startService(Intent(context, ForeverRunningService::class.java))
    }
}