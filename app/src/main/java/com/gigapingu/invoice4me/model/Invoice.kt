package com.gigapingu.invoice4me.model

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