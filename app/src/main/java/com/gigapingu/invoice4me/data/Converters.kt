package com.gigapingu.invoice4me.data

import androidx.room.TypeConverter
import com.gigapingu.invoice4me.model.InvoiceStatus
import com.gigapingu.invoice4me.model.UnitType

class Converters {
    @TypeConverter
    fun fromInvoiceStatus(value: InvoiceStatus): String {
        return value.name
    }

    @TypeConverter
    fun toInvoiceStatus(value: String): InvoiceStatus {
        return InvoiceStatus.valueOf(value)
    }

    @TypeConverter
    fun fromUnitType(value: UnitType): String {
        return value.name
    }

    @TypeConverter
    fun toUnitType(value: String): UnitType {
        return UnitType.valueOf(value)
    }
}
