package com.app.grocer.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.app.grocer.data.model.RegisterRequest
import com.app.grocer.ui.navigation.Route
import com.app.grocer.ui.viewmodels.AuthViewModel
import com.app.humaraapnabazaar.R

@Composable
fun RegisterScreen(
  modifier: Modifier = Modifier,
  authViewModel: AuthViewModel = hiltViewModel(),
  navController: NavHostController,
) {
  val name = rememberSaveable { mutableStateOf("") }
  val email = rememberSaveable { mutableStateOf("") }
  val password = rememberSaveable { mutableStateOf("") }
  val phone = rememberSaveable { mutableStateOf("") }
  var passwordVisible by rememberSaveable { mutableStateOf(false) }
  val context = LocalContext.current
  val userState = authViewModel.registeredUserStateHolder.collectAsState()
  var showSuccessDialog by rememberSaveable { mutableStateOf(false) }

  LaunchedEffect(userState.value.isRegistered) {
    if (userState.value.isRegistered) {
      showSuccessDialog = true
    }
  }

  LaunchedEffect(userState.value.error) {
    if (userState.value.error?.isNotEmpty() == true) {
      Toast.makeText(context, userState.value.error, Toast.LENGTH_SHORT).show()
    }
  }

  if (userState.value.isLoading) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
      CircularProgressIndicator()
    }
  } else {
    Box(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
      Image(
        painter = painterResource(R.drawable.sign_up_screen_2),
        contentDescription = "welcome Image",
        modifier = Modifier.fillMaxSize().height(400.dp),
        contentScale = ContentScale.Crop,
      )
      Column(
        modifier =
          Modifier.fillMaxSize()
            .height(500.dp)
            .background(Color.White)
            .align(Alignment.BottomCenter)
            .padding(16.dp)
      ) {
        Text(
          text = "Create account",
          style = MaterialTheme.typography.displaySmall,
          fontWeight = FontWeight.Bold,
          modifier = Modifier.fillMaxWidth(),
          color = Color.Black,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
          text = "Quickly create your account",
          style = MaterialTheme.typography.bodyMedium,
          modifier = Modifier.fillMaxWidth(),
          color = Color.DarkGray,
        )
        Spacer(Modifier.height(10.dp))
        // Name Field
        Box(Modifier.fillMaxWidth().height(60.dp)) {
          OutlinedTextField(
            value = name.value,
            onValueChange = { name.value = it },
            modifier = Modifier.fillMaxSize(),
            maxLines = 1,
            singleLine = true,
            colors =
              TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Black,
                focusedTextColor = Color.Black,
              ),
            leadingIcon = {
              Icon(
                imageVector = Icons.Default.Face,
                contentDescription = "name",
                tint = Color.LightGray,
              )
            },
            label = { Text(text = "Name") },
          )
        }
        Spacer(Modifier.height(10.dp))
        // Email Field
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
                contentDescription = "email",
                tint = Color.LightGray,
              )
            },
            label = { Text(text = "Email address") },
          )
        }
        Spacer(Modifier.height(10.dp))
        // Phone Field
        Box(Modifier.fillMaxWidth().height(60.dp)) {
          OutlinedTextField(
            value = phone.value,
            onValueChange = { phone.value = it },
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
                imageVector = Icons.Default.Phone,
                contentDescription = "phone",
                tint = Color.LightGray,
              )
            },
            label = { Text(text = "Phone Number") },
          )
        }
        Spacer(Modifier.height(10.dp))
        // Password Field
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
                contentDescription = "password",
                tint = Color.LightGray,
              )
            },
            label = { Text(text = "Password") },
          )
        }
        Spacer(Modifier.height(20.dp))
        // Signup Button
        Box(
          modifier =
            Modifier.fillMaxWidth()
              .height(48.dp)
              .clip(RoundedCornerShape(8.dp))
              .background(
                brush = Brush.linearGradient(listOf(Color(0xFFAEDC81), Color(0xFF498813)))
              )
        ) {
          Button(
            onClick = {
              if (
                name.value.isEmpty() ||
                  email.value.isEmpty() ||
                  password.value.isEmpty() ||
                  phone.value.isEmpty()
              ) {
                Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_SHORT).show()
              }
              authViewModel.registerUser(
                RegisterRequest(
                  name = name.value,
                  email = email.value,
                  password = password.value,
                  phone = phone.value,
                )
              )
            },
            modifier = Modifier.fillMaxSize(),
            enabled =
              name.value.isNotEmpty() &&
                email.value.isNotEmpty() &&
                password.value.isNotEmpty() &&
                phone.value.isNotEmpty(), // Enable only if all fields are filled
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
              Text(
                text = "Signup",
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
              )
            }
          }
        }
        Spacer(Modifier.height(8.dp))
        // Login Link
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
          Text(text = "Already have an account? ", color = Color.Gray, fontWeight = FontWeight.Bold)
          Text(
            text = "Login",
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.clickable(
              indication = null,
              interactionSource = remember { MutableInteractionSource() }
            ) {

              navController.navigate(Route.LoginScreen) },
          )
        }
      }
    }
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
      Icon(
        imageVector = Icons.Default.ArrowBackIosNew,
        contentDescription = "back_button",
        modifier =
          Modifier.padding(start = 10.dp, top = 40.dp).clickable { navController.popBackStack() },
      )
      Text(
        text = "Welcome",
        modifier = Modifier.fillMaxWidth().padding(top = 40.dp),
        color = Color.White,
        fontSize = MaterialTheme.typography.titleLarge.fontSize,
        textAlign = TextAlign.Center,
      )
      // Show success dialog
      if (showSuccessDialog) {
        AlertDialog(
          onDismissRequest = { showSuccessDialog = false },
          title = { Text(text = "Registration Successful") },
          text = { Text(text = "Your account has been successfully created. Please log in.") },
          confirmButton = {
            TextButton(
              onClick = {
                showSuccessDialog = false
                navController.navigate(Route.LoginScreen)
              }
            ) {
              Text("OK")
            }
          },
        )
      }
    }
  }
}
