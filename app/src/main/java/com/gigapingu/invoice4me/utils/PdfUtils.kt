package com.gigapingu.invoice4me.utils


import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.pdf.PdfDocument
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Composition
import androidx.compose.runtime.Recomposer
import androidx.compose.ui.platform.AndroidUiDispatcher
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.graphics.createBitmap
import androidx.core.view.doOnLayout
import androidx.core.view.doOnPreDraw
import com.gigapingu.invoice4me.TempInvoicePdf


//private fun createBitmapFromComposable(
//    context: Context,
//    width: Int,
//    height: Int,
//    content: @Composable () -> Unit
//): Bitmap {
//
//    val recomposer = Recomposer(AndroidUiDispatcher.CurrentThread)
//val containerView = FrameLayout(context)
//
//    val displayMetrics = context.resources.displayMetrics
//
////    composeView.measure(
////        View.MeasureSpec.makeMeasureSpec(
////            displayMetrics.widthPixels,
////            View.MeasureSpec.EXACTLY
////        ),
////        View.MeasureSpec.makeMeasureSpec(
////            displayMetrics.heightPixels,
////            View.MeasureSpec.EXACTLY
////        )
////    )
//
//    composeView.layout(0, 0, width, height)
//
//    val bitmap = createBitmap(width, height, Bitmap.Config.ARGB_8888)
//        val canvas = Canvas(bitmap)
//        composeView.draw(canvas)
//        composeView.invalidate()
//    return bitmap
//}

//fun createPdfFromComposable(
//    context: Context,
//    width: Int = 595,
//    height: Int = 842,
//    content: @Composable () -> Unit
//): PdfDocument {
//    val pdfDocument = PdfDocument()
//    val pageInfo = PdfDocument.PageInfo.Builder(width, height, 1).create()
//    val page = pdfDocument.startPage(pageInfo)
//
//
//    val bitmap = createBitmapFromComposable(context, width, height) { content() }
//    page.canvas.drawBitmap(bitmap, 0f, 0f, null)
//    pdfDocument.finishPage(page)
//
//    bitmap.recycle()
//
//    return pdfDocument
//}
