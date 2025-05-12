package com.app.grocer.data.model


import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class ShippingAddressX(
    val address: String,
    val city: String,
    val country: String,
    val postalCode: String
) : Parcelable