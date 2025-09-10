package com.gigapingu.invoice4me.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.collectAsState
import androidx.compose.foundation.isSystemInDarkTheme
import com.gigapingu.invoice4me.data.UserPreferencesRepository
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ThemeManager(private val userPreferencesRepository: UserPreferencesRepository) {
    
    private val _isDarkTheme = MutableStateFlow(false)
    val isDarkTheme: StateFlow<Boolean> = _isDarkTheme.asStateFlow()
    
    suspend fun initialize() {
        userPreferencesRepository.initializePreferences()
        userPreferencesRepository.isDarkTheme.collect { isDark ->
            _isDarkTheme.value = isDark
        }
    }
    
    suspend fun toggleTheme(isDarkTheme: Boolean) {
        userPreferencesRepository.updateTheme(isDarkTheme)
    }
}

@Composable
fun rememberThemeManager(userPreferencesRepository: UserPreferencesRepository): ThemeManager {
    return remember { ThemeManager(userPreferencesRepository) }
}

@Composable
fun ThemeProvider(
    themeManager: ThemeManager,
    content: @Composable (Boolean) -> Unit
) {
    val isDarkTheme by themeManager.isDarkTheme.collectAsState()
    val systemInDarkTheme = isSystemInDarkTheme()
    
    var initialized by remember { mutableStateOf(false) }
    
    LaunchedEffect(Unit) {
        if (!initialized) {
            themeManager.initialize()
            initialized = true
        }
    }
    
    content(if (initialized) isDarkTheme else systemInDarkTheme)
}