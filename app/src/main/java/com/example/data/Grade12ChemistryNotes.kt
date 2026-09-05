package com.example.data

object Grade12ChemistryNotes {

    fun getGrade12ChemistryNotes(): List<SubjectNote> {
        val notesList = mutableListOf<SubjectNote>()
        val subId = "euee_nat_chem"
        var idx = 1

        fun addNote(unit: String, title: String, content: String) {
            notesList.add(
                SubjectNote(
                    id = "g12_chem_note_${idx}",
                    subjectId = subId,
                    unit = unit,
                    title = title,
                    content = content,
                    gradeLevel = "Grade 12"
                )
            )
            idx++
        }

        // Unit 1
        addNote(
            "Unit 1 - Acid-Base Equilibria",
            "Acid-Base Concepts / Ionic Equilibria of Weak Acids and Bases / Common Ion Effect and Buffer Solution / Hydrolysis of Salts / Acid-Base Indicators and Titrations",
            """
            • Arrhenius Concept: An acid produces H+ ions and a base produces OH- ions in aqueous solution.
            • Brønsted-Lowry Concept: An acid is a proton (H+) donor, and a base is a proton acceptor.
            • Conjugate Acid-Base Pairs: An acid and its conjugate base differ by a single proton (H+). The conjugate base has one fewer H and one more minus charge than the acid.
            • Lewis Concept: A Lewis acid is an electron-pair acceptor, and a Lewis base is an electron-pair donor.
            • Amphiprotic Species: Substances that can act as both an acid and a base by either donating or accepting a proton (e.g., H₂O, NH₃).
            • Ion Product of Water (Kw): Pure water auto-ionizes slightly; Kw = [H₃O+][OH-] = 1.0 x 10⁻¹⁴ at 25°C.
            • pH and pOH: pH = -log[H₃O+]; pOH = -log[OH-]. In neutral solution, pH = pOH = 7.
            • Acid-Dissociation Constant (Ka): A measure of acid strength; larger Ka indicates a stronger weak acid.
            • Percent Ionization: (Ionized acid concentration at equilibrium / Initial concentration of acid) x 100%. It increases with acid/base strength.
            • Common Ion Effect: The shift in equilibrium caused by the addition of a compound having an ion in common with the dissolved substance.
            • Buffer Solution: A mixture of a weak acid and its conjugate base (or weak base and conjugate acid) that resists changes in pH when small amounts of acid or base are added.
            • Salt Hydrolysis: The reaction of an anion or a cation of a salt, or both, with water. Salts of strong acids and strong bases (e.g., NaCl) do not undergo hydrolysis (pH = 7). Salts of weak acids and strong bases (e.g., CH₃COONa) form basic solutions (pH > 7). Salts of strong acids and weak bases (e.g., NH₄Cl) form acidic solutions (pH < 7).
            • Indicators: Substances that change color at a specific pH range (e.g., Methyl orange: 3.2-4.4; Phenolphthalein: 8.2-10.0).
            • Titration: A technique where a solution of known concentration is used to determine the concentration of an unknown solution.
            """.trimIndent()
        )

        // Unit 2
        addNote(
            "Unit 2 - Electrochemistry",
            "Oxidation-Reduction Reactions / Electrolysis of Aqueous Solutions / Quantitative Aspects of Electrolysis / Voltaic Cells",
            """
            • Oxidation: A process in which an atom loses electrons, resulting in an increase in its oxidation number.
            • Reduction: A process in which an atom gains electrons, resulting in a decrease in its oxidation number.
            • Redox Reactions: Reactions involving both oxidation and reduction occurring simultaneously.
            • Oxidizing Agent: The substance that is reduced (gains electrons). Reducing Agent: The substance that is oxidized (loses electrons).
            • Electrolysis: The process of using electrical energy to drive a non-spontaneous chemical reaction.
            • Preferential Discharge: When multiple ions are present, the ion lower in the electrochemical series is discharged preferentially at the electrode.
            • Electrolysis of Brine (Concentrated NaCl): Produces H₂ gas at the cathode, Cl₂ gas at the anode, and NaOH as a byproduct.
            • Faraday's First Law: The mass of a substance liberated at an electrode is directly proportional to the quantity of electricity passed through the electrolyte (m = ZIt).
            • Faraday's Second Law: The masses of different substances liberated by the same amount of electricity are proportional to their equivalent masses.
            • Voltaic (Galvanic) Cell: An electrochemical cell that uses a spontaneous redox reaction to generate electricity.
            • Cell Notation: Conventional representation (e.g., Zn(s)|Zn²+(aq)||Cu²+(aq)|Cu(s)). Anode is on the left, cathode is on the right.
            • Standard Cell Potential (E°cell): The potential difference between two electrodes under standard conditions; E°cell = E°cathode - E°anode.
            • Nernst Equation: Relates cell potential to the concentrations of reactants and products under non-standard conditions.
            """.trimIndent()
        )

        // Unit 3
        addNote(
            "Unit 3 - Industrial Chemistry",
            "Manufacturing of Valuable Products / Manufacturing Industries in Ethiopia",
            """
            • Ammonia (NH₃): Produced industrially by the Haber-Bosch process (direct combination of N₂ and H₂ under high pressure with a catalyst).
            • Nitric Acid (HNO₃): Produced by the three-step Ostwald process (oxidation of ammonia to NO, then NO₂, then reaction with water).
            • Sulphuric Acid (H₂SO₄): Produced by the Contact process.
            • Sodium Carbonate (Na₂CO₃): Manufactured by the Solvay process using brine, limestone, and ammonia.
            • Sodium Hydroxide (NaOH): Produced by the electrolysis of brine (NaCl solution).
            • Glass: An amorphous solid containing silica (SiO₂) as the main component. Soda-lime glass is a mixture of sodium and calcium silicates.
            • Cement: Major raw materials in Ethiopia include limestone, clay, silica sand, gypsum, and pumice.
            • Tanning: The process of converting raw animal hides and skins into leather using tannin.
            • Saponification: The process of making soap by reacting long-chain fatty acids (fats/oils) with a strong base like NaOH or KOH.
            """.trimIndent()
        )

        // Unit 4
        addNote(
            "Unit 4 - Polymers",
            "Polymerization Reactions / Classification of Polymers",
            """
            • Polymerization: The chemical change by which monomer units combine to form a large molecule (polymer).
            • Addition Polymerization: Monomers add one at a time to a growing chain without the loss of any small molecules (e.g., Polyethylene, PVC, Teflon).
            • Condensation Polymerization: Monomers combine with the elimination of a small molecule like water (e.g., Nylons, Polyesters, Proteins).
            • Natural Polymers: Occur in nature (e.g., Cellulose, Starch, Wool, Silk, DNA).
            • Synthetic Polymers: Man-made (e.g., Plastics, Nylon, Bakelite).
            • Thermoplastics: Soften when heated and can be remolded/recycled (e.g., Polyethylene).
            • Thermosetting Polymers: Do not soften on heating and cannot be remolded (e.g., Bakelite).
            """.trimIndent()
        )

        // Unit 5
        addNote(
            "Unit 5 - Introduction to Environmental Chemistry",
            "Components and Cycles of the Environment / Environmental Pollution / Green Chemistry",
            """
            • Natural Cycles: The environment circulates essential constituents through cycles like the hydrologic (water), oxygen, nitrogen, and carbon cycles.
            • Lithosphere: The earth's crust consisting of rocks and soil; site of chemical weathering and mineral oxidation/reduction.
            • Air Pollution: Caused by pollutants like SO₂, NOx, CO, and CFCs. SO₂ and NOx contribute to acid rain (H₂SO₄, HNO₃).
            • Water Pollution: Caused by domestic sewage, solid waste, and fertilizers (nitrates/phosphates).
            • Eutrophication: Excessive plant growth in water bodies due to nutrient enrichment, leading to oxygen depletion and death of aquatic life.
            • Global Warming: Caused by the greenhouse effect where gases like CO₂ and water vapor trap infrared radiation.
            • Green Chemistry: Designing chemical products and processes that reduce or eliminate the use and generation of hazardous substances.
            • Atom Economy: A principle of green chemistry that aims to maximize the incorporation of all materials used in a process into the final product.
            """.trimIndent()
        )

        return notesList
    }
}