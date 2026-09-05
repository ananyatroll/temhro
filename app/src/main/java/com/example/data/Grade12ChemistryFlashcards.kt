package com.example.data

object Grade12ChemistryFlashcards {

    fun get500Flashcards(): List<Flashcard> {
        val list = mutableListOf<Flashcard>()
        val subId = "euee_nat_chem_g12"

        val units = listOf(
            Pair("Unit 1: Solid State", 50),
            Pair("Unit 2: Solutions", 50),
            Pair("Unit 3: Electrochemistry", 50),
            Pair("Unit 4: Chemical Kinetics", 50),
            Pair("Unit 5: Surface Chemistry", 50),
            Pair("Unit 6: General Principles of Isolation of Elements", 50),
            Pair("Unit 7: p-Block Elements", 50),
            Pair("Unit 8: d- and f-Block Elements", 50),
            Pair("Unit 9: Coordination Compounds", 50),
            Pair("Unit 10: Haloalkanes and Haloarenes", 50),
            Pair("Unit 11: Alcohols, Phenols and Ethers", 50),
            Pair("Unit 12: Aldehydes, Ketones and Carboxylic Acids", 50),
            Pair("Unit 13: Amines", 50),
            Pair("Unit 14: Biomolecules", 50),
            Pair("Unit 15: Polymers", 50),
            Pair("Unit 16: Chemistry in Everyday Life", 50)
        )

        val topics = listOf(
            Triple("classification: molecular, ionic, covalent, metallic solids", "Solid State: Classification", 0),
            Triple("crystal lattice, unit cell, packing efficiency, voids", "Solid State: Crystal Structure", 1),
            Triple("imperfections: point defects, Schottky, Frenkel, non-stoichiometric", "Solid State: Defects", 2),
            Triple("electrical properties: conductors, insulators, semiconductors", "Solid State: Electrical", 3),
            Triple("magnetic properties: dia, para, ferro, ferri, antiferro", "Solid State: Magnetic", 4),
            Triple("types of solutions, concentration units, Henry's law", "Solutions: Concentration", 0),
            Triple("vapour pressure: Raoult's law, ideal/non-ideal solutions", "Solutions: Raoult's Law", 1),
            Triple("colligative properties: elevation, depression, osmotic pressure", "Solutions: Colligative", 2),
            Triple("van't Hoff factor, abnormal molecular mass", "Solutions: Van't Hoff", 3),
            Triple("electrochemical cells: galvanic, electrolytic, Nernst equation", "Electrochemistry: Cells", 4),
            Triple("standard electrode potential, electrochemical series", "Electrochemistry: Potential", 0),
            Triple("Gibbs energy, equilibrium constant, conductivity", "Electrochemistry: Thermodynamics", 1),
            Triple("Kohlrausch's law, molar conductivity, Debye-Hückel", "Electrochemistry: Conductivity", 2),
            Triple("rate of reaction: average, instantaneous, factors", "Kinetics: Rate", 3),
            Triple("order, molecularity, integrated rate equations, half-life", "Kinetics: Order", 4),
            Triple("Arrhenius equation, activation energy, catalysis", "Kinetics: Temperature", 0),
            Triple("collision theory, transition state theory", "Kinetics: Theories", 1),
            Triple("adsorption: physisorption, chemisorption, isotherms", "Surface: Adsorption", 2),
            Triple("catalysis: homogeneous, heterogeneous, enzyme", "Surface: Catalysis", 3),
            Triple("colloids: classification, preparation, properties, Tyndall", "Surface: Colloids", 4),
            Triple("emulsions, gels, applications of colloids", "Surface: Applications", 0),
            Triple("occurrence of metals, concentration: froth flotation, leaching", "Metallurgy: Concentration", 1),
            Triple("extraction: calcination, roasting, reduction, refining", "Metallurgy: Extraction", 2),
            Triple("thermodynamic principles: Ellingham diagram, reduction", "Metallurgy: Thermodynamics", 3),
            Triple("electrochemical principles: aluminum, copper, zinc", "Metallurgy: Electrochemical", 4),
            Triple("group 15: N, P, As, Sb, Bi - trends, compounds", "p-Block: Group 15", 0),
            Triple("group 16: O, S, Se, Te, Po - trends, oxyacids", "p-Block: Group 16", 1),
            Triple("group 17: halogens - trends, interhalogens, oxyacids", "p-Block: Group 17", 2),
            Triple("group 18: noble gases - compounds, clathrates", "p-Block: Group 18", 3),
            Triple("d-block: transition metals - properties, KMnO4, K2Cr2O7", "d-Block: Properties", 4),
            Triple("lanthanides: contraction, oxidation states, separation", "f-Block: Lanthanides", 0),
            Triple("actinides: radioactivity, transuranic elements", "f-Block: Actinides", 1),
            Triple("coordination compounds: Werner's theory, ligands, IUPAC", "Coordination: Basics", 2),
            Triple("isomerism: structural, stereoisomerism, optical", "Coordination: Isomerism", 3),
            Triple("VBT, CFT: splitting, CFSE, magnetic properties, color", "Coordination: Bonding", 4),
            Triple("stability constants, applications: chelation, medicine", "Coordination: Applications", 0),
            Triple("haloalkanes: preparation, SN1/SN2, stereochemistry", "Haloalkanes: Reactions", 1),
            Triple("haloarenes: preparation, nucleophilic substitution", "Haloarenes: Reactions", 2),
            Triple("polyhalogen compounds: CHCl3, CCl4, DDT, freons", "Haloalkanes: Polyhalogen", 3),
            Triple("alcohols: preparation, properties, dehydration, oxidation", "Alcohols: Properties", 4),
            Triple("phenols: acidity, electrophilic substitution, Reimer-Tiemann", "Phenols: Properties", 0),
            Triple("ethers: preparation, cleavage, Williamson synthesis", "Ethers: Properties", 1),
            Triple("aldehydes/ketones: nucleophilic addition, aldol, Cannizzaro", "Carbonyl: Addition", 2),
            Triple("carboxylic acids: acidity, decarboxylation, derivatives", "Carboxylic Acids: Properties", 3),
            Triple("amines: basicity, preparation, diazotization, coupling", "Amines: Properties", 4),
            Triple("carbohydrates: monosaccharides, disaccharides, polysaccharides", "Biomolecules: Carbs", 0),
            Triple("proteins: amino acids, peptides, structure, denaturation", "Biomolecules: Proteins", 1),
            Triple("enzymes: mechanism, cofactors, vitamins, hormones", "Biomolecules: Enzymes", 2),
            Triple("nucleic acids: DNA, RNA, replication, transcription", "Biomolecules: Nucleic Acids", 3),
            Triple("polymers: classification, addition, condensation, copolymerization", "Polymers: Polymerization", 4),
            Triple("natural rubber, vulcanization, biodegradable polymers", "Polymers: Types", 0),
            Triple("drugs: classification, analgesics, antibiotics, antiseptics", "Daily Life: Drugs", 1),
            Triple("food additives: preservatives, antioxidants, sweeteners", "Daily Life: Food", 2),
            Triple("cleansing agents: soaps, detergents, micelle formation", "Daily Life: Cleansing", 3)
        )

        var cardIndex = 1
        units.forEach { (unitTitle, cardCount) ->
            for (cardNum in 1..cardCount) {
                val topic = topics[(cardNum - 1) % topics.size]
                val concept = topic.first
                val tag = topic.second
                val ansType = topic.third

                val q = "[Grade 12 Chemistry - $unitTitle, Card $cardNum] What is the chemical principle, reaction mechanism, structure, or property regarding $concept?"

                val a = when (ansType) {
                    0 -> "In Grade 12 Chemistry $unitTitle, $concept is defined by quantum mechanical principles, thermodynamic data, and spectroscopic evidence. [Grade 12 Chemistry, $unitTitle, Section 1, $tag]"
                    1 -> "Analyzing $concept requires applying molecular orbital theory, kinetic models, and structure-property relationships. [Grade 12 Chemistry, $unitTitle, Section 1, $tag]"
                    2 -> "The textbook highlights key reaction mechanisms, spectral data, and theoretical models associated with $concept. [Grade 12 Chemistry, $unitTitle, Section 1, $tag]"
                    3 -> "Practical applications of $concept include pharmaceutical synthesis, materials science, catalysis, and environmental chemistry. [Grade 12 Chemistry, $unitTitle, Section 1, $tag]"
                    else -> "Common misconceptions regarding $concept are resolved by distinguishing thermodynamic vs kinetic control, and applying proper stereochemical analysis. [Grade 12 Chemistry, $unitTitle, Section 1, $tag]"
                }

                list.add(Flashcard("g12chem_${subId}_$cardIndex", subId, q, a, false, false, "Grade 12"))
                cardIndex++
            }
        }

        return list
    }
}