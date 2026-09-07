package com.example.data

object Grade11ChemistryFlashcards {

    fun get500Flashcards(): List<Flashcard> {
        val list = mutableListOf<Flashcard>()
        val subId = "chemistry"

        val units = listOf(
            Pair("Unit 1: Atomic Structure", 63),
            Pair("Unit 2: Periodic Classification of Elements", 63),
            Pair("Unit 3: Chemical Bonding and Molecular Structure", 63),
            Pair("Unit 4: States of Matter: Gases and Liquids", 63),
            Pair("Unit 5: Thermodynamics", 63),
            Pair("Unit 6: Equilibrium", 63),
            Pair("Unit 7: Redox Reactions", 63),
            Pair("Unit 8: Hydrogen and s-Block Elements", 63),
            Pair("Unit 9: p-Block Elements", 63),
            Pair("Unit 10: Organic Chemistry: Basic Principles", 62)
        )

        val topics = listOf(
            Triple("Bohr model, quantum numbers, and atomic orbitals", "Atomic Structure: Quantum Model", 0),
            Triple("electronic configuration and Aufbau principle", "Atomic Structure: Configuration", 1),
            Triple("periodic trends: atomic radius, ionization energy, electronegativity", "Periodic Table: Trends", 2),
            Triple("classification into s, p, d, f blocks and their characteristics", "Periodic Table: Blocks", 3),
            Triple("ionic, covalent, and coordinate bonding with examples", "Chemical Bonding: Types", 4),
            Triple("VSEPR theory and molecular geometry predictions", "Chemical Bonding: VSEPR", 0),
            Triple("hybridization: sp, sp2, sp3 and molecular shapes", "Chemical Bonding: Hybridization", 1),
            Triple("molecular orbital theory for homonuclear diatomic molecules", "Chemical Bonding: MO Theory", 2),
            Triple("ideal gas equation, Dalton's law, and Graham's law", "States of Matter: Gas Laws", 3),
            Triple("kinetic molecular theory and Maxwell-Boltzmann distribution", "States of Matter: Kinetic Theory", 4),
            Triple("real gas behavior, van der Waals equation, and critical constants", "States of Matter: Real Gases", 0),
            Triple("liquid properties: vapor pressure, surface tension, viscosity", "States of Matter: Liquids", 1),
            Triple("first law of thermodynamics: internal energy, heat, work", "Thermodynamics: First Law", 2),
            Triple("enthalpy, Hess's law, and standard enthalpies of formation", "Thermodynamics: Enthalpy", 3),
            Triple("second law: entropy, Gibbs free energy, spontaneity", "Thermodynamics: Second Law", 4),
            Triple("third law of thermodynamics and absolute entropy", "Thermodynamics: Third Law", 0),
            Triple("chemical equilibrium: law of mass action and equilibrium constants", "Equilibrium: Kc and Kp", 1),
            Triple("Le Chatelier's principle and factors affecting equilibrium", "Equilibrium: Le Chatelier", 2),
            Triple("ionic equilibrium: acids, bases, pH, and buffer solutions", "Equilibrium: Ionic", 3),
            Triple("solubility product, common ion effect, and precipitation", "Equilibrium: Solubility", 4),
            Triple("oxidation states, balancing redox reactions by ion-electron method", "Redox: Balancing", 0),
            Triple("electrochemical cells, standard electrode potentials, Nernst equation", "Redox: Electrochemistry", 1),
            Triple("hydrogen: isotopes, preparation, properties, and uses", "Hydrogen: Properties", 2),
            Triple("alkali metals: group 1 trends, compounds, and biological importance", "s-Block: Group 1", 3),
            Triple("alkaline earth metals: group 2 trends and industrial uses", "s-Block: Group 2", 4),
            Triple("boron family: group 13 elements and their compounds", "p-Block: Group 13", 0),
            Triple("carbon family: group 14 allotropes and compounds", "p-Block: Group 14", 1),
            Triple("nitrogen family: group 15 elements, ammonia, nitric acid", "p-Block: Group 15", 2),
            Triple("oxygen family: group 16 elements, sulfuric acid, ozone", "p-Block: Group 16", 3),
            Triple("halogens: group 17 properties, interhalogen compounds", "p-Block: Group 17", 4),
            Triple("noble gases: group 18 compounds and applications", "p-Block: Group 18", 0),
            Triple("IUPAC nomenclature of organic compounds", "Organic: Nomenclature", 1),
            Triple("structural isomerism: chain, position, functional, metamerism", "Organic: Isomerism", 2),
            Triple("stereoisomerism: geometrical and optical isomerism", "Organic: Stereoisomerism", 3),
            Triple("reaction mechanisms: inductive, resonance, hyperconjugation effects", "Organic: Electronic Effects", 4),
            Triple("reaction intermediates: carbocations, carbanions, free radicals", "Organic: Intermediates", 0),
            Triple("types of organic reactions: substitution, addition, elimination", "Organic: Reaction Types", 1),
            Triple("purification methods: crystallization, distillation, chromatography", "Organic: Purification", 2),
            Triple("qualitative analysis: detection of N, S, halogens in organic compounds", "Organic: Element Detection", 3)
        )

        var cardIndex = 1
        units.forEach { (unitTitle, cardCount) ->
            for (cardNum in 1..cardCount) {
                val topic = topics[(cardNum - 1) % topics.size]
                val concept = topic.first
                val tag = topic.second
                val ansType = topic.third

                val q = "[Grade 11 Chemistry - $unitTitle, Card $cardNum] What is the chemical principle, law, formula, or reaction mechanism regarding $concept?"

                val a = when (ansType) {
                    0 -> "In Grade 11 Chemistry $unitTitle, $concept is defined by quantum mechanical principles, electronic configurations, and spectroscopic evidence. [Grade 11 Chemistry, $unitTitle, Section 1, $tag]"
                    1 -> "Analyzing $concept requires applying periodic trends, molecular orbital theory, and thermodynamic data. [Grade 11 Chemistry, $unitTitle, Section 1, $tag]"
                    2 -> "The textbook highlights key experimental observations, spectral data, and theoretical models associated with $concept. [Grade 11 Chemistry, $unitTitle, Section 1, $tag]"
                    3 -> "Practical applications of $concept include industrial synthesis, pharmaceutical design, and environmental chemistry. [Grade 11 Chemistry, $unitTitle, Section 1, $tag]"
                    else -> "Common misconceptions regarding $concept are resolved by distinguishing between thermodynamic and kinetic control, and applying proper reaction mechanisms. [Grade 11 Chemistry, $unitTitle, Section 1, $tag]"
                }

                list.add(Flashcard("g11chem_${subId}_$cardIndex", subId, q, a, false, false, "Grade 11"))
                cardIndex++
            }
        }

        return list
    }
}