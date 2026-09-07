package com.example.ui.tools.ai

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Locale

class TamheroSmartEngine : AiProvider {

    override val providerName: String = "Tamhero Smart Engine (Offline Fast Mode)"
    override val isLocalAi: Boolean = false

    // Lightweight conversation memory to handle smart follow-ups ("Give me an example", "Make it simpler")
    private var lastSubject: String = "Geography"
    private var lastTopic: String = "Map Reading & Fundamentals of Geography"
    private var lastConcept: String = "Map Scale"

    override suspend fun ask(prompt: String, context: StudentContext): String = withContext(Dispatchers.Default) {
        val lower = prompt.lowercase(Locale.ROOT).trim()
        val lContext = context.learningContext

        // 1. Establish context hierarchy
        val selectedSnippet = lContext?.selectedText?.trim()?.ifBlank { null }
        val noteContent = lContext?.contentText?.trim()?.ifBlank { null } ?: context.activeNotesSnippet.trim().ifBlank { null }
        val questionText = lContext?.question?.trim()?.ifBlank { null }
        val flashcardFront = lContext?.flashcardFront?.trim()?.ifBlank { null }

        // Dynamic course and topic
        val rawSubject = context.subject.trim().ifBlank { lContext?.courseName?.trim()?.ifBlank { null } ?: lastSubject }
        val rawTopic = context.currentTopic.trim().ifBlank { lContext?.topicName?.trim()?.ifBlank { null } ?: lastTopic }

        // Update conversation state
        lastSubject = rawSubject
        if (rawTopic.isNotBlank() && !rawTopic.equals("General", ignoreCase = true)) {
            lastTopic = rawTopic
        }

        val subjectNorm = rawSubject.lowercase(Locale.ROOT)
        val topicNorm = rawTopic.lowercase(Locale.ROOT)

        // 2. Online Gemini AI attempt with rich learning context
        try {
            val onlinePrompt = buildString {
                appendLine("Subject: $rawSubject")
                if (rawTopic.isNotBlank() && rawTopic != "General") appendLine("Topic / Unit: $rawTopic")
                if (selectedSnippet != null) appendLine("Student Selected Passage:\n$selectedSnippet")
                if (questionText != null) appendLine("Active Practice Question:\n$questionText")
                if (flashcardFront != null) appendLine("Active Flashcard Front:\n$flashcardFront")
                if (noteContent != null && selectedSnippet == null) appendLine("Current Study Material Snippet:\n$noteContent")
                appendLine("\nStudent Question / Command: $prompt")
            }
            val sysInstruction = "You are Tamhero, an elite academic tutor and university exam mentor. Answer directly, clearly, and encourage the student. Use clean formatting with key formulas, bullet points, and exam strategies where appropriate."
            val onlineResponse = com.example.ui.api.GeminiHttpClient.generateText(onlinePrompt, sysInstruction)
            if (!onlineResponse.isNullOrBlank()) {
                lastConcept = prompt.take(60)
                return@withContext onlineResponse
            }
        } catch (_: Throwable) {
            // Gracefully fall back to local curriculum knowledge base
        }

        // 3. Local Heuristic Engine (Offline Fast Mode)

        // --- Priority 1: User selected text from the active page ---
        if (selectedSnippet != null && (lower.contains("explain") || lower.contains("what is") || lower.contains("this") || lower.contains("mean") || lower.contains("simpler") || lower.contains("example"))) {
            lastConcept = selectedSnippet.take(60)
            return@withContext explainSelectedSnippet(selectedSnippet, rawSubject, rawTopic, lower)
        }

        // --- Priority 1.5: Active scanned document ---
        val isScannedDoc = lContext?.contentType == "scanned_doc"
        if (isScannedDoc && noteContent != null && (lower.contains("explain") || lower.contains("page") || lower.contains("scan") || lower.contains("document") || lower.contains("mean") || lower.contains("takeaway") || lower.contains("solve"))) {
            lastConcept = (lContext.topicName.ifBlank { rawTopic }).take(60)
            return@withContext explainScannedDocument(noteContent, rawSubject, lContext.topicName.ifBlank { rawTopic }, lower)
        }

        // --- Priority 2: Active practice question (Hint or Step-by-step breakdown) ---
        if (questionText != null && (lower.contains("hint") || lower.contains("why") || lower.contains("explain") || lower.contains("answer") || lower.contains("step"))) {
            return@withContext explainPracticeQuestion(questionText, lContext, rawSubject, lower)
        }

        // --- Priority 3: Active flashcard ---
        if (flashcardFront != null && (lower.contains("card") || lower.contains("flashcard") || lower.contains("remember") || lower.contains("example") || lower.contains("explain") || lower.contains("trick"))) {
            return@withContext explainFlashcard(flashcardFront, lContext?.flashcardBack ?: "", rawSubject, lower)
        }

        // --- Priority 4: Direct Follow-ups ("Give me an example", "Make it simpler", "Test me", "Summarize") ---
        val isExampleRequest = lower.startsWith("give me an example") || lower.contains("an example") || lower == "example" || lower.contains("show an example")
        val isSimplerRequest = lower.contains("simpler") || lower.contains("easier") || lower.contains("simple") || lower.contains("explain simply")
        val isSummaryRequest = lower.contains("summar") || lower.contains("key takeaway") || lower.contains("bullet") || lower.contains("overview")
        val isExamTipRequest = lower.contains("exam tip") || lower.contains("what should i remember") || lower.contains("important part") || lower.contains("remember for the exam")
        val isTestMeRequest = lower.contains("test me") || lower.contains("mcq") || lower.contains("practice question") || lower.contains("create questions") || lower.contains("quiz me")
        val isFlashcardGenRequest = lower.contains("turn this into flashcards") || lower.contains("create flashcards") || lower.contains("make flashcards")

        if (isExampleRequest) {
            return@withContext generateTargetedExample(rawSubject, rawTopic, lastConcept)
        }

        if (isSimplerRequest) {
            return@withContext generateSimplerExplanation(rawSubject, rawTopic, lastConcept)
        }

        if (isSummaryRequest && noteContent != null) {
            return@withContext summarize(noteContent)
        }

        if (isExamTipRequest) {
            return@withContext generateExamTip(rawSubject, rawTopic, lastConcept)
        }

        if (isTestMeRequest) {
            return@withContext generateTopicMCQs(rawSubject, rawTopic)
        }

        if (isFlashcardGenRequest) {
            return@withContext generateTopicFlashcards(rawSubject, rawTopic)
        }

        // --- Priority 5: Specific conceptual questions (Short, focused answers) ---
        val specificAnswer = checkSpecificConceptFactual(lower, rawSubject)
        if (specificAnswer != null) {
            return@withContext specificAnswer
        }

        // --- Try Online Gemini with graceful offline fallback ---
        try {
            val onlinePrompt = buildString {
                appendLine("Subject: $rawSubject")
                if (rawTopic.isNotBlank() && rawTopic != "General") appendLine("Topic: $rawTopic")
                if (noteContent != null) appendLine("Active Material Context:\n$noteContent")
                appendLine("Student Query: $prompt")
            }
            val sysInstruction = "You are Tamhero, an expert, encouraging, and structured academic study assistant. Provide accurate, clear, and high-yield academic explanations with key exam takeaways."
            val onlineResponse = com.example.ui.api.GeminiHttpClient.generateText(onlinePrompt, sysInstruction)
            if (!onlineResponse.isNullOrBlank()) {
                return@withContext onlineResponse
            }
        } catch (_: Throwable) {
            // Graceful transparent fallback to local smart engine
        }

        // --- Priority 6: Active lesson / topic explanation ("Explain this", "Explain this concept") ---
        if (lower.contains("explain") || lower.contains("teach") || lower.contains("break down") || lower.contains("concept") || lower.contains("this")) {
            return@withContext explainCurriculumTopic(rawSubject, rawTopic, noteContent)
        }

        // --- Priority 7: General Course or Topic Fallback ---
        return@withContext explainCurriculumTopic(rawSubject, rawTopic, noteContent)
    }

    
    private fun generateDynamicSubjectTip(subject: String, content: String? = null): String {
        val lowerSub = subject.lowercase(java.util.Locale.ROOT)
        val lowerContent = content?.lowercase(java.util.Locale.ROOT) ?: ""
        
        return when {
            lowerSub.contains("hist") || lowerContent.contains("century") || lowerContent.contains("war") -> 
                "Focus on the timeline of events, primary causes, and decisive leadership actions."
            lowerSub.contains("geo") || lowerContent.contains("climate") || lowerContent.contains("earth") -> 
                "Remember spatial relationships: always link geographical features to their economic or environmental impacts."
            lowerSub.contains("math") || lowerContent.contains("equation") || lowerContent.contains("theorem") -> 
                "Double-check your signs and always write out each step. If stuck, try working backward from the answer."
            lowerSub.contains("econ") || lowerContent.contains("market") || lowerContent.contains("supply") -> 
                "Remember the distinction between a 'shift in' the curve vs a 'movement along' the curve."
            lowerSub.contains("eng") || lowerContent.contains("grammar") || lowerContent.contains("tense") -> 
                "Pay close attention to tense transformations and subject-verb agreement exceptions."
            lowerSub.contains("bio") || lowerContent.contains("cell") || lowerContent.contains("protein") -> 
                "Structure determines function. Always connect the anatomical structure to its biological purpose."
            lowerSub.contains("phys") || lowerContent.contains("force") || lowerContent.contains("energy") -> 
                "Always write down your knowns and unknowns first, and verify your units match before calculating."
            lowerSub.contains("chem") || lowerContent.contains("reaction") || lowerContent.contains("acid") -> 
                "Ensure chemical equations are balanced and remember your periodic table trends (electronegativity, radius)."
            lowerSub.contains("account") || lowerContent.contains("balance") || lowerContent.contains("asset") -> 
                "Assets = Liabilities + Equity. Always ensure every transaction has matching debit and credit entries."
            lowerSub.contains("law") || lowerContent.contains("article") || lowerContent.contains("court") -> 
                "Memorize the precedent cases and the specific constitutional articles that apply to the scenario."
            else -> "Break down the core concept into 3 bullet points. Try teaching it out loud to verify you understand it."
        }
    }

