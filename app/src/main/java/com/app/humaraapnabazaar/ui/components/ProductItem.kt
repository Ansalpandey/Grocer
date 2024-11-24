package com.app.humaraapnabazaar.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.app.humaraapnabazaar.data.model.Product

@Composable
fun ProductItem(
  modifier: Modifier = Modifier,
  product: Product,
  onAddToCart: (Int) -> Unit,
  onClick: () -> Unit,
) {
  var cartCount by remember { mutableIntStateOf(0) }

  val discountedPrice = product.price * (1 - product.discount / 100.0)

  Card(
    modifier =
      modifier.width(160.dp).height(320.dp).padding(8.dp).clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() },
      ) {
        onClick.invoke()
      },
    shape = RoundedCornerShape(16.dp),
  ) {
    Column(
      modifier = Modifier.fillMaxSize(),
      verticalArrangement = Arrangement.SpaceBetween,
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      // Top section with discount badge and stock status
      Row(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
      ) {
        // Stock status
        Text(
          text = if (product.inStock) "In Stock" else "Out of Stock",
          color = if (product.inStock) MaterialTheme.colorScheme.primary else Color.Red,
          fontSize = 12.sp,
          fontWeight = FontWeight.Bold,
        )

        // Discount badge
        if (product.discount > 0) {
          Text(
            text = "${product.discount}% OFF",
            color = Color.White,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            modifier =
              Modifier.background(MaterialTheme.colorScheme.primary, RoundedCornerShape(4.dp))
                .padding(horizontal = 6.dp, vertical = 2.dp),
          )
        }
      }

      // Product image
      Box(
        modifier = Modifier.fillMaxWidth().weight(1f).padding(8.dp).clip(RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center,
      ) {
        AsyncImage(
          model = product.productImage,
          contentDescription = "product_image",
          modifier = Modifier.fillMaxSize().aspectRatio(1f),
          contentScale = ContentScale.Crop,
        )
      }

      // Product name
      Text(
        text = product.name,
        textAlign = TextAlign.Center,
        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
        maxLines = 2,
        softWrap = true,
        modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
      )

      // Product price with discount
      Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
      ) {
        if (product.discount > 0) {
          Text(
            text = "₹${product.price}",
            textAlign = TextAlign.Center,
            fontSize = 14.sp,
            color = Color.Gray,
            textDecoration = TextDecoration.LineThrough,
            modifier = Modifier.padding(vertical = 2.dp),
          )
        }
        Text(
          text = "₹${"%.2f".format(discountedPrice)}",
          textAlign = TextAlign.Center,
          fontSize = 16.sp,
          fontWeight = FontWeight.Bold,
          modifier = Modifier.padding(vertical = 4.dp),
        )
      }

      // Add to cart or increment/decrement buttons
      Row(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
      ) {
        if (cartCount > 0) {
          IconButton(
            onClick = {
              cartCount = (cartCount - 1).coerceAtLeast(0)
              onAddToCart(cartCount)
            }
          ) {
            Icon(imageVector = Icons.Default.Remove, contentDescription = "decrement")
          }
          Text(
            text = cartCount.toString(),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 8.dp),
          )
          IconButton(
            onClick = {
              cartCount++
              onAddToCart(cartCount)
            }
          ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "increment")
          }
        } else {
          TextButton(
            onClick = {
              cartCount = 1
              onAddToCart(cartCount)
            }
          ) {
            Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = "add_to_cart")
            Text("Add to cart")
          }
        }
      }
    }
  }
}
