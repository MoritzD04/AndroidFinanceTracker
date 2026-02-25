package com.example.hiltbase.ui.navigation


import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.hiltbase.ui.view.MainView


const val NAV_MAIN: String = "MainView"
const val NAV_SETTINGS: String = "SettingsView"

@Composable
fun AppNavigation(
    navHostController: NavHostController,
) {
    LaunchedEffect(Unit) {

    }

    val startDestination = NAV_MAIN

    NavHost(navController = navHostController, startDestination = startDestination) {
        composable(NAV_MAIN) {
            MainView(navHostController)
        }
    }
}
