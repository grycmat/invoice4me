package com.gigapingu.invoice4me.ui.components.invoice

import java.text.SimpleDateFormat
import java.util.*

fun validateForm(formState: InvoiceFormState): Map<String, String> {
    val errors = mutableMapOf<String, String>()
    
    if (formState.clientName.isBlank()) {
        errors["clientName"] = "Client name is required"
    }
    
    if (formState.amount.isBlank()) {
        errors["amount"] = "Amount is required"
    } else {
        try {
            val amount = formState.amount.toDouble()
            if (amount <= 0) {
                errors["amount"] = "Amount must be greater than 0"
            }
        } catch (e: NumberFormatException) {
            errors["amount"] = "Invalid amount format"
        }
    }
    
    if (formState.date.isBlank()) {
        errors["date"] = "Date is required"
    } else if (!isValidDate(formState.date)) {
        errors["date"] = "Invalid date format (YYYY-MM-DD)"
    }
    
    return errors
}

fun isValidDate(date: String): Boolean {
    return try {
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        format.isLenient = false
        format.parse(date)
        true
    } catch (e: Exception) {
        false
    }
}

fun generateInvoiceId(): String {
    val timestamp = System.currentTimeMillis()
    return "INV-${timestamp.toString().takeLast(6)}"
}

fun getCurrentDate(): String {
    val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return format.format(Date())
}