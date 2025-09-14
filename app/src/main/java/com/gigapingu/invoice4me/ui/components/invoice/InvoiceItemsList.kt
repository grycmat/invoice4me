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
import com.gigapingu.invoice4me.ui.theme.GlassBlue1
import com.gigapingu.invoice4me.ui.theme.GlassPink1
import com.gigapingu.invoice4me.ui.theme.GlassWhite15
import com.gigapingu.invoice4me.ui.theme.Invoice4MeTheme
import com.gigapingu.invoice4me.utils.calculateInvoiceItemsTotal

@Composable
fun InvoiceItemsList(
    items: List<InvoiceItem>,
    modifier: Modifier = Modifier,
    onAddItem: () -> Unit = {},
    onEditItem: (InvoiceItem) -> Unit = {},
    onDeleteItem: (InvoiceItem) -> Unit = {},
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