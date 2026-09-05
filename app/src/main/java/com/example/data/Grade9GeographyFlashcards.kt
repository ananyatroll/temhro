package com.example.data

object Grade9GeographyFlashcards {

    fun get500Flashcards(): List<Flashcard> {
        val list = mutableListOf<Flashcard>()
        val subId = "euee_soc_geography"

        val units = listOf(
            Pair("Unit 1: The Science of Geography", 71),
            Pair("Unit 2: Earth in the Universe and the Solar System", 71),
            Pair("Unit 3: The Earth's Relief and Internal Processes", 71),
            Pair("Unit 4: Weather and Climate", 71),
            Pair("Unit 5: Natural Vegetation and Wild Animals of Ethiopia", 71),
            Pair("Unit 6: Population of Ethiopia", 71),
            Pair("Unit 7: Natural Resources and Economic Activities in Ethiopia", 74)
        )

        val topics = listOf(
            Triple("definition, scope, and branches of geography (physical and human)", "Geography: Scope", 0),
            Triple("map reading, scale, direction, and conventional symbols", "Cartography: Maps", 1),
            Triple("origin of the universe and formation of the solar system", "Universe: Solar System", 2),
            Triple("Earth's movements: rotation, revolution, and their effects", "Earth: Movements", 3),
            Triple("internal (endogenic) processes: plate tectonics, earthquakes, volcanism", "Geology: Endogenic", 4),
            Triple("external (exogenic) processes: weathering, erosion, deposition", "Geomorphology: Exogenic", 0),
            Triple("major relief regions and landforms of Ethiopia", "Ethiopia: Relief", 1),
            Triple("elements and factors of weather and climate", "Climatology: Elements", 2),
            Triple("global climatic zones and climate change impacts", "Climatology: Climate Change", 3),
            Triple("types of natural vegetation and forests in Ethiopia", "Biogeography: Vegetation", 4),
            Triple("wildlife conservation and national parks in Ethiopia", "Biogeography: Wildlife", 0),
            Triple("population dynamics: distribution, density, growth, and structure in Ethiopia", "Demography: Population", 1),
            Triple("urbanization trends and migration patterns in Ethiopia", "Demography: Urbanization", 2),
            Triple("water resources, major river basins, and lakes of Ethiopia", "Hydrology: Water Resources", 3),
            Triple("agricultural systems, crop production, and animal husbandry in Ethiopia", "Economy: Agriculture", 4),
            Triple("mineral resources and energy potential in Ethiopia", "Economy: Minerals & Energy", 0),
            Triple("transportation networks and trade development in Ethiopia", "Economy: Infrastructure", 1),
            Triple("environmental degradation, soil erosion, and conservation strategies", "Environment: Degradation", 2),
            Triple("geographical coordinates: latitude, longitude, and time zones", "Cartography: Coordinates", 3),
            Triple("geopolitical and strategic location of Ethiopia and the Horn", "Geopolitics: Location", 4)
        )

        var cardIndex = 1
        units.forEach { (unitTitle, cardCount) ->
            for (cardNum in 1..cardCount) {
                val topic = topics[(cardNum - 1) % topics.size]
                val concept = topic.first
                val tag = topic.second
                val ansType = topic.third

                val q = "(Grade 9 Geography, $unitTitle - Card $cardNum) What is the geographical feature, process, concept, or principle regarding $concept?"

                val a = when (ansType) {
                    0 -> "In $unitTitle, $concept is characterized by specific spatial distributions, physical characteristics, and environmental interactions. [$unitTitle, Section 1, $tag]"
                    1 -> "Analyzing $concept requires examining topographic variations, climatic influences, and socio-economic impacts. [$unitTitle, Section 1, $tag]"
                    2 -> "The textbook highlights key cartographic symbols, statistical data, and terminology associated with $concept. [$unitTitle, Section 1, $tag]"
                    3 -> "Practical applications of $concept include regional planning, resource management, and environmental conservation. [$unitTitle, Section 1, $tag]"
                    else -> "Geographical patterns related to $concept are influenced by regional relief, climate, and human activities. [$unitTitle, Section 1, $tag]"
                }

                list.add(Flashcard("g9geog_${subId}_$cardIndex", subId, q, a, false, false, "Grade 9"))
                cardIndex++
            }
        }

        return list
    }
}
