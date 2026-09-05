package com.example.data

object Grade10PhysicsNotes {

    fun getGrade10PhysicsNotes(): List<SubjectNote> {
        val notesList = mutableListOf<SubjectNote>()
        val subId = "euee_nat_physics"
        var idx = 1

        fun addNote(unit: String, title: String, content: String) {
            notesList.add(
                SubjectNote(
                    id = "g10_phy_note_${idx}",
                    subjectId = subId,
                    unit = unit,
                    title = title,
                    content = content,
                    gradeLevel = "Grade 10"
                )
            )
            idx++
        }

        addNote(
            "Unit 1 - Vector Quantities",
            "Scalars, Vectors, and Vector Operations (Sections 1.1 - 1.3)",
            """
            • Scalars vs. Vectors: Scalars have magnitude only (mass, temperature, speed); Vectors have magnitude and direction (displacement, velocity, force, acceleration).
            • Vector Representation: Graphical arrows (length = magnitude, head = direction) or bold/arrow notation.
            • Vector Addition:
              - Graphical: Head-to-tail and Parallelogram methods.
              - Analytical (Components): A_x = A cos θ, A_y = A sin θ. Resultant R = √(R_x² + R_y²), tan θ = R_y / R_x.
            """.trimIndent()
        )

        addNote(
            "Unit 2 - Uniformly Accelerated Motion",
            "Kinematics and Free Fall (Sections 2.1 - 2.3)",
            """
            • Kinematic Equations for Constant Acceleration a:
              1. v = u + at
              2. s = ut + ½at²
              3. v² = u² + 2as
              4. s = ½(u + v)t
            • Free Fall: Acceleration due to gravity g ≈ 9.8 m/s² downwards (neglecting air resistance).
            """.trimIndent()
        )

        addNote(
            "Unit 3 - Dynamics",
            "Newton's Laws of Motion and Friction (Sections 3.1 - 3.3)",
            """
            • Newton's 1st Law (Inertia): An object remains at rest or in uniform linear motion unless acted upon by a net force.
            • Newton's 2nd Law: F_net = ma.
            • Newton's 3rd Law: Action-reaction pair F_AB = -F_BA.
            • Friction: Static (f_s ≤ μ_s N) and Kinetic (f_k = μ_k N) friction opposing relative motion.
            """.trimIndent()
        )

        addNote(
            "Unit 4 - Static and Current Electricity",
            "Electrostatics, Ohm's Law, and Circuits (Sections 4.1 - 4.4)",
            """
            • Charge & Coulomb's Law: Q = ne, F = k (q₁q₂ / r²).
            • Electric Current & Resistance: I = Q/t. Ohm's Law: V = IR.
            • Resistors in Series (R_eq = R₁ + R₂ + ...) vs. Parallel (1/R_eq = 1/R₁ + 1/R₂ + ...).
            • Electric Power: P = IV = I²R = V²/R. Energy E = P·t.
            """.trimIndent()
        )

        addNote(
            "Unit 5 - Magnetism",
            "Magnetic Fields and Electromagnetism (Sections 5.1 - 5.3)",
            """
            • Magnetic Poles & Fields: N and S poles, field lines from North to South.
            • Oersted's Discovery: Moving electric charges / currents produce magnetic fields.
            • Electromagnetic Induction: Faraday's Law - changing magnetic flux induces an electromotive force (emf) in a circuit.
            """.trimIndent()
        )

        return notesList
    }
}
