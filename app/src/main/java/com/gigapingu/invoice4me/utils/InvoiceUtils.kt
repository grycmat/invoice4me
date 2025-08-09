package com.gigapingu.invoice4me.utils

/**
 * Generates a unique invoice ID based on current timestamp
 */
fun generateInvoiceId(): String {
    val timestamp = System.currentTimeMillis()
    return "INV-${timestamp.toString().takeLast(6)}"
}