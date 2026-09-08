package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.StudyViewModel
import com.example.ui.components.*
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(viewModel: StudyViewModel) {
    val progress by viewModel.userProgress.collectAsState()
    val isEditing by viewModel.isProfileEditing.collectAsState()
    val usernameInput by viewModel.profileUsernameEditInput.collectAsState()

    val studentName by viewModel.studentName.collectAsState()
    val studentEmail by viewModel.studentEmail.collectAsState()
    val studentGoal by viewModel.studentGoal.collectAsState()
    val studentClassLevel by viewModel.studentClassLevel.collectAsState()
    val currentLang by viewModel.currentLanguage.collectAsState()
    val isDarkTheme by viewModel.isDarkTheme.collectAsState()

    val headerTextColor = if (isDarkTheme) Color.White else IndigoSecondary
    val subTextColor = if (isDarkTheme) TextMuted else Color.DarkGray
    val cardBg = if (isDarkTheme) CardBgDark.copy(alpha = 0.65f) else Color.White.copy(alpha = 0.7f)
    val cardBorder = if (isDarkTheme) Color.White.copy(alpha = 0.25f) else Color.White.copy(alpha = 0.5f)

    val packageDisplayName = when(progress.activePackageId) {
        "euee" -> "EUEE Prep Ultimate"
        "euee_natural" -> "EUEE Prep - Natural Science"
        "euee_social" -> "EUEE Prep - Social Science"
        "freshman" -> "Freshman"
        "aau_uat" -> "AAU UAT PREP"
        "department" -> "University Department"
        "exit_exam" -> "Exit Exam Prep"
        "grade12" -> "Freshman"
        else -> "None Selected"
    }

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
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                        .clickable { viewModel.currentTab.value = "home" }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = if (isDarkTheme) HolographicAqua else IndigoSecondary,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = com.example.ui.TranslationManager.get("btn_back_to_dashboard", currentLang),
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = if (isDarkTheme) HolographicAqua else IndigoSecondary
                    )
                }

                Text(
                    text = com.example.ui.TranslationManager.get("profile_title", currentLang),
                    style = MaterialTheme.typography.displayLarge.copy(
                        fontWeight = FontWeight.Black,
                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                        lineHeight = 28.sp,
                        fontSize = 22.sp
                    ),
                    color = headerTextColor
                )
                Text(
                    text = com.example.ui.TranslationManager.get("profile_desc", currentLang),
                    style = MaterialTheme.typography.bodyMedium,
                    color = subTextColor,
                    modifier = Modifier.padding(top = 4.dp, bottom = 24.dp)
                )
            }

            // High-end Profile Card
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, cardBorder, SubjectCardRoundedShape),
                    shape = SubjectCardRoundedShape,
                    colors = CardDefaults.cardColors(containerColor = cardBg)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Avatar
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape)
                                .background(IndigoMedium),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = progress.username.take(2).uppercase(),
                                color = Color.White,
                                style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Black)
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Username view/edit inline state managers
                        if (isEditing) {
                            OutlinedTextField(
                                value = usernameInput,
                                onValueChange = { viewModel.profileUsernameEditInput.value = it },
                                label = { Text("Display Name") },
                                singleLine = true,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("username_edit_field"),
                                shape = RoundedCornerShape(12.dp),
                                trailingIcon = {
                                    IconButton(onClick = { viewModel.submitUsername() }) {
                                        Icon(Icons.Default.Check, contentDescription = "Save", tint = EmeraldPrimary)
                                    }
                                }
                            )
                        } else {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier.clickable { viewModel.isProfileEditing.value = true }
                            ) {
                                Text(
                                    text = progress.username,
                                    style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Bold),
                                    color = headerTextColor
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Icon(
                                    Icons.Default.Edit,
                                    contentDescription = "Edit name",
                                    tint = IndigoMedium,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Active Package label
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Inventory2, contentDescription = null, tint = EmeraldPrimary, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "${com.example.ui.TranslationManager.get("lbl_class_pack_prefix", currentLang)}$packageDisplayName",
                                style = MaterialTheme.typography.bodyMedium,
                                color = subTextColor,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        // Gamified Stats Grid (Duolingo / Phantom Wallet style)
                        val answeredQuestionsCount = viewModel.answeredQuestionsSet.collectAsState().value.size
                        val readNotesCount = viewModel.readNotesSet.collectAsState().value.size
                        val totalXp = (answeredQuestionsCount * 15) + (readNotesCount * 25) + (progress.scoreCount * 10)
                        val streakDays = (answeredQuestionsCount / 3).coerceAtLeast(1)

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            // Streak Card
                            Card(
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = if (isDarkTheme) Color(0xFF1E293B) else Color(0xFFFFFBEB)
                                ),
                                border = BorderStroke(1.dp, Color(0xFFF97316).copy(alpha = 0.35f))
                            ) {
                                Column(
                                    modifier = Modifier.padding(12.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.LocalFireDepartment,
                                        contentDescription = "Streak",
                                        tint = Color(0xFFF97316),
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "$streakDays Days",
                                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Black),
                                        color = if (isDarkTheme) Color.White else Color(0xFF9A3412)
                                    )
                                    Text(
                                        text = "Study Streak",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = TextMuted
                                    )
                                }
                            }

                            // XP Card
                            Card(
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = if (isDarkTheme) Color(0xFF1E293B) else Color(0xFFEFF6FF)
                                ),
                                border = BorderStroke(1.dp, Color(0xFF3B82F6).copy(alpha = 0.35f))
                            ) {
                                Column(
                                    modifier = Modifier.padding(12.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Bolt,
                                        contentDescription = "XP",
                                        tint = Color(0xFF3B82F6),
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "$totalXp XP",
                                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Black),
                                        color = if (isDarkTheme) Color.White else Color(0xFF1E40AF)
                                    )
                                    Text(
                                        text = "Total Points",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = TextMuted
                                    )
                                }
                            }

                            // Solved Card
                            Card(
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = if (isDarkTheme) Color(0xFF1E293B) else Color(0xFFECFDF5)
                                ),
                                border = BorderStroke(1.dp, EmeraldPrimary.copy(alpha = 0.35f))
                            ) {
                                Column(
                                    modifier = Modifier.padding(12.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = "Questions Solved",
                                        tint = EmeraldPrimary,
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "$answeredQuestionsCount",
                                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Black),
                                        color = if (isDarkTheme) Color.White else EmeraldDark
                                    )
                                    Text(
                                        text = "Questions",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = TextMuted
                                    )
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }

            // App Settings & Reading Preferences Segment
            item {
                Text(
                    text = com.example.ui.TranslationManager.get("settings_reading_mode_title", currentLang),
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Black,
                        fontSize = 15.sp,
                        letterSpacing = 0.5.sp
                    ),
                    color = headerTextColor,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, cardBorder, SubjectCardRoundedShape),
                    shape = SubjectCardRoundedShape,
                    colors = CardDefaults.cardColors(containerColor = cardBg)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Language Selector
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Language,
                                    contentDescription = "Language",
                                    tint = EmeraldPrimary,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = com.example.ui.TranslationManager.get("app_language_title", currentLang),
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = headerTextColor
                                )
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                val langs = listOf(
                                    "en" to "English",
                                    "am" to "አማርኛ",
                                    "om" to "Oromoo",
                                    "so" to "Somali",
                                    "ti" to "ትግርኛ"
                                )
                                langs.forEach { (code, label) ->
                                    val isSelected = currentLang == code
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(if (isSelected) EmeraldPrimary else (if (isDarkTheme) Color(0xFF1E293B) else BgSoftGray))
                                            .pressBounce(pressedScale = 0.92f)
                                            .clickable { viewModel.setLanguage(code) }
                                            .padding(horizontal = 8.dp, vertical = 6.dp)
                                    ) {
                                        Text(
                                            text = label,
                                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                                            color = if (isSelected) Color.White else (if (isDarkTheme) TextMuted else Color.DarkGray),
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }

            // Student Profile & Academic Goals Display Segment
            item {
                Text(
                    text = com.example.ui.TranslationManager.get("profile_controls", currentLang),
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Black,
                        fontSize = 15.sp,
                        letterSpacing = 0.5.sp
                    ),
                    color = headerTextColor,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, if (isDarkTheme) Color(0xFF334155) else Color(0xFFE2E8F0), RoundedCornerShape(20.dp)),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = if (isDarkTheme) Color(0xFF131B2E) else Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .background(EmeraldPrimary.copy(alpha = 0.15f), RoundedCornerShape(8.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.Badge, contentDescription = null, tint = EmeraldPrimary, modifier = Modifier.size(18.dp))
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(com.example.ui.TranslationManager.get("lbl_student_name", currentLang), style = MaterialTheme.typography.labelSmall, color = TextMuted)
                                Text(text = studentName.ifEmpty { "Default Candidate" }, style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold), color = if (isDarkTheme) Color.White else IndigoSecondary)
                            }
                        }

                        HorizontalDivider(color = if (isDarkTheme) Color(0xFF334155).copy(alpha = 0.6f) else Color(0xFFE2E8F0))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .background(EmeraldPrimary.copy(alpha = 0.15f), RoundedCornerShape(8.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.Email, contentDescription = null, tint = EmeraldPrimary, modifier = Modifier.size(18.dp))
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(com.example.ui.TranslationManager.get("lbl_reg_email", currentLang), style = MaterialTheme.typography.labelSmall, color = TextMuted)
                                Text(text = studentEmail.ifEmpty { "student@ethio-hub.edu" }, style = MaterialTheme.typography.bodyMedium, color = if (isDarkTheme) Color.White else IndigoSecondary)
                            }
                        }

                        HorizontalDivider(color = if (isDarkTheme) Color(0xFF334155).copy(alpha = 0.6f) else Color(0xFFE2E8F0))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .background(EmeraldPrimary.copy(alpha = 0.15f), RoundedCornerShape(8.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.School, contentDescription = null, tint = EmeraldPrimary, modifier = Modifier.size(18.dp))
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(com.example.ui.TranslationManager.get("lbl_current_class", currentLang), style = MaterialTheme.typography.labelSmall, color = TextMuted)
                                Text(text = studentClassLevel.ifEmpty { "Grade 12 (EUEE Prep)" }, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold), color = if (isDarkTheme) Color.White else IndigoSecondary)
                            }
                        }

                        HorizontalDivider(color = if (isDarkTheme) Color(0xFF334155).copy(alpha = 0.6f) else Color(0xFFE2E8F0))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .background(GoldAccent.copy(alpha = 0.15f), RoundedCornerShape(8.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.EmojiEvents, contentDescription = null, tint = GoldAccent, modifier = Modifier.size(18.dp))
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(com.example.ui.TranslationManager.get("lbl_academic_goal", currentLang), style = MaterialTheme.typography.labelSmall, color = TextMuted)
                                Text(text = studentGoal.ifEmpty { "Score 500+ Matric Result / First Class Dept Honors" }, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold), color = GoldAccent)
                            }
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        OutlinedButton(
                            onClick = { viewModel.restartOnboarding() },
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = EmeraldPrimary),
                            border = androidx.compose.foundation.BorderStroke(1.dp, EmeraldPrimary.copy(alpha = 0.5f)),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(44.dp)
                                .pressBounce(pressedScale = 0.96f)
                                .testTag("edit_onboarding_preferences_button")
                        ) {
                            Icon(Icons.Default.Tune, contentDescription = null, modifier = Modifier.size(16.dp), tint = EmeraldPrimary)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(com.example.ui.TranslationManager.get("btn_reconfigure_onboarding", currentLang), fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }

            // Account & Subscriptions Controls
            item {
                Text(
                    text = com.example.ui.TranslationManager.get("subscription_controls", currentLang),
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Black,
                        fontSize = 15.sp,
                        letterSpacing = (-0.2).sp
                    ),
                    color = headerTextColor,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                // Modify / Switch Package Button (satisfying state requirements)
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, Color.Red.copy(alpha = 0.2f), ChamferedCardShape)
                        .pressBounce(pressedScale = 0.96f)
                        .clickable { viewModel.resetEnrollment() }
                        .testTag("profile_switch_package_button"),
                    shape = ChamferedCardShape,
                    colors = CardDefaults.cardColors(containerColor = cardBg)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.ChangeCircle, contentDescription = null, tint = Color.Red)
                            Spacer(modifier = Modifier.width(16.dp))
                            Column {
                                Text(
                                    text = com.example.ui.TranslationManager.get("btn_reset_enrollment", currentLang),
                                    style = MaterialTheme.typography.titleMedium,
                                    color = headerTextColor,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = com.example.ui.TranslationManager.get("desc_reset_enrollment", currentLang),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = subTextColor
                                )
                            }
                        }
                        Icon(Icons.Default.ArrowForwardIos, contentDescription = "Reset", tint = Color.Gray, modifier = Modifier.size(16.dp))
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Legal Documents Footer (Privacy Policy, Terms of Service, EULA)
                com.example.ui.components.LegalLinksFooter(
                    currentLang = currentLang,
                    textColor = subTextColor,
                    screenTagPrefix = "profile"
                )

                Spacer(modifier = Modifier.height(28.dp))
            }
        }
    }
}
