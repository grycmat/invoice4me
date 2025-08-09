package com.gigapingu.invoice4me.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gigapingu.invoice4me.ui.theme.Invoice4MeTheme
import com.gigapingu.invoice4me.ui.theme.StatusOverdueBg
import com.gigapingu.invoice4me.ui.theme.StatusOverdueRed

@Composable
fun ErrorMessage(
    message: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = StatusOverdueBg
        )
    ) {
        Text(
            text = message,
            modifier = Modifier.padding(12.dp),
            style = MaterialTheme.typography.bodyMedium,
            color = StatusOverdueRed
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorMessagePreview() {
    Invoice4MeTheme {
        ErrorMessage(message = "This is an error message")
    }
}