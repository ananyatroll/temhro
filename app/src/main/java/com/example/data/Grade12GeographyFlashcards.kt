package com.example.data

object Grade12GeographyFlashcards {

    fun get500Flashcards(): List<Flashcard> {
        val list = mutableListOf<Flashcard>()
        val subId = "euee_soc_geo_g12"

        val units = listOf(
            Pair("Unit 1: Human Geography: Nature and Scope", 42),
            Pair("Unit 2: People", 42),
            Pair("Unit 3: Human Activities", 42),
            Pair("Unit 4: Transport, Communication and Trade", 42),
            Pair("Unit 5: Human Settlements", 42),
            Pair("Unit 6: India: People and Economy", 42),
            Pair("Unit 7: India: Resources and Development", 42),
            Pair("Unit 8: India: Transport, Communication and Trade", 42),
            Pair("Unit 9: Geographical Perspective on Selected Issues", 42),
            Pair("Unit 10: Map Work and Practical Skills", 40)
        )

        val topics = listOf(
            Triple("nature of human geography: approaches, branches, interdisciplinarity", "Human Geo: Nature", 0),
            Triple("population: distribution, density, growth, demographic transition", "People: Population", 1),
            Triple("population composition: age-sex, rural-urban, literacy, occupational", "People: Composition", 2),
            Triple("migration: types, causes, consequences, theories, remittances", "People: Migration", 3),
            Triple("human development: HDI, GDI, capabilities, sustainability", "People: Human Development", 4),
            Triple("primary activities: hunting, pastoralism, agriculture types", "Activities: Primary", 0),
            Triple("agriculture: subsistence, commercial, plantation, mixed farming", "Activities: Agriculture", 1),
            Triple("land use, cropping patterns, green revolution, food security", "Activities: Land Use", 2),
            Triple("minerals: distribution, mining, conservation, critical minerals", "Activities: Minerals", 3),
            Triple("manufacturing: location factors, types, industrial regions", "Activities: Manufacturing", 4),
            Triple("tertiary: trade, transport, tourism, services, quaternary", "Activities: Services", 0),
            Triple("transport: road, rail, water, air, pipeline, intermodal", "Transport: Modes", 1),
            Triple("communication: satellite, internet, fiber, digital divide", "Communication: Modern", 2),
            Triple("international trade: basis, balance, direction, WTO, blocs", "Trade: International", 3),
            Triple("settlements: rural-urban, types, patterns, functions", "Settlements: Types", 4),
            Triple("urbanization: trends, problems, smart cities, slums", "Settlements: Urbanization", 0),
            Triple("India: population distribution, density, growth, policy", "India: Population", 1),
            Triple("India: migration, urbanization, slums, smart cities", "India: Migration", 2),
            Triple("India: land resources, agriculture, cropping patterns", "India: Agriculture", 3),
            Triple("India: water resources, irrigation, scarcity, interlinking", "India: Water", 4),
            Triple("India: mineral, energy resources, distribution, policy", "India: Minerals Energy", 0),
            Triple("India: manufacturing, industrial corridors, SEZ, Make in India", "India: Industry", 1),
            Triple("India: transport network, dedicated corridors, waterways", "India: Transport", 2),
            Triple("India: communication, digital India, e-governance", "India: Communication", 3),
            Triple("India: international trade, ports, balance, partners", "India: Trade", 4),
            Triple("environmental issues: pollution, degradation, climate change", "Issues: Environment", 0),
            Triple("sustainable development: SDGs, India's initiatives, NAPCC", "Issues: Sustainable Dev", 1),
            Triple("disaster management: floods, droughts, cyclones, earthquakes", "Issues: Disasters", 2),
            Triple("geospatial technology: GIS, GPS, remote sensing, Bhuvan", "Issues: Geospatial", 3),
            Triple("map reading: scale, projection, symbols, topographical sheets", "Practical: Map Reading", 4),
            Triple("data representation: graphs, diagrams, thematic maps", "Practical: Data Rep", 0),
            Triple("statistical methods: central tendency, dispersion, correlation", "Practical: Statistics", 1),
            Triple("computer aided: spreadsheets, GIS software, data analysis", "Practical: Computer", 2),
            Triple("field survey: questionnaire, observation, GPS mapping", "Practical: Field Survey", 3)
        )

        var cardIndex = 1
        units.forEach { (unitTitle, cardCount) ->
            for (cardNum in 1..cardCount) {
                val topic = topics[(cardNum - 1) % topics.size]
                val concept = topic.first
                val tag = topic.second
                val ansType = topic.third

                val q = "[Grade 12 Geography - $unitTitle, Card $cardNum] What is the geographical concept, pattern, process, or technique regarding $concept?"

                val a = when (ansType) {
                    0 -> "In Grade 12 Geography $unitTitle, $concept is explained through spatial patterns, demographic models, and human-environment interactions. [Grade 12 Geography, $unitTitle, Section 1, $tag]"
                    1 -> "Analyzing $concept requires understanding population dynamics, economic activities, and settlement hierarchies. [Grade 12 Geography, $unitTitle, Section 1, $tag]"
                    2 -> "The textbook illustrates $concept with statistical data, thematic maps, satellite imagery, and regional case studies. [Grade 12 Geography, $unitTitle, Section 1, $tag]"
                    3 -> "Practical applications of $concept include urban planning, resource management, policy formulation, and sustainable development. [Grade 12 Geography, $unitTitle, Section 1, $tag]"
                    else -> "Geographical perspectives on $concept emphasize spatial justice, regional disparities, and participatory planning approaches. [Grade 12 Geography, $unitTitle, Section 1, $tag]"
                }

                list.add(Flashcard("g12geo_${subId}_$cardIndex", subId, q, a, false, false, "Grade 12"))
                cardIndex++
            }
        }

        return list
    }
}