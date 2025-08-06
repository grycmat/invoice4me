package com.gigapingu.invoice4me.ui.components.invoice

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.gigapingu.invoice4me.ui.theme.*

@Composable
fun InvoiceFormField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    prefix: String = "",
    enabled: Boolean = true,
    isError: Boolean = false,
    errorMessage: String? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    Column(modifier = modifier) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = if (enabled) TextSecondary else TextQuaternary,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            placeholder = {
                Text(
                    text = placeholder,
                    color = TextQuaternary
                )
            },
            prefix = if (prefix.isNotEmpty()) {
                {
                    Text(
                        text = prefix,
                        color = if (enabled) TextSecondary else TextQuaternary
                    )
                }
            } else null,
            trailingIcon = trailingIcon,
            isError = isError,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = TextPrimary,
                unfocusedTextColor = TextPrimary,
                disabledTextColor = TextQuaternary,
                focusedContainerColor = GlassWhite10,
                unfocusedContainerColor = GlassWhite10,
                disabledContainerColor = GlassWhite10,
                focusedBorderColor = TextTertiary,
                unfocusedBorderColor = TextQuaternary,
                disabledBorderColor = TextQuaternary.copy(alpha = 0.5f),
                errorBorderColor = StatusOverdueRed,
                cursorColor = TextPrimary
            )
        )
        
        errorMessage?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.bodySmall,
                color = StatusOverdueRed,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}