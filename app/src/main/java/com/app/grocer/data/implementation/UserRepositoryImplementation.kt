package com.app.grocer.data.implementation

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import com.app.grocer.common.Resource
import com.app.grocer.data.datasource.UserDataSource
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
import com.app.grocer.data.model.User
import com.app.grocer.data.repository.UserRepository
import com.app.grocer.preferences.UserPreferences
import com.app.grocer.preferences.dataStore
import com.app.grocer.ui.stateholder.UserStateHolder
import com.app.grocer.utils.TokenManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class UserRepositoryImplementation
@Inject
constructor(
  private val userDataSource: UserDataSource,
  @ApplicationContext private val context: Context,
  private val tokenManager: TokenManager,
) : UserRepository {
  private val dataStore = context.dataStore

  override val userStateHolder: Flow<UserStateHolder> =
    dataStore.data
      .map { preferences ->
        val isLoggedIn = preferences[UserPreferences.IS_LOGGED_IN] ?: false
        if (isLoggedIn) {
          UserStateHolder(
            isLoading = false,
            data =
              flowOf(
                LoginResponse(
                  token = tokenManager.getToken() ?: "",
                  user =
                    User(
                      _id = preferences[UserPreferences.USER_ID] ?: "",
                      name = preferences[UserPreferences.USER_NAME] ?: "Unknown",
                      email = preferences[UserPreferences.USER_EMAIL] ?: "Unknown",
                      role = "",
                    ),
                )
              ),
            error = "",
            isLoggedIn = isLoggedIn,
          )
        } else {
          UserStateHolder()
        }
      }
      .catch { exception ->
        emit(UserStateHolder(error = exception.message ?: "An error occurred"))
      }

  private suspend fun setUserPreferences(user: LoginResponse, isLoggedIn: Boolean) {
    try {
      dataStore.edit { preferences ->
        preferences[UserPreferences.IS_LOGGED_IN] = isLoggedIn
        preferences[UserPreferences.USER_NAME] = user.user.name ?: "Unknown"
        preferences[UserPreferences.USER_ID] = user.user._id ?: ""
        preferences[UserPreferences.USER_EMAIL] = user.user.email ?: "Unknown"
      }
    } catch (e: Exception) {
      Log.e("UserRepository", "Failed to set user preferences: ${e.message}")
    }
  }

  override suspend fun registerUser(user: RegisterRequest): Flow<Resource<RegisterResponse>> {
    return userDataSource
      .registerUser(user)
      .onEach { resource ->
        when (resource) {
          is Resource.Error -> {
            Log.e("UserRepository", "registerUser: ${resource.message}")
          }
          else -> Unit
        }
      }
      .catch { exception ->
        Log.e("UserRepository", "registerUser failed: ${exception.message}")
        emit(Resource.Error("Failed to register user: ${exception.message}"))
      }
  }

  override suspend fun loginUser(user: LoginRequest): Flow<Resource<LoginResponse>> {
    return userDataSource
      .loginUser(user)
      .onEach { resource ->
        when (resource) {
          is Resource.Success -> {
            resource.data?.let { loginResponse ->
              setUserPreferences(loginResponse, true)

              val newToken = loginResponse.token
              val currentToken = tokenManager.getToken()
              if (newToken != currentToken) {
                tokenManager.saveToken(newToken)
              }
            } ?: Log.e("UserRepository", "loginUser: Empty response data")
          }
          is Resource.Error -> {
            Log.e("UserRepository", "loginUser: ${resource.message}")
          }
          else -> Unit
        }
      }
      .catch { exception ->
        Log.e("UserRepository", "loginUser failed: ${exception.message}")
        emit(Resource.Error("Login failed: ${exception.message}"))
      }
  }

  override suspend fun refreshToken(): Resource<RefreshTokenResponse> {
    return try {
      userDataSource.refreshToken()
    } catch (e: Exception) {
      Log.e("UserRepository", "refreshToken: ${e.message}")
      Resource.Error("Failed to refresh token: ${e.message}")
    }
  }

  override suspend fun forgetPassword(
    forgetPasswordRequest: ForgetPasswordRequest
  ): Resource<ForgetPasswordResponse> {
    return try {
      userDataSource.forgetPassword(forgetPasswordRequest)
    } catch (e: Exception) {
      Log.e("UserRepository", "forgetPassword: ${e.message}")
      Resource.Error("Failed to process forget password request: ${e.message}")
    }
  }

  override suspend fun getProfile(): Resource<ProfileResponse> {
    return try {
      userDataSource.getProfile()
    } catch (e: Exception) {
      Log.e("UserRepository", "getProfile: ${e.message}")
      Resource.Error("Failed to fetch profile: ${e.message}")
    }
  }

  override suspend fun updateProfile(
    profileRequest: UpdateProfileRequest
  ): Resource<UpdateProfileResponse> {
    return try {
      userDataSource.updateProfile(profileRequest)
    } catch (e: Exception) {
      Log.e("UserRepository", "updateProfile: ${e.message}")
      Resource.Error("Failed to update profile: ${e.message}")
    }
  }

  private suspend fun clearUserPreferences() {
    dataStore.edit { preferences -> preferences.clear() }
  }

  override suspend fun logout() {
    clearUserPreferences()
  }
}
