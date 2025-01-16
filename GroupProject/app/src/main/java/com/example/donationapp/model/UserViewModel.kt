package com.example.donationapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.donationapp.model.User
import com.example.donationapp.repository.UserRepository
import com.example.donationapp.util.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(private val repository: UserRepository) : ViewModel() {

    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> get() = _authState

    fun login(username: String, password: String) {
        viewModelScope.launch {
            val user = repository.login(username, password)
            if (user != null) {
                _authState.postValue(AuthState.Success(user))
            } else {
                _authState.postValue(AuthState.Error("Invalid credentials"))
            }
        }
    }

    fun register(user: User) {
        viewModelScope.launch {
            val success = repository.register(user)
            if (success) {
                _authState.postValue(AuthState.Registered)
            } else {
                _authState.postValue(AuthState.Error("Registration failed"))
            }
        }
    }
}

sealed class AuthState {
    data class Success(val user: User) : AuthState()
    object Registered : AuthState()
    data class Error(val message: String) : AuthState()
}

