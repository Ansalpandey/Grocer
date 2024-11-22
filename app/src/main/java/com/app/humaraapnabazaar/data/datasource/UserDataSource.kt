package com.app.humaraapnabazaar.data.datasource

import android.util.Log
import com.app.humaraapnabazaar.common.Resource
import com.app.humaraapnabazaar.data.api.ApiService
import com.app.humaraapnabazaar.data.api.AuthenticatedApiService
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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserDataSource @Inject constructor(private val apiService: ApiService, private val authenticatedApiService: AuthenticatedApiService) {
  fun registerUser(user: RegisterRequest): Flow<Resource<RegisterResponse>> = flow {
    emit(Resource.Loading())
    try {
      val response = apiService.register(user)
      Log.d("RegisterUser", "Response: $response")
      if (response.isSuccessful && response.body() != null) {
        emit(Resource.Success(response.body()!!))
      } else {
        val errorMessage = response.errorBody()?.string() ?: "Unknown error"
        emit(Resource.Error("Registration failed: $errorMessage"))
      }
    } catch (e: Exception) {
      Log.e("RegisterUser", "Error registering user", e)
      emit(Resource.Error(e.localizedMessage ?: "Unknown error"))
    }
  }

  fun loginUser(user: LoginRequest): Flow<Resource<LoginResponse>> = flow {
    emit(Resource.Loading())
    try {
      val response = apiService.login(user)
      if (response.isSuccessful) {
        val userResponse = response.body()
        emit(Resource.Success(userResponse))
      } else {
        emit(Resource.Error("Login failed"))
      }
    } catch (e: Exception) {
      emit(Resource.Error(e.localizedMessage ?: "Unknown error"))
    }
  }

  suspend fun refreshToken(): Resource<RefreshTokenResponse> {
    return try {
      val response = apiService.refreshToken()
      if (response.isSuccessful) {
        Resource.Success(response.body()!!)
      } else {
        Resource.Error("Failed to refresh token")
      }
    } catch (e: Exception) {
      Resource.Error(e.localizedMessage ?: "Unknown error")
    }
  }

  suspend fun forgetPassword(forgetPasswordRequest: ForgetPasswordRequest): Resource<ForgetPasswordResponse> {
    return try {
      val response = apiService.forgetPassword(forgetPasswordRequest)
      Log.d("UserDataSource", "Response: $response")
      if (response.isSuccessful) {
        Resource.Success(response.body()!!)
      } else {
        Resource.Error("Failed to refresh token")
      }
    } catch (e: Exception) {
      Resource.Error(e.localizedMessage ?: "Unknown error")
    }
  }

  suspend fun getProfile() : Resource<ProfileResponse> {
    return try {
      val response = authenticatedApiService.getProfile()
      if (response.isSuccessful) {
        Resource.Success(response.body()!!)
      } else {
        Resource.Error("Failed to get profile")
      }
    } catch (e: Exception) {
      Resource.Error("Failed to get profile")
    }
  }

  suspend fun updateProfile(profileRequest: UpdateProfileRequest): Resource<UpdateProfileResponse> {
    return try {
      val response = authenticatedApiService.updateProfile(profileRequest)
      if (response.isSuccessful) {
        Resource.Success(response.body()!!)
      } else {
        Resource.Error("Failed to get profile")
      }
    } catch (e: Exception) {
      Resource.Error("Failed to get profile")
    }
  }
}
