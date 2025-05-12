package com.app.grocer.ui.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.app.grocer.data.model.AddToCartRequest
import com.app.grocer.ui.components.ProductItem
import com.app.grocer.ui.navigation.Route
import com.app.grocer.ui.viewmodels.ProductViewModel
import com.app.humaraapnabazaar.R

@Composable
fun CategoryWiseProductsScreen(
    modifier: Modifier = Modifier,
    categoryName: String,
    categoryId: String,
    navController: NavHostController,
    productViewModel: ProductViewModel = hiltViewModel(),
) {
    // State to manage the visibility of the dialog
    var isDialogOpen by remember { mutableStateOf(false) }
    var minPrice by remember { mutableStateOf("") }
    var maxPrice by remember { mutableStateOf("") }

    // Get the paged data from the ViewModel
    val productsByCategory = productViewModel.productsByCategory.collectAsLazyPagingItems()
    val context = LocalContext.current

    LaunchedEffect(key1 = productsByCategory) { productViewModel.getProductsByCategory(categoryName) }

    Column(modifier = modifier
      .fillMaxSize()
      .safeContentPadding()) {
        // Header with category name and back button
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBackIos,
                contentDescription = "back",
                modifier = Modifier
                  .padding(start = 20.dp)
                  .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                  ) { navController.popBackStack() },
            )
            Text(
                text = categoryName,
                textAlign = TextAlign.Center,
                maxLines = 1,
                softWrap = true,
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                fontWeight = FontWeight.Bold,
            )

            Icon(
                painter = painterResource(id = R.drawable.filter),
                modifier = Modifier
                  .padding(end = 20.dp)
                  .size(24.dp)
                  .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                  ) { isDialogOpen = true },
                contentDescription = "filter",
            )
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(productsByCategory.itemCount) { index ->
                val product = productsByCategory[index]
                if (product != null) {
                    ProductItem(
                        product = product,
                        onClick = {
                            navController.navigate(Route.ProductDetailsScreen(productId = product._id))
                        },
                        onAddToCart = {
                            productViewModel.addToCart(
                                AddToCartRequest(
                                    productId = product._id,
                                    quantity = 1
                                )
                            )
                            Toast.makeText(context, "Product added to cart", Toast.LENGTH_SHORT)
                                .show()
                        },
                    )
                }
            }
        }

        // Price Filter Dialog
        if (isDialogOpen) {
            Dialog(onDismissRequest = { isDialogOpen = false }) {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = MaterialTheme.colorScheme.surface,
                    modifier = Modifier.padding(16.dp),
                ) {
                    Column(
                        modifier = Modifier
                          .padding(16.dp)
                          .fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        Text(
                            text = "Filter by Price",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                        )

                        // Input fields for min and max price
                        OutlinedTextField(
                            value = minPrice,
                            onValueChange = { minPrice = it },
                            label = { Text("Min Price") },
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                            modifier = Modifier.fillMaxWidth(),
                        )
                        OutlinedTextField(
                            value = maxPrice,
                            onValueChange = { maxPrice = it },
                            label = { Text("Max Price") },
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                            modifier = Modifier.fillMaxWidth(),
                        )

                        // Buttons for applying and canceling
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            TextButton(onClick = { isDialogOpen = false }) { Text("Cancel") }
                            TextButton(
                                onClick = {
                                    isDialogOpen = false
                                    val min = minPrice.toIntOrNull() ?: 0
                                    val max = maxPrice.toIntOrNull() ?: Int.MAX_VALUE
                                    productViewModel.getProductsByPriceRange(
                                        minPrice = min.toDouble(),
                                        maxPrice = max.toDouble(),
                                        category = categoryId,
                                    )
                                }
                            ) {
                                Text("Apply")
                            }
                        }
                    }
                }
            }
        }
    }
}
