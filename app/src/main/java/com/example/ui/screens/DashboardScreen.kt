package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.AbsoluteCutCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ads.InterstitialAdManager
import com.example.ads.AdsManager
import com.example.ui.StudyViewModel
import com.example.ui.TranslationManager
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import com.example.ui.components.*
import com.example.ui.theme.*

// Styling a smooth rounded shape
val HexagonChamferShape = RoundedCornerShape(16.dp)

@Composable
fun DashboardScreen(viewModel: StudyViewModel) {
    val progress by viewModel.userProgress.collectAsState()
    val subjectsList by viewModel.subjects.collectAsState()
    val subjectProgressMap by viewModel.subjectProgressMap.collectAsState()
    val completedSubjectIds = remember(progress.completedSubjects) {
        progress.completedSubjects.split(",").filter { it.isNotEmpty() }.toSet()
    }

    val studentName by viewModel.studentName.collectAsState()
    val studentGoal by viewModel.studentGoal.collectAsState()
    val currentLang by viewModel.currentLanguage.collectAsState()
    val isDarkTheme by viewModel.isDarkTheme.collectAsState()

    fun t(key: String): String = TranslationManager.get(key, currentLang)

    val academicDepartment by viewModel.academicDepartment.collectAsState()
    val academicYear by viewModel.academicYear.collectAsState()

    // Department Curriculum Definition by Year
    val departmentCurriculum = remember(academicDepartment) {
        val isEngineering = academicDepartment.contains("Engineering", ignoreCase = true)
        val dept = academicDepartment.ifBlank { "Department" }

        val year2Courses = when {
            dept.contains("Accounting", ignoreCase = true) -> listOf(
                StudySubject("dept_fin_acc_1", "Financial Accounting I", "accounting", "department"),
                StudySubject("dept_cost_acc", "Cost and Management Accounting I", "accounting", "department"),
                StudySubject("dept_business_law", "Business Law", "civics", "department"),
                StudySubject("dept_micro_econ", "Microeconomics", "economics", "department")
            )
            dept.contains("Economics", ignoreCase = true) -> listOf(
                StudySubject("dept_micro_1", "Intermediate Microeconomics I", "economics", "department"),
                StudySubject("dept_macro_1", "Intermediate Macroeconomics I", "economics", "department"),
                StudySubject("dept_math_econ", "Calculus for Economists", "maths", "department"),
                StudySubject("dept_stats_econ", "Statistics for Economists", "analytics", "department")
            )
            dept.contains("Computer Science", ignoreCase = true) || dept.contains("Software", ignoreCase = true) -> listOf(
                StudySubject("dept_dsa", "Data Structures & Algorithms", "computer", "department"),
                StudySubject("dept_oop", "Object Oriented Programming (Java/C++)", "software", "department"),
                StudySubject("dept_db_sys", "Database Systems & SQL", "analytics", "department"),
                StudySubject("dept_comp_org", "Computer Architecture & Organization", "computer", "department")
            )
            dept.contains("Electrical", ignoreCase = true) -> listOf(
                StudySubject("dept_circuit_1", "Electric Circuits I", "electrical", "department"),
                StudySubject("dept_applied_math_1", "Applied Mathematics I", "maths", "department"),
                StudySubject("dept_electromagnetics", "Electromagnetic Fields", "physics", "department"),
                StudySubject("dept_electronics_1", "Basic Electronics", "electrical", "department")
            )
            dept.contains("Mechanical", ignoreCase = true) -> listOf(
                StudySubject("dept_engineering_mechanics", "Engineering Mechanics (Statics)", "mechanical", "department"),
                StudySubject("dept_thermodynamics_1", "Thermodynamics I", "mechanical", "department"),
                StudySubject("dept_materials_sci", "Materials Science and Engineering", "chemistry", "department"),
                StudySubject("dept_applied_math_mech", "Applied Mathematics I", "maths", "department")
            )
            dept.contains("Law", ignoreCase = true) -> listOf(
                StudySubject("dept_constitutional_law", "Law of Constitutional Governance", "civics", "department"),
                StudySubject("dept_contracts_law", "Law of Contracts I", "civics", "department"),
                StudySubject("dept_criminal_law", "Criminal Law I", "civics", "department"),
                StudySubject("dept_legal_research", "Legal Research and Writing", "english", "department")
            )
            else -> listOf(
                StudySubject("dept_core_1", "$dept Core Fundamentals", "computer", "department"),
                StudySubject("dept_methods_1", "Research Methods & Quantitative Tools", "analytics", "department"),
                StudySubject("dept_theory_1", "Applied Professional Theory I", "management", "department"),
                StudySubject("dept_ethics_1", "Professional Ethics & Governance", "civics", "department")
            )
        }

        val year3Courses = when {
            dept.contains("Accounting", ignoreCase = true) -> listOf(
                StudySubject("dept_fin_acc_2", "Financial Accounting II", "accounting", "department"),
                StudySubject("dept_auditing_1", "Principles of Auditing I", "accounting", "department"),
                StudySubject("dept_tax_acc", "Ethiopian Tax Accounting", "accounting", "department"),
                StudySubject("dept_corp_fin", "Corporate Finance", "business", "department")
            )
            dept.contains("Economics", ignoreCase = true) -> listOf(
                StudySubject("dept_econometrics_1", "Introduction to Econometrics", "analytics", "department"),
                StudySubject("dept_dev_econ", "Development Economics", "economics", "department"),
                StudySubject("dept_monetary_econ", "Monetary and Banking Economics", "economics", "department"),
                StudySubject("dept_pub_fin", "Public Finance", "business", "department")
            )
            dept.contains("Computer Science", ignoreCase = true) || dept.contains("Software", ignoreCase = true) -> listOf(
                StudySubject("dept_os", "Operating Systems & Concurrency", "computer", "department"),
                StudySubject("dept_networks", "Computer Networks & Protocols", "computer", "department"),
                StudySubject("dept_soft_eng", "Software Engineering Principles", "software", "department"),
                StudySubject("dept_web_dev", "Web Architecture & Fullstack Dev", "computer", "department")
            )
            dept.contains("Electrical", ignoreCase = true) -> listOf(
                StudySubject("dept_signals_sys", "Signals and Systems", "electrical", "department"),
                StudySubject("dept_power_sys", "Electrical Power Systems", "electrical", "department"),
                StudySubject("dept_control_sys", "Control Systems Engineering", "electrical", "department"),
                StudySubject("dept_microprocessors", "Microprocessors & Embedded Systems", "computer", "department")
            )
            dept.contains("Mechanical", ignoreCase = true) -> listOf(
                StudySubject("dept_fluid_mechanics", "Fluid Mechanics", "mechanical", "department"),
                StudySubject("dept_heat_transfer", "Heat Transfer & Cooling", "mechanical", "department"),
                StudySubject("dept_machine_design", "Machine Elements Design I", "mechanical", "department"),
                StudySubject("dept_dynamics", "Dynamics of Machinery", "mechanical", "department")
            )
            dept.contains("Law", ignoreCase = true) -> listOf(
                StudySubject("dept_property_law", "Ethiopian Property & Land Law", "civics", "department"),
                StudySubject("dept_commercial_law", "Commercial Law & Business Entities", "civics", "department"),
                StudySubject("dept_admin_law", "Administrative Law", "civics", "department"),
                StudySubject("dept_human_rights", "Human Rights Law", "civics", "department")
            )
            else -> listOf(
                StudySubject("dept_core_2", "Advanced $dept Studies", "computer", "department"),
                StudySubject("dept_methods_2", "Statistical Modeling & Analytics", "analytics", "department"),
                StudySubject("dept_project_prep", "Departmental Project Studio", "management", "department"),
                StudySubject("dept_applied_policy", "Policy and Systems Framework", "civics", "department")
            )
        }

        val year4Courses = when {
            dept.contains("Accounting", ignoreCase = true) -> listOf(
                StudySubject("dept_advanced_acc", "Advanced Financial Accounting", "accounting", "department"),
                StudySubject("dept_acct_info_sys", "Accounting Information Systems", "analytics", "department"),
                StudySubject("dept_gov_acc", "Public Sector and Fund Accounting", "accounting", "department"),
                StudySubject("dept_senior_research_acc", "Senior Accounting Research", "business", "department")
            )
            dept.contains("Economics", ignoreCase = true) -> listOf(
                StudySubject("dept_adv_econometrics", "Applied Econometrics & Time Series", "analytics", "department"),
                StudySubject("dept_intl_trade", "International Economics & Trade", "economics", "department"),
                StudySubject("dept_eth_econ", "The Ethiopian Economy: Policy & Growth", "economics", "department"),
                StudySubject("dept_econ_thesis", "Undergraduate Research Project", "economics", "department")
            )
            dept.contains("Computer Science", ignoreCase = true) || dept.contains("Software", ignoreCase = true) -> listOf(
                StudySubject("dept_distributed_sys", "Distributed Systems & Cloud Computing", "computer", "department"),
                StudySubject("dept_ai_ml", "Artificial Intelligence & Data Mining", "computer", "department"),
                StudySubject("dept_cybersecurity", "Information Security & Cryptography", "computer", "department"),
                StudySubject("dept_capstone_1", "Senior Capstone Project I", "software", "department")
            )
            dept.contains("Electrical", ignoreCase = true) -> listOf(
                StudySubject("dept_comm_systems", "Telecommunications Engineering", "electrical", "department"),
                StudySubject("dept_power_electronics", "Power Electronics & Drives", "electrical", "department"),
                StudySubject("dept_power_protection", "Power System Protection & Relay", "electrical", "department"),
                StudySubject("dept_ee_capstone_1", "Engineering Capstone Project I", "electrical", "department")
            )
            dept.contains("Mechanical", ignoreCase = true) -> listOf(
                StudySubject("dept_cad_cam", "CAD/CAM and Industrial Automation", "mechanical", "department"),
                StudySubject("dept_refrigeration", "HVAC & Thermal Systems", "mechanical", "department"),
                StudySubject("dept_ic_engines", "Internal Combustion Engines", "mechanical", "department"),
                StudySubject("dept_mech_capstone_1", "Mechanical Capstone Project I", "mechanical", "department")
            )
            dept.contains("Law", ignoreCase = true) -> listOf(
                StudySubject("dept_evidence_law", "Law of Evidence & Trial Practice", "civics", "department"),
                StudySubject("dept_criminal_proc", "Criminal Procedure", "civics", "department"),
                StudySubject("dept_civil_proc", "Civil Procedure & Litigation", "civics", "department"),
                StudySubject("dept_intl_law", "Public International Law", "civics", "department")
            )
            else -> listOf(
                StudySubject("dept_seminar_adv", "Senior Academic Seminar", "management", "department"),
                StudySubject("dept_internship_app", "Professional Field Practicum", "business", "department"),
                StudySubject("dept_strategic_mgmt", "Strategic Systems Management", "management", "department"),
                StudySubject("dept_grad_thesis", "Graduation Research Project", "computer", "department")
            )
        }

        val year5Courses = if (isEngineering) {
            when {
                dept.contains("Electrical", ignoreCase = true) -> listOf(
                    StudySubject("dept_ee_high_voltage", "High Voltage Engineering", "electrical", "department"),
                    StudySubject("dept_ee_smart_grid", "Renewable Energy & Smart Grid", "electrical", "department"),
                    StudySubject("dept_ee_final_thesis", "Final Year Engineering Thesis", "electrical", "department")
                )
                dept.contains("Mechanical", ignoreCase = true) -> listOf(
                    StudySubject("dept_mech_tribology", "Industrial Maintenance & Tribology", "mechanical", "department"),
                    StudySubject("dept_mech_power_plant", "Power Plant Engineering", "mechanical", "department"),
                    StudySubject("dept_mech_final_thesis", "Final Year Engineering Thesis", "mechanical", "department")
                )
                else -> listOf(
                    StudySubject("dept_eng_mgmt", "Engineering Economics & Project Management", "management", "department"),
                    StudySubject("dept_eng_internship", "Industrial Engineering Internship", "mechanical", "department"),
                    StudySubject("dept_eng_final_capstone", "Senior Design Capstone II", "software", "department")
                )
            }
        } else {
            emptyList()
        }

        listOf(
            "Year 2" to year2Courses,
            "Year 3" to year3Courses,
            "Year 4" to year4Courses
        ) + if (isEngineering) listOf("Year 5" to year5Courses) else emptyList()
    }

    val enrolledYearNumber = remember(academicYear) {
        when {
            academicYear.contains("5") -> 5
            academicYear.contains("4") -> 4
            academicYear.contains("3") -> 3
            else -> 2
        }
    }

    val isDepartmentMode = progress.activePackageId == "department"

    val bgModifier = Modifier.background(Color.Transparent)

    // Outer layout with custom background gradients or dark reader canvas
    Box(
        modifier = Modifier
            .fillMaxSize()
            .then(bgModifier)
    ) {
        val showCelebration by viewModel.showCompletionParticles.collectAsState()
        if (showCelebration) {
            CompletionParticleEffect()
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(top = 24.dp, bottom = 100.dp)
        ) {
            // Header item containing Greeting and dropdowns (span across both columns)
            item(span = { GridItemSpan(maxLineSpan) }) {
                Column {
                    GreetingHeader(viewModel = viewModel, username = studentName.ifEmpty { progress.username })
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            // Header item containing Section Title
            item(span = { GridItemSpan(maxLineSpan) }) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val sectionTitle = when (progress.activePackageId) {
                        "euee_natural", "euee_social" -> t("high_yield_topics")
                        "freshman" -> t("freshman_curriculum")
                        "aau_uat" -> t("aau_uat_matrix")
                        "department" -> if (academicDepartment.isNotBlank()) "${academicDepartment.uppercase()} CURRICULUM" else t("dept_course_matrix")
                        "exit_exam" -> t("exit_exam_blueprints")
                        else -> t("course_syllabus")
                    }

                    Text(
                        text = sectionTitle,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Black,
                            fontSize = 14.sp,
                            letterSpacing = 0.5.sp
                        ),
                        maxLines = 1,
                        overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
                        color = if (progress.activePackageId == "exit_exam") Color(0xFFDC2626) else if (isDarkTheme) Color.White else IndigoSecondary,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = java.lang.String.format(t("view_all_progress"), completedSubjectIds.size.toString(), subjectsList.size.toString()),
                        style = MaterialTheme.typography.labelMedium,
                        color = EmeraldDark,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1
                    )
                }
            }

            // Spacing below header
            item(span = { GridItemSpan(maxLineSpan) }) {
                Spacer(modifier = Modifier.height(12.dp))
            }

            if (isDepartmentMode) {
                // Multi-Year Department Curriculum Section
                departmentCurriculum.forEach { (yearLabel, yearCourses) ->
                    val yearNum = when {
                        yearLabel.contains("5") -> 5
                        yearLabel.contains("4") -> 4
                        yearLabel.contains("3") -> 3
                        else -> 2
                    }
                    val isYearLocked = yearNum > enrolledYearNumber && progress.paymentStatus != "approved"

                    // Year Header Banner
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp, bottom = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = if (isYearLocked) Color(0xFF475569) else EmeraldPrimary,
                                modifier = Modifier.padding(end = 12.dp)
                            ) {
                                Text(
                                    text = yearLabel.uppercase(),
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Black),
                                    color = Color.White
                                )
                            }
                            HorizontalDivider(
                                modifier = Modifier.weight(1f),
                                color = if (isDarkTheme) Color(0xFF334155) else Color(0xFFE2E8F0),
                                thickness = 1.dp
                            )
                            if (isYearLocked) {
                                Spacer(modifier = Modifier.width(8.dp))
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Lock,
                                        contentDescription = "Locked",
                                        tint = Color(0xFFEF4444),
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Text(
                                        text = "Upgrade to Unlock",
                                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                        color = Color(0xFFEF4444),
                                        fontSize = 10.sp
                                    )
                                }
                            }
                        }
                    }

                    // Courses for this year
                    items(yearCourses) { subject ->
                        val isDone = completedSubjectIds.contains(subject.id)
                        val context = LocalContext.current

                        SubjectHexCard(
                            subjectName = subject.name,
                            iconName = subject.icon,
                            isCompleted = isDone,
                            isLocked = isYearLocked,
                            progress = if (isDone) 1f else 0f,
                            isDarkTheme = isDarkTheme,
                            currentLang = currentLang,
                            onSubjectClick = {
                                if (isYearLocked) {
                                    viewModel.paywallPackageIdForUpgrade.value = "department"
                                    viewModel.showFreeTrialPaywall.value = true
                                } else {
                                    val activity = AdsManager.findActivity(context)
                                    if (activity != null) {
                                        InterstitialAdManager.showIfAllowed(activity) {
                                            viewModel.selectSubject(subject)
                                        }
                                    } else {
                                        viewModel.selectSubject(subject)
                                    }
                                }
                            }
                        )
                    }
                }

                // Exit Exam Preparation Gateway at the bottom of the department senior curriculum
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isDarkTheme) Color(0xFF1E293B) else Color(0xFFEFF6FF)
                        ),
                        border = BorderStroke(1.5.dp, Color(0xFF3B82F6)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 24.dp)
                            .clickable {
                                viewModel.enrollInPackage("exit_exam")
                            }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(18.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "PREPARING FOR GRADUATION?",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.Black,
                                        letterSpacing = 1.sp
                                    ),
                                    color = Color(0xFF3B82F6)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "National Exit Exam Preparation",
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                    color = if (isDarkTheme) Color.White else Color(0xFF1E293B)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Blueprints, ministry practice exams & model questions tailored for $academicDepartment.",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = if (isDarkTheme) TextMuted else Color(0xFF475569)
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Button(
                                onClick = {
                                    viewModel.enrollInPackage("exit_exam")
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3B82F6)),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text("Enroll", color = Color.White, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            } else if (subjectsList.isEmpty()) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isDarkTheme) Color(0xFF1E293B) else Color.White
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.MenuBook,
                                contentDescription = null,
                                tint = EmeraldPrimary,
                                modifier = Modifier.size(44.dp)
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = t("empty_preparing_modules"),
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = if (isDarkTheme) Color.White else IndigoSecondary
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = t("empty_select_preferred"),
                                style = MaterialTheme.typography.bodySmall,
                                color = if (isDarkTheme) TextMuted else Color.Gray,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(
                                onClick = {
                                    viewModel.enrollInPackage("euee_natural")
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text(t("empty_load_subjects"), color = Color.White, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            } else {
                // Grid Items (each taking 1 column span by default)
                items(subjectsList) { subject ->
                    val isDone = completedSubjectIds.contains(subject.id)
                    val isLocked = viewModel.isSubjectLocked(subject)
                    val progressVal = subjectProgressMap[subject.id] ?: if (isDone) 1f else 0f
                    val context = LocalContext.current
                    SubjectHexCard(
                        subjectName = subject.name,
                        iconName = subject.icon,
                        isCompleted = isDone,
                        isLocked = isLocked,
                        progress = progressVal,
                        isDarkTheme = isDarkTheme,
                        currentLang = currentLang,
                        onSubjectClick = {
                            val activity = AdsManager.findActivity(context)
                            if (activity != null) {
                                InterstitialAdManager.showIfAllowed(activity) {
                                    viewModel.selectSubject(subject)
                                }
                            } else {
                                viewModel.selectSubject(subject)
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun GreetingHeader(viewModel: StudyViewModel, username: String) {
    val currentLang by viewModel.currentLanguage.collectAsState()
    val isDarkTheme by viewModel.isDarkTheme.collectAsState()
    var langMenuExpanded by remember { mutableStateOf(false) }

    fun t(key: String): String = TranslationManager.get(key, currentLang)

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.weight(1f)
        ) {
            // Circular profile icon in the top left corner
            Surface(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .clickable { viewModel.currentTab.value = "profile" }
                    .testTag("user_profile_icon"),
                color = if (isDarkTheme) CardBgDark else IndigoSecondary,
                shape = CircleShape,
                shadowElevation = 4.dp
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "User Profile",
                        tint = if (isDarkTheme) HolographicAqua else Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Column(
                modifier = Modifier.weight(1f, fill = false),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = t("home").uppercase(),
                    style = MaterialTheme.typography.labelSmall.copy(
                        letterSpacing = 2.sp,
                        color = if (isDarkTheme) TextMuted else IndigoSecondary.copy(alpha = 0.6f),
                        fontWeight = FontWeight.Bold
                    ),
                    maxLines = 1,
                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                )
                Text(
                    text = "${t("greeting_prefix")}$username",
                    style = MaterialTheme.typography.displayLarge.copy(
                        fontWeight = FontWeight.Black,
                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                        lineHeight = 26.sp,
                        fontSize = 20.sp
                    ),
                    color = if (isDarkTheme) Color.White else IndigoSecondary,
                    maxLines = 1,
                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                )
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Dark / Light Mode symbol toggle (pure symbols, no text)
            Box(
                modifier = Modifier
                    .clip(HexagonChamferShape)
                    .background(if (isDarkTheme) CardBgDark else Color.White)
                    .clickable { viewModel.toggleDarkTheme() }
                    .padding(horizontal = 10.dp, vertical = 7.dp)
                    .border(1.dp, if (isDarkTheme) EmeraldPrimary.copy(alpha = 0.35f) else IndigoLight, HexagonChamferShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (isDarkTheme) Icons.Default.DarkMode else Icons.Default.WbSunny,
                    contentDescription = "Toggle Theme",
                    tint = if (isDarkTheme) HolographicAqua else GoldDark,
                    modifier = Modifier.size(20.dp)
                )
            }

            // Localized Language selector symbol button
            Box {
                Box(
                    modifier = Modifier
                        .clip(HexagonChamferShape)
                        .background(if (isDarkTheme) CardBgDark else Color.White)
                        .clickable { langMenuExpanded = true }
                        .padding(horizontal = 10.dp, vertical = 7.dp)
                        .border(1.dp, if (isDarkTheme) EmeraldPrimary.copy(alpha = 0.35f) else IndigoLight, HexagonChamferShape),
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
                            contentDescription = "Languages",
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
                    val menuItemColor = if (isDarkTheme) Color.White else Color.DarkGray
                    DropdownMenuItem(
                        text = { Text("🇬🇧 English", color = menuItemColor, fontSize = 13.sp) },
                        onClick = {
                            viewModel.setLanguage("en")
                            langMenuExpanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("🇪🇹 አማርኛ (Amharic)", color = menuItemColor, fontSize = 13.sp) },
                        onClick = {
                            viewModel.setLanguage("am")
                            langMenuExpanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("🇪🇹 Afaan Oromoo (Oromifa)", color = menuItemColor, fontSize = 13.sp) },
                        onClick = {
                            viewModel.setLanguage("om")
                            langMenuExpanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("🇪🇹 Soomaali (Somali)", color = menuItemColor, fontSize = 13.sp) },
                        onClick = {
                            viewModel.setLanguage("so")
                            langMenuExpanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("🇪🇹 ትግርኛ (Tigrigna)", color = menuItemColor, fontSize = 13.sp) },
                        onClick = {
                            viewModel.setLanguage("ti")
                            langMenuExpanded = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun SubjectHexCard(
    subjectName: String,
    iconName: String,
    isCompleted: Boolean,
    isLocked: Boolean = false,
    progress: Float = 0f,
    isDarkTheme: Boolean = false,
    currentLang: String = "en",
    onSubjectClick: () -> Unit
) {
    fun t(key: String): String = TranslationManager.get(key, currentLang)

    val isEffectivelyCompleted = isCompleted || progress >= 0.999f

    val cardBg = if (isDarkTheme) {
        if (isLocked) Color(0xFF1E293B) else CardBgDark
    } else {
        if (isLocked) Color(0xFFF8FAFC) else Color.White
    }

    val cardBorderColor = if (isDarkTheme) {
        if (isLocked) Color(0xFF334155)
        else if (isEffectivelyCompleted) EmeraldPrimary
        else Color(0xFF334155)
    } else {
        if (isLocked) Color(0xFFE2E8F0)
        else if (isEffectivelyCompleted) EmeraldPrimary
        else Color(0xFFE2E8F0)
    }

    val iconBoxBg = if (isDarkTheme) {
        if (isLocked) Color(0xFF331E1E)
        else if (isEffectivelyCompleted) Color(0xFF065F46)
        else Color(0xFF1E293B)
    } else {
        if (isLocked) Color(0xFFFEF2F2)
        else if (isEffectivelyCompleted) Color(0xFFECFDF5)
        else Color(0xFFEEF2FF)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .pressBounce()
            .clickable(onClick = onSubjectClick)
            .testTag("subject_card_${iconName}"),
        shape = RoundedCornerShape(18.dp),
        border = BorderStroke(width = 1.dp, color = cardBorderColor),
        colors = CardDefaults.cardColors(
            containerColor = cardBg
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Circular Progress Bar in Top Right Corner showing material usage
            SubjectProgressIndicator(
                progress = progress,
                isCompleted = isCompleted,
                isLocked = isLocked,
                isDarkTheme = isDarkTheme,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 10.dp, end = 10.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Icon Box
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(HexagonChamferShape)
                        .background(iconBoxBg),
                    contentAlignment = Alignment.Center
                ) {
                    DuotoneIcon(
                        name = if (isLocked) "lock" else iconName,
                        isActive = !isLocked && isEffectivelyCompleted,
                        modifier = Modifier.size(32.dp)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = subjectName,
                    style = MaterialTheme.typography.displaySmall.copy(
                        fontWeight = FontWeight.Black,
                        fontSize = 13.sp,
                        letterSpacing = 0.5.sp
                    ),
                    color = if (isLocked) Color.Gray else if (isDarkTheme) Color.White else IndigoSecondary,
                    textAlign = TextAlign.Center,
                    maxLines = 2,
                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Completed / Play / Locked guide indicators
                if (isLocked) {
                    Text(
                        text = t("badge_locked_trial"),
                        color = Color(0xFFEF4444),
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Black,
                            fontSize = 10.sp
                        ),
                        maxLines = 1,
                        overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                    )
                } else if (isEffectivelyCompleted) {
                    Text(
                        text = t("badge_completed"),
                        color = if (isDarkTheme) HolographicAqua else EmeraldDark,
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Black,
                            fontSize = 10.sp
                        ),
                        maxLines = 1,
                        overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                    )
                } else {
                    Text(
                        text = t("badge_tap_to_study"),
                        color = if (isDarkTheme) TextMuted else Color.Gray,
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.sp
                        ),
                        maxLines = 1,
                        overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Composable
fun SubjectProgressIndicator(
    progress: Float,
    isCompleted: Boolean,
    isLocked: Boolean,
    isDarkTheme: Boolean,
    modifier: Modifier = Modifier
) {
    val animatedProgress by animateFloatAsState(
        targetValue = if (isCompleted) 1f else progress.coerceIn(0f, 1f),
        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing),
        label = "subject_progress_anim"
    )

    val progressPercent = (animatedProgress * 100).toInt()
    val trackColor = if (isDarkTheme) Color.White.copy(alpha = 0.12f) else Color(0xFFE2E8F0)
    val progressColor = when {
        isLocked -> Color(0xFF94A3B8)
        isCompleted || animatedProgress >= 0.999f -> EmeraldPrimary
        animatedProgress > 0.5f -> HolographicAqua
        animatedProgress > 0.1f -> GoldDark
        else -> EmeraldPrimary
    }

    Box(
        modifier = modifier
            .size(32.dp)
            .testTag("subject_progress_ring"),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            progress = { if (isLocked) 0f else animatedProgress },
            modifier = Modifier.fillMaxSize(),
            color = progressColor,
            strokeWidth = 2.5.dp,
            trackColor = trackColor,
            strokeCap = StrokeCap.Round
        )

        if (isLocked) {
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = "Locked",
                tint = Color(0xFFEF4444),
                modifier = Modifier.size(11.dp)
            )
        } else if (isCompleted || animatedProgress >= 0.999f) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Completed",
                tint = EmeraldPrimary,
                modifier = Modifier.size(12.dp)
            )
        } else {
            Text(
                text = "$progressPercent%",
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Black,
                    fontSize = if (progressPercent == 100) 8.sp else 8.5.sp
                ),
                color = if (isDarkTheme) Color.White else IndigoSecondary
            )
        }
    }
}
