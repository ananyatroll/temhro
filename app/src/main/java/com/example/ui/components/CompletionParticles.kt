package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.HolographicAqua
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

private data class Particle(
    val initialX: Float,
    val initialY: Float,
    val angle: Double,
    val speed: Float,
    val radius: Float,
    val color: Color,
    val rotation: Float
)

@Composable
fun CompletionParticleEffect(
    modifier: Modifier = Modifier,
    particleCount: Int = 45,
    colors: List<Color> = listOf(
        EmeraldPrimary,
        GoldAccent,
        HolographicAqua,
        Color(0xFF34D399),
        Color(0xFFFBBF24),
        Color.White
    )
) {
    val progress = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        progress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 2200, easing = FastOutSlowInEasing)
        )
    }

    val particles = remember {
        val list = mutableListOf<Particle>()
        val rnd = Random(System.currentTimeMillis())
        for (i in 0 until particleCount) {
            val angle = rnd.nextDouble(0.0, Math.PI * 2)
            val speed = rnd.nextFloat() * 320f + 80f
            val radius = rnd.nextFloat() * 4.5f + 2.5f
            val color = colors[rnd.nextInt(colors.size)]
            val rotation = rnd.nextFloat() * 360f
            list.add(Particle(0.5f, 0.5f, angle, speed, radius, color, rotation))
        }
        list
    }

    Canvas(modifier = modifier.fillMaxSize()) {
        val t = progress.value
        val centerX = size.width / 2f
        val centerY = size.height / 2f
        val alpha = (1f - t * t).coerceIn(0f, 1f)

        for (p in particles) {
            val distance = p.speed * t
            val gravity = 90f * t * t
            val x = centerX + (cos(p.angle) * distance).toFloat()
            val y = centerY + (sin(p.angle) * distance).toFloat() + gravity

            if (alpha > 0f) {
                drawCircle(
                    color = p.color.copy(alpha = alpha),
                    radius = (p.radius * (1f - t * 0.4f)).coerceAtLeast(1f),
                    center = Offset(x, y)
                )
            }
        }
    }
}
