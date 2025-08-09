package com.gigapingu.invoice4me.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.gigapingu.invoice4me.model.InvoiceItem
import com.gigapingu.invoice4me.ui.components.invoice.InvoiceFormScreen
import com.gigapingu.invoice4me.ui.components.invoice.InvoiceFormState
import com.gigapingu.invoice4me.ui.components.invoice.InvoiceItemFormScreen
import com.gigapingu.invoice4me.ui.navigation.AppNavigation
import com.gigapingu.invoice4me.ui.navigation.routes.Routes
import com.gigapingu.invoice4me.ui.theme.Invoice4MeTheme

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    
    // Shared state for managing invoice items across navigation
    var tempInvoiceItems by remember { mutableStateOf<List<InvoiceItem>>(emptyList()) }
    var editingItem by remember { mutableStateOf<InvoiceItem?>(null) }

    Invoice4MeTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = Color.Transparent,
            bottomBar = { AppNavigation(navController) }
        ) { innerPadding ->
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
                    InvoiceFormScreenWithItemManagement(
                        modifier = Modifier.fillMaxSize(), 
                        contentPadding = innerPadding,
                        navController = navController,
                        tempInvoiceItems = tempInvoiceItems,
                        onUpdateTempItems = { tempInvoiceItems = it },
                        onSetEditingItem = { editingItem = it }
                    )
                }
                composable(Routes.AddInvoiceItem.route) {
                    InvoiceItemFormScreen(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = innerPadding,
                        onNavigateBack = { navController.popBackStack() },
                        onSave = { item ->
                            tempInvoiceItems = tempInvoiceItems + item
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
                    val itemToEdit = tempInvoiceItems.find { it.id == itemId } ?: editingItem
                    
                    InvoiceItemFormScreen(
                        initialItem = itemToEdit,
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = innerPadding,
                        onNavigateBack = { navController.popBackStack() },
                        onSave = { updatedItem ->
                            tempInvoiceItems = tempInvoiceItems.map { item ->
                                if (item.id == updatedItem.id) updatedItem else item
                            }
                            Result.success(Unit)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun InvoiceFormScreenWithItemManagement(
    modifier: Modifier = Modifier,
    contentPadding: androidx.compose.foundation.layout.PaddingValues,
    navController: NavController,
    tempInvoiceItems: List<InvoiceItem>,
    onUpdateTempItems: (List<InvoiceItem>) -> Unit,
    onSetEditingItem: (InvoiceItem?) -> Unit
) {
    InvoiceFormScreenWithState(
        initialItems = tempInvoiceItems,
        modifier = modifier,
        contentPadding = contentPadding,
        onNavigateBack = {
            onUpdateTempItems(emptyList())
            navController.popBackStack()
        },
        onNavigateToAddItem = {
            onSetEditingItem(null)
            navController.navigate(Routes.AddInvoiceItem.route)
        },
        onNavigateToEditItem = { item ->
            onSetEditingItem(item)
            navController.navigate(Routes.EditInvoiceItem.createRoute(item.id))
        },
        onItemsChanged = { items ->
            onUpdateTempItems(items)
        }
    )
}

@Preview(name = "MainScreen")
@Composable
private fun PreviewMainScreen() {
    MainScreen()
}