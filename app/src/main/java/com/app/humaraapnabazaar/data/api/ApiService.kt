package com.app.humaraapnabazaar.data.api

import com.app.humaraapnabazaar.data.model.ForgetPasswordRequest
import com.app.humaraapnabazaar.data.model.ForgetPasswordResponse
import com.app.humaraapnabazaar.data.model.LoginRequest
import com.app.humaraapnabazaar.data.model.LoginResponse
import com.app.humaraapnabazaar.data.model.RefreshTokenResponse
import com.app.humaraapnabazaar.data.model.RegisterRequest
import com.app.humaraapnabazaar.data.model.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT

interface ApiService {
  @POST("users/login") suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

  @POST("users/register")
  suspend fun register(@Body registerRequest: RegisterRequest): Response<RegisterResponse>

  @POST("users/refresh-token") suspend fun refreshToken(): Response<RefreshTokenResponse>

  @PUT("users/forget-password")
  suspend fun forgetPassword(
    @Body forgetPasswordRequest: ForgetPasswordRequest
  ): Response<ForgetPasswordResponse>
}
