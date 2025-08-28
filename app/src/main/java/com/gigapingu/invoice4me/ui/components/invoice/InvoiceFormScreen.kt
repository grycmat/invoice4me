package com.gigapingu.invoice4me.ui.components.invoice

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
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
import com.gigapingu.invoice4me.model.InvoiceItem
import com.gigapingu.invoice4me.model.InvoiceStatus
import com.gigapingu.invoice4me.ui.components.ErrorMessage
import com.gigapingu.invoice4me.ui.theme.*
import com.gigapingu.invoice4me.utils.generateInvoiceId
import com.gigapingu.invoice4me.utils.getCurrentDate
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun InvoiceFormScreen(
    initialInvoice: Invoice? = null,
    onNavigateBack: () -> Unit = {},
    onSave: suspend (Invoice) -> Result<Unit> = { Result.success(Unit) },
    onNavigateToAddItem: () -> Unit = {},
    onNavigateToEditItem: (InvoiceItem) -> Unit = {},
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    var formState by remember {
        mutableStateOf(
            if (initialInvoice != null) {
                InvoiceFormState(
                    id = initialInvoice.id,
                    clientName = initialInvoice.clientName,
                    amount = initialInvoice.amount.toString(),
                    date = initialInvoice.date,
                    status = initialInvoice.status,
                    items = initialInvoice.items
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
            val finalAmount = if (formState.shouldUseCalculatedTotal) {
                formState.calculatedTotal
            } else {
                formState.amount.toDouble()
            }
            
            val invoice = Invoice(
                id = formState.id,
                clientName = formState.clientName.trim(),
                amount = finalAmount,
                date = formState.date,
                status = formState.status,
                items = formState.items
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
                onNavigateToAddItem = onNavigateToAddItem,
                onNavigateToEditItem = onNavigateToEditItem,
                focusManager = focusManager
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
    onNavigateToAddItem: () -> Unit,
    onNavigateToEditItem: (InvoiceItem) -> Unit,
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

            // Amount (calculated or manual)
            InvoiceFormField(
                label = if (formState.shouldUseCalculatedTotal) "Total Amount (Calculated)" else "Amount",
                value = formState.displayAmount,
                onValueChange = { 
                    if (!formState.shouldUseCalculatedTotal && (it.isEmpty() || it.matches(Regex("^\\d*\\.?\\d*$")))) {
                        onStateChange(formState.copy(amount = it, errors = formState.errors - "amount"))
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
                            items = formState.items.filter { it.id != itemToDelete.id }
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


@Preview(showBackground = true)
@Composable
fun InvoiceFormScreenPreview() {
    Invoice4MeTheme {
        InvoiceFormScreen()
    }
}

import androidx.lifecycle.viewmodel.compose.viewModel
import com.gigapingu.invoice4me.Invoice4MeApplication
import com.gigapingu.invoice4me.ui.InvoiceViewModel
import com.gigapingu.invoice4me.ui.InvoiceViewModelFactory

@Composable
fun InvoiceFormScreenWithState(
    initialItems: List<InvoiceItem> = emptyList(),
    initialInvoice: Invoice? = null,
    onNavigateBack: () -> Unit = {},
    onNavigateToAddItem: () -> Unit = {},
    onNavigateToEditItem: (InvoiceItem) -> Unit = {},
    onItemsChanged: (List<InvoiceItem>) -> Unit = {},
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    val context = LocalContext.current
    val invoiceViewModel: InvoiceViewModel = viewModel(
        factory = InvoiceViewModelFactory((context.applicationContext as Invoice4MeApplication).repository)
    )

    var formState by remember {
        mutableStateOf(
            if (initialInvoice != null) {
                InvoiceFormState(
                    id = initialInvoice.id,
                    clientName = initialInvoice.clientName,
                    amount = initialInvoice.amount.toString(),
                    date = initialInvoice.date,
                    status = initialInvoice.status,
                    items = initialInvoice.items
                )
            } else {
                InvoiceFormState(
                    id = generateInvoiceId(),
                    date = getCurrentDate(),
                    items = initialItems
                )
            }
        )
    }

    // Update items when external state changes
    LaunchedEffect(initialItems) {
        if (formState.items != initialItems) {
            formState = formState.copy(items = initialItems)
        }
    }

    // Notify parent when items change
    LaunchedEffect(formState.items) {
        onItemsChanged(formState.items)
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
            val finalAmount = if (formState.shouldUseCalculatedTotal) {
                formState.calculatedTotal
            } else {
                formState.amount.toDouble()
            }

            val invoice = Invoice(
                id = formState.id,
                clientName = formState.clientName.trim(),
                amount = finalAmount,
                date = formState.date,
                status = formState.status,
                items = formState.items
            )

            invoiceViewModel.insert(invoice)
            delay(500) // UX feedback delay
            onNavigateBack()

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
                onNavigateToAddItem = onNavigateToAddItem,
                onNavigateToEditItem = onNavigateToEditItem,
                focusManager = focusManager
            )
        }
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
            status = InvoiceStatus.SENT,
            items = emptyList()
        )
        InvoiceFormScreen(initialInvoice = sampleInvoice)
    }
}