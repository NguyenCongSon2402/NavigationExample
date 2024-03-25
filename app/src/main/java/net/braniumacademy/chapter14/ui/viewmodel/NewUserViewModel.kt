package net.braniumacademy.chapter14.ui.viewmodel

import androidx.core.util.PatternsCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import net.braniumacademy.chapter14.R
import net.braniumacademy.chapter14.data.model.Profile
import net.braniumacademy.chapter14.data.model.User
import net.braniumacademy.chapter14.data.repository.UserRepository
import net.braniumacademy.chapter14.ui.RegisterFormState

class NewUserViewModel(
    private val repository: UserRepository
) : ViewModel() {
    private val _savedState = MutableLiveData<Boolean>()
    val savedState: LiveData<Boolean> = _savedState

    private val _registerFormState = MutableLiveData<RegisterFormState>()
    val registerFormState: LiveData<RegisterFormState> = _registerFormState

    fun addNewUser(fullName: String, email: String, avatar: Int = 0) {
        viewModelScope.launch {
            val user = User(0, email, fullName, avatar)
            val rowId = repository.save(user)
            if (rowId > 0) {
                _savedState.postValue(true)
            } else {
                _savedState.postValue(false)
            }
        }
    }

    fun registerInfoChanged(email: String, fullName: String, avatar: Int = 0) {
        if (fullName.isBlank()) {
            _registerFormState.value = RegisterFormState(fullNameError = R.string.error_full_name)
        } else if (email.isBlank() || !isEmailValid(email)) {
            _registerFormState.value = RegisterFormState(emailError = R.string.error_email)
        } else if (avatar == 0) {
            _registerFormState.value = RegisterFormState(avatarError = R.string.error_avatar)
        } else {
            _registerFormState.value = RegisterFormState(isCorrect = true)
        }
    }

    private fun isEmailValid(email: String): Boolean {
        return if (email.contains('@')) {
            PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()
        } else {
            false
        }
    }
}