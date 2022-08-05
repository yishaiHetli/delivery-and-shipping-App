package com.example.project_part1_5173_9142

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class ReceivedBroadcast: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
            if("project_part1_5173_9142.ACTION" == intent?.action){
                val receiveText = intent.getStringExtra("project_part1_5173_9142.EXTRA_TEXT")
                Toast.makeText(context,receiveText,Toast.LENGTH_LONG).show()
            }
    }
}