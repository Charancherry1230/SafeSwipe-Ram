package com.example.safeswipe.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.safeswipe.repository.AuthRepository
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    private val authRepository = AuthRepository()

    private val _user = MutableStateFlow<FirebaseUser?>(null)
    val user: StateFlow<FirebaseUser?> get() = _user

    fun signUp(email: String, password: String) {
        viewModelScope.launch {
            val newUser = authRepository.signUp(email, password)
            _user.value = newUser
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            val loggedInUser = authRepository.login(email, password)
            _user.value = loggedInUser
        }
    }

    fun logout() {
        authRepository.logout()
        _user.value = null
    }
}
