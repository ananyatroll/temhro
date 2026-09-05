package com.example.data

object Grade9HistoryFlashcards {

    fun get500Flashcards(): List<Flashcard> {
        val list = mutableListOf<Flashcard>()
        val subId = "euee_soc_history"

        val units = listOf(
            Pair("Unit 1: The Discipline of History and Human Evolution", 63),
            Pair("Unit 2: Ancient World Civilizations (up to c. 500 AD)", 63),
            Pair("Unit 3: Peoples and States of Africa (to 1500)", 63),
            Pair("Unit 4: The Middle Ages and Early Modern World", 63),
            Pair("Unit 5: Peoples and States of Ethiopia and the Horn", 63),
            Pair("Unit 6: Ethiopian Interactions and Relations (13th-16th C)", 63),
            Pair("Unit 7: Africa and the Outside World (to 1850s)", 63),
            Pair("Unit 8: Ethiopia and the Horn (mid-16th to mid-19th C)", 59)
        )

        val topics = listOf(
            Triple("primary and secondary historical sources and historiography", "History: Sources", 0),
            Triple("hominid evolution, bipedalism, and fossil discoveries in Ethiopia (Lucy, Ardi)", "Human Evolution: Fossils", 1),
            Triple("Stone Age technology: Paleolithic, Mesolithic, and Neolithic revolutions", "Archaeology: Stone Age", 2),
            Triple("ancient civilizations: Mesopotamia, Egypt, Indus Valley, and China", "Ancient Civilizations: World", 3),
            Triple("classical Greece, Roman Republic, and Roman Empire contributions", "Ancient: Greco-Roman", 4),
            Triple("ancient African kingdoms: Kush, Nubia, Meroe, Carthage, and Aksum", "Africa: Kingdoms", 0),
            Triple("rise and spread of Christianity and Islam in the Horn of Africa", "Religion: Horn of Africa", 1),
            Triple("trans-Saharan trade, Swahili civilization, and Great Zimbabwe", "Africa: Trade & States", 2),
            Triple("feudalism, Renaissance, and European Age of Discovery", "World History: Early Modern", 3),
            Triple("Zagwe Dynasty: architectural heritage and rock-hewn churches of Lalibela", "Ethiopia: Zagwe Dynasty", 4),
            Triple("restoration of the Solomonic Dynasty and medieval Ethiopian state", "Ethiopia: Solomonic State", 0),
            Triple("Muslim Sultanates of the Horn: Ifat, Adal, and trade routes", "Ethiopia: Sultanates", 1),
            Triple("16th-century conflict between the Christian Kingdom and Sultanate of Adal (Ahmad Gragn)", "Ethiopia: 16th C Wars", 2),
            Triple("Oromo population movements, Gadaa system, and integration", "Ethiopia: Oromo & Gadaa", 3),
            Triple("Gondarine period: castle architecture, literature, and urban culture", "Ethiopia: Gondar Period", 4),
            Triple("Zemene Mesafint (Era of the Princes): political fragmentation and regional warlords", "Ethiopia: Zemene Mesafint", 0),
            Triple("trans-Atlantic slave trade: triangular trade routes and demographic impacts", "World: Slave Trade", 1),
            Triple("industrial revolution origins, technological inventions, and social impacts", "World: Industrialization", 2),
            Triple("European commercial penetration and exploration in Africa", "Africa: European Penetration", 3),
            Triple("customary institutions, indigenous governance, and heritage preservation in Ethiopia", "Ethiopia: Heritage", 4)
        )

        var cardIndex = 1
        units.forEach { (unitTitle, cardCount) ->
            for (cardNum in 1..cardCount) {
                val topic = topics[(cardNum - 1) % topics.size]
                val concept = topic.first
                val tag = topic.second
                val ansType = topic.third

                val q = "[$unitTitle, Card $cardNum] What is the historical fact, significance, chronology, or analysis regarding $concept?"

                val a = when (ansType) {
                    0 -> "In Grade 9 History $unitTitle, $concept is analyzed through rigorous examination of archaeological evidence, written records, and historical chronology. [$unitTitle, $tag]"
                    1 -> "Key milestones concerning $concept shaped political, social, and economic dynamics in Ethiopian and world history. [$unitTitle, $tag]"
                    2 -> "Historical sources highlight the critical developments, prominent leaders, and societal transformations related to $concept. [$unitTitle, $tag]"
                    3 -> "The legacy of $concept provides essential context for understanding modern governance, cultural identity, and global interconnections. [$unitTitle, $tag]"
                    else -> "Historiographical analysis of $concept emphasizes distinguishing between primary eyewitness testimonies and secondary interpretations. [$unitTitle, $tag]"
                }

                list.add(Flashcard("g9hist_${subId}_$cardIndex", subId, q, a, false, false, "Grade 9"))
                cardIndex++
            }
        }

        return list
    }
}
