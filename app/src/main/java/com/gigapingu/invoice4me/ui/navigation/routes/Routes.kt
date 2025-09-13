package com.gigapingu.invoice4me.ui.navigation.routes

sealed class Routes(val route: String) {
    object Home : Routes("home")
    object AddInvoice : Routes("add_invoice")
    object Settings : Routes("settings")
    object InvoiceDetails : Routes("invoice_details/{invoiceId}") {
        fun createRoute(invoiceId: String) = "invoice_details/$invoiceId"
    }
    object CompanyDetails : Routes("company_details/{companyId}") {
        fun createRoute(companyId: String) = "company_details/$companyId"
    }
    object AddInvoiceItem : Routes("add_invoice_item")
    object EditInvoiceItem : Routes("edit_invoice_item/{itemId}") {
        fun createRoute(itemId: String) = "edit_invoice_item/$itemId"
    }
    object PdfPreview : Routes("pdf_preview")
}