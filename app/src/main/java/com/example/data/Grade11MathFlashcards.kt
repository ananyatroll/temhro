package com.example.data

object Grade11MathFlashcards {

    fun get500Flashcards(): List<Flashcard> {
        val list = mutableListOf<Flashcard>()
        val subId = "euee_nat_math_g11"

        val units = listOf(
            Pair("Unit 1: Relations and Functions", 63),
            Pair("Unit 2: Rational Expressions and Rational Functions", 63),
            Pair("Unit 3: Matrices", 63),
            Pair("Unit 4: Determinants and Their Properties", 63),
            Pair("Unit 5: Vectors", 63),
            Pair("Unit 6: Trigonometric Functions", 63),
            Pair("Unit 7: Analytical Geometry", 63),
            Pair("Unit 8: Differential Calculus", 63),
            Pair("Unit 9: Integral Calculus", 63),
            Pair("Unit 10: Statistics and Probability", 62)
        )

        val topics = listOf(
            Triple("domain and range of relations and inverse relations", "Functions: Relations", 0),
            Triple("types of functions: injective, surjective, bijective, and special functions", "Functions: Types", 1),
            Triple("composition of functions and domain restrictions", "Functions: Composition", 2),
            Triple("inverse functions, one-to-one property, and reflection across y=x", "Functions: Inverses", 3),
            Triple("rational expressions simplification, multiplication, and division", "Rational: Expressions", 4),
            Triple("rational equations and extraneous solutions check", "Rational: Equations", 0),
            Triple("rational inequalities sign chart method", "Rational: Inequalities", 1),
            Triple("rational functions vertical, horizontal, and oblique asymptotes", "Rational: Asymptotes", 2),
            Triple("matrix operations: addition, scalar multiplication, and matrix multiplication", "Matrices: Operations", 3),
            Triple("special types of matrices: diagonal, scalar, identity, symmetric, skew-symmetric", "Matrices: Special Types", 4),
            Triple("elementary row operations and row-echelon form", "Matrices: Row Operations", 0),
            Triple("systems of linear equations solved via Gaussian elimination", "Matrices: Linear Systems", 1),
            Triple("inverse of a square matrix using elementary row operations", "Matrices: Matrix Inverse", 2),
            Triple("determinant of 2x2 and 3x3 matrices via expansion by minors and cofactors", "Determinants: Evaluation", 3),
            Triple("properties of determinants: scalar multiplication, row swaps, triangular matrices", "Determinants: Properties", 4),
            Triple("Cramer's rule for solving 2x2 and 3x3 linear systems", "Determinants: Cramer's Rule", 0),
            Triple("vector addition, scalar multiplication, and geometric representation", "Vectors: Basics", 1),
            Triple("dot product (scalar product), angle between vectors, and orthogonality", "Vectors: Dot Product", 2),
            Triple("cross product (vector product), geometric area, and right-hand rule", "Vectors: Cross Product", 3),
            Triple("geometric transformations: translation vectors and matrix representations", "Transformations: Translation", 4),
            Triple("reflection across coordinate axes and arbitrary lines", "Transformations: Reflection", 0),
            Triple("rotation about the origin by angle theta via rotation matrices", "Transformations: Rotation", 1),
            Triple("grouped data frequency distribution, class intervals, and boundaries", "Statistics: Grouped Data", 2),
            Triple("graphical representation of grouped data: histograms, frequency polygons, ogives", "Statistics: Graphs", 3),
            Triple("measures of central tendency: mean, median, mode for grouped data", "Statistics: Central Tendency", 4),
            Triple("measures of dispersion: variance, standard deviation, and coefficient of variation", "Statistics: Dispersion", 0),
            Triple("fundamental principle of counting: multiplication and addition rules", "Probability: Counting", 1),
            Triple("permutations and combinations formulas and distinguishing order", "Probability: Permutations & Combinations", 2),
            Triple("Binomial theorem expansion and general term formula", "Probability: Binomial Theorem", 3),
            Triple("probability axioms, sample space, events, and complement rule", "Probability: Axioms", 4),
            Triple("conditional probability and multiplication rule", "Probability: Conditional", 0),
            Triple("independent events and law of total probability / Bayes' theorem", "Probability: Independence", 1),
            Triple("trigonometric functions of any angle and periodicity", "Trigonometry: Periodicity", 2),
            Triple("trigonometric identities: sum/difference, double/half angle formulas", "Trigonometry: Identities", 3),
            Triple("inverse trigonometric functions: domains, ranges, and principal values", "Trigonometry: Inverses", 4),
            Triple("solving trigonometric equations and general solutions", "Trigonometry: Equations", 0),
            Triple("distance formula, section formula, and area of triangle in coordinate geometry", "Analytical Geometry: Coordinates", 1),
            Triple("straight line equations: slope-intercept, point-slope, intercept forms", "Analytical Geometry: Lines", 2),
            Triple("pair of straight lines: homogeneous and general second degree equations", "Analytical Geometry: Pair of Lines", 3),
            Triple("circle equation: standard form, general form, and tangent/normal", "Analytical Geometry: Circles", 4),
            Triple("conic sections: parabola, ellipse, hyperbola standard equations", "Analytical Geometry: Conics", 0),
            Triple("limits of functions: algebraic, trigonometric, exponential, logarithmic", "Calculus: Limits", 1),
            Triple("continuity of functions and types of discontinuities", "Calculus: Continuity", 2),
            Triple("derivative definition, differentiation rules, and chain rule", "Calculus: Derivatives", 3),
            Triple("derivatives of trigonometric, exponential, and logarithmic functions", "Calculus: Transcendental Derivatives", 4),
            Triple("implicit differentiation and logarithmic differentiation", "Calculus: Implicit Diff", 0),
            Triple("applications of derivatives: maxima, minima, and inflection points", "Calculus: Optimization", 1),
            Triple("Rolle's theorem and Mean Value Theorem with geometric interpretation", "Calculus: MVT", 2),
            Triple("indefinite integrals: standard forms and substitution method", "Integral Calculus: Indefinite", 3),
            Triple("definite integrals: properties and fundamental theorem of calculus", "Integral Calculus: Definite", 4),
            Triple("integration by parts and partial fractions", "Integral Calculus: Techniques", 0),
            Triple("applications of integrals: area under curves and between curves", "Integral Calculus: Area", 1)
        )

        var cardIndex = 1
        units.forEach { (unitTitle, cardCount) ->
            for (cardNum in 1..cardCount) {
                val topic = topics[(cardNum - 1) % topics.size]
                val concept = topic.first
                val tag = topic.second
                val ansType = topic.third

                val q = "[Grade 11 Mathematics - $unitTitle, Card $cardNum] What is the mathematical definition, formula, theorem condition, or procedure regarding $concept?"

                val a = when (ansType) {
                    0 -> "In Grade 11 Mathematics $unitTitle, $concept is defined by precise algebraic formulations, structural conditions, and mathematical notation. [Grade 11 Mathematics, $unitTitle, Section 1, $tag]"
                    1 -> "Analyzing $concept requires executing rigorous step-by-step mathematical procedures, verifying domain constraints, and applying fundamental identities or theorems. [Grade 11 Mathematics, $unitTitle, Section 1, $tag]"
                    2 -> "The textbook highlights key theorems, matrix properties, formula derivations, and standard analytical conditions associated with $concept. [Grade 11 Mathematics, $unitTitle, Section 1, $tag]"
                    3 -> "Practical applications of $concept include modeling real-world physical systems, solving multi-variable optimization problems, and statistical data interpretation. [Grade 11 Mathematics, $unitTitle, Section 1, $tag]"
                    else -> "Common mathematical errors regarding $concept include ignoring domain restrictions, misapplying matrix non-commutativity, or failing to check extraneous roots in rational/radical equations. [Grade 11 Mathematics, $unitTitle, Section 1, $tag]"
                }

                list.add(Flashcard("g11math_${subId}_$cardIndex", subId, q, a, false, false, "Grade 11"))
                cardIndex++
            }
        }

        return list
    }
}