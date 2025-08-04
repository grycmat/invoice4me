package com.gigapingu.invoice4me.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gigapingu.invoice4me.model.Invoice
import com.gigapingu.invoice4me.model.InvoiceStatus
import com.gigapingu.invoice4me.ui.theme.Invoice4MeTheme

@Composable
fun InvoiceCard(invoice: Invoice) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = invoice.id,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = invoice.clientName,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
                Text(
                    text = invoice.date,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                )
            }
            
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "$${String.format("%.2f", invoice.amount)}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                StatusChip(status = invoice.status)
            }
        }
    }
}

@Composable
fun StatusChip(status: InvoiceStatus) {
    val (backgroundColor, textColor, text) = when (status) {
        InvoiceStatus.PAID -> Triple(
            Color(0xFF4CAF50).copy(alpha = 0.1f),
            Color(0xFF4CAF50),
            "Paid"
        )
        InvoiceStatus.SENT -> Triple(
            Color(0xFF2196F3).copy(alpha = 0.1f),
            Color(0xFF2196F3),
            "Sent"
        )
        InvoiceStatus.DRAFT -> Triple(
            Color(0xFF9E9E9E).copy(alpha = 0.1f),
            Color(0xFF9E9E9E),
            "Draft"
        )
        InvoiceStatus.OVERDUE -> Triple(
            Color(0xFFF44336).copy(alpha = 0.1f),
            Color(0xFFF44336),
            "Overdue"
        )
    }
    
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = backgroundColor
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelSmall,
            color = textColor,
            fontWeight = FontWeight.Medium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun InvoiceCardPreview() {
    Invoice4MeTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            InvoiceCard(
                invoice = Invoice("INV-001", "ABC Corp", 1250.00, "2024-01-15", InvoiceStatus.PAID)
            )
            InvoiceCard(
                invoice = Invoice("INV-002", "XYZ Ltd", 2100.50, "2024-01-18", InvoiceStatus.SENT)
            )
            InvoiceCard(
                invoice = Invoice("INV-003", "Tech Solutions", 850.75, "2024-01-20", InvoiceStatus.DRAFT)
            )
            InvoiceCard(
                invoice = Invoice("INV-004", "Design Co", 3200.00, "2024-01-12", InvoiceStatus.OVERDUE)
            )
        }
    }
}