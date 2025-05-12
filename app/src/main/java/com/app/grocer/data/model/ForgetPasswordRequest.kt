package com.app.grocer.data.model

data class ForgetPasswordRequest(
    val email: String,
    val password: String,
    val role: String = "user"
)
