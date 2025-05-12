package com.app.grocer.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

enum class Destination(val icon: ImageVector, val contentDescription: String? = null) {
  Home(Icons.Outlined.Home, contentDescription = "home"),
  Search(Icons.Default.Search, contentDescription = "search"),
  Account(Icons.Outlined.AccountCircle, contentDescription = "account"),
  Cart(Icons.Outlined.ShoppingCart, contentDescription = "cart");

  companion object {
    val all = entries.toTypedArray()
  }
}
