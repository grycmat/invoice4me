package com.gigapingu.invoice4me.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.pdf.PdfDocument
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ScrollView
import androidx.core.graphics.createBitmap
import kotlin.math.max

private fun createBitmapFromView(
    context: Context,
    targetWidth: Int = BITMAP_WIDTH,
    targetHeight: Int = A4_HEIGHT_POINTS
): Result<Bitmap> = runCatching {
    val inflater = LayoutInflater.from(context)
    val rootView =
        inflater.inflate(com.gigapingu.invoice4me.R.layout.activity_invoice_pdf, null)

    val scrollView = rootView as? ScrollView
        ?: throw IllegalStateException("Root view must be a ScrollView")

    val contentView = scrollView.getChildAt(0)
        ?: throw IllegalStateException("ScrollView must have content")

    val widthSpec = View.MeasureSpec.makeMeasureSpec(targetWidth, View.MeasureSpec.EXACTLY)
    val heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)

    scrollView.measure(widthSpec, heightSpec)
    contentView.measure(widthSpec, heightSpec)

    val actualWidth = max(scrollView.measuredWidth, targetWidth)
    val actualHeight = max(contentView.measuredHeight, MIN_HEIGHT)

    scrollView.layout(0, 0, actualWidth, actualHeight)
    contentView.layout(0, 0, actualWidth, contentView.measuredHeight)

    Log.d(
        "PdfUtils",
        "ScrollView measured: ${scrollView.measuredWidth}x${scrollView.measuredHeight}"
    )
    Log.d(
        "PdfUtils",
        "Content measured: ${contentView.measuredWidth}x${contentView.measuredHeight}"
    )
    Log.d("PdfUtils", "Bitmap dimensions: ${actualWidth}x${actualHeight}")

    validateBitmapDimensions(actualWidth, actualHeight)

    val bitmap = createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888).apply {
        eraseColor(Color.WHITE)
    }

    val canvas = Canvas(bitmap)
    scrollView.draw(canvas)

    bitmap
}

fun createInvoicePdf(
    context: Context,
    targetWidth: Int = BITMAP_WIDTH,
    targetHeight: Int = A4_HEIGHT_POINTS
): Result<PdfDocument> = runCatching {
    val bitmapResult = createBitmapFromView(context, targetWidth, targetHeight)
    val bitmap = bitmapResult.getOrThrow()
    
    val pdfDocument = PdfDocument()
    val pageInfo = PdfDocument.PageInfo.Builder(A4_WIDTH_POINTS, A4_HEIGHT_POINTS, 1).create()
    val page = pdfDocument.startPage(pageInfo)
    
    val canvas = page.canvas
    
    // Scale bitmap to fit A4 page while maintaining aspect ratio
    val scaleX = A4_WIDTH_POINTS.toFloat() / bitmap.width
    val scaleY = A4_HEIGHT_POINTS.toFloat() / bitmap.height
    val scale = minOf(scaleX, scaleY)
    
    // Center the bitmap on the page
    val scaledWidth = bitmap.width * scale
    val scaledHeight = bitmap.height * scale
    val x = (A4_WIDTH_POINTS - scaledWidth) / 2
    val y = (A4_HEIGHT_POINTS - scaledHeight) / 2
    
    val destRect = android.graphics.Rect(
        x.toInt(),
        y.toInt(),
        (x + scaledWidth).toInt(),
        (y + scaledHeight).toInt()
    )
    
    canvas.drawBitmap(bitmap, null, destRect, null)
    pdfDocument.finishPage(page)
    
    bitmap.recycle()
    
    pdfDocument
}

private fun validateBitmapDimensions(width: Int, height: Int) {
    require(width > 0 && height > 0) { "Bitmap dimensions must be positive: ${width}x${height}" }
    require(width <= MAX_BITMAP_SIZE && height <= MAX_BITMAP_SIZE) {
        "Bitmap dimensions too large: ${width}x${height}. Max allowed: $MAX_BITMAP_SIZE"
    }

    val totalPixels = width.toLong() * height.toLong()
    require(totalPixels <= MAX_PIXELS) {
        "Bitmap too large: $totalPixels pixels. Max allowed: $MAX_PIXELS"
    }
}

private const val A4_WIDTH_POINTS = 595
private const val BITMAP_WIDTH = 1200

private const val A4_HEIGHT_POINTS = 842
private const val MIN_HEIGHT = 100
private const val MAX_BITMAP_SIZE = 4096
private const val MAX_PIXELS = 16_000_000L