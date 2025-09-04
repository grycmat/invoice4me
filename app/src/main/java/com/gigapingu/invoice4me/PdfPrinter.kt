package com.gigapingu.invoice4me

import android.graphics.Picture
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.drawscope.draw
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.createBitmap

@Composable
fun PdfPrinter(
    modifier: Modifier = Modifier
) {
    val ctx = LocalContext.current
    var picture: Picture? by remember { mutableStateOf(null) }
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.CreateDocument("image/png")) { uri ->
            if (uri != null) {
                ctx.contentResolver.openOutputStream(uri).use {
                    if (it != null && picture != null) {
                        val bitmap = createBitmap(picture!!.width, picture!!.height)
                        val canvas = android.graphics.Canvas(bitmap)
                        picture!!.draw(canvas)
                        bitmap.compress(android.graphics.Bitmap.CompressFormat.PNG, 100, it)
                        bitmap.recycle()
                    }
                }
            }
        }
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(modifier = Modifier.padding(vertical = 100.dp), onClick = {
                launcher.launch("saved_image.png")
            }) {
                Text("PDF")
            }
        }
    ) { innerPadding ->
        Box(
            modifier = modifier
                .padding(innerPadding)
                .drawWithContent {
                    val width = 500
                    val height = 700
                    val pic = Picture()
                    val canvas = Canvas(pic.beginRecording(width, height))
                    draw(this, this.layoutDirection, canvas, this.size) {
                        this@drawWithContent.drawContent()
                    }
                    pic.endRecording()
                    picture = pic
                    drawContent()
                }
        ) {
            TempInvoicePdf()
        }
    }
}

@Preview(name = "PdfPrinter")
@Composable
private fun PreviewPdfPrinter() {
    PdfPrinter()
}