    private fun explainScannedDocument(
        docText: String,
        subject: String,
        title: String,
        promptLower: String
    ): String {
        return buildString {
            appendLine("📄 **Scanned Page: ${title.ifBlank { subject }}**")
            appendLine()
            appendLine("Here is an intuitive, structured breakdown of this page:")
            appendLine()

            val lines = docText.lines().map { it.trim() }.filter { it.isNotBlank() }
            val numberedItems = lines.filter { line ->
                line.startsWith("1.") || line.startsWith("2.") || line.startsWith("3.") || line.startsWith("4.") || line.startsWith("-")
            }

            if (numberedItems.isNotEmpty()) {
                appendLine("### 🔑 Key Concepts on this Page:")
                numberedItems.take(4).forEach { item ->
                    appendLine("• $item")
                }
                appendLine()
            }

            appendLine("### 💡 High-Yield Exam Takeaway for $subject:")
            appendLine("• " + generateDynamicSubjectTip(subject, docText))
            appendLine()
            appendLine("✨ *Tip:* You can ask me: *'Give me an example'*, *'Make it simpler'*, or tap **'Cards'** or **'MCQs'** to generate practice assets directly from this page!")
        }
    }

    private fun explainSelectedSnippet(
        snippet: String,
        subject: String,
        topic: String,
        promptLower: String
    ): String {
        return if (promptLower.contains("example")) {
            """
            > "$snippet"

            Here is a practical example illustrating this:
            In **$subject**, when applied directly to exam scenarios, consider a case where this condition is tested. For instance, if this rule sets a threshold or relationship, examiners will test whether increasing one variable causes the corresponding change described here.

            EXAM TIP: Highlight this phrase in your notes. When multiple choice questions ask about $topic, this exact wording often separates the correct answer from distractor options.
            """.trimIndent()
        } else {
            """
            > "$snippet"

            In **$subject** ($topic), this excerpt defines a foundational principle:

            • **Meaning**: It highlights the exact relationship between the primary variables or conditions being studied.
            • **Why It Matters**: This definition is a high-yield benchmark on national exams. Rather than testing obscure trivia, examiners test your ability to apply this exact relationship.
            • **Key Takeaway**: Ensure you understand both the direct effect and what happens when the conditions are reversed.

            EXAM TIP: Watch out for negative question stems (e.g., "Which of the following is NOT true regarding this principle?").
            """.trimIndent()
        }
    }

