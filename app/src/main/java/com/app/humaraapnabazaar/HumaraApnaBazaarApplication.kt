package com.app.humaraapnabazaar

import android.app.Application
import com.app.humaraapnabazaar.di.AppModule
import com.app.humaraapnabazaar.utils.TokenRefreshManager
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class HumaraApnaBazaarApplication : Application() {
  @Inject lateinit var tokenRefreshManager: TokenRefreshManager

  override fun onCreate() {
    super.onCreate()
    if (::tokenRefreshManager.isInitialized) {
      tokenRefreshManager.startTokenRefresh()
    }
  }
}
