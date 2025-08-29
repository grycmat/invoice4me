package com.gigapingu.invoice4me.model

import androidx.room.Embedded
import androidx.room.Relation

data class InvoiceWithItems(
    @Embedded var invoice: Invoice = Invoice(),
    @Relation(
        parentColumn = "id",
        entityColumn = "invoiceId"
    )
    var items: List<InvoiceItem> = emptyList()
) {
    // No-arg constructor for Room
    constructor() : this(
        invoice = Invoice(),
        items = emptyList()
    )
}
