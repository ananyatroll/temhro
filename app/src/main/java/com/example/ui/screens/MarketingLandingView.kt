package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.StudyViewModel
import com.example.ui.components.*
import com.example.ui.theme.*

@Composable
fun MarketingLandingView(viewModel: StudyViewModel) {
    // Standard FAQ Model
    val isDarkTheme by viewModel.isDarkTheme.collectAsState()

    val faqs = listOf(
        FaqItem("Does this app support full offline study?", "Yes! ተምህሮ / Temhiro features complete high-performance offline caching. Once downloaded, all notes, textbook chapters, previous question banks, and flashcard sets are saved locally in SQLite, supporting full studies anywhere without any network connection."),
        FaqItem("How does the Telegram Bot integration work?", "Activating Telegram synchronization links your candidate profile to our automated bot network. It pushes threshold changes, exam metrics, and daily study reminders instantly to your account."),
        FaqItem("Is the EUEE Prep package updated regularly?", "Absolutely. We cross-reference the latest Ethiopian Ministry of Education requirements, seeding real national entrance questions, subject summaries, and answer rationales."),
        FaqItem("Are these study tools really free?", "Our Free Trial plans offer unlimited access to foundational chapters and past questions. Premium bundles for full school coverage are highly affordable.")
    )

    // Expanded states tracker
    val expandedFaqIndex = remember { mutableStateMapOf<Int, Boolean>() }

    val bgModifier = Modifier.background(Color.Transparent)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .then(bgModifier)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(top = 24.dp, bottom = 48.dp)
        ) {
            // Header close
            item {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(
                        onClick = { viewModel.showMarketingPage.value = false },
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(if (isDarkTheme) CardBgDark else IndigoLight)
                    ) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = if (isDarkTheme) Color.White else IndigoMedium)
                    }
                }
            }

            // Big Bold Title
            item {
                Text(
                    text = "Ethiopia's #1 Student Platform",
                    style = MaterialTheme.typography.displayLarge.copy(
                        fontWeight = FontWeight.Black,
                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                        lineHeight = 28.sp,
                        fontSize = 22.sp
                    ),
                    color = if (isDarkTheme) Color.White else IndigoSecondary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = "Our high-fidelity educational workspace elevates learning, driving stellar academic scores across regional high schools.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = if (isDarkTheme) TextMuted else Color.Gray,
                    textAlign = TextAlign.Center,
                    lineHeight = 22.sp,
                    modifier = Modifier.padding(vertical = 12.dp)
                )
            }

            // Infographic statistics data blocks
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    StatisticBlock(valueText = "50,000+", label = "Students Active", isDarkTheme = isDarkTheme, modifier = Modifier.weight(1.1f))
                    StatisticBlock(valueText = "200+", label = "Ethiopian Schools", isDarkTheme = isDarkTheme, modifier = Modifier.weight(0.9f))
                    StatisticBlock(valueText = "98%", label = "Success Rate", isDarkTheme = isDarkTheme, modifier = Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.height(32.dp))
            }

            // "How it Works" horizontal timeline guide
            item {
                Text(
                    text = "HOW IT WORKS",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Black,
                        fontSize = 15.sp,
                        letterSpacing = 0.5.sp
                    ),
                    color = IndigoSecondary,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                val timelineSteps = listOf(
                    TimelineStep("1", "Register", "Create your account details fully.", "profile"),
                    TimelineStep("2", "Select Package", "Pick EUEE or Freshman bundles.", "school"),
                    TimelineStep("3", "Study Tactile", "Consume notes and flashcards offline.", "book"),
                    TimelineStep("4", "Perfect Score", "Pass timed national examinations.", "rank")
                )

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
                    contentPadding = PaddingValues(end = 16.dp)
                ) {
                    items(timelineSteps) { step ->
                        Card(
                            modifier = Modifier
                                .width(160.dp)
                                .height(140.dp)
                                .border(1.dp, Color(0xFFF1F5F9), ChamferedCardShape),
                            shape = ChamferedCardShape,
                            colors = CardDefaults.cardColors(containerColor = Color.White)
                        ) {
                            Column(
                                modifier = Modifier.padding(12.dp).fillMaxSize(),
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(28.dp)
                                            .clip(CircleShape)
                                            .background(EmeraldLight),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(step.num, color = EmeraldDark, fontWeight = FontWeight.Bold)
                                    }

                                    DuotoneIcon(name = step.iconName, isActive = true, modifier = Modifier.size(20.dp))
                                }

                                Column {
                                    Text(step.title, style = MaterialTheme.typography.titleMedium, color = IndigoSecondary, fontWeight = FontWeight.Bold)
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(step.desc, style = MaterialTheme.typography.labelSmall, color = Color.Gray, lineHeight = 12.sp)
                                }
                            }
                        }
                    }
                }
            }

            // Interactive student testimonials carousel
            item {
                Text(
                    text = "SUCCESS TESTIMONIALS",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Black,
                        fontSize = 15.sp,
                        letterSpacing = 0.5.sp
                    ),
                    color = if (isDarkTheme) Color.White else IndigoSecondary,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                val testimonials = listOf(
                    Testimonial("Hewan Solomon", "EUEE Score: 586/600", "AAU Medicine Department", "Using these notes offline in my countryside hometown allowed me to excel tremendously without an active data subscription!"),
                    Testimonial("Dagnachew K.", "EUEE Score: 579/600", "ASTU Computer Engineering", "The timed exam modes gave me true exam pressure. Best preparational software in Ethiopia!"),
                    Testimonial("Nardos Abebe", "EUEE Score: 581/600", "AAU Architecture", "Flashcard repetition is absolute magic for Civics memory requirements. Strongly recommend the free trial.")
                )

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp)
                ) {
                    items(testimonials) { test ->
                        Card(
                            modifier = Modifier
                                .width(280.dp)
                                .height(180.dp)
                                .border(1.2.dp, GoldAccent.copy(alpha = 0.4f), ChamferedCardShape),
                            shape = ChamferedCardShape,
                            colors = CardDefaults.cardColors(containerColor = IndigoSecondary)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp).fillMaxSize(),
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(test.name, style = MaterialTheme.typography.titleMedium, color = Color.White, fontWeight = FontWeight.Bold)
                                        Text(
                                            text = test.score,
                                            color = GoldAccent,
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Black
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(test.placement, style = MaterialTheme.typography.labelSmall, color = HolographicAqua, fontWeight = FontWeight.Medium)
                                }

                                Text(
                                    text = "\"${test.text}\"",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color(0xFFD1D5DB),
                                    lineHeight = 16.sp,
                                    fontWeight = FontWeight.Normal
                                )
                            }
                        }
                    }
                }
            }

            // Accordion FAQs Section
            item {
                Text(
                    text = "FREQUENTLY ASKED QUESTIONS",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Black,
                        fontSize = 15.sp,
                        letterSpacing = 0.5.sp
                    ),
                    color = if (isDarkTheme) Color.White else IndigoSecondary,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
            }

            items(faqs.size) { idx ->
                val faq = faqs[idx]
                val exp = expandedFaqIndex[idx] ?: false

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .border(1.dp, if (isDarkTheme) Color.White.copy(alpha = 0.12f) else Color(0xFFF1F5F9), ChamferedCardShape)
                        .clickable { expandedFaqIndex[idx] = !exp },
                    shape = ChamferedCardShape,
                    colors = CardDefaults.cardColors(containerColor = if (isDarkTheme) CardBgDark else Color.White)
                ) {
                    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = faq.q,
                                style = MaterialTheme.typography.titleMedium,
                                color = if (isDarkTheme) Color.White else IndigoSecondary,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.weight(1f)
                            )
                            Icon(
                                imageVector = if (exp) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                contentDescription = "Toggle text",
                                tint = EmeraldPrimary
                            )
                        }

                        AnimatedVisibility(
                            visible = exp,
                            enter = expandVertically() + fadeIn(),
                            exit = shrinkVertically() + fadeOut()
                        ) {
                            Text(
                                text = faq.a,
                                style = MaterialTheme.typography.bodyMedium,
                                color = if (isDarkTheme) TextMuted else Color.DarkGray,
                                modifier = Modifier.padding(top = 12.dp),
                                lineHeight = 18.sp
                            )
                        }
                    }
                }
            }

            // Footer Call To Action
            item {
                Spacer(modifier = Modifier.height(32.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .pulseGlow(EmeraldPrimary),
                    shape = ChamferedCardShape,
                    colors = CardDefaults.cardColors(containerColor = IndigoSecondary)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Ready to start?",
                            style = MaterialTheme.typography.displayMedium,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "Take control of your national high-school and university threshold scores seamlessly.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextMuted,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(top = 4.dp, bottom = 20.dp)
                        )

                        Button(
                            onClick = { /* Telegram redirect link emulation */ },
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .pressBounce()
                                .testTag("telegram_marketing_button"),
                            colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Send, contentDescription = null, tint = Color.White)
                                Spacer(modifier = Modifier.width(12.dp))
                                Text("Register via Telegram", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun StatisticBlock(
    valueText: String,
    label: String,
    isDarkTheme: Boolean = false,
    modifier: Modifier = Modifier
) {
    val targetNumber = remember(valueText) {
        when {
            valueText.contains("50,000") -> 50
            valueText.contains("200") -> 200
            valueText.contains("98") -> 98
            else -> 0
        }
    }

    var animatedValue by remember { mutableStateOf(0) }

    LaunchedEffect(targetNumber) {
        if (targetNumber > 0) {
            val durationMs = 1200
            val steps = 24
            val delayValue = (durationMs / steps).toLong()
            for (step in 1..steps) {
                kotlinx.coroutines.delay(delayValue)
                animatedValue = (targetNumber * step) / steps
            }
        }
    }

    val displayString = remember(animatedValue, valueText) {
        when {
            valueText.contains("50,000") -> "${animatedValue},000+"
            valueText.contains("200") -> "${animatedValue}+"
            valueText.contains("98") -> "${animatedValue}%"
            else -> valueText
        }
    }

    Card(
        modifier = modifier
            .height(90.dp)
            .border(1.5.dp, GoldAccent.copy(alpha = 0.5f), ChamferedCardShape),
        shape = ChamferedCardShape,
        colors = CardDefaults.cardColors(containerColor = if (isDarkTheme) CardBgDark else Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = displayString,
                style = MaterialTheme.typography.displayMedium.copy(fontSize = 18.sp),
                color = if (isDarkTheme) GoldAccent else GoldDark,
                fontWeight = FontWeight.Black
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = if (isDarkTheme) TextMuted else Color.Gray,
                textAlign = TextAlign.Center,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

data class FaqItem(val q: String, val a: String)
data class TimelineStep(val num: String, val title: String, val desc: String, val iconName: String)
data class Testimonial(val name: String, val score: String, val placement: String, val text: String)
