package com.example.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.ui.StudyViewModel
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.theme.IndigoSecondary

@Composable
fun DepartmentSetupModal(viewModel: StudyViewModel, onDismiss: () -> Unit) {
    var selectedYear by remember { mutableStateOf("") }
    val years = listOf("Year 2", "Year 3", "Year 4", "Year 5")
    val departmentName by viewModel.academicDepartment.collectAsState()

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Select Academic Year",
                    style = MaterialTheme.typography.titleLarge,
                    color = IndigoSecondary,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "You selected $departmentName. Which year are you in?",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                // Year Selection
                Row(
                    modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()).padding(bottom = 24.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    years.forEach { year ->
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = if (selectedYear == year) EmeraldPrimary else Color(0xFFF1F5F9),
                            modifier = Modifier.clickable { selectedYear = year }
                        ) {
                            Text(
                                text = year,
                                color = if (selectedYear == year) Color.White else Color.Gray,
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                Button(
                    onClick = {
                        if (selectedYear.isNotEmpty()) {
                            viewModel.saveDepartmentSetup(departmentName, selectedYear)
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
                    shape = RoundedCornerShape(12.dp),
                    enabled = selectedYear.isNotEmpty()
                ) {
                    Text("Save & Proceed", color = Color.White, fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 4.dp))
                }
            }
        }
    }
}
