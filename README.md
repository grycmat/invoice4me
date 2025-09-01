# Invoice4Me 📱

A modern Android invoice management application built with Jetpack Compose and Material3 design principles. The app provides comprehensive invoice management with Room database persistence and a beautiful glassmorphic UI.

## 🌟 Features

### Core Functionality
- **Invoice Management**: Create, edit, and manage invoices with persistent storage
- **Dynamic Status Tracking**: Track invoice status (Draft, Sent, Paid, Overdue) with color-coded indicators
- **Line Items Support**: Add multiple items to invoices with automatic total calculations
- **Database Persistence**: Local Room database for reliable data storage
- **Company Profile**: Manage your company information for invoice generation

### User Interface
- **Modern UI**: Glassmorphism design with Material3 theming
- **Theme Support**: Light and dark mode toggle in settings
- **Dashboard**: Real-time statistics overview with invoice summaries
- **Edge-to-Edge Display**: Modern Android UI with full screen utilization
- **Bottom Navigation**: Easy navigation between Dashboard, Add Invoice, and Settings

## 🏗️ Architecture & Technologies

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose with Material3
- **Architecture**: MVVM with Repository pattern, Single Activity
- **Database**: Room with TypeConverters
- **Navigation**: Jetpack Navigation Compose
- **State Management**: ViewModel with StateFlow
- **Dependency Injection**: Manual (Application class)
- **Build System**: Gradle with Kotlin DSL
- **Minimum SDK**: 34 (Android 14)
- **Target SDK**: 36

## 📦 Project Structure

```
app/src/main/java/com/gigapingu/invoice4me/
├── Invoice4MeApplication.kt        # Application class with dependencies
├── MainActivity.kt                 # Single activity entry point
├── data/                          # Data layer
│   ├── AppDatabase.kt            # Room database configuration
│   ├── Converters.kt             # TypeConverters for Room
│   ├── InvoiceDao.kt             # Data Access Object
│   ├── InvoiceRepository.kt      # Repository pattern implementation
│   └── SampleData.kt             # Sample invoice data
├── model/                         # Data models
│   ├── CompanyData.kt            # Company profile model
│   ├── Invoice.kt                # Invoice entity with status enum
│   ├── InvoiceItem.kt            # Invoice line item entity
│   └── InvoiceWithItems.kt       # Relation model for Room
├── ui/
│   ├── InvoiceViewModel.kt       # ViewModel with Factory
│   ├── components/               # Reusable UI components
│   │   ├── invoice/              # Invoice-specific components
│   │   └── ...                   # Dashboard, stats, cards
│   ├── navigation/               # Navigation setup
│   │   ├── AppNavigation.kt      # Bottom navigation bar
│   │   ├── NavigationHost.kt     # Navigation graph
│   │   └── routes/               # Route definitions
│   ├── screens/                  # Screen composables
│   │   ├── DashboardScreen.kt
│   │   ├── InvoiceFormContainerScreen.kt
│   │   ├── MainScreen.kt
│   │   └── SettingsScreen.kt
│   └── theme/                    # Material3 theme setup
├── modifier/                     # Custom modifiers (if any)
└── utils/                        # Utility functions
```
## 🎨 Design System

The application follows Material3 design guidelines with custom glassmorphism effects:

- **Colors**: Dynamic color theming with system color extraction
- **Typography**: Material3 typography scale
- **Components**: Custom glass-morphic cards and surfaces
- **Theme**: Automatic light/dark mode detection
- **Icons**: Material Design icons

## 🛠️ Development Environment

- **Java Version**: 11
- **Kotlin Version**: 2.0.21
- **Android Gradle Plugin**: 8.12.1
- **Compose BOM**: 2024.09.00
- **Room Version**: 2.7.2
- **Navigation Compose**: 2.9.3
- **Min SDK**: 34 (Android 14)
- **Target SDK**: 36

## 📚 Key Dependencies

- **Jetpack Compose**: Complete UI toolkit
- **Material3**: Modern Material Design components
- **Room Database**: Local data persistence
- **Navigation Compose**: In-app navigation
- **ViewModel & LiveData**: Lifecycle-aware components
- **Kotlin Coroutines**: Asynchronous programming
- **KSP**: Kotlin Symbol Processing for Room

## 🚀 Getting Started

### Prerequisites
- Android Studio (latest stable version recommended)
- JDK 11 or higher
- Android SDK with API 34+

### Building the Project

1. Clone the repository:
```bash
git clone https://github.com/yourusername/invoice4me.git
cd invoice4me
```

2. Open in Android Studio:
   - File → Open → Select the project directory
   - Let Android Studio sync the project

3. Build and run:
   - Select a device/emulator (API 34+)
   - Click Run (▶️) or use `Shift+F10`

### Running Tests

```bash
# Unit tests
./gradlew test

# Instrumented tests
./gradlew connectedAndroidTest
```

## 🔮 Future Enhancements

Based on the roadmap, upcoming features include:
- PDF invoice generation and export
- Customer database management
- Reusable product/service catalog
- Company logo support
- Enhanced reporting and analytics
- Cloud backup and sync

## 📄 License

This project is currently in development. License information will be added soon.

---

Made with ❤️ using Jetpack Compose and Material3
