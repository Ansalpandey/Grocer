package com.app.grocer.data.model

data class OrderResponseItem(
    val __v: Int,
    val _id: String,
    val createdAt: String,
    val isDelivered: Boolean,
    val isOrderShipped: Boolean,
    val isOutForDelivery: Boolean,
    val orderItems: List<OrderItem>,
    val shippingAddress: ShippingAddress,
    val status: List<String>,
    val totalPrice: Float,
    val updatedAt: String,
    val user: UserX
)