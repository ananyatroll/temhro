package com.example.data

object Grade10HistoryNotes {

    fun getGrade10HistoryNotes(): List<SubjectNote> {
        val notesList = mutableListOf<SubjectNote>()
        val subId = "euee_soc_history"
        var idx = 1

        fun addNote(unit: String, title: String, content: String) {
            notesList.add(
                SubjectNote(
                    id = "g10_hist_note_${idx}",
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
            "Unit 1 - Development of Capitalism and Nationalism 1815 to 1914",
            "Industrial Revolution, Capitalism, and Nation-State Unification (Sections 1.1 - 1.2)",
            """
            • Industrial Revolution: Factory system replacing domestic production, creation of bourgeoisie and proletariat classes.
            • Unification of Italy (1815–1870): Cavour, Garibaldi, Mazzini uniting fragmented states against Austrian dominance.
            • Unification of Germany (1871): Bismarck's "blood and iron" policies and victories over Denmark, Austria, and France.
            • American Civil War (1861–1865): Union preservation and abolition of slavery.
            """.trimIndent()
        )

        addNote(
            "Unit 2 - Africa & the Colonial Experience (1880s - 1960s)",
            "Scramble for Africa, Berlin Conference, and Colonial Rule (Sections 2.1 - 2.6)",
            """
            • Scramble & Berlin Conference (1884–1885): Partition of Africa without African participation.
            • Colonial Policies: Direct Rule (French assimilation), Indirect Rule (British traditional structures), Company Rule, Settlers Rule.
            • Resistance: Samori Touré, Maji Maji rebellion, Zulu and Boer conflicts.
            """.trimIndent()
        )

        addNote(
            "Unit 3 - Developments in Ethiopia (mid 19thc to 1941)",
            "State Building, Adwa Victory, and Resistance to Fascist Occupation (Sections 3.1 - 3.5)",
            """
            • 19th C Trade & State Building: Unification under Tewodros II, Yohannes IV, Menelik II.
            • Battle of Adwa (1896): Defeat of Italian colonial forces, preserving Ethiopian independence.
            • Fascist Occupation (1935–1941): Italian aggression, Graziani massacre, patriotic guerrilla resistance, 1941 liberation.
            """.trimIndent()
        )

        addNote(
            "Unit 4 - Society and Politics in the Age of World Wars (1914-1945)",
            "World Wars, Russian Revolution, Great Depression, Fascism (Sections 4.1 - 4.6)",
            """
            • World War I (1914–1918) & Versailles Treaty; Russian Revolution (1917) establishing Soviet Union under Lenin.
            • Great Depression (1929) & Rise of Fascism (Mussolini) / Nazism (Hitler).
            • World War II (1939–1945): Axis vs. Allies, defeat of fascism, atomic bomb.
            """.trimIndent()
        )

        addNote(
            "Unit 5 - Global and Regional Developments Since 1945",
            "UN, Cold War, Non-Aligned Movement, and USSR Collapse (Sections 5.1 - 5.6)",
            """
            • United Nations (1945) created for global peace and security.
            • Cold War: US vs. USSR rivalry, nuclear arms race, proxy wars, Non-Aligned Movement (Bandung 1955).
            • USSR Collapse (1991): Gorbachev's Glasnost and Perestroika ending Cold War.
            """.trimIndent()
        )

        addNote(
            "Unit 6 - Ethiopia: Internal Developments and External Influences (1941 to 1991)",
            "Post-Liberation, Student Movement, Derg, and Fall of Derg (Sections 6.1 - 6.4)",
            """
            • Post-Liberation: Haile Selassie I, 1955 Constitution, land tenure inequalities.
            • 1974 Revolution: Student movement, overthrow of emperor, Derg military junta taking power.
            • 1977 Ethio-Somalia War, Eritrean conflict, and Derg overthrow in 1991 by EPRDF.
            """.trimIndent()
        )

        addNote(
            "Unit 7 - Africa Since 1960",
            "Decolonization, Apartheid, and Pan-Africanism (Sections 7.1 - 7.3)",
            """
            • Decolonization: Wave of African independence from 1960s onward.
            • Apartheid: White minority rule in South Africa ended in 1994 with democratic election of Nelson Mandela.
            • Pan-Africanism: OAU established in 1963 in Addis Ababa, evolving into African Union (AU).
            """.trimIndent()
        )

        addNote(
            "Unit 8 - Post-1991 Developments in Ethiopia",
            "Transitional Government, 1995 FDRE Constitution, and GERD (Sections 8.1 - 8.2)",
            """
            • TGE & 1995 Constitution: Federal Democratic Republic of Ethiopia with ethnic-linguistic federal structure.
            • Nile Hydro-politics: Historical treaties, Grand Ethiopian Renaissance Dam (GERD) construction and equitable utilization.
            """.trimIndent()
        )

        addNote(
            "Unit 9 - Indigenous Knowledge and Heritages of Ethiopia",
            "Indigenous Knowledge Systems and Cultural Heritages (Sections 9.1 - 9.2)",
            """
            • Indigenous Systems: Gadaa system, Shimgilina conflict resolution, traditional architecture, agriculture.
            • UNESCO Heritages: Lalibela, Axum, Gondar castles, Lower Omo, Meskel, Timket, Gadaa system.
            """.trimIndent()
        )

        return notesList
    }
}
