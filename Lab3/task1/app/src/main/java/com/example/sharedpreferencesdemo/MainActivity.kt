package com.example.sharedpreferencesdemo

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var greetingTextView: TextView
    private lateinit var nameEditText: EditText
    private lateinit var ageEditText: EditText
    private lateinit var saveButton: Button
    private lateinit var loadButton: Button
    private lateinit var clearButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Initialize UI components
        greetingTextView = findViewById(R.id.tv_greeting)
        nameEditText = findViewById(R.id.et_name)
        ageEditText = findViewById(R.id.et_age)
        saveButton = findViewById(R.id.btn_save)
        loadButton = findViewById(R.id.btn_load)
        clearButton = findViewById(R.id.btn_clear)

        //Set up button click listeners
        saveButton.setOnClickListener{
            saveName()
        }

        loadButton.setOnClickListener {
            loadName()
        }

        clearButton.setOnClickListener {
            clearName()
        }
    }

    private fun saveName(){
        //Validate the input
        if(nameEditText.text.toString().isEmpty()){
            nameEditText.error = "Please enter your name"
            return
        }
        if(ageEditText.text.toString().isEmpty()){
            ageEditText.error = "Please enter your age"
            return
        }
        //Get the SharedPreferences object
        val sharedPreferences = getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)

        //Open the editor to write to SharedPreferences
        val editor = sharedPreferences.edit()

        //Get the name from EditText and save it with a key
        val name = nameEditText.text.toString()
        editor.putString("userName", name)

        //Apply changes to save the data
        editor.apply()

        //Show a confirmation message
        greetingTextView.text = "Name Saved!"

    }

    private fun loadName(){
        //Get the SharedPreferences object
        val sharedPreferences = getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)

        //Retrieve the saved name using the key
        val savedName = sharedPreferences.getString("userName", "No name saved")

        //Display the saved name in the TextView
        greetingTextView.text = "Welcome, $savedName!"
    }

    private fun clearName() {
        //Get the SharedPreferences object
        val sharedPreferences = getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)

        //Open the editor to write to SharedPreferences
        val editor = sharedPreferences.edit()

        //Clear the saved name using the key
        editor.remove("userName")
        editor.remove("userAge")

        //Apply changes to save the data
        editor.apply()

        //Clear the EditText and TextView
        nameEditText.text.clear()
        ageEditText.text.clear()

        //Output the response message
        greetingTextView.text = "Data cleared!"
    }
}