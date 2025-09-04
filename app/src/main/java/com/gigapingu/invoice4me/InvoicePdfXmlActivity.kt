package com.gigapingu.invoice4me

import android.app.Activity
import android.os.Bundle
import android.widget.TextView

class InvoicePdfXmlActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invoice_pdf)
        
        setupInvoiceData()
    }
    
    private fun setupInvoiceData() {
        // Invoice header data
        findViewById<TextView>(R.id.tv_issue_date)?.text = "Wystawiono dnia DD-MM-YYYY, [City]"
        findViewById<TextView>(R.id.tv_invoice_number)?.text = "Faktura nr [Invoice Number]"
        findViewById<TextView>(R.id.tv_sale_date)?.text = "Data sprzedaży: DD-MM-YYYY"
        findViewById<TextView>(R.id.tv_payment_method)?.text = "Sposób zapłaty: Przelew"
        findViewById<TextView>(R.id.tv_payment_due)?.text = "Termin płatności: DD-MM-YYYY"
        
        // Seller data
        findViewById<TextView>(R.id.tv_seller_name1)?.text = "[Company Name]"
        findViewById<TextView>(R.id.tv_seller_name2)?.text = "[Business Type]"
        findViewById<TextView>(R.id.tv_seller_name3)?.text = "[Owner Name]"
        findViewById<TextView>(R.id.tv_seller_address1)?.text = "[Street Address]"
        findViewById<TextView>(R.id.tv_seller_address2)?.text = "[Postal Code] [City]"
        findViewById<TextView>(R.id.tv_seller_nip)?.text = "NIP [Tax ID]"
        
        // Buyer data
        findViewById<TextView>(R.id.tv_buyer_name)?.text = "[Buyer Company Name]"
        findViewById<TextView>(R.id.tv_buyer_address1)?.text = "[Buyer Street Address]"
        findViewById<TextView>(R.id.tv_buyer_address2)?.text = "[Postal Code] [City]"
        findViewById<TextView>(R.id.tv_buyer_nip)?.text = "NIP [Buyer Tax ID]"
        
        // Invoice item data
        findViewById<TextView>(R.id.tv_item_number)?.text = "1."
        findViewById<TextView>(R.id.tv_item_name)?.text = "Usługa stomatologiczna"
        findViewById<TextView>(R.id.tv_legal_basis)?.text = "Art. 43 ust. 1 pkt 18a"
        findViewById<TextView>(R.id.tv_quantity)?.text = "1"
        findViewById<TextView>(R.id.tv_unit)?.text = "usł."
        findViewById<TextView>(R.id.tv_unit_price)?.text = "[Unit Price]"
        findViewById<TextView>(R.id.tv_total_price)?.text = "[Total Price]"
        
        // Summary data
        findViewById<TextView>(R.id.tv_total_amount)?.text = "[Total Amount]"
        findViewById<TextView>(R.id.tv_paid_amount)?.text = "0,00"
        findViewById<TextView>(R.id.tv_remaining_amount)?.text = "[Remaining Amount]"
        findViewById<TextView>(R.id.tv_amount_in_words)?.text = "Słownie: [Amount in words] PLN"
        
        // Footer data
        findViewById<TextView>(R.id.tv_bank_account)?.text = "Konto bankowe: [Bank Name] [Account Number]"
    }
}