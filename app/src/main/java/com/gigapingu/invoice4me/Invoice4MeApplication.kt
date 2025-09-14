package com.gigapingu.invoice4me

import android.app.Application
import com.gigapingu.invoice4me.data.AppDatabase
import com.gigapingu.invoice4me.data.InvoiceRepository
import com.gigapingu.invoice4me.data.CompanyDataRepository
import com.gigapingu.invoice4me.data.UserPreferencesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class Invoice4MeApplication : Application() {
    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { InvoiceRepository(database.invoiceDao()) }
    val companyDataRepository by lazy { CompanyDataRepository(database.companyDataDao()) }
    val userPreferencesRepository by lazy { UserPreferencesRepository(database.userPreferencesDao()) }

    override fun onCreate() {
        super.onCreate()
        initializeUserPreferences()
    }

    private fun initializeUserPreferences() {
        applicationScope.launch {
            userPreferencesRepository.initializePreferences()
        }
    }
}
