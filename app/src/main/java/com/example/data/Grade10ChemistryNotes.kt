package com.example.data

object Grade10ChemistryNotes {

    fun getGrade10ChemistryNotes(): List<SubjectNote> {
        val notesList = mutableListOf<SubjectNote>()
        val subId = "chemistry"
        var idx = 1

        fun addNote(unit: String, title: String, content: String) {
            notesList.add(
                SubjectNote(
                    id = "g10_chem_note_${idx}",
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
            "Unit 1 - Chemical Reactions and Stoichiometry",
            "Chemical Equations, Redox, and Stoichiometric Calculations (Sections 1.1 - 1.6)",
            """
            • Chemical Reactions & Equations: Reactants ➔ Products with state symbols. Law of Conservation of Mass requires balanced equations.
            • Types of Reactions: Combination (A+B ➔ AB), Decomposition (AB ➔ A+B), Single Displacement (A+BC ➔ AC+B), Double Displacement (AB+CD ➔ AD+CB).
            • Redox Reactions: Oxidation (loss of electrons / increase in oxidation number) vs. Reduction (gain of electrons / decrease in oxidation number).
            • Stoichiometry: Mole concept (6.022 × 10²³ particles), Molar Mass, Empirical vs. Molecular formulas.
            • Limiting Reactant & Yield: Percentage Yield = (Actual Yield / Theoretical Yield) × 100%.
            """.trimIndent()
        )

        addNote(
            "Unit 2 - Solutions",
            "Mixtures, Solution Process, Concentration, and Titrations (Sections 2.1 - 2.7)",
            """
            • Solution Types: Homogeneous mixture of solute in solvent. Solvation follows 'like dissolves like'.
            • Solubility: Maximum solute dissolved at given temperature; affected by temperature and pressure (Henry's Law for gases).
            • Concentration Units:
              - Molarity (M) = moles of solute / Liters of solution
              - Molality (m) = moles of solute / kg of solvent
              - Mass/Volume Percentage, Mole Fraction
            • Dilution Formula: M₁V₁ = M₂V₂.
            """.trimIndent()
        )

        addNote(
            "Unit 3 - Important Inorganic Compounds",
            "Oxides, Acids, Bases, and Salts (Sections 3.1 - 3.5)",
            """
            • Oxides: Basic oxides (metal + O₂), Acidic oxides (nonmetal + O₂), Amphoteric oxides (react with both acids & bases like Al₂O₃, ZnO), Neutral oxides (NO, CO).
            • Acids & Bases: Arrhenius, Brønsted-Lowry (proton donor/acceptor), Lewis (electron pair acceptor/donor) theories. Strong vs. weak electrolytes.
            • Salts: Formed by neutralization (acid + base ➔ salt + water). Hydrolysis determines acidic, basic, or neutral pH of salt solutions.
            """.trimIndent()
        )

        addNote(
            "Unit 4 - Energy Changes and Electrochemistry",
            "Thermochemistry, Galvanic Cells, and Electrolysis (Sections 4.1 - 4.4)",
            """
            • Thermochemistry: Exothermic (ΔH < 0, heat released) vs. Endothermic (ΔH > 0, heat absorbed) reactions.
            • Galvanic (Voltaic) Cells: Spontaneous redox reaction generates electricity (e.g. Daniell cell, Zn/Cu electrodes, salt bridge).
            • Electrolytic Cells: Non-spontaneous reaction driven by external DC power; applications in electroplating, aluminum extraction (Hall-Héroult), chlor-alkali.
            """.trimIndent()
        )

        addNote(
            "Unit 5 - Metals and Nonmetals",
            "Properties, Metallurgy, and Nonmetal Production (Sections 5.1 - 5.3)",
            """
            • Metals: Luster, high electrical/thermal conductivity, malleability, ductility. Extracted via pyrometallurgy (blast furnace for iron), electrometallurgy.
            • Nonmetals: Production and properties of sulfur (Contact process for H₂SO₄), nitrogen/ammonia (Haber process), chlorine.
            """.trimIndent()
        )

        addNote(
            "Unit 6 - Hydrocarbons and Their Natural Sources",
            "Alkanes, Alkenes, Alkynes, Aromatics, and Petroleum (Sections 6.1 - 6.5)",
            """
            • Hydrocarbons: Organic compounds containing only carbon and hydrogen.
            • Alkanes (C_n H_{2n+2}): Saturated, single bonds, substitution reactions.
            • Alkenes (C_n H_{2n}) & Alkynes (C_n H_{2n-2}): Unsaturated, double/triple bonds, addition reactions.
            • Aromatics (Benzene C₆H₆): Delocalized π-electron ring, resonance stability.
            • Natural Sources: Crude oil fractional distillation, cracking processes, natural gas.
            """.trimIndent()
        )

        return notesList
    }
}
