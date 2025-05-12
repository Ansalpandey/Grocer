package com.app.grocer.data.model

data class Product(
    val __v: Int,
    val _id: String,
    val category: String,
    val discount: Int,
    val inStock: Boolean,
    val name: String,
    val price: Double,
    val productImage: String,
    val rating: Float,
    val description: String,
)