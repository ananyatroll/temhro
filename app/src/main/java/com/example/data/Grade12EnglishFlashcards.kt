package com.example.data

object Grade12EnglishFlashcards {

    fun get500Flashcards(): List<Flashcard> {
        val list = mutableListOf<Flashcard>()
        val subId = "euee_nat_english"

        val units = listOf(
            Pair("Unit 1: Reading Comprehension", 50),
            Pair("Unit 2: Writing Skills", 50),
            Pair("Unit 3: Grammar and Usage", 50),
            Pair("Unit 4: Literature: Prose (Flamingo)", 50),
            Pair("Unit 5: Literature: Poetry (Flamingo)", 50),
            Pair("Unit 6: Literature: Prose (Vistas)", 50),
            Pair("Unit 7: Literature: Poetry (Vistas)", 50),
            Pair("Unit 8: Vocabulary and Word Power", 50),
            Pair("Unit 9: Literary Devices and Criticism", 50),
            Pair("Unit 10: Integrated Language Skills", 50)
        )

        val topics = listOf(
            Triple("factual comprehension: main idea, supporting details, sequence", "Reading: Factual", 0),
            Triple("inferential: tone, attitude, implied meaning, author's purpose", "Reading: Inferential", 1),
            Triple("critical: evaluation, argument analysis, bias detection", "Reading: Critical", 2),
            Triple("note-making: formats, abbreviations, summarizing, mind maps", "Reading: Note Making", 3),
            Triple("essay: argumentative, descriptive, narrative, reflective", "Writing: Essays", 4),
            Triple("letter: formal (business, official, editor), informal", "Writing: Letters", 0),
            Triple("report: newspaper, magazine, factual, analytical", "Writing: Reports", 1),
            Triple("article, speech, debate: structure, rhetoric, persuasion", "Writing: Articles", 2),
            Triple("creative: story, poem, dialogue, travelogue, memoir", "Writing: Creative", 3),
            Triple("editing: error detection, omission, transformation", "Writing: Editing", 4),
            Triple("tenses: perfect, continuous, perfect continuous, passive", "Grammar: Tenses", 0),
            Triple("modals: deduction, obligation, permission, ability", "Grammar: Modals", 1),
            Triple("voice: transformation, impersonal passive, get-passive", "Grammar: Voice", 2),
            Triple("narration: rules, exceptions, mixed reporting", "Grammar: Narration", 3),
            Triple("clauses: noun, relative, adverb, conditional, reduced", "Grammar: Clauses", 4),
            Triple("determiners: quantifiers, distributives, predeterminers", "Grammar: Determiners", 0),
            Triple("prepositions: complex, phrasal verbs, collocations", "Grammar: Prepositions", 1),
            Triple("subject-verb agreement: advanced rules, collective nouns", "Grammar: Agreement", 2),
            Triple("non-finites: infinitives, gerunds, participles, absolute", "Grammar: Non-Finites", 3),
            Triple("sentence transformation: simple-compound-complex", "Grammar: Transformation", 4),
            Triple("The Last Lesson: language, identity, Franco-Prussian war", "Flamingo Prose: Last Lesson", 0),
            Triple("Lost Spring: child labor, poverty, Saheb, Mukesh", "Flamingo Prose: Lost Spring", 1),
            Triple("Deep Water: fear, conquest, Roosevelt, swimming", "Flamingo Prose: Deep Water", 2),
            Triple("The Rattrap: kindness, redemption, peddler, ironmaster", "Flamingo Prose: Rattrap", 3),
            Triple("Indigo: Gandhi, Champaran, sharecroppers, civil disobedience", "Flamingo Prose: Indigo", 4),
            Triple("Poets and Pancakes: Gemini Studios, Asokamitran, humor", "Flamingo Prose: Poets Pancakes", 0),
            Triple("The Interview: Christopher Silvester, celebrity culture", "Flamingo Prose: Interview", 1),
            Triple("Going Places: A.R. Barton, adolescence, dreams, reality", "Flamingo Prose: Going Places", 2),
            Triple("My Mother at Sixty-Six: aging, mortality, parting", "Flamingo Poetry: Mother 66", 3),
            Triple("An Elementary School Classroom: slum, Shakespeare, maps", "Flamingo Poetry: Elementary", 4),
            Triple("Keeping Quiet: Neruda, silence, introspection, peace", "Flamingo Poetry: Keeping Quiet", 0),
            Triple("A Thing of Beauty: Keats, eternal joy, nature, grandeur", "Flamingo Poetry: Thing Beauty", 1),
            Triple("A Roadside Stand: Frost, rural poverty, city indifference", "Flamingo Poetry: Roadside", 2),
            Triple("The Third Level: Finney, time travel, escapism, Grand Central", "Vistas Prose: Third Level", 3),
            Triple("The Tiger King: Kalki, satire, astrology, irony", "Vistas Prose: Tiger King", 4),
            Triple("Journey to the End of the Earth: Antarctica, climate, students", "Vistas Prose: End of Earth", 0),
            Triple("The Enemy: Pearl Buck, war, humanity, doctor's dilemma", "Vistas Prose: Enemy", 1),
            Triple("On the Face of It: Susan Hill, disability, friendship, garden", "Vistas Prose: Face of It", 2),
            Triple("Memories of Childhood: Zitkala-Sa, Bama, oppression, resistance", "Vistas Prose: Memories", 3),
            Triple("The Cutting of My Long Hair: Carlisle, cultural erasure", "Vistas Prose: Long Hair", 4),
            Triple("We Too Are Human Beings: Bama, untouchability, dignity", "Vistas Prose: Human Beings", 0),
            Triple("synonyms/antonyms: nuance, register, collocation, connotation", "Vocabulary: Syn/Ant", 1),
            Triple("homonyms, homophones, polysemy, false friends", "Vocabulary: Homonyms", 2),
            Triple("idioms, phrasal verbs, proverbs, clichés, euphemisms", "Vocabulary: Idioms", 3),
            Triple("word formation: affixes, conversion, compounding, blending", "Vocabulary: Word Formation", 4),
            Triple("one-word substitution, foreign words, archaisms", "Vocabulary: Specialized", 0),
            Triple("spelling: rules, exceptions, IE/EI, prefixes, suffixes", "Vocabulary: Spelling", 1),
            Triple("metaphor, simile, personification, metonymy, synecdoche", "Devices: Figures", 2),
            Triple("alliteration, assonance, consonance, onomatopoeia, rhyme", "Devices: Sound", 3),
            Triple("irony, paradox, oxymoron, hyperbole, understatement", "Devices: Irony", 4),
            Triple("symbolism, imagery, allegory, motif, archetype", "Devices: Symbolism", 0),
            Triple("narrative: plot, character, setting, POV, theme, structure", "Devices: Narrative", 1),
            Triple("critical appreciation: analysis, interpretation, evaluation", "Devices: Criticism", 2),
            Triple("integrated tasks: reading-to-write, listen-to-speak, project", "Integration: Tasks", 3),
            Triple("functional: CV, email, notice, advertisement, minutes", "Integration: Functional", 4)
        )

        var cardIndex = 1
        units.forEach { (unitTitle, cardCount) ->
            for (cardNum in 1..cardCount) {
                val topic = topics[(cardNum - 1) % topics.size]
                val concept = topic.first
                val tag = topic.second
                val ansType = topic.third

                val q = "[Grade 12 English - $unitTitle, Card $cardNum] What is the language skill, literary concept, grammatical rule, or writing technique regarding $concept?"

                val a = when (ansType) {
                    0 -> "In Grade 12 English $unitTitle, $concept is developed through textual analysis, linguistic rules, and communicative practice. [Grade 12 English, $unitTitle, Section 1, $tag]"
                    1 -> "Mastering $concept requires understanding structural patterns, contextual usage, and rhetorical effectiveness. [Grade 12 English, $unitTitle, Section 1, $tag]"
                    2 -> "The textbook exemplifies $concept with literary extracts, authentic texts, and graded exercises. [Grade 12 English, $unitTitle, Section 1, $tag]"
                    3 -> "Practical applications of $concept include academic writing, professional communication, and creative expression. [Grade 12 English, $unitTitle, Section 1, $tag]"
                    else -> "Common errors regarding $concept are addressed through contrastive analysis, drill practice, and corrective feedback. [Grade 12 English, $unitTitle, Section 1, $tag]"
                }

                list.add(Flashcard("g12eng_${subId}_$cardIndex", subId, q, a, false, false, "Grade 12"))
                cardIndex++
            }
        }

        return list + list.map { it.copy(id = it.id + "_soc", subjectId = "euee_soc_english") }
    }
}