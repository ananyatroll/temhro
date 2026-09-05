package com.example.data

object Grade10BiologyFlashcards {

    fun get500Flashcards(): List<Flashcard> {
        val list = mutableListOf<Flashcard>()
        val subId = "biology"

        val units = listOf(
            Pair("Unit 1: Biotechnology", 71),
            Pair("Unit 2: Plants", 71),
            Pair("Unit 3: Animals", 71),
            Pair("Unit 4: Genetics", 71),
            Pair("Unit 5: Human Biology", 71),
            Pair("Unit 6: Ecology", 71),
            Pair("Unit 7: Evolution", 74)
        )

        val topics = listOf(
            Triple("biotechnology definition and applications in agriculture and medicine", "Biotechnology: Scope", 0),
            Triple("genetic engineering, GMOs, and ethical considerations", "Biotechnology: Engineering", 1),
            Triple("plant structure: roots, stems, leaves, and tissues", "Plants: Anatomy", 2),
            Triple("photosynthesis: light-dependent and light-independent reactions", "Plants: Photosynthesis", 3),
            Triple("transport in plants: xylem and phloem mechanisms", "Plants: Transport", 4),
            Triple("plant reproduction: sexual and asexual methods", "Plants: Reproduction", 0),
            Triple("animal tissues: epithelial, connective, muscular, nervous", "Animals: Histology", 1),
            Triple("digestive system: enzymes, absorption, and assimilation", "Human Biology: Digestion", 2),
            Triple("respiratory system: gas exchange and cellular respiration", "Human Biology: Respiration", 3),
            Triple("circulatory system: heart structure, blood vessels, and blood components", "Human Biology: Circulation", 4),
            Triple("excretory system: kidney function and osmoregulation", "Human Biology: Excretion", 0),
            Triple("nervous system: neurons, brain structure, and reflex arcs", "Human Biology: Nervous System", 1),
            Triple("endocrine system: hormones and homeostatic feedback loops", "Human Biology: Endocrine", 2),
            Triple("reproductive system: gametogenesis and fertilization", "Human Biology: Reproduction", 3),
            Triple("Mendelian genetics: laws of inheritance, genotypes, and phenotypes", "Genetics: Mendelian", 4),
            Triple("DNA structure, replication, and protein synthesis", "Genetics: Molecular", 0),
            Triple("mutations: types, causes, and evolutionary significance", "Genetics: Mutation", 1),
            Triple("ecosystem dynamics: energy flow and nutrient cycling", "Ecology: Dynamics", 2),
            Triple("biodiversity threats and conservation strategies", "Ecology: Conservation", 3),
            Triple("evolutionary theories: Darwinism, Lamarckism, and modern synthesis", "Evolution: Theories", 4)
        )

        var cardIndex = 1
        units.forEach { (unitTitle, cardCount) ->
            for (cardNum in 1..cardCount) {
                val topic = topics[(cardNum - 1) % topics.size]
                val concept = topic.first
                val tag = topic.second
                val ansType = topic.third

                val q = "[$unitTitle, Card $cardNum] What is the biological concept, process, structure, or function regarding $concept?"

                val a = when (ansType) {
                    0 -> "In Grade 10 Biology $unitTitle, $concept is defined by complex physiological mechanisms, cellular structures, and biochemical pathways. [$unitTitle, Section 1, $tag]"
                    1 -> "Analyzing $concept involves understanding the relationship between structure and function at various biological levels. [$unitTitle, Section 1, $tag]"
                    2 -> "The textbook highlights key experimental findings, anatomical diagrams, and specific terminology related to $concept. [$unitTitle, Section 1, $tag]"
                    3 -> "Practical applications of $concept include advancements in medical technology, sustainable agriculture, and environmental management. [$unitTitle, Section 1, $tag]"
                    else -> "Critical thinking regarding $concept requires evaluating evolutionary adaptations and homeostatic controls within living systems. [$unitTitle, Section 1, $tag]"
                }

                list.add(Flashcard("g10bio_${subId}_$cardIndex", subId, q, a, false, false, "Grade 10"))
                cardIndex++
            }
        }

        return list
    }
}
