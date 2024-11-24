package com.app.humaraapnabazaar.ui.navigation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
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
    composable<Route.WelcomeScreen>(
      enterTransition = { slideInHorizontally(initialOffsetX = { fullWidth -> -fullWidth }) },
      exitTransition = { slideOutHorizontally(targetOffsetX = { fullWidth -> fullWidth }) },
      popEnterTransition = { slideInHorizontally(initialOffsetX = { fullWidth -> fullWidth }) },
      popExitTransition = { slideOutHorizontally(targetOffsetX = { fullWidth -> -fullWidth }) },
    ) {
      WelcomeScreen(navController = navController)
    }
    composable<Route.RegisterScreen>(
      enterTransition = { slideInHorizontally(initialOffsetX = { fullWidth -> -fullWidth }) },
      exitTransition = { slideOutHorizontally(targetOffsetX = { fullWidth -> fullWidth }) },
      popEnterTransition = { slideInHorizontally(initialOffsetX = { fullWidth -> fullWidth }) },
      popExitTransition = { slideOutHorizontally(targetOffsetX = { fullWidth -> -fullWidth }) },
    ) {
      RegisterScreen(navController = navController)
    }
    composable<Route.LoginScreen>(
      enterTransition = { slideInHorizontally(initialOffsetX = { fullWidth -> -fullWidth }) },
      exitTransition = { slideOutHorizontally(targetOffsetX = { fullWidth -> fullWidth }) },
      popEnterTransition = { slideInHorizontally(initialOffsetX = { fullWidth -> fullWidth }) },
      popExitTransition = { slideOutHorizontally(targetOffsetX = { fullWidth -> -fullWidth }) },
    ) {
      LoginScreen(navController = navController)
    }
    composable<Route.HomeScreen>(
      enterTransition = { slideInHorizontally(initialOffsetX = { fullWidth -> -fullWidth }) },
      exitTransition = { slideOutHorizontally(targetOffsetX = { fullWidth -> fullWidth }) },
      popEnterTransition = { slideInHorizontally(initialOffsetX = { fullWidth -> fullWidth }) },
      popExitTransition = { slideOutHorizontally(targetOffsetX = { fullWidth -> -fullWidth }) },
    ) {
      HomeScreen(modifier = modifier, navController = navController)
    }
    composable<Route.MainScreen>(
      enterTransition = { slideInHorizontally(initialOffsetX = { fullWidth -> -fullWidth }) },
      exitTransition = { slideOutHorizontally(targetOffsetX = { fullWidth -> fullWidth }) },
      popEnterTransition = { slideInHorizontally(initialOffsetX = { fullWidth -> fullWidth }) },
      popExitTransition = { slideOutHorizontally(targetOffsetX = { fullWidth -> -fullWidth }) },
    ) {
      MainScreen(modifier = modifier, navController)
    }
    composable<Route.AboutMeScreen>(
      enterTransition = { slideInHorizontally(initialOffsetX = { fullWidth -> -fullWidth }) },
      exitTransition = { slideOutHorizontally(targetOffsetX = { fullWidth -> fullWidth }) },
      popEnterTransition = { slideInHorizontally(initialOffsetX = { fullWidth -> fullWidth }) },
      popExitTransition = { slideOutHorizontally(targetOffsetX = { fullWidth -> -fullWidth }) },
    ) {
      AboutMeScreen(
        name = it.arguments?.getString("name")!!,
        email = it.arguments?.getString("email")!!,
        phone = it.arguments?.getString("phone")!!,
        navController = navController,
      )
    }
    composable<Route.CategoryWiseProductScreen>(
      enterTransition = { slideInHorizontally(initialOffsetX = { fullWidth -> -fullWidth }) },
      exitTransition = { slideOutHorizontally(targetOffsetX = { fullWidth -> fullWidth }) },
      popEnterTransition = { slideInHorizontally(initialOffsetX = { fullWidth -> fullWidth }) },
      popExitTransition = { slideOutHorizontally(targetOffsetX = { fullWidth -> -fullWidth }) },
    ) {
      CategoryWiseProductsScreen(
        categoryName = it.arguments?.getString("categoryName")!!,
        categoryId = it.arguments?.getString("categoryId")!!,
        navController = navController,
      )
    }

    composable<Route.ProductDetailsScreen>(
      enterTransition = { slideInHorizontally(initialOffsetX = { fullWidth -> -fullWidth }) },
      exitTransition = { slideOutHorizontally(targetOffsetX = { fullWidth -> fullWidth }) },
      popEnterTransition = { slideInHorizontally(initialOffsetX = { fullWidth -> fullWidth }) },
      popExitTransition = { slideOutHorizontally(targetOffsetX = { fullWidth -> -fullWidth }) },
    ) {
      ProductDetailScreen(
        productId = it.arguments?.getString("productId")!!,
        navController = navController,
      )
    }

    composable<Route.FeaturedProductsScreen>(
      enterTransition = { slideInHorizontally(initialOffsetX = { fullWidth -> -fullWidth }) },
      exitTransition = { slideOutHorizontally(targetOffsetX = { fullWidth -> fullWidth }) },
      popEnterTransition = { slideInHorizontally(initialOffsetX = { fullWidth -> fullWidth }) },
      popExitTransition = { slideOutHorizontally(targetOffsetX = { fullWidth -> -fullWidth }) },
    ) {
      FeaturedProductsScreen(navController = navController)
    }

    composable<Route.CartScreen>(
      enterTransition = { slideInHorizontally(initialOffsetX = { fullWidth -> -fullWidth }) },
      exitTransition = { slideOutHorizontally(targetOffsetX = { fullWidth -> fullWidth }) },
      popEnterTransition = { slideInHorizontally(initialOffsetX = { fullWidth -> fullWidth }) },
      popExitTransition = { slideOutHorizontally(targetOffsetX = { fullWidth -> -fullWidth }) },
    ) {
      CartScreen(navController = navController)
    }

    composable<Route.OrdersScreen>(
      enterTransition = { slideInHorizontally(initialOffsetX = { fullWidth -> -fullWidth }) },
      exitTransition = { slideOutHorizontally(targetOffsetX = { fullWidth -> fullWidth }) },
      popEnterTransition = { slideInHorizontally(initialOffsetX = { fullWidth -> fullWidth }) },
      popExitTransition = { slideOutHorizontally(targetOffsetX = { fullWidth -> -fullWidth }) },
    ) {
      OrdersScreen(navController = navController)
    }

    composable<Route.CreateOrderScreen>(
      typeMap = mapOf(typeOf<CreateOrderRequest>() to CreateOrderRequestNavType),
      enterTransition = { slideInHorizontally(initialOffsetX = { fullWidth -> -fullWidth }) },
      exitTransition = { slideOutHorizontally(targetOffsetX = { fullWidth -> fullWidth }) },
      popEnterTransition = { slideInHorizontally(initialOffsetX = { fullWidth -> fullWidth }) },
      popExitTransition = { slideOutHorizontally(targetOffsetX = { fullWidth -> -fullWidth }) },
    ) {
      CreateOrderScreen(
        navController = navController,
        createOrderRequest = it.arguments?.getParcelable("createOrderRequest")!!,
      )
    }
  }
}
