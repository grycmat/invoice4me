package com.gigapingu.invoice4me.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.gigapingu.invoice4me.model.UserPreferences
import kotlinx.coroutines.flow.Flow

@Dao
interface UserPreferencesDao {
    
    @Query("SELECT * FROM user_preferences WHERE id = 1 LIMIT 1")
    fun getUserPreferences(): Flow<UserPreferences?>
    
    @Query("SELECT * FROM user_preferences WHERE id = 1 LIMIT 1")
    suspend fun getUserPreferencesOnce(): UserPreferences?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserPreferences(userPreferences: UserPreferences)
    
    @Update
    suspend fun updateUserPreferences(userPreferences: UserPreferences)
    
    @Query("UPDATE user_preferences SET isDarkTheme = :isDarkTheme WHERE id = 1")
    suspend fun updateTheme(isDarkTheme: Boolean)
}