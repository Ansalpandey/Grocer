package com.app.humaraapnabazaar.ui.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.app.humaraapnabazaar.data.model.AddToCartRequest
import com.app.humaraapnabazaar.ui.components.ProductItem
import com.app.humaraapnabazaar.ui.navigation.Route
import com.app.humaraapnabazaar.ui.viewmodels.ProductViewModel

@Composable
fun FeaturedProductsScreen(
  modifier: Modifier = Modifier,
  navController: NavController,
  productViewModel: ProductViewModel = hiltViewModel(),
) {
  val context = LocalContext.current
  val products = productViewModel.products.collectAsLazyPagingItems()

  LaunchedEffect(key1 = products) { productViewModel.getProducts() }
  Column(modifier.fillMaxSize().safeContentPadding().verticalScroll(rememberScrollState())) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
      Icon(
        imageVector = Icons.AutoMirrored.Filled.ArrowBackIos,
        contentDescription = "back",
        modifier = Modifier.padding(start = 20.dp).clickable(
          indication = null,
          interactionSource = remember { MutableInteractionSource() },
        ) { navController.popBackStack() },
      )
      Text(
        text = "Featured Products",
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        maxLines = 1,
        softWrap = true,
        fontSize = MaterialTheme.typography.titleLarge.fontSize,
        fontWeight = FontWeight.Bold,
      )
    }
    LazyVerticalGrid(
      columns = GridCells.Fixed(2),
      modifier = Modifier.weight(1f).fillMaxSize(),
      contentPadding = PaddingValues(16.dp),
    ) {
      items(products.itemCount) { index ->
        val product = products[index]
        if (product != null) {
          ProductItem(
            modifier = Modifier.padding(10.dp),
            product = product,
            onClick = {
              navController.navigate(Route.ProductDetailsScreen(productId = product._id))
            },
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
