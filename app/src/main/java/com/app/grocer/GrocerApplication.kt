package com.app.grocer

import android.app.Application
import com.app.grocer.utils.TokenRefreshManager
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class GrocerApplication : Application() {
  @Inject lateinit var tokenRefreshManager: TokenRefreshManager

  override fun onCreate() {
    super.onCreate()
    if (::tokenRefreshManager.isInitialized) {
      tokenRefreshManager.startTokenRefresh()
    }
  }
}
