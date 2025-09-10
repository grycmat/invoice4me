package com.gigapingu.invoice4me.data

import com.gigapingu.invoice4me.model.CompanyData
import kotlinx.coroutines.flow.Flow

class CompanyDataRepository(private val companyDataDao: CompanyDataDao) {

    val companyData: Flow<CompanyData?> = companyDataDao.getCompanyData()

    suspend fun insertOrUpdate(companyData: CompanyData) {
        companyDataDao.insertCompanyData(companyData)
    }

    suspend fun update(companyData: CompanyData) {
        companyDataDao.updateCompanyData(companyData)
    }

    suspend fun delete() {
        companyDataDao.deleteCompanyData()
    }
}