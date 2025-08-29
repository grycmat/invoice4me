package com.gigapingu.invoice4me.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.gigapingu.invoice4me.ui.navigation.routes.Routes
import com.gigapingu.invoice4me.ui.theme.GlassBlue1
import com.gigapingu.invoice4me.ui.theme.GlassWhite50
import com.gigapingu.invoice4me.ui.theme.Invoice4MeTheme
import com.gigapingu.invoice4me.ui.theme.GlassWhite60

data class AppNavItem(
    val label: String,
    val icon: ImageVector,
    val route: String
)

val navigationItems = listOf(
    AppNavItem("Home", Icons.Filled.Home, Routes.Home.route),
    AppNavItem("Add", Icons.Filled.Add, Routes.AddInvoice.route),
    AppNavItem("Settings", Icons.Filled.Settings, Routes.Settings.route)
)

@Composable
fun AppNavigation(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = GlassWhite50
    ) {
        navigationItems.forEach { item ->
            NavigationBarItem(
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = GlassBlue1,
                    selectedTextColor = GlassBlue1,
                    unselectedIconColor = GlassBlue1,
                    unselectedTextColor = GlassBlue1,
                    indicatorColor = GlassWhite60
                ),
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                label = { Text(item.label) },
                icon = { Icon(item.icon, contentDescription = item.label) }
            )
        }
    }
}

@Preview
@Composable
fun AppNavigationPreview() {
    Invoice4MeTheme {
        val navController = rememberNavController()
        AppNavigation(navController = navController)
    }
}
