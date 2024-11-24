package com.app.humaraapnabazaar.data.repository

import com.app.humaraapnabazaar.common.Resource
import com.app.humaraapnabazaar.data.model.ForgetPasswordRequest
import com.app.humaraapnabazaar.data.model.ForgetPasswordResponse
import com.app.humaraapnabazaar.data.model.LoginRequest
import com.app.humaraapnabazaar.data.model.LoginResponse
import com.app.humaraapnabazaar.data.model.ProfileResponse
import com.app.humaraapnabazaar.data.model.RefreshTokenResponse
import com.app.humaraapnabazaar.data.model.RegisterRequest
import com.app.humaraapnabazaar.data.model.RegisterResponse
import com.app.humaraapnabazaar.data.model.UpdateProfileRequest
import com.app.humaraapnabazaar.data.model.UpdateProfileResponse
import com.app.humaraapnabazaar.ui.stateholder.UserStateHolder
import kotlinx.coroutines.flow.Flow

interface UserRepository {
  val userStateHolder: Flow<UserStateHolder>

  suspend fun registerUser(user: RegisterRequest): Flow<Resource<RegisterResponse>>

  suspend fun refreshToken(): Resource<RefreshTokenResponse>

  suspend fun loginUser(user: LoginRequest): Flow<Resource<LoginResponse>>

  suspend fun forgetPassword(
    forgetPasswordRequest: ForgetPasswordRequest
  ): Resource<ForgetPasswordResponse>

  suspend fun getProfile(): Resource<ProfileResponse>

  suspend fun updateProfile(profileRequest: UpdateProfileRequest): Resource<UpdateProfileResponse>

  suspend fun logout()
}
