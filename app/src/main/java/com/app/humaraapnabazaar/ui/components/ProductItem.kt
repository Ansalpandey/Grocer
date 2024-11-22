package com.app.humaraapnabazaar.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.app.humaraapnabazaar.data.model.Product

@Composable
fun ProductItem(
  modifier: Modifier = Modifier,
  product: Product,
  onAddToCart: () -> Unit,
) {
  Spacer(Modifier.width(10.dp))
  Card(
    modifier = modifier.fillMaxSize().width(150.dp).height(320.dp),
    shape = RoundedCornerShape(16.dp),
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween, // Distribute the items across the row
    ) {
      Text(
        text = if (product.inStock) "In Stock" else "Out of Stock",
        color = if (product.inStock) MaterialTheme.colorScheme.primary else Color.Red,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(start = 10.dp).weight(1f),
      )
    }
    AsyncImage(
      model = product.productImage,
      contentDescription = "product_image",
      modifier =
        Modifier.width(150.dp)
          .height(150.dp)
          .padding(top = 5.dp, start = 10.dp, end = 10.dp)
          .clip(RoundedCornerShape(8.dp)),
      contentScale = ContentScale.Crop,
    )
    Text(
      product.name,
      modifier = Modifier.fillMaxWidth(),
      textAlign = TextAlign.Center,
      fontSize = MaterialTheme.typography.bodyMedium.fontSize,
      maxLines = 2,
      softWrap = true,
    )
    Text(
      "${product.price}",
      modifier = Modifier.fillMaxWidth(),
      textAlign = TextAlign.Center,
      fontSize = 16.sp,
    )
    Row(
      modifier = Modifier.fillMaxWidth(),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.Center,
    ) {
      Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = "add_to_cart")
      TextButton(onClick = { onAddToCart.invoke() }) { Text("Add to cart") }
    }
  }
  Spacer(Modifier.width(10.dp))
}
