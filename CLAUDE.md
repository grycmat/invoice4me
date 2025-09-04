# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview
Invoice4Me is a modern Android invoice management application built with Jetpack Compose and Material3 design principles. It uses a Single Activity architecture with MVVM pattern and Room database for persistence.

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
- **Single Activity**: All screens are Composables within MainActivity
- **Room Database**: Local persistence with entities, DAOs, and TypeConverters
- **Manual Dependency Injection**: Dependencies are managed through Application class

### Key Components
- **Application**: `Invoice4MeApplication` - Provides database and repository instances
- **Database**: `AppDatabase` - Room database with Invoice and InvoiceItem entities
- **Repository**: `InvoiceRepository` - Mediates between ViewModels and data sources
- **ViewModel**: `InvoiceViewModel` - Manages UI-related data and business logic
- **Navigation**: Uses Jetpack Navigation Compose with bottom navigation bar

### Package Structure
```
com.gigapingu.invoice4me/
├── data/           # Database, DAOs, Repository, Converters
├── model/          # Data classes and entities (Invoice, InvoiceItem, etc.)
├── ui/
│   ├── components/ # Reusable UI components organized by feature
│   │   └── invoice/    # Invoice-specific UI components
│   ├── screens/    # Screen-level Composables
│   ├── navigation/ # Navigation setup and route definitions
│   └── theme/      # Material3 theme configuration
├── utils/          # Utility functions (DateUtils, InvoiceUtils, PdfUtils, etc.)
├── modifier/       # Custom Compose modifiers (GlassBackground effect)
├── navigation/     # Shared navigation utilities (LocalNavController)
└── *Pdf*.kt        # PDF generation and printing utilities
```

## Key Technologies
- **Kotlin 1.9.10** with Java 17 compatibility
- **Jetpack Compose** with Material3 design system
- **Room 2.5.2** for local database persistence
- **Navigation Compose 2.6.0** for in-app navigation
- **KSP 1.9.10-1.0.13** for Room annotation processing
- **Android Gradle Plugin 8.2.2**
- **Minimum SDK 26, Target SDK 33, Compile SDK 34**

## Development Guidelines
- UI follows glassmorphism design with Material3 components
- Database operations are performed asynchronously using Kotlin coroutines
- ViewModels use StateFlow for reactive UI updates
- Custom modifiers like `GlassBackground` provide consistent visual effects
- All database entities use Room with proper TypeConverters for complex types
- Navigation uses sealed class routes in `ui/navigation/routes/Routes.kt`
- PDF generation capabilities are implemented with temporary files and printing services
- Manual dependency injection through `Invoice4MeApplication` class

## Database Schema
- **Invoice**: Core invoice entity with status enum (Draft, Sent, Paid, Overdue)
- **InvoiceItem**: Line items belonging to invoices (one-to-many relationship)
- **InvoiceWithItems**: Relation class for querying invoices with their items
- **CompanyData**: Data class for company profile information

## Testing Structure
- Unit tests: `app/src/test/` - Test business logic and utilities
- Instrumented tests: `app/src/androidTest/` - Test UI and database operations
- Uses JUnit 4 and Espresso for Android testing