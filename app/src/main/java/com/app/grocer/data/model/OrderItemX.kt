package com.app.grocer.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class OrderItemX(
    val product: String,
    val quantity: Int
) : Parcelable