package com.gigapingu.invoice4me.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gigapingu.invoice4me.data.CompanyDataRepository
import com.gigapingu.invoice4me.data.UserPreferencesRepository
import com.gigapingu.invoice4me.model.SettingsState
import com.gigapingu.invoice4me.model.UserPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val repository: UserPreferencesRepository,
) : ViewModel() {
    
    private val _settingsState = MutableStateFlow(SettingsState())
    val settingsState: StateFlow<UserPreferences> = repository.userPreferences.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = UserPreferences()
    )

    val isDarkTheme: StateFlow<Boolean> = settingsState
        .combine(_settingsState) { prefs, _ ->
            prefs.isDarkTheme
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )
    

    fun toggleTheme(isDarkTheme: Boolean) {
        viewModelScope.launch {
            try {
                repository.updateTheme(isDarkTheme)
            } catch (e: Exception) {
                // Handle error - could emit error state or show toast
                e.printStackTrace()
            }
        }
    }
}