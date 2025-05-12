package com.app.grocer.ui.stateholder

import com.app.grocer.data.model.LoginResponse
import com.app.grocer.data.model.RegisterResponse
import kotlinx.coroutines.flow.Flow

data class UserStateHolder(
  val isLoading: Boolean = false,
  val data: Flow<LoginResponse>? = null,
  val error: String? = "",
  var isLoggedIn: Boolean = false,
  val isRegistered: Boolean = false,
)
data class RegisterUserStateHolder(
    val isLoading: Boolean = false,
    val data: Flow<RegisterResponse>? = null,
    val error: String? = "",
    var isLoggedIn: Boolean = false,
    val isRegistered: Boolean = false,
)
