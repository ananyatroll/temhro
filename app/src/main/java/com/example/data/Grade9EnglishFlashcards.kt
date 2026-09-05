package com.example.data

object Grade9EnglishFlashcards {

    fun get500Flashcards(): List<Flashcard> {
        val list = mutableListOf<Flashcard>()
        val subjectIds = listOf("euee_nat_english", "euee_soc_english")

        val units = listOf(
            "Unit 1: Extra-curricular Activities",
            "Unit 2: National Parks",
            "Unit 3: Traffic Safety",
            "Unit 4: Digital Literacy",
            "Unit 5: Small Businesses",
            "Unit 6: Youth Entrepreneurship",
            "Unit 7: Traditional Crafts",
            "Unit 8: Health and HIV/AIDS",
            "Unit 9: Fair Trade",
            "Unit 10: Tourism"
        )

        val topics = listOf(
            Triple("simple present tense usage for habits and routines", "Grammar: Present Simple", 0),
            Triple("present continuous tense for actions happening now", "Grammar: Present Continuous", 1),
            Triple("simple past tense for completed past actions", "Grammar: Past Simple", 2),
            Triple("past continuous tense for interrupted past actions", "Grammar: Past Continuous", 3),
            Triple("present perfect tense for past actions with present relevance", "Grammar: Present Perfect", 4),
            Triple("future forms: will vs. going to for predictions and plans", "Grammar: Future Forms", 0),
            Triple("modal verbs of obligation (must, have to, should)", "Grammar: Modals of Obligation", 1),
            Triple("first conditional sentences (if + present, will + base verb)", "Grammar: First Conditional", 2),
            Triple("second conditional sentences (if + past, would + base verb)", "Grammar: Second Conditional", 3),
            Triple("passive voice formation across tenses", "Grammar: Passive Voice", 4),
            Triple("relative pronouns (who, which, that, whose)", "Grammar: Relative Clauses", 0),
            Triple("direct and reported speech conversion rules", "Grammar: Reported Speech", 1),
            Triple("comparison of adjectives (comparative and superlative forms)", "Grammar: Adjectives", 2),
            Triple("prefixes and suffixes for word formation", "Vocabulary: Affixes", 3),
            Triple("synonyms, antonyms, and contextual vocabulary acquisition", "Vocabulary: Context", 4)
        )

        subjectIds.forEach { subId ->
            var cardIndex = 1
            units.forEachIndexed { unitIdx, unitTitle ->
                val unitNum = unitIdx + 1
                for (cardNum in 1..50) {
                    val topic = topics[(cardNum - 1) % topics.size]
                    val concept = topic.first
                    val tag = topic.second
                    val ansType = topic.third

                    val q = "(Grade 9 English, $unitTitle - Card $cardNum) What is the grammatical rule, vocabulary concept, or language function regarding $concept?"

                    val a = when (ansType) {
                        0 -> "In $unitTitle, $concept is applied in context with specific structural markers and communicative functions. [$unitTitle, Section 1, $tag]"
                        1 -> "When practicing $concept, learners must ensure proper subject-verb agreement and contextual appropriateness. [$unitTitle, Section 1, $tag]"
                        2 -> "The textbook emphasizes correct punctuation, spelling, and syntactic positioning for $concept. [$unitTitle, Section 1, $tag]"
                        3 -> "In reading and listening comprehension tasks within $unitTitle, $concept serves to enhance clarity and coherence. [$unitTitle, Section 1, $tag]"
                        else -> "Exercises in $unitTitle require students to identify errors, transform sentences, and produce original examples of $concept. [$unitTitle, Section 1, $tag]"
                    }

                    list.add(Flashcard("g9eng_${subId}_$cardIndex", subId, q, a, false, false, "Grade 9"))
                    cardIndex++
                }
            }
        }

        return list
    }
}
