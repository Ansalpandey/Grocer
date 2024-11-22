package com.app.humaraapnabazaar.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults.InputField
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.app.humaraapnabazaar.data.model.AddToCartRequest
import com.app.humaraapnabazaar.ui.components.SearchItem
import com.app.humaraapnabazaar.ui.navigation.Route
import com.app.humaraapnabazaar.ui.viewmodels.ProductViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
  modifier: Modifier = Modifier,
  productViewModel: ProductViewModel = hiltViewModel(),
  navController: NavHostController,
) {
  var query by remember { mutableStateOf("") }
  val products = productViewModel.searchedProducts.collectAsState()

  // Trigger the search query only if it's not empty
  LaunchedEffect(key1 = query) {
    if (query.isNotEmpty()) {
      delay(1000) // Debounce to avoid excessive API calls
      productViewModel.searchProducts(query)
    }
  }

  Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
    // Search Bar
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {

      SearchBar(
        inputField = {
          InputField(
            query = query,
            onQueryChange = { query = it },
            onSearch = {},
            expanded = false,
            onExpandedChange = {},
            placeholder = { Text(text = "Search keywords...") },
          )
        },
        expanded = false,
        onExpandedChange = {},
      ) {}
    }

    Spacer(modifier = Modifier.height(16.dp)) // Add spacing below the search bar

    // Product List or Fallback UI
    if (products.value?.products.isNullOrEmpty() && query.isNotEmpty()) {
      // Fallback message when no products are available
      Text(
        text = "No products found for \"$query\".",
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.padding(16.dp),
      )
    } else if (products.value?.products.isNullOrEmpty()) {
      // Fallback message for empty query
      Text(
        text = "Start typing to search for products",
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.padding(16.dp),
      )
    } else {
      LazyColumn(
        modifier = Modifier.fillMaxSize(),
      ) {
        items(products.value?.products!!) {
          SearchItem(searchResponse = it, modifier = Modifier.fillMaxWidth().clickable {
            navController.navigate(Route.ProductDetailsScreen(it._id).toString())
          }) {
            productViewModel.addToCart(AddToCartRequest(it._id, quantity = 1))
          }
        }
      }
    }
  }
}
