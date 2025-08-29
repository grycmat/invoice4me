package com.gigapingu.invoice4me.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "invoices")
data class Invoice(
    @PrimaryKey val id: String,
    val clientName: String,
    val amount: Double,
    val date: String,
    val status: InvoiceStatus,
    @Ignore
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