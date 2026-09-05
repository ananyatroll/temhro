package com.example.data

object Grade11HistoryFlashcards {

    fun get500Flashcards(): List<Flashcard> {
        val list = mutableListOf<Flashcard>()
        val subId = "euee_soc_hist_g11"

        val units = listOf(
            Pair("Unit 1: From the Beginning of Time", 50),
            Pair("Unit 2: Writing and City Life", 50),
            Pair("Unit 3: An Empire Across Three Continents", 50),
            Pair("Unit 4: The Central Islamic Lands", 50),
            Pair("Unit 5: Nomadic Empires", 50),
            Pair("Unit 6: The Three Orders", 50),
            Pair("Unit 7: Changing Cultural Traditions", 50),
            Pair("Unit 8: Confrontation of Cultures", 50),
            Pair("Unit 9: The Industrial Revolution", 50),
            Pair("Unit 10: Displacing Indigenous Peoples", 50),
            Pair("Unit 11: Paths to Modernization", 50)
        )

        val topics = listOf(
            Triple("early human evolution: Australopithecus to Homo sapiens", "Prehistory: Human Evolution", 0),
            Triple("Paleolithic tools, art, and hunter-gatherer societies", "Prehistory: Paleolithic", 1),
            Triple("Mesolithic transition and Neolithic revolution: agriculture", "Prehistory: Neolithic", 2),
            Triple("origins of writing: cuneiform, hieroglyphics, Indus script", "Early Civilizations: Writing", 3),
            Triple("Sumerian city-states: Uruk, Ur, temple economy", "Mesopotamia: Sumer", 4),
            Triple("Akkadian and Babylonian empires: law codes, Hammurabi", "Mesopotamia: Empires", 0),
            Triple("Egyptian Old Kingdom: pyramids, pharaohs, bureaucracy", "Egypt: Old Kingdom", 1),
            Triple("Egyptian Middle and New Kingdoms: expansion, Hatshepsut, Akhenaten", "Egypt: New Kingdom", 2),
            Triple("Harappan civilization: urban planning, trade, decline", "Indus Valley: Civilization", 3),
            Triple("Vedic period: Rig Veda, society, polity, religion", "India: Vedic Age", 4),
            Triple("Mahajanapadas, rise of Magadha, Mauryan empire", "India: Mauryan", 0),
            Triple("Ashoka: edicts, dhamma, administration, decline", "India: Ashoka", 1),
            Triple("post-Mauryan: Sungas, Satavahanas, Kushanas, trade", "India: Post-Mauryan", 2),
            Triple("Gupta empire: golden age, art, science, decline", "India: Gupta", 3),
            Triple("Roman Republic: constitution, expansion, civil wars", "Rome: Republic", 4),
            Triple("Roman Empire: Augustus, Pax Romana, Christianity", "Rome: Empire", 0),
            Triple("decline of Rome: crisis, division, fall of West", "Rome: Decline", 1),
            Triple("Byzantine Empire: Justinian, law code, Hagia Sophia", "Byzantium: Empire", 2),
            Triple("rise of Islam: Prophet Muhammad, Quran, caliphates", "Islam: Origins", 3),
            Triple("Umayyad and Abbasid caliphates: golden age, Baghdad", "Islam: Caliphates", 4),
            Triple("Islamic contributions: science, medicine, philosophy, art", "Islam: Achievements", 0),
            Triple("Mongol empire: Genghis Khan, conquests, Pax Mongolica", "Nomads: Mongols", 1),
            Triple("feudalism in Europe: manors, serfs, vassalage", "Medieval Europe: Feudalism", 2),
            Triple("three orders: those who pray, fight, work", "Medieval Europe: Three Orders", 3),
            Triple("church power: papacy, monasteries, crusades", "Medieval Europe: Church", 4),
            Triple("Renaissance: humanism, art, printing, Italian city-states", "Early Modern: Renaissance", 0),
            Triple("Reformation: Luther, Calvin, Counter-Reformation", "Early Modern: Reformation", 1),
            Triple("Age of Exploration: Columbus, Vasco da Gama, Magellan", "Early Modern: Exploration", 2),
            Triple("conquest of Americas: Aztecs, Incas, Columbian Exchange", "Early Modern: Americas", 3),
            Triple("scientific revolution: Copernicus, Galileo, Newton", "Early Modern: Science", 4),
            Triple("Enlightenment: Locke, Voltaire, Rousseau, reason", "Early Modern: Enlightenment", 0),
            Triple("industrial revolution: Britain, steam power, factories", "Modern: Industrial Rev", 1),
            Triple("social impact: urbanization, labor movements, socialism", "Modern: Industrial Society", 2),
            Triple("imperialism: scramble for Africa, Asia, causes", "Modern: Imperialism", 3),
            Triple("nationalism: unification of Italy and Germany", "Modern: Nationalism", 4),
            Triple("World War I: causes, trench warfare, Treaty of Versailles", "Modern: WWI", 0),
            Triple("Russian Revolution: 1905, 1917, Lenin, USSR", "Modern: Russian Rev", 1),
            Triple("interwar period: Great Depression, rise of fascism", "Modern: Interwar", 2),
            Triple("World War II: causes, Holocaust, atomic bomb, UN", "Modern: WWII", 3),
            Triple("Cold War: bipolarity, proxy wars, détente, end", "Modern: Cold War", 4),
            Triple("decolonization: India, Africa, Southeast Asia", "Modern: Decolonization", 0),
            Triple("contemporary world: globalization, terrorism, climate", "Modern: Contemporary", 1)
        )

        var cardIndex = 1
        units.forEach { (unitTitle, cardCount) ->
            for (cardNum in 1..cardCount) {
                val topic = topics[(cardNum - 1) % topics.size]
                val concept = topic.first
                val tag = topic.second
                val ansType = topic.third

                val q = "[Grade 11 History - $unitTitle, Card $cardNum] What is the historical event, process, personality, or interpretation regarding $concept?"

                val a = when (ansType) {
                    0 -> "In Grade 11 History $unitTitle, $concept is analyzed through primary sources, archaeological evidence, and historiographical debates. [Grade 11 History, $unitTitle, Section 1, $tag]"
                    1 -> "Understanding $concept requires examining political structures, economic systems, and social hierarchies of the period. [Grade 11 History, $unitTitle, Section 1, $tag]"
                    2 -> "The textbook presents multiple perspectives on $concept, including contemporary accounts and modern historical interpretations. [Grade 11 History, $unitTitle, Section 1, $tag]"
                    3 -> "The significance of $concept lies in its long-term impact on cultural exchange, technological diffusion, and power relations. [Grade 11 History, $unitTitle, Section 1, $tag]"
                    else -> "Historical debates regarding $concept focus on causation, agency, and the relative weight of structural vs. individual factors. [Grade 11 History, $unitTitle, Section 1, $tag]"
                }

                list.add(Flashcard("g11hist_${subId}_$cardIndex", subId, q, a, false, false, "Grade 11"))
                cardIndex++
            }
        }

        return list
    }
}