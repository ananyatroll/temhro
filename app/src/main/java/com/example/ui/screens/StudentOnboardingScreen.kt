package com.example.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.StudyViewModel
import com.example.ui.TranslationManager
import com.example.ui.theme.*

@Composable
fun StudentOnboardingScreen(viewModel: StudyViewModel) {
    val focusManager = LocalFocusManager.current
    val currentLang by viewModel.currentLanguage.collectAsState()
    val isDarkTheme by viewModel.isDarkTheme.collectAsState()
    val savedName by viewModel.studentName.collectAsState()
    val savedEmail by viewModel.studentEmail.collectAsState()
    val savedGoal by viewModel.studentGoal.collectAsState()
    val savedLevel by viewModel.studentClassLevel.collectAsState()

    var name by remember { mutableStateOf(savedName) }
    var email by remember { mutableStateOf(savedEmail) }
    var selectedLang by remember { mutableStateOf(currentLang) }
    var selectedThemeDark by remember { mutableStateOf(isDarkTheme) }
    var selectedClassLevel by remember {
        mutableStateOf(
            if (savedLevel.isNotEmpty() && savedLevel != "Grade 11 Candidate") {
                if (savedLevel == "University Department") "Department student" else savedLevel
            } else "Grade 12 (EUEE Prep)"
        )
    }
    var goal by remember { mutableStateOf(if (savedGoal.isNotEmpty()) savedGoal else "Complete classes with good grades") }
    var agreedToConditions by remember { mutableStateOf(false) }
    var showConditionsDialog by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    fun t(key: String): String = TranslationManager.get(key, selectedLang)

    val bgGradient = Brush.verticalGradient(colors = listOf(Color.Transparent, Color.Transparent))

    val cardBg = if (selectedThemeDark) CardBgDark.copy(alpha = 0.65f) else Color.White.copy(alpha = 0.7f)
    val cardBorder = if (selectedThemeDark) Color.White.copy(alpha = 0.25f) else Color.White.copy(alpha = 0.5f)
    val textPrimary = if (selectedThemeDark) Color.White else IndigoSecondary
    val textSecondary = if (selectedThemeDark) TextMuted else Color.Gray

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bgGradient)
            .windowInsetsPadding(WindowInsets.safeDrawing),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 16.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(12.dp))

            // Header Hero Banner
            Image(
                painter = painterResource(id = R.drawable.img_app_icon),
                contentDescription = "ተምህሮ / Temhiro Logo",
                modifier = Modifier
                    .size(76.dp)
                    .clip(RoundedCornerShape(18.dp))
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = t("onboarding_title"),
                style = MaterialTheme.typography.displayLarge.copy(
                    fontWeight = FontWeight.Black,
                    fontSize = 24.sp,
                    lineHeight = 30.sp
                ),
                color = textPrimary,
                textAlign = TextAlign.Center
            )

            Text(
                text = t("onboarding_desc"),
                style = MaterialTheme.typography.bodyMedium,
                color = textSecondary,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 6.dp, bottom = 24.dp)
            )

            // ------------------ QUESTION 1: NAME & EMAIL ------------------
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp)
                    .border(1.dp, cardBorder, RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = cardBg)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(IndigoMedium),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(t("step1_num"), color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = t("pref_name_question"),
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = textPrimary
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text(t("your_fullname"), color = textSecondary) },
                        placeholder = { Text(t("placeholder_fullname"), color = textSecondary.copy(alpha = 0.5f)) },
                        singleLine = true,
                        leadingIcon = { Icon(Icons.Default.Badge, contentDescription = null, tint = EmeraldPrimary) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = textPrimary,
                            unfocusedTextColor = textPrimary,
                            focusedBorderColor = EmeraldPrimary,
                            unfocusedBorderColor = cardBorder
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("onboarding_name_field")
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text(t("email_address"), color = textSecondary) },
                        placeholder = { Text(t("placeholder_email"), color = textSecondary.copy(alpha = 0.5f)) },
                        singleLine = true,
                        leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = EmeraldPrimary) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = textPrimary,
                            unfocusedTextColor = textPrimary,
                            focusedBorderColor = EmeraldPrimary,
                            unfocusedBorderColor = cardBorder
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            // ------------------ QUESTION 2: PREFERRED LANGUAGE ------------------
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp)
                    .border(1.dp, cardBorder, RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = cardBg)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(IndigoMedium),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(t("step2_num"), color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = t("pref_lang_title"),
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = textPrimary
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    val languages = listOf(
                        Triple("en", "🇬🇧 English", "Standard English curriculum"),
                        Triple("am", "🇪🇹 አማርኛ (Amharic)", "ለኢትዮጵያ ተማሪዎች የቀረበ"),
                        Triple("om", "🇪🇹 Afaan Oromoo", "Barannoowwan Oromiyaa"),
                        Triple("so", "🇸🇴 Soomaali (Somali)", "Taageerada ardayda Soomaalida"),
                        Triple("ti", "🇪🇹 ትግርኛ (Tigrigna)", "ትምህርታዊ ሓበሬታታት")
                    )

                    languages.forEach { (code, title, desc) ->
                        val isSelected = selectedLang == code
                        val bgAnim by animateColorAsState(if (isSelected) EmeraldPrimary.copy(alpha = 0.15f) else Color.Transparent)
                        val borderAnim by animateColorAsState(if (isSelected) EmeraldPrimary else cardBorder)

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(bgAnim)
                                .border(1.5.dp, borderAnim, RoundedCornerShape(12.dp))
                                .clickable {
                                    selectedLang = code
                                    viewModel.setLanguage(code)
                                }
                                .padding(horizontal = 14.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = title,
                                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                    color = textPrimary
                                )
                                Text(
                                    text = desc,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = textSecondary
                                )
                            }
                            RadioButton(
                                selected = isSelected,
                                onClick = {
                                    selectedLang = code
                                    viewModel.setLanguage(code)
                                },
                                colors = RadioButtonDefaults.colors(selectedColor = EmeraldPrimary)
                            )
                        }
                    }
                }
            }

            // ------------------ TELEGRAM CHANNEL SUBSCRIPTION STEP ------------------
            val context = androidx.compose.ui.platform.LocalContext.current
            var hasClickedJoinTelegram by remember { mutableStateOf(false) }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp)
                    .border(1.5.dp, if (hasClickedJoinTelegram) EmeraldPrimary else Color(0xFF0088CC), RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = cardBg)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF0088CC)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(imageVector = Icons.Default.Send, contentDescription = "Telegram", tint = Color.White, modifier = Modifier.size(18.dp))
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "JOIN OFFICIAL TELEGRAM",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = textPrimary
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "To stay updated with exam announcements, answer keys, and instant vouchers, subscribe to @temhiroapp_official on Telegram to continue.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = textSecondary,
                        lineHeight = 18.sp
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Button(
                        onClick = {
                            hasClickedJoinTelegram = true
                            try {
                                val tgIntent = android.content.Intent(
                                    android.content.Intent.ACTION_VIEW,
                                    android.net.Uri.parse("https://t.me/temhiroapp_official")
                                )
                                context.startActivity(tgIntent)
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        },
                        modifier = Modifier.fillMaxWidth().height(48.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0088CC), contentColor = Color.White)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.Send, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("📢 Join @temhiroapp_official", fontWeight = FontWeight.Bold)
                        }
                    }

                    if (hasClickedJoinTelegram) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = EmeraldPrimary, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Joined! You can now complete your setup below.", style = MaterialTheme.typography.labelSmall, color = EmeraldPrimary, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            // ------------------ QUESTION 3: PREFERRED THEME ------------------
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp)
                    .border(1.dp, cardBorder, RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = cardBg)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(IndigoMedium),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(t("step3_num"), color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = t("pref_theme_title"),
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = textPrimary
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // Light Mode Box
                        val isLight = !selectedThemeDark
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(14.dp))
                                .background(if (isLight) GoldLight else (if (selectedThemeDark) Color(0xFF1E293B) else BgSoftGray))
                                .border(2.dp, if (isLight) GoldDark else cardBorder, RoundedCornerShape(14.dp))
                                .clickable {
                                    selectedThemeDark = false
                                    viewModel.setDarkTheme(false)
                                }
                                .padding(14.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(Icons.Default.WbSunny, contentDescription = "Light Theme", tint = GoldDark, modifier = Modifier.size(28.dp))
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(t("theme_light_title"), style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold), color = Color(0xFF0F172A))
                                Text(t("theme_light_desc"), style = MaterialTheme.typography.labelSmall, color = Color.DarkGray, fontSize = 10.sp)
                            }
                        }

                        // Dark Mode Box
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(14.dp))
                                .background(if (selectedThemeDark) IndigoSecondary else (if (selectedThemeDark) Color(0xFF1E293B) else BgSoftGray))
                                .border(2.dp, if (selectedThemeDark) HolographicAqua else cardBorder, RoundedCornerShape(14.dp))
                                .clickable {
                                    selectedThemeDark = true
                                    viewModel.setDarkTheme(true)
                                }
                                .padding(14.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(Icons.Default.DarkMode, contentDescription = "Dark Theme", tint = HolographicAqua, modifier = Modifier.size(28.dp))
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(t("theme_dark_title"), style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold), color = Color.White)
                                Text(t("theme_dark_desc"), style = MaterialTheme.typography.labelSmall, color = TextMuted, fontSize = 10.sp)
                            }
                        }
                    }
                }
            }

            // ------------------ QUESTION 4: CURRENT CLASS LEVEL ------------------
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp)
                    .border(1.dp, cardBorder, RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = cardBg)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(IndigoMedium),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(t("step4_num"), color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = t("pref_class_title"),
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = textPrimary
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    val levels = listOf(
                        "Grade 12 (EUEE Prep)" to "class_grade12",
                        "University Freshman" to "class_freshman",
                        "Department student" to "class_department",
                        "National Exit Exam Prep" to "class_exit_exam"
                    )

                    levels.forEach { (lvlValue, lvlKey) ->
                        val isSelected = selectedClassLevel == lvlValue
                        val bgAnim by animateColorAsState(if (isSelected) EmeraldPrimary.copy(alpha = 0.15f) else Color.Transparent)
                        val borderAnim by animateColorAsState(if (isSelected) EmeraldPrimary else cardBorder)

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(bgAnim)
                                .border(1.5.dp, borderAnim, RoundedCornerShape(12.dp))
                                .clickable { selectedClassLevel = lvlValue }
                                .padding(horizontal = 14.dp, vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = if (lvlValue.contains("Exit")) Icons.Default.WorkspacePremium
                                    else if (lvlValue.contains("Freshman") || lvlValue.contains("Dept")) Icons.Default.AccountBalance
                                    else Icons.Default.School,
                                    contentDescription = null,
                                    tint = if (isSelected) EmeraldPrimary else textSecondary,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = t(lvlKey),
                                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal),
                                    color = textPrimary
                                )
                            }
                            RadioButton(
                                selected = isSelected,
                                onClick = { selectedClassLevel = lvlValue },
                                colors = RadioButtonDefaults.colors(selectedColor = EmeraldPrimary)
                            )
                        }
                    }
                }
            }

            // ------------------ QUESTION 5: ACADEMIC GOALS ------------------
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
                    .border(1.dp, cardBorder, RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = cardBg)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(IndigoMedium),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(t("step5_num"), color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = t("pref_goal_title"),
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = textPrimary
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    OutlinedTextField(
                        value = goal,
                        onValueChange = { goal = it },
                        label = { Text(t("matric_target"), color = textSecondary) },
                        placeholder = { Text(t("placeholder_goal"), color = textSecondary.copy(alpha = 0.5f)) },
                        singleLine = true,
                        leadingIcon = { Icon(Icons.Default.EmojiEvents, contentDescription = null, tint = GoldAccent) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = textPrimary,
                            unfocusedTextColor = textPrimary,
                            focusedBorderColor = EmeraldPrimary,
                            unfocusedBorderColor = cardBorder
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("onboarding_goal_field")
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(t("goal_suggestions_title"), style = MaterialTheme.typography.labelSmall, color = textSecondary)
                    Spacer(modifier = Modifier.height(8.dp))

                    val quickGoals = listOf(
                        "Complete classes with good grades" to "goal_classes_grades",
                        "Score 500+ Matric Result" to "goal_matric_500",
                        "Pass National Exit Exam" to "goal_exit_exam",
                        "CGPA 4.0 First Class Honors" to "goal_cgpa_4",
                        "Top Department Placement" to "goal_top_placement"
                    )

                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        quickGoals.forEach { (gValue, gKey) ->
                            val gDisplay = t(gKey)
                            val isChosen = goal == gValue || goal == gDisplay
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (isChosen) EmeraldPrimary.copy(alpha = 0.15f) else (if (selectedThemeDark) Color(0xFF1E293B) else BgSoftGray))
                                    .border(1.dp, if (isChosen) EmeraldPrimary else cardBorder, RoundedCornerShape(8.dp))
                                    .clickable { goal = gDisplay }
                                    .padding(horizontal = 12.dp, vertical = 8.dp)
                            ) {
                                Text(
                                    text = gDisplay,
                                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = if (isChosen) FontWeight.Bold else FontWeight.Normal),
                                    color = if (isChosen) EmeraldPrimary else textPrimary
                                )
                            }
                        }
                    }
                }
            }

            // ------------------ STEP 6: LEGAL COMPLIANCE & POLICIES ------------------
            val uriHandler = androidx.compose.ui.platform.LocalUriHandler.current
            fun openLegalUrl(url: String) {
                try {
                    uriHandler.openUri(url)
                } catch (_: Exception) {}
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
                    .border(1.dp, if (agreedToConditions) EmeraldPrimary.copy(alpha = 0.5f) else cardBorder, RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = cardBg)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(if (agreedToConditions) EmeraldPrimary else IndigoMedium),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = if (agreedToConditions) Icons.Default.Check else Icons.Default.Gavel,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Legal Agreements & Policies",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = textPrimary
                            )
                            Text(
                                text = "Please review our legal agreements before continuing",
                                style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                                color = textSecondary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Buttons to view official legal websites
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // EULA Button
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = if (selectedThemeDark) Color(0xFF1E293B) else Color(0xFFF1F5F9),
                            border = androidx.compose.foundation.BorderStroke(1.dp, if (selectedThemeDark) Color(0xFF334155) else Color(0xFFE2E8F0)),
                            modifier = Modifier
                                .weight(1f)
                                .clickable { openLegalUrl(com.example.ui.components.URL_EULA) }
                        ) {
                            Row(
                                modifier = Modifier.padding(vertical = 10.dp, horizontal = 6.dp),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Default.Article, contentDescription = null, tint = EmeraldPrimary, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "EULA",
                                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold, fontSize = 11.sp),
                                    color = textPrimary,
                                    maxLines = 1
                                )
                            }
                        }

                        // Terms & Conditions Button
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = if (selectedThemeDark) Color(0xFF1E293B) else Color(0xFFF1F5F9),
                            border = androidx.compose.foundation.BorderStroke(1.dp, if (selectedThemeDark) Color(0xFF334155) else Color(0xFFE2E8F0)),
                            modifier = Modifier
                                .weight(1.3f)
                                .clickable { openLegalUrl(com.example.ui.components.URL_TERMS_OF_SERVICE) }
                        ) {
                            Row(
                                modifier = Modifier.padding(vertical = 10.dp, horizontal = 6.dp),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Default.Gavel, contentDescription = null, tint = EmeraldPrimary, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "Terms & Conditions",
                                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold, fontSize = 11.sp),
                                    color = textPrimary,
                                    maxLines = 1
                                )
                            }
                        }

                        // Privacy Policy Button
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = if (selectedThemeDark) Color(0xFF1E293B) else Color(0xFFF1F5F9),
                            border = androidx.compose.foundation.BorderStroke(1.dp, if (selectedThemeDark) Color(0xFF334155) else Color(0xFFE2E8F0)),
                            modifier = Modifier
                                .weight(1.2f)
                                .clickable { openLegalUrl(com.example.ui.components.URL_PRIVACY_POLICY) }
                        ) {
                            Row(
                                modifier = Modifier.padding(vertical = 10.dp, horizontal = 6.dp),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Default.PrivacyTip, contentDescription = null, tint = EmeraldPrimary, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "Privacy Policy",
                                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold, fontSize = 11.sp),
                                    color = textPrimary,
                                    maxLines = 1
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = if (agreedToConditions) EmeraldPrimary.copy(alpha = 0.12f) else (if (selectedThemeDark) Color(0xFF1E293B) else Color(0xFFF8FAFC)),
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp,
                            if (agreedToConditions) EmeraldPrimary else cardBorder
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { agreedToConditions = !agreedToConditions }
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = agreedToConditions,
                                onCheckedChange = { agreedToConditions = it },
                                colors = CheckboxDefaults.colors(checkedColor = EmeraldPrimary)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "I have read and agree to the EULA Agreement, Terms and Conditions, and Privacy Policy.",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontWeight = if (agreedToConditions) FontWeight.Bold else FontWeight.Medium,
                                    fontSize = 12.sp,
                                    lineHeight = 17.sp
                                ),
                                color = textPrimary,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }

            val isValid = name.trim().isNotEmpty() &&
                    goal.trim().isNotEmpty() &&
                    selectedClassLevel.trim().isNotEmpty() &&
                    selectedLang.trim().isNotEmpty() &&
                    hasClickedJoinTelegram &&
                    agreedToConditions

            if (!isValid) {
                val errorMsg = when {
                    !hasClickedJoinTelegram -> "Please click 'Join @temhiroapp_official' above to continue."
                    !agreedToConditions -> "Please agree to the EULA Agreement, Terms and Conditions, and Privacy Policy above to continue to packages."
                    else -> t("onboarding_validation_error")
                }
                Text(
                    text = errorMsg,
                    style = MaterialTheme.typography.labelSmall,
                    color = if (selectedThemeDark) Color(0xFFFCA5A5) else Color(0xFFDC2626),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            // Save & Continue Button
            Button(
                onClick = {
                    if (isValid) {
                        viewModel.saveStudentProfile(
                            name = name,
                            email = email,
                            goal = goal,
                            language = selectedLang,
                            isDark = selectedThemeDark,
                            classLevel = selectedClassLevel
                        )
                    }
                },
                enabled = isValid,
                colors = ButtonDefaults.buttonColors(
                    containerColor = EmeraldPrimary,
                    disabledContainerColor = Color.Gray.copy(alpha = 0.3f),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .testTag("onboarding_submit_button")
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = t("continue_to_tools"),
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(Icons.Default.ArrowForward, contentDescription = null)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Legal Documents Footer (Privacy Policy, Terms of Service, EULA)
            com.example.ui.components.LegalLinksFooter(
                currentLang = selectedLang,
                textColor = if (selectedThemeDark) TextMuted else Color.Gray,
                screenTagPrefix = "onboarding"
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
