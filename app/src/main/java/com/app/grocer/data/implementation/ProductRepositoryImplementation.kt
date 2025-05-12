package com.app.grocer.data.implementation

import com.app.grocer.data.datasource.ProductDataSource
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
import com.app.grocer.data.repository.ProductRepository
import retrofit2.Response
import javax.inject.Inject

class ProductRepositoryImplementation
@Inject
constructor(private val productDataSource: ProductDataSource) : ProductRepository {
  override suspend fun getProducts(page: Int, limit: Int): Response<ProductsResponse> {
    return productDataSource.getProducts(page = page, limit = limit)
  }

  override suspend fun getProductsByCategory(
    category: String,
    page: Int,
    limit: Int,
  ): Response<ProductByCategoryResponse> {
    return productDataSource.getProductsByCategory(page = page, limit = limit, category = category)
  }

  override suspend fun addToCart(addToCartRequest: AddToCartRequest): Response<AddToCartResponse> {
    return productDataSource.addToCart(addToCartRequest)
  }

  override suspend fun getCartItems(): Response<List<CartResponseItem>> {
    return productDataSource.getCartItems()
  }

  override suspend fun getProductDetails(productId: String): Response<Product> {
    return productDataSource.getProductDetails(productId)
  }

  override suspend fun getOrders(): Response<List<OrderResponseItem>> {
    return productDataSource.getOrders()
  }

  override suspend fun searchProducts(
    query: String
  ) : Response<SearchResponse> {
    return productDataSource.searchProducts(query)
  }

  override suspend fun createOrder(orderRequest: CreateOrderRequest): Response<CreateOrderResponse> {
    return productDataSource.createOrder(orderRequest)
  }

  override suspend fun removeProductFromCart(productId: String): Response<AddToCartResponse> {
    return productDataSource.removeProductFromCart(productId)
  }

  override suspend fun getProductsByPriceRange(
    minPrice: Double,
    maxPrice: Double,
    category: String,
  ): Response<ProductByCategoryResponse>{
    return productDataSource.getProductsByPriceRange(
      minPrice = minPrice,
      maxPrice = maxPrice,
      category = category,
    )
  }
}
