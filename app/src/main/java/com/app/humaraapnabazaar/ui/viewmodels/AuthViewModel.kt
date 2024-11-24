package com.app.humaraapnabazaar.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.humaraapnabazaar.common.Resource
import com.app.humaraapnabazaar.data.model.ForgetPasswordRequest
import com.app.humaraapnabazaar.data.model.LoginRequest
import com.app.humaraapnabazaar.data.model.LoginResponse
import com.app.humaraapnabazaar.data.model.ProfileResponse
import com.app.humaraapnabazaar.data.model.RegisterRequest
import com.app.humaraapnabazaar.data.model.RegisterResponse
import com.app.humaraapnabazaar.data.model.UpdateProfileRequest
import com.app.humaraapnabazaar.data.repository.UserRepository
import com.app.humaraapnabazaar.ui.stateholder.RegisterUserStateHolder
import com.app.humaraapnabazaar.ui.stateholder.UserStateHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {
  private val _loggedInUserStateHolder = MutableStateFlow(UserStateHolder())
  val loggedInUserStateHolder: StateFlow<UserStateHolder> = _loggedInUserStateHolder.asStateFlow()
  private val _profileState = MutableStateFlow<Resource<ProfileResponse>>(Resource.Loading())
  val userProfileState: StateFlow<Resource<ProfileResponse>> = _profileState.asStateFlow()
  private val _registeredUserStateHolder = MutableStateFlow(RegisterUserStateHolder())
  val registeredUserStateHolder: StateFlow<RegisterUserStateHolder> =
    _registeredUserStateHolder.asStateFlow()

  init {
    viewModelScope.launch {
      userRepository.userStateHolder.collect { state -> _loggedInUserStateHolder.value = state }
    }
  }

  private fun handleRegisterResource(resource: Resource<RegisterResponse>) {
    when (resource) {
      is Resource.Loading -> {
        _registeredUserStateHolder.value = _registeredUserStateHolder.value.copy(isLoading = true)
      }
      is Resource.Success -> {

        _registeredUserStateHolder.value =
          _registeredUserStateHolder.value.copy(
            isLoading = false,
            data = flowOf(resource.data!!),
            isLoggedIn = false,
            isRegistered = true, // Set registration status here
          )
      }
      is Resource.Error -> {
        _registeredUserStateHolder.value =
          _registeredUserStateHolder.value.copy(
            isLoading = false,
            error = resource.message,
            isRegistered = false, // Reset registration status on error
          )
      }
    }
  }

  private fun handleLoginResource(resource: Resource<LoginResponse>) {
    when (resource) {
      is Resource.Loading -> {
        _loggedInUserStateHolder.value = _loggedInUserStateHolder.value.copy(isLoading = true)
      }
      is Resource.Success -> {
        _loggedInUserStateHolder.value =
          _loggedInUserStateHolder.value.copy(
            isLoading = false,
            data = flowOf(resource.data!!),
            isLoggedIn = true,
            isRegistered = false, // Set registration status here
          )
      }
      is Resource.Error -> {
        _loggedInUserStateHolder.value =
          _loggedInUserStateHolder.value.copy(
            isLoading = false,
            error = resource.message,
            isRegistered = false, // Reset registration status on error
          )
      }
    }
  }

  fun registerUser(user: RegisterRequest) {
    Log.d("AuthViewModel", "registerUser: $user")
    viewModelScope.launch {
      userRepository.registerUser(user).collect { result ->
        if (result is Resource.Success) {
          handleRegisterResource(result)
          _registeredUserStateHolder.value = RegisterUserStateHolder(isRegistered = true)
        } else if (result is Resource.Error) {
          _registeredUserStateHolder.value = RegisterUserStateHolder(error = result.message)
        }
      }
    }
  }

  fun loginUser(user: LoginRequest) {
    viewModelScope.launch {
      userRepository.loginUser(user).collect { result ->
        if (result is Resource.Success) {
          handleLoginResource(result)
          _loggedInUserStateHolder.value = UserStateHolder(isLoggedIn = true)
        } else if (result is Resource.Error) {
          _loggedInUserStateHolder.value = UserStateHolder(error = result.message)
        }
      }
    }
  }

  fun getProfile() {
    viewModelScope.launch {
      val response = userRepository.getProfile()
      _profileState.value = response
    }
  }

  fun forgetPassword(forgetPasswordRequest: ForgetPasswordRequest) {
    viewModelScope.launch { userRepository.forgetPassword(forgetPasswordRequest) }
  }

  fun updateProfile(profileRequest: UpdateProfileRequest) {
    viewModelScope.launch {
      userRepository.updateProfile(profileRequest)
    }
  }

  fun logout() {
    viewModelScope.launch {
      userRepository.logout()
    }
  }
}
