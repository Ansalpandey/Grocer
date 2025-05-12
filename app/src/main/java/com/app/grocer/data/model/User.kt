package com.app.grocer.data.model

data class User(
    val _id: String,
    val name: String,
    val email: String,
    val role: String = "user",
)
