package com.mfa.products.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mfa.product.list.ProductListRoute

@Composable
fun ProductsNavigation(modifier: Modifier, navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = "list"){
        composable("list") {
            ProductListRoute(modifier = modifier, onClick = {
                navController.navigate("product/detail/$it")
            })
        }
        composable(
            "product/detail/{id}",
            listOf(navArgument("id") { type = NavType.StringType })
        ) {
            //ProductDetailRoute()
        }
    }
}