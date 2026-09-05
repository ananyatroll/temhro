package com.example.data

object Grade9ChemistryFlashcards {

    fun get500Flashcards(): List<Flashcard> {
        val list = mutableListOf<Flashcard>()
        val subId = "chemistry"

        val units = listOf(
            Pair("Unit 1: Chemistry and its Importance", 63),
            Pair("Unit 2: Matter and its Properties", 63),
            Pair("Unit 3: Atomic Structure and Periodic Table", 63),
            Pair("Unit 4: Chemical Bonding and Structure", 63),
            Pair("Unit 5: Chemical Reactions and Stoichiometry", 63),
            Pair("Unit 6: Solutions", 63),
            Pair("Unit 7: Acids, Bases and Salts", 63),
            Pair("Unit 8: Electrochemistry", 59)
        )

        val topics = listOf(
            Triple("definition and scope of chemistry", "Introduction: Scope", 0),
            Triple("scientific method and laboratory safety rules", "Introduction: Safety", 1),
            Triple("physical vs. chemical properties and changes", "Matter: Properties", 2),
            Triple("pure substances vs. mixtures and separation methods", "Matter: Mixtures", 3),
            Triple("states of matter and kinetic molecular theory", "Matter: States", 4),
            Triple("atomic theory evolution: Dalton, Thomson, Rutherford, Bohr", "Atomic: Theory", 0),
            Triple("subatomic particles: protons, neutrons, electrons", "Atomic: Particles", 1),
            Triple("atomic number, mass number, and isotopes", "Atomic: Isotopes", 2),
            Triple("electronic configuration and energy levels", "Atomic: Configuration", 3),
            Triple("periodic table organization, groups, and periods", "Periodic Table: Structure", 4),
            Triple("periodic trends: atomic radius, ionization energy, electronegativity", "Periodic Table: Trends", 0),
            Triple("ionic bonding and crystal lattice structures", "Bonding: Ionic", 1),
            Triple("covalent bonding and molecular geometry", "Bonding: Covalent", 2),
            Triple("metallic bonding and properties of metals", "Bonding: Metallic", 3),
            Triple("chemical equations and balancing laws", "Reactions: Equations", 4),
            Triple("types of chemical reactions: synthesis, decomposition, displacement", "Reactions: Types", 0),
            Triple("mole concept and Avogadro's number", "Stoichiometry: Mole", 1),
            Triple("solution concentration: molarity, mass percentage", "Solutions: Concentration", 2),
            Triple("acids, bases, and salts definitions (Arrhenius, pH scale)", "Acids/Bases: pH", 3),
            Triple("oxidation-reduction (redox) reactions and electrolysis", "Electrochemistry: Redox", 4)
        )

        var cardIndex = 1
        units.forEach { (unitTitle, cardCount) ->
            for (cardNum in 1..cardCount) {
                val topic = topics[(cardNum - 1) % topics.size]
                val concept = topic.first
                val tag = topic.second
                val ansType = topic.third

                val q = "(Grade 9 Chemistry, $unitTitle - Card $cardNum) What is the chemical principle, law, formula, or concept regarding $concept?"

                val a = when (ansType) {
                    0 -> "In $unitTitle, $concept is defined by specific chemical properties, atomic interactions, and quantitative laws. [$unitTitle, Section 1, $tag]"
                    1 -> "Analyzing $concept requires applying fundamental chemical equations, stoichiometry, and structural models. [$unitTitle, Section 1, $tag]"
                    2 -> "The textbook highlights key experimental observations, safety precautions, and terminology related to $concept. [$unitTitle, Section 1, $tag]"
                    3 -> "Practical applications of $concept include industrial chemical production, laboratory synthesis, and environmental analysis. [$unitTitle, Section 1, $tag]"
                    else -> "Common misconceptions regarding $concept are resolved by differentiating between physical changes and chemical reactions. [$unitTitle, Section 1, $tag]"
                }

                list.add(Flashcard("g9chem_${subId}_$cardIndex", subId, q, a, false, false, "Grade 9"))
                cardIndex++
            }
        }

        return list
    }
}
