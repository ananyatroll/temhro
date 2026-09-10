package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.window.Dialog
import com.example.ads.InterstitialAdManager
import com.example.ads.AdsManager
import com.example.ui.StudyViewModel
import com.example.ui.TranslationManager
import com.example.ui.components.*
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChooseToolScreen(viewModel: StudyViewModel) {
    val progress by viewModel.userProgress.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val accordionExpanded by viewModel.packageAccordionExpanded.collectAsState()
    val confirmationPackage by viewModel.enrollmentConfirmationPackage.collectAsState()
    val showPaymentForm by viewModel.showPaymentVerificationScreen.collectAsState()

    val currentLang by viewModel.currentLanguage.collectAsState()
    val studentName by viewModel.studentName.collectAsState()
    val studentGoal by viewModel.studentGoal.collectAsState()
    val isDarkTheme by viewModel.isDarkTheme.collectAsState()

    fun t(key: String): String = TranslationManager.get(key, currentLang)

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
            contentPadding = PaddingValues(top = 16.dp, bottom = 80.dp)
        ) {
            // Header Row
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    var langMenuExpanded by remember { mutableStateOf(false) }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Dark / Light Mode symbol toggle (pure symbols, no text)
                        Box(
                            modifier = Modifier
                                .clip(HexagonalCutShape)
                                .background(if (isDarkTheme) CardBgDark else Color.White)
                                .pressBounce(pressedScale = 0.92f)
                                .clickable { viewModel.toggleDarkTheme() }
                                .padding(horizontal = 10.dp, vertical = 7.dp)
                                .border(1.dp, if (isDarkTheme) EmeraldPrimary.copy(alpha = 0.35f) else IndigoLight, HexagonalCutShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = if (isDarkTheme) Icons.Default.DarkMode else Icons.Default.WbSunny,
                                contentDescription = "Toggle Theme",
                                tint = if (isDarkTheme) HolographicAqua else GoldDark,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        Box(
                            modifier = Modifier
                                .clip(HexagonalCutShape)
                                .background(if (isDarkTheme) CardBgDark else Color.White)
                                .pressBounce(pressedScale = 0.94f)
                                .clickable { langMenuExpanded = true }
                                .padding(horizontal = 10.dp, vertical = 7.dp)
                                .border(1.dp, if (isDarkTheme) EmeraldPrimary.copy(alpha = 0.35f) else IndigoLight, HexagonalCutShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(3.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Language,
                                    contentDescription = "Language",
                                    tint = if (isDarkTheme) HolographicAqua else EmeraldPrimary,
                                    modifier = Modifier.size(20.dp)
                                )
                                Icon(
                                    imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription = "Language Dropdown",
                                    tint = if (isDarkTheme) Color.White else IndigoSecondary,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }

                        DropdownMenu(
                            expanded = langMenuExpanded,
                            onDismissRequest = { langMenuExpanded = false },
                            modifier = Modifier.glassEffect(
                                shape = RoundedCornerShape(16.dp),
                                backgroundColor = if (isDarkTheme) CardBgDark.copy(alpha = 0.85f) else Color.White.copy(alpha = 0.88f),
                                borderColor = Color.White.copy(alpha = 0.35f)
                            )
                        ) {
                            val itemColor = if (isDarkTheme) Color.White else Color.DarkGray
                            DropdownMenuItem(
                                text = { Text("🇬🇧 English", color = itemColor, fontSize = 13.sp) },
                                onClick = {
                                    viewModel.setLanguage("en")
                                    langMenuExpanded = false
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("🇪🇹 አማርኛ (Amharic)", color = itemColor, fontSize = 13.sp) },
                                onClick = {
                                    viewModel.setLanguage("am")
                                    langMenuExpanded = false
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("🇪🇹 Afaan Oromoo (Oromifa)", color = itemColor, fontSize = 13.sp) },
                                onClick = {
                                    viewModel.setLanguage("om")
                                    langMenuExpanded = false
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("🇪🇹 Soomaali (Somali)", color = itemColor, fontSize = 13.sp) },
                                onClick = {
                                    viewModel.setLanguage("so")
                                    langMenuExpanded = false
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("🇪🇹 ትግርኛ (Tigrigna)", color = itemColor, fontSize = 13.sp) },
                                onClick = {
                                    viewModel.setLanguage("ti")
                                    langMenuExpanded = false
                                }
                            )
                        }
                    }
                }
            }

            // Title
            item {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = t("choose_tool"),
                    style = MaterialTheme.typography.displayLarge.copy(
                        fontWeight = FontWeight.Black,
                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                        lineHeight = 28.sp,
                        fontSize = 22.sp,
                        letterSpacing = (-0.4).sp
                    ),
                    color = if (isDarkTheme) Color.White else IndigoSecondary,
                )
                Text(
                    text = t("choose_tool_desc"),
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (isDarkTheme) TextMuted else Color.Gray,
                    modifier = Modifier.padding(top = 4.dp, bottom = 24.dp)
                )
            }

            // Popular Tools Section
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, bottom = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = t("popular_tools").uppercase(),
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Black,
                            fontSize = 14.sp,
                            letterSpacing = (-0.2).sp
                        ),
                        color = if (isDarkTheme) Color.White else IndigoSecondary
                    )
                    Text(
                        text = t("study_programs_count"),
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.ExtraBold, letterSpacing = 0.5.sp),
                        color = EmeraldPrimary,
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(EmeraldPrimary.copy(alpha = 0.1f))
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
                Spacer(modifier = Modifier.height(14.dp))
            }

            // Cards Data Filtering based on search query
            val cardsList = listOf(
                PackageCardData(
                    id = "euee",
                    title = t("pkg_euee_title"),
                    badge = t("status_free_trial"),
                    description = t("pkg_euee_desc"),
                    comingSoon = false
                ),
                PackageCardData(
                    id = "aau_uat",
                    title = t("pkg_uat_title"),
                    badge = t("status_free_trial"),
                    description = t("pkg_uat_desc"),
                    comingSoon = false
                ),
                PackageCardData(
                    id = "freshman",
                    title = "Freshman Package",
                    badge = t("status_free_trial"),
                    description = "Complete Ethiopian University Freshman Year courses & exam prep for Natural and Social streams.",
                    comingSoon = false
                ),
                PackageCardData(
                    id = "coc",
                    title = "COC Exam Preparation",
                    badge = t("status_free_trial"),
                    description = "National Certificate of Competency (COC) review for Medical (Medicine, Dentistry, Pharmacy), Engineering, Architecture, Computer Science & Information Science, and Law.",
                    comingSoon = false
                ),
                PackageCardData(
                    id = "department",
                    title = t("pkg_dept_title"),
                    badge = t("status_free_trial"),
                    description = t("pkg_dept_desc"),
                    comingSoon = false
                ),
                PackageCardData(
                    id = "exit_exam",
                    title = t("pkg_exit_title"),
                    badge = t("status_free_trial"),
                    description = t("pkg_exit_desc"),
                    comingSoon = false
                )
            ).filter { it.title.lowercase().contains(searchQuery.lowercase()) || it.description.lowercase().contains(searchQuery.lowercase()) }

            if (cardsList.isEmpty() && searchQuery.isNotEmpty()) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(Icons.Default.SearchOff, contentDescription = null, modifier = Modifier.size(48.dp), tint = Color.Gray)
                        Text(t("no_packages_match"), style = MaterialTheme.typography.bodyLarge, color = Color.Gray, modifier = Modifier.padding(top = 12.dp))
                    }
                }
            } else {
                items(cardsList) { pkg ->
                    val isEnrolled = when (pkg.id) {
                        "euee" -> progress.activePackageId == "euee_natural" || progress.activePackageId == "euee_social"
                        "freshman" -> progress.activePackageId == "freshman_natural" || progress.activePackageId == "freshman_social"
                        "coc" -> progress.activePackageId?.startsWith("coc_") == true
                        else -> progress.activePackageId == pkg.id
                    }

                    val effectivePurchased = if (progress.purchasedPackageId.isNotEmpty()) {
                        progress.purchasedPackageId
                    } else if (progress.paymentStatus == "approved") {
                        progress.activePackageId ?: "euee_natural"
                    } else {
                        ""
                    }

                    val isPkgPurchased = viewModel.isPackagePurchased(effectivePurchased, pkg.id)

                    val context = androidx.compose.ui.platform.LocalContext.current
                    PackageCard(
                        data = pkg,
                        isEnrolled = isEnrolled,
                        isApproved = isPkgPurchased,
                        isResetMode = progress.activePackageId == null,
                        isDarkTheme = isDarkTheme,
                        currentLang = currentLang,
                        onEnrollClick = {
                            if (isEnrolled) {
                                val activity = AdsManager.findActivity(context)
                                if (activity != null) {
                                    InterstitialAdManager.showIfAllowed(activity) {
                                        viewModel.currentTab.value = "home"
                                    }
                                } else {
                                    viewModel.currentTab.value = "home"
                                }
                            } else if (isPkgPurchased) {
                                val targetPkg = if (pkg.id == "euee") {
                                    if (progress.activePackageId == "euee_social") "euee_social" else "euee_natural"
                                } else {
                                    pkg.id
                                }
                                val activity = AdsManager.findActivity(context)
                                if (activity != null) {
                                    InterstitialAdManager.showIfAllowed(activity) {
                                        viewModel.enrollInPackage(targetPkg)
                                        viewModel.currentTab.value = "home"
                                    }
                                } else {
                                    viewModel.enrollInPackage(targetPkg)
                                    viewModel.currentTab.value = "home"
                                }
                            } else {
                                viewModel.enrollmentConfirmationPackage.value = pkg.id
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            item {
                com.example.ui.components.LegalLinksFooter(
                    currentLang = currentLang,
                    textColor = if (isDarkTheme) TextMuted else Color.Gray,
                    screenTagPrefix = "choosetool"
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        // Enrollment Dialog Backdrop
        if (confirmationPackage != null) {
            val pkgId = confirmationPackage!!
            val pkgName = when(pkgId) {
                "euee" -> t("pkg_euee_title")
                "freshman_natural" -> t("pkg_freshman_natural_title")
                "freshman_social" -> t("pkg_freshman_social_title")
                "aau_uat" -> t("pkg_uat_title")
                "department" -> t("pkg_dept_title")
                "exit_exam" -> t("pkg_exit_title")
                else -> "Study Plan"
            }
            val modalContext = androidx.compose.ui.platform.LocalContext.current
            EnrollmentConfirmationModal(
                packageId = pkgId,
                packageName = pkgName,
                currentLang = currentLang,
                viewModel = viewModel,
                onDismiss = { viewModel.enrollmentConfirmationPackage.value = null },
                onConfirm = { selectedPackageId ->
                    val activity = AdsManager.findActivity(modalContext)
                    if (activity != null) {
                        InterstitialAdManager.showIfAllowed(activity) {
                            viewModel.enrollInPackage(selectedPackageId)
                            viewModel.currentTab.value = "home"
                        }
                    } else {
                        viewModel.enrollInPackage(selectedPackageId)
                        viewModel.currentTab.value = "home"
                    }
                },
                onUpgradePremium = {
                    viewModel.paywallPackageIdForUpgrade.value = pkgId
                    viewModel.enrollmentConfirmationPackage.value = null
                    viewModel.showPaymentVerificationScreen.value = true
                }
            )
        }
    }
}

data class PackageCardData(
    val id: String,
    val title: String,
    val badge: String,
    val description: String,
    val comingSoon: Boolean
)

@Composable
fun PackageCard(
    data: PackageCardData,
    isEnrolled: Boolean,
    isApproved: Boolean = false,
    isResetMode: Boolean = false,
    isDarkTheme: Boolean = false,
    currentLang: String = "en",
    onEnrollClick: () -> Unit
) {
    fun t(key: String): String = TranslationManager.get(key, currentLang)

    val pkgIcon = when (data.id) {
        "euee" -> Icons.Default.School
        "freshman_natural" -> Icons.Default.LocalLibrary
        "freshman_social" -> Icons.Default.LocalLibrary
        "aau_uat" -> Icons.Default.AutoAwesome
        "department" -> Icons.Default.AccountBalance
        "exit_exam" -> Icons.Default.Assignment
        else -> Icons.Default.MenuBook
    }

    val brandColor = if (isEnrolled) EmeraldPrimary else if (isApproved) GoldAccent else if (isDarkTheme) HolographicAqua else IndigoMedium

    val containerBg = if (isDarkTheme) {
        if (data.comingSoon) Color(0xFF0F172A).copy(alpha = 0.5f) else CardBgDark
    } else {
        if (data.comingSoon) Color(0xFFF8FAFC) else Color.White
    }

    val cardBorderColor = if (isEnrolled) {
        EmeraldPrimary
    } else if (isApproved) {
        GoldAccent
    } else if (isDarkTheme) {
        Color(0xFF334155).copy(alpha = 0.8f)
    } else {
        Color(0xFFE2E8F0)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .pressBounce(pressedScale = 0.97f),
        shape = RoundedCornerShape(22.dp),
        border = BorderStroke(width = if (isEnrolled) 1.5.dp else 1.dp, color = cardBorderColor),
        colors = CardDefaults.cardColors(
            containerColor = containerBg
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isDarkTheme) 6.dp else if (isEnrolled) 4.dp else 2.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(
                                if (data.comingSoon) Color.LightGray.copy(alpha = 0.2f)
                                else brandColor.copy(alpha = 0.12f)
                            )
                            .border(
                                1.dp,
                                if (data.comingSoon) Color.Transparent else brandColor.copy(alpha = 0.25f),
                                RoundedCornerShape(14.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        DuotoneIcon(
                            name = data.id,
                            isActive = isEnrolled || isApproved,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    Text(
                        text = data.title,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Black,
                            fontSize = 17.sp,
                            letterSpacing = 0.2.sp
                        ),
                        color = if (data.comingSoon) Color.Gray else if (isDarkTheme) Color.White else IndigoSecondary
                    )
                }

                // High-contrast modern badge
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = if (data.comingSoon) Color(0xFFE2E8F0)
                            else if (isEnrolled) EmeraldPrimary
                            else if (isApproved) GoldAccent
                            else if (isDarkTheme) Color(0xFF78350F).copy(alpha = 0.6f)
                            else GoldLight,
                    border = BorderStroke(
                        1.dp,
                        if (isEnrolled) EmeraldPrimary else if (isApproved) GoldAccent else GoldAccent.copy(alpha = 0.4f)
                    )
                ) {
                    Text(
                        text = if (isEnrolled) t("status_active") else if (isApproved) t("status_unlocked") else data.badge.uppercase(),
                        color = if (data.comingSoon) Color.DarkGray else if (isEnrolled) Color.White else if (isApproved) Color(0xFF0F172A) else if (isDarkTheme) GoldLight else GoldDark,
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 9.sp,
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 0.5.sp
                        ),
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = data.description,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 13.sp,
                    lineHeight = 20.sp
                ),
                color = if (data.comingSoon) Color.Gray else if (isDarkTheme) TextMuted else Color.DarkGray
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Action Button
            if (data.comingSoon) {
                Button(
                    onClick = {},
                    enabled = false,
                    modifier = Modifier.fillMaxWidth(),
                    shape = ChamferedCardShape,
                    colors = ButtonDefaults.buttonColors(
                        disabledContainerColor = Color(0xFFE5E7EB),
                        disabledContentColor = Color(0xFF9CA3AF)
                    )
                ) {
                    Text(
                        text = t("status_coming_soon"),
                        style = MaterialTheme.typography.titleMedium.copy(fontSize = 14.sp),
                        fontWeight = FontWeight.Bold
                    )
                }
            } else {
                Button(
                    onClick = onEnrollClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .pressBounce()
                        .testTag("enroll_button_${data.id}"),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isEnrolled) IndigoMedium else if (isApproved) EmeraldPrimary else EmeraldPrimary,
                        contentColor = Color.White
                    )
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (isEnrolled) {
                            Icon(Icons.Default.Check, contentDescription = null, tint = Color.White)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = t("btn_continue_prep"),
                                style = MaterialTheme.typography.titleMedium.copy(fontSize = 14.sp),
                                fontWeight = FontWeight.Bold
                            )
                        } else if (isApproved) {
                            Icon(Icons.Default.LockOpen, contentDescription = null, tint = Color.White)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (isResetMode) t("btn_go_back") else t("btn_switch_program"),
                                style = MaterialTheme.typography.titleMedium.copy(fontSize = 14.sp),
                                fontWeight = FontWeight.Bold
                            )
                        } else {
                            Text(
                                text = t("btn_enroll_free_trial"),
                                style = MaterialTheme.typography.titleMedium.copy(fontSize = 14.sp),
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EnrollmentConfirmationModal(
    packageId: String,
    packageName: String,
    currentLang: String = "en",
    viewModel: StudyViewModel,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit,
    onUpgradePremium: () -> Unit
) {
    var selectedStream by remember { mutableStateOf("natural") } // "natural" or "social"
    val years = listOf("Year 2", "Year 3", "Year 4", "Year 5")
    var selectedYear by remember { mutableStateOf("Year 2") }
    var selectedDept by remember { mutableStateOf("Accounting and Finance") }
    val departments = listOf(
        "Accounting and Finance",
        "Economics",
        "Management",
        "Logistics and Supply Chain Management (LSCM)",
        "Business Administration and Information Systems (BAIS)",
        "Political Science and International Relations (PSIR)",
        "Marketing Management",
        "Public Administration and Development Management (PADM)",
        "Computer Science",
        "Information Sciences",
        "Psychology",
        "Software Engineering",
        "Mechanical Engineering",
        "Electrical Engineering",
        "Law"
    )
    fun t(key: String): String = TranslationManager.get(key, currentLang)

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .border(
                    width = 1.5.dp,
                    brush = Brush.linearGradient(
                        listOf(Color.White.copy(alpha = 0.5f), HolographicAqua.copy(alpha = 0.5f))
                    ),
                    shape = ChamferedCardShape
                ),
            shape = ChamferedCardShape,
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF0F172A).copy(alpha = 0.96f)
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .holographicShimmer()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(ChamferedCardShape)
                        .background(EmeraldPrimary.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.WorkspacePremium,
                        contentDescription = null,
                        tint = GoldAccent,
                        modifier = Modifier.size(36.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = t("modal_confirm_enrollment"),
                    style = MaterialTheme.typography.displayMedium,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = packageName,
                    style = MaterialTheme.typography.titleMedium,
                    color = GoldAccent,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 4.dp, bottom = 12.dp)
                )

                if (packageId == "euee" || packageId == "freshman") {
                    Text(
                        text = t("modal_choose_stream"),
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Black, letterSpacing = 1.5.sp),
                        color = GoldAccent,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Natural Science Card Button
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .clickable { selectedStream = "natural" }
                                .border(
                                    1.5.dp,
                                    if (selectedStream == "natural") EmeraldPrimary else Color.Transparent,
                                    RoundedCornerShape(12.dp)
                                ),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (selectedStream == "natural") Color(0xFF1E293B) else Color(0xFF0F172A)
                            )
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Science,
                                    contentDescription = null,
                                    tint = if (selectedStream == "natural") EmeraldPrimary else Color.Gray,
                                    modifier = Modifier.size(24.dp)
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = t("stream_natural"),
                                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                    color = Color.White
                                )
                            }
                        }

                        // Social Science Card Button
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .clickable { selectedStream = "social" }
                                .border(
                                    1.5.dp,
                                    if (selectedStream == "social") EmeraldPrimary else Color.Transparent,
                                    RoundedCornerShape(12.dp)
                                ),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (selectedStream == "social") Color(0xFF1E293B) else Color(0xFF0F172A)
                            )
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Public,
                                    contentDescription = null,
                                    tint = if (selectedStream == "social") EmeraldPrimary else Color.Gray,
                                    modifier = Modifier.size(24.dp)
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = t("stream_social"),
                                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                    color = Color.White
                                )
                            }
                        }
                    }
                }

                var selectedPlan by remember { mutableStateOf("sem1") } // "sem1", "sem2", "full_year"
                var selectedCocField by remember { mutableStateOf("coc_medicine") }

                if (packageId == "freshman" || packageId == "department") {
                    Text(
                        text = "CHOOSE STUDY PLAN",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Black, letterSpacing = 1.5.sp),
                        color = GoldAccent,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        val plans = listOf(
                            "sem1" to ("Sem 1" to "300 ETB"),
                            "sem2" to ("Sem 2" to "300 ETB"),
                            "full_year" to ("Full Year" to "500 ETB")
                        )
                        plans.forEach { (pKey, pInfo) ->
                            val isSel = selectedPlan == pKey
                            Surface(
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { selectedPlan = pKey },
                                shape = RoundedCornerShape(10.dp),
                                color = if (isSel) EmeraldPrimary.copy(alpha = 0.25f) else Color(0xFF1E293B),
                                border = BorderStroke(1.dp, if (isSel) EmeraldPrimary else Color.Transparent)
                            ) {
                                Column(
                                    modifier = Modifier.padding(vertical = 10.dp, horizontal = 4.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = pInfo.first,
                                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                        color = Color.White
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = pInfo.second,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = if (isSel) GoldAccent else TextMuted
                                    )
                                }
                            }
                        }
                    }
                } else if (packageId == "coc") {
                    var selectedCocMainCategory by remember { mutableStateOf("medical") } // "medical", "engineering", "architecture", "cs_is", "law"
                    var selectedMedicalSubField by remember { mutableStateOf("coc_medicine") } // "coc_medicine", "coc_dentistry", "coc_pharmacy"

                    Text(
                        text = "SELECT YOUR COC CATEGORY",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Black, letterSpacing = 1.5.sp),
                        color = GoldAccent,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    val mainCategories = listOf(
                        "medical" to "🩺 Medical",
                        "engineering" to "🏗️ Engineering",
                        "architecture" to "🏛️ Architecture",
                        "cs_is" to "💻 Computer Science & Info",
                        "law" to "⚖️ Law"
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        mainCategories.forEach { (catKey, catLabel) ->
                            val isSel = selectedCocMainCategory == catKey
                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        selectedCocMainCategory = catKey
                                        if (catKey != "medical") {
                                            selectedCocField = when (catKey) {
                                                "engineering" -> "coc_engineering"
                                                "architecture" -> "coc_architecture"
                                                "cs_is" -> "coc_cs_is"
                                                "law" -> "coc_law"
                                                else -> "coc_medicine"
                                            }
                                        } else {
                                            selectedCocField = selectedMedicalSubField
                                        }
                                    },
                                shape = RoundedCornerShape(10.dp),
                                color = if (isSel) EmeraldPrimary.copy(alpha = 0.25f) else Color(0xFF1E293B),
                                border = BorderStroke(1.dp, if (isSel) EmeraldPrimary else Color.Transparent)
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = catLabel,
                                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                        color = Color.White
                                    )
                                    Text(
                                        text = "300 ETB",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = GoldAccent
                                    )
                                }
                            }
                        }
                    }

                    // Nested Medical Field Sub-Selection
                    if (selectedCocMainCategory == "medical") {
                        Text(
                            text = "SELECT MEDICAL FIELD",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Black, letterSpacing = 1.5.sp),
                            color = GoldAccent,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        val medicalSubFields = listOf(
                            "coc_medicine" to "🩺 Medicine",
                            "coc_dentistry" to "🦷 Dental Medicine",
                            "coc_pharmacy" to "💊 Pharmacy"
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            medicalSubFields.forEach { (mKey, mLabel) ->
                                val isSel = selectedMedicalSubField == mKey
                                Surface(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clickable {
                                            selectedMedicalSubField = mKey
                                            selectedCocField = mKey
                                        },
                                    shape = RoundedCornerShape(10.dp),
                                    color = if (isSel) EmeraldPrimary.copy(alpha = 0.35f) else Color(0xFF1E293B),
                                    border = BorderStroke(1.5.dp, if (isSel) EmeraldPrimary else Color.Transparent)
                                ) {
                                    Column(
                                        modifier = Modifier.padding(vertical = 10.dp, horizontal = 4.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(
                                            text = mLabel,
                                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                                            color = Color.White,
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                if (packageId == "department") {
                    Text(
                        text = "SELECT YOUR CURRENT STUDY YEAR",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Black, letterSpacing = 1.5.sp),
                        color = GoldAccent,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        years.forEach { yr ->
                            val isSelected = selectedYear == yr
                            Surface(
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable {
                                        selectedYear = yr
                                        viewModel.saveDepartmentSetup(selectedDept, yr)
                                    },
                                shape = RoundedCornerShape(10.dp),
                                color = if (isSelected) EmeraldPrimary.copy(alpha = 0.2f) else Color(0xFF1E293B),
                                border = BorderStroke(1.dp, if (isSelected) EmeraldPrimary else Color.Transparent)
                            ) {
                                Column(
                                    modifier = Modifier.padding(vertical = 12.dp, horizontal = 4.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icon(
                                        imageVector = if (isSelected) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
                                        contentDescription = null,
                                        tint = if (isSelected) EmeraldPrimary else Color.Gray,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = yr,
                                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal),
                                        color = Color.White
                                    )
                                }
                            }
                        }
                    }
                }

                val currentPriceETB = if (selectedPlan == "full_year") 500 else 300

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // 1. Premium Upgrade Button
                    Button(
                        onClick = {
                            if (packageId == "department" || packageId == "exit_exam") {
                                viewModel.saveDepartmentSetup("", selectedYear)
                            }
                            val streamSuffix = if (selectedStream == "natural") "natural_science" else "social_science"
                            val targetProductId = when {
                                packageId == "freshman" -> "freshman_${streamSuffix}_y1_${selectedPlan}"
                                packageId == "euee" -> "euee_${selectedStream}"
                                packageId == "coc" -> selectedCocField
                                else -> packageId
                            }
                            val matchedProduct = com.example.data.ProductCatalog.get(targetProductId)
                                ?: com.example.data.Product(
                                    id = targetProductId,
                                    category = packageId,
                                    stream = streamSuffix,
                                    academicYear = 1,
                                    plan = selectedPlan,
                                    amount = currentPriceETB,
                                    shortLabel = if (selectedPlan == "full_year") "Full Academic Year" else "Semester ${if (selectedPlan == "sem1") "1" else "2"}"
                                )
                            viewModel.createPurchaseRequest(matchedProduct)
                            val semFilter = when (selectedPlan) {
                                "sem1" -> "sem1"
                                "sem2" -> "sem2"
                                else -> "all"
                            }
                            viewModel.setSemesterFilter(semFilter)
                            onUpgradePremium()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .pressBounce()
                            .testTag("modal_upgrade_premium_button"),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = GoldAccent,
                            contentColor = Color(0xFF0F172A)
                        )
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(imageVector = Icons.Default.WorkspacePremium, contentDescription = null, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Go Premium ($currentPriceETB ETB)",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1,
                                overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                            )
                        }
                    }

                    // 2. Free Trial Button
                    val context = androidx.compose.ui.platform.LocalContext.current
                    Button(
                        onClick = {
                            val targetPackageId = when (packageId) {
                                "euee" -> if (selectedStream == "natural") "euee_natural" else "euee_social"
                                "freshman" -> if (selectedStream == "natural") "freshman_natural" else "freshman_social"
                                "coc" -> selectedCocField
                                else -> {
                                    if (packageId == "department" || packageId == "exit_exam") {
                                        viewModel.saveDepartmentSetup("", selectedYear)
                                    }
                                    packageId
                                }
                            }
                            viewModel.activateFreeTrialConfirmed(targetPackageId, context)
                            onConfirm(targetPackageId)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("modal_confirm_button"),
                        shape = ChamferedCardShape,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = EmeraldPrimary,
                            contentColor = Color.White
                        )
                    ) {
                        Text(
                            text = t("btn_activate_trial"),
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // 3. Cancel Button
                    TextButton(
                        onClick = onDismiss,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = t("btn_cancel_back"),
                            color = Color.LightGray,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FreeTrialConfirmationModal(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "⏰ 72-HOUR FREE TRIAL",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.5.sp
                    ),
                    color = GoldAccent
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "You are activating a 72-hour free trial.\n\nYou have exactly 72 hours (3 days) to explore your trial course. If you do not purchase the full package for 300 ETB, access will lock automatically after 72 hours.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp
                )
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = onConfirm,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = EmeraldPrimary,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "I Understand & Activate",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                TextButton(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Cancel",
                        color = Color.LightGray
                    )
                }
            }
        }
    }
}
