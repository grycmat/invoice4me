package com.gigapingu.invoice4me.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "invoices")
data class Invoice(
    @PrimaryKey var id: String = "",
    var clientName: String = "",
    var amount: Double = 0.0,
    var date: String = "",
    var status: InvoiceStatus = InvoiceStatus.DRAFT,
    @Ignore
    var items: List<InvoiceItem> = emptyList()
) {
    // Computed properties
    val calculatedTotal: Double
        get() = items.sumOf { it.total }

    val shouldUseCalculatedTotal: Boolean
        get() = items.isNotEmpty()
    
    // No-arg constructor for Room
    constructor() : this(
        id = "",
        clientName = "",
        amount = 0.0,
        date = "",
        status = InvoiceStatus.DRAFT,
        items = emptyList()
    )
}

enum class InvoiceStatus {
    DRAFT, SENT, PAID, OVERDUE
}