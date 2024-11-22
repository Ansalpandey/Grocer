package com.app.humaraapnabazaar.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.app.humaraapnabazaar.data.model.OrderResponseItem
import com.app.humaraapnabazaar.ui.viewmodels.ProductViewModel
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

@Composable
fun OrdersScreen(
  modifier: Modifier = Modifier,
  productViewModel: ProductViewModel = hiltViewModel(),
  navController: NavController,
) {
  val orders = productViewModel.orders.collectAsState().value

  LaunchedEffect(key1 = orders) {
    productViewModel.getOrders() // Load orders when the screen is first composed
  }

  Column(modifier = Modifier.fillMaxSize().safeContentPadding()) {
    // Header
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
      Icon(
        imageVector = Icons.AutoMirrored.Filled.ArrowBackIos,
        contentDescription = "back",
        modifier = Modifier.clickable { navController.popBackStack() },
      )
      Spacer(modifier = Modifier.width(8.dp))
      Text(
        text = "Your Orders",
        style = MaterialTheme.typography.titleLarge,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.fillMaxWidth(),
      )
    }

    if (orders.isEmpty()) {
      Text("No orders found.", style = MaterialTheme.typography.bodyLarge)
    } else {
      Spacer(modifier = Modifier.height(8.dp))
      LazyColumn { items(orders) { order -> OrderItem(order = order) } }
    }
  }
}

@Composable
fun OrderItem(order: OrderResponseItem) {
  // State to manage collapsed/expanded state of each order
  var isExpanded by remember { mutableStateOf(false) }

  // Format the createdAt date to "Date - Day" format
  val formattedDate = getRelativeTimeSpanString(order.createdAt)

  Card(
    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
    shape = RoundedCornerShape(8.dp),
  ) {
    Column(modifier = Modifier.padding(16.dp)) {
      // Order header (collapsed view)
      Row(
        modifier = Modifier.fillMaxWidth().clickable { isExpanded = !isExpanded },
        verticalAlignment = Alignment.CenterVertically,
      ) {
        // Order image, name, and price in the collapsed state
        AsyncImage(
          model = order.orderItems.firstOrNull()?.product?.productImage,
          contentDescription = "Product Image",
          modifier = Modifier.size(100.dp).clip(RoundedCornerShape(8.dp)),
          contentScale = ContentScale.Crop,
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
          val truncatedOrderId = order._id.take(7)
          Text(text = "Order ID: #$truncatedOrderId", style = MaterialTheme.typography.bodyMedium)
          val productName = order.orderItems.firstOrNull()?.product?.name ?: "Unknown Product"
          Text(text = "Product: $productName", style = MaterialTheme.typography.bodyMedium)
          // Show formatted date
          Text(text = "Placed on $formattedDate", style = MaterialTheme.typography.bodySmall)
        }
        Spacer(modifier = Modifier.weight(1f))

        // Show expand/collapse icon
        Icon(
          imageVector =
            if (isExpanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
          contentDescription = "Expand/Collapse",
        )
      }

      // Show the order status steps and additional details only when expanded
      AnimatedVisibility(visible = isExpanded) {
        Column(modifier = Modifier.padding(top = 8.dp)) {
          // Order steps (Order placed, confirmed, shipped, out for delivery, delivered)
          OrderSteps(order)

          Text(
            text = "Total: ₹${order.totalPrice}",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 8.dp),
          )
        }
      }
    }
  }
}

fun getRelativeTimeSpanString(dateString: String): String {
  val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
  sdf.timeZone = TimeZone.getTimeZone("UTC") // Set the timezone to UTC
  val date = sdf.parse(dateString)

  date?.let {
    // Convert UTC time to local time (Indian time)
    val localSdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault())
    localSdf.timeZone = TimeZone.getDefault() // Use default system time zone (Indian Standard Time)
    val localDateString = localSdf.format(date)
    val localDate = localSdf.parse(localDateString)

    localDate?.let {
      // Indian date format: dd MMM yyyy (e.g., 20 Nov 2024)
      val indianDateFormat = SimpleDateFormat("dd MMM yyyy", Locale("en", "IN"))
      return indianDateFormat.format(localDate)
    }
  }
  return "Unknown time"
}

@Composable
fun OrderSteps(order: OrderResponseItem) {
  // List of status steps from the API response
  val steps = order.status
  val currentStepIndex = steps.indexOfLast { it != "Order Placed" } // Find the current step

  Column(modifier = Modifier.padding(bottom = 8.dp)) {
    steps.forEachIndexed { index, step ->
      Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
        // Circle/Indicator for the step
        Box(
          modifier =
            Modifier.size(12.dp)
              .background(
                color =
                  when {
                    index == currentStepIndex -> Color.Green // Bold current step
                    else -> Color.Gray
                  },
                shape = CircleShape,
              )
        )

        // Line connecting the steps
        if (index < steps.size - 1) {
          Box(
            modifier =
              Modifier.weight(1f).height(2.dp).background(color = Color.Gray) // Line between steps
          )
        }

        // Step text
        Text(
          text = step,
          style =
            MaterialTheme.typography.bodySmall.copy(
              fontWeight = if (index == currentStepIndex) FontWeight.Bold else FontWeight.Normal
            ),
          color =
            when (index) {
              0 -> Color.Gray // Order Placed (not yet confirmed)
              1 -> Color.Blue // Order Confirmed
              2 -> Color.Cyan // Order Shipped
              3 -> Color.Yellow // Out for Delivery
              else -> if (order.isDelivered) Color.Green else Color.Red // Delivered
            },
          modifier = Modifier.padding(start = 8.dp),
        )
      }

      // Spacer between steps
      if (index < steps.size - 1) {
        Spacer(modifier = Modifier.height(8.dp))
      }
    }
  }
}
