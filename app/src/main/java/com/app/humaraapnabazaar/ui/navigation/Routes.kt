package com.app.humaraapnabazaar.ui.navigation

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import com.app.humaraapnabazaar.data.model.CreateOrderRequest
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlin.reflect.KClass

sealed class Route {

  @Serializable data object LoginScreen : Route()

  @Serializable data object RegisterScreen : Route()

  @Serializable data object WelcomeScreen : Route()

  @Serializable data object HomeScreen : Route()

  @Serializable data object MainScreen : Route()

  @Serializable
  data class AboutMeScreen(val name: String, val email: String, val phone: String) : Route()

  @Serializable data class CategoryWiseProductScreen(val categoryName: String) : Route()

  @Serializable data class ProductDetailsScreen(val productId: String) : Route()

  @Serializable data object FeaturedProductsScreen : Route()

  @Serializable data object CartScreen : Route()

  @Serializable data object OrdersScreen : Route()

  @Serializable data class CreateOrderScreen(val createOrderRequest: CreateOrderRequest) : Route()

}
val CreateOrderRequestNavType = object : CustomNavType<CreateOrderRequest>(
  clazz = CreateOrderRequest::class,
  serializer = CreateOrderRequest.serializer()
) {}
open class CustomNavType<T : Parcelable>(
  private val clazz: KClass<T>,
  private val serializer: KSerializer<T>,
) : NavType<T>(false) {
  override fun get(bundle: Bundle, key: String): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
      bundle.getParcelable(key, clazz.java)
    } else {
      bundle.getParcelable(key)
    }
  }

  override fun parseValue(value: String): T {
    return Json.decodeFromString(serializer, value)
  }

  override fun put(bundle: Bundle, key: String, value: T) {
    bundle.putParcelable(key, value)
  }

  override fun serializeAsValue(value: T): String {
    return Json.encodeToString(serializer, value)
  }
}
