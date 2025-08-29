package com.gigapingu.invoice4me.data

import com.gigapingu.invoice4me.model.Invoice
import com.gigapingu.invoice4me.model.InvoiceWithItems
import kotlinx.coroutines.flow.Flow

class InvoiceRepository(private val invoiceDao: InvoiceDao) {

    val allInvoices: Flow<List<InvoiceWithItems>> = invoiceDao.getInvoices()

    fun getInvoiceById(id: String): Flow<InvoiceWithItems> {
        return invoiceDao.getInvoiceById(id)
    }

    suspend fun insert(invoice: Invoice) {
        invoiceDao.insertInvoice(invoice)
    }

    suspend fun update(invoice: Invoice) {
        invoiceDao.updateInvoice(invoice)
    }

    suspend fun delete(invoice: Invoice) {
        invoiceDao.deleteInvoice(invoice)
    }

    suspend fun deleteAll() {
        invoiceDao.deleteAllInvoices()
    }
}
