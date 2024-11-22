package com.app.humaraapnabazaar.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.app.humaraapnabazaar.data.model.CreateOrderRequest
import com.app.humaraapnabazaar.ui.screens.AboutMeScreen
import com.app.humaraapnabazaar.ui.screens.CartScreen
import com.app.humaraapnabazaar.ui.screens.CategoryWiseProductsScreen
import com.app.humaraapnabazaar.ui.screens.CreateOrderScreen
import com.app.humaraapnabazaar.ui.screens.FeaturedProductsScreen
import com.app.humaraapnabazaar.ui.screens.HomeScreen
import com.app.humaraapnabazaar.ui.screens.LoginScreen
import com.app.humaraapnabazaar.ui.screens.MainScreen
import com.app.humaraapnabazaar.ui.screens.OrdersScreen
import com.app.humaraapnabazaar.ui.screens.ProductDetailScreen
import com.app.humaraapnabazaar.ui.screens.RegisterScreen
import com.app.humaraapnabazaar.ui.screens.WelcomeScreen
import com.app.humaraapnabazaar.ui.viewmodels.AuthViewModel
import kotlin.reflect.typeOf

@Composable
fun NavigationSetup(
  modifier: Modifier = Modifier,
  navController: NavHostController,
  authViewModel: AuthViewModel = hiltViewModel(),
) {
  val userState by authViewModel.loggedInUserStateHolder.collectAsState()
  val startDestination = if (userState.isLoggedIn) Route.MainScreen else Route.WelcomeScreen

  NavHost(navController = navController, startDestination = startDestination) {
    composable<Route.WelcomeScreen> { WelcomeScreen(navController = navController) }
    composable<Route.RegisterScreen> { RegisterScreen(navController = navController) }
    composable<Route.LoginScreen> { LoginScreen(navController = navController) }
    composable<Route.HomeScreen> { HomeScreen(modifier = modifier, navController = navController) }
    composable<Route.MainScreen> { MainScreen(modifier = modifier, navController) }
    composable<Route.AboutMeScreen> {
      AboutMeScreen(
        name = it.arguments?.getString("name")!!,
        email = it.arguments?.getString("email")!!,
        phone = it.arguments?.getString("phone")!!,
        navController = navController,
      )
    }
    composable<Route.CategoryWiseProductScreen> {
      CategoryWiseProductsScreen(
        categoryName = it.arguments?.getString("categoryName")!!,
        navController = navController,
      )
    }

    composable<Route.ProductDetailsScreen> {
      ProductDetailScreen(
        productId = it.arguments?.getString("productId")!!,
        navController = navController,
      )
    }

    composable<Route.FeaturedProductsScreen> {
      FeaturedProductsScreen(navController = navController)
    }

    composable<Route.CartScreen> { CartScreen(navController = navController) }

    composable<Route.OrdersScreen> { OrdersScreen(navController = navController) }

    composable<Route.CreateOrderScreen>(
      typeMap = mapOf(typeOf<CreateOrderRequest>() to CreateOrderRequestNavType)
    ) {
      CreateOrderScreen(
        navController = navController,
        createOrderRequest = it.arguments?.getParcelable("createOrderRequest")!!,
      )
    }
  }
}
