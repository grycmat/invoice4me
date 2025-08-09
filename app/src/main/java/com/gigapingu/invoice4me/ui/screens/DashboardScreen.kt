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
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gigapingu.invoice4me.data.sampleInvoices
import com.gigapingu.invoice4me.ui.components.DashboardHeader
import com.gigapingu.invoice4me.ui.components.InvoiceCard
import com.gigapingu.invoice4me.ui.components.StatsCards
import com.gigapingu.invoice4me.ui.theme.GlassBlue1
import com.gigapingu.invoice4me.ui.theme.GlassPink1
import com.gigapingu.invoice4me.ui.theme.Invoice4MeTheme
import com.gigapingu.invoice4me.ui.theme.TextPrimary
import com.gigapingu.invoice4me.utils.calculatePadding

@Composable
fun DashboardScreen(modifier: Modifier = Modifier, contentPadding: PaddingValues = PaddingValues(0.dp)) {
    val layoutDirection = LocalLayoutDirection.current
    val combinedPadding = calculatePadding(contentPadding, layoutDirection)

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        GlassBlue1,
                        GlassPink1
                    )
                )
            )
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
                StatsCards(invoices = sampleInvoices)
            }
            
            item {
                Text(
                    text = "Recent Invoices",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary,
                    modifier = Modifier.padding(vertical = 12.dp) 
                )
            }
            
            items(sampleInvoices) { invoice ->
                InvoiceCard(invoice = invoice)
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