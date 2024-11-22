package com.app.humaraapnabazaar.ui.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.app.humaraapnabazaar.data.model.AddToCartRequest
import com.app.humaraapnabazaar.ui.viewmodels.ProductViewModel
import com.gowtham.ratingbar.RatingBar

@Composable
fun ProductDetailScreen(
  modifier: Modifier = Modifier,
  productId: String,
  productViewModel: ProductViewModel = hiltViewModel(),
  navController: NavHostController,
) {
  val productDetails = productViewModel.productDetails.collectAsState()
  var quantity by remember { mutableIntStateOf(1) }

  LaunchedEffect(key1 = productId) { productViewModel.getProductDetails(productId) }

  Column(
    modifier = Modifier.fillMaxSize().safeContentPadding().verticalScroll(rememberScrollState())
  ) {
    // Back Button
    Icon(
      imageVector = Icons.Default.ArrowBackIosNew,
      contentDescription = "back",
      modifier = Modifier.clickable { navController.popBackStack() }.padding(16.dp),
      tint = MaterialTheme.colorScheme.primary,
    )
    Spacer(Modifier.height(10.dp))

    // Product Details
    if (productDetails.value != null) {
      val product = productDetails.value!!

      // Product Image
      AsyncImage(
        model = product.productImage,
        contentDescription = "product_image",
        contentScale = ContentScale.FillWidth,
        modifier =
          Modifier.fillMaxWidth()
            .height(300.dp)
            .clip(RoundedCornerShape(10.dp))
            .padding(horizontal = 16.dp),
      )

      // Product Price
      Text(
        text = "Rs. ${product.price}",
        fontSize = MaterialTheme.typography.titleLarge.fontSize,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.fillMaxWidth().padding(top = 20.dp, start = 16.dp),
        fontWeight = FontWeight.ExtraBold,
      )

      // Product Name
      Text(
        text = product.name,
        modifier = Modifier.fillMaxWidth().padding(start = 16.dp, top = 8.dp),
        fontSize = MaterialTheme.typography.headlineMedium.fontSize,
        fontWeight = FontWeight.Bold,
      )
      Spacer(Modifier.height(10.dp))

      // Product Rating
      var rating by remember { mutableFloatStateOf(product.rating) }
      RatingBar(
        modifier = Modifier.padding(start = 16.dp),
        value = rating,
        onValueChange = { rating = it },
        onRatingChanged = { Log.d("TAG", "onRatingChanged: $it") },
      )

      // Product Description
      Text(
        text = product.description,
        modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp, top = 16.dp),
      )

      Spacer(Modifier.height(16.dp))

      // Quantity Selector
      Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
      ) {
        Text(
          text = "Quantity:",
          fontWeight = FontWeight.SemiBold,
          fontSize = MaterialTheme.typography.bodyLarge.fontSize,
        )
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween,
        ) {
          IconButton(onClick = { if (quantity > 1) quantity-- }, modifier = Modifier.size(40.dp)) {
            Icon(
              imageVector = Icons.Default.Remove,
              contentDescription = "Decrease Quantity",
              tint = MaterialTheme.colorScheme.primary,
            )
          }
          Text(
            text = quantity.toString(),
            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 8.dp),
          )
          IconButton(onClick = { quantity++ }, modifier = Modifier.size(40.dp)) {
            Icon(
              imageVector = Icons.Default.Add,
              contentDescription = "Increase Quantity",
              tint = MaterialTheme.colorScheme.primary,
            )
          }
        }
      }

      Spacer(Modifier.height(20.dp))

      // Add to Cart Button
      Button(
        modifier =
          Modifier.fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(50.dp)
            .clip(RoundedCornerShape(8.dp)),
        onClick = {
          productViewModel.addToCart(
            AddToCartRequest(
              productId = product._id,
              quantity = quantity,
            )
          )
        },
        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
      ) {
        Text(
          text = "Add to Cart",
          fontSize = MaterialTheme.typography.bodyLarge.fontSize,
          color = Color.White,
        )
      }
    } else {
      // Loading State
      Text(
        text = "Loading...",
        modifier = Modifier.fillMaxWidth().padding(top = 50.dp),
        textAlign = TextAlign.Center,
      )
    }
  }
}
