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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.gigapingu.invoice4me.R
import com.gigapingu.invoice4me.model.CompanyData
import com.gigapingu.invoice4me.ui.theme.GlassWhite15

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
            containerColor = MaterialTheme.colorScheme.surface
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
                text = stringResource(id = R.string.company_info_title),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = stringResource(id = R.string.company_info_subtitle),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            CompanyDataField(
                label = stringResource(id = R.string.company_info_name_label),
                value = localCompanyData.name,
                onValueChange = { localCompanyData = localCompanyData.copy(name = it) },
                placeholder = stringResource(id = R.string.company_info_name_placeholder)
            )

            CompanyDataField(
                label = stringResource(id = R.string.company_info_address_label),
                value = localCompanyData.address,
                onValueChange = { localCompanyData = localCompanyData.copy(address = it) },
                placeholder = stringResource(id = R.string.company_info_address_placeholder)
            )

            CompanyDataField(
                label = stringResource(id = R.string.company_info_email_label),
                value = localCompanyData.email,
                onValueChange = { localCompanyData = localCompanyData.copy(email = it) },
                placeholder = stringResource(id = R.string.company_info_email_placeholder)
            )

            CompanyDataField(
                label = stringResource(id = R.string.company_info_phone_label),
                value = localCompanyData.phone,
                onValue-Change = { localCompanyData = localCompanyData.copy(phone = it) },
                placeholder = stringResource(id = R.string.company_info_phone_placeholder)
            )

            CompanyDataField(
                label = stringResource(id = R.string.company_info_tax_id_label),
                value = localCompanyData.taxId,
                onValueChange = { localCompanyData = localCompanyData.copy(taxId = it) },
                placeholder = stringResource(id = R.string.company_info_tax_id_placeholder)
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
                        Text(stringResource(id = R.string.company_info_save_button))
                    }
                }
            }
        }
    }
}
