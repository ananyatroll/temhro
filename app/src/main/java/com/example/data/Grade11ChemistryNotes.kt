package com.example.data

object Grade11ChemistryNotes {

    fun getGrade11ChemistryNotes(): List<SubjectNote> {
        val notesList = mutableListOf<SubjectNote>()
        val subId = "chemistry"
        var idx = 1

        fun addNote(unit: String, title: String, content: String) {
            notesList.add(
                SubjectNote(
                    id = "g11_chem_note_${idx}",
                    subjectId = subId,
                    unit = unit,
                    title = title,
                    content = content,
                    gradeLevel = "Grade 11"
                )
            )
            idx++
        }

        // Unit 1
        addNote(
            "Unit 1 - Atomic Structure and Periodic Properties of the Elements",
            "Atomic Theories, Quantum Mechanical Model, and Periodic Properties (Sections 1.1 - 1.8)",
            """
            • Historical Development of Atomic Structure: From Dalton's solid sphere model, Thomson's plum pudding model, Rutherford's nuclear model (alpha scattering experiment), to Bohr's quantized energy level model.
            • Subatomic Particles: Protons (positive charge, mass ≈ 1 amu), neutrons (neutral, mass ≈ 1 amu), and electrons (negative charge, negligible mass). Atomic number (Z) equals number of protons; mass number (A) equals protons plus neutrons. Isotopes are atoms of the same element with different neutron numbers.
            • Quantum Mechanical Model of the Atom: Based on de Broglie's wave-particle duality, Heisenberg's Uncertainty Principle (Δx · Δp ≥ h/4π), and Schrödinger's wave equation. Describes electrons in terms of wave functions and atomic orbitals.
            • Quantum Numbers: Principal quantum number (n), angular momentum quantum number (l), magnetic quantum number (m_l), and spin quantum number (m_s) completely describe the energy, shape, orientation, and spin of electrons in orbitals.
            • Electronic Configurations and Orbital Diagrams: Built using the Aufbau principle, Pauli exclusion principle, and Hund's rule of maximum multiplicity.
            • Periodic Properties: Atomic radius, ionization energy, electron affinity, and electronegativity exhibit systematic periodic trends across periods and down groups in the modern periodic table.
            """.trimIndent()
        )

        // Unit 2
        addNote(
            "Unit 2 - Chemical Bonding",
            "Ionic, Covalent, and Metallic Bonding, Molecular Geometry, and Bonding Theories (Sections 2.1 - 2.6)",
            """
            • Octet Rule: Atoms tend to gain, lose, or share electrons to achieve a stable noble gas electronic configuration with eight valence electrons.
            • Ionic Bonding: Formed by the electrostatic attraction between oppositely charged ions resulting from complete electron transfer from metal to nonmetal. Lewis electron-dot symbols illustrate valence electrons.
            • Covalent Bonding and Molecular Geometry: Formed by the sharing of electron pairs between nonmetals. VSEPR (Valence Shell Electron Pair Repulsion) theory predicts molecular shapes (linear, trigonal planar, tetrahedral, pyramidal, bent) based on repulsion between electron pairs.
            • Intermolecular Forces: Dipole-dipole forces, hydrogen bonding, and London dispersion forces determining physical properties of covalent substances.
            • Metallic Bonding: Described by the electron-sea model where valence electrons are delocalized across a lattice of positive metal ions, explaining electrical/thermal conductivity, malleability, and ductility.
            • Chemical Bonding Theories: Valence Bond (VB) theory involving orbital overlap and hybridization (sp, sp², sp³), and Molecular Orbital Theory (MOT) involving linear combination of atomic orbitals (LCAO).
            • Types of Crystals: Ionic, covalent network, molecular, and metallic crystals.
            """.trimIndent()
        )

        // Unit 3
        addNote(
            "Unit 3 - Physical States of Matter",
            "Kinetic Theory, Gases, Liquids, and Solids (Sections 3.1 - 3.5)",
            """
            • Kinetic Theory of Matter: Matter is composed of particles in constant random motion, with kinetic energy dependent on temperature.
            • Gaseous State and Gas Laws: Ideal gas behavior governed by Boyle's Law, Charles's Law, Gay-Lussac's Law, Avogadro's Law, and the Ideal Gas Equation (PV = nRT), alongside Dalton's Law of Partial Pressures and Kinetic Molecular Theory of Gases.
            • Liquid State: Properties of liquids including vapor pressure, surface tension, viscosity, and energy changes during phase transitions.
            • Solid State: Distinction between crystalline solids (orderly repeating lattices) and amorphous solids.
            """.trimIndent()
        )

        // Unit 4
        addNote(
            "Unit 4 - Chemical Kinetics",
            "Rate of a Reaction and Factors Affecting Reaction Rates (Sections 4.1 - 4.3)",
            """
            • Rate of a Reaction: Defined as the change in concentration of reactants or products per unit time (Rate = Δ[C]/Δt).
            • Collision Theory: Chemical reactions occur when reactant particles collide with sufficient activation energy (E_a) and proper spatial orientation.
            • Factors Affecting Reaction Rates: Nature of reactants, concentration/pressure, surface area, temperature (Arrhenius equation), and catalysts (which lower activation energy without being consumed).
            """.trimIndent()
        )

        // Unit 5
        addNote(
            "Unit 5 - Chemical Equilibrium",
            "Reversible Reactions, Equilibrium Constants, and Le-Chatelier's Principle (Sections 5.1 - 5.7)",
            """
            • Reversible and Irreversible Reactions: Chemical equilibrium is a dynamic state where the forward reaction rate equals the reverse reaction rate in a closed system.
            • Equilibrium Expression and Constant (K_c and K_p): Law of mass action relating equilibrium concentrations of products and reactants.
            • Applications of Equilibrium Constant: Predicting the extent of reactions and calculating equilibrium concentrations.
            • Le-Chatelier's Principle: If a stress (change in concentration, pressure, volume, or temperature) is applied to a system at equilibrium, the system shifts in a direction that relieves the stress.
            • Equilibrium in Industry: Application of chemical equilibrium principles in industrial processes such as the Haber process for ammonia synthesis.
            """.trimIndent()
        )

        // Unit 6
        addNote(
            "Unit 6 - Some Important Oxygen-Containing Organic Compounds",
            "Alcohols, Ethers, Aldehydes, Ketones, Carboxylic Acids, Esters, Fats, and Oils (Sections 6.1 - 6.6)",
            """
            • Alcohols and Ethers: Classification, IUPAC nomenclature, physical properties, preparation, and chemical reactions of hydroxyl (-OH) and ether (-O-) compounds.
            • Aldehydes and Ketones: Containing the carbonyl group (>C=O), nomenclature, physical properties, and oxidation/reduction reactions.
            • Carboxylic Acids: Containing the carboxyl group (-COOH), acidity, preparation, chemical properties, fatty acids, and their industrial and biological uses.
            • Esters: Formation via esterification (carboxylic acid + alcohol), nomenclature, properties, and applications in artificial flavors and fragrances.
            • Fats and Oils: Triglyceride structures, physical properties, hardening of oils (hydrogenation), and rancidity.
            """.trimIndent()
        )

        return notesList
    }
}