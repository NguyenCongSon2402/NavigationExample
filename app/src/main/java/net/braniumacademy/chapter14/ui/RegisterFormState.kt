package net.braniumacademy.chapter14.ui

data class RegisterFormState(
    val fullNameError: Int? = null,
    val emailError: Int? = null,
    val avatarError: Int? = null,
    val isCorrect: Boolean = false
)
