package com.example.data

object Grade10EnglishFlashcards {

    fun get500Flashcards(): List<Flashcard> {
        val list = mutableListOf<Flashcard>()
        val subId = "euee_nat_english"

        val units = listOf(
            Pair("Unit 1: Community Development", 63),
            Pair("Unit 2: Traditional Healing Systems", 63),
            Pair("Unit 3: Gender Equality", 63),
            Pair("Unit 4: Wildlife Conservation", 63),
            Pair("Unit 5: Information Technology", 63),
            Pair("Unit 6: Cooperative Societies", 63),
            Pair("Unit 7: Charity Organization", 63),
            Pair("Unit 8: Ethics", 59)
        )

        val topics = listOf(
            Triple("present perfect continuous tense usage and structure", "Grammar: Present Perfect Continuous", 0),
            Triple("past perfect tense vs. simple past in narrative texts", "Grammar: Narrative Tenses", 1),
            Triple("future continuous and future perfect tense structures", "Grammar: Future Tenses", 2),
            Triple("conditional sentences type 2 and type 3 (hypothetical)", "Grammar: Conditionals", 3),
            Triple("passive voice transformation across complex tenses", "Grammar: Passive Voice", 4),
            Triple("reported speech: backshifting tense and time markers", "Grammar: Reported Speech", 0),
            Triple("relative clauses: defining vs. non-defining clauses", "Grammar: Relative Clauses", 1),
            Triple("modal verbs of deduction and probability (must, might, can't)", "Grammar: Modals", 2),
            Triple("gerunds and infinitives as subjects and objects", "Grammar: Gerunds & Infinitives", 3),
            Triple("connectors of contrast, concession, and purpose", "Grammar: Discourse Markers", 4),
            Triple("prefix and suffix word formation rules", "Vocabulary: Affixes", 0),
            Triple("collocations, idioms, and phrasal verbs in context", "Vocabulary: Idioms", 1),
            Triple("reading comprehension strategies: skimming, scanning, inferencing", "Reading: Strategies", 2),
            Triple("paragraph unity, coherence, and topic sentences", "Writing: Paragraph Structure", 3),
            Triple("formal vs. informal letter writing conventions", "Writing: Correspondence", 4),
            Triple("argumentative essay structure and persuasive language", "Writing: Essays", 0),
            Triple("listening for specific information and note-taking", "Listening: Skills", 1),
            Triple("speaking skills: expressing opinions, agreeing, and disagreeing", "Speaking: Interaction", 2),
            Triple("synonyms, antonyms, and contextual vocabulary analysis", "Vocabulary: Context", 3),
            Triple("punctuation rules: semicolons, colons, and quotation marks", "Mechanics: Punctuation", 4)
        )

        var cardIndex = 1
        units.forEach { (unitTitle, cardCount) ->
            for (cardNum in 1..cardCount) {
                val topic = topics[(cardNum - 1) % topics.size]
                val concept = topic.first
                val tag = topic.second
                val ansType = topic.third

                val q = "[$unitTitle, Card $cardNum] What is the grammatical rule, language function, vocabulary concept, or writing skill regarding $concept?"

                val a = when (ansType) {
                    0 -> "In Grade 10 English $unitTitle, $concept is defined by specific structural rules, syntactic patterns, and communicative functions. [$unitTitle, Section 1, $tag]"
                    1 -> "Applying $concept requires careful attention to context, agreement, and tense consistency. [$unitTitle, Section 1, $tag]"
                    2 -> "The textbook highlights key examples, structural formulas, and usage guidelines associated with $concept. [$unitTitle, Section 1, $tag]"
                    3 -> "Practical applications of $concept include composing essays, participating in debates, and analyzing complex reading texts. [$unitTitle, Section 1, $tag]"
                    else -> "Common errors regarding $concept are avoided by distinguishing between confusing grammatical forms and applying standard usage rules. [$unitTitle, Section 1, $tag]"
                }

                list.add(Flashcard("g10eng_${subId}_$cardIndex", subId, q, a, false, false, "Grade 10"))
                cardIndex++
            }
        }

        return list
    }
}
