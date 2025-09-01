package com.gigapingu.invoice4me.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.pdf.PdfDocument
import androidx.compose.runtime.Composable

private fun createBitmapFromComposable(
    context: Context,
    composable: @Composable (() -> Unit)
): Bitmap {
    TODO()
}

fun createPdfFromComposable(context: Context, source: @Composable (() -> Unit)): PdfDocument {
    TODO()
}

