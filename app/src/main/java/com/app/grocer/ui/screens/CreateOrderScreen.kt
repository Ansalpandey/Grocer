package com.app.grocer.ui.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.app.grocer.data.model.CreateOrderRequest
import com.app.grocer.data.model.OrderItemX
import com.app.grocer.data.model.ShippingAddressX
import com.app.grocer.ui.viewmodels.ProductViewModel

@Composable
fun CreateOrderScreen(
  modifier: Modifier = Modifier,
  navController: NavHostController,
  createOrderRequest: CreateOrderRequest,
  productViewModel: ProductViewModel = hiltViewModel(),
) {
  // State variables for shipping address
  var address by remember { mutableStateOf(createOrderRequest.shippingAddress.address ?: "") }
  var city by remember { mutableStateOf(createOrderRequest.shippingAddress.city ?: "") }
  var country by remember { mutableStateOf(createOrderRequest.shippingAddress.country ?: "") }
  var postalCode by remember { mutableStateOf(createOrderRequest.shippingAddress.postalCode ?: "") }

  // Handle the UI elements and their state
  Column(modifier = modifier.fillMaxSize().safeContentPadding()) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
      Icon(
        imageVector = Icons.Default.ArrowBackIosNew,
        contentDescription = "back",
        modifier =
          Modifier.clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() },
          ) {
            navController.popBackStack()
          },
      )
      Text(
        modifier = Modifier.fillMaxWidth(),
        text = "Order Details",
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
      )
    }
    // Order ID Section
    Spacer(Modifier.height(10.dp))
    Text(
      text = "Order ID: ${createOrderRequest.orderItems.firstOrNull()?.product ?: "N/A"}",
      style = MaterialTheme.typography.titleMedium,
      modifier = Modifier.padding(start = 10.dp),
    )
    Spacer(Modifier.height(50.dp))
    // Order Items Section
    Text(
      text = "Items in your Order",
      style = MaterialTheme.typography.headlineSmall,
      fontWeight = FontWeight.Bold,
      modifier = Modifier.padding(start = 10.dp),
    )
    LazyColumn(modifier = Modifier.fillMaxHeight().weight(1f)) {
      items(createOrderRequest.orderItems) { orderItem -> OrderItemRow(orderItem = orderItem) }
    }

    // Shipping Address Section
    Text(
      text = "Shipping Address",
      style = MaterialTheme.typography.headlineSmall,
      fontWeight = FontWeight.Bold,
      modifier = Modifier.padding(start = 10.dp, bottom = 8.dp),
    )

    // Text Fields for Shipping Address
    TextField(
      value = address,
      onValueChange = { address = it },
      label = { Text("Address") },
      modifier = Modifier.fillMaxWidth().padding(8.dp),
    )
    TextField(
      value = city,
      onValueChange = { city = it },
      label = { Text("City") },
      modifier = Modifier.fillMaxWidth().padding(8.dp),
    )
    TextField(
      value = country,
      onValueChange = { country = it },
      label = { Text("Country") },
      modifier = Modifier.fillMaxWidth().padding(8.dp),
    )
    TextField(
      value = postalCode,
      onValueChange = { postalCode = it },
      label = { Text("Postal Code") },
      modifier = Modifier.fillMaxWidth().padding(8.dp),
    )

    Spacer(modifier = Modifier.height(16.dp))

    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically,
    ) {
      Text(
        text = "Total Amount:",
        modifier = Modifier.padding(start = 10.dp),
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
      )
      Text(
        text = "Rs. ${"%.2f".format(createOrderRequest.totalPrice)}",
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.ExtraBold,
        modifier = Modifier.padding(end = 10.dp),
        color = MaterialTheme.colorScheme.primary,
      )
    }

    Spacer(modifier = Modifier.height(24.dp))

    Button(
      onClick = {
        if (city.isEmpty() || address.isEmpty() || country.isEmpty() || postalCode.isEmpty()) {
          Toast.makeText(navController.context, "Please fill in all fields", Toast.LENGTH_SHORT)
            .show()
          return@Button
        }
        val updatedShippingAddress =
          ShippingAddressX(
            city = city,
            address = address,
            country = country,
            postalCode = postalCode,
          )

        // Map the order items to send each product's quantity
        val updatedOrderItems =
          createOrderRequest.orderItems.map { orderItem ->
            OrderItemX(
              product = orderItem.product, // Send the product ID
              quantity = orderItem.quantity, // Send the quantity for each item
            )
          }

        // Create an updated order request with the new shipping address and updated order items
        val updatedOrderRequest =
          createOrderRequest.copy(
            shippingAddress = updatedShippingAddress,
            orderItems = updatedOrderItems,
          )

        // Send the updated order request to create the order
        productViewModel.createOrder(updatedOrderRequest)

        // Show a confirmation message
        Toast.makeText(navController.context, "Order Placed!", Toast.LENGTH_SHORT).show().also {
          navController.popBackStack()
        }
      },
      modifier = Modifier.fillMaxWidth().padding(start = 20.dp, end = 20.dp),
    ) {
      Text("Place Order")
    }
  }
}

@Composable
fun OrderItemRow(orderItem: OrderItemX) {
  Row(
    modifier = Modifier.fillMaxWidth().padding(start = 10.dp),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    // You can add product image here if available, currently just displaying the product name
    Text(
      text = "Product: ID ${orderItem.product.take(7)}",
      style = MaterialTheme.typography.titleLarge,
      modifier = Modifier.weight(1f),
    )
    Text(
      text = "Quantity: ${orderItem.quantity}",
      style = MaterialTheme.typography.titleLarge,
      modifier = Modifier.padding(end = 10.dp),
    )
  }
}
