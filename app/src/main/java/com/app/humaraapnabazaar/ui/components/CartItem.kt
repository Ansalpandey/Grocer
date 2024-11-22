package com.app.humaraapnabazaar.ui.components

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.app.humaraapnabazaar.data.model.CartResponseItem

@Composable
fun CartItem(
  modifier: Modifier = Modifier,
  cartResponseItem: CartResponseItem,
  onRemove: () -> Unit // A callback to remove the item
) {
  Card(modifier = modifier.padding(10.dp)) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp),
      verticalAlignment = Alignment.CenterVertically,
    ) {
      // Product Image
      AsyncImage(
        model = cartResponseItem.product.productImage,
        contentDescription = "product image",
        modifier = Modifier
          .size(100.dp)
          .clip(RoundedCornerShape(18.dp)),
      )
      Spacer(Modifier.width(10.dp))
      Column(
        modifier = Modifier.weight(1f) // To ensure the column takes the remaining space
      ) {
        // Price and Quantity Text
        Text(
          "Rs. " + cartResponseItem.product.price.toString() + "x" + cartResponseItem.quantity,
          color = MaterialTheme.colorScheme.primary,
        )
        Text(cartResponseItem.product.name)
      }
      // Remove Button
      Button(
        onClick = { onRemove.invoke() },
        modifier = Modifier.align(Alignment.CenterVertically),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
      ) {
        Text("Remove")
      }
    }
  }
}

