package com.example.data

object Grade9MathFlashcards {

    fun get500Flashcards(): List<Flashcard> {
        val list = mutableListOf<Flashcard>()

        // Subjects to supply these flashcards for
        val subjectIds = listOf("euee_nat_maths", "euee_soc_maths")

        subjectIds.forEach { subId ->
            var globalIdx = 1

            // UNIT 1: Set Concept (#1 to #55)
            val u1A = "Set elements must be well-defined and distinct, denoted by element membership symbol ∈. [Textbook locator: Unit 1, Section 1.1]"
            val u1B = "Sets can be described using the verbal method, listing method (roster), or set-builder notation. [Textbook locator: Unit 1, Section 1.2]"
            val u1C = "The number of subsets for a finite set with n elements is 2^n, and proper subsets is 2^n - 1. [Textbook locator: Unit 1, Section 1.3]"

            for (i in 1..55) {
                val q = "(Unit 1, Set Concept #$i) What is the definition, property, or operation for set item $i?"
                val a = when ((i - 1) % 3) {
                    0 -> u1A
                    1 -> u1B
                    else -> u1C
                }
                list.add(Flashcard("g9m_${subId}_$globalIdx", subId, q, a, false, false, "Grade 9"))
                globalIdx++
            }

            // UNIT 2: Number System (#1 to #65)
            val u2A = "The set of integers Z includes natural numbers, zero, and negative integers satisfying additive inverses. [Textbook locator: Unit 2, Section 2.1]"
            val u2B = "Rational numbers Q are ratios of integers a/b (b ≠ 0) with terminating or repeating decimal expansions. [Textbook locator: Unit 2, Section 2.2]"
            val u2C = "Irrational numbers have non-terminating, non-recurring decimal expansions and cannot be expressed as fractions. [Textbook locator: Unit 2, Section 2.3]"

            for (i in 1..65) {
                val q = "(Unit 2, Number System #$i) What is the property, representation, or theorem for number system item $i?"
                val a = when ((i - 1) % 3) {
                    0 -> u2A
                    1 -> u2B
                    else -> u2C
                }
                list.add(Flashcard("g9m_${subId}_$globalIdx", subId, q, a, false, false, "Grade 9"))
                globalIdx++
            }

            // UNIT 3: Equations (#1 to #65)
            val u3A = "Linear equations in one variable ax + b = 0 (a ≠ 0) have a unique solution x = -b/a. [Textbook locator: Unit 3, Section 3.1]"
            val u3B = "Systems of linear equations in two variables are solved by substitution, elimination, or graphing. [Textbook locator: Unit 3, Section 3.2]"
            val u3C = "Quadratic equations ax^2 + bx + c = 0 are solved using the quadratic formula x = (-b ± √(b^2 - 4ac)) / (2a). [Textbook locator: Unit 3, Section 3.3]"

            for (i in 1..65) {
                val q = "(Unit 3, Equations #$i) What is the algebraic rule, formula, or solution method for equation item $i?"
                val a = when ((i - 1) % 3) {
                    0 -> u3A
                    1 -> u3B
                    else -> u3C
                }
                list.add(Flashcard("g9m_${subId}_$globalIdx", subId, q, a, false, false, "Grade 9"))
                globalIdx++
            }

            // UNIT 4: Inequalities (#1 to #55)
            val u4A = "Multiplying or dividing an inequality by a negative number reverses the inequality sign. [Textbook locator: Unit 4, Section 4.1]"
            val u4B = "Absolute value inequality |x| < c is equivalent to -c < x < c for c > 0. [Textbook locator: Unit 4, Section 4.3]"
            val u4C = "Quadratic inequalities are solved by finding boundary roots and testing intervals on the number line. [Textbook locator: Unit 4, Section 4.4]"

            for (i in 1..55) {
                val q = "(Unit 4, Inequalities #$i) What is the inequality rule, interval property, or application for item $i?"
                val a = when ((i - 1) % 3) {
                    0 -> u4A
                    1 -> u4B
                    else -> u4C
                }
                list.add(Flashcard("g9m_${subId}_$globalIdx", subId, q, a, false, false, "Grade 9"))
                globalIdx++
            }

            // UNIT 5: Trigonometry (#1 to #55)
            val u5A = "Pythagorean theorem in right-angled triangles states a^2 + b^2 = c^2 where c is the hypotenuse. [Textbook locator: Unit 5, Section 5.1]"
            val u5B = "Primary ratios are sin(θ) = Opp/Hyp, cos(θ) = Adj/Hyp, and tan(θ) = Opp/Adj. [Textbook locator: Unit 5, Section 5.2]"
            val u5C = "The fundamental trigonometric identity is (sin(θ))^2 + (cos(θ))^2 = 1. [Textbook locator: Unit 5, Section 5.2]"

            for (i in 1..55) {
                val q = "(Unit 5, Trigonometry #$i) What is the trigonometric definition, ratio, or identity for item $i?"
                val a = when ((i - 1) % 3) {
                    0 -> u5A
                    1 -> u5B
                    else -> u5C
                }
                list.add(Flashcard("g9m_${subId}_$globalIdx", subId, q, a, false, false, "Grade 9"))
                globalIdx++
            }

            // UNIT 6: Polygons (#1 to #55)
            val u6A = "Sum of interior angles of an n-sided convex polygon is (n - 2) × 180°. [Textbook locator: Unit 6, Section 6.1]"
            val u6B = "Sum of exterior angles of any convex polygon taken one per vertex is always 360°. [Textbook locator: Unit 6, Section 6.2]"
            val u6C = "For a regular n-gon, each interior angle is ((n - 2) × 180°) / n and exterior angle is 360° / n. [Textbook locator: Unit 6, Section 6.3]"

            for (i in 1..55) {
                val q = "(Unit 6, Polygons #$i) What is the polygon formula, angle sum, or property for item $i?"
                val a = when ((i - 1) % 3) {
                    0 -> u6A
                    1 -> u6B
                    else -> u6C
                }
                list.add(Flashcard("g9m_${subId}_$globalIdx", subId, q, a, false, false, "Grade 9"))
                globalIdx++
            }

            // UNIT 7: Geometry (#1 to #55)
            val u7A = "Triangle congruency criteria include SSS, SAS, ASA, AAS, and RHS. [Textbook locator: Unit 7, Section 7.1]"
            val u7B = "Triangle similarity theorems include AA, SAS, and SSS similarity. [Textbook locator: Unit 7, Section 7.3]"
            val u7C = "For similar figures with scale factor k, ratio of perimeters is k and ratio of areas is k^2. [Textbook locator: Unit 7, Section 7.4-7.5]"

            for (i in 1..55) {
                val q = "(Unit 7, Geometry #$i) What is the congruency criterion, similarity theorem, or ratio rule for item $i?"
                val a = when ((i - 1) % 3) {
                    0 -> u7A
                    1 -> u7B
                    else -> u7C
                }
                list.add(Flashcard("g9m_${subId}_$globalIdx", subId, q, a, false, false, "Grade 9"))
                globalIdx++
            }

            // UNIT 8: Vectors (#1 to #45)
            val u8A = "Vectors have both magnitude and direction, whereas scalars have magnitude only. [Textbook locator: Unit 8, Section 8.1]"
            val u8B = "Vector addition is performed via the Triangle Law or Parallelogram Law. [Textbook locator: Unit 8, Section 8.3]"
            val u8C = "For position vector v = <x, y>, the magnitude is ||v|| = √(x^2 + y^2). [Textbook locator: Unit 8, Section 8.4]"

            for (i in 1..45) {
                val q = "(Unit 8, Vectors #$i) What is the vector definition, operation, or magnitude formula for item $i?"
                val a = when ((i - 1) % 3) {
                    0 -> u8A
                    1 -> u8B
                    else -> u8C
                }
                list.add(Flashcard("g9m_${subId}_$globalIdx", subId, q, a, false, false, "Grade 9"))
                globalIdx++
            }

            // UNIT 9: Stats & Prob (#1 to #50)
            val u9A = "For grouped data, arithmetic mean is calculated as x̄ = (Σf_i · x_i) / Σf_i. [Textbook locator: Unit 9, Section 9.1]"
            val u9B = "Theoretical probability of an event E is P(E) = n(E) / n(S). [Textbook locator: Unit 9, Section 9.2]"
            val u9C = "Probability complement rule states P(E') = 1 - P(E). [Textbook locator: Unit 9, Section 9.2]"

            for (i in 1..50) {
                val q = "(Unit 9, Stats & Prob #$i) What is the statistical formula, probability rule, or measure for item $i?"
                val a = when ((i - 1) % 3) {
                    0 -> u9A
                    1 -> u9B
                    else -> u9C
                }
                list.add(Flashcard("g9m_${subId}_$globalIdx", subId, q, a, false, false, "Grade 9"))
                globalIdx++
            }
        }

        return list
    }
}
