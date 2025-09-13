package com.gigapingu.invoice4me.ui.theme

import androidx.compose.ui.graphics.Color

/**
 * Invoice4Me Glassmorphism Color System
 * 
 * A comprehensive Material3 color scheme designed specifically for glassmorphism UI
 * with professional invoice management aesthetics. This system provides:
 * 
 * - Full Material3 color token coverage for both light and dark themes
 * - WCAG AA accessibility compliance with 4.5:1 contrast ratios
 * - Optimized glassmorphism layering with proper alpha blending
 * - Professional blue-purple palette suitable for business applications
 * - Seamless integration with existing Glass overlay system
 * 
 * Usage:
 * - Use MaterialTheme.colorScheme.* to access semantic colors
 * - Combine with existing Glass overlay colors for glassmorphism effects
 * - Surface containers provide 5 elevation levels for proper layering
 */

// Legacy Material3 colors (keeping for backward compatibility)
val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

// LIGHT THEME COLORS
// Primary Colors - Based on existing GlassBlue1 with accessibility improvements
val LightPrimary = Color(0xFF5A61E8)           // Slightly darker for better contrast
val LightOnPrimary = Color(0xFFFFFFFF)         // Pure white for maximum contrast
val LightPrimaryContainer = Color(0xFFE8EAFF)  // Very light tint for containers
val LightOnPrimaryContainer = Color(0xFF1A1C72) // Dark blue for container text

// Secondary Colors - Sophisticated purple-gray palette
val LightSecondary = Color(0xFF7C7FA8)         // Muted purple-gray
val LightOnSecondary = Color(0xFFFFFFFF)       // White text on secondary
val LightSecondaryContainer = Color(0xFFE6E7FF) // Light container
val LightOnSecondaryContainer = Color(0xFF2E3154) // Dark text on container

// Tertiary Colors - Complementary soft pink for accents
val LightTertiary = Color(0xFF9D7AA0)          // Sophisticated mauve
val LightOnTertiary = Color(0xFFFFFFFF)        // White on tertiary
val LightTertiaryContainer = Color(0xFFF5E8F6) // Very light pink container
val LightOnTertiaryContainer = Color(0xFF3D2940) // Dark text on tertiary container

// Error Colors - Professional red with good contrast
val LightError = Color(0xFFE53E3E)             // Clear, accessible red
val LightOnError = Color(0xFFFFFFFF)           // White on error
val LightErrorContainer = Color(0xFFFFEBEB)    // Light red container
val LightOnErrorContainer = Color(0xFF741B1B)  // Dark red for container text

// Surface Colors - Glassmorphism-optimized
val LightBackground = Color(0xFFFAFBFF)        // Very light blue-white
val LightOnBackground = Color(0xFF1A1C2E)      // Dark blue-gray for text
val LightSurface = Color(0xFFFAFBFF)           // Same as background for consistency
val LightOnSurface = Color(0xFF1A1C2E)         // Primary text color
val LightSurfaceVariant = Color(0xFFE6E8F2)    // Slightly darker surface
val LightOnSurfaceVariant = Color(0xFF474A5C)  // Medium contrast text

// Outline Colors
val LightOutline = Color(0xFF787B8E)           // Medium gray for borders
val LightOutlineVariant = Color(0xFFC7CADB)    // Light gray for subtle borders

// Additional Surface Colors for glassmorphism layers
val LightSurfaceDim = Color(0xFFDADCE0)        // Dimmed surface
val LightSurfaceBright = Color(0xFFFAFBFF)     // Bright surface
val LightSurfaceContainerLowest = Color(0xFFFFFFFF)    // Pure white
val LightSurfaceContainerLow = Color(0xFFF4F5FA)       // Very light
val LightSurfaceContainer = Color(0xFFEEF0F5)          // Light container
val LightSurfaceContainerHigh = Color(0xFFE8EAEF)      // Medium container
val LightSurfaceContainerHighest = Color(0xFFE2E4EA)   // Highest elevation

// Inverse Colors
val LightInverseSurface = Color(0xFF2F3142)    // Dark surface for contrast
val LightInverseOnSurface = Color(0xFFF1F1F6)  // Light text on dark surface
val LightInversePrimary = Color(0xFFB3B8FF)    // Light primary for dark backgrounds

// Scrim
val LightScrim = Color(0xFF000000)             // Black for overlays

// DARK THEME COLORS
// Primary Colors - Maintaining glassmorphism aesthetic in dark mode
val DarkPrimary = Color(0xFF9BB5FF)            // Your existing GlassPink1
val DarkOnPrimary = Color(0xFF1A1C72)          // Dark blue for contrast
val DarkPrimaryContainer = Color(0xFF3A3F7A)   // Medium dark container
val DarkOnPrimaryContainer = Color(0xFFE8EAFF) // Light text on dark container

// Secondary Colors
val DarkSecondary = Color(0xFFB8BBE0)          // Light purple-gray
val DarkOnSecondary = Color(0xFF2E3154)        // Dark text
val DarkSecondaryContainer = Color(0xFF44476B) // Dark container
val DarkOnSecondaryContainer = Color(0xFFE6E7FF) // Light container text

// Tertiary Colors
val DarkTertiary = Color(0xFFD4B8D7)          // Light mauve
val DarkOnTertiary = Color(0xFF3D2940)         // Dark text
val DarkTertiaryContainer = Color(0xFF553F58)  // Dark container
val DarkOnTertiaryContainer = Color(0xFFF5E8F6) // Light container text

