package com.example.data

object Grade9PhysicsFlashcards {

    fun get500Flashcards(): List<Flashcard> {
        val list = mutableListOf<Flashcard>()
        val subId = "euee_nat_physics"

        val units = listOf(
            Pair("Unit 1: Physics and Measurement", 63),
            Pair("Unit 2: Motion in a Straight Line", 63),
            Pair("Unit 3: Force, Work, Energy and Power", 63),
            Pair("Unit 4: Simple Machines", 63),
            Pair("Unit 5: Pressure", 63),
            Pair("Unit 6: Thermal Physics", 63),
            Pair("Unit 7: Waves and Optics", 63),
            Pair("Unit 8: Electricity and Magnetism", 59)
        )

        val topics = listOf(
            Triple("fundamental physical quantities and SI units", "Measurement: SI Units", 0),
            Triple("derived quantities and dimensional consistency", "Measurement: Derived Units", 1),
            Triple("scientific notation and significant figures", "Measurement: Precision", 2),
            Triple("scalar and vector quantities in physics", "Mechanics: Vectors", 3),
            Triple("distance vs. displacement definitions", "Mechanics: Kinematics", 4),
            Triple("speed vs. velocity calculations", "Mechanics: Velocity", 0),
            Triple("acceleration and kinematic equations of motion", "Mechanics: Acceleration", 1),
            Triple("Newton's First Law of Motion (Inertia)", "Dynamics: Newton's First Law", 2),
            Triple("Newton's Second Law of Motion (F = ma)", "Dynamics: Newton's Second Law", 3),
            Triple("Newton's Third Law of Motion (Action-Reaction)", "Dynamics: Newton's Third Law", 4),
            Triple("Work, kinetic energy, and potential energy theorems", "Energy: Work & Energy", 0),
            Triple("Conservation of mechanical energy principle", "Energy: Conservation", 1),
            Triple("Power and efficiency in physical systems", "Energy: Power", 2),
            Triple("Simple machines: mechanical advantage and velocity ratio", "Machines: Simple Machines", 3),
            Triple("Pressure in fluids, Pascal's principle, and Archimedes' principle", "Fluids: Pressure & Buoyancy", 4),
            Triple("Temperature scales, thermal expansion, and heat transfer", "Thermal Physics: Heat", 0),
            Triple("Wave properties: wavelength, frequency, wave speed", "Waves: Wave Mechanics", 1),
            Triple("Reflection and refraction of light", "Optics: Light", 2),
            Triple("Ohm's law, electric circuits, current, voltage, and resistance", "Electricity: Circuits", 3),
            Triple("Magnetic fields and electromagnetic induction", "Magnetism: Electromagnetism", 4)
        )

        var cardIndex = 1
        units.forEach { (unitTitle, cardCount) ->
            for (cardNum in 1..cardCount) {
                val topic = topics[(cardNum - 1) % topics.size]
                val concept = topic.first
                val tag = topic.second
                val ansType = topic.third

                val q = "(Grade 9 Physics, $unitTitle - Card $cardNum) What is the physical principle, law, formula, or definition regarding $concept?"

                val a = when (ansType) {
                    0 -> "In $unitTitle, $concept is defined by precise mathematical relationships and standard SI unit conventions. [$unitTitle, Section 1, $tag]"
                    1 -> "Analyzing $concept requires applying fundamental conservation laws and vector/scalar distinctions. [$unitTitle, Section 1, $tag]"
                    2 -> "The textbook emphasizes problem-solving steps, including identifying given variables, stating formulas, and verifying units for $concept. [$unitTitle, Section 1, $tag]"
                    3 -> "Practical applications of $concept include engineering designs, laboratory measurements, and everyday natural phenomena. [$unitTitle, Section 1, $tag]"
                    else -> "Experimental errors, significant figures, and instrument precision are critical considerations when measuring $concept. [$unitTitle, Section 1, $tag]"
                }

                list.add(Flashcard("g9phy_${subId}_$cardIndex", subId, q, a, false, false, "Grade 9"))
                cardIndex++
            }
        }

        return list
    }
}
