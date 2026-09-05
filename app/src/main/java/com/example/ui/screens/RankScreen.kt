package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.StudyViewModel
import com.example.ui.TranslationManager
import com.example.ui.components.*
import com.example.ui.theme.*

@Composable
fun RankScreen(viewModel: StudyViewModel) {
    val progress by viewModel.userProgress.collectAsState()
    val currentLang by viewModel.currentLanguage.collectAsState()
    val isDarkTheme by viewModel.isDarkTheme.collectAsState()

    // Mock high-performing students based in Ethiopia
    val leaderboard = listOf(
        LeaderboardUser("Yonas Melaku", 920, "EUEE Score: 591/600", "AAU Medicine"),
        LeaderboardUser("Samrawit Kassa", 880, "EUEE Score: 586/600", "AAU Engineering"),
        LeaderboardUser("Dagmawi Tadesse", 850, "EUEE Score: 580/600", "ASTU Computer Science"),
        LeaderboardUser("Hilina Bekele", 790, "EUEE Score: 574/600", "Mekelle Medical"),
        LeaderboardUser("Ananya Bayable (You)", progress.scoreCount, "In Progress", "Goal: AAU Tech"),
        LeaderboardUser("Bereket Lemma", 610, "EUEE Score: 561/600", "Adama Science & Tech")
    ).sortedByDescending { it.score }

    val userRank = leaderboard.indexOfFirst { it.name.startsWith(progress.username) || it.name.contains("(You)") } + 1

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
            contentPadding = PaddingValues(top = 24.dp, bottom = 80.dp)
        ) {
            item {
                Text(
                    text = TranslationManager.get("eth_elite_ranks", currentLang),
                    style = MaterialTheme.typography.displayLarge.copy(
                        fontWeight = FontWeight.Black,
                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                        lineHeight = 28.sp,
                        fontSize = 22.sp
                    ),
                    color = if (isDarkTheme) Color.White else IndigoSecondary
                )
                Text(
                    text = TranslationManager.get("compete_nationwide", currentLang),
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (isDarkTheme) TextMuted else Color.Gray,
                    modifier = Modifier.padding(top = 4.dp, bottom = 24.dp)
                )
            }

            // Stat Blocks
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Card(
                        modifier = Modifier
                            .weight(1f),
                        shape = SubjectCardRoundedShape,
                        border = BorderStroke(1.dp, if (isDarkTheme) Color(0xFF334155) else GoldAccent.copy(alpha = 0.5f)),
                        colors = CardDefaults.cardColors(containerColor = if (isDarkTheme) CardBgDark else IndigoSecondary)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(Icons.Default.EmojiEvents, contentDescription = null, tint = GoldAccent, modifier = Modifier.size(32.dp))
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(TranslationManager.get("your_rank", currentLang), style = MaterialTheme.typography.labelSmall, color = TextMuted)
                            Text(
                                text = "#${if (userRank > 0) userRank else 5}",
                                style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Black),
                                color = Color.White
                            )
                        }
                    }

                    Card(
                        modifier = Modifier
                            .weight(1f),
                        shape = SubjectCardRoundedShape,
                        border = BorderStroke(1.dp, if (isDarkTheme) Color(0xFF334155) else EmeraldPrimary.copy(alpha = 0.5f)),
                        colors = CardDefaults.cardColors(containerColor = if (isDarkTheme) CardBgDark else Color.White)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(Icons.Default.Star, contentDescription = null, tint = EmeraldPrimary, modifier = Modifier.size(32.dp))
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(TranslationManager.get("study_points", currentLang), style = MaterialTheme.typography.labelSmall, color = if (isDarkTheme) TextMuted else Color.Gray)
                            Text(
                                text = "${progress.scoreCount} pts",
                                style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Black),
                                color = if (isDarkTheme) Color.White else IndigoSecondary
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = TranslationManager.get("national_leaderboard", currentLang),
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Black,
                        fontSize = 15.sp,
                        letterSpacing = 0.5.sp
                    ),
                    color = if (isDarkTheme) Color.White else IndigoSecondary,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
            }

            // Leaderboard Items
            itemsIndexed(leaderboard) { idx, user ->
                val isMe = user.name.contains("(You)")
                val rankNum = idx + 1

                val itemBg = if (isMe) {
                    if (isDarkTheme) Color(0xFF064E3B) else EmeraldLight
                } else {
                    if (isDarkTheme) CardBgDark else Color.White
                }

                val itemBorderColor = if (isMe) {
                    EmeraldPrimary
                } else {
                    if (isDarkTheme) Color(0xFF334155) else Color(0xFFE2E8F0)
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    shape = SubjectCardRoundedShape,
                    border = BorderStroke(1.dp, itemBorderColor),
                    colors = CardDefaults.cardColors(
                        containerColor = itemBg
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            // Rank number circle
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(
                                        when (rankNum) {
                                            1 -> GoldAccent
                                            2 -> Color.LightGray
                                            3 -> Color(0xFFCD7F32) // Bronze
                                            else -> if (isDarkTheme) Color(0xFF1E293B) else IndigoLight
                                        }
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = rankNum.toString(),
                                    color = if (rankNum <= 3) Color.White else if (isDarkTheme) Color.White else IndigoSecondary,
                                    fontWeight = FontWeight.Black,
                                    fontSize = 14.sp
                                )
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Column {
                                val displayName = if (isMe) progress.username + " (You)" else user.name
                                Text(
                                    text = displayName,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = if (isDarkTheme) Color.White else IndigoSecondary,
                                    fontWeight = if (isMe) FontWeight.Bold else FontWeight.Normal
                                )
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    modifier = Modifier.padding(top = 2.dp)
                                ) {
                                    Text(
                                        text = user.eueeScore,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = if (rankNum == 1) GoldDark else if (isDarkTheme) TextMuted else Color.Gray,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = "• ${user.placement}",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = if (isDarkTheme) TextMuted else Color.Gray
                                    )
                                }
                            }
                        }

                        // Point Badge
                        Box(
                            modifier = Modifier
                                .clip(HexagonalCutShape)
                                .background(if (isMe) EmeraldPrimary else if (isDarkTheme) Color(0xFF1E293B) else IndigoSecondary)
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = "${if (isMe) progress.scoreCount else user.score} PTS",
                                color = Color.White,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Black
                            )
                        }
                    }
                }
            }
        }
    }
}

data class LeaderboardUser(
    val name: String,
    val score: Int,
    val eueeScore: String,
    val placement: String
)
