package com.gigapingu.invoice4me.ui.components.invoice

import com.gigapingu.invoice4me.model.InvoiceStatus

data class InvoiceFormState(
    val id: String = "",
    val clientName: String = "",
    val amount: String = "",
    val date: String = "",
    val status: InvoiceStatus = InvoiceStatus.DRAFT,
    val isLoading: Boolean = false,
    val errors: Map<String, String> = emptyMap()
)