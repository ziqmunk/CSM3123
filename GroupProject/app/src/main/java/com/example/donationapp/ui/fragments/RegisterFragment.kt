package com.example.donationapp.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.donationapp.R
import com.example.donationapp.model.User
import com.example.donationapp.model.UserViewModelFactory
import com.example.donationapp.repository.UserRepository
import com.example.donationapp.util.AppDatabase
import com.example.donationapp.viewmodel.AuthState
import com.example.donationapp.viewmodel.UserViewModel

class RegisterFragment : Fragment(R.layout.fragment_register) {

    private lateinit var viewModel: UserViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize ViewModel
        val repository = UserRepository(AppDatabase.getInstance(requireContext()).userDao())
        viewModel = ViewModelProvider(this, UserViewModelFactory(repository))[UserViewModel::class.java]

        val registerButton: Button = view.findViewById(R.id.registerButton)
        val nameEditText: EditText = view.findViewById(R.id.name)
        val usernameEditText: EditText = view.findViewById(R.id.username)
        val emailEditText: EditText = view.findViewById(R.id.email)
        val phoneNumberEditText: EditText = view.findViewById(R.id.phonenumber)
        val passwordEditText: EditText = view.findViewById(R.id.password)

        viewModel.authState.observe(viewLifecycleOwner) { authState ->
            when (authState) {
                is AuthState.Registered -> {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, LoginFragment())
                        .commit()
                }
                is AuthState.Error -> {
                    Toast.makeText(requireContext(), authState.message, Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }

        registerButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val username = usernameEditText.text.toString()
            val email = emailEditText.text.toString()
            val phoneNumber = phoneNumberEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (name.isBlank() || username.isBlank() || email.isBlank() || phoneNumber.isBlank() || password.isBlank()) {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                val user = User(name = name, userName = username, email = email, phoneNumber = phoneNumber, password = password)
                viewModel.register(user)
            }
        }
    }
}
