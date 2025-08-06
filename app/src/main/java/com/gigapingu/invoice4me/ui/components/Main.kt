package com.gigapingu.invoice4me.ui.components

import AppNavigation
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.gigapingu.invoice4me.ui.theme.Invoice4MeTheme

@Composable
fun Main(
    modifier: Modifier = Modifier 
) {
    Invoice4MeTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = Color.Transparent,
            bottomBar = { AppNavigation() }
        ) { innerPadding ->
            Dashboard(
                modifier = Modifier.fillMaxSize(),
                contentPadding = innerPadding
            )
        }
    }
}

@Preview(name = "Main")
@Composable
private fun PreviewMain() {
    Main()
}