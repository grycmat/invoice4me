package com.gigapingu.invoice4me.ui.components

import androidx.compose.foundation.background
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
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gigapingu.invoice4me.model.Invoice
import com.gigapingu.invoice4me.model.InvoiceStatus
import com.gigapingu.invoice4me.ui.theme.*

@Composable
fun Dashboard(modifier: Modifier = Modifier) {
    val sampleInvoices = remember {
        listOf(
            Invoice("INV-001", "ABC Corp", 1250.00, "2024-01-15", InvoiceStatus.PAID),
            Invoice("INV-002", "XYZ Ltd", 2100.50, "2024-01-18", InvoiceStatus.SENT),
            Invoice("INV-003", "Tech Solutions", 850.75, "2024-01-20", InvoiceStatus.DRAFT),
            Invoice("INV-004", "Design Co", 3200.00, "2024-01-12", InvoiceStatus.OVERDUE),
            Invoice("INV-005", "Marketing Plus", 975.25, "2024-01-22", InvoiceStatus.PAID),
            Invoice("INV-006", "Web Agency", 1680.00, "2024-01-25", InvoiceStatus.SENT),
            Invoice("INV-007", "Creative Studio", 2450.75, "2024-01-28", InvoiceStatus.DRAFT),
            Invoice("INV-008", "Data Systems", 1120.00, "2024-01-30", InvoiceStatus.PAID),
            Invoice("INV-009", "Cloud Services", 3850.50, "2024-02-02", InvoiceStatus.OVERDUE),
            Invoice("INV-010", "Mobile Dev Co", 2275.00, "2024-02-05", InvoiceStatus.SENT),
            Invoice("INV-011", "UI/UX Studio", 1895.25, "2024-02-08", InvoiceStatus.PAID),
            Invoice("INV-012", "Analytics Inc", 4200.00, "2024-02-10", InvoiceStatus.DRAFT),
            Invoice("INV-013", "Security Solutions", 1575.50, "2024-02-12", InvoiceStatus.SENT),
            Invoice("INV-014", "E-commerce Hub", 2950.75, "2024-02-15", InvoiceStatus.OVERDUE),
            Invoice("INV-015", "Integration Services", 1725.00, "2024-02-18", InvoiceStatus.PAID)
        )
    }

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
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
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

@Composable
fun DashboardHeader() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = GlassWhite20
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Column {
                Text(
                    text = "Invoice Dashboard",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Manage your invoices efficiently",
                    style = MaterialTheme.typography.bodyLarge,
                    color = TextSecondary
                )
            }
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .size(32.dp),
                tint = TextTertiary
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