package com.app.humaraapnabazaar.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.app.humaraapnabazaar.data.model.CartResponseItem

@Composable
fun CartItem(
  modifier: Modifier = Modifier,
  cartResponseItem: CartResponseItem,
  onRemove: () -> Unit,
  onClick: () -> Unit,
) {
  Card(
    modifier =
      modifier.padding(10.dp).clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() },
      ) {
        onClick.invoke()
      }
  ) {
    Row(
      modifier = Modifier.fillMaxWidth().padding(10.dp),
      verticalAlignment = Alignment.CenterVertically,
    ) {
      Box {
        // Product Image with Discount Badge
        AsyncImage(
          model = cartResponseItem.product.productImage,
          contentDescription = "product image",
          modifier = Modifier.size(100.dp).clip(RoundedCornerShape(18.dp)),
        )
        if (cartResponseItem.product.discount > 0) {
          Box(
            modifier =
              Modifier.align(Alignment.TopEnd)
                .padding(4.dp)
                .background(Color.Red, shape = RoundedCornerShape(4.dp))
                .padding(horizontal = 6.dp, vertical = 2.dp)
          ) {
            Text(
              text = "-${cartResponseItem.product.discount}%",
              color = Color.White,
              fontWeight = FontWeight.Bold,
              fontSize = 12.sp,
            )
          }
        }
      }
      Spacer(Modifier.width(10.dp))
      Column(modifier = Modifier.weight(1f)) {
        // Product Name
        Text(
          cartResponseItem.product.name,
          maxLines = 2,
          softWrap = true,
          fontWeight = FontWeight.Bold,
        )

        // Calculate Discounted Price and Savings
        val discountedPrice =
          cartResponseItem.product.price * (1 - cartResponseItem.product.discount / 100.0)
        val savingsPerItem = cartResponseItem.product.price - discountedPrice
        val totalSavings = savingsPerItem * cartResponseItem.quantity

        // Original Price with Strikethrough and Discounted Price
        Row(verticalAlignment = Alignment.CenterVertically) {
          if (cartResponseItem.product.discount > 0) {
            Text(
              text = "₹${cartResponseItem.product.price}",
              style =
                MaterialTheme.typography.bodyMedium.copy(
                  textDecoration = TextDecoration.LineThrough
                ),
              color = Color.Gray,
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "₹${"%.2f".format(discountedPrice)}",
              style = MaterialTheme.typography.bodyLarge,
              color = MaterialTheme.colorScheme.primary,
              fontWeight = FontWeight.Bold,
            )
          } else {
            // If there's no discount, just show the original price without strikethrough
            Text(
              text = "₹${cartResponseItem.product.price}",
              style = MaterialTheme.typography.bodyLarge,
              color = MaterialTheme.colorScheme.primary,
              fontWeight = FontWeight.Bold,
            )
          }
        }

        // Quantity
        Text("Quantity: ${cartResponseItem.quantity}", style = MaterialTheme.typography.bodyMedium)

        // Show Savings Only if > 0
        if (totalSavings > 0) {
          Text(
            text = "You save: ₹${"%.2f".format(totalSavings)}",
            color = Color(0xFF7DC270), // Green for savings
            fontWeight = FontWeight.Medium,
          )
        }
      }
      // Remove Button
      Button(
        onClick = { onRemove.invoke() },
        modifier = Modifier.align(Alignment.CenterVertically),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
      ) {
        Text("Remove")
      }
    }
  }
}
