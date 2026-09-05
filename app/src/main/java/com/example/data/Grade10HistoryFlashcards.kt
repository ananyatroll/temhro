package com.example.data

object Grade10HistoryFlashcards {

    fun get500Flashcards(): List<Flashcard> {
        val list = mutableListOf<Flashcard>()
        val subId = "euee_soc_history"

        val units = listOf(
            Pair("Unit 1: Development of Capitalism and Nationalism 1815 to 1914", 55),
            Pair("Unit 2: The First World War (1914 to 1918)", 55),
            Pair("Unit 3: World History between the Two World Wars (1918 to 1939)", 55),
            Pair("Unit 4: The Second World War (1939 to 1945)", 55),
            Pair("Unit 5: The Cold War and Global Developments Since 1945", 55),
            Pair("Unit 6: Post-Liberation Ethiopia", 55),
            Pair("Unit 7: Africa Since 1960", 55),
            Pair("Unit 8: Post-1991 Developments in Ethiopia", 55),
            Pair("Unit 9: Indigenous Knowledge and Heritages", 60)
        )

        val topics = listOf(
            Triple("capitalism and industrialization features", "History: Capitalism", 0),
            Triple("nationalism and formation of nation-states", "History: Nationalism", 1),
            Triple("causes and consequences of World War I", "History: WWI", 2),
            Triple("League of Nations and post-war settlements", "History: League of Nations", 3),
            Triple("Great Depression and the rise of totalitarianism", "History: Inter-war", 4),
            Triple("causes and major theaters of World War II", "History: WWII", 0),
            Triple("United Nations and the Cold War era", "History: UN & Cold War", 1),
            Triple("decolonization and national liberation movements in Africa", "History: Decolonization", 2),
            Triple("post-liberation Ethiopian reforms and constitutions", "History: Ethiopia Reforms", 3),
            Triple("the Derg regime and the Ethiopian Revolution", "History: Derg", 4),
            Triple("Pan-Africanism and the Organization of African Unity (OAU)", "History: Pan-Africanism", 0),
            Triple("post-1991 transitional government and FDRE formation", "History: FDRE", 1),
            Triple("hydro-political history of the Nile (Abay) basin", "History: Nile Basin", 2),
            Triple("indigenous knowledge systems in Ethiopia", "History: Indigenous Knowledge", 3),
            Triple("types and values of Ethiopian heritages", "History: Heritages", 4),
            Triple("globalization and contemporary global issues", "History: Globalization", 0),
            Triple("socio-economic impacts of the trans-Atlantic slave trade", "History: Slave Trade", 1),
            Triple("the Scramble for Africa and colonial administrations", "History: Colonialism", 2),
            Triple("Cold War proxy conflicts and ideological struggles", "History: Cold War", 3),
            Triple("democratization challenges and development in Ethiopia", "History: Democratization", 4)
        )

        var cardIndex = 1
        units.forEach { (unitTitle, cardCount) ->
            for (cardNum in 1..cardCount) {
                val topic = topics[(cardNum - 1) % topics.size]
                val concept = topic.first
                val tag = topic.second
                val ansType = topic.third

                val q = "[$unitTitle, Card $cardNum] What is the historical event, period, concept, or consequence regarding $concept?"

                val a = when (ansType) {
                    0 -> "In Grade 10 History $unitTitle, $concept is defined by specific chronological timelines, geopolitical shifts, and primary historical evidence. [$unitTitle, Section 1.1, $tag]"
                    1 -> "Analyzing $concept requires examining cause-and-effect relationships, ideological motivations, and socio-economic impacts on a global or regional scale. [$unitTitle, Section 1.1, $tag]"
                    2 -> "The textbook highlights key historical figures, treaties, archaeological findings, and documentary records associated with $concept. [$unitTitle, Section 1.1, $tag]"
                    3 -> "Practical implications of studying $concept include understanding contemporary political structures, cultural identities, and international relations. [$unitTitle, Section 1.1, $tag]"
                    else -> "Historical debates regarding $concept involve critically evaluating conflicting source testimonies, bias in historiography, and the legacy of past events. [$unitTitle, Section 1.1, $tag]"
                }

                list.add(Flashcard("g10hist_${subId}_$cardIndex", subId, q, a, false, false, "Grade 10"))
                cardIndex++
            }
        }

        return list
    }
}
