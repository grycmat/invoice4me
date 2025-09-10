package com.gigapingu.invoice4me.ui.company

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.gigapingu.invoice4me.model.CompanyData
import com.gigapingu.invoice4me.ui.theme.GlassWhite15
import com.gigapingu.invoice4me.ui.theme.TextPrimary
import com.gigapingu.invoice4me.ui.theme.TextTertiary

@Composable
fun CompanyDataForm(
    viewModel: CompanyDataViewModel,
    modifier: Modifier = Modifier
) {
    val companyData by viewModel.companyData.collectAsState()
    val isSaving by viewModel.isSaving.collectAsState()
    val saveSuccess by viewModel.saveSuccess.collectAsState()
    
    var localCompanyData by remember { mutableStateOf(CompanyData()) }
    
    LaunchedEffect(companyData) {
        companyData?.let {
            localCompanyData = it
        }
    }
    
    LaunchedEffect(saveSuccess) {
        if (saveSuccess) {
            viewModel.clearSaveSuccess()
        }
    }
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = GlassWhite15
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Company Information",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimary
            )

            Text(
                text = "This information will appear on your invoices",
                style = MaterialTheme.typography.bodySmall,
                color = TextTertiary,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            CompanyDataField(
                label = "Company Name",
                value = localCompanyData.name,
                onValueChange = { localCompanyData = localCompanyData.copy(name = it) },
                placeholder = "Enter your company name"
            )

            CompanyDataField(
                label = "Address",
                value = localCompanyData.address,
                onValueChange = { localCompanyData = localCompanyData.copy(address = it) },
                placeholder = "Enter your company address"
            )

            CompanyDataField(
                label = "Email",
                value = localCompanyData.email,
                onValueChange = { localCompanyData = localCompanyData.copy(email = it) },
                placeholder = "company@example.com"
            )

            CompanyDataField(
                label = "Phone",
                value = localCompanyData.phone,
                onValueChange = { localCompanyData = localCompanyData.copy(phone = it) },
                placeholder = "+1 (555) 123-4567"
            )

            CompanyDataField(
                label = "Tax ID",
                value = localCompanyData.taxId,
                onValueChange = { localCompanyData = localCompanyData.copy(taxId = it) },
                placeholder = "Enter your tax identification number"
            )


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ){
                Button(
                    onClick = { viewModel.saveCompanyData(localCompanyData) },
                    enabled = !isSaving
                ) {
                    if (isSaving) {
                        CircularProgressIndicator()
                    } else {
                        Text("Save")
                    }
                }
            }
        }
    }
}
