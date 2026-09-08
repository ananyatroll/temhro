package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import com.example.ui.theme.*

// 1. Sleek smooth glassmorphic rounded shapes
val ChamferedCardShape = RoundedCornerShape(20.dp)

val AsymmetricHeaderShape = RoundedCornerShape(
    topStart = 0.dp,
    topEnd = 0.dp,
    bottomEnd = 24.dp,
    bottomStart = 24.dp
)

val HexagonalCutShape = RoundedCornerShape(16.dp)

// 1.1 Professional Polish Shapes
val DailyChallengeAsymmetricShape = RoundedCornerShape(24.dp)

val SubjectCardRoundedShape = RoundedCornerShape(24.dp)

// 1.2 Custom Bottom Border Modifier
fun Modifier.bottomBorder(width: androidx.compose.ui.unit.Dp, color: Color): Modifier = this.drawBehind {
    val strokeWidth = width.toPx()
    val y = size.height - strokeWidth / 2
    drawLine(
        color = color,
        start = Offset(0f, y),
        end = Offset(size.width, y),
        strokeWidth = strokeWidth
    )
}

// 2. Holographic Shimmer Modifier
fun Modifier.holographicShimmer(): Modifier = composed {
    val transition = rememberInfiniteTransition(label = "shimmer")
    val translateAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "trans"
    )

    val shimmerColors = listOf(
        Color.Transparent,
        HolographicAqua.copy(alpha = 0.3f),
        HolographicPink.copy(alpha = 0.3f),
        Color.Transparent,
    )

    this.drawBehind {
        val brush = Brush.linearGradient(
            colors = shimmerColors,
            start = Offset(translateAnim.value - 300f, translateAnim.value - 300f),
            end = Offset(translateAnim.value + 300f, translateAnim.value + 300f)
        )
        drawRect(brush = brush)
    }
}

// 3. Pulsing Glow Modifier
fun Modifier.pulseGlow(color: Color = EmeraldPrimary): Modifier = composed {
    val transition = rememberInfiniteTransition(label = "glow")
    val alphaAnim by transition.animateFloat(
        initialValue = 0.15f,
        targetValue = 0.65f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = EaseInOutQuad),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glowAlpha"
    )
    val scaleAnim by transition.animateFloat(
        initialValue = 1.0f,
        targetValue = 1.04f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = EaseInOutQuad),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glowScale"
    )

    this.graphicsLayer {
        scaleX = scaleAnim
        scaleY = scaleAnim
    }
    // Draws a dynamic glowing background border
    this.drawBehind {
        drawCircle(
            color = color,
            alpha = alphaAnim,
            radius = size.width.coerceAtLeast(size.height) * 0.52f
        )
    }
}

// 4. Fluid press effect modifier (WWDC Designing Fluid Interfaces: instant feedback, critically damped spring)
fun Modifier.pressBounce(
    pressedScale: Float = 0.97f,
    dampingRatio: Float = Spring.DampingRatioLowBouncy,
    stiffness: Float = Spring.StiffnessMediumLow
): Modifier = composed {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) pressedScale else 1.0f,
        animationSpec = spring(
            dampingRatio = dampingRatio,
            stiffness = stiffness
        ),
        label = "fluidPressScale"
    )

    this
        .scale(scale)
        .pointerInput(Unit) {
            awaitPointerEventScope {
                while (true) {
                    val down = awaitFirstDown(requireUnconsumed = false)
                    isPressed = true
                    val pointerId = down.id
                    var cancelled = false
                    while (!cancelled) {
                        val event = awaitPointerEvent()
                        val consumed = event.changes.any { it.isConsumed }
                        val released = event.changes.firstOrNull { it.id == pointerId }?.pressed == false
                        val positionChange = event.changes.firstOrNull { it.id == pointerId }?.let {
                            (it.position - it.previousPosition).getDistance()
                        } ?: 0f
                        if (consumed || released || positionChange > 10f) {
                            isPressed = false
                            cancelled = true
                        }
                    }
                }
            }
        }
}

