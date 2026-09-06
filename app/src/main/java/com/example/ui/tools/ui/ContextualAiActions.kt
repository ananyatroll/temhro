package com.example.ui.tools.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material.icons.filled.TipsAndUpdates
import androidx.compose.material3.Icon
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.StudyViewModel
import com.example.ui.tools.ai.LearningContext
import com.example.ui.theme.EmeraldLight
import com.example.ui.theme.EmeraldPrimary

/**
 * A single contextual AI action button. The [prompt] is routed through
 * [StudyViewModel.openStudentTools] into the Ask Tamhero sheet, where the
 * offline Tamhero Smart Engine answers it using the attached [LearningContext].
 * Prompts are designed to match the engine's known intents
 * (explain / example / simpler / summarize / flashcards / test me / exam tip / hint).
 */
data class ContextualAiAction(
    val label: String,
    val prompt: String,
    val icon: ImageVector = Icons.Default.AutoAwesome
)

/**
 * Horizontal, wrapping row of contextual AI action chips.
 * Drop this anywhere a student is viewing content (note, flashcard, practice question)
 * and pass the matching [LearningContext] so the engine always answers about
 * exactly what is on screen.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ContextualAiActions(
    viewModel: StudyViewModel,
    learningContext: LearningContext,
    actions: List<ContextualAiAction>,
    modifier: Modifier = Modifier
) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        actions.forEach { action ->
            SuggestionChip(
                onClick = {
                    viewModel.openStudentTools(
                        tab = "ask",
                        prompt = action.prompt,
                        context = learningContext
                    )
                },
                label = {
                    Text(
                        text = action.label,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                icon = {
                    Icon(
                        imageVector = action.icon,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp)
                    )
                },
                colors = SuggestionChipDefaults.suggestionChipColors(
                    containerColor = EmeraldPrimary.copy(alpha = 0.15f),
                    labelColor = EmeraldLight,
                    iconContentColor = EmeraldLight
                )
            )
        }
    }
}

/**
 * Ready-made action sets per content type. Prompt wording intentionally hits the
 * exact intents recognized by TamheroSmartEngine, so every tap is a guaranteed hit.
 */
object ContextualAiActionSets {

    fun notes(noteTitle: String): List<ContextualAiAction> = listOf(
        ContextualAiAction(
            label = "Explain this",
            prompt = "Explain this lesson on '$noteTitle' step by step",
            icon = Icons.Default.SmartToy
        ),
        ContextualAiAction(
            label = "Summarize",
            prompt = "Summarize this lesson note",
            icon = Icons.Default.AutoAwesome
        ),
        ContextualAiAction(
            label = "Make flashcards",
            prompt = "Turn this into flashcards",
            icon = Icons.Default.AutoAwesome
        ),
        ContextualAiAction(
            label = "Test me",
            prompt = "Test me with practice questions on this topic",
            icon = Icons.Default.Quiz
        ),
        ContextualAiAction(
            label = "Exam tip",
            prompt = "What are the exam tips for this topic?",
            icon = Icons.Default.TipsAndUpdates
        )
    )

    fun flashcard(): List<ContextualAiAction> = listOf(
        ContextualAiAction(
            label = "Explain this card",
            prompt = "Explain this flashcard concept in detail",
            icon = Icons.Default.SmartToy
        ),
        ContextualAiAction(
            label = "Give an example",
            prompt = "Give me an example for this flashcard",
            icon = Icons.Default.Lightbulb
        ),
        ContextualAiAction(
            label = "Memory trick",
            prompt = "Give me a memory trick to remember this flashcard",
            icon = Icons.Default.TipsAndUpdates
        ),
        ContextualAiAction(
            label = "Exam tip",
            prompt = "What are the exam tips for this concept?",
            icon = Icons.Default.AutoAwesome
        )
    )

    fun practice(): List<ContextualAiAction> = listOf(
        ContextualAiAction(
            label = "Need a hint",
            prompt = "Give me a hint for this question",
            icon = Icons.Default.Lightbulb
        ),
        ContextualAiAction(
            label = "Explain the answer",
            prompt = "Why is the correct answer correct? Explain the steps",
            icon = Icons.Default.SmartToy
        )
    )

    fun scannedPage(): List<ContextualAiAction> = listOf(
        ContextualAiAction(
            label = "Explain this page",
            prompt = "Explain this scanned page step by step",
            icon = Icons.Default.SmartToy
        ),
        ContextualAiAction(
            label = "Key takeaways",
            prompt = "Summarize the key takeaways of this scanned page",
            icon = Icons.Default.AutoAwesome
        ),
        ContextualAiAction(
            label = "Exam tip",
            prompt = "What are the exam tips for this page?",
            icon = Icons.Default.TipsAndUpdates
        )
    )
}
