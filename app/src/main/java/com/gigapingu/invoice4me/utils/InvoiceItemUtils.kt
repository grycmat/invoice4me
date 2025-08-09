package com.gigapingu.invoice4me.utils

import com.gigapingu.invoice4me.model.InvoiceItem
import com.gigapingu.invoice4me.model.UnitType
import java.text.NumberFormat
import java.util.*

fun generateInvoiceItemId(): String {
    return "ITEM-${System.currentTimeMillis()}"
}

fun formatCurrency(amount: Double): String {
    val formatter = NumberFormat.getCurrencyInstance(Locale.US)
    return formatter.format(amount)
}

fun formatQuantity(quantity: Double): String {
    return if (quantity % 1.0 == 0.0) {
        quantity.toLong().toString()
    } else {
        String.format("%.3f", quantity).trimEnd('0').trimEnd('.')
    }
}

fun formatQuantityWithUnit(quantity: Double, unitType: UnitType): String {
    return "${formatQuantity(quantity)} ${unitType.symbol}"
}

fun calculateInvoiceItemsTotal(items: List<InvoiceItem>): Double {
    return items.sumOf { it.total }
}

fun validateQuantityInput(input: String): Boolean {
    if (input.isEmpty()) return true
    return input.matches(Regex("^\\d*\\.?\\d*$"))
}

fun validatePriceInput(input: String): Boolean {
    if (input.isEmpty()) return true
    return input.matches(Regex("^\\d*\\.?\\d{0,2}$"))
}