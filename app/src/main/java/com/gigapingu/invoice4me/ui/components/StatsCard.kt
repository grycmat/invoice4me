package com.gigapingu.invoice4me.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import.androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gigapingu.invoice4me.R
import com.gigapingu.invoice4me.model.Invoice
import com.gigapingu.invoice4me.model.InvoiceStatus
import com.gigapingu.invoice4me.modifier.gradientBackground
import com.gigapingu.invoice4me.ui.theme.GlassBlue1
import com.gigapingu.invoice4me.ui.theme.GlassPink1
import com.gigapingu.invoice4me.ui.theme.GlassWhite15
import com.gigapingu.invoice4me.ui.theme.Invoice4MeTheme

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
            title = stringResource(id = R.string.stats_card_total),
            value = "$${String.format("%.2f", totalAmount)}",
            modifier = Modifier.weight(1f)
        )
        StatCard(
            title = stringResource(id = R.string.stats_card_paid),
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
            title = stringResource(id = R.string.stats_card_pending),
            value = pendingCount.toString(),
            modifier = Modifier.weight(1f)
        )
        StatCard(
            title = stringResource(id = R.string.stats_card_overdue),
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
            containerColor = MaterialTheme.colorScheme.surface
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
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
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
                .gradientBackground()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                StatsCards(invoices = sampleInvoices)
            }
        }
    }
}