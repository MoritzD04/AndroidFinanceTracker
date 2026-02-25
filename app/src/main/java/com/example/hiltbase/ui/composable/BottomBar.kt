package com.example.hiltbase.ui.composable

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.hiltbase.ui.navigation.NAV_MAIN
import com.example.hiltbase.ui.navigation.NAV_SETTINGS
import com.example.hiltbase.ui.viewmodel.NavigationViewModel

@Composable
fun BottomBar(
    navController: NavController,
    viewModel: NavigationViewModel = viewModel(),
) {
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.primary,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        NavigationBarItem(selected = currentRoute == NAV_MAIN, onClick = {
            navController.navigate(NAV_MAIN) {
                navController.graph.startDestinationRoute?.let { screenRoute ->
                    popUpTo(screenRoute) {
                        saveState = false
                        inclusive = false
                    }
                }
                launchSingleTop = true
                restoreState = false
            }
        }, icon = { Icon(Icons.Default.Home, contentDescription = "Home") })

        NavigationBarItem(selected = currentRoute == NAV_SETTINGS, onClick = {
            navController.navigate(NAV_SETTINGS) {
                navController.graph.startDestinationRoute?.let { screenRoute ->
                    popUpTo(screenRoute) {
                        saveState = false
                        inclusive = false
                    }
                }
                launchSingleTop = true
                restoreState = false
            }
        }, icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") })
    }
}