    private fun explainPracticeQuestion(
        questionText: String,
        lContext: LearningContext?,
        subject: String,
        promptLower: String
    ): String {
        val correct = lContext?.correctAnswer?.ifBlank { "the correct choice" } ?: "the correct choice"
        val explanation = lContext?.explanation?.ifBlank { "Based on standard national curriculum guidelines." } ?: "Based on standard national curriculum guidelines."

        return if (promptLower.contains("hint")) {
            """
            Here is a hint for this question:
            Look closely at the key conditions stated in the question: "$questionText".
            Eliminate choices that violate standard laws of $subject. Pay special attention to whether the problem implies a direct or inverse relationship!
            """.trimIndent()
        } else {
            """
            **Question Breakdown ($subject)**
            • **Question**: "$questionText"
            • **Correct Answer**: Option $correct
            • **Step-by-Step Explanation**:
              $explanation

            EXAM TIP: When approaching multi-step questions in $subject, always identify what is given and what is being asked before evaluating the answer choices.
            """.trimIndent()
        }
    }

    private fun explainFlashcard(
        front: String,
        back: String,
        subject: String,
        promptLower: String
    ): String {
        return """
        **Flashcard Concept: $front**

        • **Core Definition**: $back
        • **Real-World Application**: In $subject, $front is used to classify or compute outcomes in both theoretical and practical problems.
        • **Memory Trick**: Associate the initial letter or keyword of "$front" with the governing rule: $back.

        EXAM TIP: Questions testing this term frequently require you to distinguish it from closely related concepts in the same unit.
        """.trimIndent()
    }

    private fun generateTargetedExample(subject: String, topic: String, concept: String): String {
        val sub = subject.lowercase(Locale.ROOT)
        val top = topic.lowercase(Locale.ROOT)

        return when {
            sub.contains("geo") || top.contains("map") || top.contains("scale") -> {
                """
                Here is a practical map scale example:

                Suppose a topographic map has a scale of **1:50,000**, and the measured distance between two towns on the map is **6 cm**.

                1. **Calculate ground distance in centimeters**:
                   6 cm × 50,000 = 300,000 cm.
                2. **Convert to kilometers**:
                   300,000 cm ÷ 100,000 = **3 km**.

                The actual distance on the ground is **3 kilometers**.
                """.trimIndent()
            }
            sub.contains("econ") || top.contains("opportunity") || top.contains("scarcity") -> {
                """
                Here is a classic Opportunity Cost example:

                You have 100 Birr and 2 free hours. You can either:
                A) Buy a study guide and prepare for your national exam.
                B) Go to the cinema with friends.

                If you choose to buy the study guide, the **opportunity cost** is the enjoyment and leisure of the cinema movie you forgave.
                Opportunity cost is always the value of the **next best alternative**, not all alternatives combined.
                """.trimIndent()
            }
            sub.contains("math") || top.contains("derivative") || top.contains("calculus") -> {
                """
                Here is a step-by-step calculus derivative example:

                Find the derivative of f(x) = 4x³ - 5x² + 7x - 9:

                1. **Apply the Power Rule** [d/dx(xⁿ) = n·xⁿ⁻¹]:
                   • d/dx(4x³) = 4 · 3x² = 12x²
                   • d/dx(-5x²) = -5 · 2x¹ = -10x
                   • d/dx(7x) = 7 · 1x⁰ = 7
                   • d/dx(-9) = 0 (derivative of a constant is zero)
                2. **Combine terms**:
                   **f'(x) = 12x² - 10x + 7**.
                """.trimIndent()
            }
            sub.contains("phy") || top.contains("newton") || top.contains("force") -> {
                """
                Here is an example applying Newton's Second Law (F = m·a):

                A 1,200 kg car accelerates from rest at 2.5 m/s². What net force must the engine apply?

                1. State knowns: mass m = 1,200 kg, acceleration a = 2.5 m/s².
                2. Formula: F = m · a
                3. Calculate: F = 1,200 kg × 2.5 m/s² = **3,000 Newtons (N)**.
                """.trimIndent()
            }
            else -> {
                """
                Here is a concrete example for **$topic** in **$subject**:

                Suppose an exam question asks you to evaluate this principle under standard conditions.
                You identify the governing formula or rule, substitute the initial given values, and verify whether the outcome matches the expected theoretical direction.

                EXAM TIP: In exams, always verify whether units are in standard SI form before computing the final answer!
                """.trimIndent()
            }
        }
    }

