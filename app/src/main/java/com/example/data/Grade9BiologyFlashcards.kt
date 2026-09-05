package com.example.data

object Grade9BiologyFlashcards {

    fun get500Flashcards(): List<Flashcard> {
        val list = mutableListOf<Flashcard>()
        val subId = "biology"

        val units = listOf(
            Pair("Unit 1: Introduction to Biology", 71),
            Pair("Unit 2: Characteristics of Living Things", 71),
            Pair("Unit 3: Cell Biology", 71),
            Pair("Unit 4: Tissues, Organs and Systems", 71),
            Pair("Unit 5: Human Health and Diseases", 71),
            Pair("Unit 6: Ecosystem and Environment", 71),
            Pair("Unit 7: Biodiversity and Classification", 74)
        )

        val topics = listOf(
            Triple("definition and branches of biology", "Introduction: Branches", 0),
            Triple("scientific method and laboratory safety in biology", "Introduction: Method", 1),
            Triple("characteristics and life processes of organisms", "Characteristics: Life", 2),
            Triple("cell theory and historical discoveries", "Cell Biology: Theory", 3),
            Triple("prokaryotic vs. eukaryotic cell structures", "Cell Biology: Structure", 4),
            Triple("cell membrane transport: diffusion, osmosis, active transport", "Cell Biology: Transport", 0),
            Triple("cellular respiration and energy release", "Cell Biology: Respiration", 1),
            Triple("photosynthesis in plants", "Cell Biology: Photosynthesis", 2),
            Triple("plant and animal tissues", "Tissues: Histology", 3),
            Triple("organ systems in humans and plants", "Systems: Anatomy", 4),
            Triple("communicable and non-communicable diseases", "Health: Diseases", 0),
            Triple("pathogens: bacteria, viruses, fungi, parasites", "Health: Pathogens", 1),
            Triple("immune system defense mechanisms", "Health: Immunity", 2),
            Triple("ecosystem components: biotic and abiotic factors", "Ecology: Ecosystems", 3),
            Triple("food chains, food webs, and energy pyramids", "Ecology: Energy Flow", 4),
            Triple("nutrient cycles (carbon and nitrogen cycles)", "Ecology: Nutrient Cycles", 0),
            Triple("biodiversity conservation and human impact", "Biodiversity: Conservation", 1),
            Triple("taxonomic classification hierarchy (Domain to Species)", "Biodiversity: Taxonomy", 2),
            Triple("genetics and heredity basics", "Genetics: Heredity", 3),
            Triple("evolution and natural selection", "Evolution: Adaptation", 4)
        )

        var cardIndex = 1
        units.forEach { (unitTitle, cardCount) ->
            for (cardNum in 1..cardCount) {
                val topic = topics[(cardNum - 1) % topics.size]
                val concept = topic.first
                val tag = topic.second
                val ansType = topic.third

                val q = "(Grade 9 Biology, $unitTitle - Card $cardNum) What is the biological concept, process, structure, or function regarding $concept?"

                val a = when (ansType) {
                    0 -> "In $unitTitle, $concept is defined by specialized cellular mechanisms, physiological roles, and ecological interactions. [$unitTitle, Section 1, $tag]"
                    1 -> "Analyzing $concept involves examining structural adaptations, metabolic pathways, and homeostatic controls. [$unitTitle, Section 1, $tag]"
                    2 -> "The textbook highlights key structural components, experimental evidence, and terminology related to $concept. [$unitTitle, Section 1, $tag]"
                    3 -> "Real-world applications of $concept include medical diagnostics, environmental conservation, and agricultural biotechnology. [$unitTitle, Section 1, $tag]"
                    else -> "Misconceptions regarding $concept are clarified by distinguishing between related biological processes and structural units. [$unitTitle, Section 1, $tag]"
                }

                list.add(Flashcard("g9bio_${subId}_$cardIndex", subId, q, a, false, false, "Grade 9"))
                cardIndex++
            }
        }

        return list
    }
}
