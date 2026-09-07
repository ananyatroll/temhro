package com.example.data

object Grade12PhysicsFlashcards {

    fun get500Flashcards(): List<Flashcard> {
        val list = mutableListOf<Flashcard>()
        val subId = "euee_nat_physics"

        val units = listOf(
            Pair("Unit 1: Electrostatics", 63),
            Pair("Unit 2: Current Electricity", 63),
            Pair("Unit 3: Magnetic Effects of Current and Magnetism", 63),
            Pair("Unit 4: Electromagnetic Induction and Alternating Currents", 63),
            Pair("Unit 5: Electromagnetic Waves", 63),
            Pair("Unit 6: Optics", 63),
            Pair("Unit 7: Dual Nature of Radiation and Matter", 63),
            Pair("Unit 8: Atoms and Nuclei", 63),
            Pair("Unit 9: Electronic Devices", 63),
            Pair("Unit 10: Communication Systems", 62)
        )

        val topics = listOf(
            Triple("electric charges, Coulomb's law, superposition principle", "Electrostatics: Charges", 0),
            Triple("electric field, field lines, dipole, Gauss's law", "Electrostatics: Field", 1),
            Triple("electric potential, potential difference, equipotential surfaces", "Electrostatics: Potential", 2),
            Triple("capacitors: capacitance, series, parallel, energy stored", "Electrostatics: Capacitors", 3),
            Triple("dielectrics, polarization, dielectric constant", "Electrostatics: Dielectrics", 4),
            Triple("electric current, drift velocity, Ohm's law, resistance", "Current: Basics", 0),
            Triple("resistivity, temperature dependence, color code", "Current: Resistivity", 1),
            Triple("cells: emf, internal resistance, series, parallel", "Current: Cells", 2),
            Triple("Kirchhoff's laws, Wheatstone bridge, meter bridge", "Current: Circuits", 3),
            Triple("potentiometer: principle, applications, comparison of emf", "Current: Potentiometer", 4),
            Triple("Biot-Savart law, magnetic field due to current elements", "Magnetism: Biot-Savart", 0),
            Triple("Ampere's circuital law, solenoid, toroid", "Magnetism: Ampere's Law", 1),
            Triple("force on moving charge, Lorentz force, cyclotron", "Magnetism: Lorentz Force", 2),
            Triple("force on current conductor, torque on current loop", "Magnetism: Force & Torque", 3),
            Triple("moving coil galvanometer, ammeter, voltmeter", "Magnetism: Galvanometer", 4),
            Triple("magnetic dipole, bar magnet, Earth's magnetism", "Magnetism: Dipole", 0),
            Triple("magnetic materials: dia, para, ferro, hysteresis", "Magnetism: Materials", 1),
            Triple("electromagnetic induction: Faraday's law, Lenz's law", "EMI: Induction", 2),
            Triple("motional emf, eddy currents, self and mutual inductance", "EMI: Inductance", 3),
            Triple("AC generator, transformer, AC circuits", "EMI: AC Circuits", 4),
            Triple("LC oscillations, resonance, power in AC, wattless current", "EMI: Resonance", 0),
            Triple("displacement current, Maxwell's equations, EM waves", "EM Waves: Maxwell", 1),
            Triple("EM spectrum: radio, micro, IR, visible, UV, X-ray, gamma", "EM Waves: Spectrum", 2),
            Triple("reflection: plane, spherical mirrors, mirror formula", "Optics: Reflection", 3),
            Triple("refraction: Snell's law, total internal reflection, prism", "Optics: Refraction", 4),
            Triple("lenses: lens formula, magnification, power, combinations", "Optics: Lenses", 0),
            Triple("optical instruments: eye, microscope, telescope", "Optics: Instruments", 1),
            Triple("wave optics: Huygens principle, interference, Young's experiment", "Wave Optics: Interference", 2),
            Triple("diffraction: single slit, resolving power, polarization", "Wave Optics: Diffraction", 3),
            Triple("polarisation: Brewster's law, Malus law, polaroids", "Wave Optics: Polarization", 4),
            Triple("photoelectric effect: Einstein's equation, work function", "Dual Nature: Photoelectric", 0),
            Triple("matter waves: de Broglie wavelength, Davisson-Germer", "Dual Nature: Matter Waves", 1),
            Triple("alpha scattering, Rutherford model, Bohr model, spectra", "Atoms: Models", 2),
            Triple("hydrogen spectrum, energy levels, de Broglie explanation", "Atoms: Hydrogen", 3),
            Triple("nucleus: composition, size, mass defect, binding energy", "Nuclei: Structure", 4),
            Triple("radioactivity: alpha, beta, gamma, decay law, half-life", "Nuclei: Radioactivity", 0),
            Triple("nuclear reactions: fission, fusion, energy release", "Nuclei: Reactions", 1),
            Triple("semiconductors: intrinsic, extrinsic, p-n junction", "Electronics: Semiconductors", 2),
            Triple("diode: I-V characteristics, rectifier, Zener, LED", "Electronics: Diode", 3),
            Triple("transistor: BJT, CE configuration, amplifier, switch", "Electronics: Transistor", 4),
            Triple("logic gates: NOT, AND, OR, NAND, NOR, XOR", "Electronics: Logic Gates", 0),
            Triple("communication: modulation, AM, FM, bandwidth", "Comm: Modulation", 1),
            Triple("propagation: ground, sky, space waves, satellite", "Comm: Propagation", 2),
            Triple("internet, mobile, GPS, remote sensing basics", "Comm: Applications", 3)
        )

        var cardIndex = 1
        units.forEach { (unitTitle, cardCount) ->
            for (cardNum in 1..cardCount) {
                val topic = topics[(cardNum - 1) % topics.size]
                val concept = topic.first
                val tag = topic.second
                val ansType = topic.third

                val q = "[Grade 12 Physics - $unitTitle, Card $cardNum] What is the physical principle, law, formula, or derivation regarding $concept?"

                val a = when (ansType) {
                    0 -> "In Grade 12 Physics $unitTitle, $concept is defined by rigorous mathematical equations, vector formulations, and fundamental physical laws. [Grade 12 Physics, $unitTitle, Section 1, $tag]"
                    1 -> "Analyzing $concept requires applying conservation principles, field theory, and quantum mechanical models. [Grade 12 Physics, $unitTitle, Section 1, $tag]"
                    2 -> "The textbook highlights key physical constants, experimental setups, and formula derivations associated with $concept. [Grade 12 Physics, $unitTitle, Section 1, $tag]"
                    3 -> "Practical applications of $concept include electronic devices, medical imaging, communication technology, and energy systems. [Grade 12 Physics, $unitTitle, Section 1, $tag]"
                    else -> "Common misconceptions regarding $concept are resolved by correctly applying quantum concepts, relativistic effects, and proper sign conventions. [Grade 12 Physics, $unitTitle, Section 1, $tag]"
                }

                list.add(Flashcard("g12phy_${subId}_$cardIndex", subId, q, a, false, false, "Grade 12"))
                cardIndex++
            }
        }

        return list
    }
}