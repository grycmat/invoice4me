package com.gigapingu.invoice4me.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.pdf.PdfDocument
import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView

private fun createBitmapFromComposable(
    context: Context,
    composable: @Composable (() -> Unit)
): Bitmap {
    val view = ComposeView(context)
    view.setContent(composable)

    view.measure(
        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
    )
    view.layout(0, 0, view.measuredWidth, view.measuredHeight)

    val bitmap = Bitmap.createBitmap(view.measuredWidth, view.measuredHeight, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    view.draw(canvas)

    return bitmap
}

fun createPdfFromComposable(context: Context, source: @Composable (() -> Unit)): PdfDocument {
    val bitmap = createBitmapFromComposable(context, source)

    val document = PdfDocument()
    val pageInfo = PdfDocument.PageInfo.Builder(bitmap.width, bitmap.height, 1).create()
    val page = document.startPage(pageInfo)

    page.canvas.drawBitmap(bitmap, 0f, 0f, null)
    document.finishPage(page)

    return document
}

