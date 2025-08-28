# Invoice4Me MVP Roadmap

This document outlines the roadmap to achieve the Minimum Viable Product (MVP) for the Invoice4Me application, based on the specified requirements.

## MVP Goals

The primary goals for the MVP are:
1.  **Invoice Creation:** Create invoices with multiple line items.
2.  **Data Management:** Store and manage user's data, customer data, and reusable invoice items for quick selection.
3.  **Invoice History:** View and manage previous invoices.
4.  **PDF Export:** Generate a PDF of an invoice and save it to the device.

---

## Current Status: Achieved Functionality

The foundational work for the application has been completed. This provides a solid base to build the required MVP features.

-   **✅ Local Database:** A Room database has been set up for local data persistence.
-   **✅ Core Architecture:** The app uses a modern MVVM (Model-View-ViewModel) architecture with a Repository pattern.
-   **✅ Basic UI:** A dashboard screen is implemented to display a list of invoices.
-   **✅ Data Models:** Initial data models for `Invoice` and `InvoiceItem` have been created.
-   **✅ State Management:** A `ViewModel` is in place to manage UI state and business logic.

---

## Missing Functionality & Roadmap to MVP

This section details the features that are still missing and the planned steps to implement them.

### Milestone 1: Core Data Models & Management

*(Estimated Timeline: Stage 1)*

This milestone focuses on creating the necessary data structures to support all MVP features.

-   [ ] **Customer Management:**
    -   Create a `Customer` entity in the database (with fields like name, address, contact info).
    -   Create a screen to add, edit, and view customers.
    -   Modify the "Add Invoice" screen to allow selecting a customer from the list ("quick select").
-   [ ] **User Profile Management:**
    -   Create a `UserProfile` or `Company` entity to store the user's own data (name, address, logo, bank details).
    -   Create a settings screen where the user can input and edit this information.
-   [ ] **Reusable Invoice Items:**
    -   Create a `Product` or `Service` entity for reusable invoice items.
    -   Create a screen to manage these reusable items.
    -   Update the "Add Invoice Item" screen to allow selecting from this list.

### Milestone 2: Full Invoice Lifecycle

*(Estimated Timeline: Stage 2)*

This milestone focuses on completing the core functionality of creating, viewing, and managing invoices.

-   [ ] **Complete "Add Invoice" Flow:**
    -   Implement the logic to save `InvoiceItem`s to the database when an invoice is created.
    -   Integrate the Customer and User Profile data into the invoice creation process.
-   [ ] **Invoice Detail View:**
    -   Create a dedicated screen to view the full details of a single invoice, including all line items, customer data, and user data.
-   [ ] **Edit/Delete Invoices:**
    -   Implement the functionality to edit an existing invoice.
    -   Implement the functionality to delete an invoice.

### Milestone 3: PDF Generation & Export

*(Estimated Timeline: Stage 3)*

This is the final major feature for the MVP.

-   [ ] **PDF Template Design:**
    -   Design the layout of the invoice PDF, including company logo, "from" and "to" addresses, line items table, totals, and payment details.
-   [ ] **Implement PDF Generation:**
    -   Integrate a PDF creation library.
    -   Write the logic to populate the PDF template with data from a specific invoice.
-   [ ] **Save to Filesystem:**
    -   Implement the functionality to save the generated PDF to the device's storage and handle necessary permissions (if any).
    -   Add a button on the "Invoice Detail" screen to trigger the PDF export.

---

## Suggested Missing Dependencies

To achieve the MVP goals, the following dependencies would be beneficial:

1.  **PDF Generation:**
    *   **Suggestion:** Android's built-in `android.graphics.pdf.PdfDocument`.
    *   **Reason:** It's part of the Android SDK, has no licensing costs, and is sufficient for creating standard PDF documents. For more complex layouts, a library like `iTextPDF` could be considered, but be mindful of its AGPL license.

2.  **Dependency Injection:**
    *   **Suggestion:** `Hilt`.
    *   **Reason:** As the application grows with more ViewModels and Repositories, manual dependency injection (as it is now) becomes cumbersome. Hilt is the recommended DI framework for Android, simplifies the setup, and reduces boilerplate code.

3.  **Image Loading (for company logo):**
    *   **Suggestion:** `Coil`.
    *   **Reason:** A modern, Kotlin-first image loading library for Android. It will be needed to load the user's company logo onto the invoice PDF and potentially in the UI.
