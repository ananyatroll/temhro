package com.example.data

object Grade9BiologyNotes {

    fun getGrade9BiologyNotes(): List<SubjectNote> {
        val notesList = mutableListOf<SubjectNote>()
        val subId = "biology"
        var idx = 1

        fun addNote(unit: String, title: String, content: String) {
            notesList.add(
                SubjectNote(
                    id = "g9_bio_note_${idx}",
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
            "Unit 1 - Introduction to Biology",
            "Definition of Biology (Section 1.1)",
            """
            • Biology is defined as the scientific study of life and living organisms, derived from two Greek words: "bios" (meaning life) and "logos" (meaning study) (Chapter 1, Section 1.1, Page 1).
            • Living things share common characteristics: composed of one or more cells, growth and development, reproduction, transmission of genetic information, requirement of energy, maintenance of constant internal conditions (homeostasis), and evolutionary adaptation (Chapter 1, Section 1.1, Page 1).
            • Relationship with other sciences: Biology intersects with chemistry (chemical bonding, organic molecules, photosynthesis) and physics (radiant energy conversion, biophysics) (Chapter 1, Section 1.1, Page 1–2).
            """.trimIndent()
        )

        addNote(
            "Unit 1 - Introduction to Biology",
            "Why Do We Study Biology? (Section 1.2)",
            """
            • Studying biology helps human beings understand how living things function, interact with their environment, and how biological processes apply to daily life (Chapter 1, Section 1.2, Page 2).
            • Practical applications include biotechnology (wine-making, bread baking, cheese production via fermentation by yeasts and bacteria), antibiotic production (penicillin from fungi), industrial chemical production, sewage disposal, and DNA forensic analysis (Chapter 1, Section 1.2, Page 2).
            • Medical and environmental applications: Developing treatments for AIDS, tuberculosis, and cancer, and addressing global environmental challenges like global warming and ecology (Chapter 1, Section 1.2, Page 2).
            """.trimIndent()
        )

        addNote(
            "Unit 1 - Introduction to Biology",
            "The Scientific Method (Section 1.3)",
            """
            • The scientific method is a systematic series of inquiry steps used by biologists to investigate the living world and form scientific theories (Chapter 1, Section 1.3, Pages 2–3).
            • Steps of the scientific method:
              1. Observation: Making careful direct or indirect observations leading to a problem/question.
              2. Asking Questions: Formulating inquiries based on observations.
              3. Forming a Hypothesis: Proposing a testable scientific explanation.
              4. Testing Hypothesis: Conducting reproducible experiments to support or reject.
              5. Making Conclusions: Evaluating experimental data against hypothesis.
              6. Communicating Findings: Publishing peer-reviewed papers (Chapter 1, Section 1.3, Pages 2–4).
            """.trimIndent()
        )

        addNote(
            "Unit 1 - Introduction to Biology",
            "Tools of a Biologist (Section 1.4)",
            """
            • Laboratory tools: Hand lens (convex lens providing enlarged images), glassware (beakers, test tubes, flasks), measuring cylinders, and bunsen burners (Chapter 1, Section 1.4.1, Pages 5–10).
            • Field tools: Pootters (collecting small insects), sweep nets (flying/grassland insects), plankton nets (aquatic microorganisms), pitfall traps (ground insects), and quadrat frames (plant distribution) (Chapter 1, Section 1.4.2, Pages 10–11).
            """.trimIndent()
        )

        addNote(
            "Unit 1 - Introduction to Biology",
            "The Light Microscope (Section 1.5)",
            """
            • Parts and functions of a compound light microscope: Eyepiece (ocular lens), objective lenses (low, medium, high power), stage, diaphragm, condenser, mirror/light source, coarse adjustment knob, and fine adjustment knob (Chapter 1, Section 1.5.1, Pages 11–13).
            • Total Magnification formula: Total Magnification = (Magnification of Ocular Lens) × (Magnification of Objective Lens) (Chapter 1, Section 1.5.1, Page 12).
            • Handling: Carrying with two hands (one on arm, one under base), starting with low power objective, focusing with adjustment knobs (Chapter 1, Section 1.5.2, Pages 13–14).
            """.trimIndent()
        )

        addNote(
            "Unit 1 - Introduction to Biology",
            "General Laboratory Safety Rules (Section 1.6)",
            """
            • Essential laboratory safety rules: Wearing lab coats and protective goggles, never tasting or smelling chemicals directly, handling glassware/sharp instruments with care, washing hands after experiments, knowing fire extinguisher and first aid kit locations (Chapter 1, Section 1.6, Pages 14–17).
            """.trimIndent()
        )

        // Unit 2
        addNote(
            "Unit 2 - Characteristics and Classification of Organisms",
            "Characteristics of Living Things (Section 2.1)",
            """
            • Living organisms share defining biological properties: cellular organization, metabolism, growth, response to stimuli (irritability), homeostasis, reproduction, and evolution (Chapter 2, Section 2.1, Pages 19–20).
            • Distinction between living biotic factors and non-living abiotic components in ecosystems (Chapter 2, Section 2.1, Page 20).
            """.trimIndent()
        )

        addNote(
            "Unit 2 - Characteristics and Classification of Organisms",
            "Taxonomy of Living Things (Section 2.2)",
            """
            • Taxonomy is the science of classification, naming, and grouping organisms based on shared characteristics and evolutionary relationships (Chapter 2, Section 2.2, Page 21).
            • Taxonomic hierarchies (from broad to specific): Domain, Kingdom, Phylum (or Division), Class, Order, Family, Genus, and Species (Chapter 2, Section 2.2.2, Pages 22–23).
            • Species is defined as a group of actually or potentially interbreeding natural populations reproductively isolated from other such groups (Chapter 2, Section 2.2.2, Page 23).
            """.trimIndent()
        )

        addNote(
            "Unit 2 - Characteristics and Classification of Organisms",
            "Relevance of Classification and Nomenclature (Sections 2.3 & 2.4)",
            """
            • Relevance of classification: Organizes biological diversity, facilitates international scientific communication, predicts evolutionary relationships (Chapter 2, Section 2.3, Page 24).
            • Linnaean System of Binomial Nomenclature: Established by Carl Linnaeus, assigning each species a two-part scientific name consisting of capitalized Genus name and lowercase species epithet, written in italics or underlined (e.g., Homo sapiens) (Chapter 2, Section 2.4, Page 25).
            """.trimIndent()
        )

        addNote(
            "Unit 2 - Characteristics and Classification of Organisms",
            "The Five-Kingdom System of Classification (Section 2.6)",
            """
            • Kingdom Monera: Prokaryotic, unicellular organisms lacking true membrane-bound nucleus (e.g., bacteria, blue-green algae) (Chapter 2, Section 2.6.1, Page 30).
            • Kingdom Protista: Eukaryotic, mostly unicellular organisms (e.g., Amoeba, Paramecium, Euglena) (Chapter 2, Section 2.6.2, Pages 31–32).
            • Kingdom Fungi: Eukaryotic, heterotrophic spore-bearing organisms with chitin cell walls (e.g., yeasts, molds, mushrooms) (Chapter 2, Section 2.6.3, Pages 33–34).
            • Kingdom Plantae: Eukaryotic, multicellular autotrophic organisms performing photosynthesis with cellulose cell walls (Chapter 2, Section 2.6.4, Page 35).
            • Kingdom Animalia: Eukaryotic, multicellular heterotrophic organisms lacking cell walls (Chapter 2, Section 2.6.5, Pages 36–38).
            """.trimIndent()
        )

        // Unit 3
        addNote(
            "Unit 3 - Cells",
            "Cell Theory (Section 3.2)",
            """
            • The Cell Theory states:
              1. All living things are made up of one or more cells.
              2. The cell is the basic structural and functional unit of life.
              3. All cells arise from pre-existing cells through cell division (Chapter 3, Section 3.2, Page 45).
            • Historical contributors: Robert Hooke (coined term "cell"), Anton van Leeuwenhoek (observed living microscopic organisms), Matthias Schleiden, Theodor Schwann, and Rudolf Virchow (Chapter 3, Section 3.2, Pages 44–45).
            """.trimIndent()
        )

        addNote(
            "Unit 3 - Cells",
            "Cell Structure and Function (Section 3.3)",
            """
            • Plasma Membrane: Selectively permeable phospholipid bilayer regulating entry and exit of substances (Chapter 3, Section 3.3, Pages 46–47).
            • Cytoplasm: Jelly-like matrix containing organelles where metabolic reactions occur (Chapter 3, Section 3.3, Page 47).
            • Nucleus: Control center containing genetic material (DNA), enclosed by nuclear membrane (Chapter 3, Section 3.3, Pages 47–48).
            • Key organelles: Mitochondria (cellular respiration/ATP production), Ribosomes (protein synthesis), Endoplasmic Reticulum (rough/smooth ER), Golgi apparatus (packaging proteins), Lysosomes (digestive enzymes), Chloroplasts (photosynthesis in plant cells) (Chapter 3, Section 3.3, Pages 48–53).
            """.trimIndent()
        )

        addNote(
            "Unit 3 - Cells",
            "Types of Cells and Animal vs. Plant Cells (Sections 3.4 & 3.5)",
            """
            • Prokaryotic cells: Lack true membrane-bound nucleus and membrane-bound organelles (e.g., bacteria) (Chapter 3, Section 3.4, Page 53).
            • Eukaryotic cells: Possess true membrane-bound nucleus and specialized membrane-bound organelles (e.g., plant and animal cells) (Chapter 3, Section 3.4, Page 53).
            • Plant vs Animal Cells: Plant cells possess rigid cellulose cell wall, large central vacuoles, chloroplasts; animal cells lack cell walls, have smaller temporary vacuoles, contain centrioles (Chapter 3, Section 3.5, Pages 54–55).
            """.trimIndent()
        )

        addNote(
            "Unit 3 - Cells",
            "The Cell and Its Environment / Transport Mechanisms (Section 3.7)",
            """
            • Passive Transport: Movement down concentration gradient without expending ATP. Includes:
              - Diffusion: Net movement of particles from high to low concentration.
              - Osmosis: Diffusion of water across selectively permeable membrane.
              - Facilitated Diffusion: Assisted by transport proteins (Chapter 3, Section 3.7.1, Pages 59–67).
            • Active Transport: Movement against concentration gradient (low to high) requiring cellular energy (ATP) and carrier proteins (Chapter 3, Section 3.7.2, Page 68).
            """.trimIndent()
        )

        addNote(
            "Unit 3 - Cells",
            "Levels of Biological Organization (Section 3.8)",
            """
            • Biological hierarchy: Cell → Tissue → Organ → Organ System → Organism → Population → Community → Ecosystem → Biosphere (Chapter 3, Section 3.8, Pages 68–73).
            """.trimIndent()
        )

        // Unit 4
        addNote(
            "Unit 4 - Reproduction",
            "Introduction and Asexual Reproduction (Sections 4.1 - 4.3)",
            """
            • Reproduction is the biological process by which organisms produce new individual offspring of the same kind, ensuring continuation of species (Chapter 4, Section 4.1, Page 74).
            • Asexual reproduction: Single parent producing genetically identical offspring (clones) without fusion of gametes (Chapter 4, Section 4.2, Page 75).
            • Types: Fission (binary fission in amoeba), Fragmentation (spirogyra), Budding (yeast, hydra), Vegetative Propagation (runners, tubers, bulbs, cuttings) (Chapter 4, Section 4.3, Pages 76–80).
            """.trimIndent()
        )

        addNote(
            "Unit 4 - Reproduction",
            "Sexual Reproduction in Humans (Sections 4.4 - 4.9)",
            """
            • Sexual reproduction involves fusion of male and female gametes (sperm and egg) to form a genetically unique zygote (Chapter 4, Section 4.4, Page 81).
            • Primary and secondary sexual characteristics develop during puberty under hormonal control (Chapter 4, Section 4.5, Pages 81–83).
            • Male system: Testes (sperm/testosterone), epididymis, vas deferens, seminal vesicles, prostate, penis (Chapter 4, Section 4.6, Pages 84–85).
            • Female system: Ovaries (eggs/hormones), fallopian tubes, uterus, cervix, vagina (Chapter 4, Section 4.7, Pages 86–87).
            • Menstrual cycle: Periodic hormonal and physiological changes, averaging 28 days (Chapter 4, Section 4.8, Pages 88–89).
            • Fertilization and Pregnancy: Fusion in fallopian tube, implantation in uterine lining, embryonic development (Chapter 4, Section 4.9, Pages 90–91).
            """.trimIndent()
        )

        addNote(
            "Unit 4 - Reproduction",
            "Birth Control and Sexually Transmitted Infections (Sections 4.10 & 4.11)",
            """
            • Birth Control (Contraception): Natural methods, barrier methods (condoms), hormonal methods (pills), IUDs, surgical sterilization (Chapter 4, Section 4.10, Pages 92–96).
            • STIs: Bacterial (gonorrhea, syphilis, chlamydia) or viral (HIV/AIDS, hepatitis B, herpes) transmitted primarily via sexual contact (Chapter 4, Section 4.11, Pages 97–104).
            """.trimIndent()
        )

        // Unit 5
        addNote(
            "Unit 5 - Human Health, Nutrition, and Disease",
            "Nutrition and Nutrients (Sections 5.1 - 5.3)",
            """
            • Food provides chemical energy and organic building blocks for growth, repair, and metabolism (Chapter 5, Section 5.1, Page 105).
            • Major Nutrients: Carbohydrates (energy), Proteins (growth/repair), Lipids (energy storage/insulation), Vitamins/Minerals (metabolic regulation/immunity), Water (solvent/medium) (Chapter 5, Section 5.3, Pages 106–109).
            """.trimIndent()
        )

        addNote(
            "Unit 5 - Human Health, Nutrition, and Disease",
            "Balanced Diets and Nutritional Disorders (Sections 5.4 - 5.6)",
            """
            • Balanced diet: Contains all essential nutrients in correct proportions (Chapter 5, Section 5.4, Pages 110–111).
            • Deficiency diseases: Kwashiorkor/Marasmus (protein-energy), Scurvy (Vit C), Rickets (Vit D/calcium), Anemia (iron) (Chapter 5, Section 5.5, Pages 112–114).
            • Malnutrition: Unbalanced diet including undernutrition and overnutrition/obesity (Chapter 5, Section 5.6, Pages 115–116).
            """.trimIndent()
        )

        addNote(
            "Unit 5 - Human Health, Nutrition, and Disease",
            "Substance Abuse and Diseases (Sections 5.7 & 5.8)",
            """
            • Substance abuse: Harmful use of alcohol, tobacco (nicotine), cannabis, illicit drugs (Chapter 5, Section 5.7, Pages 117–126).
            • Infectious diseases: Pathogens (bacteria, viruses, fungi, protozoa) transmitted between individuals (malaria, TB, HIV/AIDS, COVID-19) (Chapter 5, Section 5.8.1, Pages 127–135).
            • Non-infectious diseases: Genetic, lifestyle, environmental factors (diabetes, cardiovascular disease, cancer) (Chapter 5, Section 5.8.2, Page 136).
            """.trimIndent()
        )

        // Unit 6
        addNote(
            "Unit 6 - Ecology",
            "Ecology and Ecosystems (Section 6.1)",
            """
            • Ecology: Study of interactions between organisms and biotic/abiotic environment (Chapter 6, Section 6.1.1, Page 139).
            • Biotic (producers, consumers, decomposers) vs. Abiotic (temperature, light, water, soil pH) (Chapter 6, Section 6.1.2, Pages 140–142).
            • Levels: Organism → Population → Community → Ecosystem → Biome → Biosphere (Chapter 6, Section 6.1.3, Page 143).
            • Succession: Primary and secondary ecological succession over time (Chapter 6, Section 6.1.6, Pages 155–156).
            """.trimIndent()
        )

        addNote(
            "Unit 6 - Ecology",
            "Ecological Relationships (Section 6.2)",
            """
            • Symbiosis: Mutualism (+/+), Commensalism (+/0), Parasitism (+/-), Predation and Competition (Chapter 6, Section 6.2, Pages 157–164).
            • Food chains/webs: Illustrating energy flow, trophic levels, and ecological pyramids (Chapter 6, Section 6.2, Pages 165–172).
            """.trimIndent()
        )

        return notesList
    }
}
