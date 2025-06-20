package com.app.grocer.data.model

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String,
    val phone: String,
    val role: String = "user"
)
