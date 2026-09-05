package com.example.data

object Grade12PhysicsNotes {

    fun getGrade12PhysicsNotes(): List<SubjectNote> {
        val notesList = mutableListOf<SubjectNote>()
        val subId = "euee_nat_physics"
        var idx = 1

        fun addNote(unit: String, title: String, content: String) {
            notesList.add(
                SubjectNote(
                    id = "g12_phy_note_${idx}",
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
            "Unit 1 - Application of Physics in Other Fields",
            "Physics and Other Sciences / Engineering / Medical Physics and Defense",
            """
            • Physics provides the fundamental understanding of matter and electricity necessary for chemistry, including atomic structure, chemical bonds, and crystal structure.
            • Spectroscopy is a collaborative tool used by both physicists and chemists to study the structure of atoms and molecules.
            • Physics explains biological processes such as human motion (mechanics), metabolism (energetics), and blood flow (fluid dynamics).
            • Newtonian mechanics explains animal stability: a body is stable if its center of mass is directly over its base of support.
            • Astronomy uses Newton's law of gravitation to describe planetary and lunar orbits.
            • Astronomers use telescopes to detect different parts of the electromagnetic spectrum (radio, infrared, optical, etc.) to study stars and the universe.
            • Chemical engineering applies laws of physical chemistry and physics (e.g., molecular dynamics, thermodynamics) to design production processes for plastics, petroleum, and detergents.
            • X-ray imaging works by detecting the absorption of X-rays in body tissues; denser elements like calcium in bones block X-rays (appearing white), while less dense areas like lungs appear dark.
            • Computed Tomography (CT) scans use rotating X-ray machines to create detailed successive images (tomograms).
            • Night vision technology uses infrared detection to identify objects based on heat emissions, as all objects give off infrared light proportional to their temperature.
            """.trimIndent()
        )

        // Unit 2
        addNote(
            "Unit 2 - Two-dimensional Motion",
            "Projectile Motion / Rotational Motion and Dynamics",
            """
            • Projectile motion involves an object in flight with constant horizontal velocity and constant vertical acceleration due to gravity.
            • Horizontal displacement: Δx = v₀ * cos(θ) * t.
            • Vertical displacement: Δy = v₀ * sin(θ) * t - (1/2)gt².
            • Maximum Height (H): H = (v₀² * sin² θ) / (2g).
            • Time to reach maximum height: t = (v₀ * sin θ) / g.
            • Angular rotation (Δθ) is the rotational equivalent of linear distance (Δs).
            • Angular velocity (ω) is the equivalent of linear velocity (v).
            • Angular acceleration (α) is the equivalent of linear acceleration (a).
            • Relationship between linear and angular speed: v = ω * r.
            • Rotational Kinematic Equation: ω_f² = ω₀² + 2αΔθ.
            • Torque (τ) and angular acceleration (α): τ = Iα, where I is the moment of inertia.
            """.trimIndent()
        )

        // Unit 3
        addNote(
            "Unit 3 - Fluid Mechanics",
            "Fluid Statics and Pressure / Archimedes' Principle",
            """
            • Density (ρ) is mass per unit volume (ρ = m/V).
            • Pressure (P) is force per unit perpendicular area (P = F/A).
            • Pressure due to liquid weight: P = hρg.
            • Pascal's Law: A change in pressure applied to an enclosed fluid is transmitted undiminished to all portions of the fluid and the container walls.
            • Absolute Pressure: P_abs = P_atm + P_gauge.
            • Buoyant Force (F_B): The net upward force exerted by a fluid on an immersed object.
            • Archimedes' Principle: The buoyant force on an object equals the weight of the fluid it displaces (F_B = W_fluid).
            • Floating Condition: An object floats if the buoyant force is greater than or equal to its weight.
            """.trimIndent()
        )

        // Unit 4
        addNote(
            "Unit 4 - Electromagnetism",
            "Magnetic Fields / Electromagnetic Induction and Generators",
            """
            • Magnetic field (B) around a long straight current-carrying wire: B = (μ₀ * I) / (2π * r).
            • Permeability of free space (μ₀): 4π x 10⁻⁷ T·m/A.
            • Fleming's Right Hand Rule: If the thumb points in the direction of current, the wrapped fingers show the direction of magnetic field lines.
            • Units: The SI unit of magnetic field is Tesla (T); 1 T = 10,000 Gauss (G).
            • AC Generator: Converts mechanical energy into electrical energy using Faraday's Law of induction.
            • Faraday's Law: A changing magnetic flux through a coil generates an alternating electromotive force (emf).
            • Transformers: Devices used to change voltage; V_s / V_p = N_s / N_p, where N is the number of turns.
            """.trimIndent()
        )

        // Unit 5
        addNote(
            "Unit 5 - Basics of Electronics",
            "Semiconductors / P-N Junctions and Diodes / Transistors",
            """
            • Intrinsic Semiconductors: Pure materials like silicon and germanium with no added impurities.
            • Extrinsic Semiconductors: Materials with added impurities (doping) to improve conductivity.
            • Doping: The process of adding a dopant to a pure semiconductor.
            • Charge Carriers: Current in semiconductors is carried by free electrons and holes (absence of electrons).
            • P-N Junction: Formed by joining P-type and N-type materials, creating a depletion region and barrier potential.
            • Barrier Potential: Approx. 0.7V for silicon and 0.3V for germanium.
            • Forward Bias: Positive terminal to P-side, negative to N-side; allows current to flow.
            • Reverse Bias: Prevents current flow by widening the depletion region.
            • Bipolar Junction Transistors (BJT): Consist of three regions - Emitter (E), Base (B), and Collector (C).
            • Types: NPN and PNP transistors.
            • Current Relation: Emitter current is the sum of base and collector currents (I_E = I_B + I_C).
            • Operation: In an NPN transistor, the emitter pushes electrons into the base, where most are collected by the collector.
            """.trimIndent()
        )

        return notesList
    }
}