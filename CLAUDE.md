# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview
Invoice4Me is a modern Android invoice management application built with Jetpack Compose and Material3 design principles. It uses a Single Activity architecture with MVVM pattern and Room database for persistence. The app features glassmorphism design, theme management, PDF generation, and comprehensive invoice management capabilities.

## Development Commands

### Build Commands
```bash
# Build debug APK
./gradlew assembleDebug

# Build release APK
./gradlew assembleRelease

# Clean build
./gradlew clean
```

### Testing Commands
```bash
# Run unit tests
./gradlew test

# Run instrumented tests (requires connected device/emulator)
./gradlew connectedAndroidTest

# Run debug unit tests specifically
./gradlew testDebugUnitTest
```

### Lint Commands
```bash
# Run lint on default variant
./gradlew lint

# Run lint and apply safe fixes
./gradlew lintFix

# Run lint on debug variant specifically
./gradlew lintDebug
```

### Development Tasks
```bash
# Install debug APK to device
./gradlew installDebug

# Generate dependency report
./gradlew dependencies

# Build and install in one command
./gradlew assembleDebug installDebug
```

## Architecture

### Core Architecture Pattern
- **MVVM with Repository Pattern**: ViewModels interact with Repository classes that manage data operations
- **Single Activity**: All screens are Composables within MainActivity with `InvoicePdfXmlActivity` for PDF operations
- **Room Database**: Local persistence with entities, DAOs, and TypeConverters
- **Manual Dependency Injection**: Dependencies are managed through `Invoice4MeApplication` class

### Key Components
- **Application**: `Invoice4MeApplication` - Provides database and repository instances
- **Database**: `AppDatabase` (version 3) - Room database with 4 entities
- **Activities**:
  - `MainActivity` - Main Compose UI container
  - `InvoicePdfXmlActivity` - Handles PDF generation and printing
- **Repositories**:
  - `InvoiceRepository` - Mediates invoice data operations
  - `CompanyDataRepository` - Manages company profile data
  - `UserPreferencesRepository` - Handles user settings and preferences
- **ViewModels**:
  - `InvoiceViewModel` - Manages invoice-related UI state
  - `MainScreenViewModel` - Manages dashboard and main screen state
  - `CompanyDataViewModel` - Manages company data state
  - `SettingsViewModel` - Manages settings and preferences
- **Navigation**: Uses Jetpack Navigation Compose with sealed class routes
- **Theme Management**: `ThemeManager` with `ThemeProvider` for dynamic theme switching

### Package Structure
```
com.gigapingu.invoice4me/
├── data/              # Database, DAOs, Repository, Converters, SampleData
├── model/             # Data classes and entities (Invoice, InvoiceItem, CompanyData, UserPreferences)
├── ui/
│   ├── company/       # Company data management UI (forms, fields, viewmodel)
│   ├── components/    # Reusable UI components
│   │   └── invoice/   # Invoice-specific UI components (forms, cards, validation)
│   ├── screens/       # Screen-level Composables (Dashboard, Settings, InvoiceForm, etc.)
│   ├── navigation/    # Navigation setup and route definitions
│   ├── settings/      # Settings UI components (header, theme toggle, viewmodel)
│   └── theme/         # Material3 theme configuration and ThemeManager
├── utils/             # Utility functions (DateUtils, InvoiceUtils, PdfUtils, LayoutUtils, etc.)
├── modifier/          # Custom Compose modifiers (GlassBackground, GradientBackground)
├── navigation/        # Shared navigation utilities (LocalNavController)
└── *Pdf*.kt          # PDF generation and printing utilities
```

## Key Technologies
- **Kotlin 1.9.10** with Java 17 compatibility
- **Jetpack Compose** with Material3 design system
- **Room 2.5.2** for local database persistence
- **Navigation Compose 2.6.0** for in-app navigation
- **KSP 1.9.10-1.0.13** for Room annotation processing
- **Android Gradle Plugin 8.2.2**
- **Minimum SDK 26, Target SDK 33, Compile SDK 34**
- **Compose BOM 2023.06.01** for version alignment
- **Lifecycle ViewModel Compose 2.6.2** for state management
- **Core KTX 1.10.1** for Kotlin extensions

## Development Guidelines
- UI follows glassmorphism design with Material3 components and custom gradient backgrounds
- Database operations are performed asynchronously using Kotlin coroutines
- ViewModels use StateFlow for reactive UI updates with proper factory patterns
- Custom modifiers like `GlassBackground` and `GradientBackground` provide consistent visual effects
- All database entities use Room with proper TypeConverters for complex types
- Navigation uses sealed class routes in `ui/navigation/routes/Routes.kt` with parameterized routes
- PDF generation capabilities implemented with temporary files and Android printing services
- Theme management with persistent user preferences and system theme detection
- Comprehensive form validation for invoices and invoice items
- Manual dependency injection through `Invoice4MeApplication` class with proper lifecycle management

## Database Schema (Version 3)
- **Invoice**: Core invoice entity with status enum (Draft, Sent, Paid, Overdue) and comprehensive fields
- **InvoiceItem**: Line items belonging to invoices (one-to-many relationship) with quantity, rate, and totals
- **InvoiceWithItems**: Relation class for querying invoices with their items
- **CompanyData**: Entity for company profile information with dedicated DAO and repository
- **UserPreferences**: Entity for user settings, theme preferences, and app configuration with dedicated DAO

## Navigation Routes
- **Home**: Dashboard screen with invoice statistics and list
- **AddInvoice**: Create new invoice form
- **EditInvoice**: Edit existing invoice with parameterized route
- **Settings**: App settings and preferences
- **InvoiceDetails**: View invoice details with parameterized route
- **CompanyDetails**: Manage company information with parameterized route
- **PdfPreview**: Preview and print invoice as PDF

## Testing Structure
- Unit tests: `app/src/test/` - Test business logic and utilities
- Instrumented tests: `app/src/androidTest/` - Test UI and database operations
- Uses JUnit 4 and Espresso for Android testing
- Test infrastructure includes example tests for both unit and instrumented testing

## Special Features
- Dynamic theme switching with persistence
- PDF generation and printing capabilities
- Glassmorphism UI design with gradient backgrounds
- Comprehensive invoice form validation
- Company data management
- Sample data generation for development
- Advanced state management with ViewModels and StateFlow