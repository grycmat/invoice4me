package com.gigapingu.invoice4me.data

import com.gigapingu.invoice4me.model.UserPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferencesRepository(private val userPreferencesDao: UserPreferencesDao) {
    
    val userPreferences: Flow<UserPreferences> = userPreferencesDao.getUserPreferences()
        .map { it ?: UserPreferences() }
    
    val isDarkTheme: Flow<Boolean> = userPreferences.map { it.isDarkTheme }
    
    suspend fun getUserPreferencesOnce(): UserPreferences {
        return userPreferencesDao.getUserPreferencesOnce() ?: UserPreferences()
    }
    
    suspend fun updateTheme(isDarkTheme: Boolean) {
        val currentPrefs = getUserPreferencesOnce()
        val updatedPrefs = currentPrefs.copy(isDarkTheme = isDarkTheme)
        userPreferencesDao.insertUserPreferences(updatedPrefs)
    }
    
    suspend fun initializePreferences() {
        val existingPrefs = userPreferencesDao.getUserPreferencesOnce()
        if (existingPrefs == null) {
            userPreferencesDao.insertUserPreferences(UserPreferences())
        }
    }
}