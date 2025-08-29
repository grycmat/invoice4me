package com.gigapingu.invoice4me.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.RectF
import android.graphics.pdf.PdfDocument
import android.os.Environment
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.core.view.drawToBitmap
import java.io.File
import java.io.FileOutputStream

fun createBitmapFromComposable(context: Context, composable: @Composable () -> Unit): Bitmap {
    val view = ComposeView(context)
    view.setContent(composable)

    view.layout(0, 0, (595 * context.resources.displayMetrics.density).toInt(), (842 * context.resources.displayMetrics.density).toInt())
    return view.drawToBitmap()
}

fun createPdfFromComposable(context: Context, source: @Composable () -> Unit): PdfDocument {
    val pdfDocument = PdfDocument()

    // A4 dimensions in points (1 point = 1/72 inch)
    val a4WidthPoints = 595
    val a4HeightPoints = 842
    val pageInfo = PdfDocument.PageInfo.Builder(a4WidthPoints, a4HeightPoints, 1).create()
    val page = pdfDocument.startPage(pageInfo)
    val canvas = page.canvas

    val invoiceBitmap = createBitmapFromComposable(context) { source() }

    // Define a RectF for the destination, scaling the bitmap to fit the A4 page with margins
    val margin = 40f
    val dstRect = RectF(margin, margin, a4WidthPoints - margin, a4HeightPoints - margin)

    // Draw the bitmap onto the canvas, scaling to fit the destination rectangle
    canvas.drawBitmap(invoiceBitmap, null, dstRect, null)

    pdfDocument.finishPage(page)

    return pdfDocument
}

fun saveToStorage(context: Context, pdfDocument: PdfDocument, fileName: String) {
    val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
    val file = File(downloadsDir, fileName)
    try {
        val fos = FileOutputStream(file)
        pdfDocument.writeTo(fos)
        pdfDocument.close()
        fos.close()
        Toast.makeText(context, "PDF saved to ${file.absolutePath}", Toast.LENGTH_LONG).show()
    } catch (e: Exception) {
        e.printStackTrace()
        Toast.makeText(context, "Error creating PDF: ${e.message}", Toast.LENGTH_LONG).show()
    }
}