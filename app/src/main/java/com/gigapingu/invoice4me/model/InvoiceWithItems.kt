package com.gigapingu.invoice4me.model

import androidx.room.Embedded
import androidx.room.Relation

data class InvoiceWithItems(
    @Embedded val invoice: Invoice,
    @Relation(
        parentColumn = "id",
        entityColumn = "invoiceId"
    )
    val items: List<InvoiceItem>
)
