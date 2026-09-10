package com.example.ui.tools.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

sealed interface MarkdownBlock {
    data class Header(val level: Int, val text: String) : MarkdownBlock
    data class Paragraph(val text: String) : MarkdownBlock
    data class BulletItem(val text: String) : MarkdownBlock
    data class NumberedItem(val number: String, val text: String) : MarkdownBlock
    data class Quote(val text: String) : MarkdownBlock
    data class CodeBlock(val language: String, val code: String) : MarkdownBlock
    data class ExamTip(val text: String) : MarkdownBlock
    data class Table(val headers: List<String>, val rows: List<List<String>>) : MarkdownBlock
    object Divider : MarkdownBlock
}

/**
 * Native Jetpack Compose Markdown Renderer for Tamhero AI.
 * Elegantly renders Headings, Bold, Italic, Bullets, Numbered Lists,
 * Quotes, Code Blocks, and Exam Tips without ever exposing raw markdown syntax.
 */
@Composable
fun TamheroMarkdownView(
    text: String,
    isDark: Boolean,
    isUser: Boolean,
    modifier: Modifier = Modifier
) {
    if (isUser) {
        // User messages are concise questions/prompts
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Color.White,
                lineHeight = 20.sp
            ),
            modifier = modifier
        )
        return
    }

    val blocks = remember(text) { parseMarkdownBlocks(text) }

    val baseColor = if (isDark) TextLight else Color(0xFF0F172A)
    val accentColor = EmeraldPrimary
    val quoteBg = if (isDark) Color(0xFF0F172A).copy(alpha = 0.6f) else Color(0xFFF1F5F9)

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        blocks.forEach { block ->
            when (block) {
                is MarkdownBlock.Header -> {
                    val fontSize = when (block.level) {
                        1 -> 16.sp
                        2 -> 15.sp
                        else -> 14.sp
                    }
                    val headerColor = if (block.level <= 2) {
                        if (isDark) EmeraldLight else EmeraldDark
                    } else {
                        baseColor
                    }
                    Text(
                        text = parseMarkdownInline(block.text, headerColor, accentColor, isDark),
                        fontSize = fontSize,
                        fontWeight = FontWeight.Bold,
                        color = headerColor,
                        modifier = Modifier.padding(top = 4.dp, bottom = 2.dp)
                    )
                }

                is MarkdownBlock.Paragraph -> {
                    Text(
                        text = parseMarkdownInline(block.text, baseColor, accentColor, isDark),
                        fontSize = 13.sp,
                        lineHeight = 19.sp,
                        color = baseColor
                    )
                }

                is MarkdownBlock.BulletItem -> {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 2.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(top = 7.dp)
                                .size(5.dp)
                                .background(accentColor, CircleShape)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = parseMarkdownInline(block.text, baseColor, accentColor, isDark),
                            fontSize = 13.sp,
                            lineHeight = 19.sp,
                            color = baseColor,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                is MarkdownBlock.NumberedItem -> {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 2.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Text(
                            text = "${block.number}.",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = accentColor,
                            modifier = Modifier.width(22.dp)
                        )
                        Text(
                            text = parseMarkdownInline(block.text, baseColor, accentColor, isDark),
                            fontSize = 13.sp,
                            lineHeight = 19.sp,
                            color = baseColor,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                is MarkdownBlock.Quote -> {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 2.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .width(3.dp)
                                .fillMaxHeight()
                                .background(accentColor, RoundedCornerShape(2.dp))
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = quoteBg,
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = parseMarkdownInline(block.text, baseColor, accentColor, isDark),
                                fontStyle = FontStyle.Italic,
                                fontSize = 12.5.sp,
                                lineHeight = 18.sp,
                                color = if (isDark) Color(0xFFCBD5E1) else Color(0xFF334155),
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)
                            )
                        }
                    }
                }

                is MarkdownBlock.ExamTip -> {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = if (isDark) Color(0xFF78350F).copy(alpha = 0.25f) else Color(0xFFFEF3C7),
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp,
                            if (isDark) Color(0xFFB45309) else Color(0xFFF59E0B)
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(10.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Icon(
                                imageVector = Icons.Default.Lightbulb,
                                contentDescription = "Exam Tip",
                                tint = if (isDark) Color(0xFFFBBF24) else Color(0xFFD97706),
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "HIGH-YIELD EXAM TIP",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isDark) Color(0xFFFBBF24) else Color(0xFFB45309)
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = parseMarkdownInline(block.text, baseColor, accentColor, isDark),
                                    fontSize = 12.sp,
                                    lineHeight = 17.sp,
                                    color = if (isDark) TextLight else Color(0xFF78350F)
                                )
                            }
                        }
                    }
                }

                is MarkdownBlock.CodeBlock -> {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = if (isDark) Color(0xFF0F172A) else Color(0xFF1E293B),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            if (block.language.isNotBlank()) {
                                Text(
                                    text = block.language.uppercase(),
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = HolographicAqua
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                            }
                            Text(
                                text = block.code,
                                fontFamily = FontFamily.Monospace,
                                fontSize = 12.sp,
                                lineHeight = 16.sp,
                                color = Color(0xFFE2E8F0)
                            )
                        }
                    }
                }

                MarkdownBlock.Divider -> {
                    HorizontalDivider(
                        thickness = 0.8.dp,
                        color = if (isDark) Color(0xFF334155) else Color(0xFFE2E8F0),
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }

                is MarkdownBlock.Table -> {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = if (isDark) Color(0xFF1E293B).copy(alpha = 0.6f) else Color(0xFFF8FAFC),
                        border = BorderStroke(1.dp, if (isDark) Color(0xFF334155) else Color(0xFFE2E8F0)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(rememberScrollState())
                        ) {
                            if (block.headers.isNotEmpty()) {
                                Row(
                                    modifier = Modifier
                                        .background(if (isDark) Color(0xFF0F172A) else Color(0xFFF1F5F9))
                                        .padding(horizontal = 12.dp, vertical = 8.dp)
                                ) {
                                    block.headers.forEach { header ->
                                        Text(
                                            text = parseMarkdownInline(header, accentColor, accentColor, isDark),
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 12.sp,
                                            color = if (isDark) EmeraldLight else EmeraldDark,
                                            modifier = Modifier
                                                .widthIn(min = 90.dp)
                                                .padding(horizontal = 4.dp)
                                        )
                                    }
                                }
                                HorizontalDivider(thickness = 1.dp, color = if (isDark) Color(0xFF334155) else Color(0xFFE2E8F0))
                            }
                            block.rows.forEachIndexed { rIdx, row ->
                                val rowBg = if (rIdx % 2 == 1) {
                                    if (isDark) Color(0xFF0F172A).copy(alpha = 0.3f) else Color(0xFFF8FAFC)
                                } else Color.Transparent
                                Row(
                                    modifier = Modifier
                                        .background(rowBg)
                                        .padding(horizontal = 12.dp, vertical = 6.dp)
                                ) {
                                    row.forEach { cell ->
                                        Text(
                                            text = parseMarkdownInline(cell, baseColor, accentColor, isDark),
                                            fontSize = 12.sp,
                                            lineHeight = 16.sp,
                                            color = baseColor,
                                            modifier = Modifier
                                                .widthIn(min = 90.dp)
                                                .padding(horizontal = 4.dp)
                                        )
                                    }
                                }
                                if (rIdx < block.rows.size - 1) {
                                    HorizontalDivider(thickness = 0.5.dp, color = if (isDark) Color(0xFF334155).copy(alpha = 0.5f) else Color(0xFFE2E8F0))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * Parses raw text lines into typed Markdown blocks.
 */
fun parseMarkdownBlocks(rawText: String): List<MarkdownBlock> {
    val lines = rawText.lines()
    val blocks = mutableListOf<MarkdownBlock>()
    var i = 0
    val total = lines.size

    while (i < total) {
        val line = lines[i]
        val trimmed = line.trim()

        if (trimmed.isEmpty()) {
            i++
            continue
        }

        // 1. Code Block ```
        if (trimmed.startsWith("```")) {
            val lang = trimmed.removePrefix("```").trim()
            val codeLines = mutableListOf<String>()
            i++
            while (i < total && !lines[i].trim().startsWith("```")) {
                codeLines.add(lines[i])
                i++
            }
            if (i < total) i++ // skip closing ```
            blocks.add(MarkdownBlock.CodeBlock(lang, codeLines.joinToString("\n")))
            continue
        }

        // 2. Horizontal Rule
        if (trimmed == "---" || trimmed == "***" || trimmed == "___") {
            blocks.add(MarkdownBlock.Divider)
            i++
            continue
        }

        // 3. Exam Tip Callout
        if (trimmed.startsWith("EXAM TIP:", ignoreCase = true) || trimmed.startsWith("TIP:", ignoreCase = true)) {
            val content = trimmed.substringAfter(":").trim()
            blocks.add(MarkdownBlock.ExamTip(content))
            i++
            continue
        }

        // 4. Header: e.g. "# ", "## ", "### "
        if (trimmed.startsWith("#")) {
            val level = trimmed.takeWhile { it == '#' }.length
            val text = trimmed.drop(level).trim()
            if (text.isNotBlank()) {
                blocks.add(MarkdownBlock.Header(level, text))
                i++
                continue
            }
        }

        // 5. Quote: e.g. "> "
        if (trimmed.startsWith(">")) {
            val quoteText = trimmed.removePrefix(">").trim()
            blocks.add(MarkdownBlock.Quote(quoteText))
            i++
            continue
        }

        // 6. Bullet Item: "- ", "* ", "+ ", "• "
        val isBullet = (trimmed.startsWith("- ") || trimmed.startsWith("* ") || trimmed.startsWith("+ ") || trimmed.startsWith("• "))
        if (isBullet) {
            val bulletText = trimmed.substring(2).trim()
            blocks.add(MarkdownBlock.BulletItem(bulletText))
            i++
            continue
        }

        // 7. Numbered Item: "1. " or "2) "
        val numberMatch = Regex("""^(\d+)[\.\)]\s+(.*)$""").find(trimmed)
        if (numberMatch != null) {
            val number = numberMatch.groupValues[1]
            val itemText = numberMatch.groupValues[2]
            blocks.add(MarkdownBlock.NumberedItem(number, itemText))
            i++
            continue
        }

        // 8. Markdown Table: starts with '|' and contains '|'
        if (trimmed.startsWith("|") && trimmed.contains("|")) {
            val tableLines = mutableListOf<String>()
            while (i < total && lines[i].trim().startsWith("|") && lines[i].trim().contains("|")) {
                tableLines.add(lines[i].trim())
                i++
            }
            if (tableLines.isNotEmpty()) {
                val headers = tableLines[0].split("|").map { it.trim() }.filter { it.isNotEmpty() }
                val rows = mutableListOf<List<String>>()
                val dataStartIndex = if (tableLines.size > 1 && tableLines[1].contains("---")) 2 else 1
                for (r in dataStartIndex until tableLines.size) {
                    val cells = tableLines[r].split("|").map { it.trim() }.filter { it.isNotEmpty() }
                    if (cells.isNotEmpty() && !tableLines[r].contains("---")) {
                        rows.add(cells)
                    }
                }
                blocks.add(MarkdownBlock.Table(headers, rows))
                continue
            }
        }

        // 9. Normal Paragraph
        blocks.add(MarkdownBlock.Paragraph(trimmed))
        i++
    }

    return blocks
}

/**
 * Parses inline markdown (bold, italic, inline code, clean tokens) into an [AnnotatedString],
 * strictly ensuring raw markdown artifacts are never presented to the user.
 */
fun parseMarkdownInline(
    text: String,
    baseColor: Color,
    accentColor: Color,
    isDark: Boolean
): AnnotatedString {
    return buildAnnotatedString {
        var i = 0
        val len = text.length

        while (i < len) {
            // Bold Italic: ***text***
            if (i + 2 < len && text[i] == '*' && text[i + 1] == '*' && text[i + 2] == '*') {
                val end = text.indexOf("***", i + 3)
                if (end != -1) {
                    withStyle(SpanStyle(fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic, color = baseColor)) {
                        append(text.substring(i + 3, end))
                    }
                    i = end + 3
                    continue
                }
            }

            // Bold: **text**
            if (i + 1 < len && text[i] == '*' && text[i + 1] == '*') {
                val end = text.indexOf("**", i + 2)
                if (end != -1) {
                    withStyle(SpanStyle(fontWeight = FontWeight.Bold, color = baseColor)) {
                        append(text.substring(i + 2, end))
                    }
                    i = end + 2
                    continue
                }
            }

            // Bold: __text__
            if (i + 1 < len && text[i] == '_' && text[i + 1] == '_') {
                val end = text.indexOf("__", i + 2)
                if (end != -1) {
                    withStyle(SpanStyle(fontWeight = FontWeight.Bold, color = baseColor)) {
                        append(text.substring(i + 2, end))
                    }
                    i = end + 2
                    continue
                }
            }

            // Inline Code: `code`
            if (text[i] == '`') {
                val end = text.indexOf('`', i + 1)
                if (end != -1) {
                    withStyle(
                        SpanStyle(
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.SemiBold,
                            color = accentColor,
                            background = if (isDark) Color(0xFF334155) else Color(0xFFE2E8F0)
                        )
                    ) {
                        append(" ${text.substring(i + 1, end)} ")
                    }
                    i = end + 1
                    continue
                }
            }

            // Italic: *text*
            if (text[i] == '*' && (i == 0 || text[i - 1] != '*') && (i + 1 == len || text[i + 1] != '*')) {
                val end = text.indexOf('*', i + 1)
                if (end != -1 && (end + 1 == len || text[end + 1] != '*')) {
                    withStyle(SpanStyle(fontStyle = FontStyle.Italic, color = baseColor)) {
                        append(text.substring(i + 1, end))
                    }
                    i = end + 1
                    continue
                }
            }

            // Italic: _text_
            if (text[i] == '_' && (i == 0 || text[i - 1] != '_') && (i + 1 == len || text[i + 1] != '_')) {
                val end = text.indexOf('_', i + 1)
                if (end != -1 && (end + 1 == len || text[end + 1] != '_')) {
                    withStyle(SpanStyle(fontStyle = FontStyle.Italic, color = baseColor)) {
                        append(text.substring(i + 1, end))
                    }
                    i = end + 1
                    continue
                }
            }

            // Suppress rogue lone markdown punctuation that would confuse the user
            if (text[i] == '*' || text[i] == '_' || text[i] == '`') {
                i++
                continue
            }

            append(text[i])
            i++
        }
    }
}
