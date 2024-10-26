package com.example.myfirstapp

//import androidx.activity.enableEdgeToEdge

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        var button: Button
        var textView: TextView
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        textView = findViewById(R.id.textView)
        button = findViewById(R.id.button)
        button.setOnClickListener{
            textView.text="Button Clicked!"
            textView.setTextColor(Color.RED)
        }

        // Get reference to the "Go to Second Activity" button
        val buttonNavigate: Button = findViewById(R.id.buttonNavigate)
        val intent = Intent(this, SecondActivity::class.java)

        // Set a click listener for the button to navigate to SecondActivity
        buttonNavigate.setOnClickListener {
            startActivity(intent)
        }

        intent.putExtra("EXTRA_MESSAGE","Hello from MainActivity!");

        button.setOnClickListener {
            textView.text = "Button Clicked!"
            textView.setTextColor(Color.RED)

            // Show a Toast when the button is clicked
            Toast.makeText(this, "Button was clicked!", Toast.LENGTH_SHORT).show()
        }
    }

}