    private fun generateSimplerExplanation(subject: String, topic: String, concept: String): String {
        val sub = subject.lowercase(Locale.ROOT)
        val top = topic.lowercase(Locale.ROOT)

        return when {
            sub.contains("geo") || top.contains("map") -> {
                """
                In simple terms:
                A map is just a shrunken bird's-eye drawing of reality.
                • **Scale** tells you how many times smaller the map is than the real world.
                • **Contour lines** show hills and valleys: close lines mean a steep climb, spaced lines mean flat ground.
                • **Latitude and Longitude** act like Earth's street address grid so you never get lost.
                """.trimIndent()
            }
            sub.contains("econ") -> {
                """
                In simple terms:
                • We want everything, but resources (money, time) are limited (**Scarcity**).
                • Because you can't have everything, you have to make a choice.
                • What you gave up to get what you chose is your **Opportunity Cost**.
                """.trimIndent()
            }
            sub.contains("phy") -> {
                """
                In simple terms:
                • Things keep doing what they are doing until you push or pull them (**Inertia**).
                • Heavy things need a bigger push to speed up (**F = m·a**).
                • If you push a wall, the wall pushes back on you with the exact same strength (**Action-Reaction**).
                """.trimIndent()
            }
            sub.contains("math") -> {
                """
                In simple terms:
                A derivative simply answers: **"How fast is something changing right now?"**
                Think of your speedometer in a car: it doesn't tell you your average speed for the whole trip; it tells you your exact speed at that single split-second.
                """.trimIndent()
            }
            else -> {
                """
                In simple terms:
                Think of **$topic** as the foundation rule for this unit in $subject.
                Instead of memorizing long paragraphs, focus on:
                1. What is the main cause?
                2. What is the main effect?
                3. What changes when the input increases or decreases?
                """.trimIndent()
            }
        }
    }

    private fun generateExamTip(subject: String, topic: String, concept: String): String {
        val sub = subject.lowercase(Locale.ROOT)
        val top = topic.lowercase(Locale.ROOT)

        return when {
            sub.contains("geo") || top.contains("map") -> {
                """
                EXAM TIP: Remember the inverse relationship for map scale:
                • **Large-scale map** (e.g., 1:5,000) shows a **smaller ground area** with **high detail** (like a town map).
                • **Small-scale map** (e.g., 1:1,000,000) shows a **large ground area** with **less detail** (like a world map).
                This distinction is tested on nearly every national geography exam!
                """.trimIndent()
            }
            sub.contains("econ") -> {
                """
                EXAM TIP: Be careful not to confuse a *change in quantity demanded* (movement along the demand curve caused only by price) with a *change in demand* (shift of the entire curve caused by income, taste, or related goods)!
                """.trimIndent()
            }
            sub.contains("phy") -> {
                """
                EXAM TIP: Action and reaction forces (Newton's 3rd Law) never cancel each other out because they act on **two different objects**, never on the same object!
                """.trimIndent()
            }
            sub.contains("math") -> {
                """
                EXAM TIP: When finding the roots of a quadratic equation ax² + bx + c = 0, first check the discriminant (b² - 4ac). If negative, there are no real roots. This saves you from tedious factorization!
                """.trimIndent()
            }
            sub.contains("hist") -> {
                """
                EXAM TIP: The primary diplomatic trigger of the 1896 Battle of Adwa was the discrepancy in **Article XVII** of the Treaty of Wuchale (1889). Italian text claimed an Italian protectorate, while the Amharic text made diplomatic mediation optional!
                """.trimIndent()
            }
            else -> {
                """
                EXAM TIP: For $topic, examiners love to test edge cases and definitions where common intuition differs from formal scientific rules. Always verify standard units before picking an answer!
                """.trimIndent()
            }
        }
    }

