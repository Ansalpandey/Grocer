package com.app.grocer.data.model

data class SearchResponse(
    val products: List<Product>,
    val totalProducts: Int
)