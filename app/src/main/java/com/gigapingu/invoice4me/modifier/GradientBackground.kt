package com.gigapingu.invoice4me.modifier

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import com.gigapingu.invoice4me.ui.theme.GlassBlue1
import com.gigapingu.invoice4me.ui.theme.GlassBlue2
import com.gigapingu.invoice4me.ui.theme.GlassPink1
import com.gigapingu.invoice4me.ui.theme.GlassPink2

@Composable
fun Modifier.gradientBackground(
    isDarkTheme: Boolean = isSystemInDarkTheme()
): Modifier = this.fillMaxSize()
    .background(
        brush = Brush.verticalGradient(
            colors = if (isDarkTheme) {
                listOf(
                    GlassBlue2,
                    GlassPink2
                )
            } else {
                listOf(
                    GlassBlue1,
                    GlassPink1
                )
            }
        )
    )