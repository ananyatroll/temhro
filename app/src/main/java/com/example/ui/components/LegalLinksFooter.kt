package com.example.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.TranslationManager

const val URL_PRIVACY_POLICY = "https://tinatapprivacy.netlify.app"
const val URL_TERMS_OF_SERVICE = "https://tinatappterms.vercel.app"
const val URL_EULA = "https://tinatappeula.vercel.app"

/**
 * Reusable legal compliance links footer rendering:
 * 1. Privacy Policy (https://tinatapprivacy.netlify.app)
 * 2. Terms & Services (https://tinatappterms.netlify.app)
 * 3. End User License Agreement / EULA (https://tinatappeula.netlify.app)
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LegalLinksFooter(
    currentLang: String,
    modifier: Modifier = Modifier,
    textColor: Color = Color.Gray,
    screenTagPrefix: String = "general"
) {
    val uriHandler = LocalUriHandler.current

    fun openUrl(url: String) {
        try {
            uriHandler.openUri(url)
        } catch (_: Exception) {}
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Option to display nicely arranged legal items
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            maxItemsInEachRow = 3
        ) {
            LegalLinkItem(
                title = TranslationManager.get("privacy_policy", currentLang),
                icon = Icons.Default.PrivacyTip,
                textColor = textColor,
                testTag = "${screenTagPrefix}_privacy_policy_link",
                onClick = { openUrl(URL_PRIVACY_POLICY) }
            )

            Text(
                text = "•",
                color = textColor.copy(alpha = 0.6f),
                fontSize = 12.sp,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(horizontal = 6.dp)
            )

            LegalLinkItem(
                title = TranslationManager.get("terms_of_service", currentLang),
                icon = Icons.Default.Gavel,
                textColor = textColor,
                testTag = "${screenTagPrefix}_terms_link",
                onClick = { openUrl(URL_TERMS_OF_SERVICE) }
            )

            Text(
                text = "•",
                color = textColor.copy(alpha = 0.6f),
                fontSize = 12.sp,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(horizontal = 6.dp)
            )

            LegalLinkItem(
                title = TranslationManager.get("eula", currentLang),
                icon = Icons.Default.Article,
                textColor = textColor,
                testTag = "${screenTagPrefix}_eula_link",
                onClick = { openUrl(URL_EULA) }
            )
        }

        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = "© 2026 ተምህሮ / Temhiro Education Hub. All rights reserved.",
            style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp),
            color = textColor.copy(alpha = 0.5f),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun LegalLinkItem(
    title: String,
    icon: ImageVector,
    textColor: Color,
    testTag: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(horizontal = 4.dp, vertical = 2.dp)
            .testTag(testTag),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = textColor,
            modifier = Modifier.size(14.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.bodySmall.copy(
                textDecoration = TextDecoration.Underline,
                fontWeight = FontWeight.SemiBold,
                fontSize = 11.sp
            ),
            color = textColor
        )
    }
}
