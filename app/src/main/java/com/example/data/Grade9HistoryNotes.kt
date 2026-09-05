package com.example.data

object Grade9HistoryNotes {

    fun getGrade9HistoryNotes(): List<SubjectNote> {
        val notesList = mutableListOf<SubjectNote>()
        val subId = "euee_soc_history"
        var idx = 1

        fun addNote(unit: String, title: String, content: String) {
            notesList.add(
                SubjectNote(
                    id = "g9_hist_note_${idx}",
                    subjectId = subId,
                    unit = unit,
                    title = title,
                    content = content,
                    gradeLevel = "Grade 9"
                )
            )
            idx++
        }

        // Unit 1
        addNote(
            "Unit 1 - The Discipline of History and Human Evolution",
            "The Discipline of History and Human Evolution (Sections 1.1 - 1.6)",
            """
            • Definition and Nature of History: History is the systematic study of the human past based on critical evaluation of historical evidence and sources, tracing societal developments from prehistoric times to the present (Chapter 1, Sections 1.1–1.2, Pages 1–5).
            • Historical Sources: Primary sources (firsthand accounts, artifacts, official documents, coins, oral traditions) vs. secondary sources (interpretations, history books, articles) (Chapter 1, Section 1.2, Pages 6–10).
            • Human Evolution: Scientific theories regarding human origins trace hominid development in Africa (Great Rift Valley), highlighting Australopithecus, Homo habilis, Homo erectus, and Homo sapiens (Chapter 1, Section 1.3, Pages 11–18).
            • The Stone Age: Paleolithic (Old Stone Age), Mesolithic (Middle Stone Age), and Neolithic (New Stone Age / Agricultural Revolution involving domestication of plants and animals) (Chapter 1, Section 1.4, Pages 19–26).
            • Emergence of States: Theories on origin of state (force theory, divine right, social contract, economic/surplus production) and features of early states (centralized authority, social stratification, taxation, writing systems) (Chapter 1, Section 1.5, Pages 27–35).
            """.trimIndent()
        )

        // Unit 2
        addNote(
            "Unit 2 - Ancient Civilizations",
            "Ancient Civilizations of the World and Africa (Sections 2.1 - 2.5)",
            """
            • Ancient Egypt: Nile River valley, pharaonic rule, monumental architecture (pyramids, temples), hieroglyphics, mathematics, medicine, engineering (Chapter 2, Section 2.1, Pages 36–42).
            • Mesopotamia: Tigris and Euphrates rivers, Sumerian city-states, cuneiform writing, Code of Hammurabi, ziggurats (Chapter 2, Section 2.2, Pages 43–48).
            • Ancient Indus Valley and China: Urban planning in Harappa/Mohenjo-Daro, dynastic rule in China (Shang and Zhou) along Yellow River, bronze metallurgy, silk production (Chapter 2, Sections 2.3–2.4, Pages 49–54).
            • Ancient Axum and Kush (Nubia): Indigenous African civilizations in Horn of Africa and Nile Valley, monumental stelae, Red Sea/Indian Ocean trade networks, coinage (Chapter 2, Section 2.5, Pages 55–63).
            """.trimIndent()
        )

        // Unit 3
        addNote(
            "Unit 3 - The Middle Ages and Early Modern World, c. 500 to 1750s",
            "The Middle Ages in Europe and the Rise of Islam (Sections 3.1 - 3.4)",
            """
            • Feudalism in Medieval Europe: Decentralized system of lords, vassals, serfs, manorialism, dominant political authority of Catholic Church (Chapter 3, Section 3.1, Pages 65–72).
            • Rise and Expansion of Islam: Arabian Peninsula in 7th century C.E. under Prophet Muhammad, expanding across Middle East, North Africa, Europe, fostering trade and science (Chapter 3, Section 3.2, Pages 73–80).
            • Medieval African Kingdoms: Trans-Saharan trade empires in West Africa (Ghana, Mali, Songhai) and East African coastal city-states (Kilwa, Mombasa, Sofala) (Chapter 3, Section 3.3, Pages 81–90).
            • Renaissance and Reformation: Cultural rebirth in Europe (revival of classical Greek/Roman art, humanism) and Protestant Reformation challenging Catholic Church hegemony (Chapter 3, Section 3.4, Pages 91–102).
            """.trimIndent()
        )

        // Unit 4
        addNote(
            "Unit 4 - Peoples and States of Ethiopia and the Horn, to the End of the 13th Century",
            "Peoples, Languages, and Early States in the Horn of Africa (Sections 4.1 - 4.5)",
            """
            • Linguistic and Ethnic Diversity: Afro-Asiatic and Nilo-Saharan super-families, comprising Cushitic, Semitic, Omotic, and Nilo-Saharan speaking peoples (Chapter 4, Section 4.1, Pages 103–110).
            • Economic/Social Life: Mixed agriculture, pastoralism, crafts, trade routes connecting interior highlands to Red Sea / Gulf of Aden ports (Chapter 4, Section 4.2, Pages 111–116).
            • Ancient/Medieval States: Dʿmt, Aksumite Empire, Zagwe Dynasty (rock-hewn churches of Lalibela), and Muslim sultanates in southeastern lowlands (Chapter 4, Sections 4.3–4.5, Pages 117–130).
            """.trimIndent()
        )

        // Unit 5
        addNote(
            "Unit 5 - Politics, Economy, and Social Processes in Ethiopia and the Horn, 13th to 16th Centuries",
            "The Christian Kingdom and Muslim Sultanates (Sections 5.1 - 5.5)",
            """
            • Restoration of "Solomonic" Dynasty: Political rule in 1270 C.E. under Yekuno Amlak, claiming Aksumite lineage (Chapter 5, Section 5.1, Pages 131–138).
            • Territorial Expansion & Rivalry: Conflicts between Christian Kingdom and Muslim sultanates (e.g., Sultanate of Adal led by Ahmed ibn Ibrahim al-Ghazi / Gragn) over trade routes (Chapter 5, Sections 5.2–5.4, Pages 139–155).
            • Population Movements: Oromo demographic movements (Gadaa system) reshaping political and social landscape in 16th century (Chapter 5, Section 5.5, Pages 156–165).
            """.trimIndent()
        )

        // Unit 6
        addNote(
            "Unit 6 - Ethiopia and the Horn, Mid-16th to the Mid-19th Centuries",
            "Gondarine Period, Zemene Mesafint, and Regional Dynamics (Sections 6.1 - 6.5)",
            """
            • Gondarine Period (1636–1769): Gondar as permanent capital under Emperor Fasilides, golden age of castles, churches, painting, literature (Chapter 6, Section 6.1, Pages 166–177).
            • Zemene Mesafint (Era of Princes, 1769–1855): Decentralized political power with regional lords fighting for supremacy while Solomonic monarchs held nominal authority (Chapter 6, Section 6.2, Pages 178–184).
            • Yejju Dynasty & Kingdom of Shewa: Regional dynasties exercising political influence during and after Zemene Mesafint (Chapter 6, Sections 6.3–6.4, Pages 181–187).
            """.trimIndent()
        )

        // Unit 7
        addNote(
            "Unit 7 - The Age of Revolutions, 1750s to 1815",
            "The Industrial Revolution, French Revolution, and Global Changes (Sections 7.1 - 7.5)",
            """
            • Industrial Revolution: Mid-18th century Great Britain, transition to machine-driven industrial capitalism, rapid urbanization (Chapter 7, Section 7.1, Pages 188–190).
            • French Revolution (1789): Overthrow of Ancien Régime and absolute monarchy, inspired by Enlightenment ideals ("Liberty, Equality, Fraternity") (Chapter 7, Section 7.2, Pages 191–195).
            • Napoleonic Era: Rise of Napoleon Bonaparte, military expansion, Napoleonic Code, reshaping European power balance (Chapter 7, Section 7.3, Pages 196–200).
            • American War of Independence: Thirteen colonies breaking away from British rule, democratic principles (Chapter 7, Section 7.4, Pages 201–202).
            • Congress of Vienna (1814–1815): Restoring conservative monarchies and power balance post-Napoleonic Wars (Chapter 7, Section 7.5, Pages 203–208).
            """.trimIndent()
        )

        return notesList
    }
}
