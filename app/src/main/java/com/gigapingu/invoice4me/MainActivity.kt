package com.gigapingu.invoice4me

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gigapingu.invoice4me.ui.theme.Invoice4MeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Invoice4MeTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = { /* TODO: Navigate to create invoice */ },
                            containerColor = MaterialTheme.colorScheme.primary
                        ) {
                            Icon(Icons.Default.Add, contentDescription = "Create Invoice")
                        }
                    }
                ) { innerPadding ->
                    Dashboard(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

data class Invoice(
    val id: String,
    val clientName: String,
    val amount: Double,
    val date: String,
    val status: InvoiceStatus
)

enum class InvoiceStatus {
    DRAFT, SENT, PAID, OVERDUE
}

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
        modifier = modifier.height(80.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

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
fun DashboardPreview() {
    Invoice4MeTheme {
        Dashboard()
    }
}