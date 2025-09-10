package com.gigapingu.invoice4me.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.gigapingu.invoice4me.model.CompanyData
import kotlinx.coroutines.flow.Flow

@Dao
interface CompanyDataDao {

    @Query("SELECT * FROM company_data WHERE id = 1")
    fun getCompanyData(): Flow<CompanyData?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompanyData(companyData: CompanyData)

    @Update
    suspend fun updateCompanyData(companyData: CompanyData)

    @Query("DELETE FROM company_data")
    suspend fun deleteCompanyData()
}