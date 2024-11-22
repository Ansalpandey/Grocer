package com.app.humaraapnabazaar.data.model

data class SearchResponse(
    val products: List<Product>,
    val totalProducts: Int
)