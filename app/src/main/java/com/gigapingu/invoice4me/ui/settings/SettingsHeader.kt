package com.gigapingu.invoice4me.ui.settings

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gigapingu.invoice4me.ui.theme.TextPrimary

@Composable
fun SettingsHeader() {
    Text(
        text = "Settings",
        style = MaterialTheme.typography.headlineMedium,
        fontWeight = FontWeight.Bold,
        color = TextPrimary,
        modifier = Modifier.padding(vertical = 12.dp)
    )
}

@Preview(name = "SettingsHeader")
@Composable
private fun PreviewSettingsHeader() {
    SettingsHeader()
}