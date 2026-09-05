package com.example.data

object Grade11PhysicsNotes {

    fun getGrade11PhysicsNotes(): List<SubjectNote> {
        val notesList = mutableListOf<SubjectNote>()
        val subId = "euee_nat_physics"
        var idx = 1

        fun addNote(unit: String, title: String, content: String) {
            notesList.add(
                SubjectNote(
                    id = "g11_phy_note_${idx}",
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
            "Unit 1 - Physics and Human Society",
            "Nature of Physics, Branches, and Applications (Sections 1.1 - 1.4)",
            """
            • Definition and Nature of Physics: Physics is a fundamental branch of natural science that investigates the basic laws of nature, matter, energy, space, and time, seeking to understand how the universe behaves at microscopic and macroscopic scales.
            • Branches of Physics: Encompasses classical mechanics, thermodynamics, electromagnetism, optics, relativity, quantum mechanics, and nuclear physics.
            • Relationship with Other Disciplines: Physics provides the theoretical and quantitative foundation for chemistry, biology, astronomy, geology, and modern engineering.
            • Role in Society and Technology: Physics principles drive advancements in communications, medical imaging, transportation, energy generation, and electronics.
            """.trimIndent()
        )

        // Unit 2
        addNote(
            "Unit 2 - Vectors",
            "Revision on Vectors, Representation, and Vector Operations (Sections 2.1 - 2.3)",
            """
            • Scalars vs. Vectors: Scalars possess magnitude only (e.g., mass, time, speed), whereas vectors possess both magnitude and direction (e.g., displacement, velocity, force, acceleration).
            • Geometric and Algebraic Representation: Vectors are represented graphically by directed line segments (arrows) and algebraically by boldface letters or coordinate components in 2D/3D space (v = ⟨v_x, v_y, v_z⟩).
            • Vector Operations:
              - Vector Addition: Performed graphically via head-to-tail or parallelogram methods, and analytically by adding corresponding components (R = A + B = ⟨A_x + B_x, A_y + B_y⟩).
              - Scalar Multiplication: Scaling a vector by a real number k changes its magnitude by |k| and reverses its direction if k < 0.
              - Dot Product (Scalar Product): A · B = |A| |B| cos θ = A_x B_x + A_y B_y + A_z B_z.
              - Cross Product (Vector Product): A × B yields a vector orthogonal to both, with magnitude |A| |B| sin θ.
            """.trimIndent()
        )

        // Unit 3
        addNote(
            "Unit 3 - Motion in One and Two Dimensions",
            "Kinematics, Projectile Motion, and Relative Motion (Sections 3.1 - 3.4)",
            """
            • Kinematics in One Dimension: Study of straight-line motion under uniform and non-uniform acceleration using kinematic equations (v = u + at, s = ut + ½at², v² = u² + 2as).
            • Graphical Analysis: Position-time, velocity-time, and acceleration-time graphs where slopes represent rates of change and areas represent accumulated quantities.
            • Projectile Motion: Two-dimensional motion under the influence of gravity, analyzed by separating independent horizontal motion (constant velocity) and vertical motion (constant acceleration g).
            • Relative Velocity: Determining the velocity of an object relative to a moving reference frame (v_AB = v_AE - v_BE).
            """.trimIndent()
        )

        // Unit 4
        addNote(
            "Unit 4 - Dynamics",
            "Newton's Laws of Motion, Friction, Work, Energy, and Momentum (Sections 4.1 - 4.5)",
            """
            • Newton's Laws of Motion:
              - First Law: Law of inertia.
              - Second Law: Net force equals rate of change of momentum (F_net = ma).
              - Third Law: Action and reaction pairs (F_AB = -F_BA).
            • Friction: Static friction (f_s ≤ μ_s N) and kinetic friction (f_k = μ_k N) opposing relative surface motion.
            • Work, Energy, and Power: Work-energy theorem (W = ΔKE), conservation of mechanical energy in conservative systems, and power (P = W/t = F · v).
            • Momentum and Collisions: Linear momentum (p = mv), Law of Conservation of Momentum, elastic and inelastic collisions in one and two dimensions.
            • Circular Motion: Centripetal force and acceleration required for uniform circular motion (F_c = mv²/r).
            """.trimIndent()
        )

        // Unit 5
        addNote(
            "Unit 5 - Heat Conduction and Calorimetry",
            "Temperature, Thermal Expansion, Heat Transfer, and Calorimetry (Sections 5.1 - 5.5)",
            """
            • Temperature and Thermal Equilibrium: Zeroth Law of Thermodynamics and temperature scales (Celsius, Fahrenheit, Kelvin).
            • Thermal Expansion: Linear (ΔL = αL₀ΔT), area, and volume expansion of solids and liquids.
            • Heat Transfer Mechanisms: Conduction (Fourier's law), convection, and radiation (Stefan-Boltzmann law).
            • Specific Heat Capacity and Calorimetry: Quantifying heat exchange (Q = mcΔT) and phase changes involving latent heat (Q = mL) in isolated calorimetric systems.
            • First Law of Thermodynamics: Conservation of energy applied to thermodynamic systems (ΔU = Q - W).
            """.trimIndent()
        )

        // Unit 6
        addNote(
            "Unit 6 - Electrostatics and Electric Circuit",
            "Coulomb's Law, Electric Fields, Potentials, and Current Circuits (Sections 6.1 - 6.6)",
            """
            • Coulomb's Law: Electrostatic force between stationary point charges (F = k q₁q₂/r²).
            • Electric Field and Potential: Electric field intensity (E = F/q₀), electric potential energy, and electric potential (V = W/q).
            • Capacitance: Ability of a system to store electrical charge (Q = CV), parallel-plate capacitors, and dielectric materials.
            • Current Electricity: Electric current, resistance, Ohm's law, and resistivity (ρ = RA/L).
            • Direct Current Circuits: Resistors in series and parallel, Kirchhoff's junction and loop rules, and electrical power dissipation (P = I²R = V²/R).
            """.trimIndent()
        )

        // Unit 7
        addNote(
            "Unit 7 - Nuclear Physics",
            "Structure of the Nucleus, Radioactivity, Nuclear Reactions, and Applications (Sections 7.1 - 7.4)",
            """
            • Structure of the Nucleus: Nucleons (protons and neutrons), atomic number (Z), mass number (A), isotopic mass, and mass defect linked to binding energy via Einstein's mass-energy equivalence (E = Δmc²).
            • Radioactivity: Spontaneous decay of unstable atomic nuclei emitting alpha (α), beta (β), and gamma (γ) radiation; radioactive decay law and half-life (T_½ = ln 2/λ).
            • Nuclear Reactions: Nuclear fission (splitting of heavy nuclei) and nuclear fusion (combining light nuclei) releasing immense binding energy.
            • Applications and Radiation Safety: Utilization of radioisotopes in medicine, power generation, carbon dating, and radiation protection standards.
            """.trimIndent()
        )

        return notesList
    }
}