package com.gigapingu.invoice4me.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gigapingu.invoice4me.Invoice4MeApplication
import com.gigapingu.invoice4me.data.sampleInvoices
import com.gigapingu.invoice4me.modifier.gradientBackground
import com.gigapingu.invoice4me.ui.InvoiceViewModel
import com.gigapingu.invoice4me.ui.InvoiceViewModelFactory
import com.gigapingu.invoice4me.navigation.LocalNavController
import com.gigapingu.invoice4me.ui.components.DashboardHeader
import com.gigapingu.invoice4me.ui.components.InvoiceCard
import com.gigapingu.invoice4me.ui.components.StatsCards
import com.gigapingu.invoice4me.ui.navigation.routes.Routes
import com.gigapingu.invoice4me.ui.theme.GlassBlue1
import com.gigapingu.invoice4me.ui.theme.GlassPink1
import com.gigapingu.invoice4me.ui.theme.Invoice4MeTheme
import com.gigapingu.invoice4me.utils.calculatePadding

@Composable
fun DashboardScreen(modifier: Modifier = Modifier, contentPadding: PaddingValues = PaddingValues(0.dp)) {
    val layoutDirection = LocalLayoutDirection.current
    val combinedPadding = calculatePadding(contentPadding, layoutDirection)
    val context = LocalContext.current
    val navController = LocalNavController.current
    val invoiceViewModel: InvoiceViewModel = viewModel(
        factory = InvoiceViewModelFactory((context.applicationContext as Invoice4MeApplication).repository)
    )
    val invoicesWithItems by invoiceViewModel.allInvoices.collectAsState()
    val invoices = invoicesWithItems.map { it.invoice.copy(items = it.items) }

    Box(
        modifier = modifier
            .gradientBackground()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = combinedPadding,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                DashboardHeader()
            }

            item {
                StatsCards(invoices = invoices)
            }

            item {
                Text(
                    text = "Recent Invoices",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(vertical = 12.dp)
                )
            }

            items(invoices) { invoice ->
                InvoiceCard(
                    invoice = invoice,
                    onClick = {
                        navController.navigate(Routes.EditInvoice.createRoute(invoice.id))
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardScreenPreview() {
    Invoice4MeTheme {
        DashboardScreen(contentPadding = PaddingValues(0.dp))
    }
}