package com.example.data

object Grade10GeographyNotes {

    fun getGrade10GeographyNotes(): List<SubjectNote> {
        val notesList = mutableListOf<SubjectNote>()
        val subId = "euee_soc_geography"
        var idx = 1

        fun addNote(unit: String, title: String, content: String) {
            notesList.add(
                SubjectNote(
                    id = "g10_geog_note_${idx}",
                    subjectId = subId,
                    unit = unit,
                    title = title,
                    content = content,
                    gradeLevel = "Grade 10"
                )
            )
            idx++
        }

        addNote(
            "Unit 1 - Landforms of Africa",
            "Major Landforms and Topography of Africa (Sections 1.1 - 1.3)",
            """
            • Landforms: Mountains (e.g. Atlas, Drakensberg, Kilimanjaro, Kenya), Plateaus (extensive African tablelands), Plains, Basins (Congo, Kalahari), Rift Valley system.
            • Location of Africa: Centrally crossed by Equator, bounded by Mediterranean, Red Sea, Atlantic, Indian Ocean.
            """.trimIndent()
        )

        addNote(
            "Unit 2 - Climate of Africa",
            "Climatic Zones and Climate Change in Africa (Sections 2.1 - 2.4)",
            """
            • Climate Zones: Equatorial Rainforest (rain year-round), Savanna (wet/dry), Desert/Arid (Sahara, Namib, Kalahari), Mediterranean (warm dry summer, mild wet winter).
            • Controls: Latitude, altitude, ocean currents (Benguela, Canary), ITCZ movement.
            • Climate Change: Prolonged drought, desertification, food security challenges.
            """.trimIndent()
        )

        addNote(
            "Unit 3 - Natural Resource Base of Africa",
            "Drainage, Soils, Minerals, Vegetation, and Wildlife (Sections 3.1 - 3.4)",
            """
            • Major Rivers: Nile (longest), Congo (highest volume), Niger, Zambezi, Orange. Lakes: Victoria, Tanganyika, Malawi.
            • Mineral Wealth: Gold, diamonds, copper, bauxite, cobalt, petroleum, natural gas.
            • Vegetation & Wildlife: Tropical rainforests, savanna grasslands, desert scrub; rich wildlife reserves and national parks.
            """.trimIndent()
        )

        addNote(
            "Unit 4 - Population of Africa",
            "Demographic Structure and Distribution (Sections 4.1 - 4.5)",
            """
            • Demographic Trends: High birth rate, high youth dependency ratio, rapid growth.
            • Distribution: Uneven; dense along river valleys, coasts, and fertile highlands; sparse in deserts and dense rainforests.
            • Urbanization: Rapid rural-to-urban migration leading to urban growth challenges.
            """.trimIndent()
        )

        addNote(
            "Unit 5 - Major Economic and Cultural Activities of Africa",
            "Economic Sectors, Agenda 2063, and Diversity (Sections 5.1 - 5.6)",
            """
            • Economic Structure: Primary sector (agriculture, mining, forestry) dominates employment; secondary (industry/manufacturing) and tertiary (services) expanding.
            • Agenda 2063: AU master plan for structural transformation, integration, and sustainable growth.
            • Cultural Diversity: High linguistic and religious diversity across African sub-regions.
            """.trimIndent()
        )

        addNote(
            "Unit 6 - Human – Natural Environment Interactions",
            "Human Impact, Sustainability, and Indigenous Knowledge (Sections 6.1 - 6.3)",
            """
            • Environmental Modification: Deforestation, soil degradation, overgrazing, pollution.
            • Indigenous Knowledge: Traditional conservation, customary land management systems.
            """.trimIndent()
        )

        addNote(
            "Unit 7 - Geographic Issues and Public Concerns in Africa",
            "Urbanization, Migration, and Coastal Pollution (Sections 7.1 - 7.3)",
            """
            • Unplanned Urbanization: Slums, infrastructure deficit, housing shortages.
            • Migration: Push/pull factors, brain drain, regional refugee dynamics.
            • Coastal & Marine Pollution: Industrial effluents, plastic waste, oil spills threatening coastal ecosystems.
            """.trimIndent()
        )

        addNote(
            "Unit 8 - Geospatial Information and Data Processing",
            "GIS, Remote Sensing, and Data Analysis (Sections 8.1 - 8.5)",
            """
            • Geospatial Data: Primary (field survey) and Secondary (census, maps, satellite imagery).
            • GIS & Remote Sensing: Spatial analysis, layering, digital mapping, satellite imagery interpretation.
            • Statistical Graphics: Bar graphs, pie charts, line graphs, climate graphs (climographs).
            """.trimIndent()
        )

        return notesList
    }
}
