package com.app.humaraapnabazaar.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.app.humaraapnabazaar.R
import com.app.humaraapnabazaar.ui.navigation.Route
import com.app.humaraapnabazaar.ui.viewmodels.AuthViewModel

@Composable
fun AccountScreen(
  modifier: Modifier = Modifier,
  authViewModel: AuthViewModel = hiltViewModel(),
  navController: NavController,
) {
  val userProfileState = authViewModel.userProfileState.collectAsState()
  var showLogoutDialog by remember { mutableStateOf(false) }
  LaunchedEffect(key1 = userProfileState) { authViewModel.getProfile() }
  Column(modifier = Modifier.fillMaxSize().safeContentPadding()) {
    // Title Row
    Row(modifier = Modifier.fillMaxWidth()) {
      Text(
        modifier = Modifier.fillMaxWidth(),
        text = "Account Details",
        fontWeight = FontWeight.Bold,
        fontSize = MaterialTheme.typography.titleLarge.fontSize,
        textAlign = TextAlign.Center,
      )
    }
    // Account
    Row(
      modifier =
        Modifier.fillMaxWidth().padding(16.dp).clickable(
          indication = null,
          interactionSource = remember { MutableInteractionSource() },
        ) {
          navController.navigate(
            Route.AboutMeScreen(
              name = userProfileState.value.data?.name!!,
              email = userProfileState.value.data?.email!!,
              phone = userProfileState.value.data?.phone!!,
            )
          )
        },
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween,
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
      ) {
        Icon(
          imageVector = Icons.Default.AccountCircle,
          tint = Color(0xFFAEDC81),
          contentDescription = "about_me",
          modifier = Modifier.size(42.dp),
        )
        Spacer(modifier = Modifier.width(8.dp)) // Space between Icon and Text
        Text(text = "About Me", style = MaterialTheme.typography.bodyLarge)
      }

      Icon(
        imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
        contentDescription = "arrow_forward",
        modifier = Modifier.size(24.dp),
      )
    }

    // Orders
    Row(
      modifier =
        Modifier.fillMaxWidth().padding(16.dp).clickable(
          indication = null,
          interactionSource = remember { MutableInteractionSource() },
        ) {
          navController.navigate(Route.OrdersScreen)
        },
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween,
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
      ) {
        Icon(
          painter = painterResource(R.drawable.box),
          contentDescription = "orders",
          tint = Color(0xFFAEDC81),
          modifier = Modifier.size(42.dp),
        )
        Spacer(modifier = Modifier.width(8.dp)) // Space between Icon and Text
        Text(text = "My Orders", style = MaterialTheme.typography.bodyLarge)
      }

      Icon(
        imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
        contentDescription = "arrow_forward",
        modifier = Modifier.size(24.dp),
      )
    }
    // Sign Out Row
    Row(
      modifier =
        Modifier.fillMaxWidth().padding(16.dp).clickable(
          indication = null,
          interactionSource = remember { MutableInteractionSource() },
        ) {
          showLogoutDialog = true
        },
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween,
    ) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
          painter = painterResource(R.drawable.signout),
          contentDescription = "sign_out",
          tint = Color(0xFFAEDC81),
          modifier = Modifier.size(42.dp),
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = "Sign out", style = MaterialTheme.typography.bodyLarge)
      }

      Icon(
        imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
        contentDescription = "arrow_forward",
        modifier = Modifier.size(24.dp),
      )
    }

    // Logout Confirmation Dialog
    if (showLogoutDialog) {
      AlertDialog(
        onDismissRequest = { showLogoutDialog = false }, // Dismiss on outside click
        title = { Text("Sign Out") },
        text = { Text("Are you sure you want to sign out?") },
        confirmButton = {
          TextButton(
            onClick = {
              showLogoutDialog = false
              authViewModel.logout() // Perform logout
              navController.navigate(Route.LoginScreen) {
                popUpTo(Route.HomeScreen) { inclusive = true }
              }
            }
          ) {
            Text("Yes")
          }
        },
        dismissButton = { TextButton(onClick = { showLogoutDialog = false }) { Text("Cancel") } },
      )
    }
  }
}
