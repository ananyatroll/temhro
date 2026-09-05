package com.example.data

object Grade9ChemistryNotes {

    fun getGrade9ChemistryNotes(): List<SubjectNote> {
        val notesList = mutableListOf<SubjectNote>()
        val subId = "chemistry"
        var idx = 1

        fun addNote(unit: String, title: String, content: String) {
            notesList.add(
                SubjectNote(
                    id = "g9_chem_note_${idx}",
                    subjectId = subId,
                    unit = unit,
                    title = title,
                    content = content,
                    gradeLevel = "Grade 9"
                )
            )
            idx++
        }

        // Unit 1
        addNote(
            "Unit 1 - Chemistry and Its Importance",
            "Definition and Scope of Chemistry (Section 1.1)",
            """
            • Chemistry is defined as a branch of natural science that studies the composition, structure, properties, and changes of matter, as well as energy changes (Chapter 1, Section 1.1, Page 2).
            • Scope: Organic chemistry (carbon compounds), inorganic chemistry (minerals), physical chemistry (energy/rates), analytical chemistry (composition analysis), biochemistry (living processes) (Chapter 1, Section 1.1, Pages 2–5).
            • Matter is anything that has mass and occupies space, existing as solid, liquid, or gas (Chapter 1, Section 1.1, Page 2).
            """.trimIndent()
        )

        addNote(
            "Unit 1 - Chemistry and Its Importance",
            "Relationship Between Chemistry and Other Natural Sciences (Section 1.2)",
            """
            • Chemistry is the "central science" connecting physics, biology, geology, environmental science (Chapter 1, Section 1.2, Page 6).
            • Relation with Biology: Biochemistry explains metabolic pathways, cellular respiration, photosynthesis, genetics (DNA/RNA) (Chapter 1, Section 1.2, Page 6).
            • Relation with Physics: Physical chemistry overlaps in studying thermodynamics, atomic structure, energy transfers (Chapter 1, Section 1.2, Page 7).
            • Relation with Geology/Agriculture: Geochemistry studies mineral compositions; agricultural chemistry involves fertilizers and soil nutrients (Chapter 1, Section 1.2, Pages 7–8).
            """.trimIndent()
        )

        addNote(
            "Unit 1 - Chemistry and Its Importance",
            "The Role Chemistry Plays in Production and in Society (Section 1.3)",
            """
            • Agriculture: Provides fertilizers (NPK), pesticides, herbicides to enhance food production (Chapter 1, Section 1.3, Page 8).
            • Medicine and Pharmacy: Pharmaceuticals, antibiotics, anesthetics, diagnostic reagents (Chapter 1, Section 1.3, Pages 9–10).
            • Building Construction: Production of cement, glass, ceramics, polymers, metals, and alloys (Chapter 1, Section 1.3, Pages 10–11).
            """.trimIndent()
        )

        addNote(
            "Unit 1 - Chemistry and Its Importance",
            "Some Common Chemical Industries in Ethiopia (Section 1.4)",
            """
            • Ethiopian chemical industries play a vital role in national economic development producing consumer and industrial goods (Chapter 1, Section 1.4, Page 12).
            • Examples: Sugar factories (producing sugar and ethanol), cement factories (Mugher, Messebo, Derba), fertilizer plants, textile/tanneries, pharmaceutical enterprises (Chapter 1, Section 1.4, Pages 12–14).
            """.trimIndent()
        )

        // Unit 2
        addNote(
            "Unit 2 - Measurements and Scientific Methods",
            "Measurements and Units in Chemistry (Section 2.1)",
            """
            • Standard SI Units: meter (m) length, kilogram (kg) mass, second (s) time, Kelvin (K) temperature, mole (mol) amount of substance (Chapter 2, Section 2.1, Pages 19–25).
            • Derived units: Volume (m³ or liters L), density (ρ = m/V, g/cm³ or kg/m³), concentration (Chapter 2, Section 2.1, Pages 26–34).
            • Temperature conversions: K = °C + 273.15; °C = (5/9)(°F - 32) (Chapter 2, Section 2.1, Pages 28–30).
            """.trimIndent()
        )

        addNote(
            "Unit 2 - Measurements and Scientific Methods",
            "Chemistry as Experimental Science (Section 2.2)",
            """
            • Scientific method: Observation, hypothesis formulation, experimentation, data collection, analysis, conclusion (Chapter 2, Section 2.2, Pages 35–38).
            • Experimental error analysis: Accuracy (closeness to true value) vs. Precision (reproducibility); Percentage Error = (|Experimental - Accepted| / Accepted) × 100% (Chapter 2, Section 2.2, Pages 39–46).
            """.trimIndent()
        )

        // Unit 3
        addNote(
            "Unit 3 - Structure of the Atom",
            "Historical Development of Atomic Theories and Fundamental Laws (Sections 3.1 & 3.2)",
            """
            • Atomic models: Democritus ("atomos"), Dalton (solid sphere), Thomson (plum pudding), Rutherford (nuclear/alpha scattering), Bohr (planetary), Quantum Mechanical model (Chapter 3, Section 3.1, Pages 51–55).
            • Fundamental Laws:
              1. Law of Conservation of Mass: Mass is neither created nor destroyed (Lavoisier).
              2. Law of Definite Proportions: Chemical compounds contain elements in fixed mass ratios (Proust).
              3. Law of Multiple Proportions: Elements combine in ratios of small whole numbers (Dalton) (Chapter 3, Section 3.2, Pages 56–63).
            """.trimIndent()
        )

        addNote(
            "Unit 3 - Structure of the Atom",
            "Discoveries of Subatomic Particles and Composition of Atoms (Sections 3.4 & 3.5)",
            """
            • Electron (Thomson cathode rays / Millikan oil drop), Proton (canal rays), Neutron (Chadwick alpha bombardment) (Chapter 3, Section 3.4, Pages 69–84).
            • Notation: Atomic Number Z (protons), Mass Number A (protons + neutrons); Isotopes have same Z but different A (Chapter 3, Section 3.5, Pages 85–100).
            """.trimIndent()
        )

        // Unit 4
        addNote(
            "Unit 4 - Periodic Classification of Elements",
            "Historical Development and the Modern Periodic Table (Sections 4.1 - 4.3)",
            """
            • Early attempts: Döbereiner's Triads, Newlands' Octaves, Mendeleev's periodic table (arranged by atomic mass) (Chapter 4, Sections 4.1–4.2, Pages 110–115).
            • Modern Periodic Table: Moseley arranged elements by increasing atomic number Z (Chapter 4, Section 4.3, Page 116).
            • Organization: Periods (horizontal rows 1–7), Groups/Families (vertical columns 1–18, e.g. Group 1 Alkali metals, Group 17 Halogens, Group 18 Noble gases) (Chapter 4, Section 4.3, Pages 117–124).
            """.trimIndent()
        )

        addNote(
            "Unit 4 - Periodic Classification of Elements",
            "Major Periodic Trends (Section 4.4)",
            """
            • Atomic Radius: Increases down a group, decreases across a period (increasing nuclear charge) (Chapter 4, Section 4.4, Pages 125–128).
            • Ionization Energy: Energy to remove electron; decreases down group, increases across period (Chapter 4, Section 4.4, Pages 129–131).
            • Electron Affinity: Energy change when adding electron; increases across period (exothermic) (Chapter 4, Section 4.4, Pages 132–134).
            • Electronegativity: Ability to attract shared electrons; decreases down group, increases across period (Fluorine highest) (Chapter 4, Section 4.4, Pages 135–136).
            """.trimIndent()
        )

        // Unit 5
        addNote(
            "Unit 5 - Chemical Bonding",
            "Chemical Bonding and Ionic Bonding (Sections 5.1 & 5.2)",
            """
            • Chemical bonding driven by achieving stable octet valence configuration (8 electrons) (Chapter 5, Section 5.1, Pages 141–142).
            • Ionic Bonding: Complete transfer of electrons from metal to non-metal, forming oppositely charged ions held by electrostatic attraction (Chapter 5, Section 5.2, Pages 143–153).
            • Properties: High melting/boiling points, crystalline lattice, conducts electricity in molten/aqueous state, soluble in water (Chapter 5, Section 5.2, Pages 150–153).
            """.trimIndent()
        )

        addNote(
            "Unit 5 - Chemical Bonding",
            "Covalent Bonding and Metallic Bonding (Sections 5.3 & 5.4)",
            """
            • Covalent Bonding: Mutual sharing of electron pairs between non-metal atoms (single, double, triple bonds) (Chapter 5, Section 5.3, Pages 154–166).
            • Covalent properties: Lower melting/boiling points, poor electrical conductivity, exist as gases, liquids, soft solids (Chapter 5, Section 5.3, Pages 162–166).
            • Metallic Bonding: "Sea of delocalized electrons" surrounding metal cations, explaining conductivity, malleability, ductility, luster (Chapter 5, Section 5.4, Pages 167–170).
            """.trimIndent()
        )

        return notesList
    }
}
