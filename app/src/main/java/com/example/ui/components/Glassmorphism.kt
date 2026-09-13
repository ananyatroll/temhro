package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.ui.theme.ReaderBgDark

@Composable
fun GlassBackground(
    modifier: Modifier = Modifier,
    isDark: Boolean = true,
    content: @Composable BoxScope.() -> Unit
) {
    val baseBg = if (isDark) ReaderBgDark else Color(0xFFF8FAFC)
    val bgBrush = androidx.compose.runtime.remember(isDark) {
        if (isDark) {
            Brush.verticalGradient(
                colors = listOf(
                    ReaderBgDark,
                    Color(0xFF0F172A),
                    Color(0xFF0D1B2A)
                )
            )
        } else {
            Brush.verticalGradient(
                colors = listOf(
                    Color(0xFFF8FAFC),
                    Color(0xFFF1F5F9),
                    Color(0xFFE2E8F0)
                )
            )
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(baseBg)
            .background(bgBrush)
    ) {
        content()
    }
}

@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(22.dp),
    backgroundColor: Color = Color.White.copy(alpha = 0.12f),
    borderColor: Color = Color.White.copy(alpha = 0.22f),
    borderWidth: Dp = 1.dp,
    elevation: Dp = 2.dp,
    onClick: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    val shapeModifier = Modifier
        .shadow(elevation = elevation, shape = shape, clip = false)
        .clip(shape)
        .background(
            brush = Brush.linearGradient(
                colors = listOf(
                    backgroundColor,
                    backgroundColor.copy(alpha = (backgroundColor.alpha * 0.5f).coerceAtLeast(0.04f))
                ),
                start = Offset(0f, 0f),
                end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
            )
        )
        .border(
            width = borderWidth,
            brush = Brush.linearGradient(
                colors = listOf(
                    borderColor,
                    borderColor.copy(alpha = (borderColor.alpha * 0.25f).coerceAtLeast(0.04f))
                ),
                start = Offset(0f, 0f),
                end = Offset(800f, 800f)
            ),
            shape = shape
        )

    val combinedModifier = if (onClick != null) {
        modifier.then(shapeModifier).clickable { onClick() }
    } else {
        modifier.then(shapeModifier)
    }

    Column(
        modifier = combinedModifier.padding(16.dp),
        content = content
    )
}

fun Modifier.glassEffect(
    shape: Shape = RoundedCornerShape(20.dp),
    backgroundColor: Color = Color.White.copy(alpha = 0.12f),
    borderColor: Color = Color.White.copy(alpha = 0.22f),
    borderWidth: Dp = 1.dp
): Modifier = this
    .clip(shape)
    .background(
        brush = Brush.linearGradient(
            colors = listOf(
                backgroundColor,
                backgroundColor.copy(alpha = (backgroundColor.alpha * 0.45f).coerceAtLeast(0.04f))
            )
        )
    )
    .border(
        width = borderWidth,
        brush = Brush.linearGradient(
            colors = listOf(
                borderColor,
                borderColor.copy(alpha = 0.1f)
            )
        ),
        shape = shape
    )
