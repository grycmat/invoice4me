package com.gigapingu.invoice4me.ui.components.invoice

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.gigapingu.invoice4me.model.InvoiceItem
import com.gigapingu.invoice4me.model.InvoiceStatus
import com.gigapingu.invoice4me.ui.components.ErrorMessage
import com.gigapingu.invoice4me.utils.createInvoicePdf
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun InvoiceFormCard(
    formState: InvoiceFormState,
    onStateChange: (InvoiceFormState) -> Unit,
    onSave: () -> Unit,
    onCancel: () -> Unit,
    onNavigateToAddItem: () -> Unit,
    onNavigateToEditItem: (InvoiceItem) -> Unit,
    focusManager: FocusManager,
    onOpenPreview: () -> Unit
) {

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.CreateDocument("application/pdf")) { uri ->
            if (uri != null) {
                coroutineScope.launch {
                    createInvoicePdf(context).fold(
                        onSuccess = { pdfDocument ->
                            context.contentResolver.openOutputStream(uri)?.use { outputStream ->
                                pdfDocument.writeTo(outputStream)
                                outputStream.flush()
                                pdfDocument.close()

                                Toast.makeText(
                                    context,
                                    "PDF saved successfully!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } ?: run {
                                Toast.makeText(
                                    context,
                                    "Failed to open file for writing",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        },
                        onFailure = { exception ->
                            Log.e("InvoiceFormCard", "Error generating PDF", exception)
                            Toast.makeText(
                                context,
                                "Error generating PDF: ${exception.message}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    )
                }
            }
        }


    val content: ColumnScope.() -> Unit = {
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
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
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
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
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
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Medium
            )

            StatusSelector(
                selectedStatus = formState.status,
                onStatusSelected = {
                    onStateChange(formState.copy(status = it))
                }
            )

            // Capturable invoice content for PDF generation
//            Box(Modifier.capturable(captureController)) {
//                InvoicePreviewContent(formState = formState)
//            }

            Button(
                onClick = {
                    launcher.launch("invoice.pdf")
                },
            ) {
                Text("Generate PDF")
            }
            Button(
                onClick = onOpenPreview,
            ) {
                Text("Preview PDF")
            }
            Spacer(modifier = Modifier.height(8.dp))

            InvoiceActionButtons(
                onCancel = onCancel,
                onSave = onSave,
                isLoading = formState.isLoading
            )

        }
    }
    Card(
           modifier = Modifier.fillMaxWidth(),
           shape = RoundedCornerShape(20.dp),
           colors = CardDefaults.cardColors(
               containerColor = MaterialTheme.colorScheme.surface
           ),
           elevation = CardDefaults.cardElevation(defaultElevation = 0.dp), content = content
    )
}

