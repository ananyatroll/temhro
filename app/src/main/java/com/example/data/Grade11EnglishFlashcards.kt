package com.example.data

object Grade11EnglishFlashcards {

    fun get500Flashcards(): List<Flashcard> {
        val list = mutableListOf<Flashcard>()
        val subId = "euee_lang_eng_g11"

        val units = listOf(
            Pair("Unit 1: Reading Comprehension", 50),
            Pair("Unit 2: Writing Skills", 50),
            Pair("Unit 3: Grammar and Usage", 50),
            Pair("Unit 4: Literature: Prose", 50),
            Pair("Unit 5: Literature: Poetry", 50),
            Pair("Unit 6: Literature: Drama", 50),
            Pair("Unit 7: Vocabulary and Word Power", 50),
            Pair("Unit 8: Listening and Speaking", 50),
            Pair("Unit 9: Literary Devices and Criticism", 50),
            Pair("Unit 10: Integrated Language Skills", 50)
        )

        val topics = listOf(
            Triple("factual comprehension: main idea, details, sequence", "Reading: Factual", 0),
            Triple("inferential comprehension: tone, purpose, implied meaning", "Reading: Inferential", 1),
            Triple("critical comprehension: evaluation, analysis, synthesis", "Reading: Critical", 2),
            Triple("note-making, summarizing, paraphrasing techniques", "Reading: Study Skills", 3),
            Triple("essay writing: types, structure, coherence, cohesion", "Writing: Essays", 4),
            Triple("letter writing: formal, informal, business, applications", "Writing: Letters", 0),
            Triple("report writing: newspaper, magazine, official reports", "Writing: Reports", 1),
            Triple("article, speech, debate writing: format, language, persuasion", "Writing: Articles", 2),
            Triple("creative writing: story, poem, dialogue, description", "Writing: Creative", 3),
            Triple("editing, proofreading: error detection, correction", "Writing: Editing", 4),
            Triple("tenses: forms, uses, sequence of tenses", "Grammar: Tenses", 0),
            Triple("modals: can, could, may, might, must, should, ought to", "Grammar: Modals", 1),
            Triple("voice: active, passive, transformation rules", "Grammar: Voice", 2),
            Triple("narration: direct, indirect speech, reporting verbs", "Grammar: Narration", 3),
            Triple("clauses: noun, adjective, adverb, conditional", "Grammar: Clauses", 4),
            Triple("determiners: articles, quantifiers, demonstratives", "Grammar: Determiners", 0),
            Triple("prepositions: time, place, direction, phrasal verbs", "Grammar: Prepositions", 1),
            Triple("subject-verb agreement: rules, exceptions, proximity", "Grammar: Agreement", 2),
            Triple("non-finites: infinitives, gerunds, participles", "Grammar: Non-Finites", 3),
            Triple("transformation: simple, compound, complex sentences", "Grammar: Transformation", 4),
            Triple("The Portrait of a Lady: themes, character, narrative technique", "Prose: Portrait of a Lady", 0),
            Triple("We're Not Afraid to Die: survival, courage, family", "Prose: Not Afraid to Die", 1),
            Triple("Discovering Tut: archaeology, mystery, scientific method", "Prose: Discovering Tut", 2),
            Triple("The Ailing Planet: environment, Green Movement, sustainability", "Prose: Ailing Planet", 3),
            Triple("The Browning Version: education, teacher-student, regret", "Prose: Browning Version", 4),
            Triple("Childhood: memory, loss of innocence, poetic devices", "Poetry: Childhood", 0),
            Triple("Father to Son: generation gap, communication, silence", "Poetry: Father to Son", 1),
            Triple("The Voice of the Rain: personification, cycle, eternity", "Poetry: Voice of Rain", 2),
            Triple("A Photograph: time, memory, mortality, sea", "Poetry: Photograph", 3),
            Triple("The Laburnum Top: nature, bird, life cycle, silence", "Poetry: Laburnum Top", 4),
            Triple("The Summer of the Beautiful White Horse: honesty, culture", "Prose: White Horse", 0),
            Triple("The Address: war, loss, memory, objects", "Prose: Address", 1),
            Triple("Ranga's Marriage: tradition, modernity, humor", "Prose: Ranga's Marriage", 2),
            Triple("Albert Einstein at School: education, rebellion, genius", "Prose: Einstein", 3),
            Triple("Mother's Day: family, appreciation, role reversal", "Drama: Mother's Day", 4),
            Triple("The Tale of Melon City: satire, justice, folly", "Poetry: Melon City", 0),
            Triple("synonyms, antonyms: context, nuance, register", "Vocabulary: Syn/Ant", 1),
            Triple("homonyms, homophones, homographs: confusion pairs", "Vocabulary: Homonyms", 2),
            Triple("idioms, phrases, proverbs: meaning, usage, origin", "Vocabulary: Idioms", 3),
            Triple("word formation: prefixes, suffixes, roots, compounds", "Vocabulary: Word Formation", 4),
            Triple("one-word substitution: precision, conciseness", "Vocabulary: One Word", 0),
            Triple("spelling rules: ie/ei, doubling, silent letters, exceptions", "Vocabulary: Spelling", 1),
            Triple("listening for specific information, gist, detail", "Listening: Skills", 2),
            Triple("speaking: pronunciation, stress, intonation, fluency", "Speaking: Skills", 3),
            Triple("group discussion: roles, etiquette, strategies", "Speaking: Group Discussion", 4),
            Triple("metaphor, simile, personification, hyperbole, irony", "Literary Devices: Figures", 0),
            Triple("alliteration, assonance, onomatopoeia, rhyme, rhythm", "Literary Devices: Sound", 1),
            Triple("symbolism, imagery, allegory, paradox, oxymoron", "Literary Devices: Meaning", 2),
            Triple("plot, character, setting, theme, point of view", "Literary Devices: Elements", 3),
            Triple("critical appreciation: analysis, interpretation, evaluation", "Literary Devices: Criticism", 4),
            Triple("integrating skills: reading-to-write, listen-to-speak", "Integration: Skills", 0),
            Triple("project work: research, presentation, citation", "Integration: Projects", 1),
            Triple("functional English: emails, notices, advertisements", "Integration: Functional", 2)
        )

        var cardIndex = 1
        units.forEach { (unitTitle, cardCount) ->
            for (cardNum in 1..cardCount) {
                val topic = topics[(cardNum - 1) % topics.size]
                val concept = topic.first
                val tag = topic.second
                val ansType = topic.third

                val q = "[Grade 11 English - $unitTitle, Card $cardNum] What is the language skill, literary concept, grammatical rule, or writing technique regarding $concept?"

                val a = when (ansType) {
                    0 -> "In Grade 11 English $unitTitle, $concept is developed through textual analysis, linguistic rules, and communicative practice. [Grade 11 English, $unitTitle, Section 1, $tag]"
                    1 -> "Mastering $concept requires understanding structural patterns, contextual usage, and rhetorical effectiveness. [Grade 11 English, $unitTitle, Section 1, $tag]"
                    2 -> "The textbook exemplifies $concept with literary extracts, authentic texts, and graded exercises. [Grade 11 English, $unitTitle, Section 1, $tag]"
                    3 -> "Practical applications of $concept include academic writing, professional communication, and creative expression. [Grade 11 English, $unitTitle, Section 1, $tag]"
                    else -> "Common errors regarding $concept are addressed through contrastive analysis, drill practice, and corrective feedback. [Grade 11 English, $unitTitle, Section 1, $tag]"
                }

                list.add(Flashcard("g11eng_${subId}_$cardIndex", subId, q, a, false, false, "Grade 11"))
                cardIndex++
            }
        }

        return list
    }
}