// 5. Custom Duotone Gradients layered Icons
@Composable
fun DuotoneIcon(
    name: String,
    modifier: Modifier = Modifier,
    isActive: Boolean = false,
    badgeCount: Int = 0
) {
    val primaryColor = if (isActive) EmeraldPrimary else IndigoSecondary
    val secondaryColor = if (isActive) HolographicAqua else GoldAccent
    
    // Pick vector icon
    val icon: ImageVector = when (name.lowercase()) {
        "home" -> Icons.Default.Home
        "notes" -> Icons.Default.Description
        "exams" -> Icons.Default.Assignment
        "flashcard" -> Icons.Default.Layers
        "video" -> Icons.Default.PlayCircle
        "rank" -> Icons.Default.Leaderboard
        "profile" -> Icons.Default.Person
        "biology" -> Icons.Default.Grass
        "civics" -> Icons.Default.Gavel
        "chemistry" -> Icons.Default.Science
        "anthropology" -> Icons.Default.HistoryEdu
        "search" -> Icons.Default.Search
        "bell" -> Icons.Default.Notifications
        "help" -> Icons.AutoMirrored.Filled.Help
        "maths", "mathematics" -> Icons.Default.Calculate
        "english" -> Icons.Default.MenuBook
        "aptitude" -> Icons.Default.Lightbulb
        "physics" -> Icons.Default.Bolt
        "history" -> Icons.Default.AutoStories
        "geography" -> Icons.Default.Public
        "economics" -> Icons.Default.TrendingUp
        "computer" -> Icons.Default.Computer
        "business" -> Icons.Default.Business
        "fitness" -> Icons.Default.FitnessCenter
        "psychology" -> Icons.Default.Psychology
        "groups" -> Icons.Default.Groups
        "analytics" -> Icons.Default.Analytics
        "accounting" -> Icons.Default.AccountBalance
        "management" -> Icons.Default.ManageAccounts
        "logistics" -> Icons.Default.LocalShipping
        "bais" -> Icons.Default.BusinessCenter
        "psir" -> Icons.Default.AccountBalance
        "marketing" -> Icons.Default.Storefront
        "padm" -> Icons.Default.CorporateFare
        "info_sci" -> Icons.Default.DataUsage
        "software" -> Icons.Default.Code
        "mechanical" -> Icons.Default.Engineering
        "electrical" -> Icons.Default.ElectricalServices
        "euee" -> Icons.Default.School
        "freshman" -> Icons.Default.LocalLibrary
        "aau_uat", "uat" -> Icons.Default.AutoAwesome
        "department", "dept" -> Icons.Default.AccountBalance
        "exit_exam", "exit" -> Icons.Default.WorkspacePremium
        "lock" -> Icons.Default.Lock
        "flame", "streak" -> Icons.Default.LocalFireDepartment
        "star" -> Icons.Default.Star
        "bolt" -> Icons.Default.Bolt
        "trophy" -> Icons.Default.EmojiEvents
        "check" -> Icons.Default.CheckCircle
        "book" -> Icons.Default.MenuBook
        else -> Icons.Default.School
    }

    Box(
        modifier = modifier.size(36.dp),
        contentAlignment = Alignment.Center
    ) {
        // Active pulsing glow
        if (isActive && (name == "home" || name == "notes" || name == "exams" || name == "flashcard" || name == "video" || name == "rank" || name == "profile")) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(EmeraldPrimary.copy(alpha = 0.15f))
            )
        }

        // Duotone Layer 1: Background shadow offset icon
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = secondaryColor.copy(alpha = 0.4f),
            modifier = Modifier
                .offset(x = 2.dp, y = 2.dp)
                .size(26.dp)
        )

        // Duotone Layer 2: Core foreground icon
        Icon(
            imageVector = icon,
            contentDescription = name,
            tint = primaryColor,
            modifier = Modifier.size(24.dp)
        )

        // Badge indicator
        if (badgeCount > 0) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = 3.dp, y = (-3).dp)
                    .background(GoldAccent, shape = RoundedCornerShape(4.dp))
                    .padding(horizontal = 4.dp, vertical = 2.dp)
            ) {
                Text(
                    text = badgeCount.toString(),
                    color = Color.Black,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
