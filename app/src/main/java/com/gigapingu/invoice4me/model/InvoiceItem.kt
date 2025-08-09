package com.gigapingu.invoice4me.model

data class InvoiceItem(
    val id: String = "",
    val name: String,
    val legalBasis: String = "",
    val quantity: Double,
    val unitType: UnitType,
    val pricePerUnit: Double,
    val total: Double = quantity * pricePerUnit
)

enum class UnitType(val displayName: String, val symbol: String) {
    PIECES("Pieces", "pcs"),
    HOURS("Hours", "hrs"),
    KILOGRAMS("Kilograms", "kg"),
    GRAMS("Grams", "g"),
    METERS("Meters", "m"),
    CENTIMETERS("Centimeters", "cm"),
    SQUARE_METERS("Square Meters", "m²"),
    CUBIC_METERS("Cubic Meters", "m³"),
    LITERS("Liters", "L"),
    MILLILITERS("Milliliters", "mL"),
    DAYS("Days", "days"),
    WEEKS("Weeks", "weeks"),
    MONTHS("Months", "months"),
    YEARS("Years", "years"),
    PAGES("Pages", "pages"),
    SESSIONS("Sessions", "sessions"),
    SERVICES("Services", "services")
}

data class InvoiceItemFormState(
    val id: String = "",
    val name: String = "",
    val legalBasis: String = "",
    val quantity: String = "",
    val unitType: UnitType = UnitType.PIECES,
    val pricePerUnit: String = "",
    val isLoading: Boolean = false,
    val errors: Map<String, String> = emptyMap()
) {
    val calculatedTotal: Double
        get() {
            val qty = quantity.toDoubleOrNull() ?: 0.0
            val price = pricePerUnit.toDoubleOrNull() ?: 0.0
            return qty * price
        }
}