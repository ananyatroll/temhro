package com.example.data

object Grade10MathNotes {

    fun getGrade10MathNotes(): List<SubjectNote> {
        val notesList = mutableListOf<SubjectNote>()
        val subId = "euee_nat_maths"
        var idx = 1

        fun addNote(unit: String, title: String, content: String) {
            notesList.add(
                SubjectNote(
                    id = "g10_math_note_${idx}",
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
            "Unit 1 - Relations and Functions",
            "Relations, Functions, and Inverses (Sections 1.1 - 1.2)",
            """
            • Relation & Domain/Range: A set of ordered pairs (x,y). Cartesian Product A × B = {(a,b) | a ∈ A, b ∈ B}. Inverse relation R⁻¹ swaps coordinates.
            • Function: A relation where every element in domain pairs with exactly one unique element in range. Tested via Vertical Line Test.
            • Algebra of Functions: (f+g)(x)=f(x)+g(x), (f·g)(x)=f(x)g(x), (f/g)(x)=f(x)/g(x) [g(x)≠0]. Composite: (f ∘ g)(x) = f(g(x)).
            • Inverse Functions: f⁻¹ exists iff f is one-to-one (injective) and onto (surjective).
            """.trimIndent()
        )

        addNote(
            "Unit 2 - Polynomial Functions",
            "Polynomials, Theorems, and Factorization (Sections 2.1 - 2.5)",
            """
            • Polynomial: P(x) = a_n xⁿ + ... + a₁ x + a₀, where n is a non-negative integer and a_n ≠ 0.
            • Remainder Theorem: Dividing P(x) by (x - c) yields remainder P(c).
            • Factor Theorem: (x - c) is a factor of P(x) iff P(c) = 0.
            • Rational Root Theorem: Potential rational roots p/q have p dividing constant term a₀ and q dividing leading coefficient a_n.
            """.trimIndent()
        )

        addNote(
            "Unit 3 - Exponential and Logarithmic Functions",
            "Exponential & Logarithmic Equations and Laws (Sections 3.1 - 3.2)",
            """
            • Exponential Function: f(x) = aˣ (a > 0, a ≠ 1). Domain R, Range (0, ∞), y-intercept (0,1).
            • Logarithm: log_a y = x ⇔ aˣ = y.
            • Laws:
              - log_a(xy) = log_a x + log_a y
              - log_a(x/y) = log_a x - log_a y
              - log_a(xᵏ) = k log_a x
              - Change of Base: log_b x = (log_a x) / (log_a b)
            """.trimIndent()
        )

        addNote(
            "Unit 4 - Trigonometric Functions",
            "Radian Measure, Ratios, Graphs, and Identities (Sections 4.1 - 4.4)",
            """
            • Radian Measure: θ = s/r, 180° = π radians.
            • Unit Circle Ratios: sin θ, cos θ, tan θ = sin θ / cos θ, csc θ, sec θ, cot θ.
            • Identities: sin²θ + cos²θ = 1; 1 + tan²θ = sec²θ; 1 + cot²θ = csc²θ.
            • Sine and Cosine Rules:
              - Sine Rule: a/sin A = b/sin B = c/sin C
              - Cosine Rule: c² = a² + b² - 2ab cos C
            """.trimIndent()
        )

        addNote(
            "Unit 5 - Circles",
            "Symmetrical and Angle Properties of Circles (Sections 5.1 - 5.4)",
            """
            • Perpendicular from center to chord bisects the chord.
            • Inscribed Angle Theorem: Angle subtended at center is twice angle at circumference. Angles in same segment are equal. Angle in semicircle is 90°.
            • Cyclic Quadrilateral: Opposite angles are supplementary (sum = 180°).
            • Arc Length s = (θ/360°) × 2πr; Sector Area = (θ/360°) × πr².
            """.trimIndent()
        )

        addNote(
            "Unit 6 - Solid Figures",
            "Prisms, Cylinders, Pyramids, Cones, Spheres, and Frustums (Sections 6.1 - 6.5)",
            """
            • Prism/Cylinder: Volume V = B·h.
            • Pyramid/Cone: Volume V = (1/3) B·h. Cone TSA = πr² + πrℓ (where ℓ = slant height).
            • Sphere: SA = 4πr², Volume V = (4/3)πr³.
            • Frustum of Cone: V = (1/3)πh (R² + r² + R·r).
            """.trimIndent()
        )

        addNote(
            "Unit 7 - Coordinate Geometry",
            "Distance, Slope, Lines, and Circles (Sections 7.1 - 7.6)",
            """
            • Distance Formula: d = √[(x₂ - x₁)² + (y₂ - y₁)²]. Midpoint M = ((x₁+x₂)/2, (y₁+y₂)/2).
            • Slope m = (y₂ - y₁) / (x₂ - x₁). Parallel lines: m₁ = m₂. Perpendicular: m₁·m₂ = -1.
            • Line Equations: Point-Slope y - y₁ = m(x - x₁); Slope-Intercept y = mx + b; General Ax + By + C = 0.
            • Circle Equation: Standard (x - h)² + (y - k)² = r² with center (h,k) and radius r.
            """.trimIndent()
        )

        return notesList
    }
}
