package com.example.data

object Grade9GeographyNotes {

    fun getGrade9GeographyNotes(): List<SubjectNote> {
        val notesList = mutableListOf<SubjectNote>()
        val subId = "euee_soc_geography"
        var idx = 1

        fun addNote(unit: String, title: String, content: String) {
            notesList.add(
                SubjectNote(
                    id = "g9_geog_note_${idx}",
                    subjectId = subId,
                    unit = unit,
                    title = title,
                    content = content,
                    gradeLevel = "Grade 9"
                )
            )
            idx++
        }

        addNote(
            "Unit 1 - Geological History and Topography of Ethiopia",
            "Meaning, Scope, and Branches of Geography (Section 1.1)",
            """
            • Geography is defined as the scientific study of the Earth's surface, its physical features, climate, resources, and human interactions, originating from the Greek word "geographia" (geo = Earth, graphien = to write).
            • Scope: Encompasses both physical environment (landforms, climate, vegetation, water bodies) and human environment (population, economic activities, settlements, cultural landscapes).
            • Branches: Physical Geography (geomorphology, climatology, hydrology, biogeography) and Human Geography (population geography, economic geography, urban geography, political geography).
            """.trimIndent()
        )

        addNote(
            "Unit 1 - Geological History and Topography of Ethiopia",
            "Location, Size, and Shape of Ethiopia (Section 1.2)",
            """
            • Absolute Location: Extends between 3°N to 15°N latitude and 33°E to 48°E longitude.
            • Relative Location: Bounded by Eritrea to N/NE, Djibouti & Somalia to E/SE, Kenya to S, Sudan & South Sudan to W/NW.
            • Size & Shape: Area approx. 1,100,000 km², 10th largest in Africa. Compact shape with peripheral projections.
            """.trimIndent()
        )

        addNote(
            "Unit 1 - Geological History and Topography of Ethiopia",
            "Geological History of Ethiopia (Section 1.3)",
            """
            • Precambrian Era: Formation of basement complex (earliest crystalline rocks, gneisses, schists).
            • Mesozoic Era: Ingress and regression of the sea leading to sedimentary layers (Adigrat sandstone, limestone, gypsum).
            • Cenozoic Era: Massive volcanic activity (trap series lava flows), Rift Valley faulting, and quaternary depositions.
            """.trimIndent()
        )

        addNote(
            "Unit 2 - Weather and Climate of Ethiopia",
            "Weather, Climate, and Controls (Section 2.1)",
            """
            • Weather is short-term atmospheric state; Climate is long-term average over 30+ years.
            • Controls in Ethiopia: Latitude, altitude (creating Dega, Weyna Dega, Kolla, Wurch zones), distance from sea, and ITCZ migration.
            """.trimIndent()
        )

        addNote(
            "Unit 2 - Weather and Climate of Ethiopia",
            "Seasons and Rainfall Regions (Section 2.2)",
            """
            • Kiremt (June–August): Main summer rainy season.
            • Belg (February–May): Small spring rainy season.
            • Bega (October–January): Dry winter season.
            """.trimIndent()
        )

        addNote(
            "Unit 3 - Natural Resource Base of Ethiopia",
            "Water Resources, Soils, and Vegetation (Sections 3.1 - 3.3)",
            """
            • "Water Tower of East Africa": Abay (Blue Nile), Baro-Akobo, Omo-Gibe, Awash, Wabi-Shebelle, Genale, plus Rift Valley lakes (Tana, Abaya, Chamo, Ziway).
            • Soils: Nitosols, Vertisols (black clay), Cambisols, Regosols.
            • Vegetation & Wildlife: Endemic species like Gelada baboon, Ethiopian wolf, Mountain Nyala.
            """.trimIndent()
        )

        addNote(
            "Unit 4 - Population of Ethiopia",
            "Population Dynamics and Distribution (Sections 4.1 - 4.3)",
            """
            • Rapid growth with high birth rates and young demographic structure.
            • Uneven distribution: Dense in central/western highlands; sparse in lowlands.
            """.trimIndent()
        )

        addNote(
            "Unit 5 - Economic Activities in Ethiopia",
            "Agriculture, Industry, Trade, and Transport (Sections 5.1 - 5.5)",
            """
            • Agriculture: Backbone of economy, subsistence crop cultivation, pastoralism, and cash crops (coffee, oilseeds, khat).
            • Infrastructure: Road networks, Ethiopian railway, Ethiopian Airlines as regional hub.
            """.trimIndent()
        )

        return notesList
    }
}
