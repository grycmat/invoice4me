package com.gigapingu.invoice4me

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun PdfPrinter(
    modifier: Modifier = Modifier
) {
    val composeView = ComposeView(LocalContext.current).apply {
        setContent {
            TempInvoicePdf()
        }
    }
        composeView.measure(0, 0)
    Box(
        modifier = modifier.wrapContentSize()
    ) {
    }

}

@Preview(name = "PdfPrinter")
@Composable
private fun PreviewPdfPrinter() {
    PdfPrinter()
}