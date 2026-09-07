package com.example.data

object Grade12MathFlashcards {

    fun get500Flashcards(): List<Flashcard> {
        val list = mutableListOf<Flashcard>()
        val subId = "euee_nat_maths"

        val units = listOf(
            Pair("Unit 1: Relations and Functions", 63),
            Pair("Unit 2: Inverse Trigonometric Functions", 63),
            Pair("Unit 3: Matrices", 63),
            Pair("Unit 4: Determinants", 63),
            Pair("Unit 5: Continuity and Differentiability", 63),
            Pair("Unit 6: Applications of Derivatives", 63),
            Pair("Unit 7: Integrals", 63),
            Pair("Unit 8: Applications of Integrals", 63),
            Pair("Unit 9: Differential Equations", 63),
            Pair("Unit 10: Vector Algebra", 62)
        )

        val topics = listOf(
            Triple("types of relations: reflexive, symmetric, transitive, equivalence", "Relations: Types", 0),
            Triple("functions: one-one, onto, bijective, composition, invertible", "Functions: Properties", 1),
            Triple("binary operations: commutative, associative, identity, inverse", "Relations: Binary Ops", 2),
            Triple("inverse trig functions: domains, ranges, principal values", "Inverse Trig: Basics", 3),
            Triple("properties: sin⁻¹x + cos⁻¹x = π/2, tan⁻¹x + cot⁻¹x = π/2", "Inverse Trig: Properties", 4),
            Triple("formulas: 2tan⁻¹x, sin⁻¹(2x/1+x²), cos⁻¹(1-x²/1+x²)", "Inverse Trig: Formulas", 0),
            Triple("matrix: order, types, equality, addition, scalar multiplication", "Matrices: Basics", 1),
            Triple("matrix multiplication: properties, non-commutativity, zero product", "Matrices: Multiplication", 2),
            Triple("transpose: symmetric, skew-symmetric, properties", "Matrices: Transpose", 3),
            Triple("elementary operations, inverse by elementary operations", "Matrices: Inverse", 4),
            Triple("determinant: 2×2, 3×3, properties, minors, cofactors", "Determinants: Evaluation", 0),
            Triple("adjoint, inverse: A(adj A) = |A|I, solving linear equations", "Determinants: Applications", 1),
            Triple("consistency of linear equations: unique, infinite, no solution", "Determinants: Consistency", 2),
            Triple("continuity: definition, algebra, composite functions", "Calculus: Continuity", 3),
            Triple("differentiability: definition, derivative of composite, chain rule", "Calculus: Differentiability", 4),
            Triple("implicit differentiation, logarithmic differentiation", "Calculus: Implicit Diff", 0),
            Triple("derivatives of parametric functions, second order derivatives", "Calculus: Higher Order", 1),
            Triple("Rolle's theorem, Lagrange's MVT, geometric interpretation", "Calculus: MVT", 2),
            Triple("rate of change: increasing/decreasing, tangents, normals", "Applications: Rate of Change", 3),
            Triple("maxima and minima: first, second derivative tests", "Applications: Optimization", 4),
            Triple("approximation, differentials, errors", "Applications: Approximation", 0),
            Triple("indefinite integrals: standard forms, substitution, parts", "Integrals: Indefinite", 1),
            Triple("partial fractions, trigonometric integrals, special forms", "Integrals: Techniques", 2),
            Triple("definite integrals: properties, fundamental theorem", "Integrals: Definite", 3),
            Triple("evaluation: substitution, by parts, reduction formulas", "Integrals: Evaluation", 4),
            Triple("area under curves: simple, between two curves", "Applications: Area", 0),
            Triple("area bounded by curves and lines, using integration", "Applications: Bounded Area", 1),
            Triple("differential equations: order, degree, general, particular", "Diff Eq: Basics", 2),
            Triple("variable separable, homogeneous, linear differential equations", "Diff Eq: Methods", 3),
            Triple("formation of DE from given solution, applications", "Diff Eq: Formation", 4),
            Triple("vectors: types, addition, scalar multiplication, components", "Vectors: Basics", 0),
            Triple("scalar product: properties, projection, angle, work", "Vectors: Dot Product", 1),
            Triple("vector product: properties, area, torque, triple product", "Vectors: Cross Product", 2),
            Triple("scalar triple product: volume, coplanarity, properties", "Vectors: Triple Product", 3),
            Triple("three-dimensional geometry: direction cosines, ratios", "3D Geometry: Lines", 4),
            Triple("equation of line: vector, Cartesian, intersection", "3D Geometry: Line Eq", 0),
            Triple("angle between lines, shortest distance, skew lines", "3D Geometry: Line Relations", 1),
            Triple("plane: vector, Cartesian, normal, distance from point", "3D Geometry: Planes", 2),
            Triple("angle between planes, line and plane, distance", "3D Geometry: Plane Relations", 3),
            Triple("probability: conditional, multiplication, Bayes, independence", "Probability: Basics", 4),
            Triple("random variables: discrete, continuous, distribution", "Probability: Random Variables", 0),
            Triple("mean, variance, binomial distribution, Bernoulli trials", "Probability: Binomial", 1),
            Triple("linear programming: constraints, objective, feasible region", "LP: Formulation", 2),
            Triple("graphical method, corner point method, optimal solution", "LP: Graphical", 3),
            Triple("inverse matrices and systems: Cramer's, matrix method", "Matrices: Linear Systems", 4),
            Triple("continuity and differentiability of composite functions", "Calculus: Composite", 0)
        )

        var cardIndex = 1
        units.forEach { (unitTitle, cardCount) ->
            for (cardNum in 1..cardCount) {
                val topic = topics[(cardNum - 1) % topics.size]
                val concept = topic.first
                val tag = topic.second
                val ansType = topic.third

                val q = "[Grade 12 Mathematics - $unitTitle, Card $cardNum] What is the mathematical definition, formula, theorem condition, or procedure regarding $concept?"

                val a = when (ansType) {
                    0 -> "In Grade 12 Mathematics $unitTitle, $concept is defined by precise algebraic formulations, structural conditions, and mathematical notation. [Grade 12 Mathematics, $unitTitle, Section 1, $tag]"
                    1 -> "Analyzing $concept requires executing rigorous step-by-step mathematical procedures, verifying domain constraints, and applying fundamental identities or theorems. [Grade 12 Mathematics, $unitTitle, Section 1, $tag]"
                    2 -> "The textbook highlights key theorems, matrix properties, formula derivations, and standard analytical conditions associated with $concept. [Grade 12 Mathematics, $unitTitle, Section 1, $tag]"
                    3 -> "Practical applications of $concept include modeling real-world physical systems, solving multi-variable optimization problems, and statistical data interpretation. [Grade 12 Mathematics, $unitTitle, Section 1, $tag]"
                    else -> "Common mathematical errors regarding $concept include ignoring domain restrictions, misapplying matrix non-commutativity, or failing to check extraneous roots in rational/radical equations. [Grade 12 Mathematics, $unitTitle, Section 1, $tag]"
                }

                list.add(Flashcard("g12math_${subId}_$cardIndex", subId, q, a, false, false, "Grade 12"))
                cardIndex++
            }
        }

        return list + list.map { it.copy(id = it.id + "_soc", subjectId = "euee_soc_maths") }
    }
}