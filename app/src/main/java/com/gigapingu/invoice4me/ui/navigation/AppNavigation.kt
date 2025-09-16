package com.gigapingu.invoice4me.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.gigapingu.invoice4me.ui.navigation.routes.Routes
import com.gigapingu.invoice4me.ui.theme.GlassDark10
import com.gigapingu.invoice4me.ui.theme.GlassWhite50
import com.gigapingu.invoice4me.ui.theme.Invoice4MeTheme
import com.gigapingu.invoice4me.ui.theme.LocalIsDarkTheme

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
    val isDarkTheme = LocalIsDarkTheme.current


    NavigationBar(
        containerColor = if (isDarkTheme) {
            GlassDark10
        } else {
            GlassWhite50
        },
    ) {
        navigationItems.forEach { item ->
            NavigationBarItem(
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    unselectedTextColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    indicatorColor =
                        MaterialTheme.colorScheme.surface
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
