package com.example.data

object Grade9MathNotes {

    fun getGrade9MathNotes(): List<SubjectNote> {
        val notesList = mutableListOf<SubjectNote>()
        val subjectIds = listOf("euee_nat_maths", "euee_soc_maths")

        subjectIds.forEach { subId ->
            var idx = 1

            fun addNote(unit: String, title: String, content: String) {
                notesList.add(
                    SubjectNote(
                        id = "g9_math_note_${subId}_$idx",
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
                "Unit 1 - Further on Sets",
                "Sets and Elements (Section 1.1)",
                """
                • A set is defined as a collection of well-defined objects or elements, meaning that given any object, we can definitively determine whether it belongs to the set or not (Chapter 1, Section 1.1, Page 2).
                • The Greek symbol ∈ (epsilon) denotes that an element belongs to a set (e.g., a ∈ A), while ∉ denotes that an element does not belong to a set (e.g., b ∉ A) (Chapter 1, Section 1.1, Page 2).
                • Example: The collection of students in your class is a well-defined set because its members are clearly known, whereas the collection of "kind students" in your school is not well-defined because membership cannot be objectively determined (Chapter 1, Section 1.1, Pages 2–3).
                • Applications of well-defined sets form the foundational basis for defining relations, functions, and advanced mathematical structures (Chapter 1, Section 1.1, Page 1).
                """.trimIndent()
            )

            addNote(
                "Unit 1 - Further on Sets",
                "Set Description (Section 1.2)",
                """
                • Sets can be described using the verbal method (statement form in ordinary English words), the complete listing method (roster method listing all elements), or the set-builder method (Chapter 1, Section 1.2, Pages 4–5).
                • The set-builder notation is written as A = {x | P(x)} or A = {x : P(x)}, read as "set A is the set of all elements x such that x satisfies property P(x)" (Chapter 1, Section 1.2, Page 5).
                • Example: The set of positive even numbers less than 10 can be expressed in listing method as {2, 4, 6, 8} and in set-builder method as {x | x is an even natural number and x < 10} (Chapter 1, Section 1.2, Pages 4–5).
                • Distinction: Complete listing is used for finite sets with a manageable number of elements, while incomplete listing using ellipsis (…) is used for infinite or large patterns (Chapter 1, Section 1.2, Page 4).
                """.trimIndent()
            )

            addNote(
                "Unit 1 - Further on Sets",
                "The Notion of Sets (Section 1.3)",
                """
                • An empty set (or null set) contains no elements and is denoted by ∅ or {} (Chapter 1, Section 1.3, Page 7).
                • A universal set (denoted by U) is a set containing elements of all related sets without repetition (Chapter 1, Section 1.3, Page 9).
                • Set A is a subset of B (A ⊆ B) if every element of A is also in B. A is a proper subset of B (A ⊂ B) if A ⊆ B and A ≠ B (Chapter 1, Section 1.3, Pages 8–10).
                • Formulas: The number of subsets of a finite set with n elements is 2^n, and the number of proper subsets is 2^n - 1 (Chapter 1, Section 1.3, Page 10).
                • Two finite sets A and B are equivalent (A ↔ B or A ~ B) if they have a one-to-one correspondence, or equivalently n(A) = n(B) (Chapter 1, Section 1.3, Page 9).
                """.trimIndent()
            )

            addNote(
                "Unit 1 - Further on Sets",
                "Operations on Sets (Section 1.4)",
                """
                • A Venn diagram is a schematic representation using interlocking circles enclosed in a rectangle (representing the universal set U) to illustrate set relationships and operations (Chapter 1, Section 1.4, Pages 12–13).
                • Union (A ∪ B): The set of all elements either in A or in B (or both); A ∪ B = {x | x ∈ A or x ∈ B} (Chapter 1, Section 1.4, Definition 1.7, Page 13).
                • Intersection (A ∩ B): The set of all elements in both A and B; A ∩ B = {x | x ∈ A and x ∈ B}. Sets are disjoint if A ∩ B = ∅ (Chapter 1, Section 1.4, Definition 1.8, Page 13).
                • Absolute complement (A'): The set of all elements of U not in A; A' = {x | x ∈ U and x ∉ A} (Chapter 1, Section 1.4, Definition 1.9, Page 15).
                • Difference of sets (A - B or A \ B): The set of elements in A and not in B; A - B = {x | x ∈ A and x ∉ B} = A ∩ B' (Chapter 1, Section 1.4, Definition 1.10, Page 16).
                • Symmetric difference (A Δ B): A Δ B = (A \ B) ∪ (B \ A) = (A ∪ B) \ (A ∩ B) (Chapter 1, Section 1.4, Definition 1.11, Page 18).
                • Cartesian product (A × B): The set of all ordered pairs (a, b) such that a ∈ A and b ∈ B (Chapter 1, Section 1.4, Definition 1.12, Page 19).
                • De Morgan's Laws: For any sets A and B, (A ∪ B)' = A' ∩ B' and (A ∩ B)' = A' ∪ B' (Chapter 1, Section 1.4, Page 16).
                """.trimIndent()
            )

            addNote(
                "Unit 1 - Further on Sets",
                "Application (Section 1.5)",
                """
                • Cardinality formula for the union of two finite sets: n(A ∪ B) = n(A) + n(B) - n(A ∩ B) (Chapter 1, Section 1.5, Page 20).
                • If sets A and B are disjoint (A ∩ B = ∅), then n(A ∩ B) = 0 and n(A ∪ B) = n(A) + n(B) (Chapter 1, Section 1.5, Page 20).
                • Application: Used in solving survey and counting problems involving overlapping categories (Chapter 1, Section 1.5, Pages 20–21).
                • Worked Example: Given n(A) = 20, n(B) = 28, and n(A ∪ B) = 36, the intersection cardinality is n(A ∩ B) = n(A) + n(B) - n(A ∪ B) = 20 + 28 - 36 = 12 (Chapter 1, Section 1.5, Page 21).
                """.trimIndent()
            )

            // Unit 2
            addNote(
                "Unit 2 - The Number System",
                "Revision on Natural Numbers and Integers (Section 2.1)",
                """
                • Natural numbers (ℕ) are counting numbers starting from 1 (ℕ = {1, 2, 3, …}), while whole numbers (W) include zero (W = {0, 1, 2, 3, …}) (Chapter 2, Section 2.1, Page 28).
                • Integers (ℤ) consist of positive integers, negative integers, and zero (ℤ = {…, -3, -2, -1, 0, 1, 2, 3, …}) (Chapter 2, Section 2.1, Page 28).
                • Operations on integers follow strict rules for addition, subtraction, multiplication, and division, including properties such as commutative, associative, and distributive laws (Chapter 2, Section 2.1, Pages 29–35).
                • Divisibility rules, prime numbers, composite numbers, Highest Common Factor (HCF), and Least Common Multiple (LCM) form the basis of number theory revision (Chapter 2, Section 2.1, Pages 36–44).
                """.trimIndent()
            )

            addNote(
                "Unit 2 - The Number System",
                "Rational Numbers (Section 2.2)",
                """
                • A rational number is any number that can be expressed in the form a/b, where a and b are integers and b ≠ 0 (Chapter 2, Section 2.2, Definition 2.1, Page 45).
                • Every rational number can be represented either as a terminating decimal or as a non-terminating repeating (recurring) decimal (Chapter 2, Section 2.2, Pages 46–49).
                • Operations (addition, subtraction, multiplication, and division) on rational numbers maintain closure, commutativity, associativity, and distributivity (Chapter 2, Section 2.2, Pages 48–50).
                • Example: Converting a repeating decimal like 0.3̄ into a fraction 1/3 using algebraic manipulation (Chapter 2, Section 2.2, Page 49).
                """.trimIndent()
            )

            addNote(
                "Unit 2 - The Number System",
                "Irrational Numbers (Section 2.3)",
                """
                • An irrational number is a number that cannot be expressed as a ratio of two integers; its decimal representation is non-terminating and non-recurring (Chapter 2, Section 2.3, Definition 2.2, Page 51).
                • Examples of irrational numbers include √2, √3, π, and non-perfect square roots (Chapter 2, Section 2.3, Pages 51–55).
                • Radicals (surds) obey laws of radicals such as √(a · b) = √a · √b and √(a/b) = √a / √b for a, b ≥ 0 (b ≠ 0) (Chapter 2, Section 2.3, Pages 56–60).
                • Simplification and rationalization of denominators (e.g., rationalizing 1/√a or 1/(√a + √b)) are essential techniques (Chapter 2, Section 2.3, Pages 58–60).
                """.trimIndent()
            )

            addNote(
                "Unit 2 - The Number System",
                "Real Numbers (Section 2.4)",
                """
                • The set of real numbers (ℝ) is the union of the set of rational numbers and the set of irrational numbers (ℝ = ℚ ∪ ℚ') (Chapter 2, Section 2.4, Definition 2.3, Page 61).
                • Real numbers can be represented continuously on the real number line, where each point corresponds to a unique real number (Chapter 2, Section 2.4, Pages 62–65).
                • Properties of real numbers include field axioms (closure, commutative, associative, identity, inverse, and distributive properties) and order properties (trichotomy law: a < b, a = b, or a > b) (Chapter 2, Section 2.4, Pages 66–75).
                • Exponents and logarithms: Laws of exponents (a^m · a^n = a^(m+n), (a^m)^n = a^(mn), etc.) and scientific notation for very large and very small numbers (Chapter 2, Section 2.4, Pages 76–94).
                """.trimIndent()
            )

            addNote(
                "Unit 2 - The Number System",
                "Application (Section 2.5)",
                """
                • Real number operations and properties are applied to solve real-world problems involving measurements, finance, proportions, and scientific calculations (Chapter 2, Section 2.5, Pages 95–98).
                • Worked Example: Calculating percentages, profit/loss, simple interest, and proportional scaling using real number arithmetic (Chapter 2, Section 2.5, Pages 96–98).
                • Distinction: Exact values using radicals versus approximate decimal approximations in practical engineering and measurement contexts (Chapter 2, Section 2.5, Page 95).
                """.trimIndent()
            )

            // Unit 3
            addNote(
                "Unit 3 - Solving Equations",
                "Revision on Linear Equation in One Variable (Section 3.1)",
                """
                • A linear equation in one variable is an equation of the form ax + b = 0, where a and b are real numbers and a ≠ 0 (Chapter 3, Section 3.1, Page 106).
                • Solution method involves isolating the variable using inverse operations (addition, subtraction, multiplication, division) while maintaining equation balance (Chapter 3, Section 3.1, Pages 106–108).
                • Example: Solving 3x - 5 = 10 gives 3x = 15 ⇒ x = 5 (Chapter 3, Section 3.1, Page 107).
                • Application: Translating verbal word problems into linear algebraic equations and solving them (Chapter 3, Section 3.1, Page 108).
                """.trimIndent()
            )

            addNote(
                "Unit 3 - Solving Equations",
                "Systems of Linear Equations in Two Variables (Section 3.2)",
                """
                • A system of linear equations in two variables consists of two linear equations sharing two variables, generally written as {a1 x + b1 y = c1, a2 x + b2 y = c2} (Chapter 3, Section 3.2, Page 109).
                • Methods of solution include graphing, substitution method, and elimination (addition/subtraction) method (Chapter 3, Section 3.2, Pages 110–122).
                • Condition for solutions: Consistent and independent (unique solution), consistent and dependent (infinitely many solutions), or inconsistent (no solution) based on coefficient ratios a1/a2, b1/b2, c1/c2 (Chapter 3, Section 3.2, Pages 115–120).
                • Worked Example: Solving {x + y = 5, 2x - y = 1} by elimination yields 3x = 6 ⇒ x = 2, y = 3 (Chapter 3, Section 3.2, Pages 112–113).
                """.trimIndent()
            )

            addNote(
                "Unit 3 - Solving Equations",
                "Solving Non-linear Equations (Section 3.3)",
                """
                • Non-linear equations include quadratic equations of the form ax² + bx + c = 0 (a ≠ 0) and equations reducible to linear or quadratic forms (Chapter 3, Section 3.3, Page 123).
                • Methods for solving quadratic equations: Factoring (zero-product property), completing the square, and using the quadratic formula x = (-b ± √(b² - 4ac)) / 2a (Chapter 3, Section 3.3, Pages 124–135).
                • Discriminant (Δ = b² - 4ac): Determines the nature of roots (Δ > 0 ⇒ two distinct real roots; Δ = 0 ⇒ one repeated real root; Δ < 0 ⇒ no real roots) (Chapter 3, Section 3.3, Pages 130–132).
                • Radical equations and rational equations that lead to quadratic or linear forms, requiring checks for extraneous solutions (Chapter 3, Section 3.3, Pages 136–142).
                """.trimIndent()
            )

            addNote(
                "Unit 3 - Solving Equations",
                "Applications of Equations (Section 3.4)",
                """
                • Equations are applied to model physical and economic situations such as motion problems (d = rt), work rates, age problems, and geometric area/perimeter problems (Chapter 3, Section 3.4, Pages 143–146).
                • Strategy: Define variables, set up the mathematical equation based on the problem statement, solve the equation, and verify the solution in the context of the original problem (Chapter 3, Section 3.4, Pages 143–146).
                • Worked Example: Finding dimensions of a rectangle given its perimeter and area relationship using quadratic equations (Chapter 3, Section 3.4, Pages 144–145).
                """.trimIndent()
            )

            // Unit 4
            addNote(
                "Unit 4 - Solving Inequalities",
                "Revision on Linear Inequalities in One Variable (Section 4.1)",
                """
                • A linear inequality in one variable uses inequality symbols (<, >, ≤, ≥) and can be written in forms like ax + b < 0 (a ≠ 0) (Chapter 4, Section 4.1, Page 152).
                • Rule of operation: Adding or subtracting the same number preserves inequality direction; multiplying or dividing by a positive number preserves direction, while multiplying or dividing by a negative number reverses the inequality sign (Chapter 4, Section 4.1, Pages 152–156).
                • Solution sets can be represented on the real number line, in set-builder notation, and in interval notation (e.g., (a, b], [a, ∞)) (Chapter 4, Section 4.1, Pages 153–155).
                • Example: Solving -2x + 4 ≥ 10 ⇒ -2x ≥ 6 ⇒ x ≤ -3, expressed as (-∞, -3] (Chapter 4, Section 4.1, Page 154).
                """.trimIndent()
            )

            addNote(
                "Unit 4 - Solving Inequalities",
                "Systems of Linear Inequalities in Two Variables (Section 4.2)",
                """
                • A system of linear inequalities in two variables consists of two or more linear inequalities sharing variables x and y (Chapter 4, Section 4.2, Page 157).
                • Solution method: Graphing each inequality on the Cartesian plane, determining the half-plane satisfying each condition, and finding the overlapping (intersecting) region as the solution set (Chapter 4, Section 4.2, Pages 157–165).
                • Boundary lines: Solid lines are used for ≤ or ≥ (inclusive), and dashed lines are used for < or > (strict inequalities) (Chapter 4, Section 4.2, Pages 158–160).
                • Application: Linear programming foundational regions for optimization problems (Chapter 4, Section 4.2, Pages 163–165).
                """.trimIndent()
            )

            addNote(
                "Unit 4 - Solving Inequalities",
                "Inequalities Involving Absolute Value (Section 4.3)",
                """
                • Absolute value inequalities involve expressions within absolute value bars, such as |x| < c, |x| > c, |ax + b| ≤ c (where c > 0) (Chapter 4, Section 4.3, Page 166).
                • Theorems:
                  1. |x| < c ⇔ -c < x < c
                  2. |x| > c ⇔ x < -c or x > c (Chapter 4, Section 4.3, Pages 166–170).
                • Worked Example: Solving |2x - 3| ≤ 5 translates to -5 ≤ 2x - 3 ≤ 5, leading to -2 ≤ 2x ≤ 8 ⇒ -1 ≤ x ≤ 4 (Chapter 4, Section 4.3, Pages 167–168).
                """.trimIndent()
            )

            addNote(
                "Unit 4 - Solving Inequalities",
                "Quadratic Inequalities (Section 4.4)",
                """
                • A quadratic inequality in one variable can be written in the form ax² + bx + c > 0 (or <, ≤, ≥) where a ≠ 0 (Chapter 4, Section 4.4, Page 171).
                • Solution method: Find the roots of the corresponding quadratic equation ax² + bx + c = 0, factor the expression as a(x - r1)(x - r2) ◯ 0, and test intervals on the number line or analyze parabola concavity (Chapter 4, Section 4.4, Pages 171–175).
                • Example: Solving x² - 5x + 6 < 0 factors into (x - 2)(x - 3) < 0, yielding the solution interval 2 < x < 3 or (2, 3) (Chapter 4, Section 4.4, Pages 172–173).
                """.trimIndent()
            )

            addNote(
                "Unit 4 - Solving Inequalities",
                "Applications of Inequalities (Section 4.5)",
                """
                • Inequalities are used to model practical constraints in business, engineering, budgeting, and resource allocation where limits are maximum or minimum thresholds (Chapter 4, Section 4.5, Pages 176–181).
                • Strategy: Translate descriptive problem statements into mathematical inequalities, solve, and interpret the range of valid practical solutions (Chapter 4, Section 4.5, Pages 176–181).
                • Worked Example: Determining the range of scores a student needs on a final test to achieve a target average grade (Chapter 4, Section 4.5, Pages 178–179).
                """.trimIndent()
            )

            // Unit 5
            addNote(
                "Unit 5 - Introduction to Trigonometry",
                "Revision on Right-angled Triangles (Section 5.1)",
                """
                • A right-angled triangle contains one 90-degree angle, with sides related by the Pythagorean Theorem: a² + b² = c², where c is the hypotenuse and a, b are the legs (Chapter 5, Section 5.1, Page 186).
                • Special right-angled triangles include the 45°-45°-90° triangle (sides in ratio 1 : 1 : √2) and the 30°-60°-90° triangle (sides in ratio 1 : √3 : 2) (Chapter 5, Section 5.1, Pages 187–189).
                • Application: Finding unknown side lengths or angles in geometric and surveying problems using Pythagorean relations (Chapter 5, Section 5.1, Pages 186–189).
                """.trimIndent()
            )

            addNote(
                "Unit 5 - Introduction to Trigonometry",
                "Trigonometric Ratios (Section 5.2)",
                """
                • For an acute angle θ in a right-angled triangle, the primary trigonometric ratios are defined as:
                  - Sine (sin θ) = Opposite / Hypotenuse
                  - Cosine (cos θ) = Adjacent / Hypotenuse
                  - Tangent (tan θ) = Opposite / Adjacent (Chapter 5, Section 5.2, Definition 5.1, Pages 190–193).
                • Reciprocal trigonometric ratios: Cosecant (csc θ = 1 / sin θ), Secant (sec θ = 1 / cos θ), and Cotangent (cot θ = 1 / tan θ) (Chapter 5, Section 5.2, Page 194).
                • Fundamental trigonometric identities: tan θ = sin θ / cos θ and (sin θ)² + (cos θ)² = 1 (Chapter 5, Section 5.2, Pages 195–196).
                • Standard angle values: Exact trigonometric ratios for 30°, 45°, and 60° derived from special right triangles (Chapter 5, Section 5.2, Pages 197–201).
                """.trimIndent()
            )

            // Unit 6
            addNote(
                "Unit 6 - Regular Polygons",
                "Sum of Interior Angles of a Convex Polygon (Section 6.1)",
                """
                • A polygon is a closed plane figure formed by three or more line segments; a convex polygon has all interior angles strictly less than 180° and no diagonals lying outside the polygon (Chapter 6, Section 6.1, Page 208).
                • Theorem: The sum of the interior angles (Si) of an n-sided convex polygon is given by the formula Si = (n - 2) × 180° (Chapter 6, Section 6.1, Theorem 6.1, Pages 208–215).
                • Derivation: Dividing an n-gon into (n - 2) triangles from a single vertex (Chapter 6, Section 6.1, Pages 209–210).
                • Example: For a pentagon (n = 5), the sum of interior angles is (5 - 2) × 180° = 3 × 180° = 540° (Chapter 6, Section 6.1, Page 211).
                """.trimIndent()
            )

            addNote(
                "Unit 6 - Regular Polygons",
                "Sum of Exterior Angles of a Convex Polygon (Section 6.2)",
                """
                • An exterior angle of a polygon is formed by extending one side and is supplementary to its adjacent interior angle (Chapter 6, Section 6.2, Page 216).
                • Theorem: For any convex polygon, the sum of the exterior angles (taken one at each vertex in the same direction) is always equal to 360°, regardless of the number of sides n (Chapter 6, Section 6.2, Theorem 6.2, Pages 216–220).
                • Worked Example: Calculating missing exterior angles or verifying polygon regularity using the constant 360° sum property (Chapter 6, Section 6.2, Pages 217–219).
                """.trimIndent()
            )

            addNote(
                "Unit 6 - Regular Polygons",
                "Measures of Each Interior Angle and Exterior Angle of a Regular Polygon (Section 6.3)",
                """
                • A regular polygon is both equilateral (all sides equal) and equiangular (all angles equal) (Chapter 6, Section 6.3, Page 221).
                • Formulas for a regular n-gon:
                  - Measure of each interior angle = ((n - 2) × 180°) / n
                  - Measure of each exterior angle = 360° / n (Chapter 6, Section 6.3, Pages 221–223).
                • Relationship: Interior angle + Exterior angle = 180° (supplementary) (Chapter 6, Section 6.3, Page 222).
                """.trimIndent()
            )

            addNote(
                "Unit 6 - Regular Polygons",
                "Properties of Regular Polygons (Section 6.4)",
                """
                • Regular polygons possess rotational symmetry, lines of symmetry equal to the number of sides n, and can be inscribed in or circumscribed about circles (Chapter 6, Section 6.4, Pages 224–232).
                • Formulas for area (A) and perimeter (P) of regular polygons using apothem (a) and perimeter: A = ½ · a · P (Chapter 6, Section 6.4, Pages 228–230).
                • Applications in tiling (tessellations), architecture, and design (Chapter 6, Section 6.4, Pages 231–232).
                """.trimIndent()
            )

            // Unit 7
            addNote(
                "Unit 7 - Congruency and Similarity",
                "Revision on Congruency of Triangles (Section 7.1)",
                """
                • Two triangles are congruent if they have exactly the same shape and size (corresponding sides and angles are equal) (Chapter 7, Section 7.1, Page 240).
                • Standard congruency criteria: SSS (Side-Side-Side), SAS (Side-Angle-Side), ASA (Angle-Side-Angle), AAS (Angle-Angle-Side), and RHS (Right angle-Hypotenuse-Side for right triangles) (Chapter 7, Section 7.1, Pages 241–244).
                • Application: Proving geometric properties, line segment equality, and angle bisectors (Chapter 7, Section 7.1, Pages 240–244).
                """.trimIndent()
            )

            addNote(
                "Unit 7 - Congruency and Similarity",
                "Definition of Similar Figures (Section 7.2)",
                """
                • Similar figures have the same shape but not necessarily the same size (corresponding angles are equal and corresponding sides are proportional) (Chapter 7, Section 7.2, Definition 7.1, Pages 245–248).
                • Scale factor (k): The ratio of any two corresponding side lengths of similar figures (k = side of image / side of original) (Chapter 7, Section 7.2, Pages 246–247).
                • Distinction: Congruence is a special case of similarity where the scale factor k = 1 (Chapter 7, Section 7.2, Page 248).
                """.trimIndent()
            )

            addNote(
                "Unit 7 - Congruency and Similarity",
                "Theorems on Similar Plane Figures (Section 7.3)",
                """
                • Triangle Similarity Theorems:
                  1. AAA (Angle-Angle) Similarity Theorem
                  2. SAS (Side-Angle-Side) Similarity Theorem
                  3. SSS (Side-Side-Side) Similarity Theorem (Chapter 7, Section 7.3, Theorems 7.1–7.3, Pages 249–258).
                • Basic Proportionality Theorem (Thales's Theorem): If a line is drawn parallel to one side of a triangle intersecting the other two sides, then it divides the two sides in the same ratio (Chapter 7, Section 7.3, Theorem 7.4, Pages 252–255).
                """.trimIndent()
            )

            addNote(
                "Unit 7 - Congruency and Similarity",
                "Ratio of Perimeters of Similar Plane Figures (Section 7.4)",
                """
                • Theorem: If two plane figures are similar with a scale factor k, then the ratio of their perimeters is equal to the scale factor k (Chapter 7, Section 7.4, Theorem 7.5, Pages 259–261).
                • Example: If the ratio of sides of two similar triangles is 2 : 3, the ratio of their perimeters is also 2 : 3 (Chapter 7, Section 7.4, Page 260).
                """.trimIndent()
            )

            addNote(
                "Unit 7 - Congruency and Similarity",
                "Ratio of Areas of Similar Plane Figures (Section 7.5)",
                """
                • Theorem: If two plane figures are similar with a scale factor k, then the ratio of their areas is equal to the square of the scale factor (k²) (Chapter 7, Section 7.5, Theorem 7.6, Pages 262–264).
                • Example: If the linear scale factor between two similar polygons is 3 : 4, the ratio of their areas is 3² : 4² = 9 : 16 (Chapter 7, Section 7.5, Page 263).
                """.trimIndent()
            )

            addNote(
                "Unit 7 - Congruency and Similarity",
                "Construction of Similar Plane Figure (Section 7.6)",
                """
                • Procedures for geometrically constructing similar polygons and triangles using a ruler, compass, and proportional division techniques (Chapter 7, Section 7.6, Pages 265–266).
                • Scaling figures from a center of dilation with a given scale factor k (Chapter 7, Section 7.6, Pages 265–266).
                """.trimIndent()
            )

            addNote(
                "Unit 7 - Congruency and Similarity",
                "Applications of Similarities (Section 7.7)",
                """
                • Similar figures are applied in indirect measurement, map scaling, architectural blueprints, and estimating heights of tall structures (trees, buildings) using shadow lengths or proportional triangles (Chapter 7, Section 7.7, Pages 267–272).
                • Worked Example: Calculating the height of a tree using a shadow-matching similar triangle setup (Chapter 7, Section 7.7, Pages 268–269).
                """.trimIndent()
            )

            // Unit 8
            addNote(
                "Unit 8 - Vectors in Two Dimensions",
                "Vector and Scalar Quantities (Section 8.1)",
                """
                • A scalar quantity has magnitude only (e.g., mass, time, temperature, speed), whereas a vector quantity has both magnitude and direction (e.g., displacement, velocity, force) (Chapter 8, Section 8.1, Pages 279–281).
                • Distinction: Scalars obey ordinary algebraic rules, while vectors obey geometric vector addition rules (Chapter 8, Section 8.1, Page 280).
                """.trimIndent()
            )

            addNote(
                "Unit 8 - Vectors in Two Dimensions",
                "Representation of a Vector (Section 8.2)",
                """
                • A vector is geometrically represented by a directed line segment (an arrow) where the length represents magnitude and the arrowhead indicates direction (Chapter 8, Section 8.2, Pages 282–287).
                • Notation: Denoted as AB⃗ or boldface a, with component form a = (x, y)ᵀ or ⟨x, y⟩ in the Cartesian coordinate plane (Chapter 8, Section 8.2, Pages 283–285).
                • Magnitude formula: If a = ⟨x, y⟩, its magnitude (length) is ||a|| = √(x² + y²) (Chapter 8, Section 8.2, Page 286).
                """.trimIndent()
            )

            addNote(
                "Unit 8 - Vectors in Two Dimensions",
                "Vectors Operations (Section 8.3)",
                """
                • Vector Addition: Geometrically by the triangle law or parallelogram law; algebraically by adding corresponding components: ⟨x1, y1⟩ + ⟨x2, y2⟩ = ⟨x1 + x2, y1 + y2⟩ (Chapter 8, Section 8.3, Pages 288–292).
                • Scalar Multiplication: Multiplying a vector by a real number k scales its magnitude by |k| and reverses its direction if k < 0; component-wise as k⟨x, y⟩ = ⟨kx, ky⟩ (Chapter 8, Section 8.3, Pages 293–295).
                • Subtraction: a - b = a + (-b) = ⟨x1 - x2, y1 - y2⟩ (Chapter 8, Section 8.3, Page 296).
                • Dot Product (Scalar Product): a · b = x1 x2 + y1 y2 = ||a|| ||b|| cos θ (Chapter 8, Section 8.3, Pages 297–298).
                """.trimIndent()
            )

            addNote(
                "Unit 8 - Vectors in Two Dimensions",
                "Position Vector (Section 8.4)",
                """
                • A position vector is a vector whose initial point is at the origin (0, 0) of the Cartesian coordinate system (Chapter 8, Section 8.4, Page 299).
                • For any point P(x, y), its position vector is OP⃗ = ⟨x, y⟩ (Chapter 8, Section 8.4, Page 299).
                • Distance and midpoint formulas derived using position vectors between points P1(x1, y1) and P2(x2, y2) (Chapter 8, Section 8.4, Pages 300–302).
                """.trimIndent()
            )

            addNote(
                "Unit 8 - Vectors in Two Dimensions",
                "Applications of Vectors in Two Dimensions (Section 8.5)",
                """
                • Vectors are applied to model physical phenomena involving navigation, wind velocity, resultant forces, and relative motion (Chapter 8, Section 8.5, Pages 303–306).
                • Worked Example: Calculating the resultant velocity of a boat crossing a river with a flowing current using vector addition (Chapter 8, Section 8.5, Pages 304–305).
                """.trimIndent()
            )

            // Unit 9
            addNote(
                "Unit 9 - Statistics and Probability",
                "Statistical Data (Section 9.1)",
                """
                • Statistics involves the collection, organization, presentation, analysis, and interpretation of numerical data (Chapter 9, Section 9.1, Page 313).
                • Data organization: Frequency distribution tables, grouped and ungrouped data, class intervals, class boundaries, and cumulative frequency (Chapter 9, Section 9.1, Pages 314–325).
                • Graphical representation: Bar graphs, histograms, frequency polygons, pie charts, and cumulative frequency curves (ogives) (Chapter 9, Section 9.1, Pages 326–335).
                • Measures of Central Tendency:
                  - Mean (x̄): Sum of values divided by number of values (Σx / n or Σ(fi·xi) / Σfi for grouped data) (Chapter 9, Section 9.1, Pages 336–340).
                  - Median: Middle value when data is ordered.
                  - Mode: Most frequently occurring value (Chapter 9, Section 9.1, Pages 341–345).
                """.trimIndent()
            )

            addNote(
                "Unit 9 - Statistics and Probability",
                "Probability (Section 9.2)",
                """
                • Probability is the mathematical measure of the likelihood of an event occurring, ranging from 0 (impossible event) to 1 (certain event) (Chapter 9, Section 9.2, Page 346).
                • Basic terminology: Experiment, outcome, sample space (S), and event (E) (Chapter 9, Section 9.2, Pages 347–349).
                • Classical Probability Formula: P(E) = n(E) / n(S), where n(E) is the number of favorable outcomes and n(S) is the total number of equally likely outcomes in the sample space (Chapter 9, Section 9.2, Definition 9.1, Page 350).
                • Probability rules: 0 ≤ P(E) ≤ 1, P(S) = 1, P(∅) = 0, and the complement rule P(E') = 1 - P(E) (Chapter 9, Section 9.2, Pages 351–353).
                • Addition rule of probability: P(A ∪ B) = P(A) + P(B) - P(A ∩ B) (Chapter 9, Section 9.2, Pages 354–357).
                • Worked Example: Calculating the probability of drawing specific cards or rolling dice combinations (Chapter 9, Section 9.2, Pages 352–356).
                """.trimIndent()
            )
        }

        return notesList
    }
}