    private fun generateTopicMCQs(subject: String, topic: String): String {
        val sub = subject.lowercase(Locale.ROOT)

        return when {
            sub.contains("geo") -> {
                """
                Here are 2 high-yield exam practice questions for **$topic**:

                **Question 1**: A map with a scale of 1:25,000 is classified as which type of map scale?
                A) Small-scale map
                B) Medium to Large-scale map
                C) Inverted relief map
                D) Azimuthal projection
                *Correct Answer*: **B**
                *Explanation*: Scales larger than 1:50,000 are categorized as large scale because they display local features with high precision.

                ---

                **Question 2**: What do closely spaced contour lines on a topographic map indicate?
                A) A gentle plain
                B) A body of standing water
                C) A steep slope or cliff
                D) A valley ridge
                *Correct Answer*: **C**
                *Explanation*: When contour lines are packed tightly together, elevation increases rapidly over a short horizontal distance, indicating a steep gradient.
                """.trimIndent()
            }
            sub.contains("econ") -> {
                """
                Here are 2 high-yield exam practice questions for **$topic**:

                **Question 1**: Opportunity cost is strictly defined in economics as:
                A) The monetary price paid for a good.
                B) The value of the next best alternative sacrificed.
                C) The total cost of all rejected alternatives combined.
                D) The depreciation expense of fixed assets.
                *Correct Answer*: **B**

                ---

                **Question 2**: When consumer income rises and demand for a particular good decreases, that good is classified as:
                A) A normal good
                B) A luxury good
                C) An inferior good
                D) A complementary good
                *Correct Answer*: **C**
                """.trimIndent()
            }
            else -> {
                """
                Here are 2 high-yield exam practice questions for **$topic** in **$subject**:

                **Question 1**: Which statement accurately represents the primary law governing this concept?
                A) The effect is directly proportional to the applied cause under steady conditions.
                B) The principle applies only in non-isolated systems.
                C) All measured parameters remain zero at equilibrium.
                D) The relation does not depend on standardized units.
                *Correct Answer*: **A**

                ---

                **Question 2**: When solving multi-variable problems in this topic, what is the best first step?
                A) Estimate without checking units.
                B) List known parameters, identify the unknown, and apply the standard equation.
                C) Combine unrelated formulas.
                D) Disregard initial conditions.
                *Correct Answer*: **B**
                """.trimIndent()
            }
        }
    }

    private fun generateTopicFlashcards(subject: String, topic: String): String {
        return """
        Here are high-yield flashcards for **$topic** in **$subject**:

        • **Card 1 (Core Term)**
          Front: What is the primary definition of $topic?
          Back: The foundational curriculum principle explaining how variables interact and operate in $subject.

        • **Card 2 (Formula / Mechanism)**
          Front: What is the governing rule or equation in $topic?
          Back: State the primary relation, identify all variables, and verify that appropriate standard units are applied.

        • **Card 3 (Exam Trap)**
          Front: What common mistake do students make when solving $topic problems?
          Back: Confusing inverse relations with direct relations and forgetting to check edge cases.
        """.trimIndent()
    }

    private fun checkSpecificConceptFactual(lower: String, subject: String): String? {
        return when {
            lower == "what is latitude?" || lower == "what is latitude" -> {
                "**Latitude** is the angular distance of a location on Earth measured north or south of the Equator (0°). Lines of latitude run horizontally as parallel circles from 0° at the Equator up to 90° North at the North Pole and 90° South at the South Pole."
            }
            lower == "what is longitude?" || lower == "what is longitude" -> {
                "**Longitude** is the angular distance of a place east or west of the Prime Meridian (0° at Greenwich, England), measured in degrees up to 180° East and 180° West. Lines of longitude (meridians) run vertically from the North Pole to the South Pole."
            }
            lower == "what is opportunity cost?" || lower == "what is opportunity cost" -> {
                "**Opportunity cost** is the value of the next best alternative that is given up when a choice is made. For example, if you spend time studying for an exam instead of sleeping, the opportunity cost is the rest you sacrificed."
            }
            lower == "what is map scale?" || lower == "what is map scale" -> {
                "**Map scale** is the ratio between a distance measured on a map and the actual corresponding distance on the Earth's surface. It can be expressed as a representative fraction (e.g., 1:50,000), a graphic bar scale, or a verbal statement."
            }
            lower == "what is a contour line?" || lower == "what is a contour line" -> {
                "A **contour line** is an imaginary line on a topographic map that joins points of equal elevation above sea level. Closely spaced lines indicate steep slopes, while widely spaced lines indicate gentle or flat terrain."
            }
            lower == "who are you" || lower.contains("what can you do") -> {
                "I am **Ask Tamhero**, your intelligent offline study companion. I can explain formulas, unpack complex theories, summarize your lesson notes, solve practice problems step-by-step, and create flashcards."
            }
            else -> null
        }
    }

