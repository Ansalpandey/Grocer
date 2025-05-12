package com.app.grocer.data.repository

import com.app.grocer.common.Resource
import com.app.grocer.data.model.ForgetPasswordRequest
import com.app.grocer.data.model.ForgetPasswordResponse
import com.app.grocer.data.model.LoginRequest
import com.app.grocer.data.model.LoginResponse
import com.app.grocer.data.model.ProfileResponse
import com.app.grocer.data.model.RefreshTokenResponse
import com.app.grocer.data.model.RegisterRequest
import com.app.grocer.data.model.RegisterResponse
import com.app.grocer.data.model.UpdateProfileRequest
import com.app.grocer.data.model.UpdateProfileResponse
import com.app.grocer.ui.stateholder.UserStateHolder
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
