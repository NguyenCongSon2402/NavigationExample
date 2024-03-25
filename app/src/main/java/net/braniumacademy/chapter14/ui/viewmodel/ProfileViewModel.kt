package net.braniumacademy.chapter14.ui.viewmodel

import androidx.core.util.PatternsCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import net.braniumacademy.chapter14.R
import net.braniumacademy.chapter14.data.model.Profile
import net.braniumacademy.chapter14.data.repository.ProfileRepository
import net.braniumacademy.chapter14.ui.LoginFormState

class ProfileViewModel(
    private val repository: ProfileRepository
) : ViewModel() {
    private val _user = MutableLiveData<Profile?>()
    val user: LiveData<Profile?> = _user

    private val _loginFormState = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginFormState

    init {
        _user.value = null
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            repository.login(email, password).collect {
                _user.postValue(it)
            }
        }
    }

    fun findProfileById(id: Int) {
        viewModelScope.launch {
            val profile = repository.findUserById(id)
            profile.collect {
                _user.postValue(it)
            }
        }
    }

    fun loginDataChanged(email: String, password: String) {
        if (email.isBlank()) {
            _loginFormState.value = LoginFormState(emailError = R.string.error_email)
        } else if (!isUserNameValid(email)) {
            _loginFormState.value = LoginFormState(emailError = R.string.error_email)
        } else if (!isPasswordValid(password)) {
            _loginFormState.value = LoginFormState(passwordError = R.string.error_password)
        } else {
            _loginFormState.value = LoginFormState(isCorrect = true)
        }
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.isNotBlank()
    }

    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            PatternsCompat.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            false
        }
    }
}