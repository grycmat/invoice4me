package com.gigapingu.invoice4me.ui.components.invoice

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gigapingu.invoice4me.model.InvoiceItem
import com.gigapingu.invoice4me.model.UnitType
import com.gigapingu.invoice4me.ui.theme.*
import com.gigapingu.invoice4me.utils.*

@Composable
fun InvoiceItemsList(
    items: List<InvoiceItem>,
    onAddItem: () -> Unit = {},
    onEditItem: (InvoiceItem) -> Unit = {},
    onDeleteItem: (InvoiceItem) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = GlassWhite15
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header with Add Button
            InvoiceItemsHeader(
                itemCount = items.size,
                totalAmount = calculateInvoiceItemsTotal(items),
                onAddItem = onAddItem
            )

            // Items List or Empty State
            if (items.isEmpty()) {
                InvoiceItemsEmptyState(onAddItem = onAddItem)
            } else {
                Column(
                    modifier = Modifier.animateContentSize(
                        animationSpec = tween(
                            durationMillis = 300,
                            easing = LinearOutSlowInEasing
                        )
                    ),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items.forEach { item ->
                        InvoiceItemCard(
                            item = item,
                            onEdit = { onEditItem(item) },
                            onDelete = { onDeleteItem(item) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun InvoiceItemsHeader(
    itemCount: Int,
    totalAmount: Double,
    onAddItem: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Invoice Items",
                    style = MaterialTheme.typography.titleMedium,
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "$itemCount ${if (itemCount == 1) "item" else "items"}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary
                )
            }

            FilledTonalButton(
                onClick = onAddItem,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.filledTonalButtonColors(
                    containerColor = GlassWhite25,
                    contentColor = TextPrimary
                ),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add item",
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Add Item")
            }
        }

        // Total Summary
        if (itemCount > 0) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = GlassWhite20
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Items Total",
                        style = MaterialTheme.typography.titleMedium,
                        color = TextSecondary,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = formatCurrency(totalAmount),
                        style = MaterialTheme.typography.titleLarge,
                        color = TextPrimary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun InvoiceItemsEmptyState(onAddItem: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = GlassWhite10
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null,
                modifier = Modifier
                    .padding(24.dp)
                    .size(32.dp),
                tint = TextTertiary
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "No items added yet",
                style = MaterialTheme.typography.titleMedium,
                color = TextPrimary,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = "Add items to build your invoice",
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary
            )
        }

        Button(
            onClick = onAddItem,
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = GlassWhite25,
                contentColor = TextPrimary
            )
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Add First Item")
        }
    }
}

@Composable
private fun InvoiceItemCard(
    item: InvoiceItem,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    var showDeleteConfirmation by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = GlassWhite20
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Item name and actions
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = item.name,
                        style = MaterialTheme.typography.titleSmall,
                        color = TextPrimary,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    if (item.legalBasis.isNotEmpty()) {
                        Text(
                            text = item.legalBasis,
                            style = MaterialTheme.typography.bodySmall,
                            color = TextTertiary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    IconButton(
                        onClick = onEdit,
                        modifier = Modifier
                            .size(32.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(GlassWhite15)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit item",
                            tint = TextSecondary,
                            modifier = Modifier.size(16.dp)
                        )
                    }

                    IconButton(
                        onClick = { showDeleteConfirmation = true },
                        modifier = Modifier
                            .size(32.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(GlassWhite15)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete item",
                            tint = StatusOverdueRed,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }

            // Item details
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Column {
                    Text(
                        text = formatQuantityWithUnit(item.quantity, item.unitType),
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondary
                    )
                    Text(
                        text = "${formatCurrency(item.pricePerUnit)} per ${item.unitType.symbol}",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextTertiary
                    )
                }

                Text(
                    text = formatCurrency(item.total),
                    style = MaterialTheme.typography.titleMedium,
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }

    // Delete Confirmation Dialog
    if (showDeleteConfirmation) {
        AlertDialog(
            onDismissRequest = { showDeleteConfirmation = false },
            title = {
                Text(
                    text = "Delete Item",
                    color = TextPrimary
                )
            },
            text = {
                Text(
                    text = "Are you sure you want to delete \"${item.name}\"? This action cannot be undone.",
                    color = TextSecondary
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDelete()
                        showDeleteConfirmation = false
                    },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = StatusOverdueRed
                    )
                ) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDeleteConfirmation = false },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = TextPrimary
                    )
                ) {
                    Text("Cancel")
                }
            },
            containerColor = GlassWhite90,
            shape = RoundedCornerShape(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun InvoiceItemsListPreview() {
    Invoice4MeTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = androidx.compose.ui.graphics.Brush.verticalGradient(
                        colors = listOf(GlassBlue1, GlassPink1)
                    )
                )
                .padding(20.dp)
        ) {
            InvoiceItemsList(
                items = listOf(
                    InvoiceItem(
                        tempId = "1",
                        invoiceId = "PREVIEW-001",
                        name = "UI/UX Design Services",
                        legalBasis = "Professional Services",
                        quantity = 40.0,
                        unitType = UnitType.HOURS,
                        pricePerUnit = 75.0
                    ),
                    InvoiceItem(
                        tempId = "2",
                        invoiceId = "PREVIEW-001",
                        name = "Web Development",
                        legalBasis = "",
                        quantity = 1.0,
                        unitType = UnitType.SERVICES,
                        pricePerUnit = 2500.0
                    ),
                    InvoiceItem(
                        tempId = "3",
                        invoiceId = "PREVIEW-001",
                        name = "Marketing Materials",
                        legalBasis = "Print Services",
                        quantity = 500.0,
                        unitType = UnitType.PIECES,
                        pricePerUnit = 1.25
                    )
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InvoiceItemsListEmptyPreview() {
    Invoice4MeTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = androidx.compose.ui.graphics.Brush.verticalGradient(
                        colors = listOf(GlassBlue1, GlassPink1)
                    )
                )
                .padding(20.dp)
        ) {
            InvoiceItemsList(items = emptyList())
        }
    }
}