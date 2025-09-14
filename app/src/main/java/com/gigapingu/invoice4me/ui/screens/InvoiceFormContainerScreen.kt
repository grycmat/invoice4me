package com.gigapingu.invoice4me.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gigapingu.invoice4me.Invoice4MeApplication
import com.gigapingu.invoice4me.model.Invoice
import com.gigapingu.invoice4me.model.InvoiceItem
import com.gigapingu.invoice4me.modifier.gradientBackground
import com.gigapingu.invoice4me.ui.InvoiceViewModel
import com.gigapingu.invoice4me.ui.InvoiceViewModelFactory
import com.gigapingu.invoice4me.ui.components.invoice.InvoiceFormCard
import com.gigapingu.invoice4me.ui.components.invoice.InvoiceFormHeader
import com.gigapingu.invoice4me.ui.components.invoice.InvoiceFormState
import com.gigapingu.invoice4me.ui.components.invoice.validateForm
import com.gigapingu.invoice4me.ui.theme.GlassBlue1
import com.gigapingu.invoice4me.ui.theme.GlassPink1
import com.gigapingu.invoice4me.utils.generateInvoiceId
import com.gigapingu.invoice4me.utils.getCurrentDate
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun InvoiceFormContainerScreen(
    modifier: Modifier = Modifier,
    initialItems: List<InvoiceItem> = emptyList(),
    initialInvoice: Invoice? = null,
    onNavigateBack: () -> Unit = {},
    onNavigateToAddItem: () -> Unit = {},
    onNavigateToEditItem: (InvoiceItem) -> Unit = {},
    onItemsChanged: (List<InvoiceItem>) -> Unit = {},
    onOpenPreview: () -> Unit = {},
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
            .gradientBackground()
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
                focusManager = focusManager,
                onOpenPreview = onOpenPreview
            )
        }
    }
}
