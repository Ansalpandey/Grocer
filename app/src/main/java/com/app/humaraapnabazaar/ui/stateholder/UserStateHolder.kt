package com.app.humaraapnabazaar.ui.stateholder

import com.app.humaraapnabazaar.data.model.LoginResponse
import com.app.humaraapnabazaar.data.model.RegisterResponse
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
