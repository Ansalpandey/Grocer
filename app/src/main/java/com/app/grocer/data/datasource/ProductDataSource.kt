package com.app.grocer.data.datasource

import com.app.grocer.data.api.AuthenticatedApiService
import com.app.grocer.data.model.AddToCartRequest
import com.app.grocer.data.model.AddToCartResponse
import com.app.grocer.data.model.CartResponseItem
import com.app.grocer.data.model.CreateOrderRequest
import com.app.grocer.data.model.CreateOrderResponse
import com.app.grocer.data.model.OrderResponseItem
import com.app.grocer.data.model.Product
import com.app.grocer.data.model.ProductByCategoryResponse
import com.app.grocer.data.model.ProductsResponse
import com.app.grocer.data.model.SearchResponse
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
