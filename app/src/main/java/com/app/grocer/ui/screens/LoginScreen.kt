package com.app.grocer.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.app.grocer.data.model.ForgetPasswordRequest
import com.app.grocer.data.model.LoginRequest
import com.app.grocer.ui.navigation.Route
import com.app.grocer.ui.viewmodels.AuthViewModel
import com.app.humaraapnabazaar.R

@Composable
fun LoginScreen(
  modifier: Modifier = Modifier,
  authViewModel: AuthViewModel = hiltViewModel(),
  navController: NavHostController,
) {
  val state = authViewModel.loggedInUserStateHolder.collectAsState()
  val email = rememberSaveable { mutableStateOf("") }
  val password = rememberSaveable { mutableStateOf("") }
  val context = LocalContext.current
  var passwordVisible by rememberSaveable { mutableStateOf(false) }
  var showForgotPasswordDialog by rememberSaveable { mutableStateOf(false) }

  LaunchedEffect(state.value.isLoggedIn) {
    if (state.value.isLoggedIn) {
      navController.navigate(Route.MainScreen) { popUpTo(Route.LoginScreen) { inclusive = true } }
    }
  }

  LaunchedEffect(state.value.error) {
    if (state.value.error?.isNotEmpty() == true) {
      Toast.makeText(context, state.value.error.toString(), Toast.LENGTH_SHORT).show()
    }
  }

  Box(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
    Image(
      painter = painterResource(R.drawable.login_screen_1),
      contentDescription = "welcome Image",
      modifier = Modifier.fillMaxSize().height(600.dp),
      contentScale = ContentScale.Crop,
    )
    Column(
      modifier =
        Modifier.fillMaxSize()
          .height(400.dp)
          .background(Color.White)
          .align(Alignment.BottomCenter)
          .padding(16.dp)
    ) {
      Text(
        text = "Welcome back!",
        style = MaterialTheme.typography.displaySmall,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.fillMaxWidth(),
        color = Color.Black,
      )
      Spacer(modifier = Modifier.height(8.dp))
      Text(
        text = "Sign in to your account",
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.fillMaxWidth(),
        color = Color.DarkGray,
      )
      Spacer(Modifier.height(10.dp))
      Box(Modifier.fillMaxWidth().height(60.dp)) {
        OutlinedTextField(
          value = email.value,
          onValueChange = { email.value = it },
          modifier = Modifier.fillMaxSize(),
          singleLine = true,
          maxLines = 1,
          colors =
            TextFieldDefaults.colors(
              focusedContainerColor = Color.Transparent,
              unfocusedContainerColor = Color.Transparent,
              unfocusedIndicatorColor = Color.Black,
              focusedTextColor = Color.Black,
            ),
          leadingIcon = {
            Icon(
              imageVector = Icons.Default.Email,
              contentDescription = "name",
              tint = Color.LightGray,
            )
          },
          label = { Text(text = "Email address") },
        )
      }
      Spacer(Modifier.height(10.dp))
      Box(Modifier.fillMaxWidth().height(60.dp)) {
        OutlinedTextField(
          visualTransformation =
            if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
          value = password.value,
          onValueChange = { password.value = it },
          modifier = Modifier.fillMaxSize(),
          singleLine = true,
          maxLines = 1,
          colors =
            TextFieldDefaults.colors(
              focusedContainerColor = Color.Transparent,
              unfocusedContainerColor = Color.Transparent,
              unfocusedIndicatorColor = Color.Black,
              focusedTextColor = Color.Black,
            ),
          trailingIcon = {
            val icon =
              if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
            IconButton(onClick = { passwordVisible = !passwordVisible }) {
              Icon(
                imageVector = icon,
                contentDescription = if (passwordVisible) "Hide password" else "Show password",
                tint = Color.LightGray,
              )
            }
          },
          leadingIcon = {
            Icon(
              imageVector = Icons.Default.Lock,
              contentDescription = "name",
              tint = Color.LightGray,
            )
          },
          label = { Text(text = "Password") },
        )
      }
      Spacer(Modifier.height(10.dp))
      Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
        Text(
          "Forgot password?",
          color = Color(0xFF407EC7),
          fontWeight = FontWeight.Bold,
          modifier = Modifier.clickable { showForgotPasswordDialog = true },
        )
        if (showForgotPasswordDialog) {
          ForgotPasswordDialog(
            onDismissRequest = { showForgotPasswordDialog = false },
            onSubmit = { email, newPassword ->
              authViewModel.forgetPassword(
                ForgetPasswordRequest(email = email, password = newPassword)
              )
            },
          )
        }
      }
      Spacer(Modifier.height(30.dp))
      Box(
        modifier =
          Modifier.fillMaxWidth()
            .height(48.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(brush = Brush.linearGradient(listOf(Color(0xFFAEDC81), Color(0xFF498813))))
      ) {
        Button(
          onClick = {
            if (email.value.isEmpty() || password.value.isEmpty()) {
              Toast.makeText(context, "Email and password are required", Toast.LENGTH_SHORT).show()
            }
            authViewModel.loginUser(LoginRequest(email = email.value, password = password.value))
          },
          modifier = Modifier.fillMaxSize(),
          enabled = email.value.isNotEmpty() && password.value.isNotEmpty(),
          colors =
            ButtonDefaults.buttonColors(
              containerColor = Color.Transparent,
              contentColor = Color.White,
            ),
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth(),
          ) {
            Spacer(modifier = Modifier.width(16.dp))
            Text(
              text = "Login",
              modifier = Modifier.weight(1f),
              fontSize = MaterialTheme.typography.titleMedium.fontSize,
              textAlign = TextAlign.Center,
            )
          }
        }
      }
      Spacer(Modifier.height(8.dp))
      Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        Text(text = "Don’t have an account? ", color = Color.Gray, fontWeight = FontWeight.Bold)
        Text(
          text = "Sign up",
          color = Color.Black,
          fontWeight = FontWeight.Bold,
          modifier = Modifier.clickable { navController.navigate(Route.RegisterScreen) },
        )
      }
    }
  }
  Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
    Icon(
      imageVector = Icons.Default.ArrowBackIosNew,
      contentDescription = "back_button",
      modifier =
        Modifier.padding(start = 10.dp, top = 60.dp).clickable { navController.popBackStack() },
    )
    Text(
      text = "Welcome",
      modifier = Modifier.fillMaxWidth().padding(top = 60.dp),
      color = Color.White,
      fontSize = MaterialTheme.typography.titleLarge.fontSize,
      textAlign = TextAlign.Center,
    )
  }
}