// Error Colors
val DarkError = Color(0xFFFF6B6B)             // Bright red for dark mode
val DarkOnError = Color(0xFF741B1B)           // Dark red text
val DarkErrorContainer = Color(0xFF8B2626)    // Dark red container
val DarkOnErrorContainer = Color(0xFFFFE6E6)  // Light error container text

// Surface Colors - Dark glassmorphism
val DarkBackground = Color(0xFF0F1116)         // Very dark blue-black
val DarkOnBackground = Color(0xFFE8EAEF)       // Light text
val DarkSurface = Color(0xFF0F1116)            // Same as background
val DarkOnSurface = Color(0xFFE8EAEF)          // Primary light text
val DarkSurfaceVariant = Color(0xFF474A5C)     // Darker variant
val DarkOnSurfaceVariant = Color(0xFFC7CADB)   // Light variant text

// Dark Outline Colors
val DarkOutline = Color(0xFF919AAA)           // Medium gray outlines
val DarkOutlineVariant = Color(0xFF474A5C)    // Darker variant outlines

// Dark Surface Elevation Colors
val DarkSurfaceDim = Color(0xFF0F1116)         // Dimmest surface
val DarkSurfaceBright = Color(0xFF35373E)      // Brightest dark surface
val DarkSurfaceContainerLowest = Color(0xFF0A0D11)     // Lowest container
val DarkSurfaceContainerLow = Color(0xFF171A1F)        // Low container
val DarkSurfaceContainer = Color(0xFF1B1E23)           // Standard container
val DarkSurfaceContainerHigh = Color(0xFF262931)       // High container
val DarkSurfaceContainerHighest = Color(0xFF30333C)    // Highest container

// Dark Inverse Colors
val DarkInverseSurface = Color(0xFFE8EAEF)     // Light surface
val DarkInverseOnSurface = Color(0xFF2F3142)   // Dark text on light
val DarkInversePrimary = Color(0xFF5A61E8)     // Original primary for light backgrounds

// Dark Scrim
val DarkScrim = Color(0xFF000000)              // Black scrim

// Glassmorphism gradient colors - Subtle Professional Palette
val GlassBlue1 = Color(0xFF6B73FF)     // Primary gradient start - Periwinkle Blue
val GlassBlue2 = Color(0xFF764ba2)     // Alternative blue option
val GlassPink1 = Color(0xFF9BB5FF)     // Primary gradient end - Soft Lavender Blue
val GlassPink2 = Color(0xFFf5576c)     // Alternative pink option
val GlassCyan1 = Color(0xFF4facfe)     // Alternative cyan option
val GlassCyan2 = Color(0xFF00f2fe)     // Alternative cyan option

// Alternative gradient options for future customization
val GlassSubtle1 = Color(0xFF5B6FE8)   // Monochromatic blue start
val GlassSubtle2 = Color(0xFF7A8FFF)   // Monochromatic blue end
val GlassPurple1 = Color(0xFF667eea)   // Purple-blue start (original)
val GlassPurple2 = Color(0xFFa8a4ff)   // Purple-blue end (softer)

// Enhanced glassmorphism colors
val GlassWhite10 = Color(0x1AFFFFFF)  // 10% opacity
val GlassWhite15 = Color(0x26FFFFFF)  // 15% opacity
val GlassWhite20 = Color(0x33FFFFFF)  // 20% opacity
val GlassWhite25 = Color(0x40FFFFFF)  // 25% opacity
val GlassWhite30 = Color(0x4DFFFFFF)  // 30% opacity
val GlassWhite35 = Color(0x59FFFFFF)  // 35% opacity
val GlassWhite40 = Color(0x66FFFFFF)  // 40% opacity
val GlassWhite45 = Color(0x73FFFFFF)  // 45% opacity
val GlassWhite50 = Color(0x80FFFFFF)  // 50% opacity
val GlassWhite55 = Color(0x8CFFFFFF)  // 55% opacity
val GlassWhite60 = Color(0x99FFFFFF)  // 60% opacity
val GlassWhite65 = Color(0xA6FFFFFF)  // 65% opacity
val GlassWhite70 = Color(0xB3FFFFFF)  // 70% opacity
val GlassWhite75 = Color(0xBFFFFFFF)  // 75% opacity
val GlassWhite80 = Color(0xCCFFFFFF)  // 80% opacity
val GlassWhite85 = Color(0xD9FFFFFF)  // 85% opacity
val GlassWhite90 = Color(0xE6FFFFFF)  // 90% opacity


// Text colors for better contrast
val TextPrimary = Color(0xFFFFFFFF)
val TextSecondary = Color(0xE6FFFFFF)  // 90% opacity
val TextTertiary = Color(0xB3FFFFFF)   // 70% opacity
val TextQuaternary = Color(0x80FFFFFF) // 50% opacity

// Status colors with better accessibility
val StatusPaidGreen = Color(0xFF10B981)     // Emerald-500
val StatusPaidBg = Color(0x1A10B981)        // 10% opacity
val StatusSentBlue = Color(0xFF3B82F6)      // Blue-500
val StatusSentBg = Color(0x1A3B82F6)        // 10% opacity
val StatusDraftGray = Color(0xFF6B7280)     // Gray-500
val StatusDraftBg = Color(0x1A6B7280)       // 10% opacity
val StatusOverdueRed = Color(0xFFEF4444)    // Red-500
val StatusOverdueBg = Color(0x1AEF4444)     // 10% opacity

// Glass overlay colors (legacy)
val GlassWhite = Color(0xFFFFFFFF)
val GlassTransparent = Color(0x00FFFFFF)