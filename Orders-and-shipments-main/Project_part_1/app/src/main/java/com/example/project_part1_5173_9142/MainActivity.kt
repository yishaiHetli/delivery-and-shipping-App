package com.example.project_part1_5173_9142

import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.util.*
import kotlin.concurrent.timerTask

class MainActivity : AppCompatActivity() {
    private val broadcastReceiver = ReceivedBroadcast()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val filter = IntentFilter("project_part1_5173_9142.ACTION")
        registerReceiver(broadcastReceiver,filter)
        startTimerActivity()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(broadcastReceiver)
    }

    private fun startTimerActivity() {
        if (!isDestroyed) {
            val intent = Intent(this, Second::class.java)
            val timeTask = timerTask {
                if (!isDestroyed) {
                    startActivity(intent)
                    finish()
                }
            }
            val timer = Timer()
            timer.schedule(timeTask, 3000)
        }
    }
}