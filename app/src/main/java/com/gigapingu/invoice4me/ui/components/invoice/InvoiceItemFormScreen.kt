package com.gigapingu.invoice4me.ui.components.invoice

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gigapingu.invoice4me.model.*
import com.gigapingu.invoice4me.ui.components.ErrorMessage
import com.gigapingu.invoice4me.ui.theme.*
import com.gigapingu.invoice4me.utils.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale

@Composable
fun InvoiceItemFormScreen(
    modifier: Modifier = Modifier,
    initialItem: InvoiceItem? = null,
    onNavigateBack: () -> Unit = {},
    onSave: suspend (InvoiceItem) -> Result<Unit> = { Result.success(Unit) },
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    var formState by remember {
        mutableStateOf(
            if (initialItem != null) {
                InvoiceItemFormState(
                    tempId = initialItem.tempId,
                    name = initialItem.name,
                    legalBasis = initialItem.legalBasis,
                    quantity = formatQuantity(initialItem.quantity),
                    unitType = initialItem.unitType,
                    pricePerUnit = String.format(Locale.ROOT, "%.2f", initialItem.pricePerUnit)
                )
            } else {
                InvoiceItemFormState(
                    tempId = generateInvoiceItemId()
                )
            }
        )
    }

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val coroutineScope = rememberCoroutineScope()

    suspend fun handleSave() {
        keyboardController?.hide()
        
        val validation = validateInvoiceItemForm(formState)
        if (validation.isNotEmpty()) {
            formState = formState.copy(errors = validation)
            return
        }

        formState = formState.copy(isLoading = true, errors = emptyMap())
        
        try {
            val item = InvoiceItem(
                invoiceId = "", // Not associated with an invoice yet
                tempId = formState.tempId,
                name = formState.name.trim(),
                legalBasis = formState.legalBasis.trim(),
                quantity = formState.quantity.toDouble(),
                unitType = formState.unitType,
                pricePerUnit = formState.pricePerUnit.toDouble()
            )
            
            val result = onSave(item)
            delay(500) // UX feedback delay
            
            result.fold(
                onSuccess = { onNavigateBack() },
                onFailure = { error ->
                    formState = formState.copy(
                        isLoading = false,
                        errors = mapOf("general" to (error.message ?: "Failed to save item"))
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
            InvoiceItemFormHeader(
                title = if (initialItem != null) "Edit Item" else "Add Item",
                onNavigateBack = onNavigateBack
            )

            InvoiceItemFormCard(
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
private fun InvoiceItemFormHeader(
    title: String,
    onNavigateBack: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onNavigateBack,
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(GlassWhite20)
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Back",
                tint = TextPrimary,
                modifier = Modifier.size(20.dp)
            )
        }
        
        Spacer(modifier = Modifier.width(16.dp))
        
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            color = TextPrimary,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun InvoiceItemFormCard(
    formState: InvoiceItemFormState,
    onStateChange: (InvoiceItemFormState) -> Unit,
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

            // Item Name
            InvoiceFormField(
                label = "Item Name",
                value = formState.name,
                onValueChange = { 
                    onStateChange(formState.copy(name = it, errors = formState.errors - "name"))
                },
                placeholder = "Enter item name",
                isError = formState.errors.containsKey("name"),
                errorMessage = formState.errors["name"],
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                )
            )

            // Legal Basis
            InvoiceFormField(
                label = "Legal Basis (Optional)",
                value = formState.legalBasis,
                onValueChange = { 
                    onStateChange(formState.copy(legalBasis = it, errors = formState.errors - "legalBasis"))
                },
                placeholder = "Tax/legal reference",
                isError = formState.errors.containsKey("legalBasis"),
                errorMessage = formState.errors["legalBasis"],
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                )
            )

            // Quantity and Unit Type Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Quantity
                Column(modifier = Modifier.weight(1f)) {
                    InvoiceFormField(
                        label = "Quantity",
                        value = formState.quantity,
                        onValueChange = { value ->
                            if (validateQuantityInput(value)) {
                                onStateChange(formState.copy(quantity = value, errors = formState.errors - "quantity"))
                            }
                        },
                        placeholder = "0",
                        isError = formState.errors.containsKey("quantity"),
                        errorMessage = formState.errors["quantity"],
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Decimal,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Down) }
                        )
                    )
                }

                // Unit Type
                Column(modifier = Modifier.weight(1f)) {
                    UnitTypeSelector(
                        selectedUnit = formState.unitType,
                        onUnitSelected = { 
                            onStateChange(formState.copy(unitType = it))
                        }
                    )
                }
            }

            // Price per Unit
            InvoiceFormField(
                label = "Price per Unit",
                value = formState.pricePerUnit,
                onValueChange = { value ->
                    if (validatePriceInput(value)) {
                        onStateChange(formState.copy(pricePerUnit = value, errors = formState.errors - "pricePerUnit"))
                    }
                },
                placeholder = "0.00",
                prefix = "$",
                isError = formState.errors.containsKey("pricePerUnit"),
                errorMessage = formState.errors["pricePerUnit"],
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.clearFocus() }
                )
            )

            // Calculated Total Display
            TotalDisplayCard(total = formState.calculatedTotal)

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
                    Text(if (formState.isLoading) "Saving..." else "Save Item")
                }
            }
        }
    }
}

@Composable
private fun UnitTypeSelector(
    selectedUnit: UnitType,
    onUnitSelected: (UnitType) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        Text(
            text = "Unit Type",
            style = MaterialTheme.typography.labelLarge,
            color = TextSecondary,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Box {
            OutlinedTextField(
                value = "${selectedUnit.displayName} (${selectedUnit.symbol})",
                onValueChange = { },
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = true },
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = GlassWhite15,
                    focusedContainerColor = GlassWhite20,
                    unfocusedBorderColor = GlassWhite30,
                    focusedBorderColor = GlassWhite50,
                    unfocusedTextColor = TextPrimary,
                    focusedTextColor = TextPrimary,
                    cursorColor = TextPrimary
                ),
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Select unit",
                        tint = TextTertiary,
                        modifier = Modifier.clickable { expanded = true }
                    )
                }
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.background(
                    color = GlassWhite90,
                    shape = RoundedCornerShape(12.dp)
                )
            ) {
                UnitType.values().forEach { unitType ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = "${unitType.displayName} (${unitType.symbol})",
                                color = if (unitType == selectedUnit) TextPrimary else TextSecondary,
                                fontWeight = if (unitType == selectedUnit) FontWeight.Medium else FontWeight.Normal
                            )
                        },
                        onClick = {
                            onUnitSelected(unitType)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun TotalDisplayCard(total: Double) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = GlassWhite15
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Total",
                style = MaterialTheme.typography.titleMedium,
                color = TextSecondary,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = formatCurrency(total),
                style = MaterialTheme.typography.titleLarge,
                color = TextPrimary,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InvoiceItemFormScreenPreview() {
    Invoice4MeTheme {
        InvoiceItemFormScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun InvoiceItemFormScreenEditPreview() {
    Invoice4MeTheme {
        val sampleItem = InvoiceItem(
            invoiceId = "INV-PREVIEW-001",
            name = "Design Services",
            legalBasis = "Professional Services",
            quantity = 10.0,
            unitType = UnitType.HOURS,
            pricePerUnit = 75.0
        )
        InvoiceItemFormScreen(initialItem = sampleItem)
    }
}