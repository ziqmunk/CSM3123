package com.example.roomdatabasedemo

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private val userViewModel: UserViewModel by viewModels()
    private lateinit var userAdapter: UserAdapter
    private lateinit var taskAdapter: TaskAdapter
    private var selectedUserId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editTextName = findViewById<EditText>(R.id.editTextName)
        val editTextAge = findViewById<EditText>(R.id.editTextAge)
        val buttonAdd = findViewById<Button>(R.id.buttonAdd)
        val buttonUpdate = findViewById<Button>(R.id.buttonUpdate)
        val buttonDelete = findViewById<Button>(R.id.buttonDelete)
        val searchBar = findViewById<EditText>(R.id.search_bar)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val buttonAddTask = findViewById<Button>(R.id.buttonAddTask)
        val taskRecyclerView = findViewById<RecyclerView>(R.id.taskRecyclerView)
        val editTextTask = findViewById<EditText>(R.id.editTextTask)

        // Initialize the adapters
        userAdapter = UserAdapter { selecteduser ->
            selectedUserId = selecteduser.id
            loadTasksForUser(selecteduser.id)
            Toast.makeText(this, "Selected User: ${selecteduser.name}", Toast.LENGTH_SHORT).show()
        }
        taskAdapter = TaskAdapter()

        recyclerView.adapter = userAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        taskRecyclerView.adapter = taskAdapter
        taskRecyclerView.layoutManager = LinearLayoutManager(this)

        // Observe the LiveData from the ViewModel
        userViewModel.allUsers.observe(this) { users ->
            userAdapter.submitFullList(users)
        }

        // Search bar functionality
        searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                userAdapter.filter(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        // Add User
        buttonAdd.setOnClickListener {
            val name = editTextName.text.toString()
            val age = editTextAge.text.toString().toIntOrNull()

            if (name.isNotBlank() && age != null) {
                val user = User(name = name, age = age)
                userViewModel.insertUser(user)
                Toast.makeText(this, "User Added", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please provide valid input", Toast.LENGTH_SHORT).show()
            }
        }

        // Update User
        buttonUpdate.setOnClickListener {
            val name = editTextName.text.toString()
            val age = editTextAge.text.toString().toIntOrNull()

            if (name.isNotBlank() && age != null) {
                val user = User(name = name, age = age) // Update logic might require user ID
                userViewModel.updateUser(user)
                Toast.makeText(this, "User Updated", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please provide valid input", Toast.LENGTH_SHORT).show()
            }
        }

        // Delete User
        buttonDelete.setOnClickListener {
            val name = editTextName.text.toString()
            val age = editTextAge.text.toString().toIntOrNull()

            if (name.isNotBlank() && age != null) {
                val user = User(name = name, age = age) // Delete logic might require user ID
                userViewModel.deleteUser(user)
                Toast.makeText(this, "User Deleted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please provide valid input", Toast.LENGTH_SHORT).show()
            }
        }

        // Add Task
        buttonAddTask.setOnClickListener {
            val taskName = editTextTask.text.toString()

            if (selectedUserId != null && taskName.isNotBlank()) {
                val task = Task(userId = selectedUserId!!, taskName = taskName)
                userViewModel.insertTask(task)
                Toast.makeText(this, "Task Added", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Select a user and provide a task name", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadTasksForUser(userId: Int) {
        userViewModel.getTasksForUser(userId) { tasks ->
            runOnUiThread {
                taskAdapter.submitList(tasks)
            }
        }
    }
}
