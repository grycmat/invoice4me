package com.gigapingu.invoice4me.model

data class Invoice(
    val id: String,
    val clientName: String,
    val amount: Double,
    val date: String,
    val status: InvoiceStatus,
    val items: List<InvoiceItem> = emptyList()
) {
    val calculatedTotal: Double
        get() = items.sumOf { it.total }
        
    val shouldUseCalculatedTotal: Boolean
        get() = items.isNotEmpty()
}

enum class InvoiceStatus {
    DRAFT, SENT, PAID, OVERDUE
}