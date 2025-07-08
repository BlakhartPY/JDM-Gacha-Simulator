package com.example.jdm_gacha_simulator.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jdm_gacha_simulator.ui.login.LoginScreen
import com.example.jdm_gacha_simulator.ui.pull.PullScreen
import com.example.jdm_gacha_simulator.ui.collection.CollectionScreen

object Routes {
    const val LOGIN = "login"
    const val PULL = "pull"
    const val COLLECTION = "collection"
}

@Composable
fun AppNavigation() {
    val navController: NavHostController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.LOGIN) {
        composable(Routes.LOGIN) {
            LoginScreen(navController)
        }
        composable(Routes.PULL) {
            PullScreen(navController)
        }
        composable(Routes.COLLECTION) {
            CollectionScreen(navController)
        }
    }
}
