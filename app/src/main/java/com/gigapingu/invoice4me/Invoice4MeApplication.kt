package com.gigapingu.invoice4me

import android.app.Application
import com.gigapingu.invoice4me.data.AppDatabase
import com.gigapingu.invoice4me.data.InvoiceRepository

class Invoice4MeApplication : Application() {
    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { InvoiceRepository(database.invoiceDao()) }
}
