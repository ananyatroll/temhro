package com.example.data

import android.content.Context
import android.util.Log
import java.io.BufferedReader
import java.io.InputStreamReader

object FreshmanNotesLoader {

    private const val TAG = "FreshmanNotesLoader"

    val FRESHMAN_SUBJECT_IDS = listOf(
        // Natural Science Stream
        "freshman_nat_english_1",
        "freshman_nat_psychology",
        "freshman_nat_geography",
        "freshman_nat_critical_thinking",
        "freshman_nat_physical_fitness",
        "freshman_nat_maths",
        "freshman_nat_physics",
        "freshman_nat_history",
        "freshman_nat_applied_maths",
        "freshman_nat_english_2",
        "freshman_nat_emerging_tech",
        "freshman_nat_anthropology",
        "freshman_nat_civics",
        "freshman_nat_biology",
        "freshman_nat_chemistry",
        "freshman_nat_computer_programming",

        // Social Science Stream
        "freshman_soc_civics",
        "freshman_soc_anthropology",
        "freshman_soc_english_1",
        "freshman_soc_global_trends",
        "freshman_soc_economics",
        "freshman_soc_emerging_tech",
        "freshman_soc_entrepreneurship",
        "freshman_soc_geography",
        "freshman_soc_history",
        "freshman_soc_inclusiveness",
        "freshman_soc_physical_fitness",
        "freshman_soc_english_2",
        "freshman_soc_psychology",
        "freshman_soc_maths",
        "freshman_soc_critical_thinking"
    )

    fun isFreshmanSubject(subjectId: String): Boolean {
        return subjectId.startsWith("freshman_")
    }

