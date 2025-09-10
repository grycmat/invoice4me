package com.gigapingu.invoice4me.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "company_data")
data class CompanyData(
    @PrimaryKey
    val id: Int = 1, // Using single row approach for company data
    val name: String = "",
    val address: String = "",
    val email: String = "",
    val phone: String = "",
    val taxId: String = ""
)

data class SettingsState(
    val isDarkTheme: Boolean = false,
    val companyData: CompanyData = CompanyData()
)