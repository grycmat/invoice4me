package com.gigapingu.invoice4me.utils

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

fun calculatePadding(
    contentPadding: PaddingValues,
    layoutDirection: LayoutDirection
): PaddingValues {
    return PaddingValues(
        start = contentPadding.calculateStartPadding(layoutDirection) + 20.dp,
        top = contentPadding.calculateTopPadding() + 20.dp,
        end = contentPadding.calculateEndPadding(layoutDirection) + 20.dp,
        bottom = contentPadding.calculateBottomPadding() + 20.dp
    )
}