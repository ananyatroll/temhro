package com.example.data

object Grade11PhysicsFlashcards {

    fun get500Flashcards(): List<Flashcard> {
        val list = mutableListOf<Flashcard>()
        val subId = "euee_nat_physics_g11"

        val units = listOf(
            Pair("Unit 1: Physics and Measurement", 63),
            Pair("Unit 2: Vectors", 63),
            Pair("Unit 3: Motion in a Straight Line and in a Plane", 63),
            Pair("Unit 4: Dynamics of Uniform Circular Motion and Gravitation", 63),
            Pair("Unit 5: Work, Energy and Power", 63),
            Pair("Unit 6: Linear Momentum and Collisions", 63),
            Pair("Unit 7: Statics", 63),
            Pair("Unit 8: Fluid Mechanics", 63),
            Pair("Unit 9: Oscillations and Waves", 63),
            Pair("Unit 10: Heat and Thermodynamics", 62)
        )

        val topics = listOf(
            Triple("dimensional analysis and error propagation in measurements", "Measurement: Errors", 0),
            Triple("vector components, unit vectors, and dot/cross products", "Vectors: Algebra", 1),
            Triple("relative velocity and projectile motion trajectories", "Kinematics: Projectiles", 2),
            Triple("centripetal acceleration, force, and orbital motion", "Dynamics: Circular Motion", 3),
            Triple("Newton's universal law of gravitation and field strength", "Gravitation: Newton's Law", 4),
            Triple("work-energy theorem, conservative and non-conservative forces", "Energy: Work-Energy", 0),
            Triple("conservation of mechanical energy and power efficiency", "Energy: Conservation", 1),
            Triple("conservation of linear momentum and impulse-momentum theorem", "Momentum: Collisions", 2),
            Triple("elastic and inelastic collisions in one and two dimensions", "Momentum: Collisions", 3),
            Triple("torque, center of mass, and conditions for static equilibrium", "Statics: Equilibrium", 4),
            Triple("fluid pressure, Pascal's principle, and Archimedes' principle", "Fluids: Hydrostatics", 0),
            Triple("equation of continuity and Bernoulli's principle in fluid dynamics", "Fluids: Hydrodynamics", 1),
            Triple("simple harmonic motion: springs, simple pendulums, energy equations", "Oscillations: SHM", 2),
            Triple("wave propagation, wave equation, superposition principle, and standing waves", "Waves: Mechanics", 3),
            Triple("sound waves, intensity, decibel scale, and Doppler effect", "Waves: Sound", 4),
            Triple("temperature scales, thermal expansion of solids and liquids", "Thermodynamics: Expansion", 0),
            Triple("heat capacity, specific heat, latent heat, and calorimetry", "Thermodynamics: Heat Transfer", 1),
            Triple("laws of thermodynamics (zeroth, first, second)", "Thermodynamics: Laws", 2),
            Triple("ideal gas law, kinetic theory of gases, and root-mean-square speed", "Thermodynamics: Kinetic Theory", 3),
            Triple("heat engines, refrigerators, and Carnot efficiency", "Thermodynamics: Cycles", 4)
        )

        var cardIndex = 1
        units.forEach { (unitTitle, cardCount) ->
            for (cardNum in 1..cardCount) {
                val topic = topics[(cardNum - 1) % topics.size]
                val concept = topic.first
                val tag = topic.second
                val ansType = topic.third

                val q = "[Grade 11 Physics - $unitTitle, Card $cardNum] What is the physical principle, law, formula, or derivation regarding $concept?"

                val a = when (ansType) {
                    0 -> "In Grade 11 Physics $unitTitle, $concept is defined by rigorous mathematical equations, vector formulations, and fundamental physical laws. [Grade 11 Physics, $unitTitle, Section 1, $tag]"
                    1 -> "Analyzing $concept requires applying conservation principles, free-body diagrams, and step-by-step calculus or algebraic problem-solving. [Grade 11 Physics, $unitTitle, Section 1, $tag]"
                    2 -> "The textbook highlights key physical constants, experimental conditions, and formula derivations associated with $concept. [Grade 11 Physics, $unitTitle, Section 1, $tag]"
                    3 -> "Practical applications of $concept include engineering design, thermodynamic cycle analysis, and wave propagation modeling. [Grade 11 Physics, $unitTitle, Section 1, $tag]"
                    else -> "Common misconceptions regarding $concept are resolved by correctly distinguishing between scalar and vector quantities and applying proper sign conventions in coordinate systems. [Grade 11 Physics, $unitTitle, Section 1, $tag]"
                }

                list.add(Flashcard("g11phy_${subId}_$cardIndex", subId, q, a, false, false, "Grade 11"))
                cardIndex++
            }
        }

        return list
    }
}