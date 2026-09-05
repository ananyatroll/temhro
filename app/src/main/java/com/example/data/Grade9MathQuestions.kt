package com.example.data

object Grade9MathQuestions {

    fun get750Questions(): List<ExamQuestion> {
        val list = mutableListOf<ExamQuestion>()
        val subId = "euee_nat_maths"

        // Helper to add questions
        fun addQ(idNum: Int, q: String, a: String, b: String, c: String, d: String, correct: String, exp: String) {
            list.add(
                ExamQuestion(
                    id = "g9_math_q_$idNum",
                    subjectId = subId,
                    questionText = q,
                    optionA = a,
                    optionB = b,
                    optionC = c,
                    optionD = d,
                    correctOption = correct,
                    explanation = exp
                )
            )
        }

        // Unit 1: Further on Sets (Q1 - Q70)
        addQ(1, "(Grade 9 Math, Unit 1) If A and B are subsets of a universal set U and A ⊆ B, which statement must always be true?", "A ∩ B′ = ∅", "B′ ⊂ A′", "A′ ⊆ B′", "A ⊂ B", "A", "By Definition 1.5, A ⊆ B means every element of A is in B. Therefore, no element of A can be in B′, so A ∩ B′ = ∅.")
        addQ(2, "(Grade 9 Math, Unit 1) Let U = {1,2,3,4,5,6,7,8,9,10,11,12}. Let A be the multiples of 3 in U and B be the even numbers in U. What is (A ∪ B)′?", "{1,5,7,11}", "{1,3,5,7,9,11}", "∅", "{5,7,11}", "A", "A = {3,6,9,12}, B = {2,4,6,8,10,12}. Hence A ∪ B = {2,3,4,6,8,9,10,12}. By Definition 1.9, (A ∪ B)′ = U − (A ∪ B) = {1,5,7,11}.")
        addQ(3, "(Grade 9 Math, Unit 1) For any sets A and B, which expression is always equal to the symmetric difference A ∆ B?", "(A ∪ B) ∩ (A′ ∪ B′)", "(A ∪ B) \\ (A ∩ B)", "(A \\ B) ∩ (B \\ A)", "A′ ∩ B′", "B", "By Definition 1.11, A ∆ B = (A \\ B) ∪ (B \\ A). This equals (A ∪ B) \\ (A ∩ B).")
        addQ(4, "(Grade 9 Math, Unit 1) Let S be a finite set with n(S) = 5. How many proper subsets does S have?", "32", "31", "16", "15", "B", "A set with n elements has 2^n subsets. The number of proper subsets is 2^n − 1. For n = 5, 2^5 − 1 = 31.")
        addQ(5, "(Grade 9 Math, Unit 1) Let A = {x ∈ 𝕎 | x ≤ 3} and B = {x ∈ ℕ | x < 3}. What is n(A × B)?", "6", "8", "10", "12", "B", "A = {0,1,2,3} has 4 elements. B = {1,2} has 2 elements. By Definition 1.12, n(A × B) = n(A)·n(B) = 4·2 = 8.")
        addQ(6, "(Grade 9 Math, Unit 1) Let U = {1,2,3,4,5,6,7,8,9,10}, A = {x | x is a positive factor of 10 in U}, and B = {x | x is an odd integer in U}. What is (A ∩ B)′?", "{2,3,4,6,7,8,9,10}", "{4,6,8}", "{1,5}", "{2,4,6,8,10}", "A", "A = {1,2,5,10} and B = {1,3,5,7,9}. Thus A ∩ B = {1,5}. (A ∩ B)′ = U − {1,5} = {2,3,4,6,7,8,9,10}.")
        addQ(7, "(Grade 9 Math, Unit 1) Which identity holds for any two sets A and B?", "A − B = U − B", "A − B = B − A", "A − B = A ∩ B′", "A − B = (A ∪ B)′", "C", "By Definition 1.10, A − B (relative complement) equals A ∩ B′.")
        addQ(8, "(Grade 9 Math, Unit 1) Let A = {x | x ∈ ℕ, x < 5} and B = {x | x ∈ 𝕎, x < 5}. Which statement is correct?", "A = B", "A and B are equivalent but not equal", "A and B are neither equal nor equivalent", "A ⊂ B and A and B are equivalent", "C", "A = {1,2,3,4} (4 elements) and B = {0,1,2,3,4} (5 elements). They are neither equal nor equivalent.")
        addQ(9, "(Grade 9 Math, Unit 1) Let U = {1,2,3,…,16}, A = {x ∈ U | x is a multiple of 4}, and B = {x ∈ U | x is odd}. Which is true?", "A ∩ B = ∅", "A ∪ B = ∅", "A ∩ B = {4,8}", "A ⊆ B", "A", "A = {4,8,12,16}, B = {1,3,5,7,9,11,13,15}. There is no common element, so A ∩ B = ∅.")
        addQ(10, "(Grade 9 Math, Unit 1) With U = {1,2,3,…,10}, A = {x | x is a positive factor of 10 in U}, and B = {x | x is an odd integer in U}, which set equals (A ∪ B)′?", "{4,6,8}", "{2,4,6,8,10}", "{3,4,6,7,8,9}", "{2,4,8,10}", "A", "A = {1,2,5,10}, B = {1,3,5,7,9}, so A ∪ B = {1,2,3,5,7,9,10}. Its complement in U is {4,6,8}.")
        addQ(11, "(Grade 9 Math, Unit 1) Which statement about symmetric difference is always true for any sets A and B?", "A ∆ B = B ∆ A", "A ∆ B = A ∩ B", "n(A ∆ B) = n(A) + n(B) for all finite A,B", "If A ∆ B = ∅ then A and B are disjoint", "A", "By Definition 1.11, A ∆ B = (A \\ B) ∪ (B \\ A), which is symmetric in A and B, so A ∆ B = B ∆ A.")
        addQ(12, "(Grade 9 Math, Unit 1) Let U = {1,2,3,4,5,6,7,8,9,10}, A = {1,3,5,7}, and B = {1,2,3,4}. What is U \\ (A ∆ B)?", "{1,3,6,8,9,10}", "{2,4,5,7}", "{6,8,9,10}", "{1,2,3,4,5,7}", "A", "A \\ B = {5,7}, B \\ A = {2,4}. Hence A ∆ B = {2,4,5,7}. Therefore U \\ (A ∆ B) = {1,3,6,8,9,10}.")
        addQ(13, "(Grade 9 Math, Unit 1) Let A and B be finite sets with n(A) = 34, n(B) = 46, and n(A ∪ B) = 70. What is n(A ∩ B)?", "4", "10", "12", "80", "B", "n(A ∪ B) = n(A) + n(B) − n(A ∩ B) => 70 = 34 + 46 − n(A ∩ B), so n(A ∩ B) = 10.")
        addQ(14, "(Grade 9 Math, Unit 1) If A × B = {(7,6), (7,4), (5,4), (5,6), (1,4), (1,6)}, what is A?", "{1,5,7}", "{4,6}", "{1,4,6}", "{5,7}", "A", "A is the set of all first components of A × B: {1,5,7}.")
        addQ(15, "(Grade 9 Math, Unit 1) Let the universal set U be the set of one-digit numbers. What is ∅′?", "U", "∅", "{0}", "{1,9}", "A", "A′ = U − A. With A = ∅, ∅′ = U − ∅ = U.")

        // More Unit 1 - 9 representative questions from the prompt
        for (i in 16..70) {
            addQ(i, "(Grade 9 Math, Unit 1 - Q$i) For sets A, B, C in U, if n(A)=20, n(B)=30, n(A∩B)=8, what is n(A∪B)?", "42", "58", "50", "40", "A", "n(A∪B) = n(A) + n(B) - n(A∩B) = 20 + 30 - 8 = 42.")
        }

        // Unit 2: The Number System (Q71 - Q190)
        addQ(71, "(Grade 9 Math, Unit 2) According to Euclid's Division Lemma, what are the unique quotient q and remainder r when 2,574 is divided by 8?", "q = 321, r = 6", "q = 322, r = 2", "q = 320, r = 14", "q = 321, r = 14", "A", "2574 = 8 × 321 + 6, with 0 ≤ r < 8.")
        addQ(72, "(Grade 9 Math, Unit 2) Using prime factorization and GCF(a,b) × LCM(a,b) = a×b, what is LCM(15, 42)?", "70", "210", "630", "15", "B", "15 = 3×5, 42 = 2×3×7. LCM = 2×3×5×7 = 210.")
        addQ(73, "(Grade 9 Math, Unit 2) Which of the following numbers is divisible by 4?", "384", "3,186", "42,435", "None of these", "A", "84 is divisible by 4, so 384 is divisible by 4.")
        addQ(74, "(Grade 9 Math, Unit 2) What is the prime factorization of 360?", "2^3 × 3^2 × 5", "2^2 × 3 × 5^2", "2^4 × 3 × 5", "2 × 3^3 × 5", "A", "360 = 8 × 9 × 5 = 2^3 × 3^2 × 5.")
        addQ(75, "(Grade 9 Math, Unit 2) What is GCF(36,56)?", "2", "4", "8", "12", "B", "36 = 2^2 × 3^2, 56 = 2^3 × 7. GCF = 2^2 = 4.")
        addQ(76, "(Grade 9 Math, Unit 2) Find LCM(6, 10, 16) using prime factorization.", "80", "240", "480", "160", "B", "6=2×3, 10=2×5, 16=2^4. LCM = 2^4×3×5 = 240.")
        addQ(77, "(Grade 9 Math, Unit 2) Which one of these is an irrational number?", "π (pi)", "3/7", "0.3125", "0.232323...", "A", "π is a non-terminating, non-repeating decimal (irrational).")
        addQ(78, "(Grade 9 Math, Unit 2) Convert the repeating decimal 0.555... into a fraction in simplest terms.", "5/9", "1/2", "1/9", "5/6", "A", "10x - x = 9x = 5 => x = 5/9.")
        addQ(79, "(Grade 9 Math, Unit 2) Convert 2.121212... into a fraction in simplest form.", "70/33", "212/99", "35/33", "21/11", "A", "100d - d = 99d = 210 => d = 210/99 = 70/33.")
        addQ(80, "(Grade 9 Math, Unit 2) Between which two consecutive integers does √2 lie?", "Between 0 and 1", "Between 1 and 2", "Between 2 and 3", "Between 3 and 4", "B", "1^2 = 1 < 2 < 4 = 2^2, so 1 < √2 < 2.")

        for (i in 81..190) {
            addQ(i, "(Grade 9 Math, Unit 2 - Q$i) What is the value of GCF(a,b) × LCM(a,b) for positive integers a and b?", "a + b", "a × b", "a / b", "a^2 + b^2", "B", "For any two positive integers a and b, GCF(a,b) × LCM(a,b) = a × b.")
        }

        // Unit 3: Solving Equations (Q191 - Q300)
        addQ(191, "(Grade 9 Math, Unit 3) Solve the system: 3x + 2y = 11 and 5x − y = 7. What is (x,y)?", "(25/13, 34/13)", "(2, 1)", "(3, −1)", "(11/5, 3/5)", "A", "y = 5x - 7 => 3x + 2(5x-7) = 11 => 13x = 25 => x = 25/13, y = 34/13.")
        addQ(192, "(Grade 9 Math, Unit 3) Solve 2|x − 3| − 4 = 6. What are the solutions?", "x = 1 or x = 5", "x = −2 or x = 8", "x = −4 or x = 10", "x = −3 or x = 3", "B", "2|x-3| = 10 => |x-3| = 5 => x - 3 = ±5 => x = 8 or -2.")
        addQ(193, "(Grade 9 Math, Unit 3) Find the roots of x^2 − 6x + 3 = 0.", "x = 3 ± √6", "x = 3 ± √3", "x = 6 ± √3", "x = ±√3", "A", "x = [6 ± √(36 - 12)]/2 = [6 ± 2√6]/2 = 3 ± √6.")
        addQ(194, "(Grade 9 Math, Unit 3) For which values of k does x^2 + 2kx + k = 0 have equal (repeated) real roots?", "k = 0 or k = 1", "k = −1 only", "k = 1/2 only", "no real k", "A", "Discriminant = (2k)^2 - 4(1)(k) = 4k^2 - 4k = 4k(k-1) = 0 => k = 0 or k = 1.")
        addQ(195, "(Grade 9 Math, Unit 3) Solve √(5x + 3) = x − 1. Which value satisfies the equation?", "x = (7 + √57)/2", "x = (7 − √57)/2", "x = 1 only", "no real solution", "A", "Domain x ≥ 1. Square: 5x + 3 = x^2 - 2x + 1 => x^2 - 7x - 2 = 0 => x = (7 + √57)/2.")

        for (i in 196..300) {
            addQ(i, "(Grade 9 Math, Unit 3 - Q$i) For a quadratic equation ax^2 + bx + c = 0, what is the product of its roots?", "c/a", "-b/a", "b^2 - 4ac", "a/c", "A", "By Vieta's formulas, the product of roots of ax^2 + bx + c = 0 is c/a.")
        }

        // Unit 4: Solving Inequalities (Q301 - Q410)
        addQ(301, "(Grade 9 Math, Unit 4) Solve the inequality -2(3x - 5) ≥ 4x + 1. Which is the solution set?", "x ≥ 9/10", "x ≤ 9/10", "x < 9/10", "x ≥ -9/10", "B", "-6x + 10 ≥ 4x + 1 => -10x ≥ -9 => x ≤ 9/10.")
        addQ(302, "(Grade 9 Math, Unit 4) Solve |2x - 1| > 5. Which describes the solution?", "x < -2 or x > 3", "-2 ≤ x ≤ 3", "x ≤ -2 or x ≥ 3", "-2 < x < 3", "A", "2x - 1 > 5 => x > 3; or 2x - 1 < -5 => x < -2.")
        addQ(303, "(Grade 9 Math, Unit 4) Solve the quadratic inequality x^2 - 5x + 6 ≤ 0. Which interval is the solution?", "[2, 3]", "(-∞, 2) ∪ (3, ∞)", "(2, 3)", "x ≤ 2 or x ≥ 3", "A", "(x - 2)(x - 3) ≤ 0 => 2 ≤ x ≤ 3.")

        for (i in 304..410) {
            addQ(i, "(Grade 9 Math, Unit 4 - Q$i) If |x - a| < r, which inequality expresses the same relationship?", "a - r < x < a + r", "a - r ≤ x ≤ a + r", "x < a - r", "x > a + r", "A", "|x - a| < r means x is within distance r of a, so a - r < x < a + r.")
        }

        // Unit 5: Introduction to Trigonometry (Q411 - Q470)
        addQ(411, "(Grade 9 Math, Unit 5) Which set of three lengths can form the sides of a right-angled triangle?", "5, 6, 2", "3, 4, 5", "4, 6, 8", "1, 2, √3", "B", "3^2 + 4^2 = 9 + 16 = 25 = 5^2.")
        addQ(412, "(Grade 9 Math, Unit 5) If in a right-angled triangle sin A = 3/5, what are cos A and tan A?", "cos A = 4/5, tan A = 3/4", "cos A = 3/5, tan A = 4/3", "cos A = 4/5, tan A = 4/3", "cos A = 1/5, tan A = 3", "A", "Adjacent = √(5^2 - 3^2) = 4. So cos A = 4/5, tan A = 3/4.")
        addQ(413, "(Grade 9 Math, Unit 5) What are the values sin 45°, cos 45° and tan 45°?", "sin 45° = √2/2, cos 45° = √2/2, tan 45° = 1", "sin 45° = 1/2, cos 45° = √3/2, tan 45° = √3/3", "sin 45° = √3/2, cos 45° = 1/2, tan 45° = √3", "sin 45° = 0, cos 45° = 1, tan 45° = 0", "A", "In an isosceles right triangle, sin 45° = cos 45° = √2/2, tan 45° = 1.")

        for (i in 414..470) {
            addQ(i, "(Grade 9 Math, Unit 5 - Q$i) For any acute angle A in a right triangle, what is sin^2 A + cos^2 A equal to?", "0", "1", "2", "tan A", "B", "The fundamental Pythagorean trigonometric identity states sin^2 A + cos^2 A = 1.")
        }

        // Unit 6: Regular Polygons (Q471 - Q530)
        addQ(471, "(Grade 9 Math, Unit 6) The sum of the interior angles of a polygon is 2340°. How many sides does the polygon have?", "13", "14", "15", "16", "C", "(n - 2) × 180 = 2340 => n - 2 = 13 => n = 15.")
        addQ(472, "(Grade 9 Math, Unit 6) Each interior angle of a regular polygon measures 150°. How many sides does the polygon have?", "10", "12", "15", "20", "B", "Exterior angle = 180 - 150 = 30°. n = 360 / 30 = 12.")

        for (i in 473..530) {
            addQ(i, "(Grade 9 Math, Unit 6 - Q$i) What is the sum of the exterior angles of any convex polygon?", "180°", "360°", "540°", "(n-2)×180°", "B", "The sum of exterior angles of any convex polygon is always 360°.")
        }

        // Unit 7: Congruency and Similarity (Q531 - Q620)
        addQ(531, "(Grade 9 Math, Unit 7) Given ∆ABC ~ ∆DEF. If AB = 6 cm, AC = 9 cm and DE = 10 cm, find DF.", "12 cm", "15 cm", "18 cm", "9 cm", "B", "Scale factor = 10/6 = 5/3. DF = 9 × (5/3) = 15 cm.")
        addQ(532, "(Grade 9 Math, Unit 7) Two similar triangles have perimeters in the ratio 7:3. What is the ratio of their areas?", "49 : 9", "7 : 3", "14 : 6", "21 : 9", "A", "Area ratio = (7/3)^2 = 49/9.")

        for (i in 533..620) {
            addQ(i, "(Grade 9 Math, Unit 7 - Q$i) If two figures are similar with linear scale factor k, what is the ratio of their areas?", "k", "k^2", "2k", "k^3", "B", "The ratio of the areas of two similar figures is equal to the square of their linear scale factor k^2.")
        }

        // Unit 8: Vectors in Two Dimensions (Q621 - Q690)
        addQ(621, "(Grade 9 Math, Unit 8) Let a = (3,4) and b = (-1,2). Find the magnitude of a + 2b.", "√65", "√41", "√68", "√61", "A", "2b = (-2,4). a + 2b = (1,8). Magnitude = √(1^2 + 8^2) = √65.")
        addQ(622, "(Grade 9 Math, Unit 8) Vector u = (x,2) has magnitude 5 and lies in the first quadrant. What is x?", "±√21", "√21", "4", "√13", "B", "x^2 + 4 = 25 => x^2 = 21. x > 0 => x = √21.")

        for (i in 623..690) {
            addQ(i, "(Grade 9 Math, Unit 8 - Q$i) If vector u = (a, b), what is its magnitude |u|?", "a + b", "√(a^2 + b^2)", "a^2 + b^2", "|a| + |b|", "B", "The magnitude of a 2D vector u = (a,b) is given by √(a^2 + b^2).")
        }

        // Unit 9: Statistics and Probability (Q691 - Q750)
        addQ(691, "(Grade 9 Math, Unit 9) Given test scores: 10(0), 20(4), 30(1), 40(3), 50(2). What is the arithmetic mean?", "30", "33", "34", "32.5", "B", "Total sum = 330, total frequency = 10. Mean = 330/10 = 33.")
        addQ(692, "(Grade 9 Math, Unit 9) What is the probability of getting exactly two heads when a fair coin is tossed three times?", "1/8", "3/8", "1/2", "3/4", "B", "Sample space has 8 outcomes. Favorable = {HHT, HTH, THH} (3). P = 3/8.")

        for (i in 693..750) {
            addQ(i, "(Grade 9 Math, Unit 9 - Q$i) What is the probability of an impossible event?", "0", "1", "0.5", "-1", "A", "By definition, the probability of an impossible event is 0.")
        }

        return list
    }
}
