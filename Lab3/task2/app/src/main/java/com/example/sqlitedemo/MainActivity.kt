package com.example.sqlitedemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var nameEditText: EditText
    private lateinit var ageEditText: EditText
    private lateinit var resultTextView: TextView
    private lateinit var addButton: Button
    private lateinit var viewButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Initialize DatabaseHelper
        databaseHelper = DatabaseHelper(this)

        //Initialize UI components
        nameEditText = findViewById(R.id.et_name)
        ageEditText = findViewById(R.id.et_age)
        resultTextView = findViewById(R.id.tv_result)
        addButton = findViewById(R.id.btn_add)
        viewButton = findViewById(R.id.btn_view)

        //set button click listeners
        addButton.setOnClickListener {
            addUser()
        }
        viewButton.setOnClickListener {
            viewUsers()
        }
    }
    private fun addUser(){
        val name = nameEditText.text.toString()
        val age = ageEditText.text.toString().toIntOrNull()

        if(name.isNotEmpty()&& age != null){
            val success = databaseHelper.addUser(name, age)
            if(success){
                Toast.makeText(this, "User added successfully!", Toast.LENGTH_SHORT).show()
                nameEditText.text.clear()
                ageEditText.text.clear()
            }else{
                Toast.makeText(this, "Failed to add user", Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(this, "Please enter a valid name and age", Toast.LENGTH_SHORT).show()
        }
    }

    private fun viewUsers(){
        val users = databaseHelper.getAllUsers()
        resultTextView.text = if (users.isNotEmpty()) {
            users.joinToString ("\n")
        }else{
            "No users found"
        }
    }
}