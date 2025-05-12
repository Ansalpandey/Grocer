package com.app.grocer.utils

import android.content.Context
import android.os.Handler
import android.os.Looper
import com.app.grocer.common.Resource
import com.app.grocer.data.datasource.UserDataSource
import dagger.Lazy
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton

class TokenManager @Inject constructor(@ApplicationContext private val context: Context) {
    private val sharedPreferences = context.getSharedPreferences("TokenManager", Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        sharedPreferences.edit().putString("token", token).apply()
    }

    fun getToken(): String? {
        return sharedPreferences.getString("token", null)
    }

    fun deleteToken() {
        sharedPreferences.edit().remove("token").apply()
    }

    fun clearTokens() {
        sharedPreferences.edit().clear().apply()
    }
}

@Singleton
class TokenRefreshManager
@Inject
constructor(
    private val tokenManager: TokenManager,
    private val userDataSource: Lazy<UserDataSource>,
) {
    private val handler = Handler(Looper.getMainLooper())
    private val lock = Any()

    fun startTokenRefresh() {
        val refreshTokenRunnable =
            object : Runnable {
                override fun run() {
                    synchronized(lock) {
                        if (tokenManager.getToken() != null) {
                            val newToken = refreshAccessToken()
                            if (newToken != null) {
                                tokenManager.saveToken(newToken)
                            }
                        }
                        handler.postDelayed(this, 1296000990L) // Schedule to run every 15 days
                    }
                }
            }
        handler.postDelayed(refreshTokenRunnable, 1296000990L) // Start after 15 days
    }

    private fun refreshAccessToken(): String? {
        return try {
            val refreshResponse = runBlocking { userDataSource.get().refreshToken() }
            if (refreshResponse is Resource.Success) {
                val newToken = refreshResponse.data?.token
                if (newToken != null) {
                    return newToken
                }
            }
            tokenManager.clearTokens()
            null
        } catch (e: Exception) {
            e.printStackTrace()
            tokenManager.clearTokens()
            null
        }
    }
}
