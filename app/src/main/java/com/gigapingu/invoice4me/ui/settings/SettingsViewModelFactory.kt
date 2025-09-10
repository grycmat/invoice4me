package com.gigapingu.invoice4me.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gigapingu.invoice4me.data.CompanyDataRepository
import com.gigapingu.invoice4me.data.UserPreferencesRepository

class SettingsViewModelFactory(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val companyDataRepository: CompanyDataRepository
) : ViewModelProvider.Factory {
    
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SettingsViewModel(userPreferencesRepository, companyDataRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}