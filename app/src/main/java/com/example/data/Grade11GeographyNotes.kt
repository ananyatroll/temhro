package com.example.data

object Grade11GeographyNotes {

    fun getGrade11GeographyNotes(): List<SubjectNote> {
        val notesList = mutableListOf<SubjectNote>()
        val subId = "euee_soc_geography"
        var idx = 1

        fun addNote(unit: String, title: String, content: String) {
            notesList.add(
                SubjectNote(
                    id = "g11_geo_note_${idx}",
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
            "Unit 1 - Formation of the Continents",
            "Formation of the Continents and Oceans (Sections 1.1 - 1.4)",
            """
            • Formation of the Earth: Attributed to the origin of the Solar System, which formed from a rotating cloud of gas and dust (Nebular Hypothesis) or the Big Bang theory.
            • Continental Drift Theory: Proposed by Alfred Wegener, suggesting that all continents were once joined in a single supercontinent called Pangaea, which later broke apart.
            • Geological Timescale: The history of the Earth is divided into Eons, Eras, Periods, and Epochs. Major eras include Precambrian (longest), Paleozoic (ancient life), Mesozoic (age of reptiles), and Cenozoic (age of mammals).
            • Distribution of Continents and Oceans: Oceans cover about 71% of the Earth's surface, while continents cover 29%. Major oceans include Pacific, Atlantic, Indian, Arctic, and Southern.
            • Plate Tectonics: The theory explaining the movement of Earth's lithospheric plates, leading to the formation of mountains, volcanoes, and the changing positions of continents.
            """.trimIndent()
        )

        // Unit 2
        addNote(
            "Unit 2 - Climate Classification and Climate Regions of Our World",
            "Criteria, Köppen's Classification, and Ethiopian Indigenous System (Sections 2.1 - 2.5)",
            """
            • Criteria for Climate Classification: Primarily based on temperature and precipitation patterns over a long period (at least 30 years).
            • Köppen's Climate Classification: Uses capital letters to designate major groups: A (Tropical), B (Dry), C (Mild Mid-latitude), D (Severe Mid-latitude), E (Polar), and H (Highland).
            • Factors Influencing Climate: Latitude, altitude, distance from the sea (continentality), ocean currents, and mountain barriers.
            • Indigenous Climate Classification of Ethiopia: Based on altitude and temperature, categorized as Bereha (hot arid), Kolla (warm), Woina Dega (temperate), Dega (cool), and Wurch (cold).
            """.trimIndent()
        )

        // Unit 3
        addNote(
            "Unit 3 - Natural Resources and Conflicts Over Resources",
            "Land Management, Resource Depletion, and Transboundary Rivers (Sections 3.1 - 3.7)",
            """
            • Functions of Land: Land serves as a platform for human activities, source of food, minerals, and habitat for biodiversity.
            • Resource Depletion and Degradation: Resulting from overexploitation, deforestation, and unsustainable agricultural practices, leading to loss of productivity.
            • Transboundary Rivers: Rivers that cross international borders, such as the Nile, which requires regional cooperation among riparian states (Ethiopia, Sudan, Egypt).
            • Potential and Actual Water Use: Ethiopia's water resources are vital for hydropower (e.g., GERD) and irrigation, while downstream countries rely heavily on the same flow for agriculture.
            """.trimIndent()
        )

        // Unit 4
        addNote(
            "Unit 4 - Global Population Dynamics and Challenges",
            "Population Growth, Migration, and Policies (Sections 4.1 - 4.4)",
            """
            • World Population Growth: Characterized by exponential growth since the Industrial Revolution, leading to concerns about resource sustainability.
            • Factors of Population Growth: Fertility (birth rates), mortality (death rates), and migration are the three main drivers.
            • International Migration: Driven by push factors (poverty, conflict, environmental stress) and pull factors (economic opportunity, safety, education).
            • Population Policies: Strategies adopted by governments to influence population trends, categorized as pro-natalist (encouraging births) or anti-natalist (limiting births).
            """.trimIndent()
        )

        // Unit 5
        addNote(
            "Unit 5 - Geography and Economic Development",
            "Geographic Location, Climate Extremes, and Landlocked Countries (Sections 5.1 - 5.4)",
            """
            • Effects of Geographic Location: A country's absolute location (latitude/longitude) and relative location (proximity to markets/oceans) significantly impact its economic development potential.
            • Disadvantages of Landlocked Countries: Lack of direct access to the sea, leading to high transit costs, dependence on neighboring countries, and trade barriers.
            • Intraregional Trade in Africa: Efforts like the African Continental Free Trade Area (AfCFTA) aim to boost economic integration and reduce trade barriers across the continent.
            """.trimIndent()
        )

        // Unit 6
        addNote(
            "Unit 6 - Major Global Environmental Changes",
            "Persistent Problems, Poverty-Environment Nexus, and Sustainability (Sections 6.1 - 6.3)",
            """
            • Persistent Environmental Problems: Global warming (greenhouse effect), acid rain, ozone layer depletion, and loss of biodiversity.
            • Poverty-Environment Nexus: A reciprocal relationship where poverty leads to environmental degradation (due to survival needs) and environmental degradation exacerbates poverty (loss of resources).
            • Sustainable Development: Development that meets the needs of the present without compromising the ability of future generations to meet their own needs.
            """.trimIndent()
        )

        // Unit 7
        addNote(
            "Unit 7 - Geographic Issues and Public Concerns",
            "Land Degradation, Drought, Deforestation, and Digital Divide (Sections 7.1 - 7.5)",
            """
            • Land Degradation and Desertification: The process by which fertile land becomes desert, often due to drought, deforestation, or inappropriate agriculture.
            • Recurrent Drought and Famine: Severe impacts on food security, especially in regions like the Horn of Africa, necessitating improved early warning systems.
            • Worldwide Digital Divide: The gap between individuals, households, businesses, and geographic areas at different socio-economic levels regarding their opportunities to access ICT.
            """.trimIndent()
        )

        // Unit 8
        addNote(
            "Unit 8 - Geo-spatial Information and Data Processing",
            "Relief Representation, GIS Concepts, and ArcMap Tools (Sections 8.1 - 8.3)",
            """
            • Representation of Relief: Relief features like mountains, plateaus, and valleys are represented on topographic maps using contour lines (lines connecting points of equal elevation).
            • Drainage Patterns: The arrangement of streams in a drainage basin, such as dendritic (tree-like), trellis (rectangular), or radial (outward from a center).
            • Geographical Information System (GIS): A system designed to capture, store, manipulate, analyze, manage, and present spatial or geographic data. Components include hardware, software, data, people, and methods.
            • ArcMap Tools: Essential tools for digital mapping, including Zoom In/Out, Pan, Identify, Measure, and Add Layers for spatial analysis.
            """.trimIndent()
        )

        return notesList
    }
}