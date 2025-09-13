package com.gigapingu.invoice4me.ui.screens

import android.content.Context
import android.graphics.drawable.Icon
import android.view.LayoutInflater
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.gigapingu.invoice4me.R
import com.gigapingu.invoice4me.ui.theme.TextTertiary


@Composable
fun InvoicePreview(
    modifier: Modifier = Modifier
) {
    Box(modifier.fillMaxSize()) {
        AndroidView(factory = { context: Context ->
            LayoutInflater
                .from(context)
                .inflate(R.layout.activity_invoice_pdf, null, false)
        }, modifier = Modifier.fillMaxSize())
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.End,
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.Bottom
        ) {
            Button(
                onClick = {},
                shape = RoundedCornerShape(50.dp),
                modifier = Modifier
            ) { Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Save",
                tint = TextTertiary
            ) }
        }
    }
}

@Preview(name = "InvoicePreview")
@Composable
private fun PreviewInvoicePreview() {
    InvoicePreview()
}