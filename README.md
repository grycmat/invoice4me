# Invoice4Me 📱

A modern Android invoice management application built with Jetpack Compose and Material3 design principles.

## 🌟 Features

- **Invoice Management**: Create, edit, and manage invoices with comprehensive details
- **Dynamic Status Tracking**: Track invoice status (Draft, Sent, Paid, Overdue) with color-coded indicators
- **Line Items Support**: Add multiple items to invoices with automatic total calculations
- **Modern UI**: Glassmorphism design with Material3 theming
- **Theme Support**: Light and dark mode with dynamic color (Android 12+)
- **Dashboard**: Statistics overview with invoice summaries and quick actions
- **Edge-to-Edge Display**: Modern Android UI with full screen utilization

## 🏗️ Architecture & Technologies

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose with Material3
- **Architecture**: Single Activity with Compose Navigation
- **Build System**: Gradle with Kotlin DSL
- **Minimum SDK**: 34 (Android 14)
- **Target SDK**: 36
- **Design**: Glassmorphism with Material3 components

## 📦 Project Structure

```
app/src/main/java/com/gigapingu/invoice4me/
├── MainActivity.kt                 # Main entry point
├── data/                          # Sample and test data
├── model/                         # Core data models
│   ├── Invoice.kt
│   ├── InvoiceItem.kt
│   └── InvoiceStatus.kt
├── ui/
│   ├── components/               # Reusable UI components
│   ├── navigation/               # Navigation setup
│   ├── screens/                  # Main screen composables
│   └── theme/                    # Theme definitions
├── modifier/                     # Custom modifiers (GlassBackground)
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
- **Android Gradle Plugin**: 8.13.0-alpha04
- **Compose BOM**: 2024.09.00
- **Min SDK**: 34 (Android 14)
- **Target SDK**: 36


Made with ❤️ using Jetpack Compose and Material3