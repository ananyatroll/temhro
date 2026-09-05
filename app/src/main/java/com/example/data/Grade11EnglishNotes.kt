package com.example.data

object Grade11EnglishNotes {

    fun getGrade11EnglishNotes(): List<SubjectNote> {
        val notesList = mutableListOf<SubjectNote>()
        val subId = "euee_lang_eng"
        var idx = 1

        fun addNote(unit: String, title: String, content: String) {
            notesList.add(
                SubjectNote(
                    id = "g11_eng_note_${idx}",
                    subjectId = subId,
                    unit = unit,
                    title = title,
                    content = content,
                    gradeLevel = "Grade 11"
                )
            )
            idx++
        }

        // Unit 1
        addNote(
            "Unit 1 - Environmental Hazards",
            "Listening, Speaking, Reading, Vocabulary, Grammar, and Writing (Sections 1A - 1F)",
            """
            • Listening Skills (Section 1A): Focuses on listening to lecture notes and audio texts about environmental hazards, conceptualizing environmental conservation, acid deposition, greenhouse gas emissions, and poor waste management.
            • Speaking Skills (Section 1B): Practicing discussions on familiar environmental hazards, identifying causes, consequences, and solutions, and participating in panel discussions with stakeholders (public, media, environmentalists, government) using appropriate expressions.
            • Reading Skills (Section 1C): Applying text attack strategies such as skimming for gist, scanning for specific information, identifying explicitly and implicitly stated information, and answering comprehension questions about environmental protection.
            • Vocabulary Development (Section 1D): Word attack skills, inferring the meanings of vocabulary words based on contextual clues, and expanding environmental terminology.
            • Grammar Skills (Section 1E): Differentiating noun categories, types of sentences in English, correct punctuation marks usage, and clauses of concession (e.g., although, even though, despite).
            • Writing Skills (Section 1F): Summarizing and retelling stories, paragraph organization, and logical presentation of ideas in essays and debates.
            """.trimIndent()
        )

        // Unit 2
        addNote(
            "Unit 2 - Tourism",
            "Listening, Speaking, Reading, Vocabulary, Grammar, and Writing (Sections 2A - 2F)",
            """
            • Listening & Speaking (Sections 2A - 2B): Listening to audio accounts of tourist destinations, ecotourism, historical heritages, and practicing conversational expressions for making travel arrangements and recommending attractions.
            • Reading Skills (Section 2C): Reading descriptive and expository texts about tourist attractions, economic benefits of tourism, and sustainable tourism practices in Ethiopia and globally.
            • Vocabulary Development (Section 2D): Expanding travel, geographical, and cultural vocabulary through contextual exercises.
            • Grammar Skills (Section 2E): Reviewing and applying narrative tenses, modal verbs, and complex grammatical structures related to travel descriptions.
            • Writing Skills (Section 2F): Writing descriptive essays, travel itineraries, and reviews of tourist sites.
            """.trimIndent()
        )

        // Unit 3
        addNote(
            "Unit 3 - Good Citizenship",
            "Listening, Speaking, Reading, Vocabulary, Grammar, and Writing (Sections 3A - 3F)",
            """
            • Listening & Speaking (Sections 3A - 3B): Listening to speeches and discussions on civic duties, community service, rights and responsibilities, and practicing oral presentations and debates on citizenship.
            • Reading Skills (Section 3C): Reading informative essays and biographical accounts of prominent figures exemplifying good citizenship.
            • Vocabulary Development (Section 3D): Mastering sociopolitical and civic vocabulary, antonyms, synonyms, and word formation.
            • Grammar Skills (Section 3E): Advanced syntactic structures, relative clauses, and conditional sentences expressing civic obligations.
            • Writing Skills (Section 3F): Composing persuasive essays, argumentative paragraphs, and community project proposals.
            """.trimIndent()
        )

        // Unit 4
        addNote(
            "Unit 4 - Traffic Accidents",
            "Listening, Speaking, Reading, Vocabulary, Grammar, and Writing (Sections 4A - 4F)",
            """
            • Listening & Speaking (Sections 4A - 4B): Listening to safety campaigns, traffic police reports, and discussions on road safety, causes of traffic accidents, and preventive measures.
            • Reading Skills (Section 4C): Comprehending statistical reports, news articles, and safety manuals regarding traffic accident analysis.
            • Vocabulary Development (Section 4D): Technical and everyday vocabulary associated with transportation, driving rules, and accident prevention.
            • Grammar Skills (Section 4E): Modal verbs of necessity, prohibition, and advice (must, should, have to, ought to) applied to road safety regulations.
            • Writing Skills (Section 4F): Writing accident reports, safety awareness leaflets, and argumentative essays on enforcing traffic laws.
            """.trimIndent()
        )

        // Unit 5
        addNote(
            "Unit 5 - Indigenous Technical Knowledge (ITK)",
            "Listening, Speaking, Reading, Vocabulary, Grammar, and Writing (Sections 5A - 5F)",
            """
            • Listening & Speaking (Sections 5A - 5B): Listening to cultural presentations on traditional architecture, agriculture, medicine, and indigenous problem-solving techniques.
            • Reading Skills (Section 5C): Analyzing anthropological and historical texts detailing indigenous technical knowledge systems across diverse Ethiopian communities.
            • Vocabulary Development (Section 5D): Exploring traditional, ethnobotanical, and artisan terminology.
            • Grammar Skills (Section 5E): Passive voice constructions in technical and descriptive writing.
            • Writing Skills (Section 5F): Documenting local indigenous practices, writing descriptive reports, and comparing traditional and modern technologies.
            """.trimIndent()
        )

        // Unit 6
        addNote(
            "Unit 6 - Global Warming",
            "Listening, Speaking, Reading, Vocabulary, Grammar, and Writing (Sections 6A - 6F)",
            """
            • Listening & Speaking (Sections 6A - 6B): Listening to scientific lectures on climate change, carbon footprints, melting ice caps, and discussing mitigation strategies.
            • Reading Skills (Section 6C): Reading scientific articles and reports discussing the causes and global consequences of rising atmospheric temperatures.
            • Vocabulary Development (Section 6D): Environmental science and meteorological terminology.
            • Grammar Skills (Section 6E): Future tenses, conditionals, and expressing cause and effect in scientific discourse.
            • Writing Skills (Section 6F): Writing cause-and-effect essays, persuasive letters to policymakers, and summaries of environmental reports.
            """.trimIndent()
        )

        // Unit 7
        addNote(
            "Unit 7 - Patriotism",
            "Listening, Speaking, Reading, Vocabulary, Grammar, and Writing (Sections 7A - 7F)",
            """
            • Listening & Speaking (Sections 7A - 7B): Listening to historical accounts of national heroes, patriotic poems, and discussions on national unity and loyalty.
            • Reading Skills (Section 7C): Reading historical narratives, biographical sketches, and essays on patriotism and nation-building.
            • Vocabulary Development (Section 7D): Sociopolitical vocabulary associated with loyalty, sacrifice, and community service.
            • Grammar Skills (Section 7E): Reported speech (direct and indirect speech) and narrative integration.
            • Writing Skills (Section 7F): Writing biographical profiles, reflective essays on patriotism, and editorial commentaries.
            """.trimIndent()
        )

        // Unit 8
        addNote(
            "Unit 8 - Efficiency of Health Services",
            "Listening, Speaking, Reading, Vocabulary, Grammar, and Writing (Sections 8A - 8F)",
            """
            • Listening & Speaking (Sections 8A - 8B): Listening to health sector reports, patient-doctor consultations, and discussions on healthcare accessibility and quality.
            • Reading Skills (Section 8C): Reading public health articles, medical reports, and policy briefs on healthcare delivery.
            • Vocabulary Development (Section 8D): Medical and healthcare administrative terminology.
            • Grammar Skills (Section 8E): Complex sentence structures, gerunds, and infinitives in professional contexts.
            • Writing Skills (Section 8F): Writing formal letters of complaint, health service evaluation reports, and proposal summaries.
            """.trimIndent()
        )

        // Unit 9
        addNote(
            "Unit 9 - Indigenous Conflict Resolution",
            "Listening, Speaking, Reading, Vocabulary, Grammar, and Writing (Sections 9A - 9F)",
            """
            • Listening & Speaking (Sections 9A - 9B): Listening to discussions on traditional customary legal systems (e.g., Gadaa, Shimgilina, customary arbitration) and peace-building mechanisms.
            • Reading Skills (Section 9C): Analyzing sociological texts on customary laws, reconciliation processes, and community harmony.
            • Vocabulary Development (Section 9D): Legal, anthropological, and mediation terminology.
            • Grammar Skills (Section 9E): Advanced conditional clauses and subjunctive structures in legal/formal discourse.
            • Writing Skills (Section 9F): Writing case studies of customary arbitration, comparative essays on legal systems, and meeting minutes.
            """.trimIndent()
        )

        // Unit 10
        addNote(
            "Unit 10 - Artificial Intelligence",
            "Listening, Speaking, Reading, Vocabulary, Grammar, and Writing (Sections 10A - 10F)",
            """
            • Listening & Speaking (Sections 10A - 10B): Listening to technological podcasts, AI applications in education and industry, and discussing ethical implications of automation.
            • Reading Skills (Section 10C): Reading tech articles on machine learning, robotics, algorithms, and future workforce impacts.
            • Vocabulary Development (Section 10D): Computer science, digital, and AI-specific terminology.
            • Grammar Skills (Section 10E): Advanced passive voice, causative verbs, and complex relative clauses in technical writing.
            • Writing Skills (Section 10F): Writing argumentative essays on artificial intelligence ethics, research summaries, and technology reviews.
            """.trimIndent()
        )

        return notesList
    }
}