package com.app.humaraapnabazaar.data.repository

import com.app.humaraapnabazaar.data.model.AddToCartRequest
import com.app.humaraapnabazaar.data.model.AddToCartResponse
import com.app.humaraapnabazaar.data.model.CartResponseItem
import com.app.humaraapnabazaar.data.model.CreateOrderRequest
import com.app.humaraapnabazaar.data.model.CreateOrderResponse
import com.app.humaraapnabazaar.data.model.OrderResponseItem
import com.app.humaraapnabazaar.data.model.Product
import com.app.humaraapnabazaar.data.model.ProductByCategoryResponse
import com.app.humaraapnabazaar.data.model.ProductsResponse
import com.app.humaraapnabazaar.data.model.SearchResponse
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
}
