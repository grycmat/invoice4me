package com.gigapingu.invoice4me

import android.app.Application
import com.gigapingu.invoice4me.data.AppDatabase
import com.gigapingu.invoice4me.data.InvoiceRepository
import com.gigapingu.invoice4me.data.CompanyDataRepository
import com.gigapingu.invoice4me.data.UserPreferencesRepository

class Invoice4MeApplication : Application() {
    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { InvoiceRepository(database.invoiceDao()) }
    val companyDataRepository by lazy { CompanyDataRepository(database.companyDataDao()) }
    val userPreferencesRepository by lazy { UserPreferencesRepository(database.userPreferencesDao()) }
}
