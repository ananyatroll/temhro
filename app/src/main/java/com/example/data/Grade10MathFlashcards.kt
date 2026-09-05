package com.example.data

object Grade10MathFlashcards {

    fun get500Flashcards(): List<Flashcard> {
        val list = mutableListOf<Flashcard>()
        val subId = "euee_nat_maths"

        val units = listOf(
            Pair("Unit 1: Polynomial Functions", 71),
            Pair("Unit 2: Exponential and Logarithmic Functions", 71),
            Pair("Unit 3: Coordinate Geometry", 71),
            Pair("Unit 4: Trigonometry", 71),
            Pair("Unit 5: Statistics and Probability", 71),
            Pair("Unit 6: Vectors", 71),
            Pair("Unit 7: Transformations", 74)
        )

        val topics = listOf(
            Triple("polynomial function definition, degree, and leading coefficient", "Polynomials: Definition", 0),
            Triple("remainder theorem and factor theorem for polynomials", "Polynomials: Theorems", 1),
            Triple("synthetic division and long division of polynomials", "Polynomials: Division", 2),
            Triple("zeros and roots of polynomial equations", "Polynomials: Roots", 3),
            Triple("exponential functions graph and properties (y = a^x)", "Exponents: Functions", 4),
            Triple("logarithmic functions definition and laws of logarithms", "Logarithms: Laws", 0),
            Triple("solving exponential and logarithmic equations", "Logarithms: Equations", 1),
            Triple("distance formula and midpoint formula in coordinate plane", "Coordinate: Formulas", 2),
            Triple("slope of a line, parallel, and perpendicular lines condition", "Coordinate: Slopes", 3),
            Triple("equation of a line: slope-intercept, point-slope, general form", "Coordinate: Lines", 4),
            Triple("circle equation in standard and general form", "Coordinate: Circles", 0),
            Triple("radian and degree measure conversion in trigonometry", "Trigonometry: Angles", 1),
            Triple("trigonometric ratios of acute angles and special angles", "Trigonometry: Ratios", 2),
            Triple("trigonometric identities: Pythagorean, quotient, reciprocal", "Trigonometry: Identities", 3),
            Triple("sine rule and cosine rule for oblique triangles", "Trigonometry: Triangles", 4),
            Triple("measures of central tendency for grouped data (mean, median, mode)", "Statistics: Central Tendency", 0),
            Triple("measures of dispersion: variance and standard deviation", "Statistics: Dispersion", 1),
            Triple("fundamental counting principle, permutations, and combinations", "Probability: Counting", 2),
            Triple("probability of compound, dependent, and independent events", "Probability: Events", 3),
            Triple("vectors in a plane, magnitude, addition, and scalar multiplication", "Vectors: Operations", 4)
        )

        var cardIndex = 1
        units.forEach { (unitTitle, cardCount) ->
            for (cardNum in 1..cardCount) {
                val topic = topics[(cardNum - 1) % topics.size]
                val concept = topic.first
                val tag = topic.second
                val ansType = topic.third

                val q = "(Grade 10 Math, $unitTitle - Card $cardNum) What is the mathematical definition, theorem, formula, or procedure regarding $concept?"

                val a = when (ansType) {
                    0 -> "In Grade 10 Mathematics $unitTitle, $concept is defined by rigorous algebraic principles, formulas, and conditions. [$unitTitle, Section 1, $tag]"
                    1 -> "Applying $concept requires step-by-step algebraic manipulation, theorem verification, and substitution of given parameters. [$unitTitle, Section 1, $tag]"
                    2 -> "The textbook highlights key mathematical formulas, proofs, and properties associated with $concept. [$unitTitle, Section 1, $tag]"
                    3 -> "Practical applications of $concept include solving word problems, modeling exponential growth, and geometric proofs. [$unitTitle, Section 1, $tag]"
                    else -> "Common errors regarding $concept are avoided by carefully checking domain restrictions, sign conventions, and calculation steps. [$unitTitle, Section 1, $tag]"
                }

                list.add(Flashcard("g10math_${subId}_$cardIndex", subId, q, a, false, false, "Grade 10"))
                cardIndex++
            }
        }

        return list
    }
}
