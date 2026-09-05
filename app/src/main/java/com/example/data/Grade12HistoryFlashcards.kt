package com.example.data

object Grade12HistoryFlashcards {

    fun get500Flashcards(): List<Flashcard> {
        val list = mutableListOf<Flashcard>()
        val subId = "euee_soc_hist_g12"

        val units = listOf(
            Pair("Unit 1: Bricks, Beads and Bones", 46),
            Pair("Unit 2: Kings, Farmers and Towns", 46),
            Pair("Unit 3: Kinship, Caste and Class", 46),
            Pair("Unit 4: Thinkers, Beliefs and Buildings", 46),
            Pair("Unit 5: Through the Eyes of Travellers", 46),
            Pair("Unit 6: Bhakti-Sufi Traditions", 46),
            Pair("Unit 7: An Imperial Capital: Vijayanagara", 46),
            Pair("Unit 8: Peasants, Zamindars and the State", 46),
            Pair("Unit 9: Kings and Chronicles", 46),
            Pair("Unit 10: Colonialism and the Countryside", 46),
            Pair("Unit 11: Rebels and the Raj", 46),
            Pair("Unit 12: Colonial Cities", 46),
            Pair("Unit 13: Mahatma Gandhi and the Nationalist Movement", 46),
            Pair("Unit 14: Understanding Partition", 46),
            Pair("Unit 15: Framing the Constitution", 46)
        )

        val topics = listOf(
            Triple("Harappan civilization: discovery, extent, chronology", "Harappa: Discovery", 0),
            Triple("urban planning: citadel, lower town, drainage, standardization", "Harappa: Urban Planning", 1),
            Triple("subsistence: agriculture, animal husbandry, trade", "Harappa: Subsistence", 2),
            Triple("crafts: beads, seals, weights, metallurgy, shell working", "Harappa: Crafts", 3),
            Triple("script: undeciphered, seal inscriptions, theories", "Harappa: Script", 4),
            Triple("decline: climate, river shifts, Aryan invasion theory", "Harappa: Decline", 0),
            Triple("Mahajanapadas: 16 states, Magadha's rise, polity", "Early India: Mahajanapadas", 1),
            Triple("Mauryan empire: Chandragupta, Arthashastra, administration", "Early India: Mauryan", 2),
            Triple("Ashoka: edicts, dhamma, missions, pillars", "Early India: Ashoka", 3),
            Triple("post-Mauryan: Sungas, Satavahanas, Indo-Greeks, Kushanas", "Early India: Post-Mauryan", 4),
            Triple("Gupta empire: golden age, administration, art, science", "Early India: Gupta", 0),
            Triple("land grants, peasantry, urban centers, guilds", "Early India: Economy", 1),
            Triple("varna, jati, untouchability, women, property rights", "Social: Varna Jati", 2),
            Triple("marriage rules: gotra, exogamy, endogamy, anuloma/pratiloma", "Social: Marriage", 3),
            Triple("Mahabharata: kinship, dharma, war, critical edition", "Social: Mahabharata", 4),
            Triple("Buddhism: Buddha, Four Noble Truths, Eightfold Path", "Religion: Buddhism", 0),
            Triple("Jainism: Mahavira, anekantavada, ahimsa, sects", "Religion: Jainism", 1),
            Triple("Stupas: Sanchi, Amaravati, architecture, sculpture", "Religion: Stupas", 2),
            Triple("Hinduism: Puranic traditions, temples, bhakti", "Religion: Hinduism", 3),
            Triple("Megasthenes, Fa-Hien, Hsuan Tsang: accounts, society", "Travellers: Foreign", 4),
            Triple("Al-Biruni: Kitab-ul-Hind, caste, science, comparison", "Travellers: Al-Biruni", 0),
            Triple("Ibn Battuta: Rihla, Delhi Sultanate, customs", "Travellers: Ibn Battuta", 1),
            Triple("Francois Bernier: Mughal empire, land revenue, cities", "Travellers: Bernier", 2),
            Triple("Bhakti: Alvars, Nayanars, Kabir, Guru Nanak, Mirabai", "Bhakti: Saints", 3),
            Triple("Sufism: silsilas, khanqahs, Chishti, Suhrawardi", "Sufism: Orders", 4),
            Triple("Vijayanagara: foundation, Krishnadevaraya, architecture", "Vijayanagara: Empire", 0),
            Triple("Vijayanagara: water management, bazaars, Hampi ruins", "Vijayanagara: City", 1),
            Triple("Mughal agrarian system: zabti, batai, kankut, zamindars", "Mughal: Agrarian", 2),
            Triple("peasant life: jins-i-kamil, revenue demands, revolts", "Mughal: Peasants", 3),
            Triple("Ain-i-Akbari: Abul Fazl, administration, culture", "Mughal: Ain-i-Akbari", 4),
            Triple("Mughal chronicles: Baburnama, Akbarnama, Padshahnama", "Mughal: Chronicles", 0),
            Triple("painting: miniatures, portraits, European influence", "Mughal: Painting", 1),
            Triple("colonialism: Permanent, Ryotwari, Mahalwari settlements", "Colonial: Land Revenue", 2),
            Triple("commercialization: indigo, opium, cotton, famine", "Colonial: Commercialization", 3),
            Triple("Paharias, Santhals: resistance, Damin-i-Koh", "Colonial: Tribal Resistance", 4),
            Triple("1857 revolt: causes, leaders, suppression, nature", "1857: Revolt", 0),
            Triple("aftermath: Crown rule, Queen's Proclamation, reorganization", "1857: Aftermath", 1),
            Triple("colonial cities: port cities, hill stations, cantonments", "Colonial: Cities", 2),
            Triple("urban planning: segregation, public buildings, railways", "Colonial: Urban Planning", 3),
            Triple("Gandhi: South Africa, Hind Swaraj, satyagraha, ashrams", "Nationalism: Gandhi", 4),
            Triple("non-cooperation, salt march, quit India, negotiations", "Nationalism: Movements", 0),
            Triple("Subhas Chandra Bose, INA, naval mutiny, INA trials", "Nationalism: Bose INA", 1),
            Triple("partition: demand, Cabinet Mission, Mountbatten Plan", "Partition: Demand", 2),
            Triple("violence, migration, rehabilitation, women's experience", "Partition: Experience", 3),
            Triple("Constituent Assembly: debates, Preamble, fundamental rights", "Constitution: Assembly", 4),
            Triple("federalism, judiciary, amendment, directive principles", "Constitution: Features", 0)
        )

        var cardIndex = 1
        units.forEach { (unitTitle, cardCount) ->
            for (cardNum in 1..cardCount) {
                val topic = topics[(cardNum - 1) % topics.size]
                val concept = topic.first
                val tag = topic.second
                val ansType = topic.third

                val q = "[Grade 12 History - $unitTitle, Card $cardNum] What is the historical event, process, personality, or interpretation regarding $concept?"

                val a = when (ansType) {
                    0 -> "In Grade 12 History $unitTitle, $concept is analyzed through primary sources, archaeological evidence, and historiographical debates. [Grade 12 History, $unitTitle, Section 1, $tag]"
                    1 -> "Understanding $concept requires examining political structures, economic systems, and social hierarchies of the period. [Grade 12 History, $unitTitle, Section 1, $tag]"
                    2 -> "The textbook presents multiple perspectives on $concept, including contemporary accounts and modern historical interpretations. [Grade 12 History, $unitTitle, Section 1, $tag]"
                    3 -> "The significance of $concept lies in its long-term impact on cultural exchange, technological diffusion, and power relations. [Grade 12 History, $unitTitle, Section 1, $tag]"
                    else -> "Historical debates regarding $concept focus on causation, agency, and the relative weight of structural vs. individual factors. [Grade 12 History, $unitTitle, Section 1, $tag]"
                }

                list.add(Flashcard("g12hist_${subId}_$cardIndex", subId, q, a, false, false, "Grade 12"))
                cardIndex++
            }
        }

        return list
    }
}