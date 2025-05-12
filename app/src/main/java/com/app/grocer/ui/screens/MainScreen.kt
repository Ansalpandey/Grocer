package com.app.grocer.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.app.grocer.data.model.CategoriesResponseItem
import com.app.grocer.ui.navigation.Destination

@Composable
fun MainScreen(modifier: Modifier = Modifier, navController: NavHostController) {
  val selectedItem = remember { mutableStateOf(Destination.Home) }
  BackHandler(enabled = selectedItem.value != Destination.Home) {
    selectedItem.value = Destination.Home
  }

  NavigationSuiteScaffold(
    containerColor = Color.Transparent,
    navigationSuiteColors =
      NavigationSuiteDefaults.colors(
        navigationBarContainerColor = Color.Transparent,
        navigationBarContentColor = Color.Gray,
      ),
    navigationSuiteItems = {
      Destination.all.forEach {
        item(
          selected = selectedItem.value == it,
          onClick = { selectedItem.value = it },
          icon = { Icon(imageVector = it.icon, contentDescription = it.contentDescription) },
        )
      }
    },
  ) {
    when (selectedItem.value) {
      Destination.Home -> {
        HomeScreen(navController = navController)
      }
      Destination.Search -> {
        SearchScreen(navController = navController)
      }
      Destination.Account -> {
        AccountScreen(navController = navController)
      }
      Destination.Cart -> {
        CartScreen(navController = navController)
      }
    }
  }
}

@Composable
fun CategoryItem(
  modifier: Modifier = Modifier,
  categoriesResponseItem: CategoriesResponseItem,
  onClick: () -> Unit,
) {
  Column(
    modifier = Modifier.width(80.dp).padding(vertical = 8.dp).clickable(
      indication = null,
      interactionSource = remember { MutableInteractionSource() },
    ) { onClick.invoke() },
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center,
  ) {
    AsyncImage(
      model = categoriesResponseItem.categoryImage,
      contentDescription = "category_image",
      modifier =
        Modifier.size(44.dp)
          .clip(CircleShape)
          .background(Color(0xFFF3F4F6)), // Optional: background circle
    )
    Text(
      text = categoriesResponseItem.name,
      fontSize = 12.sp,
      textAlign = TextAlign.Center,
      modifier = Modifier.padding(top = 8.dp),
    )
  }
}
