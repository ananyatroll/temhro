package com.example.data

object Grade12EnglishNotes {

    fun getGrade12EnglishNotes(): List<SubjectNote> {
        val notesList = mutableListOf<SubjectNote>()
        val subId = "euee_lang_eng"
        var idx = 1

        fun addNote(unit: String, title: String, content: String) {
            notesList.add(
                SubjectNote(
                    id = "g12_eng_note_${idx}",
                    subjectId = subId,
                    unit = unit,
                    title = title,
                    content = content,
                    gradeLevel = "Grade 12"
                )
            )
            idx++
        }

        // Unit 1
        addNote(
            "Unit 1 - Sustainable Development",
            "Grammar Skills: Present Perfect and Past Simple / Used to and Would",
            """
            • The Past Simple is used to talk about completed actions at a specific time in the past.
            • The Present Perfect is used to talk about actions that happened at an unspecified time in the past or actions that started in the past and continue to the present.
            • Signal words for Past Simple: yesterday, last week, in 2010, two days ago.
            • Signal words for Present Perfect: already, yet, ever, never, just, for, since.
            • Example (Past Simple): "The government built a new school last year."
            • Example (Present Perfect): "We have lived in this city for ten years."
            • 'Used to' is used for past habits or states that are no longer true. It can be used with both action and stative verbs.
            • 'Would' is used for past habits or repeated actions. It is NOT used with stative verbs (like be, have, know, like).
            • Example: "I used to have long hair" (Correct); "I would have long hair" (Incorrect).
            """.trimIndent()
        )

        // Unit 2
        addNote(
            "Unit 2 - Global Warming",
            "Grammar Skills: The Passive Voice",
            """
            • The Passive Voice is used when the focus is on the action or the receiver of the action rather than the doer.
            • Structure: Form of 'be' + Past Participle (V3).
            • Example (Present Simple Passive): "Trees are planted every year."
            • Example (Past Simple Passive): "The report was written by the committee."
            """.trimIndent()
        )

        // Unit 3
        addNote(
            "Unit 3 - Evidence on Traffic Accident",
            "Grammar Skills: Tense Balance in Conditional Sentences",
            """
            • Zero Conditional: Used for general truths and facts. Structure: If + Present Simple, Present Simple.
            • First Conditional: Used for real and possible future situations. Structure: If + Present Simple, Will + Verb.
            • Second Conditional: Used for hypothetical or unlikely situations. Structure: If + Past Simple, Would + Verb.
            • Third Conditional: Used for past regrets or impossible situations. Structure: If + Past Perfect, Would have + Past Participle.
            • Example (Third Conditional): "If I hadn't been tested positive for COVID-19, I would have been in Tokyo."
            """.trimIndent()
        )

        // Unit 4
        addNote(
            "Unit 4 - Natural Resource Management",
            "Grammar Skills: Adverbial Clauses of Time",
            """
            • Adverbial clauses of time tell us when something happens. They start with conjunctions like when, before, after, while, as, by the time, until, since, and as soon as.
            • 'When' means 'at that moment'. It can take simple past or present.
            • 'Before' means 'before that moment'.
            • 'After' means 'after that moment'. It takes present for future events and past/past perfect for past events.
            • 'While' and 'as' mean 'during that time'. They are usually used with the past continuous to indicate an action in progress.
            • 'By the time' expresses the idea that one event has been completed before another.
            • 'Until' and 'till' express 'up to that time'.
            • 'Since' means 'from that time'. Usually used with present perfect.
            • 'As soon as' means 'immediately afterwards'.
            """.trimIndent()
        )

        // Unit 5
        addNote(
            "Unit 5 - Mechanized Agriculture",
            "Grammar Skills: Gerunds, Infinitives, and Participles",
            """
            • Gerunds: The -ing form of a verb acting as a noun. Used after certain verbs like 'enjoy', 'finish', 'avoid'.
            • Infinitives: The 'to + verb' form. Used after certain verbs like 'want', 'decide', 'hope'.
            • Participles: Present participles (-ing) and Past participles (-ed/V3) used as adjectives or to form tenses.
            • Example (Present Participle as Adjective): "A welcoming cup of tea."
            • Example (Past Participle as Adjective): "The broken plate."
            """.trimIndent()
        )

        // Unit 6
        addNote(
            "Unit 6 - Green Economies",
            "Grammar Skills: Relative Pronouns",
            """
            • WHO: Relates to people (subject).
            • WHOM: Relates to people (object).
            • WHICH: Relates to animals and objects.
            • THAT: Relates to people, animals, and things.
            • WHOSE: Relates to possession for people.
            • WHERE: Refers to places.
            • WHEN: Refers to times.
            • WHY: Refers to reasons.
            • WHAT: Refers to things.
            """.trimIndent()
        )

        // Unit 7
        addNote(
            "Unit 7 - National Pride",
            "Grammar Skills: Direct and Indirect Speech",
            """
            • Direct Speech: Quoting the exact words spoken.
            • Indirect (Reported) Speech: Reporting what someone said without using exact words.
            • Tense Shift: When the reporting verb is in the past (e.g., said), the tense in the reported speech usually shifts back (e.g., present simple to past simple).
            • Example (Direct): "I think Peter won't be on time."
            • Example (Indirect): "She predicted that Peter wouldn't be on time."
            """.trimIndent()
        )

        // Unit 8
        addNote(
            "Unit 8 - Telemedicine",
            "Grammar Skills: Active and Passive Voice Revision",
            """
            • Active Voice: The subject performs the action.
            • Passive Voice: The subject receives the action.
            • Passive is often used when the doer is unknown or unimportant.
            """.trimIndent()
        )

        // Unit 9
        addNote(
            "Unit 9 - Conflict Management",
            "Grammar Skills: Faulty Subject-Verb Agreement",
            """
            • The subject and verb must agree in number (singular or plural).
            • Compound subjects joined by 'and' take a plural verb.
            • Subjects joined by 'or' or 'nor' take a verb that agrees with the nearer subject.
            • Example: "Neither spoken words nor body language are unimportant."
            • Example: "An apple or grapes makes a better choice."
            """.trimIndent()
        )

        return notesList
    }
}