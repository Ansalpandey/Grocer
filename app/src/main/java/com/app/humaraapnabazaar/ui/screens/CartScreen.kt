package com.app.humaraapnabazaar.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
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
import com.app.humaraapnabazaar.data.model.CreateOrderRequest
import com.app.humaraapnabazaar.data.model.OrderItemX
import com.app.humaraapnabazaar.data.model.ShippingAddressX
import com.app.humaraapnabazaar.ui.components.CartItem
import com.app.humaraapnabazaar.ui.navigation.Route
import com.app.humaraapnabazaar.ui.viewmodels.ProductViewModel
@Composable
fun CartScreen(
  modifier: Modifier = Modifier,
  productViewModel: ProductViewModel = hiltViewModel(),
  navController: NavController,
) {
  val cartItems = productViewModel.cartItems.collectAsState()
  LaunchedEffect(key1 = cartItems) { productViewModel.getCartItems() }

  // Calculate the total cost and total savings
  val totalCost = cartItems.value.sumOf {
    val discountedPrice = it.product.price * (1 - it.product.discount / 100.0)
    discountedPrice * it.quantity
  }
  val totalSavings = cartItems.value.sumOf {
    val savingsPerItem = it.product.price - (it.product.price * (1 - it.product.discount / 100.0))
    savingsPerItem * it.quantity
  }

  Column(modifier = Modifier.fillMaxSize().safeContentPadding()) {
    // Header
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
      Spacer(modifier = Modifier.width(8.dp))
      Text(
        text = "Shopping Cart",
        style = MaterialTheme.typography.titleLarge,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.fillMaxWidth(),
      )
    }

    if (cartItems.value.isEmpty()) {
      Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
      ) {
        Image(painter = painterResource(R.drawable.cart), contentDescription = "cart")
        Text(
          text = "Your cart is empty!",
          style = MaterialTheme.typography.titleLarge,
          fontWeight = FontWeight.Bold,
        )
        Text(
          text = "Start shopping now!",
          style = MaterialTheme.typography.titleMedium,
          fontWeight = FontWeight.Bold,
        )

        Button(
          modifier = Modifier.fillMaxWidth().padding(20.dp),
          onClick = {
            navController.navigate(Route.MainScreen) {
              popUpTo(Route.MainScreen) { inclusive = true }
            }
          },
        ) {
          Text("Start Shopping")
        }
      }
    }

    // Cart Items
    LazyColumn(
      modifier =
      Modifier.weight(1f) // Allow the cart items to scroll if they exceed the screen height
        .fillMaxSize()
    ) {
      items(cartItems.value) {
        CartItem(
          cartResponseItem = it,
          onRemove = { productViewModel.removeProductFromCart(productId = it.product._id) },
          onClick = { navController.navigate(Route.ProductDetailsScreen(productId = it.product._id)) }
        )
      }
    }

    // Total Cost and Savings Section
    Column(
      modifier = Modifier.fillMaxWidth().padding(16.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      Row(
        modifier = Modifier.fillMaxWidth().padding(start = 20.dp, end = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
      ) {
        Text(
          text = "Total",
          style = MaterialTheme.typography.titleLarge,
          fontWeight = FontWeight.Bold,
        )
        Text(
          text = "₹${"%.2f".format(totalCost)}",
          style = MaterialTheme.typography.headlineLarge,
          fontWeight = FontWeight.ExtraBold,
        )
      }
      Row(
        modifier = Modifier.fillMaxWidth().padding(start = 20.dp, end = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
      ) {
        Text(
          text = "You Saved",
          style = MaterialTheme.typography.bodyLarge,
          fontWeight = FontWeight.Bold,
        )
        Text(
          text = "₹${"%.2f".format(totalSavings)}",
          color = Color(0xFF43A047), // Green for savings
          style = MaterialTheme.typography.titleMedium,
        )
      }
      Button(
        onClick = {
          val orderItems =
            cartItems.value.map { cartItem ->
              OrderItemX(
                product = cartItem.product._id, // Use the product ID
                quantity = cartItem.quantity,
              )
            }
          navController.navigate(
            Route.CreateOrderScreen(
              createOrderRequest =
              CreateOrderRequest(
                orderItems = orderItems,
                shippingAddress =
                ShippingAddressX(city = "", address = "", country = "", postalCode = ""),
                totalPrice = totalCost,
              )
            )
          )
        },
        modifier = Modifier.fillMaxWidth(),
      ) {
        Text("Checkout")
      }
    }
  }
}

