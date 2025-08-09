package com.gigapingu.invoice4me.ui.components.invoice

import com.gigapingu.invoice4me.model.InvoiceItem
import com.gigapingu.invoice4me.model.InvoiceStatus

data class InvoiceFormState(
    val id: String = "",
    val clientName: String = "",
    val amount: String = "",
    val date: String = "",
    val status: InvoiceStatus = InvoiceStatus.DRAFT,
    val items: List<InvoiceItem> = emptyList(),
    val isLoading: Boolean = false,
    val errors: Map<String, String> = emptyMap()
) {
    val calculatedTotal: Double
        get() = items.sumOf { it.total }
        
    val shouldUseCalculatedTotal: Boolean
        get() = items.isNotEmpty()
        
    val displayAmount: String
        get() = if (shouldUseCalculatedTotal) {
            String.format("%.2f", calculatedTotal)
        } else {
            amount
        }
}