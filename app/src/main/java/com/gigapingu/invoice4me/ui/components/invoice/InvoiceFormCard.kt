package com.gigapingu.invoice4me.ui.components.invoice

import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.gigapingu.invoice4me.TempInvoicePdf
import com.gigapingu.invoice4me.model.InvoiceItem
import com.gigapingu.invoice4me.ui.components.ErrorMessage
import com.gigapingu.invoice4me.ui.theme.GlassWhite20
import com.gigapingu.invoice4me.ui.theme.TextSecondary
import com.gigapingu.invoice4me.ui.theme.TextTertiary
import com.gigapingu.invoice4me.utils.createPdfFromComposable
import java.io.IOException

@Composable
fun InvoiceFormCard(
    formState: InvoiceFormState,
    onStateChange: (InvoiceFormState) -> Unit,
    onSave: () -> Unit,
    onCancel: () -> Unit,
    onNavigateToAddItem: () -> Unit,
    onNavigateToEditItem: (InvoiceItem) -> Unit,
    focusManager: FocusManager
) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CreateDocument("application/pdf"),
        onResult = { uri: Uri? ->
            uri?.let {
                try {
                    val document = createPdfFromComposable(context) { TempInvoicePdf() }
                    context.contentResolver.openOutputStream(it)?.use { outputStream ->
                        document.writeTo(outputStream)
                    }
                    document.close()
                } catch (e: IOException) {
                    Log.e("InvoiceFormCard", "Error generating PDF", e)
                }
            }
        }
    )

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = GlassWhite20
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // General error display
            formState.errors["general"]?.let { error ->
                ErrorMessage(message = error)
            }

            // Invoice ID (read-only)
            InvoiceFormField(
                label = "Invoice ID",
                value = formState.id,
                onValueChange = { },
                enabled = false,
                isError = false
            )

            // Client Name
            InvoiceFormField(
                label = "Client Name",
                value = formState.clientName,
                onValueChange = {
                    onStateChange(
                        formState.copy(
                            clientName = it,
                            errors = formState.errors - "clientName"
                        )
                    )
                },
                placeholder = "Enter client name",
                isError = formState.errors.containsKey("clientName"),
                errorMessage = formState.errors["clientName"],
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                )
            )

            // Amount (calculated or manual)
            InvoiceFormField(
                label = if (formState.shouldUseCalculatedTotal) "Total Amount (Calculated)" else "Amount",
                value = formState.displayAmount,
                onValueChange = {
                    if (!formState.shouldUseCalculatedTotal && (it.isEmpty() || it.matches(Regex("^\\d*\\.?\\d*$")))) {
                        onStateChange(
                            formState.copy(
                                amount = it,
                                errors = formState.errors - "amount"
                            )
                        )
                    }
                },
                enabled = !formState.shouldUseCalculatedTotal,
                placeholder = "0.00",
                prefix = "$",
                isError = formState.errors.containsKey("amount"),
                errorMessage = formState.errors["amount"],
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                )
            )

            if (formState.shouldUseCalculatedTotal) {
                Text(
                    text = "Amount is automatically calculated from invoice items",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextTertiary,
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
            }

            // Date
            InvoiceFormField(
                label = "Date",
                value = formState.date,
                onValueChange = {
                    onStateChange(formState.copy(date = it, errors = formState.errors - "date"))
                },
                placeholder = "YYYY-MM-DD",
                isError = formState.errors.containsKey("date"),
                errorMessage = formState.errors["date"],
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Select date",
                        tint = TextTertiary
                    )
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.clearFocus() }
                )
            )

            // Invoice Items
            InvoiceItemsList(
                items = formState.items,
                onAddItem = onNavigateToAddItem,
                onEditItem = onNavigateToEditItem,
                onDeleteItem = { itemToDelete ->
                    onStateChange(
                        formState.copy(
                            items = formState.items.filter { it.itemId != itemToDelete.itemId }
                        )
                    )
                }
            )

            // Status Selection
            Text(
                text = "Status",
                style = MaterialTheme.typography.labelLarge,
                color = TextSecondary,
                fontWeight = FontWeight.Medium
            )

            StatusSelector(
                selectedStatus = formState.status,
                onStatusSelected = {
                    onStateChange(formState.copy(status = it))
                }
            )

            Button(
                onClick = {
                    launcher.launch("invoice.pdf")
                },
            ) {
                Text("Test Generation")
            }
            Spacer(modifier = Modifier.height(8.dp))

            InvoiceActionButtons(
                onCancel = onCancel,
                onSave = onSave,
                isLoading = formState.isLoading
            )

        }
    }
}
