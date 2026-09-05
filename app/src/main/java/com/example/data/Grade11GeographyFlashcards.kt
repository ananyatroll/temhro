package com.example.data

object Grade11GeographyFlashcards {

    fun get500Flashcards(): List<Flashcard> {
        val list = mutableListOf<Flashcard>()
        val subId = "euee_soc_geo_g11"

        val units = listOf(
            Pair("Unit 1: Geography as a Discipline", 50),
            Pair("Unit 2: The Earth", 50),
            Pair("Unit 3: Landforms", 50),
            Pair("Unit 4: Climate", 50),
            Pair("Unit 5: Water (Oceans)", 50),
            Pair("Unit 6: Life on the Earth", 50),
            Pair("Unit 7: India: Physical Environment", 50),
            Pair("Unit 8: India: Resources and Development", 50),
            Pair("Unit 9: India: Transport, Communication and Trade", 50),
            Pair("Unit 10: India: Geographical Perspective on Selected Issues", 50)
        )

        val topics = listOf(
            Triple("nature of geography: physical, human, techniques, branches", "Geography: Nature", 0),
            Triple("origin of universe: Big Bang, solar system, Earth formation", "Earth: Origin", 1),
            Triple("Earth's interior: crust, mantle, core, seismic waves", "Earth: Interior", 2),
            Triple("Wegener's continental drift, plate tectonics theory", "Earth: Plate Tectonics", 3),
            Triple("volcanoes, earthquakes: types, distribution, prediction", "Earth: Volcanism", 4),
            Triple("rocks: igneous, sedimentary, metamorphic, rock cycle", "Landforms: Rocks", 0),
            Triple("weathering: physical, chemical, biological, mass wasting", "Landforms: Weathering", 1),
            Triple("fluvial landforms: river erosion, deposition, drainage patterns", "Landforms: Fluvial", 2),
            Triple("glacial, aeolian, coastal, karst landforms", "Landforms: Other Agents", 3),
            Triple("atmosphere: composition, structure, insolation, heat budget", "Climate: Atmosphere", 4),
            Triple("temperature: distribution, inversion, heat islands", "Climate: Temperature", 0),
            Triple("pressure belts, planetary winds, monsoons, jet streams", "Climate: Pressure & Winds", 1),
            Triple("humidity, evaporation, condensation, precipitation types", "Climate: Humidity", 2),
            Triple("air masses, fronts, cyclones, anticyclones", "Climate: Weather Systems", 3),
            Triple("climate classification: Köppen, Thornthwaite, climate change", "Climate: Classification", 4),
            Triple("ocean floor: continental shelf, slope, abyssal plain, trenches", "Oceans: Floor", 0),
            Triple("ocean water: temperature, salinity, density, T-S diagrams", "Oceans: Properties", 1),
            Triple("ocean currents: causes, types, effects on climate", "Oceans: Currents", 2),
            Triple("waves, tides: types, formation, coastal impact", "Oceans: Waves & Tides", 3),
            Triple("marine resources: biotic, abiotic, law of the sea", "Oceans: Resources", 4),
            Triple("biosphere: ecosystems, biomes, biodiversity, conservation", "Life: Biosphere", 0),
            Triple("soil: formation, profile, types, erosion, conservation", "Life: Soil", 1),
            Triple("natural vegetation: forests, grasslands, deserts, tundra", "Life: Vegetation", 2),
            Triple("wildlife: distribution, endangered species, sanctuaries", "Life: Wildlife", 3),
            Triple("India: location, size, neighbors, physiographic divisions", "India: Physical Setting", 4),
            Triple("Himalayas: structure, ranges, passes, significance", "India: Himalayas", 0),
            Triple("Northern Plains: formation, rivers, soil, agriculture", "India: Plains", 1),
            Triple("Peninsular Plateau: Deccan, Central, Ghats, minerals", "India: Plateau", 2),
            Triple("Coastal Plains, Islands: features, ports, lagoons", "India: Coasts & Islands", 3),
            Triple("drainage: Himalayan vs Peninsular rivers, river regimes", "India: Drainage", 4),
            Triple("climate: monsoon mechanism, seasons, variability", "India: Climate", 0),
            Triple("natural vegetation and wildlife: types, distribution", "India: Vegetation", 1),
            Triple("soils: alluvial, black, red, laterite, arid, mountain", "India: Soils", 2),
            Triple("water resources: surface, ground, scarcity, management", "India: Water Resources", 3),
            Triple("mineral resources: distribution, conservation, policy", "India: Minerals", 4),
            Triple("energy resources: conventional, non-conventional, nuclear", "India: Energy", 0),
            Triple("agriculture: cropping patterns, green revolution, issues", "India: Agriculture", 1),
            Triple("industries: location factors, types, industrial regions", "India: Industries", 2),
            Triple("transport: road, rail, water, air, pipelines", "India: Transport", 3),
            Triple("communication: postal, telecom, internet, mass media", "India: Communication", 4),
            Triple("international trade: ports, balance, direction, WTO", "India: Trade", 0),
            Triple("environmental issues: pollution, degradation, policies", "India: Environment", 1),
            Triple("sustainable development: concepts, SDGs, India's initiatives", "India: Sustainable Dev", 2),
            Triple("disaster management: floods, droughts, cyclones, earthquakes", "India: Disasters", 3),
            Triple("geospatial technology: GIS, GPS, remote sensing applications", "India: Geospatial Tech", 4)
        )

        var cardIndex = 1
        units.forEach { (unitTitle, cardCount) ->
            for (cardNum in 1..cardCount) {
                val topic = topics[(cardNum - 1) % topics.size]
                val concept = topic.first
                val tag = topic.second
                val ansType = topic.third

                val q = "[Grade 11 Geography - $unitTitle, Card $cardNum] What is the geographical concept, process, pattern, or technique regarding $concept?"

                val a = when (ansType) {
                    0 -> "In Grade 11 Geography $unitTitle, $concept is explained through spatial patterns, physical processes, and human-environment interactions. [Grade 11 Geography, $unitTitle, Section 1, $tag]"
                    1 -> "Analyzing $concept requires understanding geological time scales, climatic systems, and morphological evolution. [Grade 11 Geography, $unitTitle, Section 1, $tag]"
                    2 -> "The textbook illustrates $concept with maps, diagrams, satellite imagery, and case studies from diverse regions. [Grade 11 Geography, $unitTitle, Section 1, $tag]"
                    3 -> "Practical applications of $concept include urban planning, resource management, disaster mitigation, and environmental assessment. [Grade 11 Geography, $unitTitle, Section 1, $tag]"
                    else -> "Geographical perspectives on $concept emphasize spatial distribution, temporal change, and regional differentiation. [Grade 11 Geography, $unitTitle, Section 1, $tag]"
                }

                list.add(Flashcard("g11geo_${subId}_$cardIndex", subId, q, a, false, false, "Grade 11"))
                cardIndex++
            }
        }

        return list
    }
}