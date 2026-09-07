package com.example.data

object Grade12MathNotes {

    fun getGrade12MathNotes(): List<SubjectNote> {
        val notesList = mutableListOf<SubjectNote>()
        val subId = "euee_nat_maths"
        var idx = 1

        fun addNote(unit: String, title: String, content: String) {
            notesList.add(
                SubjectNote(
                    id = "g12_math_note_${idx}",
                    subjectId = subId,
                    unit = unit,
                    title = title,
                    content = content,
                    gradeLevel = "Grade 12"
                )
            )
            idx++
        }

        // Unit 1
        addNote(
            "Unit 1 - Sequences and Series",
            "Sequence / Arithmetic and Geometric Sequences / Sigma Notation and Partial Sums / Infinite Series / Applications",
            """
            • A sequence is an arrangement of numbers in a definite order according to some rule. A sequence is a function whose domain is the collection of all integers greater than or equal to a given integer m (usually 0 or 1).
            • Notation: A sequence is denoted by {a_n}. The functional values a_1, a_2, a_3, ..., a_n, ... are called the terms of the sequence.
            • Arithmetic Sequence: Each term except the first is obtained by adding a fixed number d (common difference) to the preceding term. Formula: A_n = A_1 + (n - 1)d.
            • Geometric Sequence: The ratio between consecutive terms is a non-zero constant r (common ratio). Formula: G_n = G_1 * r^(n-1).
            • Sigma Notation: S_n = Σ (k=1 to n) a_k = a_1 + a_2 + a_3 + ... + a_n.
            • Sum of Arithmetic Sequence: S_n = (n/2)(A_1 + A_n) = (n/2)[2A_1 + (n - 1)d].
            • Sum of Geometric Sequence: S_n = G_1(1 - r^n) / (1 - r) for r ≠ 1.
            • Infinite Series: Converges if the sequence of partial sums {S_n} converges to a limit S. Sum of infinite geometric series: S = G_1 / (1 - r) if |r| < 1.
            • Applications include salary increases (arithmetic), population growth (geometric), and financial calculations.
            """.trimIndent()
        )

        // Unit 2
        addNote(
            "Unit 2 - Introductions to Calculus",
            "Introduction to Derivatives / Application of Derivative / Introduction to Integration",
            """
            • Rate of Change: A measure of how a function's value changes as its input changes. Average Rate of Change on [a, b]: Δy/Δx = (f(b) - f(a)) / (b - a).
            • Gradient at a Point: The slope of the tangent line to the curve at that point.
            • Definition of Derivative: f'(a) = lim (h -> 0) [f(a + h) - f(a)] / h.
            • Applications: Finding instantaneous rates of change, approximate values, equations of tangent/normal lines. Optimization: Finding maximum and minimum values in fields like business (profit/loss), physics (velocity/acceleration), and biology (population growth).
            • Integration: The process of finding a function from its derivative, also known as anti-differentiation.
            • Riemann Sum: For a partition P = {x_0, x_1, ..., x_n} on [a, b], the sum Σ (k=1 to n) f(t_k)Δx_k is a Riemann sum.
            • Definite Integral: If f is a function over [a, b] and F is any anti-derivative of f, then ∫ (a to b) f(x)dx = F(b) - F(a).
            """.trimIndent()
        )

        // Unit 3
        addNote(
            "Unit 3 - Statistics",
            "Measures of Absolute Dispersions / Interpretation of Relative Dispersions / Use of Frequency Curves / Sampling Techniques",
            """
            • Measure of Dispersion: The degree to which numerical data tends to spread about an average value.
            • Absolute Measure of Dispersion: Measures variability in the same units as the data (e.g., Range, Inter-quartile range, Mean deviation, Standard deviation).
            • Range: The difference between the highest and lowest values in a data set.
            • Relative Measure of Dispersion: Compares variability independent of units (e.g., Coefficient of Variation).
            • Coefficient of Range: CR = (L - S) / (L + S), where L is the largest and S is the smallest value.
            • Frequency Curve: A curve that describes the distribution of a data set. Symmetrical Distribution: Mean = Median = Mode.
            • Skewness: Describes the lack of symmetry in a distribution.
            • Sampling: The collection of data from a subset (sample) of a population. Random Sampling: Every individual has an equal chance of selection (e.g., lottery method).
            • Importance: Statistics is used in schools, business forecasting, weather forecasting, and health insurance.
            """.trimIndent()
        )

        // Unit 4
        addNote(
            "Unit 4 - Introduction to Linear Programming",
            "Graphical Solutions of System of Linear Inequalities / Maximum and Minimum Values",
            """
            • Linear Inequality: A statement involving signs like <, >, ≤, or ≥.
            • Graphical Solution: Finding the region of points that satisfy a set of inequalities by plotting them on a coordinate plane.
            • Optimization Problem: A problem that seeks to maximize or minimize a linear function subject to linear constraints.
            • Linear Programming (LP): A field of mathematics dealing with finding max/min values of an objective function.
            • Feasible Region: The set of all points that satisfy the constraints of the LP problem.
            • Optimal Solution: A point in the feasible region that gives the maximum or minimum value of the objective function.
            """.trimIndent()
        )

        // Unit 5
        addNote(
            "Unit 5 - Mathematical Applications in Business",
            "Basic Mathematical Concepts in Business / Time Value of Money / Saving, Investing and Borrowing Money / Taxation",
            """
            • Ratio: A comparison of two numbers by division. Rate: A comparison of two quantities with different units. Proportion: A statement that two ratios are equal. Percentage: A ratio whose second term is 100.
            • Time Value of Money: The concept that money available now is worth more than the same amount in the future.
            • Simple Interest: I = Prt, where P is principal, r is rate, and t is time.
            • Compound Interest: A = P(1 + r/100)^n, where interest is calculated on both principal and accumulated interest.
            • Saving and Investing: Using money to gain future benefits through interest or asset growth. Borrowing: Taking money from a creditor with the obligation to pay it back with interest.
            • Taxation: A compulsory financial charge imposed by the government to fund public spending. Government Functions: Divided into Allocation (services), Distribution (welfare), and Stabilization (economic growth).
            • Federal Tax System: In Ethiopia, tax powers are divided between the federal government and regional states.
            """.trimIndent()
        )

        return notesList + notesList.map { it.copy(id = it.id + "_soc", subjectId = "euee_soc_maths") }
    }
}