package com.pupilmesh.assignment.presentation.signin

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pupilmesh.assignment.data.local.preferences.UserPreferencesRepository
import com.pupilmesh.assignment.domain.model.User
import com.pupilmesh.assignment.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel(){

    val isLoggedIn = mutableStateOf(false)

    init {
        viewModelScope.launch {
            userPreferencesRepository.isLoggedIn.collectLatest {
                isLoggedIn.value = it
            }
        }
    }

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            if (checkIfUserExist(email)) {
                checkLoginCredentials(email, password)
            } else {
                createNewUser(email, password)
            }
        }
    }

    private suspend fun createNewUser(email: String, password: String) {
        userRepository.insertUser(User(email, password))
        checkLoginCredentials(email, password)
    }

    private suspend fun checkLoginCredentials(email: String, password: String){
        val result = userRepository.getUserByEmailAndPassword(email, password)
        if (result != null){
            userPreferencesRepository.setLoggedIn(email)
            isLoggedIn.value = true
        } else{
            Log.d("Logged IN", "checkLoginCredentials: Login Failed")
            isLoggedIn.value = false
        }
    }

    private suspend fun checkIfUserExist(email: String): Boolean {
        val result = userRepository.getUserByEmail(email)
        return result != null
    }
}