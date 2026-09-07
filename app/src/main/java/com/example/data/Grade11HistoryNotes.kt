package com.example.data

object Grade11HistoryNotes {

    fun getGrade11HistoryNotes(): List<SubjectNote> {
        val notesList = mutableListOf<SubjectNote>()
        val subId = "euee_soc_history"
        var idx = 1

        fun addNote(unit: String, title: String, content: String) {
            notesList.add(
                SubjectNote(
                    id = "g11_hist_note_${idx}",
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
            "Unit 1 - History, Historiography, and Human Evolution",
            "History, Historiography, Origin of Human Beings, and Emergence of State (Sections 1.1 - 1.3)",
            """
            • Definition and Nature of History: History is the systematic study of past human societies, events, actions, and socio-economic changes based on critical evaluation of historical sources.
            • Historiography: The history of historical writing; studying how historical interpretations change over time based on new evidence, methodologies, and perspectives.
            • Historical Sources: Categorized into primary sources (firsthand accounts, artifacts, documents created at the time) and secondary sources (later interpretations, textbooks, scholarly articles).
            • Periodization: Dividing history into distinct chronological eras (e.g., ancient, medieval, modern) to facilitate historical analysis, recognizing both change and continuity.
            • Origin of Human Beings: Scientific consensus pointing to East Africa (including the Ethiopian Rift Valley) as the cradle of humankind, tracing human evolution from early hominids (Australopithecus, Homo habilis, Homo erectus) to anatomically modern humans (Homo sapiens sapiens).
            • Emergence of State: The transition from egalitarian tribal societies to complex socio-political state systems characterized by centralized authority, social stratification, taxation, and administrative organization.
            """.trimIndent()
        )

        // Unit 2
        addNote(
            "Unit 2 - Major Spots of Ancient World Civilizations up to c.500 A.D",
            "Ancient Civilizations of Africa, Asia, and Europe (Sections 2.1 - 2.3)",
            """
            • Ancient Civilizations of Africa: Ancient Egypt along the Nile River valley (hieroglyphic writing, monumental architecture like pyramids, advancements in mathematics, astronomy, and medicine) and the Kingdom of Kush/Meroë (iron metallurgy, trade networks).
            • Civilizations in Asia: Mesopotamia in the Tigris-Euphrates valley (cuneiform script, code of Hammurabi), Ancient Indus Valley Civilization (urban planning, drainage systems), Ancient China along the Yellow River (dynastic cycles, bronze working, philosophy), and Ancient Persia (administrative satrapies).
            • Civilizations in Europe: Ancient Greece (development of democracy, philosophy, arts, city-states like Athens and Sparta, Hellenistic legacy) and Ancient Roman Civilization (legal systems, engineering, republic and empire, expansion across the Mediterranean basin).
            """.trimIndent()
        )

        // Unit 3
        addNote(
            "Unit 3 - Peoples, States and Historical Processes in Ethiopia and the Horn to the End of the 13th Century",
            "Languages, Religions, Pre-Aksumite, Aksumite, Shewa, and Zagwe (Sections 3.1 - 3.6)",
            """
            • Languages, Religions, and Peoples: Linguistic diversity in Ethiopia and the Horn (Afroasiatic families: Cushitic, Semitic, Omotic; Nilo-Saharan) and indigenous and world religious traditions.
            • Pre-Aksumite States: Early civilizations such as Yeha, D'mt, and associated archaeological sites exhibiting South Arabian influences blended with indigenous cultures.
            • The Aksumite Kingdom: Rise of Aksum as a major trading power connecting the Roman Empire, India, and interior Africa; minting of gold, silver, and bronze coins; conversion to Christianity in the 4th century; monumental stelae architecture; and decline due to trade disruptions and environmental shifts.
            • Sultanate of Shewa and Zagwe Dynasty: The rise of Muslim sultanates in the region and the Zagwe Dynasty (c. 1150–1270 AD) centered in Lasta, renowned for the rock-hewn monolithic churches of Lalibela.
            """.trimIndent()
        )

        // Unit 4
        addNote(
            "Unit 4 - Christian Europe, the Renaissance, and the Reformation",
            "Feudalism, Crusades, Renaissance, Reformation, and Scientific Revolution (Sections 4.1 - 4.8)",
            """
            • European Feudalism: Socio-economic and political system of medieval Europe characterized by lords, vassals, manorialism, and serfdom.
            • The Crusades: Military expeditions launched by European Christians to recapture the Holy Land from Muslim control, stimulating trade and cultural exchange between East and West.
            • The Renaissance: Cultural and intellectual rebirth originating in Italy during the 14th–17th centuries, emphasizing humanism, classical antiquity, arts, and scientific inquiry.
            • The Reformation: Religious movement initiated by Martin Luther in 1517 challenging Catholic Church corruption and papal authority, leading to the rise of Protestantism and religious wars in Europe.
            • Scientific Revolution and Enlightenment: Shift from medieval scholasticism to empirical observation, scientific method (Galileo, Newton), and Enlightenment philosophy advocating reason, liberty, and political reform.
            """.trimIndent()
        )

        // Unit 5
        addNote(
            "Unit 5 - Peoples and States of Africa to 1500",
            "Ancient, Medieval, North, West, Central, Southern Africa, and Exchanges (Sections 5.1 - 5.7)",
            """
            • Ancient and Medieval African States: Rich indigenous state formations across the African continent prior to European colonial intrusion.
            • North Africa and Spread of Islam: Trans-Saharan trade routes facilitating the spread of Islam and Arabic culture across North and West Africa.
            • States in West Africa: Rise of great medieval empires including Ghana, Mali (under Mansa Musa), and Songhai, renowned for gold-salt trade and Islamic scholarship (Timbuktu).
            • Central, East, and Southern Africa: Kingdom of Kongo, Great Zimbabwe stone civilization, and East African coastal trading city-states (Kilwa, Sofala) trading with the Indian Ocean network.
            • Relationships and Exchanges: Inter-regional trade, migration, and cultural diffusion connecting diverse African societies.
            """.trimIndent()
        )

        // Unit 6
        addNote(
            "Unit 6 - Africa and the Outside World: 1500-1880s",
            "Medieval African States, Contacts, Trans-Atlantic Slave Trade, and Colonization (Sections 6.1 - 6.6)",
            """
            • Medieval African States and External Contacts: Interactions between African kingdoms and external powers (Portuguese, Ottoman, Arab) along coastal and interior trade routes.
            • Trans-Atlantic Slave Trade: The forced deportation of millions of Africans to the Americas to supply labor for European plantations, devastating African demographics, economies, and societies.
            • Legitimate Trade and White Settlement: Transition to "legitimate commerce" (palm oil, rubber, ivory) following the abolition of the slave trade, and early European/Boer white settlement in Southern Africa.
            • European Explorers and Missionaries (1770–1870): Geographical exploration and missionary activities that paved the way for the late 19th-century European scramble for Africa.
            """.trimIndent()
        )

        // Unit 7
        addNote(
            "Unit 7 - States, Principalities, Population Movements and Interactions in Ethiopia",
            "Restored Solomonic Dynasty, Muslim Sultanates, Adal, and Population Movements (Sections 7.1 - 7.5)",
            """
            • Restored "Solomonic" Dynasty: Establishment of the Christian Highland Kingdom in 1270 AD under Yikuno Amlak, consolidation of political power, and territorial expansion.
            • Muslim Sultanates and Relations with Adal: Flourishing Muslim sultanates (Ifat, Adal) and protracted conflicts with the Christian Kingdom over trade routes and political dominance, notably under Imam Ahmed ibn Ibrahim al-Ghazi (Gran) in the 16th century.
            • Population Movements: Major demographic shifts and migrations in the Ethiopian region, including the Oromo population expansion in the 16th century, reshaping ethno-political landscapes.
            """.trimIndent()
        )

        // Unit 8
        addNote(
            "Unit 8 - Political, Social and Economic Processes in Ethiopia, Mid 16th to Mid-19th Century",
            "Peoples/States of Ethiopia and Instability vs. Consolidation in the Christian Kingdom (Sections 8.1 - 8.2)",
            """
            • Peoples and States of Southern, Western, and Eastern Ethiopia: Political, economic, and social organizations of Omotic, Cushitic, and Nilotic societies and regional kingdoms (e.g., Kaffa, Wolayta, Gibe monarchies, Leqa, Afar, Somali).
            • Instability versus Consolidation in the Christian Kingdom (1559–1855): Moving the capital to Gondar (Gondarine Period), cultural renaissance, subsequent decentralization, and the onset of Zemene Mesafint (Era of Princes), characterized by regional warlordism and political fragmentation.
            """.trimIndent()
        )

        // Unit 9
        addNote(
            "Unit 9 - The Age of Revolutions, 1789 to 1815",
            "Industrial Revolution, American Revolution, French Revolution, and Napoleon (Sections 9.1 - 9.4)",
            """
            • Industrial Revolution: Originating in Britain, transforming manufacturing, agriculture, transportation, and giving rise to industrial capitalism and new social classes.
            • American War of Independence (1775–1783): Thirteen colonies breaking away from British colonial rule to establish the United States based on democratic republican ideals.
            • French Revolution (1789): Overthrow of the Ancien Régime and absolute monarchy in France, championing "Liberty, Equality, Fraternity" and reshaping European political thought.
            • Period of Napoleon Bonaparte: Rise of Napoleon, military expansion across Europe, Napoleonic Code, and the eventual Congress of Vienna (1815) attempting to restore the balance of power.
            """.trimIndent()
        )

        return notesList
    }
}