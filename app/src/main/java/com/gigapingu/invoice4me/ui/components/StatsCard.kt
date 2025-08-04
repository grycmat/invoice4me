package com.gigapingu.invoice4me.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
fun StatsCards(invoices: List<Invoice>) {
    val totalAmount = invoices.sumOf { it.amount }
    val paidAmount = invoices.filter { it.status == InvoiceStatus.PAID }.sumOf { it.amount }
    val pendingCount = invoices.count { it.status == InvoiceStatus.SENT }
    val overdueCount = invoices.count { it.status == InvoiceStatus.OVERDUE }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        StatCard(
            title = "Total",
            value = "$${String.format("%.2f", totalAmount)}",
            modifier = Modifier.weight(1f)
        )
        StatCard(
            title = "Paid",
            value = "$${String.format("%.2f", paidAmount)}",
            modifier = Modifier.weight(1f)
        )
    }
    
    Spacer(modifier = Modifier.height(8.dp))
    
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        StatCard(
            title = "Pending",
            value = pendingCount.toString(),
            modifier = Modifier.weight(1f)
        )
        StatCard(
            title = "Overdue",
            value = overdueCount.toString(),
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun StatCard(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.height(100.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = GlassWhite15
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium,
                color = TextTertiary,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StatsCardsPreview() {
    Invoice4MeTheme {
        val sampleInvoices = listOf(
            Invoice("INV-001", "ABC Corp", 1250.00, "2024-01-15", InvoiceStatus.PAID),
            Invoice("INV-002", "XYZ Ltd", 2100.50, "2024-01-18", InvoiceStatus.SENT),
            Invoice("INV-003", "Tech Solutions", 850.75, "2024-01-20", InvoiceStatus.DRAFT),
            Invoice("INV-004", "Design Co", 3200.00, "2024-01-12", InvoiceStatus.OVERDUE)
        )
        
        Box(
            modifier = Modifier
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
            Column(modifier = Modifier.padding(16.dp)) {
                StatsCards(invoices = sampleInvoices)
            }
        }
    }
}