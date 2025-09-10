package com.gigapingu.invoice4me.ui.company

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.gigapingu.invoice4me.data.CompanyDataRepository
import com.gigapingu.invoice4me.model.CompanyData
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CompanyDataViewModel(private val repository: CompanyDataRepository) : ViewModel() {

    val companyData: StateFlow<CompanyData?> = repository.companyData
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    private val _isSaving = MutableStateFlow(false)
    val isSaving: StateFlow<Boolean> = _isSaving.asStateFlow()

    private val _saveSuccess = MutableStateFlow(false)
    val saveSuccess: StateFlow<Boolean> = _saveSuccess.asStateFlow()

    fun saveCompanyData(companyData: CompanyData) = viewModelScope.launch {
        _isSaving.value = true
        _saveSuccess.value = false
        try {
            repository.insertOrUpdate(companyData)
            _saveSuccess.value = true
        } catch (e: Exception) {
            Log.e("CompanyDataViewModel", "Error saving company data", e)
            _saveSuccess.value = false
        } finally {
            _isSaving.value = false
        }
    }

    fun updateCompanyData(companyData: CompanyData) = viewModelScope.launch {
        repository.update(companyData)
    }

    fun deleteCompanyData() = viewModelScope.launch {
        repository.delete()
    }

    fun clearSaveSuccess() {
        _saveSuccess.value = false
    }
}

class CompanyDataViewModelFactory(private val repository: CompanyDataRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CompanyDataViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CompanyDataViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}