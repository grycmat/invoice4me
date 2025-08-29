package com.gigapingu.invoice4me.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Ignore
import androidx.room.Index
import androidx.room.PrimaryKey
import com.gigapingu.invoice4me.utils.generateInvoiceItemId

@Entity(
    tableName = "invoice_items",
    foreignKeys = [ForeignKey(
        entity = Invoice::class,
        parentColumns = ["id"],
        childColumns = ["invoiceId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["invoiceId"])]
)
data class InvoiceItem(
    @PrimaryKey(autoGenerate = true) var itemId: Long = 0,
    var invoiceId: String = "",
    @Ignore var tempId: String = generateInvoiceItemId(),
    var name: String = "",
    var legalBasis: String = "",
    var quantity: Double = 0.0,
    var unitType: UnitType = UnitType.PIECES,
    var pricePerUnit: Double = 0.0
) {
    // Computed property for total - not stored in database
    val total: Double
        get() = quantity * pricePerUnit
    
    // No-arg constructor for Room
    constructor() : this(
        itemId = 0,
        invoiceId = "",
        tempId = generateInvoiceItemId(),
        name = "",
        legalBasis = "",
        quantity = 0.0,
        unitType = UnitType.PIECES,
        pricePerUnit = 0.0
    )
}

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
    val tempId: String = "",
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