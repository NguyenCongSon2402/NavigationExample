package net.braniumacademy.chapter14.ui

data class LoginFormState(
    val emailError: Int? = null,
    val passwordError: Int? = null,
    val isCorrect: Boolean = false
)
