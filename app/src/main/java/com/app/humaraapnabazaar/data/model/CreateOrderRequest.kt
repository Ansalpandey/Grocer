package com.app.humaraapnabazaar.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class CreateOrderRequest(
    val orderItems: List<OrderItemX>,
    val shippingAddress: ShippingAddressX,
    val totalPrice: Double
) : Parcelable