package com.app.humaraapnabazaar.data.api

import com.app.humaraapnabazaar.data.model.AddToCartRequest
import com.app.humaraapnabazaar.data.model.AddToCartResponse
import com.app.humaraapnabazaar.data.model.CartResponseItem
import com.app.humaraapnabazaar.data.model.CategoriesResponseItem
import com.app.humaraapnabazaar.data.model.CreateOrderRequest
import com.app.humaraapnabazaar.data.model.CreateOrderResponse
import com.app.humaraapnabazaar.data.model.OrderResponseItem
import com.app.humaraapnabazaar.data.model.Product
import com.app.humaraapnabazaar.data.model.ProductByCategoryResponse
import com.app.humaraapnabazaar.data.model.ProductsResponse
import com.app.humaraapnabazaar.data.model.ProfileResponse
import com.app.humaraapnabazaar.data.model.SearchResponse
import com.app.humaraapnabazaar.data.model.UpdateProfileRequest
import com.app.humaraapnabazaar.data.model.UpdateProfileResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface AuthenticatedApiService {
  @GET("categories") suspend fun getCategories(): Response<List<CategoriesResponseItem>>

  @GET("products/top-products")
  suspend fun getTopProducts(
    @Query("page") page: Int,
    @Query("limit") limit: Int,
  ): Response<ProductsResponse>

  @GET("users/profile") suspend fun getProfile(): Response<ProfileResponse>

  @PUT("users/update-user")
  suspend fun updateProfile(
    @Body profileRequest: UpdateProfileRequest
  ): Response<UpdateProfileResponse>

  @GET("products/category")
  suspend fun getProductsByCategory(
    @Query("category") category: String,
    @Query("page") page: Int,
    @Query("limit") limit: Int,
  ): Response<ProductByCategoryResponse>

  @POST("products/cart")
  suspend fun addToCart(@Body addToCartRequest: AddToCartRequest): Response<AddToCartResponse>

  @GET("products/cart") suspend fun getCartItems(): Response<List<CartResponseItem>>

  @GET("products/{productId}")
  suspend fun getProductDetails(@Path("productId") productId: String): Response<Product>

  @GET("orders") suspend fun getOrders(): Response<List<OrderResponseItem>>

  @GET("products/search")
  suspend fun searchProducts(@Query("search") query: String): Response<SearchResponse>

  @POST("orders/create-order")
  suspend fun createOrder(
    @Body createOrderRequest: CreateOrderRequest
  ): Response<CreateOrderResponse>

  @DELETE("products/cart/{productId}")
  suspend fun removeFromCart(@Path("productId") productId: String): Response<AddToCartResponse>

  @GET("products/price-range")
  suspend fun getProductsByPriceRange(
    @Query("minPrice") minPrice: Double,
    @Query("maxPrice") maxPrice: Double,
    @Query("category") category: String,
  ): Response<ProductByCategoryResponse>
}
