package com.gigapingu.invoice4me.ui.components.invoice

import com.gigapingu.invoice4me.model.InvoiceItemFormState

fun validateInvoiceItemForm(formState: InvoiceItemFormState): Map<String, String> {
    val errors = mutableMapOf<String, String>()

    // Name validation
    if (formState.name.trim().isEmpty()) {
        errors["name"] = "Item name is required"
    } else if (formState.name.trim().length < 2) {
        errors["name"] = "Item name must be at least 2 characters"
    } else if (formState.name.trim().length > 100) {
        errors["name"] = "Item name must be less than 100 characters"
    }

    // Legal basis validation (optional but with constraints if provided)
    if (formState.legalBasis.isNotEmpty() && formState.legalBasis.trim().length > 200) {
        errors["legalBasis"] = "Legal basis must be less than 200 characters"
    }

    // Quantity validation
    if (formState.quantity.trim().isEmpty()) {
        errors["quantity"] = "Quantity is required"
    } else {
        val quantity = formState.quantity.toDoubleOrNull()
        if (quantity == null) {
            errors["quantity"] = "Invalid quantity format"
        } else if (quantity <= 0) {
            errors["quantity"] = "Quantity must be greater than 0"
        } else if (quantity > 999999.999) {
            errors["quantity"] = "Quantity is too large"
        }
    }

    // Price per unit validation
    if (formState.pricePerUnit.trim().isEmpty()) {
        errors["pricePerUnit"] = "Price per unit is required"
    } else {
        val price = formState.pricePerUnit.toDoubleOrNull()
        if (price == null) {
            errors["pricePerUnit"] = "Invalid price format"
        } else if (price < 0) {
            errors["pricePerUnit"] = "Price cannot be negative"
        } else if (price > 999999.99) {
            errors["pricePerUnit"] = "Price is too large"
        }
    }

    return errors
}