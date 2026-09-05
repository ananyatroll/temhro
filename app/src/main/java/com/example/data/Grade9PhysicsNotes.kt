package com.example.data

object Grade9PhysicsNotes {

    fun getGrade9PhysicsNotes(): List<SubjectNote> {
        val notesList = mutableListOf<SubjectNote>()
        val subId = "euee_nat_physics"
        var idx = 1

        fun addNote(unit: String, title: String, content: String) {
            notesList.add(
                SubjectNote(
                    id = "g9_phy_note_${idx}",
                    subjectId = subId,
                    unit = unit,
                    title = title,
                    content = content,
                    gradeLevel = "Grade 9"
                )
            )
            idx++
        }

        // Unit 1
        addNote(
            "Unit 1 - Physics and Human Society",
            "Definition and Nature of Physics (Section 1.1)",
            """
            • Physics is a branch of natural science aimed at describing the fundamental aspects of our universe, deriving its name from the Greek word "phusis", meaning nature (Chapter 1, Section 1.1, Page 2).
            • Physics attempts to describe the basic mechanisms that make our universe behave the way it does, including the properties of matter, energy, and natural phenomena (Chapter 1, Section 1.1, Page 2).
            • A person who studies physics is called a physicist (Chapter 1, Section 1.1, Page 2).
            • Applications and Careers: Physics principles underpin modern technologies (computers, smartphones, historical heritages) and fields such as transportation, aviation, medicine, forensic science, and meteorology (Chapter 1, Section 1.1, Pages 2–3).
            """.trimIndent()
        )

        addNote(
            "Unit 1 - Physics and Human Society",
            "Branches of Physics (Section 1.2)",
            """
            • Mechanics: Deals with the motion of objects with or without reference to force, divided into classical mechanics (laws of motion and macroscopic physical objects) and quantum mechanics (behavior of microscopic particles like protons, neutrons, and electrons) (Chapter 1, Section 1.2, Table 1.1, Page 4).
            • Acoustics: Deals with the study of sound and its transmission, production, and effects (Chapter 1, Section 1.2, Table 1.1, Page 4).
            • Optics: Deals with the behavior, propagation, and properties of light (Chapter 1, Section 1.2, Table 1.1, Page 4).
            • Thermodynamics: Studies thermal energy and the transfer of heat (Chapter 1, Section 1.2, Table 1.1, Page 4).
            • Electromagnetism: Deals with the study of electromagnetic force, electric currents, and magnetic fields (Chapter 1, Section 1.2, Table 1.1, Page 4).
            • Nuclear Physics: Deals with the structure, properties, and reactions of the nuclei of atoms (Chapter 1, Section 1.2, Page 5).
            • Astrophysics: Employs the methods and principles of physics to study astronomical objects and phenomena (Chapter 1, Section 1.2, Page 5).
            """.trimIndent()
        )

        addNote(
            "Unit 1 - Physics and Human Society",
            "Related Fields to Physics (Section 1.3)",
            """
            • Physics serves as the foundational bedrock for many scientific and technical disciplines (Chapter 1, Section 1.3, Page 5).
            • Chemistry: Relies on atomic and molecular physics to explain interactions of atoms and molecules (Chapter 1, Section 1.3, Page 5).
            • Engineering and Architecture: Determines structural stability, acoustics, heating, lighting, and cooling for buildings and infrastructure (Chapter 1, Section 1.3, Page 5).
            • Geology and Geophysics: Utilizes physics for radioactive dating, earthquake analysis, and heat transfer across Earth's surface (Chapter 1, Section 1.3, Pages 5–6).
            • Biophysics and Medical Physics: Applies physical principles and methods to study biological phenomena and medical imaging/treatments (Chapter 1, Section 1.3, Page 5).
            """.trimIndent()
        )

        addNote(
            "Unit 1 - Physics and Human Society",
            "Historical Issues and Contributors (Section 1.3 / 1.4)",
            """
            • Michael Faraday: Contributed significantly to electromagnetism by inventing the first electric generator in 1831 and producing mechanical motion using a magnet and electric current (Chapter 1, Section 1.4, Page 6).
            • James Prescott Joule: Studied heat and mechanical work, establishing the law of conservation of energy and the foundation for thermodynamics (Chapter 1, Section 1.4, Page 6).
            • Marie Curie: Conducted pioneering research in nuclear physics and radioactivity, discovering polonium and radium (Chapter 1, Section 1.4, Page 6).
            • Albert Einstein: Developed the theory of relativity and contributed to quantum mechanics, revolutionizing modern physics (Chapter 1, Section 1.4, Page 6).
            """.trimIndent()
        )

        // Unit 2
        addNote(
            "Unit 2 - Measurement",
            "Physical Quantities and Units (Section 2.1)",
            """
            • A physical quantity is a property of an object or a phenomenon that can be measured numerically and consists of a numerical magnitude and a unit (Chapter 2, Section 2.1).
            • Fundamental (Base) Quantities: Quantities that cannot be defined in terms of other quantities, such as length, mass, time, electric current, temperature, amount of substance, and luminous intensity (Chapter 2, Section 2.1).
            • Derived Quantities: Quantities derived from fundamental quantities through multiplication or division, such as area, volume, speed, density, and force (Chapter 2, Section 2.1).
            • International System of Units (SI Units): The standardized metric system used globally in scientific measurements (e.g., meter for length, kilogram for mass, second for time) (Chapter 2, Section 2.1).
            """.trimIndent()
        )

        addNote(
            "Unit 2 - Measurement",
            "Significant Figures and Scientific Notation (Section 2.2)",
            """
            • Scientific Notation: Expresses very large or very small numbers in the standard form a × 10ⁿ, where 1 ≤ a < 10 and n is an integer (Chapter 2, Section 2.2).
            • Significant Figures: The digits in a measured number that carry meaningful information about its precision, including all reliable digits plus one estimated uncertain digit (Chapter 2, Section 2.2).
            • Rules for arithmetic operations with significant figures and rounding off measurements to appropriate precision (Chapter 2, Section 2.2).
            """.trimIndent()
        )

        addNote(
            "Unit 2 - Measurement",
            "Measuring Instruments and Error Analysis (Section 2.3)",
            """
            • Common measuring instruments include meter rulers (length), beam balances/electronic scales (mass), stopwatches (time), and graduated cylinders (volume) (Chapter 2, Section 2.3).
            • Precision versus Accuracy: Precision refers to the reproducibility or closeness of repeated measurements to each other, while accuracy refers to how close a measurement is to the true or accepted value (Chapter 2, Section 2.3).
            • Experimental Errors: Systematic errors (consistent offset due to calibration or observational flaws) and random errors (unpredictable fluctuations in measurements) (Chapter 2, Section 2.3).
            """.trimIndent()
        )

        // Unit 3
        addNote(
            "Unit 3 - Motion in a Straight Line",
            "Rectilinear Motion and Reference Frames (Section 3.1)",
            """
            • Motion is the change in position of an object with respect to time and its surroundings, evaluated relative to a reference frame (Chapter 3, Section 3.1).
            • Distance (d): The total length of the path traveled by an object, being a scalar quantity (Chapter 3, Section 3.1).
            • Displacement (Δx): The shortest vector distance from the initial position to the final position, being a vector quantity (Chapter 3, Section 3.1).
            """.trimIndent()
        )

        addNote(
            "Unit 3 - Motion in a Straight Line",
            "Speed and Velocity (Section 3.2)",
            """
            • Average Speed (v_avg): The total distance traveled divided by the total time taken (v_avg = Total Distance / Total Time) (Chapter 3, Section 3.2).
            • Average Velocity (v): The total displacement divided by the total time taken (v = Δx / Δt), representing vector speed (Chapter 3, Section 3.2).
            • Instantaneous speed and velocity represent the rate of motion at a specific instant in time (Chapter 3, Section 3.2).
            """.trimIndent()
        )

        addNote(
            "Unit 3 - Motion in a Straight Line",
            "Acceleration (Section 3.3)",
            """
            • Acceleration (a): The rate of change of velocity with respect to time (a = Δv / Δt = (v - u) / t), measured in m/s² (Chapter 3, Section 3.3).
            • Uniform (Constant) Acceleration: Acceleration that remains constant over time (Chapter 3, Section 3.3).
            • Equations of Kinematics for Uniform Acceleration in a Straight Line:
              1. v = u + at
              2. s = ut + ½at²
              3. v² = u² + 2as
              4. s = ((u + v) / 2)t (Chapter 3, Section 3.3).
            """.trimIndent()
        )

        addNote(
            "Unit 3 - Motion in a Straight Line",
            "Graphical Analysis of Motion (Section 3.4)",
            """
            • Position-Time (x-t) Graph: The slope of a position-time graph represents the velocity of the object (Chapter 3, Section 3.4).
            • Velocity-Time (v-t) Graph: The slope of a velocity-time graph represents acceleration, and the area under the v-t graph represents displacement (Chapter 3, Section 3.4).
            • Acceleration-Time (a-t) Graph: The area under an acceleration-time graph represents the change in velocity (Chapter 3, Section 3.4).
            """.trimIndent()
        )

        // Unit 4
        addNote(
            "Unit 4 - Forces and Newton's Laws of Motion",
            "The Concept of Force (Section 4.1)",
            """
            • A force is a push or pull exerted on an object that can cause an object to accelerate, decelerate, change direction, or deform shape (Chapter 4, Section 4.1).
            • Forces are vector quantities measured in Newtons (N), where 1 N = 1 kg·m/s² (Chapter 4, Section 4.1).
            • Contact forces (friction, tension, normal force, applied force) versus non-contact / field forces (gravitational force, magnetic force, electrostatic force) (Chapter 4, Section 4.1).
            """.trimIndent()
        )

        addNote(
            "Unit 4 - Forces and Newton's Laws of Motion",
            "Newton's Laws of Motion (Section 4.2)",
            """
            • Newton's First Law of Motion (Law of Inertia): An object continues in a state of rest or of uniform motion in a straight line unless acted upon by a net external force (Chapter 4, Section 4.2).
            • Inertia is the natural tendency of an object to resist changes in its state of motion, directly proportional to its mass (Chapter 4, Section 4.2).
            • Newton's Second Law of Motion: The acceleration of an object is directly proportional to the net force acting on it and inversely proportional to its mass (F_net = ma) (Chapter 4, Section 4.2).
            • Newton's Third Law of Motion (Action and Reaction): For every action, there is an equal and opposite reaction; when body A exerts a force on body B, body B exerts an equal and opposite force on body A (F_AB = -F_BA) (Chapter 4, Section 4.2).
            """.trimIndent()
        )

        addNote(
            "Unit 4 - Forces and Newton's Laws of Motion",
            "Friction and Gravitation (Section 4.3)",
            """
            • Friction: A contact force that opposes the relative motion or tendency of motion between two surfaces in contact (Chapter 4, Section 4.3).
            • Types of friction: Static friction (prevents motion from starting) and kinetic/sliding friction (opposes motion of sliding surfaces) (Chapter 4, Section 4.3).
            • Universal Law of Gravitation: Every particle in the universe attracts every other particle with a force proportional to the product of their masses and inversely proportional to the square of the distance between their centers (F = G (m1 m2) / r²) (Chapter 4, Section 4.3).
            """.trimIndent()
        )

        // Unit 5
        addNote(
            "Unit 5 - Work, Energy and Power",
            "Work (Section 5.1)",
            """
            • Work (W) is done when a force acting on an object causes displacement of that object in the direction of the force (Chapter 5, Section 5.1).
            • Formula: W = F · d · cos θ, where F is force, d is displacement, and θ is the angle between force and displacement (Chapter 5, Section 5.1).
            • SI Unit of work is the Joule (J), where 1 J = 1 N·m (Chapter 5, Section 5.1).
            """.trimIndent()
        )

        addNote(
            "Unit 5 - Work, Energy and Power",
            "Energy (Section 5.2)",
            """
            • Energy is the capacity or ability to do work, measured in Joules (J) (Chapter 5, Section 5.2).
            • Kinetic Energy (KE): Energy possessed by an object due to its motion, calculated as KE = ½mv² (Chapter 5, Section 5.2).
            • Potential Energy (PE): Energy stored in an object due to its position or state, such as gravitational potential energy (PE = mgh) and elastic potential energy (PE = ½kx²) (Chapter 5, Section 5.2).
            • Law of Conservation of Energy: Energy cannot be created or destroyed; it can only be transformed from one form to another, meaning total mechanical energy in an isolated system remains constant (E_total = KE + PE = constant) (Chapter 5, Section 5.2).
            """.trimIndent()
        )

        addNote(
            "Unit 5 - Work, Energy and Power",
            "Power (Section 5.3)",
            """
            • Power (P) is the rate at which work is done or energy is transferred or transformed (Chapter 5, Section 5.3).
            • Formula: P = W / t = ΔE / t, or P = F · v for constant force and velocity (Chapter 5, Section 5.3).
            • SI Unit of power is the Watt (W), where 1 W = 1 J/s (Chapter 5, Section 5.3).
            """.trimIndent()
        )

        // Unit 6
        addNote(
            "Unit 6 - Simple Machines",
            "Introduction to Simple Machines (Section 6.1)",
            """
            • A simple machine is a mechanical device that changes the direction or magnitude of a force, making work easier to perform (Chapter 6, Section 6.1).
            • Mechanical Advantage (MA): The ratio of the load (resistance) force to the effort force (MA = F_load / F_effort) (Chapter 6, Section 6.1).
            • Velocity Ratio (VR): The ratio of the distance moved by effort to distance moved by load (VR = d_effort / d_load) (Chapter 6, Section 6.1).
            • Efficiency (η): The ratio of useful work output to total work input, expressed as a percentage (η = (W_output / W_input) × 100% = (MA / VR) × 100%) (Chapter 6, Section 6.1).
            """.trimIndent()
        )

        addNote(
            "Unit 6 - Simple Machines",
            "Types of Simple Machines (Section 6.2)",
            """
            • Lever: A rigid bar pivoted on a fixed point called a fulcrum (classified into first-class, second-class, and third-class levers based on fulcrum, effort, and load positions) (Chapter 6, Section 6.2).
            • Pulley: A wheel with a grooved rim used with a rope to change force direction or multiply force (fixed, movable, and block-and-tackle systems) (Chapter 6, Section 6.2).
            • Inclined Plane: A slanting surface used to raise heavy loads with less effort force over a longer distance (Chapter 6, Section 6.2).
            • Other simple machines include wedge, screw, and wheel and axle (Chapter 6, Section 6.2).
            """.trimIndent()
        )

        // Unit 7
        addNote(
            "Unit 7 - Nature of Light and Sound",
            "Nature and Properties of Light (Section 7.1)",
            """
            • Light is an electromagnetic wave that travels through a vacuum at a constant speed of approximately c = 3 × 10⁸ m/s (Chapter 7, Section 7.1).
            • Rectilinear propagation of light: Light travels in straight lines in a homogeneous medium, producing shadows and eclipses (Chapter 7, Section 7.1).
            • Reflection of light: The bouncing of light rays off surfaces, governed by Laws of Reflection:
              1. The angle of incidence equals angle of reflection (θi = θr).
              2. Incident ray, reflected ray, and normal all lie in the same plane (Chapter 7, Section 7.1).
            • Refraction of light: Bending of light rays passing obliquely from one transparent medium to another of different optical density, governed by Snell's Law (Chapter 7, Section 7.1).
            """.trimIndent()
        )

        addNote(
            "Unit 7 - Nature of Light and Sound",
            "Nature and Properties of Sound (Section 7.2)",
            """
            • Sound is a mechanical longitudinal wave produced by vibrating objects, requiring a material medium (solid, liquid, gas); cannot travel in a vacuum (Chapter 7, Section 7.2).
            • Characteristics of sound: Pitch (determined by frequency), loudness (determined by amplitude), and timbre/quality (determined by waveform/overtones) (Chapter 7, Section 7.2).
            • Reflection of sound produces echoes, and speed of sound varies depending on medium density and temperature (fastest in solids, slowest in gases) (Chapter 7, Section 7.2).
            """.trimIndent()
        )

        // Unit 8
        addNote(
            "Unit 8 - Pressure",
            "Pressure in Solids, Liquids, and Gases (Section 8.1)",
            """
            • Pressure (P) is defined as the normal force exerted per unit surface area (P = F / A) (Chapter 8, Section 8.1).
            • SI Unit of pressure is the Pascal (Pa), where 1 Pa = 1 N/m² (Chapter 8, Section 8.1).
            • Liquid Pressure: Increases with depth due to weight of liquid above, calculated as P = ρ g h (Chapter 8, Section 8.1).
            • Atmospheric Pressure: Pressure exerted by Earth's atmosphere weight, measured using barometers (Chapter 8, Section 8.1).
            • Pascal's Principle: Pressure applied to an enclosed incompressible fluid is transmitted undiminished to every portion of fluid and container walls (hydraulic systems) (Chapter 8, Section 8.1).
            • Archimedes' Principle: An object totally or partially immersed in a fluid experiences an upward buoyant force equal to weight of displaced fluid (Chapter 8, Section 8.1).
            """.trimIndent()
        )

        return notesList
    }
}
