package com.gigapingu.invoice4me.ui.components.invoice

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gigapingu.invoice4me.model.Invoice
import com.gigapingu.invoice4me.model.InvoiceStatus
import com.gigapingu.invoice4me.ui.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun InvoiceFormScreen(
    initialInvoice: Invoice? = null,
    onNavigateBack: () -> Unit = {},
    onSave: suspend (Invoice) -> Result<Unit> = { Result.success(Unit) },
    modifier: Modifier = Modifier
) {
    var formState by remember {
        mutableStateOf(
            if (initialInvoice != null) {
                InvoiceFormState(
                    id = initialInvoice.id,
                    clientName = initialInvoice.clientName,
                    amount = initialInvoice.amount.toString(),
                    date = initialInvoice.date,
                    status = initialInvoice.status
                )
            } else {
                InvoiceFormState(
                    id = generateInvoiceId(),
                    date = getCurrentDate()
                )
            }
        )
    }

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val coroutineScope = rememberCoroutineScope()

    suspend fun handleSave() {
        keyboardController?.hide()
        
        val validation = validateForm(formState)
        if (validation.isNotEmpty()) {
            formState = formState.copy(errors = validation)
            return
        }

        formState = formState.copy(isLoading = true, errors = emptyMap())
        
        try {
            val invoice = Invoice(
                id = formState.id,
                clientName = formState.clientName.trim(),
                amount = formState.amount.toDouble(),
                date = formState.date,
                status = formState.status
            )
            
            val result = onSave(invoice)
            delay(500) // UX feedback delay
            
            result.fold(
                onSuccess = { onNavigateBack() },
                onFailure = { error ->
                    formState = formState.copy(
                        isLoading = false,
                        errors = mapOf("general" to (error.message ?: "Failed to save invoice"))
                    )
                }
            )
        } catch (e: Exception) {
            formState = formState.copy(
                isLoading = false,
                errors = mapOf("general" to "Invalid input data")
            )
        }
    }

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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            InvoiceFormHeader(
                title = if (initialInvoice != null) "Edit Invoice" else "New Invoice",
                onNavigateBack = onNavigateBack
            )

            InvoiceFormCard(
                formState = formState,
                onStateChange = { formState = it },
                onSave = { 
                    if (!formState.isLoading) {
                        focusManager.clearFocus()
                        coroutineScope.launch {
                            handleSave()
                        }
                    }
                },
                onCancel = onNavigateBack,
                focusManager = focusManager
            )
        }
    }
}

@Composable
private fun InvoiceFormHeader(
    title: String,
    onNavigateBack: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = GlassWhite20
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onNavigateBack,
                modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = TextPrimary
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
        }
    }
}

@Composable
private fun InvoiceFormCard(
    formState: InvoiceFormState,
    onStateChange: (InvoiceFormState) -> Unit,
    onSave: () -> Unit,
    onCancel: () -> Unit,
    focusManager: androidx.compose.ui.focus.FocusManager
) {
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
                    onStateChange(formState.copy(clientName = it, errors = formState.errors - "clientName"))
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

            // Amount
            InvoiceFormField(
                label = "Amount",
                value = formState.amount,
                onValueChange = { 
                    if (it.isEmpty() || it.matches(Regex("^\\d*\\.?\\d*$"))) {
                        onStateChange(formState.copy(amount = it, errors = formState.errors - "amount"))
                    }
                },
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

            Spacer(modifier = Modifier.height(8.dp))

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = onCancel,
                    modifier = Modifier.weight(1f),
                    enabled = !formState.isLoading,
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = TextPrimary
                    ),
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp, 
                        TextTertiary
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Cancel")
                }

                Button(
                    onClick = onSave,
                    modifier = Modifier.weight(1f),
                    enabled = !formState.isLoading,
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = GlassWhite25,
                        contentColor = TextPrimary
                    )
                ) {
                    if (formState.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(18.dp),
                            strokeWidth = 2.dp,
                            color = TextPrimary
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(if (formState.isLoading) "Saving..." else "Save")
                }
            }
        }
    }
}

@Composable
private fun ErrorMessage(message: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
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
fun InvoiceFormScreenPreview() {
    Invoice4MeTheme {
        InvoiceFormScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun InvoiceFormScreenEditPreview() {
    Invoice4MeTheme {
        val sampleInvoice = Invoice(
            id = "INV-001",
            clientName = "ABC Corp",
            amount = 1250.00,
            date = "2024-01-15",
            status = InvoiceStatus.SENT
        )
        InvoiceFormScreen(initialInvoice = sampleInvoice)
    }
}