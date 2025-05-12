package com.app.grocer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.app.grocer.ui.navigation.NavigationSetup
import com.app.grocer.ui.theme.HumaraApnaBazaarTheme
import com.app.grocer.ui.viewmodels.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  private val splashViewModel: SplashViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val splashScreen = installSplashScreen()
    splashScreen.setKeepOnScreenCondition { splashViewModel.isSplashShow.value }
    enableEdgeToEdge()
    setContent {
      HumaraApnaBazaarTheme {
        val navController = rememberNavController()
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
          NavigationSetup(navController = navController, modifier = Modifier.padding(innerPadding))
        }
      }
    }
  }
}
