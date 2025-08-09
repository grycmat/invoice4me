package com.gigapingu.invoice4me.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * Validates if the provided date string is in valid YYYY-MM-DD format
 */
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

/**
 * Gets the current date formatted as YYYY-MM-DD
 */
fun getCurrentDate(): String {
    val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return format.format(Date())
}