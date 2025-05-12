package com.app.grocer.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.app.grocer.data.model.AddToCartRequest
import com.app.grocer.data.model.CartResponseItem
import com.app.grocer.data.model.CreateOrderRequest
import com.app.grocer.data.model.OrderResponseItem
import com.app.grocer.data.model.Product
import com.app.grocer.data.model.SearchResponse
import com.app.grocer.data.pagination.ProductByCategoryPagingSource
import com.app.grocer.data.pagination.ProductsPagingSource
import com.app.grocer.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel
@Inject
constructor(
  private val productRepository: ProductRepository,
  private val productsPagingSource: ProductsPagingSource,
) : ViewModel() {
  private val _products = MutableStateFlow<PagingData<Product>>(PagingData.empty())
  val products: StateFlow<PagingData<Product>> = _products.asStateFlow()

  private val _productDetails = MutableStateFlow<Product?>(null)
  val productDetails: StateFlow<Product?> = _productDetails.asStateFlow()

  private val _orders = MutableStateFlow<List<OrderResponseItem>>(emptyList())
  val orders: StateFlow<List<OrderResponseItem>> = _orders.asStateFlow()

  private val _productsByCategory = MutableStateFlow<PagingData<Product>>(PagingData.empty())
  val productsByCategory: StateFlow<PagingData<Product>> = _productsByCategory.asStateFlow()

  private val _searchedProducts = MutableStateFlow<SearchResponse?>(null)
  val searchedProducts: StateFlow<SearchResponse?> = _searchedProducts.asStateFlow()

  private val _cartItems = MutableStateFlow<List<CartResponseItem>>(emptyList())
  val cartItems: StateFlow<List<CartResponseItem>> = _cartItems.asStateFlow()

  fun getProducts() {
    viewModelScope.launch {
      Pager(
          config = PagingConfig(pageSize = 100, enablePlaceholders = false, initialLoadSize = 30)
        ) {
          productsPagingSource
        }
        .flow
        .cachedIn(viewModelScope)
        .collect { pagingData -> _products.value = pagingData ?: PagingData.empty() }
    }
  }

  fun getProductsByCategory(categoryName: String) {
    if (categoryName.isBlank()) return

    viewModelScope.launch {
      Pager(
          config = PagingConfig(pageSize = 100, enablePlaceholders = false, initialLoadSize = 30)
        ) {
          ProductByCategoryPagingSource(
            productRepository = productRepository,
            category = categoryName,
          )
        }
        .flow
        .cachedIn(viewModelScope)
        .collect { pagingData -> _productsByCategory.value = pagingData ?: PagingData.empty() }
    }
  }

  fun addToCart(addToCartRequest: AddToCartRequest) {
    if (addToCartRequest.productId.isBlank() || addToCartRequest.quantity <= 0) return

    viewModelScope.launch {
      val response = productRepository.addToCart(addToCartRequest)
      if (response.isSuccessful) {
        getCartItems() // Refresh cart
      }
    }
  }

  fun getCartItems() {
    viewModelScope.launch {
      val response = productRepository.getCartItems()
      _cartItems.value = response.body() ?: emptyList()
    }
  }

  fun getProductDetails(productId: String) {
    if (productId.isBlank()) return

    viewModelScope.launch {
      val response = productRepository.getProductDetails(productId)
      if (response.isSuccessful) {
        _productDetails.value = response.body()
      } else {
        _productDetails.value = null
      }
    }
  }

  fun getOrders() {
    viewModelScope.launch {
      val response = productRepository.getOrders()
      _orders.value = response.body() ?: emptyList()
    }
  }

  fun searchProducts(query: String) {
    if (query.isBlank()) return

    viewModelScope.launch {
      val response = productRepository.searchProducts(query)
      _searchedProducts.value = response.body()
    }
  }

  fun createOrder(orderRequest: CreateOrderRequest) {
    if (orderRequest.orderItems.isEmpty() || orderRequest.totalPrice <= 0) return

    viewModelScope.launch {
      val response = productRepository.createOrder(orderRequest)
      if (response.isSuccessful) {
        getOrders() // Refresh orders
      }
    }
  }

  fun removeProductFromCart(productId: String) {
    if (productId.isBlank()) return

    viewModelScope.launch {
      val response = productRepository.removeProductFromCart(productId)
      if (response.isSuccessful) {
        getCartItems() // Refresh cart
      }
    }
  }

  fun getProductsByPriceRange(minPrice: Double, maxPrice: Double, category: String) {
    viewModelScope.launch {
      val response =
        productRepository.getProductsByPriceRange(
          minPrice = minPrice,
          maxPrice = maxPrice,
          category = category,
        )

      if (response.isSuccessful) {
        _productsByCategory.value = PagingData.from(response.body()?.products ?: emptyList())
      }
    }
  }

  fun clearSearchResults() {
    _searchedProducts.value = _searchedProducts.value?.copy(products = emptyList())
  }
}
