package com.gigapingu.invoice4me.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gigapingu.invoice4me.model.CompanyData
import com.gigapingu.invoice4me.model.SettingsState
import com.gigapingu.invoice4me.ui.theme.GlassBlue1
import com.gigapingu.invoice4me.ui.theme.GlassPink1
import com.gigapingu.invoice4me.ui.theme.GlassWhite15
import com.gigapingu.invoice4me.ui.theme.GlassWhite20
import com.gigapingu.invoice4me.ui.theme.GlassWhite30
import com.gigapingu.invoice4me.ui.theme.Invoice4MeTheme
import com.gigapingu.invoice4me.ui.theme.Purple40
import com.gigapingu.invoice4me.ui.theme.TextPrimary
import com.gigapingu.invoice4me.ui.theme.TextSecondary
import com.gigapingu.invoice4me.ui.theme.TextTertiary
import com.gigapingu.invoice4me.utils.calculatePadding

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    initialSettings: SettingsState = SettingsState(),
    onThemeToggle: (Boolean) -> Unit = {},
    onCompanyDataChanged: (CompanyData) -> Unit = {}
) {
    val layoutDirection = LocalLayoutDirection.current
    val combinedPadding = calculatePadding(contentPadding, layoutDirection)
    
    var settingsState by remember { mutableStateOf(initialSettings) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        GlassBlue1,
                        GlassPink1
                    )
                )
            )
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = combinedPadding,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                SettingsHeader()
            }
            
            item {
                ThemeToggleCard(
                    isDarkTheme = settingsState.isDarkTheme,
                    onThemeToggle = { isDark ->
                        settingsState = settingsState.copy(isDarkTheme = isDark)
                        onThemeToggle(isDark)
                    }
                )
            }
            
            item {
                CompanyDataCard(
                    companyData = settingsState.companyData,
                    onCompanyDataChanged = { newData ->
                        settingsState = settingsState.copy(companyData = newData)
                        onCompanyDataChanged(newData)
                    }
                )
            }
        }
    }
}

@Composable
private fun SettingsHeader() {
    Text(
        text = "Settings",
        style = MaterialTheme.typography.headlineMedium,
        fontWeight = FontWeight.Bold,
        color = TextPrimary,
        modifier = Modifier.padding(vertical = 12.dp)
    )
}

@Composable
private fun ThemeToggleCard(
    isDarkTheme: Boolean,
    onThemeToggle: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = GlassWhite15
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(
                text = "Appearance",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimary
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Dark Theme",
                        style = MaterialTheme.typography.bodyLarge,
                        color = TextPrimary
                    )
                    Text(
                        text = "Toggle between light and dark mode",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextTertiary
                    )
                }
                
                Switch(
                    checked = isDarkTheme,
                    onCheckedChange = onThemeToggle,
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = TextPrimary,
                        checkedTrackColor = Purple40,
                        uncheckedThumbColor = TextSecondary,
                        uncheckedTrackColor = GlassWhite30
                    )
                )
            }
        }
    }
}

@Composable
private fun CompanyDataCard(
    companyData: CompanyData,
    onCompanyDataChanged: (CompanyData) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
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
                value = companyData.name,
                onValueChange = { onCompanyDataChanged(companyData.copy(name = it)) },
                placeholder = "Enter your company name"
            )
            
            CompanyDataField(
                label = "Address",
                value = companyData.address,
                onValueChange = { onCompanyDataChanged(companyData.copy(address = it)) },
                placeholder = "Enter your company address"
            )
            
            CompanyDataField(
                label = "Email",
                value = companyData.email,
                onValueChange = { onCompanyDataChanged(companyData.copy(email = it)) },
                placeholder = "company@example.com"
            )
            
            CompanyDataField(
                label = "Phone",
                value = companyData.phone,
                onValueChange = { onCompanyDataChanged(companyData.copy(phone = it)) },
                placeholder = "+1 (555) 123-4567"
            )
            
            CompanyDataField(
                label = "Tax ID",
                value = companyData.taxId,
                onValueChange = { onCompanyDataChanged(companyData.copy(taxId = it)) },
                placeholder = "Enter your tax identification number"
            )
        }
    }
}

@Composable
private fun CompanyDataField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String
) {
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Medium,
            color = TextPrimary,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = placeholder,
                    color = TextTertiary,
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = TextPrimary,
                unfocusedTextColor = TextPrimary,
                cursorColor = TextPrimary,
                focusedBorderColor = GlassWhite30,
                unfocusedBorderColor = GlassWhite20,
                focusedContainerColor = GlassWhite15,
                unfocusedContainerColor = GlassWhite15
            ),
            shape = RoundedCornerShape(12.dp),
            textStyle = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    Invoice4MeTheme {
        SettingsScreen(
            contentPadding = PaddingValues(16.dp),
            initialSettings = SettingsState(
                isDarkTheme = false,
                companyData = CompanyData(
                    name = "Invoice4Me Inc.",
                    address = "123 Business St, Suite 100, City, State 12345",
                    email = "info@invoice4me.com",
                    phone = "+1 (555) 123-4567",
                    taxId = "TAX-123456789"
                )
            )
        )
    }
}