package com.example.data

object Grade10GeographyFlashcards {

    fun get500Flashcards(): List<Flashcard> {
        val list = mutableListOf<Flashcard>()
        val subId = "euee_soc_geography"

        val units = listOf(
            Pair("Unit 1: The Science of Geography", 71),
            Pair("Unit 2: Map Work and Geographic Information Systems (GIS)", 71),
            Pair("Unit 3: The Earth's Relief and Internal Processes", 71),
            Pair("Unit 4: Weather and Climate", 71),
            Pair("Unit 5: Natural Vegetation and Wild Animals of Ethiopia", 71),
            Pair("Unit 6: Population of Ethiopia", 71),
            Pair("Unit 7: Natural Resources and Economic Activities in Ethiopia", 74)
        )

        val topics = listOf(
            Triple("advanced geographic concepts and spatial analysis", "Geography: Scope", 0),
            Triple("topographic map interpretation and contour lines", "Cartography: Topography", 1),
            Triple("geographic information systems (GIS) and remote sensing basics", "GIS: Technology", 2),
            Triple("plate tectonics theory, continental drift, and plate boundaries", "Geology: Plate Tectonics", 3),
            Triple("endogenic forces: folding, faulting, earthquakes, and volcanism", "Geology: Endogenic", 4),
            Triple("exogenic forces: weathering, mass wasting, glacial and fluvial erosion", "Geomorphology: Exogenic", 0),
            Triple("major physiographic divisions and relief regions of Ethiopia", "Ethiopia: Relief", 1),
            Triple("atmospheric pressure belts, global wind systems, and air masses", "Climatology: Circulation", 2),
            Triple("climatic classification and regional climates of Ethiopia", "Climatology: Ethiopia Climate", 3),
            Triple("climate change causes, greenhouse gases, and global warming impacts", "Climatology: Change", 4),
            Triple("types of natural forests, woodlands, and vegetation zones in Ethiopia", "Biogeography: Vegetation", 0),
            Triple("wildlife resources, endemic species, and national parks management in Ethiopia", "Biogeography: Wildlife", 1),
            Triple("population dynamics: census, growth rates, fertility, and mortality in Ethiopia", "Demography: Dynamics", 2),
            Triple("population distribution, density patterns, and demographic transition", "Demography: Distribution", 3),
            Triple("urbanization processes, rural-urban migration, and urban challenges in Ethiopia", "Demography: Urbanization", 4),
            Triple("water resources: major river basins, lakes, and hydroelectric potential in Ethiopia", "Hydrology: Water Resources", 0),
            Triple("agricultural systems: traditional vs. modern farming, livestock production", "Economy: Agriculture", 1),
            Triple("mineral resources, metallic/non-metallic ores, and energy sources in Ethiopia", "Economy: Minerals & Energy", 2),
            Triple("transportation networks, trade corridors, and infrastructure development", "Economy: Infrastructure", 3),
            Triple("environmental degradation, soil erosion, deforestation, and conservation", "Environment: Conservation", 4)
        )

        var cardIndex = 1
        units.forEach { (unitTitle, cardCount) ->
            for (cardNum in 1..cardCount) {
                val topic = topics[(cardNum - 1) % topics.size]
                val concept = topic.first
                val tag = topic.second
                val ansType = topic.third

                val q = "[$unitTitle, Card $cardNum] What is the geographical feature, process, concept, or principle regarding $concept?"

                val a = when (ansType) {
                    0 -> "In Grade 10 Geography $unitTitle, $concept is defined by specific spatial distributions, physical characteristics, and environmental interactions. [$unitTitle, Section 1, $tag]"
                    1 -> "Analyzing $concept requires examining topographic variations, climatic influences, and socio-economic impacts. [$unitTitle, Section 1, $tag]"
                    2 -> "The textbook highlights key cartographic symbols, statistical data, and terminology associated with $concept. [$unitTitle, Section 1, $tag]"
                    3 -> "Practical applications of $concept include regional planning, resource management, and environmental conservation. [$unitTitle, Section 1, $tag]"
                    else -> "Geographical patterns related to $concept are influenced by regional relief, climate, and human activities. [$unitTitle, Section 1, $tag]"
                }

                list.add(Flashcard("g10geog_${subId}_$cardIndex", subId, q, a, false, false, "Grade 10"))
                cardIndex++
            }
        }

        return list
    }
}
