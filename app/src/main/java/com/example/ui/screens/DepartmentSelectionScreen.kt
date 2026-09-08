package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.StudyViewModel
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DepartmentSelectionScreen(viewModel: StudyViewModel) {
    var searchQuery by remember { mutableStateOf("") }
    val isDarkTheme by viewModel.isDarkTheme.collectAsState()
    val academicYear by viewModel.academicYear.collectAsState()

    val facultyDepartments = remember {
        listOf(
            "Business & Economics" to listOf(
                "Accounting and Finance",
                "Economics",
                "Management",
                "Marketing Management",
                "Logistics and Supply Chain Management (LSCM)",
                "Business Administration and Information Systems (BAIS)",
                "Public Administration and Development Management (PADM)"
            ),
            "Computing & Informatics" to listOf(
                "Computer Science",
                "Software Engineering",
                "Information Sciences"
            ),
            "Engineering & Technology" to listOf(
                "Electrical Engineering",
                "Mechanical Engineering"
            ),
            "Social Sciences & Humanities" to listOf(
                "Psychology",
                "Political Science and International Relations (PSIR)"
            ),
            "Law & Governance" to listOf(
                "Law"
            )
        )
    }

    val filteredFaculties = remember(searchQuery) {
        if (searchQuery.isBlank()) {
            facultyDepartments
        } else {
            facultyDepartments.mapNotNull { (faculty, depts) ->
                val matching = depts.filter { it.contains(searchQuery, ignoreCase = true) }
                if (matching.isNotEmpty()) faculty to matching else null
            }
        }
    }

    Scaffold(
        containerColor = if (isDarkTheme) ReaderBgDark else BgOffWhite,
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "SELECT YOUR DEPARTMENT",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Black,
                                letterSpacing = 1.sp
                            ),
                            color = if (isDarkTheme) Color.White else IndigoSecondary
                        )
                        Text(
                            text = if (academicYear.isNotBlank()) "Enrolled Level: " else "University Department Program",
                            style = MaterialTheme.typography.labelSmall,
                            color = EmeraldPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = if (isDarkTheme) CardBgDark else Color.White
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Search department name...", color = TextMuted) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = EmeraldPrimary) },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(Icons.Default.Close, contentDescription = "Clear", tint = TextMuted)
                        }
                    }
                },
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = EmeraldPrimary,
                    unfocusedBorderColor = if (isDarkTheme) Color(0xFF334155) else Color(0xFFCBD5E1),
                    focusedContainerColor = if (isDarkTheme) CardBgDark else Color.White,
                    unfocusedContainerColor = if (isDarkTheme) CardBgDark else Color.White,
                    focusedTextColor = if (isDarkTheme) Color.White else Color.Black,
                    unfocusedTextColor = if (isDarkTheme) Color.White else Color.Black
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(bottom = 32.dp)
            ) {
                filteredFaculties.forEach { (facultyName, departments) ->
                    item {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = GoldAccent.copy(alpha = 0.12f),
                            border = BorderStroke(1.dp, GoldAccent.copy(alpha = 0.3f)),
                            modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
                        ) {
                            Text(
                                text = facultyName.uppercase(),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Black,
                                    letterSpacing = 1.sp,
                                    fontSize = 10.sp
                                ),
                                color = GoldAccent,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    items(departments) { dept ->
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    val currentYr = if (academicYear.isNotBlank()) academicYear else "Year 2"
                                    viewModel.saveDepartmentSetup(dept, currentYr)
                                },
                            shape = RoundedCornerShape(14.dp),
                            color = if (isDarkTheme) Color(0xFF131B2E) else Color.White,
                            border = BorderStroke(
                                1.dp,
                                if (isDarkTheme) Color(0xFF334155).copy(alpha = 0.8f) else Color(0xFFE2E8F0)
                            ),
                            shadowElevation = 3.dp
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 14.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(36.dp)
                                            .background(
                                                EmeraldPrimary.copy(alpha = 0.15f),
                                                RoundedCornerShape(8.dp)
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.School,
                                            contentDescription = null,
                                            tint = EmeraldPrimary,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(14.dp))
                                    Text(
                                        text = dept,
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            fontWeight = FontWeight.SemiBold,
                                            fontSize = 13.5.sp
                                        ),
                                        color = if (isDarkTheme) Color.White else Color(0xFF0F172A)
                                    )
                                }
                                Box(
                                    modifier = Modifier
                                        .size(28.dp)
                                        .background(
                                            Color.White.copy(alpha = 0.05f),
                                            RoundedCornerShape(6.dp)
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.ChevronRight,
                                        contentDescription = "Select",
                                        tint = EmeraldPrimary,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
