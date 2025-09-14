package com.gigapingu.invoice4me.ui.components.invoice

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.gigapingu.invoice4me.model.InvoiceStatus

@Composable
fun StatusSelector(
    selectedStatus: InvoiceStatus,
    onStatusSelected: (InvoiceStatus) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        InvoiceStatus.values().forEach { status ->
            val isSelected = status == selectedStatus
            StatusOption(
                status = status,
                isSelected = isSelected,
                onClick = { onStatusSelected(status) },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun StatusOption(
    status: InvoiceStatus,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val (backgroundColor, textColor, text) = when (status) {
        InvoiceStatus.PAID -> Triple(MaterialTheme.colorScheme.secondaryContainer, MaterialTheme.colorScheme.onSecondaryContainer, "Paid")
        InvoiceStatus.SENT -> Triple(MaterialTheme.colorScheme.primaryContainer, MaterialTheme.colorScheme.primary, "Sent")
        InvoiceStatus.DRAFT -> Triple(MaterialTheme.colorScheme.surfaceVariant, MaterialTheme.colorScheme.onSurfaceVariant, "Draft")
        InvoiceStatus.OVERDUE -> Triple(MaterialTheme.colorScheme.errorContainer, MaterialTheme.colorScheme.error, "Overdue")
    }
    
    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        color = if (isSelected) backgroundColor.copy(alpha = 0.3f) else backgroundColor,
        border = if (isSelected) {
            BorderStroke(2.dp, textColor)
        } else null
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 8.dp),
            style = MaterialTheme.typography.labelMedium,
            color = textColor,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            textAlign = TextAlign.Center
        )
    }
}