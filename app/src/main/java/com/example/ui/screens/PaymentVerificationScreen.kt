package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.StudyViewModel
import com.example.ui.theme.*

@Composable
fun SleekPaymentVerificationScreen(
    packageId: String,
    packageName: String,
    viewModel: StudyViewModel,
    onClose: () -> Unit
) {
    var phoneNumber by remember { mutableStateOf("") }
    var redeemCode by remember { mutableStateOf("") }
    val years = listOf("Year 2", "Year 3", "Year 4", "Year 5")
    var selectedYear by remember { mutableStateOf("Year 2") }
    val uriHandler = androidx.compose.ui.platform.LocalUriHandler.current
    val clipboardManager = androidx.compose.ui.platform.LocalClipboardManager.current
    val context = androidx.compose.ui.platform.LocalContext.current
    val currentLang by viewModel.currentLanguage.collectAsState()
    val activationError by viewModel.activationErrorMessage.collectAsState()
    val isActivating by viewModel.contentLoading.collectAsState()

    var copiedCbe by remember { mutableStateOf(false) }
    var copiedTelebirr by remember { mutableStateOf(false) }

    LaunchedEffect(copiedCbe) {
        if (copiedCbe) {
            kotlinx.coroutines.delay(2000)
            copiedCbe = false
        }
    }

    LaunchedEffect(copiedTelebirr) {
        if (copiedTelebirr) {
            kotlinx.coroutines.delay(2000)
            copiedTelebirr = false
        }
    }

    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0F172A).copy(alpha = 0.96f)) // Frosted dark backdrop overlay to block underlying text bleed-through
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF1E293B).copy(alpha = 0.65f))
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onClose) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = Color.White)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = com.example.ui.TranslationManager.get("upgrade_premium", currentLang),
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = Color.White
                    )
                    Text(
                        text = packageName,
                        style = MaterialTheme.typography.bodySmall,
                        color = GoldAccent
                    )
                }
            }

            val purchaseReq by viewModel.purchaseRequest.collectAsState()

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                // Section 2: Clear Bank & Telebirr Manual Payment Instructions
                Text(
                    text = com.example.ui.TranslationManager.get("manual_pay_instr", currentLang),
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Black, letterSpacing = 1.5.sp),
                    color = EmeraldPrimary,
                    modifier = Modifier.align(Alignment.Start).padding(bottom = 8.dp)
                )

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp)
                        .border(
                            width = 1.dp,
                            brush = Brush.linearGradient(listOf(Color.White.copy(alpha = 0.35f), Color.White.copy(alpha = 0.05f))),
                            shape = RoundedCornerShape(12.dp)
                        ),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A).copy(alpha = 0.92f))
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // CBE instructions
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                imageVector = Icons.Default.AccountBalance,
                                contentDescription = null,
                                tint = GoldAccent,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = com.example.ui.TranslationManager.get("cbe_bank", currentLang),
                                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                                    color = Color.White
                                )
                                Text(
                                    text = "Account: 1000721803477\nName: Ananya Bayable Balew",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.LightGray,
                                    lineHeight = 20.sp
                                )
                            }
                            IconButton(
                                onClick = {
                                    clipboardManager.setText(androidx.compose.ui.text.AnnotatedString("1000721803477"))
                                    copiedCbe = true
                                    
                                },
                                modifier = Modifier.testTag("copy_cbe_account_button")
                            ) {
                                Icon(
                                    imageVector = if (copiedCbe) Icons.Default.Check else Icons.Default.ContentCopy,
                                    contentDescription = "Copy Account Number",
                                    tint = if (copiedCbe) EmeraldPrimary else GoldAccent
                                )
                            }
                        }

                        Divider(color = Color(0xFF334155))

                        // Telebirr instructions
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                imageVector = Icons.Default.PhoneAndroid,
                                contentDescription = null,
                                tint = EmeraldPrimary,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = com.example.ui.TranslationManager.get("telebirr", currentLang),
                                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                                    color = Color.White
                                )
                                Text(
                                    text = "Phone: +251932176773\nName: Ananya Bayable Balew",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.LightGray,
                                    lineHeight = 20.sp
                                )
                            }
                            IconButton(
                                onClick = {
                                    clipboardManager.setText(androidx.compose.ui.text.AnnotatedString("+251932176773"))
                                    copiedTelebirr = true
                                    
                                },
                                modifier = Modifier.testTag("copy_telebirr_phone_button")
                            ) {
                                Icon(
                                    imageVector = if (copiedTelebirr) Icons.Default.Check else Icons.Default.ContentCopy,
                                    contentDescription = "Copy Phone Number",
                                    tint = if (copiedTelebirr) EmeraldPrimary else GoldAccent
                                )
                            }
                        }

                        Divider(color = Color(0xFF334155))

                        Text(
                            text = com.example.ui.TranslationManager.get("manual_pay_p1", currentLang),
                            style = MaterialTheme.typography.bodySmall.copy(fontStyle = androidx.compose.ui.text.font.FontStyle.Italic),
                            color = Color.Gray
                        )
                    }
                }

                // Section 3: Telegram Proof & Instantly receive credentials
                Text(
                    text = com.example.ui.TranslationManager.get("step1_send_proof", currentLang),
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Black, letterSpacing = 1.5.sp),
                    color = GoldAccent,
                    modifier = Modifier.align(Alignment.Start).padding(bottom = 8.dp)
                )

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp)
                        .border(
                            width = 1.dp,
                            brush = Brush.linearGradient(listOf(Color.White.copy(alpha = 0.35f), Color.White.copy(alpha = 0.05f))),
                            shape = RoundedCornerShape(12.dp)
                        ),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B).copy(alpha = 0.90f))
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = com.example.ui.TranslationManager.get("step1_p1", currentLang),
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White.copy(alpha = 0.9f),
                            textAlign = TextAlign.Center,
                            lineHeight = 20.sp
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = {
                                try {
                                    val refParam = purchaseReq?.reference ?: ""
                                    val tgUri = if (refParam.isNotEmpty()) "https://t.me/tinatapp_bot?start=$refParam" else "https://t.me/tinatapp_bot"
                                    uriHandler.openUri(tgUri)
                                } catch (e: Exception) {
                                    // Failback
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0088CC)),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("telegram_contact_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Send,
                                contentDescription = "Telegram Link",
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = com.example.ui.TranslationManager.get("btn_telegram_bot", currentLang),
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = Color.White
                            )
                        }
                    }
                }

                // Section 4: Activation portal
                if (packageId == "department") {
                    Text(
                        text = "SELECT YOUR CURRENT STUDY YEAR",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Black, letterSpacing = 1.5.sp),
                        color = GoldAccent,
                        modifier = Modifier.align(Alignment.Start).padding(bottom = 8.dp)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        years.forEach { yr ->
                            val isSelected = selectedYear == yr
                            Surface(
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable {
                                        selectedYear = yr
                                        viewModel.saveDepartmentSetup("", yr)
                                    },
                                shape = RoundedCornerShape(10.dp),
                                color = if (isSelected) EmeraldPrimary.copy(alpha = 0.2f) else Color(0xFF1E293B),
                                border = androidx.compose.foundation.BorderStroke(1.dp, if (isSelected) EmeraldPrimary else Color(0xFF334155))
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

                Text(
                    text = com.example.ui.TranslationManager.get("step2_portal", currentLang),
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Black, letterSpacing = 1.5.sp),
                    color = EmeraldPrimary,
                    modifier = Modifier.align(Alignment.Start).padding(bottom = 6.dp)
                )

                Text(
                    text = com.example.ui.TranslationManager.get("step2_instruction", currentLang),
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.LightGray,
                    modifier = Modifier.align(Alignment.Start).padding(bottom = 12.dp)
                )

                OutlinedTextField(
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it },
                    label = { Text(com.example.ui.TranslationManager.get("phone_number_label", currentLang), color = Color.Gray) },
                    placeholder = { Text(com.example.ui.TranslationManager.get("phone_number_placeholder", currentLang), color = Color.Gray.copy(alpha = 0.5f), style = MaterialTheme.typography.bodySmall) },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = EmeraldPrimary,
                        unfocusedBorderColor = Color(0xFF334155)
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Phone,
                        imeAction = ImeAction.Next
                    ),
                    leadingIcon = { Icon(Icons.Default.Phone, contentDescription = "Phone Number", tint = EmeraldPrimary) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                        .testTag("activation_phone_field")
                )

                OutlinedTextField(
                    value = redeemCode,
                    onValueChange = { redeemCode = it },
                    label = { Text(com.example.ui.TranslationManager.get("redeem_code_label", currentLang), color = Color.Gray) },
                    placeholder = { Text(com.example.ui.TranslationManager.get("redeem_code_placeholder", currentLang), color = Color.Gray.copy(alpha = 0.5f), style = MaterialTheme.typography.bodySmall) },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = EmeraldPrimary,
                        unfocusedBorderColor = Color(0xFF334155)
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    leadingIcon = { Icon(Icons.Default.VpnKey, contentDescription = "Redeem Code", tint = EmeraldPrimary) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp)
                        .testTag("activation_code_field")
                )

                if (activationError != null) {
                    Text(
                        text = activationError ?: "",
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(bottom = 16.dp),
                        textAlign = TextAlign.Center
                    )
                }

                val cleanPhoneDigits = phoneNumber.filter { it.isDigit() }
                val isValidActivation = cleanPhoneDigits.length in 9..15 && redeemCode.trim().isNotEmpty()

                Button(
                    onClick = {
                        if (packageId == "department") {
                            viewModel.saveDepartmentSetup("", selectedYear)
                        }
                        if (isValidActivation && !isActivating) {
                            viewModel.activatePremiumWithRedeemCode(
                                phoneNumber = phoneNumber.trim(),
                                redeemCode = redeemCode.trim(),
                                packageId = packageId
                            )
                        }
                    },
                    enabled = isValidActivation && !isActivating,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = EmeraldPrimary,
                        disabledContainerColor = Color.Gray.copy(alpha = 0.3f),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .testTag("payment_submit_button")
                ) {
                    if (isActivating) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.dp
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Verifying & Activating...",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                    } else {
                        Text(
                            text = com.example.ui.TranslationManager.get("activate_package", currentLang),
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Legal compliance footer
                com.example.ui.components.LegalLinksFooter(
                    currentLang = currentLang,
                    textColor = Color.LightGray.copy(alpha = 0.7f),
                    screenTagPrefix = "payment"
                )

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}
