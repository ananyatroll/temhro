package com.example.data

object Grade10BiologyNotes {

    fun getGrade10BiologyNotes(): List<SubjectNote> {
        val notesList = mutableListOf<SubjectNote>()
        val subId = "biology"
        var idx = 1

        fun addNote(unit: String, title: String, content: String) {
            notesList.add(
                SubjectNote(
                    id = "g10_bio_note_${idx}",
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
            "Unit 1 - Applied Biology and Biotechnology",
            "Scientific Method and Biotechnology Applications (Sections 1.1 - 1.3)",
            """
            • Pure vs. Applied Biology: Pure biology discovers mechanisms (botany, zoology, cytology); Applied biology uses knowledge for practical solutions (biotechnology, pharmacology, agriculture).
            • Scientific Method: Observation ➔ Hypothesis ➔ Experimentation ➔ Data Collection & Analysis ➔ Conclusion.
            • Biotechnology: Fermentation in food/beverage industry, recombinant DNA, insulin production, gene therapy, bio-fuels, GMOs.
            """.trimIndent()
        )

        addNote(
            "Unit 2 - Human Body Systems and Health",
            "Organ Systems, Nutrition, and Immunity (Sections 2.1 - 2.4)",
            """
            • Homeostasis: Coordination of organ systems (digestive, respiratory, circulatory, excretory, nervous, endocrine, immune).
            • Human Nutrition: Carbohydrates, proteins, lipids, vitamins, minerals, water; enzymatic breakdown in alimentary canal.
            • Pathogens & Disease: Communicable (bacteria, viruses, fungi, protozoa) vs. Non-communicable diseases.
            • Immune System: Innate immunity (physical barriers, phagocytes) vs. Adaptive immunity (B & T lymphocytes, antibodies, vaccines).
            """.trimIndent()
        )

        addNote(
            "Unit 3 - Heredity and Genetics",
            "Mendelian Genetics, DNA, and Protein Synthesis (Sections 3.1 - 3.4)",
            """
            • Mendelian Inheritance: Law of Segregation and Law of Independent Assortment; monohybrid and dihybrid Punnett square crosses.
            • DNA & RNA: Double helix structure (A-T, C-G base pairing). RNA contains Uracil instead of Thymine.
            • Protein Synthesis:
              - Transcription: mRNA synthesized from DNA template in nucleus.
              - Translation: Ribosome reads mRNA codons; tRNA brings corresponding amino acids.
            • Mutations: Point and chromosomal mutations as sources of variation.
            """.trimIndent()
        )

        addNote(
            "Unit 4 - Evolution and Biodiversity",
            "Darwinism, Natural Selection, and Conservation (Sections 4.1 - 4.4)",
            """
            • Evolutionary Theories: Lamarck (acquired characteristics) vs. Darwin & Wallace (natural selection: variation, overproduction, differential survival).
            • Evidence for Evolution: Fossils, homologous structures (common origin), analogous structures, embryology, molecular DNA comparison.
            • Biodiversity & Conservation: Threats (habitat loss, overexploitation, climate change) and protected areas / management.
            """.trimIndent()
        )

        addNote(
            "Unit 5 - Ecology and Environmental Sustainability",
            "Energy Flow, Biogeochemical Cycles, and Sustainability (Sections 5.1 - 5.3)",
            """
            • Ecosystem Dynamics: Trophic levels (producers, consumers, decomposers), food webs, energy pyramids.
            • Biogeochemical Cycles: Carbon cycle, Nitrogen cycle, Water cycle.
            • Sustainability: Deforestation, soil erosion, climate change, reforestation, and ecological preservation.
            """.trimIndent()
        )

        return notesList
    }
}
