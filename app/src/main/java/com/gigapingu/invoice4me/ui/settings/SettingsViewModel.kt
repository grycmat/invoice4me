package com.gigapingu.invoice4me.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gigapingu.invoice4me.data.CompanyDataRepository
import com.gigapingu.invoice4me.data.UserPreferencesRepository
import com.gigapingu.invoice4me.model.SettingsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val companyDataRepository: CompanyDataRepository
) : ViewModel() {
    
    private val _settingsState = MutableStateFlow(SettingsState())
    val settingsState: StateFlow<SettingsState> = _settingsState.asStateFlow()
    
    init {
        loadSettings()
    }
    
    private fun loadSettings() {
        viewModelScope.launch {
            combine(
                userPreferencesRepository.userPreferences,
                companyDataRepository.companyData
            ) { userPrefs, companyData ->
                SettingsState(
                    isDarkTheme = userPrefs.isDarkTheme,
                    companyData = companyData ?: com.gigapingu.invoice4me.model.CompanyData()
                )
            }.collect { newState ->
                _settingsState.value = newState
            }
        }
    }
    
    fun toggleTheme(isDarkTheme: Boolean) {
        viewModelScope.launch {
            try {
                userPreferencesRepository.updateTheme(isDarkTheme)
            } catch (e: Exception) {
                // Handle error - could emit error state or show toast
                e.printStackTrace()
            }
        }
    }
}