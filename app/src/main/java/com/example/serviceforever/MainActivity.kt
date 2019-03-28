package com.example.serviceforever

import android.content.ComponentName
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.serviceforever.service.ForeverRunningService
import com.example.serviceforever.service.Restarted

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       openSetting()

        startService( Intent(this, ForeverRunningService::class.java))
    }

    private fun openSetting(){
        if (Build.BRAND.equals("xiaomi", ignoreCase = true)) {
            val intent = Intent()
            intent.component =
                ComponentName("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity")
            startActivityForResult(intent,2)
        }
    }

    override fun onDestroy() {
        Log.i(ForeverRunningService.TAG, "onDestroy")
        val broadcastIntent = Intent()
        broadcastIntent.action = "restartservice"
        broadcastIntent.setClass(this, Restarted::class.java)
        this.sendBroadcast(broadcastIntent)
        super.onDestroy()
    }
}
