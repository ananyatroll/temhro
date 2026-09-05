package com.example.data

object Grade12BiologyNotes {

    fun getGrade12BiologyNotes(): List<SubjectNote> {
        val notesList = mutableListOf<SubjectNote>()
        val subId = "euee_nat_bio"
        var idx = 1

        fun addNote(unit: String, title: String, content: String) {
            notesList.add(
                SubjectNote(
                    id = "g12_bio_note_${idx}",
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
            "Unit 1 - Application of Biology",
            "Application in Conservation of Natural Resources / Food and Nutrition Security",
            """
            • Biology helps in understanding living organisms from bacteria to blue whales and determines the source of diseases and pests.
            • Conservation of natural resources is critical for the survival of people and involves managing resources like mother cells, genetic engineering, and global warming.
            • Natural resources are classified as renewable (sun, wind, water, biomass) or non-renewable (metals like gold/iron, fossil fuels like oil/gas/coal).
            • Sustainable management involves using renewable energy sources to mitigate negative impacts like climate change caused by fossil fuel use.
            • Food security is a state where all people have physical, social, and economic access to sufficient, safe, and nutritious food at all times.
            • Food insecurity is often rooted in poverty and leads to prolonged undernourishment, which stunts growth and increases susceptibility to illness.
            """.trimIndent()
        )

        // Unit 2
        addNote(
            "Unit 2 - Microorganisms",
            "Eubacteria / Archaea / Fungi / Protozoa / Viruses / Normal Microbiota and Disease",
            """
            • Eubacteria (true bacteria) are prokaryotic unicellular organisms with no nuclear membrane, mitochondria, or Golgi bodies. Nutritional Types: Photolithoautotrophs (use light and CO₂), Chemoorganoheterotrophs (use organic compounds), or Chemolithoautotrophs (oxidize inorganic compounds). Reproduction: Bacteria primarily reproduce asexually through binary fission.
            • Archaea are distinct from bacteria in their cell envelope structure and often live in extreme environments. Types: Hyperthermophiles (high temperature), Psychrophiles (cold), Halophiles (saline), and Acidophiles (low pH).
            • Fungi are used in traditional Ethiopian food/beverages (Enjera, Tela, Tej) and in producing antibiotics like penicillin and griseofulvin. Fungi can also be harmful, causing over 5,000 species of plant diseases and various animal/human infections.
            • Protozoa are heterotrophic protists that obtain food through absorption, ingestion (using cilia), or engulfing (phagocytosis). Common diseases: malaria, amoebiasis (Entamoeba histolytica), and giardiasis (Giardia lamblia).
            • Viruses are acellular microorganisms that require a host cell to replicate. Structure: Core (DNA or RNA) and a protective protein coat called a capsid. Some have an additional lipoprotein envelope. Lytic Cycle: Virulent cycle where the virus multiplies and causes the cell to burst (lysis). Lysogenic Cycle: Temperate cycle where viral DNA (prophage) integrates into the host genome without immediate lysis.
            • Normal microbiota (flora) are microorganisms that live on or in a host without causing disease, providing protection by excluding pathogens. Koch's Postulates: A series of steps used to prove that a specific microorganism causes a specific disease.
            """.trimIndent()
        )

        // Unit 3
        addNote(
            "Unit 3 - Energy Transformation",
            "Cellular Metabolism / Photosynthesis / Cellular Respiration",
            """
            • Metabolism is the sum of all chemical reactions in a cell, divided into anabolism (constructive, energy-absorbing) and catabolism (destructive, energy-releasing). Anabolism: Simpler substances form complex macromolecules (e.g., photosynthesis). Catabolism: Complex molecules are broken down (e.g., cellular respiration).
            • Photosynthesis: The process of converting sunlight into chemical energy stored in sugars. Pigments: Chlorophyll "a" (found in all plants/algae), Chlorophyll "b" (plants/green algae), and accessory pigments like carotenoids. Stages: Light-dependent reactions (in grana, produce ATP/NADPH) and Light-independent reactions (Calvin Cycle, in stroma, produce carbohydrates).
            • Aerobic Respiration: Takes place in mitochondria, requires oxygen, and is highly efficient (36-38 ATP per glucose). Glycolysis: Occurs in the cytosol; one glucose molecule splits into two pyruvates. Anaerobic Respiration: Occurs without oxygen; pyruvate converts to lactic acid (animals) or ethanol (yeast/plants).
            """.trimIndent()
        )

        // Unit 4
        addNote(
            "Unit 4 - Evolution",
            "Theories and Evidence of Evolution / Human Evolution / Mutation and Genetic Drift",
            """
            • Biochemical Theory: Suggests life originated from biochemical reactions producing organic molecules (Oparin and Haldane).
            • Lamarckism: Theory of "use and disuse" and inheritance of acquired traits (e.g., giraffe's neck).
            • Dating Techniques: Radiocarbon dating (C14 to N) and Potassium-argon dating (K40 to A40) use half-lives to determine fossil age.
            • Reproductive Isolation: Prezygotic barriers (habitat, temporal, behavioral, mechanical, gametic) and Postzygotic barriers (hybrid inviability, sterility).
            • Human Evolution: Lucy (Australopithecus afarensis): Discovered in 1974 at Hadar, Ethiopia; 3.2 million years old; evidence that bipedalism evolved before large brains. Ardi: Another significant hominin fossil found in the Afar desert, Ethiopia.
            • Frameshift Mutation: Caused by base addition or deletion, altering the reading frame of mRNA.
            • Hardy-Weinberg Equilibrium: Conditions include no mutations, random mating, no natural selection, large population, and no gene flow.
            """.trimIndent()
        )

        // Unit 5
        addNote(
            "Unit 5 - Human Body System",
            "The Nervous System / Drug Abuse / Sense Organs / Homeostasis and the Kidney",
            """
            • Neurons: Functional units consisting of dendrites (receive signals), axon (carries impulses), and cell body (contains nucleus). Types of Neurons: Sensory (afferent), Motor (efferent), and Interneurons (association).
            • Action Potential: Rapid change in membrane potential (+40mV) involving depolarization (Na+ influx) and repolarization (K+ efflux).
            • Reflex Arc: Five essential parts: Receptor, Sensory neuron, CNS (synapse), Motor neuron, and Effector.
            • Substance Use Disorder (SUD): A disease affecting brain and behavior; nicotine in cigarettes releases dopamine, leading to addiction. Alcohol Use Disorder (AUD): Chronic relapsing disorder involving compulsive drinking and negative emotional states.
            • Skin: Largest organ; layers include epidermis, dermis, and hypodermis; functions in protection, excretion, and temperature control.
            • Eye: Retina contains rods (dim light, movement) and cones (bright light, color). Optic disc is where the optic nerve enters.
            • Ear: Regions include outer ear (pinna, ear canal), middle ear (malleus, incus, stapes ossicles), and inner ear (cochlea, semicircular canals).
            • Homeostasis: Maintenance of a stable internal environment (e.g., blood sugar, temperature, water content).
            • ADH (Antidiuretic Hormone): Produced in the brain; increases permeability of kidney tubules to reabsorb more water, forming concentrated urine.
            """.trimIndent()
        )

        // Unit 6
        addNote(
            "Unit 6 - Climate Change",
            "Causes and Effects of Climate Change",
            """
            • Greenhouse Gases (GHGs): Gases like CO₂, Methane (CH₄), and Nitrous Oxide (N₂O) that trap heat in the atmosphere.
            • CO₂ Sources: Fossil fuel combustion and deforestation; responsible for two-thirds of the energy imbalance causing warming.
            • Effects: Melting ice sheets and glaciers lead to rising sea levels, flooding, and erosion of coastal areas.
            • Disaster Risk Management: Includes threat recognition, risk analysis, and precautions for events like earthquakes (e.g., taking cover under sturdy furniture).
            """.trimIndent()
        )

        return notesList
    }
}