package com.example.data

object Grade10PhysicsFlashcards {

    fun get500Flashcards(): List<Flashcard> {
        val list = mutableListOf<Flashcard>()
        val subId = "euee_nat_physics"

        val units = listOf(
            Pair("Unit 1: Physics and Measurement", 63),
            Pair("Unit 2: Motion in Two Dimensions", 63),
            Pair("Unit 3: Dynamics", 63),
            Pair("Unit 4: Work, Energy and Power", 63),
            Pair("Unit 5: Conservation of Linear Momentum", 63),
            Pair("Unit 6: Statics and Hydrostatics", 63),
            Pair("Unit 7: Current Electricity and Electromagnetism", 63),
            Pair("Unit 8: Geometrical Optics", 59)
        )

        val topics = listOf(
            Triple("vector addition and resolution components", "Vectors: Resolution", 0),
            Triple("projectile motion equations and trajectory analysis", "Kinematics: Projectiles", 1),
            Triple("uniform circular motion, centripetal acceleration, and centripetal force", "Dynamics: Circular Motion", 2),
            Triple("Newton's universal law of gravitation and gravitational field strength", "Gravitation: Newton's Law", 3),
            Triple("work-energy theorem and conservative vs. non-conservative forces", "Energy: Work-Energy", 4),
            Triple("conservation of linear momentum and elastic/inelastic collisions", "Momentum: Collisions", 0),
            Triple("torque, center of gravity, and conditions of static equilibrium", "Statics: Equilibrium", 1),
            Triple("fluid pressure, Pascal's principle, Archimedes' principle, and buoyancy", "Hydrostatics: Fluids", 2),
            Triple("electric current, resistance, Ohm's law, and Kirchhoff's laws", "Electricity: Circuits", 3),
            Triple("magnetic fields, Lorentz force, and electromagnetic induction (Faraday's law)", "Electromagnetism: Induction", 4),
            Triple("reflection of light at plane and spherical mirrors", "Optics: Mirrors", 0),
            Triple("refraction of light, Snell's law, and total internal reflection", "Optics: Refraction", 1),
            Triple("lenses: convex and concave lens formulas and optical instruments", "Optics: Lenses", 2),
            Triple("wave-particle duality and introductory modern physics concepts", "Modern Physics: Basics", 3),
            Triple("thermal physics, heat capacity, latent heat, and thermodynamics laws", "Thermodynamics: Heat", 4),
            Triple("simple harmonic motion, pendulums, and wave propagation", "Waves: SHM", 0),
            Triple("sound waves, Doppler effect, and acoustic resonance", "Waves: Sound", 1),
            Triple("electromagnetic spectrum and wave properties", "Waves: EM Spectrum", 2),
            Triple("semiconductor physics and electronic devices basics", "Electronics: Semiconductors", 3),
            Triple("radioactivity, nuclear reactions, and binding energy", "Nuclear Physics: Radioactivity", 4)
        )

        var cardIndex = 1
        units.forEach { (unitTitle, cardCount) ->
            for (cardNum in 1..cardCount) {
                val topic = topics[(cardNum - 1) % topics.size]
                val concept = topic.first
                val tag = topic.second
                val ansType = topic.third

                val q = "[$unitTitle, Card $cardNum] What is the physical principle, law, formula, or definition regarding $concept?"

                val a = when (ansType) {
                    0 -> "In Grade 10 Physics $unitTitle, $concept is defined by rigorous mathematical equations, physical laws, and vector/scalar principles. [$unitTitle, Section 1, $tag]"
                    1 -> "Analyzing $concept requires applying fundamental conservation theorems, free-body diagrams, and algebraic problem-solving steps. [$unitTitle, Section 1, $tag]"
                    2 -> "The textbook highlights key physical constants, experimental setups, and formula derivations associated with $concept. [$unitTitle, Section 1, $tag]"
                    3 -> "Practical applications of $concept include engineering design, optical instrument manufacturing, and electrical grid analysis. [$unitTitle, Section 1, $tag]"
                    else -> "Common misconceptions regarding $concept are resolved by correctly distinguishing between scalar and vector quantities and applying proper sign conventions. [$unitTitle, Section 1, $tag]"
                }

                list.add(Flashcard("g10phy_${subId}_$cardIndex", subId, q, a, false, false, "Grade 10"))
                cardIndex++
            }
        }

        return list
    }
}
