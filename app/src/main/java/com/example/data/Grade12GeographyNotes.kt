package com.example.data

object Grade12GeographyNotes {

    fun getGrade12GeographyNotes(): List<SubjectNote> {
        val notesList = mutableListOf<SubjectNote>()
        val subId = "euee_soc_geo"
        var idx = 1

        fun addNote(unit: String, title: String, content: String) {
            notesList.add(
                SubjectNote(
                    id = "g12_geo_note_${idx}",
                    subjectId = subId,
                    unit = unit,
                    title = title,
                    content = content,
                    gradeLevel = "Grade 12"
                )
            )
            idx++
        }

        // Unit 1
        addNote(
            "Unit 1 - Major Geological Processes Associated with Plate Tectonics",
            "Continental Drift Theory / Major Geological Processes",
            """
            • Continental Drift Theory: Presupposes that earth's continents have moved over geologic time relative to each other, appearing to have "drifted" across the ocean bed.
            • Pangaea: A single supercontinent that existed some 350 million years ago during the Carboniferous period.
            • Laurasia and Gondwanaland: Pangaea broke into Laurasia (northern part) and Gondwanaland (southern part) during the Triassic period.
            • Intrusive Landforms: Formed when magma cools and solidifies within the crust. Examples include batholith (large dome-shaped), sill (horizontal sheet), and dike (vertical wall-like).
            • Chemical Weathering: Water is the primary agent, especially when it dissolves O₂ and CO₂. Carbonation is the weathering of limestone by rainwater containing dissolved CO₂.
            • Karst Topography: Features like stalactites (hanging from ceiling), stalagmites (building from floor), and pillars formed in limestone caves like Sof Omar.
            • River Landforms: Meanders (winding curves), Ox-bow lakes (U-shaped cut-offs), Flood plains (sediment deposits), and Deltas (triangular land at mouth).
            • Desert Landforms: Sand dunes (hills of sand), Barkhans (crescent-shaped), and Loess (fertile wind-blown deposits).
            • Mass Wasting: Downslope movement of rock and soil under gravity, influenced by water, slope angle, and human activities.
            """.trimIndent()
        )

        // Unit 2
        addNote(
            "Unit 2 - Climate Change",
            "Basic Concepts of Climate Change / Consequences of Climate Change",
            """
            • Evidence: Atmospheric concentrations of greenhouse gases like CO₂, methane (CH₄), and nitrous oxide (N₂O) have increased over the last few centuries.
            • Milankovitch Theory: Attributes climatic changes to variations in Earth's orbit, including eccentricity (orbit shape), precession (wobble), and obliquity (tilt).
            • Natural Causes: Volcanic eruptions rich in sulfur and fluctuations in solar output.
            • Human Induced: Steady increase in CO₂ due to burning fossil fuels and deforestation. Trees store CO₂; when burned, it returns to the atmosphere.
            • Global average temperatures have risen by about 1°C since the late 19th century.
            • Projected impacts: Substantial warming, increased water vapor, extreme precipitation, worsening droughts, melting sea ice, and rising sea levels.
            """.trimIndent()
        )

        // Unit 3
        addNote(
            "Unit 3 - Management of Conflict over Resources",
            "Resource Use Policies and Related Conflicts / Governance of Natural Resources",
            """
            • Drivers of Conflict: Competition over scarce renewable resources, poor governance, and transboundary dynamics.
            • Resource Scarcity Types: Demand-induced (population growth), Supply-induced (degradation), and Structurally induced (unequal access).
            • Transboundary Conflicts: Occur when infrastructure (like dams) or pollution in one country negatively impacts another (e.g., Nile/GERD issues).
            • Livelihood Support: Includes seasonal changes in farming, augmenting water supply, and emergency food distribution during failures.
            • Principles: Legal changes to clarify rights, equitable access as a priority, and mandatory environmental/social impact assessments.
            """.trimIndent()
        )

        // Unit 4
        addNote(
            "Unit 4 - Population Policies, Programs and the Environment",
            "Theories on Population Growth and Development",
            """
            • Malthusian Theory: Population grows geometrically while food production increases linearly, leading to a "Malthusian crisis" of poverty and hunger.
            • Malthusian Checks: Preventive checks (moral restraint/late marriage) and Positive checks (disease, war, famine).
            • Boserup's Hypothesis: Population growth drives agricultural innovation and intensification.
            • Ultimate Resource Theory: Julian Simon argued that people are the ultimate resource, capable of innovating to sustain growth.
            """.trimIndent()
        )

        // Unit 5
        addNote(
            "Unit 5 - Challenges of Economic Development",
            "Faces of Poverty / Globalization / Corruption",
            """
            • Absolute Poverty: Comparing households based on a fixed income level regardless of economic growth.
            • Relative Poverty: Receiving 50% less than average household income; people lack internet, safe homes, or education enjoyed by others.
            • Persistent Poverty: Households receiving low income for 2 out of every 3 years.
            • Globalization: The process of the world becoming more linked through commerce and cultural interaction, led by multinational corporations.
            • Trade Imbalance: Occurs when a country borrows to pay for imports (current account deficit) or when companies outsource manufacturing.
            • Corruption Perceptions Index (CPI): A composite indicator by Transparency International scoring countries from 0 (most corrupt) to 100 (least corrupt).
            • Remedial Measures: Strong leadership example, credible investigations, involving citizens, and a responsible press.
            """.trimIndent()
        )

        // Unit 6
        addNote(
            "Unit 6 - Solutions to Environmental and Sustainability Problems",
            "Environmental Problems / Environmentally Friendly Indigenous Practices",
            """
            • Environment Formula: I = f(PAT), where I is impact, P is population, A is affluence/consumption, and T is technology.
            • Environmental Movements: Broad networks like those led by Greta Thunberg; types include free market environmentalism, preservation/conservation, and popular environmentalism.
            • Indigenous Knowledge (IK): Local, orally-transmitted, holistic knowledge developed through trial and error.
            • Gedeo Culture: Respect for "songo" sacred trees (Dhadacha) and "seera" traditional rules to protect forests and immature plants.
            • Gumuz Culture: Belief that resources are a gift from the deity "Yamba" and must be managed sustainably through shifting cultivation.
            """.trimIndent()
        )

        // Unit 7
        addNote(
            "Unit 7 - Contemporary Global Geographic Issues and Public Concerns",
            "Desertification / Drought",
            """
            • Desertification: Land degradation in drylands (arid, semi-arid, sub-humid areas).
            • Impact: Affects about 25% of total land surface and 2/3 of countries; drylands cover 46.2% of global land area.
            • Drought: Absence of regular rainfall over an extended period.
            • Types: Meteorological (lack of rain), Hydrological (impact on water bodies), and Agricultural (moisture deficit affecting crops).
            • Indices: Palmer Drought Severity Index (PDSI), Crop Moisture Index (CMI), and Standardized Precipitation Index (SPI).
            • Socio-economic Impacts: Reduced income for farmers, increased food prices, and migration for relief food.
            """.trimIndent()
        )

        // Unit 8
        addNote(
            "Unit 8 - Geographical Enquiry and Map Making",
            "Fundamentals of Research in Geography",
            """
            • Research Approaches: Quantitative (numerical data), Qualitative (descriptive/meaning), and Mixed (combining both).
            • GIS Map Classification: Natural breaks (Jenks) and Equal interval mapping are methods used to categorize data for visualization.
            """.trimIndent()
        )

        return notesList
    }
}