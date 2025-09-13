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
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gigapingu.invoice4me.Invoice4MeApplication
import com.gigapingu.invoice4me.model.CompanyData
import com.gigapingu.invoice4me.model.SettingsState
import com.gigapingu.invoice4me.ui.company.CompanyDataForm
import com.gigapingu.invoice4me.ui.company.CompanyDataViewModel
import com.gigapingu.invoice4me.ui.company.CompanyDataViewModelFactory
import com.gigapingu.invoice4me.ui.settings.SettingsViewModel
import com.gigapingu.invoice4me.ui.settings.SettingsViewModelFactory
import com.gigapingu.invoice4me.ui.theme.GlassBlue1
import com.gigapingu.invoice4me.ui.theme.GlassPink1
import com.gigapingu.invoice4me.ui.theme.GlassWhite15
import com.gigapingu.invoice4me.ui.theme.Invoice4MeTheme
import com.gigapingu.invoice4me.utils.calculatePadding

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    val context = LocalContext.current
    val application = context.applicationContext as Invoice4MeApplication
    val companyDataViewModel: CompanyDataViewModel = viewModel(
        factory = CompanyDataViewModelFactory(application.companyDataRepository)
    )
    val settingsViewModel: SettingsViewModel = viewModel(
        factory = SettingsViewModelFactory(
            application.userPreferencesRepository,
        )
    )

    val settings by settingsViewModel.settingsState.collectAsState()
    val layoutDirection = LocalLayoutDirection.current
    val combinedPadding = calculatePadding(contentPadding, layoutDirection)

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
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = combinedPadding,
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            item {
                SettingsHeader()
            }

            item {
                ThemeToggleCard(
                    isDarkTheme = settings.isDarkTheme,
                    onThemeToggle = { isDark ->
                        settingsViewModel.toggleTheme(isDark)
                    }
                )
            }

            item {
                CompanyDataForm(viewModel = companyDataViewModel)
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
        color = MaterialTheme.colorScheme.onSurface,
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
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.1f)
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
                color = MaterialTheme.colorScheme.onSurface
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
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Toggle between light and dark mode",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Switch(
                    checked = isDarkTheme,
                    onCheckedChange = onThemeToggle,
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = MaterialTheme.colorScheme.onPrimary,
                        checkedTrackColor = MaterialTheme.colorScheme.primary,
                        uncheckedThumbColor = MaterialTheme.colorScheme.outline,
                        uncheckedTrackColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    Invoice4MeTheme {
        SettingsScreenContent(
            settingsState = SettingsState(
                isDarkTheme = false,
                companyData = CompanyData(
                    name = "Invoice4Me Inc.",
                    address = "123 Business St, Suite 100, City, State 12345",
                    email = "info@invoice4me.com",
                    phone = "+1 (555) 123-4567",
                    taxId = "TAX-123456789"
                )
            ),
            onThemeToggle = {},
            contentPadding = PaddingValues(16.dp)
        )
    }
}

@Composable
private fun SettingsScreenContent(
    settingsState: SettingsState,
    onThemeToggle: (Boolean) -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    val layoutDirection = LocalLayoutDirection.current
    val combinedPadding = calculatePadding(contentPadding, layoutDirection)

    var currentSettingsState by remember { mutableStateOf(settingsState) }

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
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = combinedPadding,
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            item {
                SettingsHeader()
            }

            item {
                ThemeToggleCard(
                    isDarkTheme = currentSettingsState.isDarkTheme,
                    onThemeToggle = { isDark ->
                        currentSettingsState = currentSettingsState.copy(isDarkTheme = isDark)
                        onThemeToggle(isDark)
                    }
                )
            }
        }
    }
}