@Composable
fun ForgotPasswordDialog(
  onDismissRequest: () -> Unit,
  onSubmit: (email: String, newPassword: String) -> Unit,
) {
  var passwordVisible by rememberSaveable { mutableStateOf(false) }
  val email = rememberSaveable { mutableStateOf("") }
  val newPassword = rememberSaveable { mutableStateOf("") }

  Dialog(onDismissRequest = { onDismissRequest() }) {
    Box(
      modifier =
        Modifier.fillMaxWidth()
          .clip(RoundedCornerShape(12.dp))
          .background(Color.White)
          .padding(16.dp)
    ) {
      Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
      ) {
        Text(
          text = "Reset Password",
          style = MaterialTheme.typography.headlineSmall,
          fontWeight = FontWeight.Bold,
          color = Color.Black,
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
          value = email.value,
          onValueChange = { email.value = it },
          label = { Text("Email") },
          modifier = Modifier.fillMaxWidth(),
          leadingIcon = { Icon(Icons.Default.Email, contentDescription = "Email") },
          colors =
            TextFieldDefaults.colors(
              focusedContainerColor = Color.Transparent,
              unfocusedContainerColor = Color.Transparent,
              unfocusedIndicatorColor = Color.Black,
              focusedTextColor = Color.Black,
            ),
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
          value = newPassword.value,
          onValueChange = { newPassword.value = it },
          label = { Text("New Password") },
          modifier = Modifier.fillMaxWidth(),
          leadingIcon = { Icon(Icons.Default.Lock, contentDescription = "New Password") },
          visualTransformation =
            if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
          trailingIcon = {
            val icon =
              if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
            IconButton(onClick = { passwordVisible = !passwordVisible }) {
              Icon(
                imageVector = icon,
                contentDescription = if (passwordVisible) "Hide password" else "Show password",
                tint = Color.LightGray,
              )
            }
          },
          colors =
            TextFieldDefaults.colors(
              focusedContainerColor = Color.Transparent,
              unfocusedContainerColor = Color.Transparent,
              unfocusedIndicatorColor = Color.Black,
              focusedTextColor = Color.Black,
            ),
        )
        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
          Button(
            onClick = { onDismissRequest() },
            colors =
              ButtonDefaults.buttonColors(containerColor = Color.Gray, contentColor = Color.White),
          ) {
            Text("Cancel")
          }
          Button(
            onClick = {
              if (email.value.isNotEmpty() && newPassword.value.isNotEmpty()) {
                onSubmit(email.value, newPassword.value)
                onDismissRequest()
              }
            },
            colors =
              ButtonDefaults.buttonColors(
                containerColor = Color(0xFF6CC51D),
                contentColor = Color.White,
              ),
          ) {
            Text("Submit")
          }
        }
      }
    }
  }
}