    /**
     * Reads and parses a markdown note file for a given freshman subject ID.
     * Returns a list of SubjectNote objects split cleanly by chapter/unit.
     */
    fun loadNotesForSubject(context: Context, subjectId: String): List<SubjectNote> {
        val assetFileName = "freshman_notes/$subjectId.md"
        val rawMarkdown = try {
            context.assets.open(assetFileName).use { inputStream ->
                BufferedReader(InputStreamReader(inputStream, Charsets.UTF_8)).use { reader ->
                    reader.readText()
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error opening asset $assetFileName: ${e.message}")
            return emptyList()
        }

        if (rawMarkdown.isBlank()) return emptyList()

        return parseMarkdownToChapters(subjectId, rawMarkdown)
    }

    /**
     * Parses markdown text into individual chapter SubjectNotes.
     */
    fun parseMarkdownToChapters(subjectId: String, rawMarkdown: String): List<SubjectNote> {
        val lines = rawMarkdown.lines()
        val notes = mutableListOf<SubjectNote>()

        data class ChapterCandidate(
            val lineIndex: Int,
            val unitName: String,
            val titleName: String
        )

        val candidates = mutableListOf<ChapterCandidate>()

        val chapterRegex = Regex(
            """^(?:#{1,3}\s*)?(?:CHAPTER|UNIT)\s+([0-9]+|[IVXLCDM]+|ONE|TWO|THREE|FOUR|FIVE|SIX|SEVEN|EIGHT|NINE|TEN|ELEVEN|TWELVE)(?::|\s*[-–—]|\s+|$)(.*)$""",
            RegexOption.IGNORE_CASE
        )

        for (i in lines.indices) {
            val line = lines[i]
            val trimmed = line.trim().trimStart('\u000C') // remove form-feed if present

            val match = chapterRegex.find(trimmed)
            if (match != null) {
                // Ignore outlines or references in lines 0..15 if they are short table headers
                if (i > 15 || !trimmed.contains("|")) {
                    val rawNum = match.groupValues[1]
                    val rest = match.groupValues[2].trim().trimStart('-', ':', '–', '—').trim()
                    val normalizedUnit = normalizeUnitNumber(rawNum)
                    val title = if (rest.isNotBlank()) rest else normalizedUnit
                    candidates.add(ChapterCandidate(i, normalizedUnit, title))
                }
            } else if (subjectId.contains("maths", ignoreCase = true) && !subjectId.contains("applied", ignoreCase = true)) {
                // Special check for Math Note social chapter dividers
                if (trimmed.startsWith("Propositional Logic", ignoreCase = true) && i < 100) {
                    candidates.add(ChapterCandidate(i, "Chapter 1", "Propositional Logic and Set Theory"))
                } else if (trimmed.startsWith("The real number system", ignoreCase = true) || 
                           (trimmed.startsWith("Review of relations and functions", ignoreCase = true) && candidates.size == 1)) {
                    candidates.add(ChapterCandidate(i, "Chapter 2", "Relations, Functions and Real Numbers"))
                } else if (trimmed.startsWith("Matrix,Determinant", ignoreCase = true) && i > 1500 && candidates.size <= 2) {
                    candidates.add(ChapterCandidate(i, "Chapter 3", "Matrices, Determinants and Linear Systems"))
                }
            }
        }

        // Deduplicate candidates that are on adjacent lines (e.g. # CHAPTER ONE followed by Chapter One)
        val filteredCandidates = mutableListOf<ChapterCandidate>()
        for (cand in candidates) {
            val last = filteredCandidates.lastOrNull()
            if (last == null || (cand.lineIndex - last.lineIndex > 10 || cand.unitName != last.unitName)) {
                filteredCandidates.add(cand)
            }
        }

        if (filteredCandidates.isEmpty()) {
            // Fallback: entire file as one note
            notes.add(
                SubjectNote(
                    id = "${subjectId}_c1",
                    subjectId = subjectId,
                    unit = "Chapter 1",
                    title = "Course Lecture Notes",
                    content = rawMarkdown.trim(),
                    gradeLevel = "Freshman"
                )
            )
            return notes
        }

        for (idx in filteredCandidates.indices) {
            val current = filteredCandidates[idx]
            val startLine = current.lineIndex
            val endLine = if (idx + 1 < filteredCandidates.size) filteredCandidates[idx + 1].lineIndex else lines.size

            val chapterText = lines.subList(startLine, endLine).joinToString("\n").trim()
            if (chapterText.isNotBlank()) {
                val cleanTitle = current.titleName.replace(Regex("""^Chapter\s*\d+\s*[-:]?\s*""", RegexOption.IGNORE_CASE), "").trim()
                val displayTitle = if (cleanTitle.isNotBlank()) cleanTitle else current.unitName

                notes.add(
                    SubjectNote(
                        id = "${subjectId}_c${idx + 1}",
                        subjectId = subjectId,
                        unit = current.unitName,
                        title = displayTitle,
                        content = chapterText,
                        gradeLevel = "Freshman"
                    )
                )
            }
        }

        return notes
    }

    private fun normalizeUnitNumber(raw: String): String {
        val upper = raw.trim().uppercase()
        val num = when (upper) {
            "ONE", "I", "1" -> "1"
            "TWO", "II", "2" -> "2"
            "THREE", "III", "3" -> "3"
            "FOUR", "IV", "4" -> "4"
            "FIVE", "V", "5" -> "5"
            "SIX", "VI", "6" -> "6"
            "SEVEN", "VII", "7" -> "7"
            "EIGHT", "VIII", "8" -> "8"
            "NINE", "IX", "9" -> "9"
            "TEN", "X", "10" -> "10"
            "ELEVEN", "XI", "11" -> "11"
            "TWELVE", "XII", "12" -> "12"
            else -> upper
        }
        return "Chapter $num"
    }

    /**
     * Loads all freshman notes across all freshman subjects.
     */
    fun getAllFreshmanNotes(context: Context): List<SubjectNote> {
        val all = mutableListOf<SubjectNote>()
        for (subId in FRESHMAN_SUBJECT_IDS) {
            all.addAll(loadNotesForSubject(context, subId))
        }
        return all
    }
}
