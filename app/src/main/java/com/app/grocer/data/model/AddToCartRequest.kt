package com.app.grocer.data.model

data class AddToCartRequest(
    val productId: String,
    var quantity: Int
)