    private fun explainCurriculumTopic(subject: String, topic: String, noteContent: String?): String {
        val sub = subject.lowercase(Locale.ROOT)
        val top = topic.lowercase(Locale.ROOT)

        return when {
            // GEOGRAPHY: Map Reading & Fundamentals
            sub.contains("geo") && (top.contains("map") || top.contains("reading") || top.contains("fundamental")) -> {
                lastConcept = "Map Reading"
                """
                In this lesson on **Map Reading & Fundamentals of Geography**, the central goal is understanding how flat maps represent real-world landscapes.

                • **Map Scale**: The ratio between distance on the map and distance on the ground. A scale of 1:50,000 means 1 cm on the map equals 50,000 cm (500 m) in reality.
                • **Contour Lines**: Lines connecting points of equal elevation. Closely packed contour lines show steep terrain or escarpments; widely spaced lines show gentle slopes or plains.
                • **Grid Coordinates**: Latitude lines run east-west (measuring north-south from the Equator), while longitude lines run north-south (measuring east-west from the Prime Meridian).

                EXAM TIP: Remember that large-scale maps (e.g. 1:10,000) cover a small area with high detail, whereas small-scale maps (e.g. 1:1,000,000) cover a large area with less detail.
                """.trimIndent()
            }

            // GEOGRAPHY: Geological Structure & Topography of Ethiopia
            sub.contains("geo") && (top.contains("geolog") || top.contains("topography") || top.contains("rift")) -> {
                lastConcept = "Rift Valley Topography"
                """
                In this lesson on **Geological Structure & Topography of Ethiopia**, the key concept is how tectonic forces shaped the Ethiopian landscape:

                • **The Great Rift Valley**: Formed by tectonic plate divergence, dividing the country into the Northwestern Highlands and the Southeastern Highlands.
                • **Highlands vs. Lowlands**: Over 56% of Ethiopia's landmass lies above 1,000 meters elevation, giving it moderate climates and fertile volcanic soils.
                • **Danakil / Afar Depression**: One of the lowest and hottest places on Earth, lying below sea level with active volcanic activity (Erta Ale).

                EXAM TIP: Mount Ras Dejen (4,533 m) in the Simien Mountains is Ethiopia's highest peak, located in the Northwestern Highlands block.
                """.trimIndent()
            }

            // GEOGRAPHY: Climate Zones & Drainage Systems
            sub.contains("geo") && (top.contains("climate") || top.contains("drainage") || top.contains("river")) -> {
                lastConcept = "Climate & Drainage"
                """
                In this lesson on **Climate Zones & Drainage Systems of Ethiopia**:

                • **Traditional Agro-Climatic Zones**:
                  - *Berha*: Hot arid desert (< 500 m altitude).
                  - *Kolla*: Warm semi-arid (500 - 1,500 m).
                  - *Weyna Dega*: Moderate warm temperate (1,500 - 2,300 m) — supports most agriculture and population.
                  - *Dega*: Cool temperate (2,300 - 3,200 m).
                  - *Wurch*: Alpine cold (> 3,200 m).
                • **Major Drainage Basins**:
                  - *Western Basin*: Abay (Blue Nile), Baro-Akobo, Tekeze — drains westward to the Nile.
                  - *Southeastern Basin*: Wabi Shebelle and Genale-Dawa.
                  - *Rift Valley Basin*: Awash River (internal drainage, terminates in Lake Abbe).

                EXAM TIP: The Abay Basin contributes over 60% of the total annual water discharge of all Ethiopian river systems.
                """.trimIndent()
            }

            // ECONOMICS: Basic Concepts & Scarcity
            sub.contains("econ") && (top.contains("scarcity") || top.contains("basic") || top.contains("opportunity")) -> {
                lastConcept = "Scarcity & Opportunity Cost"
                """
                In this lesson on **Basic Concepts of Economics & Scarcity**:

                • **The Fundamental Economic Problem**: Unlimited human wants vs. limited economic resources (land, labor, capital, entrepreneurship).
                • **Opportunity Cost**: The value of the next best alternative forgone when making a decision. Every choice carries an opportunity cost.
                • **Production Possibility Curve (PPC)**: A graphical representation of the maximum combinations of two goods an economy can produce given current resources and technology. Points inside the curve are inefficient; points on the curve are efficient; points outside are unattainable.

                EXAM TIP: A bowed-out (concave) PPC reflects the Law of Increasing Opportunity Cost — as you produce more of one good, you must give up increasingly larger amounts of the other.
                """.trimIndent()
            }

            // ECONOMICS: Demand & Supply
            sub.contains("econ") && (top.contains("demand") || top.contains("supply") || top.contains("market")) -> {
                lastConcept = "Demand & Supply"
                """
                In this lesson on **Demand, Supply & Market Equilibrium**:

                • **Law of Demand**: As price increases, quantity demanded decreases (inverse relationship).
                • **Law of Supply**: As price increases, quantity supplied increases (direct relationship).
                • **Market Equilibrium**: The price point where quantity demanded equals quantity supplied (Qd = Qs).
                • **Shortage vs. Surplus**: If price is below equilibrium, demand exceeds supply (shortage); if price is above equilibrium, supply exceeds demand (surplus).

                EXAM TIP: A change in price causes movement along the curve (change in quantity demanded). A change in consumer income or tastes shifts the entire curve (change in demand).
                """.trimIndent()
            }

            // HISTORY: Battle of Adwa
            sub.contains("hist") && (top.contains("adwa") || top.contains("menelik") || top.contains("wuchale")) -> {
                lastConcept = "Battle of Adwa"
                """
                In this lesson on **The Battle of Adwa (1896)**:

                • **Date**: March 1, 1896.
                • **Immediate Cause**: Article XVII of the 1889 Treaty of Wuchale. Italy claimed Ethiopia was an Italian protectorate, while the Amharic version allowed optional foreign mediation.
                • **Leadership**: Emperor Menelik II and Empress Taytu Betul mobilized over 100,000 soldiers from across all Ethiopian regions.
                • **Historic Outcome**: Complete Ethiopian victory, securing national sovereignty and becoming a landmark symbol for anti-colonial independence across Africa.

                EXAM TIP: The Treaty of Addis Ababa (October 1896) formally abrogated the Treaty of Wuchale and forced Italy to recognize Ethiopia's unconditional independence.
                """.trimIndent()
            }

            // HISTORY: Aksum & Zagwe
            sub.contains("hist") && (top.contains("aksum") || top.contains("zagwe") || top.contains("ezana")) -> {
                lastConcept = "Aksum & Zagwe"
                """
                In this lesson on **The Kingdom of Aksum & Zagwe Dynasty**:

                • **Kingdom of Aksum**: Major maritime trade civilization linking the Roman Empire, India, and Arabia via the Red Sea port of Adulis. Minted its own currency and erected monumental granite stelae.
                • **King Ezana (c. 330 AD)**: Adopted Christianity as the official state religion, replacing pagan symbols on coins with the Christian cross.
                • **Zagwe Dynasty**: Centered in Lalibela (Roha), famous for carving 11 monolithic rock-hewn churches during King Lalibela's reign.

                EXAM TIP: Aksum was one of only four civilizations in the ancient world (alongside Rome, Persia, and Kushan) to mint gold, silver, and bronze coinage.
                """.trimIndent()
            }

            // MATHEMATICS: Calculus & Derivatives
            sub.contains("math") && (top.contains("limit") || top.contains("derivative") || top.contains("calculus")) -> {
                lastConcept = "Calculus Derivatives"
                """
                In this lesson on **Limits, Derivatives & Calculus**:

                • **Definition of Derivative**: The instantaneous rate of change of a function, representing the slope of the tangent line to the curve at that point.
                • **Power Rule**: d/dx(xⁿ) = n · xⁿ⁻¹. (e.g., d/dx(x⁴) = 4x³).
                • **Product Rule**: (u · v)' = u'v + uv'.
                • **Chain Rule**: d/dx[f(g(x))] = f'(g(x)) · g'(x).

                EXAM TIP: Critical points occur where f'(x) = 0 or where f'(x) is undefined. Use the second derivative f''(x) to test for local maximums (f'' < 0) and minimums (f'' > 0).
                """.trimIndent()
            }

            // MATHEMATICS: Quadratic Equations
            sub.contains("math") && (top.contains("quadrat") || top.contains("trig")) -> {
                lastConcept = "Quadratic Equations"
                """
                In this lesson on **Quadratic Equations & Trigonometry**:

                • **Standard Form**: ax² + bx + c = 0.
                • **Quadratic Formula**: x = [-b ± √(b² - 4ac)] / (2a).
                • **Discriminant (Δ = b² - 4ac)**:
                  - Δ > 0: Two distinct real roots.
                  - Δ = 0: Exactly one real repeated root.
                  - Δ < 0: Two complex roots (no real solution).
                • **Key Trig Identity**: sin²(θ) + cos²(θ) = 1.

                EXAM TIP: Vieta's formulas state that the sum of roots is -b/a, and the product of roots is c/a.
                """.trimIndent()
            }

            // PHYSICS: Newton's Laws & Force
            sub.contains("phy") && (top.contains("motion") || top.contains("newton") || top.contains("force")) -> {
                lastConcept = "Newton's Laws of Motion"
                """
                In this lesson on **Newton's Laws of Motion**:

                1. **First Law (Inertia)**: An object remains at rest or in uniform motion in a straight line unless acted upon by a net external force.
                2. **Second Law (Acceleration)**: F_net = m · a (acceleration is directly proportional to net force and inversely proportional to mass).
                3. **Third Law (Action-Reaction)**: For every action, there is an equal and opposite reaction force.

                EXAM TIP: Action and reaction forces act on different objects, which is why they never cancel each other out!
                """.trimIndent()
            }

            // GENERAL LESSON NOTE FALLBACK
            noteContent != null && noteContent.isNotBlank() -> {
                val preview = noteContent.take(280)
                """
                In this lesson on **$topic** ($subject):

                • **Overview**: $preview
                • **Key Takeaways**:
                  1. Understand the foundational definitions and boundary conditions.
                  2. Focus on the core relationships between the primary variables.
                  3. Practice applying this theory to sample multiple-choice questions.

                EXAM TIP: Review the exact terminology used in this unit, as national exam questions frequently test definitions directly.
                """.trimIndent()
            }

            else -> {
                """
                In **$subject** ($topic):

                • **Core Focus**: Master the key definitions, theoretical principles, and standard equations.
                • **Strategy**: Connect the concepts to real-world applications and test yourself using active recall.

                EXAM TIP: Examiners emphasize the practical implications of $topic on exam day.
                """.trimIndent()
            }
        }
    }

