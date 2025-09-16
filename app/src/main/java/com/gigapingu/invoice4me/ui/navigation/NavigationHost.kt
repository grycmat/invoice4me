package com.gigapingu.invoice4me.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.gigapingu.invoice4me.model.InvoiceItem
import com.gigapingu.invoice4me.ui.components.invoice.InvoiceItemFormScreen
import com.gigapingu.invoice4me.ui.navigation.routes.Routes
import com.gigapingu.invoice4me.ui.screens.DashboardScreen
import com.gigapingu.invoice4me.ui.screens.InvoiceFormContainerScreen
import com.gigapingu.invoice4me.ui.screens.InvoicePreview
import com.gigapingu.invoice4me.ui.screens.SettingsScreen

@Composable
fun NavigationHost(
    navController: NavHostController,
    innerPadding: PaddingValues,
    editingItem: InvoiceItem?
) {
    NavHost(
        navController = navController,
        startDestination = Routes.Home.route,
    ) {
        composable(Routes.Home.route) {
            DashboardScreen(
                modifier = Modifier.fillMaxSize(),
                contentPadding = innerPadding
            )
        }
        composable(Routes.AddInvoice.route) {
            InvoiceFormContainerScreen(
                modifier = Modifier.fillMaxSize(),
                contentPadding = innerPadding,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToAddItem = {
                    navController.navigate(Routes.AddInvoiceItem.route)
                },
                onNavigateToEditItem = { item ->
                    navController.navigate(Routes.EditInvoiceItem.createRoute(item.tempId))
                },
                onItemsChanged = { items ->
                },
                onOpenPreview = {navController.navigate(Routes.PdfPreview.route)
                }
            )
        }
        composable(Routes.AddInvoiceItem.route) {
            InvoiceItemFormScreen(
                modifier = Modifier.fillMaxSize(),
                contentPadding = innerPadding,
                onNavigateBack = { navController.popBackStack() },
                onSave = { item ->
                    Result.success(Unit)
                }
            )
        }
        composable(
            route = Routes.EditInvoiceItem.route,
            arguments = listOf(
                navArgument("itemId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getString("itemId")

            InvoiceItemFormScreen(
                modifier = Modifier.fillMaxSize(),
                contentPadding = innerPadding,
                onNavigateBack = { navController.popBackStack() },
                onSave = { updatedItem ->

                    Result.success(Unit)
                }
            )
        }
        composable(Routes.Settings.route) {
            SettingsScreen(
                modifier = Modifier.fillMaxSize(),
                contentPadding = innerPadding
            )
        }
        composable(Routes.PdfPreview.route) {
            InvoicePreview()
        }
    }
}