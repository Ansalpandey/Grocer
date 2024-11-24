package com.app.humaraapnabazaar.data.datasource

import com.app.humaraapnabazaar.data.api.AuthenticatedApiService
import com.app.humaraapnabazaar.data.model.AddToCartRequest
import com.app.humaraapnabazaar.data.model.AddToCartResponse
import com.app.humaraapnabazaar.data.model.AddToFavoriteResponse
import com.app.humaraapnabazaar.data.model.CartResponseItem
import com.app.humaraapnabazaar.data.model.CreateOrderRequest
import com.app.humaraapnabazaar.data.model.CreateOrderResponse
import com.app.humaraapnabazaar.data.model.OrderResponseItem
import com.app.humaraapnabazaar.data.model.Product
import com.app.humaraapnabazaar.data.model.ProductByCategoryResponse
import com.app.humaraapnabazaar.data.model.ProductsResponse
import com.app.humaraapnabazaar.data.model.SearchResponse
import retrofit2.Response
import javax.inject.Inject

class ProductDataSource
@Inject
constructor(private val authenticatedApiService: AuthenticatedApiService) {
  suspend fun getProducts(page: Int, limit: Int): Response<ProductsResponse> {
    return authenticatedApiService.getTopProducts(page = page, limit = limit)
  }

  suspend fun getProductsByCategory(
    page: Int,
    limit: Int,
    category: String,
  ): Response<ProductByCategoryResponse> {
    return authenticatedApiService.getProductsByCategory(
      page = page,
      limit = limit,
      category = category,
    )
  }

  suspend fun addToCart(addToCartRequest: AddToCartRequest): Response<AddToCartResponse> {
    return authenticatedApiService.addToCart(addToCartRequest)
  }

  suspend fun getCartItems(): Response<List<CartResponseItem>> {
    return authenticatedApiService.getCartItems()
  }

  suspend fun getProductDetails(productId: String): Response<Product> {
    return authenticatedApiService.getProductDetails(productId)
  }

  suspend fun getOrders(): Response<List<OrderResponseItem>> {
    return authenticatedApiService.getOrders()
  }

  suspend fun searchProducts(
    query: String
  ) : Response<SearchResponse> {
    return authenticatedApiService.searchProducts(query)
  }

  suspend fun createOrder(orderRequest: CreateOrderRequest) : Response<CreateOrderResponse> {
    return authenticatedApiService.createOrder(orderRequest)
  }

  suspend fun removeProductFromCart(productId: String) : Response<AddToCartResponse> {
    return authenticatedApiService.removeFromCart(productId)
  }

  suspend fun getProductsByPriceRange(
    minPrice: Double,
    maxPrice: Double,
    category: String,
  ): Response<ProductByCategoryResponse>{
    return authenticatedApiService.getProductsByPriceRange(
      minPrice = minPrice,
      maxPrice = maxPrice,
      category = category,
    )
  }
}
