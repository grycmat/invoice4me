package com.gigapingu.invoice4me.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gigapingu.invoice4me.data.UserPreferencesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class MainScreenViewModel(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    val isDarkTheme: StateFlow<Boolean> = userPreferencesRepository.isDarkTheme
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )
}