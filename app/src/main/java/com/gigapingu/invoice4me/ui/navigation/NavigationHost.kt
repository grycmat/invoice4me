package com.gigapingu.invoice4me.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gigapingu.invoice4me.ui.navigation.routes.Routes
import com.gigapingu.invoice4me.ui.screens.DashboardScreen
import com.gigapingu.invoice4me.ui.screens.InvoiceFormContainerScreen
import com.gigapingu.invoice4me.ui.screens.InvoicePreview
import com.gigapingu.invoice4me.ui.screens.SettingsScreen

@Composable
fun NavigationHost(
    navController: NavHostController,
    innerPadding: PaddingValues
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
                onItemsChanged = { items ->
                },
                onOpenPreview = { navController.navigate(Routes.PdfPreview.route) }
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