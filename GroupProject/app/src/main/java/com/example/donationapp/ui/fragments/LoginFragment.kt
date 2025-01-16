package com.example.donationapp.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.donationapp.R
import com.example.donationapp.model.UserViewModelFactory
import com.example.donationapp.repository.UserRepository
import com.example.donationapp.ui.activities.DashboardActivity
import com.example.donationapp.util.AppDatabase
import com.example.donationapp.viewmodel.AuthState
import com.example.donationapp.viewmodel.UserViewModel

class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var viewModel: UserViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize ViewModel
        val repository = UserRepository(AppDatabase.getInstance(requireContext()).userDao())
        viewModel = ViewModelProvider(this, UserViewModelFactory(repository))[UserViewModel::class.java]

        val loginButton: Button = view.findViewById(R.id.loginButton)
        val usernameEditText: EditText = view.findViewById(R.id.username)
        val passwordEditText: EditText = view.findViewById(R.id.password)

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (username.isBlank() || password.isBlank()) {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.login(username, password)
            }
        }

        // Observe authentication state
        viewModel.authState.observe(viewLifecycleOwner) { authState ->
            when (authState) {
                is AuthState.Success -> {
                    val user = authState.user  // Assuming the user object contains the userId
                    val userId = user.id  // Retrieve the userId
                    val username = usernameEditText.text.toString()  // Get the username again if needed

                    // Navigate to DashboardActivity, passing the userId and username
                    val intent = Intent(requireContext(), DashboardActivity::class.java).apply {
                        putExtra("username", username)  // Pass the logged-in username
                        putExtra("USER_ID", userId)  // Pass the userId
                    }
                    startActivity(intent)
                    requireActivity().finish()
                }
                is AuthState.Error -> {
                    Toast.makeText(requireContext(), authState.message, Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
    }
}
