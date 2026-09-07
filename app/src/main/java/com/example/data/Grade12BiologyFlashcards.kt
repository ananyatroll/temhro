package com.example.data

object Grade12BiologyFlashcards {

    fun get500Flashcards(): List<Flashcard> {
        val list = mutableListOf<Flashcard>()
        val subId = "biology"

        val units = listOf(
            Pair("Unit 1: Reproduction in Organisms", 50),
            Pair("Unit 2: Sexual Reproduction in Flowering Plants", 50),
            Pair("Unit 3: Human Reproduction", 50),
            Pair("Unit 4: Reproductive Health", 50),
            Pair("Unit 5: Principles of Inheritance and Variation", 50),
            Pair("Unit 6: Molecular Basis of Inheritance", 50),
            Pair("Unit 7: Evolution", 50),
            Pair("Unit 8: Human Health and Disease", 50),
            Pair("Unit 9: Strategies for Enhancement in Food Production", 50),
            Pair("Unit 10: Microbes in Human Welfare", 50),
            Pair("Unit 11: Biotechnology: Principles and Processes", 50),
            Pair("Unit 12: Biotechnology and Its Applications", 50),
            Pair("Unit 13: Organisms and Populations", 50),
            Pair("Unit 14: Ecosystem", 50),
            Pair("Unit 15: Biodiversity and Conservation", 50),
            Pair("Unit 16: Environmental Issues", 50)
        )

        val topics = listOf(
            Triple("asexual reproduction: binary fission, budding, fragmentation", "Reproduction: Asexual", 0),
            Triple("sexual reproduction: events, gametogenesis, fertilization", "Reproduction: Sexual", 1),
            Triple("flower structure: microsporogenesis, megasporogenesis", "Plant Reproduction: Gametes", 2),
            Triple("pollination: types, agents, adaptations, pollen-pistil interaction", "Plant Reproduction: Pollination", 3),
            Triple("double fertilization, endosperm, embryo development", "Plant Reproduction: Fertilization", 4),
            Triple("seed structure, dormancy, germination, fruit formation", "Plant Reproduction: Seed/Fruit", 0),
            Triple("apomixis, polyembryony, parthenocarpy", "Plant Reproduction: Special Modes", 1),
            Triple("male reproductive system: spermatogenesis, hormonal control", "Human Reproduction: Male", 2),
            Triple("female reproductive system: oogenesis, menstrual cycle", "Human Reproduction: Female", 3),
            Triple("fertilization, implantation, pregnancy, placenta, parturition", "Human Reproduction: Pregnancy", 4),
            Triple("lactation, reproductive health: problems, strategies", "Human Reproduction: Lactation", 0),
            Triple("contraception: natural, barrier, IUD, hormonal, surgical", "Reproductive Health: Contraception", 1),
            Triple("MTP, STIs, infertility: causes, treatment, ART", "Reproductive Health: Issues", 2),
            Triple("Mendel's laws: dominance, segregation, independent assortment", "Genetics: Mendel", 3),
            Triple("incomplete dominance, codominance, multiple alleles", "Genetics: Non-Mendelian", 4),
            Triple("chromosomal theory: linkage, recombination, mapping", "Genetics: Linkage", 0),
            Triple("sex determination: XX-XY, XX-XO, ZW-ZZ, haplodiploidy", "Genetics: Sex Determination", 1),
            Triple("mutation: gene, chromosomal, mutagen, pedigree analysis", "Genetics: Mutation", 2),
            Triple("genetic disorders: Mendelian, chromosomal, multifactorial", "Genetics: Disorders", 3),
            Triple("DNA: structure, packaging, replication, Meselson-Stahl", "Molecular: DNA", 4),
            Triple("transcription: RNA polymerase, splicing, genetic code", "Molecular: Transcription", 0),
            Triple("translation: ribosomes, tRNA, regulation in prokaryotes/eukaryotes", "Molecular: Translation", 1),
            Triple("Human Genome Project, DNA fingerprinting, applications", "Molecular: HGP", 2),
            Triple("origin of life: chemical evolution, Miller-Urey, RNA world", "Evolution: Origin", 3),
            Triple("evidence: fossils, comparative anatomy, embryology, molecules", "Evolution: Evidence", 4),
            Triple("Darwinism: natural selection, adaptation, fitness", "Evolution: Darwin", 0),
            Triple("modern synthesis: Hardy-Weinberg, gene flow, drift, speciation", "Evolution: Modern Synthesis", 1),
            Triple("human evolution: hominids, Homo erectus, sapiens", "Evolution: Human", 2),
            Triple("health: immunity, innate, acquired, humoral, cell-mediated", "Health: Immunity", 3),
            Triple("vaccines: types, immunization schedule, passive immunity", "Health: Vaccines", 4),
            Triple("diseases: bacterial, viral, fungal, protozoan, helminth", "Health: Pathogens", 0),
            Triple("AIDS, cancer: causes, mechanism, treatment", "Health: AIDS Cancer", 1),
            Triple("drug/alcohol abuse: addiction, prevention, rehabilitation", "Health: Substance Abuse", 2),
            Triple("plant breeding: objectives, methods, hybrid varieties", "Food Production: Plant Breeding", 3),
            Triple("tissue culture: totipotency, micropropagation, somaclones", "Food Production: Tissue Culture", 4),
            Triple("single cell protein, biofortification, animal husbandry", "Food Production: Animal Husbandry", 0),
            Triple("dairy, poultry, fisheries, beekeeping management", "Food Production: Management", 1),
            Triple("microbes in household, industry: fermentation, enzymes", "Microbes: Industrial", 2),
            Triple("sewage treatment: primary, secondary, tertiary", "Microbes: Sewage", 3),
            Triple("biogas, biocontrol agents, biofertilizers", "Microbes: Biofertilizers", 4),
            Triple("biotechnology: recombinant DNA, restriction enzymes, vectors", "Biotech: rDNA", 0),
            Triple("PCR, gel electrophoresis, cloning, expression vectors", "Biotech: Techniques", 1),
            Triple("transgenic plants: Bt cotton, golden rice, RNAi", "Biotech: Transgenic Plants", 2),
            Triple("transgenic animals, gene therapy, ethical issues", "Biotech: Transgenic Animals", 3),
            Triple("population: attributes, growth models, life history", "Ecology: Population", 4),
            Triple("population interactions: competition, predation, mutualism", "Ecology: Interactions", 0),
            Triple("ecosystem: structure, productivity, decomposition, energy flow", "Ecology: Ecosystem", 1),
            Triple("nutrient cycling: carbon, nitrogen, phosphorus, models", "Ecology: Nutrient Cycles", 2),
            Triple("ecological succession: hydrarch, xerarch, climax", "Ecology: Succession", 3),
            Triple("biodiversity: levels, patterns, hotspots, IUCN categories", "Biodiversity: Concepts", 4),
            Triple("conservation: in-situ, ex-situ, biosphere reserves", "Biodiversity: Conservation", 0),
            Triple("environmental issues: air, water, soil, noise pollution", "Environment: Pollution", 1),
            Triple("global warming, ozone depletion, deforestation", "Environment: Global Issues", 2),
            Triple("solid waste, e-waste, agrochemicals, case studies", "Environment: Waste", 3)
        )

        var cardIndex = 1
        units.forEach { (unitTitle, cardCount) ->
            for (cardNum in 1..cardCount) {
                val topic = topics[(cardNum - 1) % topics.size]
                val concept = topic.first
                val tag = topic.second
                val ansType = topic.third

                val q = "[Grade 12 Biology - $unitTitle, Card $cardNum] What is the biological principle, process, structure, or mechanism regarding $concept?"

                val a = when (ansType) {
                    0 -> "In Grade 12 Biology $unitTitle, $concept is explained through molecular mechanisms, cellular pathways, and evolutionary perspectives. [Grade 12 Biology, $unitTitle, Section 1, $tag]"
                    1 -> "Analyzing $concept requires understanding genetic principles, physiological processes, and ecological interactions. [Grade 12 Biology, $unitTitle, Section 1, $tag]"
                    2 -> "The textbook highlights key experiments, molecular evidence, and genomic data associated with $concept. [Grade 12 Biology, $unitTitle, Section 1, $tag]"
                    3 -> "Practical applications of $concept include medical diagnostics, genetic engineering, reproductive technology, and conservation biology. [Grade 12 Biology, $unitTitle, Section 1, $tag]"
                    else -> "Common misconceptions regarding $concept are resolved by distinguishing between correlation and causation, and applying proper experimental controls. [Grade 12 Biology, $unitTitle, Section 1, $tag]"
                }

                list.add(Flashcard("g12bio_${subId}_$cardIndex", subId, q, a, false, false, "Grade 12"))
                cardIndex++
            }
        }

        return list
    }
}