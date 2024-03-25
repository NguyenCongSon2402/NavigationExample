package net.braniumacademy.chapter14.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import net.braniumacademy.chapter14.data.model.Profile
import net.braniumacademy.chapter14.data.model.User
import net.braniumacademy.chapter14.data.repository.UserRepository

class UserViewModel(
    private val repository: UserRepository
) : ViewModel() {
    private val _users = MutableLiveData<List<User>>()
    var users: LiveData<List<User>> = _users

    init {
        loadUsers()
    }

    fun loadUsers() {
        viewModelScope.launch {
            repository.users.collect {
                _users.postValue(it)
            }
        }
    }
}