    override suspend fun explainConcept(concept: String, subject: String): String = withContext(Dispatchers.Default) {
        ask(
            prompt = "Explain $concept in $subject simply and step by step",
            context = StudentContext(subject = subject, currentTopic = concept)
        )
    }

    override suspend fun summarize(text: String): String = withContext(Dispatchers.Default) {
        if (text.isBlank()) return@withContext "No text provided to summarize."

        val sentences = text.split(Regex("[.!?\\n]+")).map { it.trim() }.filter { it.length > 15 }
        if (sentences.isEmpty()) {
            return@withContext "Summary: " + text.take(200)
        }

        val topSentences = sentences.take(4)
        buildString {
            appendLine("**High-Yield Lesson Summary**")
            appendLine("• **Core Takeaway**: ${topSentences.firstOrNull() ?: "Foundational concept."}")
            topSentences.drop(1).forEach { sentence ->
                appendLine("• $sentence.")
            }
            appendLine("")
            appendLine("EXAM TIP: Focus on active recall and practice questions rather than passive re-reading.")
        }
    }

    override suspend fun generateFlashcards(text: String, count: Int): List<GeneratedFlashcard> = withContext(Dispatchers.Default) {
        val cards = mutableListOf<GeneratedFlashcard>()
        val lines = text.split(Regex("[\\n.]+")).map { it.trim() }.filter { it.length > 10 }

        for (line in lines) {
            when {
                line.contains(" is ") -> {
                    val parts = line.split(" is ", limit = 2)
                    if (parts.size == 2 && parts[0].length < 40 && parts[1].length > 5) {
                        cards.add(GeneratedFlashcard(front = "What is ${parts[0].trim()}?", back = parts[1].trim()))
                    }
                }
                line.contains(":") -> {
                    val parts = line.split(":", limit = 2)
                    if (parts.size == 2 && parts[0].length < 40 && parts[1].length > 5) {
                        cards.add(GeneratedFlashcard(front = parts[0].trim(), back = parts[1].trim()))
                    }
                }
                line.contains(" = ") -> {
                    val parts = line.split(" = ", limit = 2)
                    if (parts.size == 2 && parts[0].length < 40) {
                        cards.add(GeneratedFlashcard(front = "Formula for ${parts[0].trim()}", back = parts[1].trim()))
                    }
                }
            }
            if (cards.size >= count) break
        }

        if (cards.isEmpty()) {
            cards.add(GeneratedFlashcard(
                front = "Key Concept: Core Definition",
                back = text.take(120).trim() + "..."
            ))
            cards.add(GeneratedFlashcard(
                front = "How should this topic be analyzed?",
                back = "Identify known variables, apply theoretical formulas, and verify units."
            ))
        }

        cards.take(count)
    }

