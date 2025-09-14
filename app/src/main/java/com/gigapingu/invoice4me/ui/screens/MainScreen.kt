package com.gigapingu.invoice4me.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.gigapingu.invoice4me.Invoice4MeApplication
import com.gigapingu.invoice4me.model.InvoiceItem
import com.gigapingu.invoice4me.navigation.LocalNavController
import com.gigapingu.invoice4me.ui.navigation.AppNavigation
import com.gigapingu.invoice4me.ui.navigation.NavigationHost
import com.gigapingu.invoice4me.ui.theme.Invoice4MeTheme

@Composable
fun MainScreen() {
    val context = LocalContext.current
    val application = context.applicationContext as Invoice4MeApplication
    val viewModel: MainScreenViewModel = viewModel(
        factory = MainScreenViewModelFactory(application.userPreferencesRepository)
    )

    val navController = rememberNavController()
    val isDarkTheme by viewModel.isDarkTheme.collectAsState()
    var editingItem by remember { mutableStateOf<InvoiceItem?>(null) }

    CompositionLocalProvider(LocalNavController provides navController)  {
        Invoice4MeTheme(darkTheme = isDarkTheme) {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                containerColor = MaterialTheme.colorScheme.background,
                bottomBar = { AppNavigation(navController) }
            ) { innerPadding ->
                NavigationHost(navController, innerPadding, editingItem)
            }
        }
    }
}



@Preview(name = "MainScreen")
@Composable
private fun PreviewMainScreen() {
    MainScreen()
}