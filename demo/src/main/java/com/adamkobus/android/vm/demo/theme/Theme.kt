package com.adamkobus.android.vm.demo.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val ColorPalette = lightColors(
    primary = Color.DarkGray,
    primaryVariant = Color.Black,
    onPrimary = Color.White,
    secondary = Color.Gray,
    secondaryVariant = Color.Gray,
    onSecondary = Color.DarkGray,
    background = Color.LightGray,
    onBackground = Color.DarkGray,
    surface = Color.White,
    onSurface = Color.DarkGray
)

@Composable
fun DemoTheme(content: @Composable() () -> Unit) =
    MaterialTheme(
        colors = ColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
