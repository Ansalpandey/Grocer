package com.app.grocer.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.app.grocer.data.model.AddToCartRequest
import com.app.grocer.ui.components.ProductItem
import com.app.grocer.ui.navigation.Route
import com.app.grocer.ui.viewmodels.CategoryViewModel
import com.app.grocer.ui.viewmodels.ProductViewModel
import com.app.humaraapnabazaar.R

@Composable
fun HomeScreen(
  modifier: Modifier = Modifier,
  categoryViewModel: CategoryViewModel = hiltViewModel(),
  productViewModel: ProductViewModel = hiltViewModel(),
  navController: NavHostController,
) {
  val categories = categoryViewModel.categories.collectAsState()
  val products = productViewModel.products.collectAsLazyPagingItems()
  val context = LocalContext.current
  LaunchedEffect(key1 = products) { productViewModel.getProducts() }

  LazyColumn(modifier = Modifier.safeContentPadding()) {
    item {
      Row(
        modifier = Modifier.fillMaxWidth().padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
      ) {
        Text(
          "Welcome To Grocer",
          fontSize = MaterialTheme.typography.titleMedium.fontSize,
          fontWeight = FontWeight.ExtraBold,
        )
      }
      Spacer(Modifier.height(10.dp))
      Image(
        modifier =
          Modifier.fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp)
            .height(200.dp)
            .clip(RoundedCornerShape(20.dp)),
        painter = painterResource(R.drawable.discount_banner),
        contentScale = ContentScale.Crop,
        contentDescription = "discount_banner",
      )
      Spacer(Modifier.height(20.dp))
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
      ) {
        Text(
          "Categories",
          modifier = Modifier.padding(start = 20.dp),
          fontSize = MaterialTheme.typography.titleLarge.fontSize,
          fontWeight = FontWeight.Bold,
        )
      }

      // Categories Row
      categories.value.data?.let { categoryList ->
        LazyRow(
          modifier = Modifier.fillMaxWidth().padding(10.dp),
          horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
          items(categoryList) { category ->
            CategoryItem(
              categoriesResponseItem = category,
              onClick = {
                navController.navigate(Route.CategoryWiseProductScreen(category.name, category._id))
              },
            )
          }
        }
      }

      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement =
          Arrangement.SpaceBetween, // This will ensure spacing between the Text and Icon
      ) {
        Text(
          modifier = Modifier.padding(start = 20.dp), // Add padding only to the left
          text = "Featured Products",
          fontSize = MaterialTheme.typography.titleLarge.fontSize,
          fontWeight = FontWeight.Bold,
        )
        Icon(
          imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
          contentDescription = "arrow_forward",
          tint = Color.Gray,
          modifier =
            Modifier.size(24.dp).clickable(
              indication = null,
              interactionSource = remember { MutableInteractionSource() },
            ) { navController.navigate(Route.FeaturedProductsScreen) },
        )
      }
      Spacer(Modifier.height(10.dp))
      LazyRow {
        items(products) { product ->
          ProductItem(
            modifier =
              Modifier.clickable {
                navController.navigate(Route.ProductDetailsScreen(productId = product!!._id))
              },
            onClick = {
              navController.navigate(Route.ProductDetailsScreen(productId = product?._id!!))
            },
            product = product!!,
            onAddToCart = {
              productViewModel.addToCart(AddToCartRequest(productId = product._id, quantity = 1))
              Toast.makeText(context, "Product added to cart", Toast.LENGTH_SHORT).show()
            },
          )
        }
      }
    }
  }
}
