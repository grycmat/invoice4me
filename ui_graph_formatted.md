
# Composable Graph (Formatted)

This document outlines the composable graph for the Invoice4Me application in a more readable format.

```
NavHost (ui/navigation/NavigationHost.kt)
├── DashboardScreen (ui/screens/DashboardScreen.kt)
│   ├── DashboardHeader (ui/components/DashboardHeader.kt)
│   ├── StatsCards (ui/components/StatsCard.kt)
│   └── InvoiceCard (ui/components/InvoiceCard.kt)
├── InvoiceFormContainerScreen (ui/screens/InvoiceFormContainerScreen.kt)
│   ├── InvoiceFormHeader (ui/components/invoice/InvoiceFormHeader.kt)
│   ├── InvoiceFormCard (ui/components/invoice/InvoiceFormCard.kt)
│   └── InvoiceItemFormScreen (ui/components/invoice/InvoiceItemFormScreen.kt) (in ModalBottomSheet)
├── SettingsScreen (ui/screens/SettingsScreen.kt)
│   ├── SettingsHeader (ui/settings/SettingsHeader.kt)
│   ├── ThemeToggleCard (ui/settings/ThemeToggleCard.kt)
│   └── CompanyDataForm (ui/company/CompanyDataForm.kt)
└── InvoicePreview (ui/screens/InvoicePreview.kt)
    └── AndroidView (androidx.compose.ui.viewinterop)
        └── layout: activity_invoice_pdf.xml (res/layout/activity_invoice_pdf.xml)
```
