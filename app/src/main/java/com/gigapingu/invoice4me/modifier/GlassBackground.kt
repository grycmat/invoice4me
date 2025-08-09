package com.gigapingu.invoice4me.modifier

import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.glassBackground(
    shape: Shape,
    color: Color,
    blurRadius: Dp = 16.dp
): Modifier = this.then(
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        Modifier.graphicsLayer(
            renderEffect = android.graphics.RenderEffect
                .createBlurEffect(
                    blurRadius.value, blurRadius.value,
                    android.graphics.Shader.TileMode.DECAL
                )
                .asComposeRenderEffect(),
            shape = shape,
            clip = true
        )
    } else {
        Modifier
    }
).background(
    color = color,
    shape = shape
)