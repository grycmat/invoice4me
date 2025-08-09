package com.gigapingu.invoice4me

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.gigapingu.invoice4me.ui.screens.DashboardScreen
import com.gigapingu.invoice4me.ui.screens.MainScreen
import com.gigapingu.invoice4me.ui.theme.Invoice4MeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        setContent {
            MainScreen()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainActivityPreview() {
    Invoice4MeTheme {
        DashboardScreen()
    }
}