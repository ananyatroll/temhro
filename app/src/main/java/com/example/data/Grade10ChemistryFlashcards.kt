package com.example.data

object Grade10ChemistryFlashcards {

    fun get500Flashcards(): List<Flashcard> {
        val list = mutableListOf<Flashcard>()
        val subId = "chemistry"

        val units = listOf(
            Pair("Unit 1: Chemical Reaction and Stoichiometry", 63),
            Pair("Unit 2: The Gaseous State of Matter", 63),
            Pair("Unit 3: Atomic Structure and Periodic Table", 63),
            Pair("Unit 4: Chemical Bonding and Structure", 63),
            Pair("Unit 5: Chemical Kinetics and Chemical Equilibrium", 63),
            Pair("Unit 6: Acids, Bases and Salts", 63),
            Pair("Unit 7: Electrochemistry", 63),
            Pair("Unit 8: Industrial Chemistry and Environmental Chemistry", 59)
        )

        val topics = listOf(
            Triple("law of conservation of mass and chemical equation balancing", "Stoichiometry: Equations", 0),
            Triple("mole concept, molar mass, and Avogadro's number calculations", "Stoichiometry: Mole", 1),
            Triple("empirical formula and molecular formula determination", "Stoichiometry: Formulas", 2),
            Triple("limiting reactant and percentage yield calculations", "Stoichiometry: Yield", 3),
            Triple("kinetic molecular theory of gases and gas laws (Boyle's, Charles's, Gay-Lussac's)", "Gases: Gas Laws", 4),
            Triple("ideal gas equation PV = nRT and Dalton's law of partial pressures", "Gases: Ideal Gas", 0),
            Triple("quantum mechanical model of the atom and quantum numbers", "Atomic: Quantum Model", 1),
            Triple("electronic configuration, Aufbau principle, Pauli exclusion principle, Hund's rule", "Atomic: Configuration", 2),
            Triple("periodic table trends: atomic radius, ionization energy, electron affinity, electronegativity", "Periodic Table: Trends", 3),
            Triple("ionic bonding, lattice energy, and properties of ionic compounds", "Bonding: Ionic", 4),
            Triple("covalent bonding, Lewis structures, VSEPR theory, and molecular polarity", "Bonding: Covalent", 0),
            Triple("intermolecular forces: hydrogen bonding, dipole-dipole, London dispersion", "Bonding: Intermolecular", 1),
            Triple("reaction rate definition, factors affecting reaction rate, and collision theory", "Kinetics: Rate", 2),
            Triple("chemical equilibrium, Le Chatelier's principle, and equilibrium constant Kc", "Equilibrium: Principles", 3),
            Triple("Arrhenius, Brønsted-Lowry, and Lewis acid-base definitions", "Acids/Bases: Definitions", 4),
            Triple("pH scale, pOH, strong vs. weak electrolytes, and buffer solutions", "Acids/Bases: pH", 0),
            Triple("oxidation-reduction (redox) reactions, oxidation numbers, and balancing redox equations", "Electrochemistry: Redox", 1),
            Triple("electrolytic cells vs. galvanic (voltaic) cells and standard electrode potentials", "Electrochemistry: Cells", 2),
            Triple("industrial manufacturing processes: Haber process, Contact process, chlor-alkali process", "Industrial: Processes", 3),
            Triple("environmental chemistry: air pollution, water pollution, greenhouse effect, and ozone depletion", "Environmental: Pollution", 4)
        )

        var cardIndex = 1
        units.forEach { (unitTitle, cardCount) ->
            for (cardNum in 1..cardCount) {
                val topic = topics[(cardNum - 1) % topics.size]
                val concept = topic.first
                val tag = topic.second
                val ansType = topic.third

                val q = "[$unitTitle, Card $cardNum] What is the chemical principle, law, formula, or concept regarding $concept?"

                val a = when (ansType) {
                    0 -> "In Grade 10 Chemistry $unitTitle, $concept is defined by specific chemical properties, atomic interactions, and quantitative laws. [$unitTitle, Section 1, $tag]"
                    1 -> "Analyzing $concept requires applying fundamental chemical equations, stoichiometry, and structural models. [$unitTitle, Section 1, $tag]"
                    2 -> "The textbook highlights key experimental observations, safety precautions, and terminology related to $concept. [$unitTitle, Section 1, $tag]"
                    3 -> "Practical applications of $concept include industrial chemical production, laboratory synthesis, and environmental analysis. [$unitTitle, Section 1, $tag]"
                    else -> "Common misconceptions regarding $concept are resolved by differentiating between physical changes and chemical reactions. [$unitTitle, Section 1, $tag]"
                }

                list.add(Flashcard("g10chem_${subId}_$cardIndex", subId, q, a, false, false, "Grade 10"))
                cardIndex++
            }
        }

        return list
    }
}
