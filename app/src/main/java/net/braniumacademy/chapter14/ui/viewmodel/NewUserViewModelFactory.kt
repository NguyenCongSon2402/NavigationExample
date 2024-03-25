package net.braniumacademy.chapter14.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import net.braniumacademy.chapter14.data.repository.UserRepository

class NewUserViewModelFactory(private val repository: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewUserViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NewUserViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}