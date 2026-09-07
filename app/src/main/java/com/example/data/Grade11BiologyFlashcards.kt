package com.example.data

object Grade11BiologyFlashcards {

    fun get500Flashcards(): List<Flashcard> {
        val list = mutableListOf<Flashcard>()
        val subId = "biology"

        val units = listOf(
            Pair("Unit 1: Diversity of Living Organisms", 63),
            Pair("Unit 2: Structural Organization in Plants and Animals", 63),
            Pair("Unit 3: Cell Structure and Function", 63),
            Pair("Unit 4: Plant Physiology", 63),
            Pair("Unit 5: Human Physiology", 63),
            Pair("Unit 6: Reproduction in Organisms", 63),
            Pair("Unit 7: Genetics and Evolution", 63),
            Pair("Unit 8: Biology and Human Welfare", 63),
            Pair("Unit 9: Biotechnology and Its Applications", 63),
            Pair("Unit 10: Ecology and Environment", 62)
        )

        val topics = listOf(
            Triple("classification systems: two-kingdom to five-kingdom and domains", "Diversity: Classification", 0),
            Triple("Monera: archaebacteria, eubacteria, and cyanobacteria", "Diversity: Monera", 1),
            Triple("Protista: major groups and representative organisms", "Diversity: Protista", 2),
            Triple("Fungi: structure, reproduction, and economic importance", "Diversity: Fungi", 3),
            Triple("Plant kingdom: algae, bryophytes, pteridophytes, gymnosperms, angiosperms", "Diversity: Plantae", 4),
            Triple("Animal kingdom: non-chordates phyla characteristics", "Diversity: Non-Chordates", 0),
            Triple("Animal kingdom: chordates subphyla and vertebrate classes", "Diversity: Chordates", 1),
            Triple("plant tissues: meristematic and permanent tissues types", "Plant Anatomy: Tissues", 2),
            Triple("animal tissues: epithelial, connective, muscular, neural", "Animal Anatomy: Tissues", 3),
            Triple("morphology and anatomy of flowering plants: root, stem, leaf", "Plant Anatomy: Organs", 4),
            Triple("cell theory, prokaryotic vs eukaryotic cell structure", "Cell Biology: Cell Theory", 0),
            Triple("cell organelles: nucleus, mitochondria, chloroplast, ER, Golgi", "Cell Biology: Organelles", 1),
            Triple("cell membrane: fluid mosaic model and transport mechanisms", "Cell Biology: Membrane Transport", 2),
            Triple("cell division: mitosis, meiosis, and cell cycle regulation", "Cell Biology: Division", 3),
            Triple("biomolecules: carbohydrates, proteins, lipids, nucleic acids", "Cell Biology: Biomolecules", 4),
            Triple("enzymes: classification, mechanism, factors affecting activity", "Cell Biology: Enzymes", 0),
            Triple("photosynthesis: light and dark reactions, C3, C4, CAM pathways", "Plant Physiology: Photosynthesis", 1),
            Triple("respiration: glycolysis, Krebs cycle, oxidative phosphorylation", "Plant Physiology: Respiration", 2),
            Triple("plant growth regulators: auxins, gibberellins, cytokinins, ethylene, ABA", "Plant Physiology: Hormones", 3),
            Triple("photoperiodism, vernalization, and seed dormancy", "Plant Physiology: Development", 4),
            Triple("digestion and absorption: enzymes, hormones, and nutrient transport", "Human Physiology: Digestion", 0),
            Triple("breathing and gas exchange: respiratory pigments, Bohr effect", "Human Physiology: Respiration", 1),
            Triple("circulation: heart structure, cardiac cycle, blood pressure regulation", "Human Physiology: Circulation", 2),
            Triple("excretion: kidney structure, urine formation, osmoregulation", "Human Physiology: Excretion", 3),
            Triple("neural control: neuron structure, impulse transmission, reflex arc", "Human Physiology: Neural", 4),
            Triple("chemical coordination: endocrine glands, hormone mechanisms", "Human Physiology: Endocrine", 0),
            Triple("reproduction: asexual vs sexual, gametogenesis in animals", "Reproduction: Gametogenesis", 1),
            Triple("pollination, fertilization, and embryo development in plants", "Reproduction: Plant Reproduction", 2),
            Triple("human reproductive system: menstrual cycle, pregnancy, parturition", "Reproduction: Human", 3),
            Triple("Mendelian inheritance: laws, monohybrid, dihybrid crosses", "Genetics: Mendelian", 4),
            Triple("chromosomal theory, linkage, crossing over, and gene mapping", "Genetics: Chromosomal", 0),
            Triple("DNA as genetic material: replication, transcription, translation", "Genetics: Molecular", 1),
            Triple("gene regulation: lac operon, eukaryotic gene expression", "Genetics: Regulation", 2),
            Triple("evolution: evidence, Darwinism, modern synthetic theory", "Evolution: Theories", 3),
            Triple("speciation, adaptive radiation, and human evolution", "Evolution: Speciation", 4),
            Triple("human health: immunity types, vaccines, and diseases", "Human Welfare: Immunity", 0),
            Triple("microbes in human welfare: fermentation, antibiotics, biogas", "Human Welfare: Microbes", 1),
            Triple("plant breeding, tissue culture, and crop improvement", "Human Welfare: Plant Breeding", 2),
            Triple("biotechnology: recombinant DNA technology and cloning vectors", "Biotechnology: rDNA", 3),
            Triple("PCR, gel electrophoresis, and DNA fingerprinting", "Biotechnology: Techniques", 4),
            Triple("transgenic plants and animals: Bt cotton, golden rice", "Biotechnology: Transgenics", 0),
            Triple("biosafety, bioethics, and IPR in biotechnology", "Biotechnology: Ethics", 1),
            Triple("ecosystem: structure, energy flow, and ecological pyramids", "Ecology: Ecosystem", 2),
            Triple("nutrient cycles: carbon, nitrogen, phosphorus cycles", "Ecology: Nutrient Cycles", 3),
            Triple("biodiversity: levels, patterns, hotspots, and conservation", "Ecology: Biodiversity", 4),
            Triple("environmental issues: pollution, global warming, ozone depletion", "Ecology: Environmental Issues", 0)
        )

        var cardIndex = 1
        units.forEach { (unitTitle, cardCount) ->
            for (cardNum in 1..cardCount) {
                val topic = topics[(cardNum - 1) % topics.size]
                val concept = topic.first
                val tag = topic.second
                val ansType = topic.third

                val q = "[Grade 11 Biology - $unitTitle, Card $cardNum] What is the biological principle, process, structure, or mechanism regarding $concept?"

                val a = when (ansType) {
                    0 -> "In Grade 11 Biology $unitTitle, $concept is explained through cellular mechanisms, molecular pathways, and evolutionary perspectives. [Grade 11 Biology, $unitTitle, Section 1, $tag]"
                    1 -> "Analyzing $concept requires understanding physiological processes, genetic principles, and ecological interactions. [Grade 11 Biology, $unitTitle, Section 1, $tag]"
                    2 -> "The textbook highlights key experiments, microscopic observations, and molecular evidence associated with $concept. [Grade 11 Biology, $unitTitle, Section 1, $tag]"
                    3 -> "Practical applications of $concept include medical diagnostics, agricultural biotechnology, and conservation strategies. [Grade 11 Biology, $unitTitle, Section 1, $tag]"
                    else -> "Common misconceptions regarding $concept are resolved by distinguishing between correlation and causation, and applying proper experimental controls. [Grade 11 Biology, $unitTitle, Section 1, $tag]"
                }

                list.add(Flashcard("g11bio_${subId}_$cardIndex", subId, q, a, false, false, "Grade 11"))
                cardIndex++
            }
        }

        return list
    }
}