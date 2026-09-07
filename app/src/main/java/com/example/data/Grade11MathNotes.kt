package com.example.data

object Grade11MathNotes {

    fun getGrade11MathNotes(): List<SubjectNote> {
        val notesList = mutableListOf<SubjectNote>()
        val subId = "euee_nat_maths"
        var idx = 1

        fun addNote(unit: String, title: String, content: String) {
            notesList.add(
                SubjectNote(
                    id = "g11_math_note_${idx}",
                    subjectId = subId,
                    unit = unit,
                    title = title,
                    content = content,
                    gradeLevel = "Grade 11"
                )
            )
            idx++
        }

        // Unit 1
        addNote(
            "Unit 1 - Relations and Functions",
            "Relations, Inverse Relations, and Types of Functions (Sections 1.1 - 1.3)",
            """
            • Relations and Cartesian Products: Given sets A and B, the Cartesian product A × B is the set of all ordered pairs (a, b) where a ∈ A and b ∈ B. A relation R from A to B is a subset of A × B.
            • Inverse of a Relation: The inverse of a relation R, denoted by R⁻¹, is defined as R⁻¹ = {(y, x) | (x, y) ∈ R}. The graph of R⁻¹ is the reflection of the graph of R across the line y = x.
            • Functions: A function f from set A to set B is a relation in which every element x ∈ A is paired with exactly one unique element y ∈ B.
            • Types of Functions:
              - One-to-One (Injective) Function: Distinct domain elements map to distinct range elements (f(x₁) = f(x₂) ⇒ x₁ = x₂).
              - Onto (Surjective) Function: Every element in the codomain is mapped to by at least one element in the domain (Range = Codomain).
              - One-to-One and Onto (Bijective) Function: Both injective and surjective.
              - Constant, Linear, Quadratic, Polynomial, Rational, Exponential, and Logarithmic Functions.
            """.trimIndent()
        )

        addNote(
            "Unit 1 - Relations and Functions",
            "Composition of Functions and Applications (Sections 1.4 - 1.6)",
            """
            • Composition of Functions: Given functions f: A → B and g: B → C, the composite function g ∘ f is defined by (g ∘ f)(x) = g(f(x)), provided the range of f is a subset of the domain of g.
            • Properties of Composition: In general, composition of functions is not commutative (g ∘ f ≠ f ∘ g). However, composition is associative.
            • Inverse Functions: A function f has an inverse function f⁻¹ if and only if f is bijective (one-to-one and onto), satisfying f(f⁻¹(x)) = x and f⁻¹(f(x)) = x.
            • Applications of Relations and Functions: Modeling real-world situations, cost-revenue-profit functions, growth and decay models.
            """.trimIndent()
        )

        // Unit 2
        addNote(
            "Unit 2 - Rational Expressions and Rational Functions",
            "Rational Expressions, Equations, Inequalities, and Functions (Sections 2.1 - 2.4)",
            """
            • Rational Expressions: An algebraic expression of the form P(x)/Q(x), where P(x) and Q(x) are polynomials and Q(x) ≠ 0. Simplification, multiplication, division, addition, and subtraction of rational expressions.
            • Rational Equations and Inequalities: Solving equations containing rational expressions by finding common denominators and checking for extraneous roots. Solving rational inequalities using sign charts and critical points.
            • Rational Functions and Their Graphs: Functions of the form f(x) = P(x)/Q(x). Determining domains, vertical asymptotes (where denominator is zero), horizontal asymptotes (based on degrees of P(x) and Q(x)), x and y intercepts, and sketching graphs.
            • Applications: Solving word problems involving work rates, speed-distance-time, and mixing solutions using rational equations.
            """.trimIndent()
        )

        // Unit 3
        addNote(
            "Unit 3 - Matrices",
            "Matrices, Operations, Special Types, and Elementary Row Operations (Sections 3.1 - 3.4)",
            """
            • Matrix Definition: A rectangular array of numbers arranged in rows (m) and columns (n), denoted as an m × n matrix.
            • Matrix Operations:
              - Addition and Subtraction: Defined for matrices of the same dimensions by adding/subtracting corresponding elements.
              - Scalar Multiplication: Multiplying every element of a matrix by a real scalar.
              - Matrix Multiplication: Product AB of an m × p matrix A and a p × n matrix B is an m × n matrix where each element is the dot product of rows of A and columns of B.
            • Special Types of Matrices: Square matrix, diagonal matrix, scalar matrix, identity matrix (I), zero matrix, symmetric matrix, and skew-symmetric matrix.
            • Elementary Row Operations (EROs):
              1. Interchange two rows (R_i ↔ R_j).
              2. Multiply a row by a non-zero scalar (kR_i).
              3. Add a scalar multiple of one row to another row (R_i + kR_j).
            - Row Echelon Form and Reduced Row Echelon Form (RREF) using Gaussian elimination.
            """.trimIndent()
        )

        addNote(
            "Unit 3 - Matrices",
            "Systems of Linear Equations and Matrix Inverse (Sections 3.5 - 3.7)",
            """
            • Systems of Linear Equations: Representing linear systems in matrix form AX = B.
            • Solutions of Systems: Using Gaussian elimination and Gauss-Jordan elimination (row reduction) to find unique, infinitely many, or no solutions.
            • Inverse of a Square Matrix: A square matrix A of order n has an inverse A⁻¹ if and only if A is non-singular (det(A) ≠ 0), satisfying A A⁻¹ = A⁻¹ A = I. Finding A⁻¹ using elementary row operations on augmented matrix [A | I].
            """.trimIndent()
        )

        // Unit 4
        addNote(
            "Unit 4 - Determinants and Their Properties",
            "Determinants, Minors, Cofactors, Properties, and Applications (Sections 4.1 - 4.7)",
            """
            • Determinants of Order 2 and 3: The determinant of a 2×2 matrix |a b; c d| = ad - bc. Determinant of a 3×3 matrix evaluated using expansion by minors and cofactors or Sarrus' rule.
            • Minors and Cofactors: Minor M_ij is the determinant of the submatrix formed by deleting row i and column j. Cofactor C_ij = (-1)^{i+j} M_ij.
            • Properties of Determinants:
              - If a matrix has a row/column of zeros, its determinant is zero.
              - Swapping two rows/columns negates the determinant.
              - Multiplying a row/column by scalar k multiplies the determinant by k.
              - det(AB) = det(A) · det(B)
              - det(A^T) = det(A)
            • Inverse Using Adjoint Matrix: A⁻¹ = (1/det(A)) Adj(A), where Adj(A) is the transpose of the cofactor matrix.
            • Cramer's Rule: Solving systems of linear equations using determinants (x_i = det(A_i)/det(A)).
            """.trimIndent()
        )

        // Unit 5
        addNote(
            "Unit 5 - Vectors",
            "Revision on Vectors, Representation, and Vector Product (Sections 5.1 - 5.3)",
            """
            • Revision on Vectors and Scalars: Scalars have magnitude only; vectors have both magnitude and direction. Geometric and algebraic representation of vectors in 2D and 3D coordinate systems (v = ⟨x, y, z⟩ = xî + yĵ + zk̂).
            • Vector Operations: Vector addition, subtraction, scalar multiplication, magnitude formula (|v| = √(x² + y² + z²)), unit vectors, and dot product (scalar product):
              - u · v = u₁v₁ + u₂v₂ + u₃v₃ = |u| |v| cos θ
            • Vector Product (Cross Product): Defined for 3D vectors yielding a vector orthogonal to both factors:
              - u × v = |î ĵ k̂; u₁ u₂ u₃; v₁ v₂ v₃|
              - Magnitude |u × v| = |u| |v| sin θ represents the area of the parallelogram spanned by u and v.
            """.trimIndent()
        )

        // Unit 6
        addNote(
            "Unit 6 - Transformations of the Plane",
            "Translation, Reflection, and Rotation (Sections 6.1 - 6.4)",
            """
            • Introduction to Transformations: A transformation is a mapping of a geometric figure onto a new position in the plane (isometries / rigid motions preserve distance and angles).
            • Translation: Sliding a figure horizontally and/or vertically by a translation vector v = ⟨h, k⟩, mapping point (x, y) to (x + h, y + k).
            • Reflection: Flipping a figure across a line of reflection (e.g., x-axis, y-axis, line y = x), mapping points to their symmetric counterparts.
            • Rotation: Turning a figure around a fixed center point (e.g., origin) by a given angle θ (e.g., 90°, 180°, 270°) using rotation matrices.
            """.trimIndent()
        )

        // Unit 7
        addNote(
            "Unit 7 - Statistics",
            "Grouped Data, Graphical Representation, and Measures of Central Tendency (Sections 7.1 - 7.5)",
            """
            • Types of Data: Quantitative (discrete and continuous) versus qualitative data; primary and secondary data sources.
            • Introduction to Grouped Data: Organizing raw data into frequency distributions, class intervals, class boundaries, class mark (x_i), and cumulative frequency.
            • Graphical Representation: Constructing histograms, frequency polygons, frequency curves, and cumulative frequency ogives.
            • Measures of Central Tendency:
              - Mean (x̄ = Σf_i x_i / Σf_i for grouped data)
              - Median (L + ((n/2 - CF)/f)w)
              - Mode (L + (Δ₁/(Δ₁ + Δ₂))w)
            • Real-life Application of Statistics: Interpreting statistical data in economic, social, and scientific contexts.
            """.trimIndent()
        )

        // Unit 8
        addNote(
            "Unit 8 - Probability",
            "Counting Principles, Permutations, Combinations, Binomial Theorem, and Probability (Sections 8.1 - 8.6)",
            """
            • Fundamental Principle of Counting (Multiplication Rule): If an event can occur in m ways and a second event in n ways, the sequence can occur in m × n ways.
            • Permutations and Combinations:
              - Permutation: Arrangement of items where order matters (P(n, r) = n!/(n-r)!).
              - Combination: Selection of items where order does not matter (C(n, r) = C(n,r) = n!/(r!(n-r)!)).
            • Binomial Theorem: Expansion of (x + y)^n = Σ_{k=0}^{n} C(n,k) x^{n-k} y^k, with general term T_{k+1} = C(n,k) x^{n-k} y^k.
            • Random Experiments and Events: Sample space (S), events, probability of an event P(A) = n(A)/n(S), axioms of probability, addition rule, and conditional probability.
            """.trimIndent()
        )

        return notesList + notesList.map { it.copy(id = it.id + "_soc", subjectId = "euee_soc_maths") }
    }
}