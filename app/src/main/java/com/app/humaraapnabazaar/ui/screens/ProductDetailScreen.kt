package com.app.humaraapnabazaar.ui.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

  Box(modifier = Modifier.fillMaxSize().safeContentPadding()) {
    Column(
      modifier =
        Modifier.fillMaxSize()
          .verticalScroll(rememberScrollState())
          .padding(bottom = 80.dp) // This ensures the button is not hidden by the scroll
    ) {
      // Back Button
      Icon(
        imageVector = Icons.Default.ArrowBackIosNew,
        contentDescription = "back",
        modifier = Modifier.clickable { navController.popBackStack() }.padding(10.dp),
        tint = MaterialTheme.colorScheme.primary,
      )

      Spacer(Modifier.height(12.dp))

      // Product Details
      if (productDetails.value != null) {
        val product = productDetails.value!!

        // Calculate Discounted Price and Savings
        val discountedPrice = product.price * (1 - product.discount / 100.0)
        val savingsPerItem = product.price - discountedPrice
        val totalSavings = savingsPerItem * quantity

        // Product Image with Parallax Effect
        Box(modifier = Modifier.fillMaxWidth()) {
          AsyncImage(
            model = product.productImage,
            contentDescription = "product_image",
            contentScale = ContentScale.Crop,
            modifier =
              Modifier.fillMaxWidth().height(350.dp).clip(RoundedCornerShape(10.dp)).graphicsLayer {
                alpha = 1f
                translationY = -100f // Simulate a parallax effect
              },
          )
        }

        // Price Section with Discount Logic
        if (product.discount > 0) {
          Row(
            modifier = Modifier.fillMaxWidth().padding(top = 20.dp, start = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
          ) {
            Text(
              text = "₹${("%.2f".format(discountedPrice))}",
              fontSize = 30.sp,
              color = MaterialTheme.colorScheme.primary,
              fontWeight = FontWeight.ExtraBold,
            )

            Spacer(Modifier.width(10.dp))

            Text(
              text = "₹${("%.2f".format(product.price))}",
              fontSize = 20.sp,
              color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
              fontWeight = FontWeight.Normal,
              modifier = Modifier.padding(top = 4.dp),
              style = TextStyle(textDecoration = TextDecoration.LineThrough),
            )

            Spacer(Modifier.width(10.dp))

            // Money Saved (Total Savings)
            Text(
              text = "Save ₹${("%.2f".format(totalSavings))}",
              fontSize = 18.sp,
              color = MaterialTheme.colorScheme.secondary,
              fontWeight = FontWeight.SemiBold,
            )
          }
        } else {
          // If No Discount
          Text(
            text = "₹${("%.2f".format(product.price))}",
            fontSize = 30.sp,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.fillMaxWidth().padding(top = 20.dp, start = 16.dp),
            fontWeight = FontWeight.ExtraBold,
          )
        }

        // Product Name
        Text(
          text = product.name,
          modifier = Modifier.fillMaxWidth().padding(start = 16.dp, top = 8.dp),
          fontSize = 22.sp,
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.onSurface,
        )

        Spacer(Modifier.height(8.dp))

        // Product Rating
        var rating by remember { mutableFloatStateOf(product.rating) }
        RatingBar(
          modifier = Modifier.padding(start = 16.dp).fillMaxWidth(),
          value = rating,
          onValueChange = { rating = it },
          onRatingChanged = { Log.d("TAG", "onRatingChanged: $it") },
        )

        Spacer(Modifier.height(10.dp))

        // Product Description
        Text(
          text = product.description,
          modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp, top = 16.dp),
          style = MaterialTheme.typography.bodyMedium,
        )

        Spacer(Modifier.height(20.dp))

        // Quantity Selector with Custom Styling
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
            IconButton(
              onClick = { if (quantity > 1) quantity-- },
              modifier = Modifier.size(50.dp),
            ) {
              Icon(
                imageVector = Icons.Default.Remove,
                contentDescription = "Decrease Quantity",
                tint = MaterialTheme.colorScheme.primary,
              )
            }

            Text(
              text = quantity.toString(),
              fontSize = 18.sp,
              fontWeight = FontWeight.Bold,
              modifier = Modifier.padding(horizontal = 16.dp),
            )

            IconButton(onClick = { quantity++ }, modifier = Modifier.size(50.dp)) {
              Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Increase Quantity",
                tint = MaterialTheme.colorScheme.primary,
              )
            }
          }
        }

        Spacer(Modifier.height(20.dp))
      } else {
        // Loading State
        Box(
          modifier = Modifier.fillMaxSize().padding(top = 50.dp),
          contentAlignment = Alignment.Center,
        ) {
          CircularProgressIndicator(
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(48.dp),
          )
        }
      }
    }

    if (productDetails.value?.inStock == true) {
      Button(
        modifier =
          Modifier.align(Alignment.BottomCenter)
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(50.dp)
            .clip(RoundedCornerShape(12.dp)),
        onClick = {
          val product = productDetails.value
          if (product != null) {
            productViewModel.addToCart(
              AddToCartRequest(productId = product._id, quantity = quantity)
            )
          }
        },
        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
        contentPadding = PaddingValues(8.dp),
      ) {
        Text(text = "Add to Cart", fontSize = 18.sp, color = Color.White)
      }
    } else {
      Button(
        onClick = {},
        modifier =
          Modifier.align(Alignment.BottomCenter)
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(50.dp),
        enabled = false,
        colors = ButtonDefaults.buttonColors(
          disabledContainerColor = Color.Red,
          disabledContentColor = Color.White,
        ),
        contentPadding = PaddingValues(8.dp),
      ) {
        Text(text = "Unavailable")
      }
    }
  }
}
