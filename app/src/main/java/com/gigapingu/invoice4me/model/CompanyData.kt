package com.gigapingu.invoice4me.model

data class CompanyData(
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