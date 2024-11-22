package com.app.humaraapnabazaar.data.implementation

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import com.app.humaraapnabazaar.common.Resource
import com.app.humaraapnabazaar.data.datasource.UserDataSource
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
import com.app.humaraapnabazaar.data.model.User
import com.app.humaraapnabazaar.data.repository.UserRepository
import com.app.humaraapnabazaar.preferences.UserPreferences
import com.app.humaraapnabazaar.preferences.dataStore
import com.app.humaraapnabazaar.ui.stateholder.UserStateHolder
import com.app.humaraapnabazaar.utils.TokenManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
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
    dataStore.data.map { preferences ->
      val isLoggedIn = preferences[UserPreferences.IS_LOGGED_IN] ?: false
      if (isLoggedIn) {
        UserStateHolder(
          isLoading = false,
          data = flowOf(
            LoginResponse(
              token = "",
              user = User(
                _id = preferences[UserPreferences.USER_ID] ?: "",
                name = preferences[UserPreferences.USER_NAME] ?: "",
                email = preferences[UserPreferences.USER_EMAIL] ?: "",
                role = "",
              )
            )
          ),
          error = "",
          isLoggedIn = isLoggedIn,
        )
      } else {

        UserStateHolder()
      }
    }

  private suspend fun setUserPreferences(user: LoginResponse, isLoggedIn: Boolean) {
    dataStore.edit { preferences ->
      preferences[UserPreferences.IS_LOGGED_IN] = isLoggedIn
      preferences[UserPreferences.USER_NAME] = user.user.name
      preferences[UserPreferences.USER_ID] = user.user._id
      preferences[UserPreferences.USER_EMAIL] = user.user.email
    }
  }

  override suspend fun registerUser(user: RegisterRequest): Flow<Resource<RegisterResponse>> {
    val response = userDataSource.registerUser(user)
    return response
  }

  override suspend fun loginUser(user: LoginRequest): Flow<Resource<LoginResponse>> {
    return userDataSource.loginUser(user).onEach { resource ->
      if (resource is Resource.Success) {
        setUserPreferences(resource.data!!, true)

        if (resource.data.token != tokenManager.getToken()) {
          tokenManager.saveToken(resource.data.token)
        }
      } else if (resource is Resource.Error) {
        setUserPreferences(resource.data!!, false)
      }
    }
  }

  override suspend fun refreshToken(): Resource<RefreshTokenResponse> {
    val response = userDataSource.refreshToken()
    return response
  }

  override suspend fun forgetPassword(forgetPasswordRequest: ForgetPasswordRequest): Resource<ForgetPasswordResponse> {
    return userDataSource.forgetPassword(forgetPasswordRequest)
  }

  override suspend fun getProfile(): Resource<ProfileResponse> {
    return userDataSource.getProfile()
  }

  override suspend fun updateProfile(profileRequest: UpdateProfileRequest): Resource<UpdateProfileResponse> {
    return userDataSource.updateProfile(profileRequest)
  }
}
