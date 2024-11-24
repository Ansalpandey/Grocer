package com.app.humaraapnabazaar.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.app.humaraapnabazaar.R
import com.app.humaraapnabazaar.ui.navigation.Route

@Composable
fun WelcomeScreen(modifier: Modifier = Modifier, navController: NavHostController) {
  Box(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
    Image(
      painter = painterResource(R.drawable.sign_up_screen_1),
      contentDescription = "welcome Image",
      modifier = Modifier.fillMaxSize(),
      contentScale = ContentScale.Crop,
    )

    Column(
      modifier =
        Modifier.fillMaxWidth()
          .fillMaxHeight(0.4f)
          .background(Color.White)
          .align(Alignment.BottomCenter)
          .padding(16.dp)
    ) {
      Text(
        text = "Welcome!",
        style = MaterialTheme.typography.displaySmall,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.fillMaxWidth(),
        color = Color.Black,
      )
      Spacer(modifier = Modifier.height(8.dp))
      Text(
        text =
          "Discover a world of fresh, affordable, and high-quality groceries, all delivered straight to your door. Shop with ease and convenience, anytime, anywhere.",
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.fillMaxWidth(),
        color = Color.Gray,
      )
      Spacer(Modifier.height(100.dp))
      Box(
        modifier =
          Modifier.fillMaxWidth()
            .height(48.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(brush = Brush.linearGradient(listOf(Color(0xFFAEDC81), Color(0xFF498813))))
      ) {
        Button(
          onClick = { navController.navigate(Route.RegisterScreen) },
          modifier = Modifier.fillMaxSize(),
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
            Icon(
              painter = painterResource(R.drawable.account_icon),
              contentDescription = "create_account",
              modifier = Modifier.size(24.dp),
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
              text = "Create an account",
              modifier = Modifier.weight(1f),
              textAlign = TextAlign.Center,
              fontSize = MaterialTheme.typography.titleMedium.fontSize,
            )
          }
        }
      }
      Spacer(Modifier.height(20.dp))
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
      ) {
        Text(text = "Already have an account? ", color = Color.Gray)
        Text(
          text = "Login",
          color = Color.Black,
          fontWeight = FontWeight.Bold,
          modifier = Modifier.clickable { navController.navigate(Route.LoginScreen) },
        )
      }
    }
  }
}
