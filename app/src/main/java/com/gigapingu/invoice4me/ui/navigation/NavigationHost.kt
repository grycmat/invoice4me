package com.gigapingu.invoice4me.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.gigapingu.invoice4me.model.InvoiceItem
import com.gigapingu.invoice4me.ui.components.invoice.InvoiceFormScreenWithState
import com.gigapingu.invoice4me.ui.components.invoice.InvoiceItemFormScreen
import com.gigapingu.invoice4me.ui.navigation.routes.Routes
import com.gigapingu.invoice4me.ui.screens.DashboardScreen
import com.gigapingu.invoice4me.ui.screens.SettingsScreen

@Composable
fun NavigationHost(
    navController: NavHostController,
    innerPadding: PaddingValues,
    tempInvoiceItems: List<InvoiceItem>,
    editingItem: InvoiceItem?
) {
    var tempInvoiceItems1 = tempInvoiceItems
    var editingItem1 = editingItem
    NavHost(
        navController = navController,
        startDestination = Routes.Home.route
    ) {
        composable(Routes.Home.route) {
            DashboardScreen(
                modifier = Modifier.fillMaxSize(),
                contentPadding = innerPadding
            )
        }
        composable(Routes.AddInvoice.route) {
            InvoiceFormScreenWithState(
                modifier = Modifier.fillMaxSize(),
                contentPadding = innerPadding,
                initialItems = tempInvoiceItems1,
                onNavigateBack = {
                    tempInvoiceItems1 = emptyList()
                    navController.popBackStack()
                },
                onNavigateToAddItem = {
                    editingItem1 = null
                    navController.navigate(Routes.AddInvoiceItem.route)
                },
                onNavigateToEditItem = { item ->
                    editingItem1 = item
                    navController.navigate(Routes.EditInvoiceItem.createRoute(item.tempId))
                },
                onItemsChanged = { items ->
                    tempInvoiceItems1 = items
                }
            )
        }
        composable(Routes.AddInvoiceItem.route) {
            InvoiceItemFormScreen(
                modifier = Modifier.fillMaxSize(),
                contentPadding = innerPadding,
                onNavigateBack = { navController.popBackStack() },
                onSave = { item ->
                    tempInvoiceItems1 = tempInvoiceItems1 + item
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
            val itemToEdit = tempInvoiceItems1.find { it.tempId == itemId } ?: editingItem1

            InvoiceItemFormScreen(
                initialItem = itemToEdit,
                modifier = Modifier.fillMaxSize(),
                contentPadding = innerPadding,
                onNavigateBack = { navController.popBackStack() },
                onSave = { updatedItem ->
                    tempInvoiceItems1 = tempInvoiceItems1.map { item ->
                        if (item.tempId == updatedItem.tempId) updatedItem else item
                    }
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
    }
}