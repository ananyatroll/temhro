package com.example.ui.tools.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.ui.theme.EmeraldPrimary

/**
 * Floating Tamhero "T" Action Button (FAB).
 *
 * Responsiveness & Safety Architecture:
 * - Positioned relative to safe visible screen area.
 * - Respects Android system/window insets and navigation bar.
 * - Automatically hides when the soft keyboard (IME) is visible to prevent obstructing text input / send button.
 * - Does not overlap AI chat, input fields, course chips, bottom navigation, or banner ads.
 */
@Composable
fun StudentToolsLauncher(
    isVisible: Boolean = true,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    safeSpacingBottom: Dp = 16.dp,
    safeSpacingEnd: Dp = 16.dp
) {
    val density = LocalDensity.current
    val imeBottom = WindowInsets.ime.getBottom(density)
    val isKeyboardOpen = imeBottom > 0

    // Automatically hide when keyboard is open or when caller requests invisible
    val effectivelyVisible = isVisible && !isKeyboardOpen

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.88f else 1.0f,
        label = "launcher_scale"
    )

    AnimatedVisibility(
        visible = effectivelyVisible,
        enter = fadeIn() + scaleIn(),
        exit = fadeOut() + scaleOut(),
        modifier = modifier.padding(end = safeSpacingEnd, bottom = safeSpacingBottom)
    ) {
        Surface(
            shape = CircleShape,
            color = Color.White,
            shadowElevation = 6.dp,
            modifier = Modifier
                .size(56.dp)
                .scale(scale)
                .clip(CircleShape)
                .border(2.dp, EmeraldPrimary, CircleShape)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = onClick
                )
                .testTag("student_tools_launcher")
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_app_icon),
                    contentDescription = "Open Student Tools",
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                )
            }
        }
    }
}