    override suspend fun generateQuestions(text: String, count: Int): List<GeneratedQuestion> = withContext(Dispatchers.Default) {
        val sampleQuestions = listOf(
            GeneratedQuestion(
                questionText = "Which of the following best summarizes the primary concept discussed in this lesson material?",
                optionA = "A fundamental principle establishing relations between variables and measured quantities.",
                optionB = "A historical hypothesis that has since been completely superseded.",
                optionC = "A constant value that never varies under any physical or chemical condition.",
                optionD = "An arbitrary classification with no predictive utility.",
                correctOption = "A",
                explanation = "The core principle defines relationship models and quantitative equations used to predict and evaluate outcomes."
            ),
            GeneratedQuestion(
                questionText = "When evaluating problems in this domain, what is the recommended first procedural step?",
                optionA = "Guess the final value from intuition alone.",
                optionB = "State given values, identify the required unknown, and apply standard formulas.",
                optionC = "Skip checking unit consistency.",
                optionD = "Combine conflicting equations without balancing.",
                correctOption = "B",
                explanation = "Systematic analysis requires identifying parameters, checking standard units, and applying validated equations."
            ),
            GeneratedQuestion(
                questionText = "How does this conceptual model apply to real-world educational evaluations and exam questions?",
                optionA = "It appears solely as an optional historical note.",
                optionB = "It forms the theoretical basis for multi-step analytical and calculation problems.",
                optionC = "It is restricted exclusively to laboratory simulations.",
                optionD = "It has no connection to university entrance curriculum.",
                correctOption = "B",
                explanation = "Foundational theories are tested through quantitative exercises, graphical analysis, and direct definitions."
            )
        )
        sampleQuestions.take(count)
    }

    override suspend fun explainGrades(courseName: String, currentAvg: Double, target: Double, reqFinal: Double): String = withContext(Dispatchers.Default) {
        when {
            reqFinal <= 0 -> {
                "🎉 **Excellent Standing in $courseName!** Your current weighted average is **${String.format(Locale.US, "%.1f", currentAvg)}%**, which already surpasses your target of **${String.format(Locale.US, "%.1f", target)}%**. Focus on maintaining your consistent study habits!"
            }
            reqFinal in 1.0..100.0 -> {
                """
                📊 **Grade Strategy for $courseName**:
                • **Current Standing**: ${String.format(Locale.US, "%.1f", currentAvg)}% | **Goal**: ${String.format(Locale.US, "%.1f", target)}%
                • **Required Final Assessment Score**: **${String.format(Locale.US, "%.1f", reqFinal)}%**
                • **Action Plan**: This target is very achievable! Allocate 2 dedicated weekly review blocks for $courseName, complete past practice questions, and focus on mastering high-weight topics before the final assessment.
                """.trimIndent()
            }
            else -> {
                """
                ⚠️ **Challenging Recovery in $courseName**:
                • **Current Standing**: ${String.format(Locale.US, "%.1f", currentAvg)}% | **Goal**: ${String.format(Locale.US, "%.1f", target)}%
                • To reach ${String.format(Locale.US, "%.1f", target)}%, the mathematical required score on the remaining portion exceeds 100%.
                • **Recommendation**: Consider adjusting your target to a strong achievable grade (such as ${String.format(Locale.US, "%.1f", (currentAvg + target) / 2)}%), talk to your instructor for extra credit opportunities, and prioritize upcoming high-weight assignments!
                """.trimIndent()
            }
        }
    }
}
