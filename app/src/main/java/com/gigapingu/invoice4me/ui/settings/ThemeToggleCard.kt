package com.gigapingu.invoice4me.ui.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gigapingu.invoice4me.ui.theme.GlassWhite15
import com.gigapingu.invoice4me.ui.theme.GlassWhite30
import com.gigapingu.invoice4me.ui.theme.Purple40
import com.gigapingu.invoice4me.ui.theme.TextPrimary
import com.gigapingu.invoice4me.ui.theme.TextSecondary
import com.gigapingu.invoice4me.ui.theme.TextTertiary

@Composable
fun ThemeToggleCard(
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

@Preview(name = "ThemeToggleCard")
@Composable
private fun PreviewThemeToggleCard() {
    ThemeToggleCard(isDarkTheme = false, onThemeToggle = {})
}