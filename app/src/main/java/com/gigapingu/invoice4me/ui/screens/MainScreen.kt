package com.gigapingu.invoice4me.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.gigapingu.invoice4me.model.InvoiceItem
import com.gigapingu.invoice4me.ui.navigation.AppNavigation
import com.gigapingu.invoice4me.ui.navigation.NavigationHost
import com.gigapingu.invoice4me.ui.theme.Invoice4MeTheme

@Composable
fun MainScreen() {
    val navController = rememberNavController()


    var editingItem by remember { mutableStateOf<InvoiceItem?>(null) }

    Invoice4MeTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = Color.Transparent,
            bottomBar = { AppNavigation(navController) }
        ) { innerPadding ->
            NavigationHost(navController, innerPadding, editingItem)
        }
    }
}



@Preview(name = "MainScreen")
@Composable
private fun PreviewMainScreen() {
    MainScreen()
}