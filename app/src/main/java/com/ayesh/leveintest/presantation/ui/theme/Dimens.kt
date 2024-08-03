package com.ayesh.leveintest.presantation.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Dimens(
    val logoSize: Dp = 42.dp,
    val defaultPadding: Dp = 20.dp,
    val textFieldHeight: Dp = 40.dp,
    val defaultTextFieldPadding: Dp = 5.dp,
    val defaultLineSpace: TextUnit = 25.sp,

    )


val LocalDimens = compositionLocalOf { Dimens() }

val dimensionScheme: Dimens
    @Composable
    @ReadOnlyComposable
    get() = LocalDimens.current
