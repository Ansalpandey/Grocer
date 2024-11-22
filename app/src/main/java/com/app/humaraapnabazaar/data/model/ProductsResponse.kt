package com.app.humaraapnabazaar.data.model

data class ProductsResponse(
    val currentPage: Int,
    val products: List<Product>,
    val totalPages: Int,
    val totalProducts: Int
)