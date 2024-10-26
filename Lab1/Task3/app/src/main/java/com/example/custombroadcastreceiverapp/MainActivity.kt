package com.example.custombroadcastreceiverapp

import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
//import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var myReceiver: MyCustomBroadcastReceiver
    private lateinit var sendBroadcastButton: Button // Declare the button here

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize sendBroadcastButton after setContentView
        sendBroadcastButton = findViewById(R.id.sendBroadcastButton)

        myReceiver = MyCustomBroadcastReceiver()
        sendBroadcastButton.setOnClickListener {
            // Create an intent with the custom action
            val intent = Intent("com.example.CUSTOM_ACTION")
            // Send the broadcast
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        // Register the receiver with the intent filter
        LocalBroadcastManager.getInstance(this)
            .registerReceiver(myReceiver,
                IntentFilter("com.example.CUSTOM_ACTION"))
    }

    override fun onStop() {
        super.onStop()
        // Unregister the receiver to prevent memory leaks
        LocalBroadcastManager.getInstance(this).unregisterReceiver(myReceiver)
    }
}
