package com.example.data

object Grade10EnglishNotes {

    fun getGrade10EnglishNotes(): List<SubjectNote> {
        val notesList = mutableListOf<SubjectNote>()
        val subId = "euee_nat_english"
        var idx = 1

        fun addNote(unit: String, title: String, content: String) {
            notesList.add(
                SubjectNote(
                    id = "g10_eng_note_${idx}",
                    subjectId = subId,
                    unit = unit,
                    title = title,
                    content = content,
                    gradeLevel = "Grade 10"
                )
            )
            idx++
        }

        addNote(
            "Unit 1 - Population Growth",
            "Listening, Reading, Grammar, and Writing (Sections 1.1 - 1.5)",
            """
            • Listening & Reading: Focuses on "Population Explosion", demographic challenges, skimming/scanning strategies.
            • Grammar: Simple present, present perfect continuous, degrees of comparison (comparative/superlative), phrasal verbs with 'fall', prefixes.
            • Writing: Paragraph unity, supporting details, cohesive discourse markers, terminal punctuation.
            """.trimIndent()
        )

        addNote(
            "Unit 2 - Travel Behaviors",
            "Travelogues, Narrative Tenses, and Descriptions (Sections 2.1 - 2.5)",
            """
            • Listening & Reading: Travel accounts, ecotourism, historical destinations, functional travel dialogues.
            • Grammar: Past simple, past continuous, narrative tenses, prepositional phrases.
            • Writing: Travel logs, descriptive paragraphs, narrative travel journeys.
            """.trimIndent()
        )

        addNote(
            "Unit 3 - Punctuality",
            "Time Management, Modals of Obligation (Sections 3.1 - 3.6)",
            """
            • Reading & Vocabulary: Articles on discipline, punctuality, synonyms/antonyms.
            • Grammar: Modal verbs of obligation and necessity (must, have to, should, ought to).
            • Writing: Argumentative and reflective essays on time management.
            """.trimIndent()
        )

        addNote(
            "Unit 4 - Biodiversity",
            "Ecosystems, Active vs. Passive Voice (Sections 4.1 - 4.6)",
            """
            • Reading: Flora, fauna, endangered species in Ethiopia, ecological conservation.
            • Grammar: Active and Passive Voice transformation across present, past, and modal forms in scientific reporting.
            • Writing: Wildlife conservation reports and persuasive compositions.
            """.trimIndent()
        )

        addNote(
            "Unit 5 - Traditional Medicine",
            "Indigenous Healing and Reported Speech (Sections 5.1 - 5.6)",
            """
            • Reading: Herbal remedies, indigenous medicinal plants in Ethiopia.
            • Grammar: Reported Speech (Direct to Indirect Speech): backshifting tenses, changing pronouns and time/place adverbs.
            • Writing: Comparative essays (traditional vs modern healthcare) and text summaries.
            """.trimIndent()
        )

        addNote(
            "Unit 6 - Indigenous Conflict Resolution",
            "Customary Justice and Conditional Sentences (Sections 6.1 - 6.6)",
            """
            • Reading: Customary peacemaking systems in Ethiopia (e.g. Gadaa, Shimagillele).
            • Grammar: Conditionals Type 1, 2, and 3 (real, hypothetical, unfulfilled past conditions).
            • Writing: Narrative accounts of conflict resolution and persuasive essays on indigenous justice.
            """.trimIndent()
        )

        addNote(
            "Unit 7 - Charity",
            "Philanthropy and Relative Clauses (Sections 7.1 - 7.6)",
            """
            • Reading: Humanitarian organizations, community solidarity, social welfare.
            • Grammar: Defining and Non-defining Relative Clauses (who, which, that, where, whose).
            • Writing: Formal donation appeal letters and persuasive paragraphs.
            """.trimIndent()
        )

        addNote(
            "Unit 8 - Jobs and Unemployment",
            "Career Guidance, CVs, and Future Tenses (Sections 8.1 - 8.6)",
            """
            • Reading: Youth employment, vocational training, job creation trends.
            • Grammar: Future tenses (will, be going to, future continuous) and modals of ability.
            • Writing: Curriculum Vitae (CV) writing, cover letters, formal job application letters.
            """.trimIndent()
        )

        addNote(
            "Unit 9 - Artificial Intelligence",
            "AI Technology, Causatives, and Analytical Essays (Sections 9.1 - 9.6)",
            """
            • Reading: Artificial intelligence, robotics, machine learning impacts on society.
            • Grammar: Advanced passive constructions and Causative Verbs (have/get something done).
            • Writing: Analytical and argumentative compositions on technology ethics.
            """.trimIndent()
        )

        return notesList
    }
}
