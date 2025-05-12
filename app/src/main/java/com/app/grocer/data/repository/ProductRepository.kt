package com.app.grocer.data.repository

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

interface ProductRepository {
  suspend fun getProducts(page: Int, limit: Int): Response<ProductsResponse>

  suspend fun getProductsByCategory(
    category: String,
    page: Int,
    limit: Int,
  ): Response<ProductByCategoryResponse>

  suspend fun addToCart(addToCartRequest: AddToCartRequest): Response<AddToCartResponse>

  suspend fun getCartItems(): Response<List<CartResponseItem>>

  suspend fun getProductDetails(productId: String): Response<Product>

  suspend fun getOrders(): Response<List<OrderResponseItem>>

  suspend fun searchProducts(
    query: String
  ) : Response<SearchResponse>

  suspend fun createOrder(orderRequest: CreateOrderRequest) : Response<CreateOrderResponse>

  suspend fun removeProductFromCart(productId: String) : Response<AddToCartResponse>

  suspend fun getProductsByPriceRange(
    minPrice: Double,
    maxPrice: Double,
    category: String,
  ): Response<ProductByCategoryResponse>
}
