package com.app.grocer.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.app.grocer.data.model.ForgetPasswordRequest
import com.app.grocer.data.model.UpdateProfileRequest
import com.app.grocer.ui.viewmodels.AuthViewModel

@Composable
fun AboutMeScreen(
  modifier: Modifier = Modifier,
  navController: NavController,
  name: String,
  email: String,
  phone: String,
  authViewModel: AuthViewModel = hiltViewModel(),
) {
  // State variables for edit ability and input values
  var isEditingName by remember { mutableStateOf(false) }
  var isEditingEmail by remember { mutableStateOf(false) }
  var isEditingPhone by remember { mutableStateOf(false) }
  var newPassword by remember { mutableStateOf("") }
  var editableName by remember { mutableStateOf(name) }
  var editableEmail by remember { mutableStateOf(email) }
  var editablePhone by remember { mutableStateOf(phone) }
  var passwordVisible by rememberSaveable { mutableStateOf(false) }

  Column(
    modifier =
      Modifier.fillMaxSize()
        .safeContentPadding()
        .padding(16.dp)
        .verticalScroll(rememberScrollState())
  ) {
    // Header
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
      Icon(
        imageVector = Icons.AutoMirrored.Filled.ArrowBackIos,
        contentDescription = "back",
        modifier =
          Modifier.clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() },
          ) {
            navController.popBackStack()
          },
      )
      Spacer(modifier = Modifier.width(8.dp))
      Text(
        text = "About Me",
        style = MaterialTheme.typography.titleLarge,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.fillMaxWidth(),
      )
    }

    Spacer(modifier = Modifier.height(20.dp))

    Text(
      text = "Personal Details",
      fontSize = MaterialTheme.typography.titleLarge.fontSize,
      fontWeight = FontWeight.Bold,
      modifier = Modifier.padding(bottom = 16.dp),
    )

    // Name Field
    EditableField(
      label = "Name",
      value = editableName,
      isEditing = isEditingName,
      onValueChange = { editableName = it },
      onEditClick = { isEditingName = !isEditingName },
      leadingIcon = {
        Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "account")
      },
    )

    Spacer(modifier = Modifier.height(16.dp))

    // Email Field
    EditableField(
      label = "Email",
      value = editableEmail,
      isEditing = isEditingEmail,
      onValueChange = { editableEmail = it },
      onEditClick = { isEditingEmail = !isEditingEmail },
      leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = "account") },
    )

    Spacer(modifier = Modifier.height(16.dp))

    // Phone Field
    EditableField(
      label = "Phone",
      value = editablePhone,
      isEditing = isEditingPhone,
      onValueChange = { editablePhone = it },
      onEditClick = { isEditingPhone = !isEditingPhone },
      leadingIcon = { Icon(imageVector = Icons.Default.Phone, contentDescription = "account") },
    )
    Spacer(Modifier.height(20.dp))
    Button(
      modifier = Modifier.fillMaxWidth().padding(end = 50.dp),
      onClick = {
        authViewModel.updateProfile(
          UpdateProfileRequest(name = editableName, email = editableEmail, phone = editablePhone)
        )
      },
    ) {
      Text("Save Changes")
    }
    Spacer(Modifier.height(20.dp))
    Text("Change Password")
    Spacer(Modifier.height(20.dp))
    OutlinedTextField(
      modifier = Modifier.fillMaxWidth(),
      leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = "email") },
      value = email,
      onValueChange = { editableEmail = it },
    )
    Spacer(Modifier.height(20.dp))
    OutlinedTextField(
      visualTransformation =
        if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
      modifier = Modifier.fillMaxWidth(),
      leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = "password") },
      value = newPassword,
      onValueChange = { newPassword = it },
      trailingIcon = {
        val icon = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
        IconButton(onClick = { passwordVisible = !passwordVisible }) {
          Icon(
            imageVector = icon,
            contentDescription = if (passwordVisible) "Hide password" else "Show password",
            tint = Color.LightGray,
          )
        }
      },
    )
    Spacer(Modifier.height(20.dp))
    Button(
      modifier = Modifier.fillMaxWidth(),
      onClick = {
        authViewModel.forgetPassword(ForgetPasswordRequest(email = email, password = newPassword))
      },
    ) {
      Text("Change Password")
    }
  }
}

@Composable
fun EditableField(
  label: String,
  value: String,
  isEditing: Boolean,
  onValueChange: (String) -> Unit,
  onEditClick: () -> Unit,
  leadingIcon: @Composable (() -> Unit)? = null,
) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween,
  ) {
    OutlinedTextField(
      value = value,
      onValueChange = onValueChange,
      label = { Text(label) },
      readOnly = !isEditing,
      modifier = Modifier.weight(1f),
      leadingIcon = leadingIcon,
    )
    IconButton(onClick = onEditClick) {
      Icon(
        imageVector = if (isEditing) Icons.Default.Check else Icons.Default.Edit,
        contentDescription = if (isEditing) "Save" else "Edit",
      )
    }
  }
}
