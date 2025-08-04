package com.gigapingu.invoice4me.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gigapingu.invoice4me.model.Invoice
import com.gigapingu.invoice4me.model.InvoiceStatus
import com.gigapingu.invoice4me.ui.theme.Invoice4MeTheme

@Composable
fun Dashboard(modifier: Modifier = Modifier) {
    val sampleInvoices = remember {
        listOf(
            Invoice("INV-001", "ABC Corp", 1250.00, "2024-01-15", InvoiceStatus.PAID),
            Invoice("INV-002", "XYZ Ltd", 2100.50, "2024-01-18", InvoiceStatus.SENT),
            Invoice("INV-003", "Tech Solutions", 850.75, "2024-01-20", InvoiceStatus.DRAFT),
            Invoice("INV-004", "Design Co", 3200.00, "2024-01-12", InvoiceStatus.OVERDUE)
        )
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
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
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
        
        items(sampleInvoices) { invoice ->
            InvoiceCard(invoice = invoice)
        }
    }
}

@Composable
fun DashboardHeader() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            Column {
                Text(
                    text = "Invoice Dashboard",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Manage your invoices efficiently",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                )
            }
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .size(48.dp),
                tint = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.3f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardPreview() {
    Invoice4MeTheme {
        Dashboard()
    }
}