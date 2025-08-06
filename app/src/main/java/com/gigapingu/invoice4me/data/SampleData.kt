package com.gigapingu.invoice4me.data

import com.gigapingu.invoice4me.model.Invoice
import com.gigapingu.invoice4me.model.InvoiceStatus

val sampleInvoices = listOf(
    Invoice("INV-001", "ABC Corp", 1250.00, "2024-01-15", InvoiceStatus.PAID),
    Invoice("INV-002", "XYZ Ltd", 2100.50, "2024-01-18", InvoiceStatus.SENT),
    Invoice("INV-003", "Tech Solutions", 850.75, "2024-01-20", InvoiceStatus.DRAFT),
    Invoice("INV-004", "Design Co", 3200.00, "2024-01-12", InvoiceStatus.OVERDUE),
    Invoice("INV-005", "Marketing Plus", 975.25, "2024-01-22", InvoiceStatus.PAID),
    Invoice("INV-006", "Web Agency", 1680.00, "2024-01-25", InvoiceStatus.SENT),
    Invoice("INV-007", "Creative Studio", 2450.75, "2024-01-28", InvoiceStatus.DRAFT),
    Invoice("INV-008", "Data Systems", 1120.00, "2024-01-30", InvoiceStatus.PAID),
    Invoice("INV-009", "Cloud Services", 3850.50, "2024-02-02", InvoiceStatus.OVERDUE),
    Invoice("INV-010", "Mobile Dev Co", 2275.00, "2024-02-05", InvoiceStatus.SENT),
    Invoice("INV-011", "UI/UX Studio", 1895.25, "2024-02-08", InvoiceStatus.PAID),
    Invoice("INV-012", "Analytics Inc", 4200.00, "2024-02-10", InvoiceStatus.DRAFT),
    Invoice("INV-013", "Security Solutions", 1575.50, "2024-02-12", InvoiceStatus.SENT),
    Invoice("INV-014", "E-commerce Hub", 2950.75, "2024-02-15", InvoiceStatus.OVERDUE),
    Invoice("INV-015", "Integration Services", 1725.00, "2024-02-18", InvoiceStatus.PAID)
)
