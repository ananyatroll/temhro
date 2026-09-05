package com.example.data

object Grade9EnglishNotes {

    fun getGrade9EnglishNotes(): List<SubjectNote> {
        val notesList = mutableListOf<SubjectNote>()
        val subjectIds = listOf("euee_nat_english", "euee_soc_english")

        subjectIds.forEach { subId ->
            var idx = 1

            fun addNote(unit: String, title: String, content: String) {
                notesList.add(
                    SubjectNote(
                        id = "g9_eng_note_${subId}_$idx",
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
                "Unit 1 - Living in Urban Areas",
                "Listening Skills (Section 1.1)",
                """
                • Focuses on developing listening comprehension regarding urban life versus rural life, enabling learners to listen to medium-level texts and transfer information (Chapter 1, Section 1.1, Pages 14–16).
                • Learners practice predicting listening text content based on pictorial cues and titles before listening (Chapter 1, Section 1.1, Page 15).
                • Rule/Method: Active listening involves noting key details, comparing rural and urban environments, and extracting main ideas from spoken passages (Chapter 1, Section 1.1, Pages 14–17).
                • Application: Understanding spoken English discourse on socio-economic topics relevant to modern Ethiopian contexts (Chapter 1, Section 1.1, Page 16).
                """.trimIndent()
            )

            addNote(
                "Unit 1 - Living in Urban Areas",
                "Reading Skills (Section 1.2)",
                """
                • Focuses on reading comprehension passages centered on life in big cities, infrastructure, and urban challenges (Chapter 1, Section 1.2, Pages 18–22).
                • Method: Skimming and scanning techniques are applied to locate specific information and understand paragraph main ideas (Chapter 1, Section 1.2, Pages 18–20).
                • Textbook Example: Reading texts about urban migration and city amenities followed by comprehension check questions (Chapter 1, Section 1.2, Pages 19–21).
                • Distinction: Differentiating between literal comprehension (surface understanding) and inferential comprehension (deeper evaluation of author intent) (Chapter 1, Section 1.2, Page 22).
                """.trimIndent()
            )

            addNote(
                "Unit 1 - Living in Urban Areas",
                "Vocabulary Development (Section 1.3)",
                """
                • Teaches vocabulary derived from listening and reading texts about urban environments, including contextual guessing strategies (Chapter 1, Section 1.3, Pages 23–25).
                • Rule/Method: Using structural clues (prefixes and suffixes) and contextual definitions to determine word meanings without relying solely on dictionaries (Chapter 1, Section 1.3, Pages 23–24).
                • Application: Expanding academic and communicative vocabulary related to urban living, infrastructure, and socio-economic terms (Chapter 1, Section 1.3, Page 25).
                """.trimIndent()
            )

            addNote(
                "Unit 1 - Living in Urban Areas",
                "Grammar (Section 1.4)",
                """
                • Focuses on selected grammatical items appropriate for Grade 9, including auxiliary verbs, question formation (Wh-questions and yes/no questions), and accurate intonation patterns (Chapter 1, Section 1.4, Pages 26–30).
                • Rule/Method: Formulating grammatically correct interrogative sentences with appropriate rising and falling intonation (Chapter 1, Section 1.4, Pages 26–28).
                • Textbook Example: Transforming statements into questions (e.g., auxiliary-beginning questions and Wh-questions) and practicing subject-verb agreement (Chapter 1, Section 1.4, Pages 28–29).
                • Application: Enhancing oral and written accuracy in everyday and academic communication (Chapter 1, Section 1.4, Page 30).
                """.trimIndent()
            )

            addNote(
                "Unit 1 - Living in Urban Areas",
                "Speaking Skills (Section 1.5)",
                """
                • Focuses on interactive communication, expressing preferences, and discussing urban versus rural lifestyles in pairs and small groups (Chapter 1, Section 1.5, Pages 31–33).
                • Rule/Method: Utilizing functional expressions to state opinions, give reasons (using "because"), and maintain conversational flow (Chapter 1, Section 1.5, Pages 31–32).
                • Application: Developing fluency and confidence in spoken English within structured classroom interactions (Chapter 1, Section 1.5, Page 33).
                """.trimIndent()
            )

            addNote(
                "Unit 1 - Living in Urban Areas",
                "Writing Skills (Section 1.6)",
                """
                • Focuses on sentence and paragraph-level writing, emphasizing punctuation, capitalization, and cohesive paragraph construction (Chapter 1, Section 1.6, Pages 34–36).
                • Rule/Method: Constructing well-organized paragraphs with a clear topic sentence, supporting details, and a concluding sentence (Chapter 1, Section 1.6, Pages 34–35).
                • Application: Writing descriptive and argumentative paragraphs comparing city and village life (Chapter 1, Section 1.6, Page 36).
                """.trimIndent()
            )

            // Unit 2
            addNote(
                "Unit 2 - Study Skills",
                "Listening Skills (Section 2.1)",
                """
                • Focuses on listening to texts about learning strategies, note-taking methods, and effective study habits (Chapter 2, Section 2.1, Pages 37–40).
                • Rule/Method: Listening for signpost words and structuring notes using headings and bullet points during academic lectures (Chapter 2, Section 2.1, Pages 38–39).
                • Application: Improving academic listening and information retention for school subjects (Chapter 2, Section 2.1, Page 40).
                """.trimIndent()
            )

            addNote(
                "Unit 2 - Study Skills",
                "Reading Skills (Section 2.2)",
                """
                • Focuses on reading strategies such as SQ3R (Survey, Question, Read, Recite, Review) and active reading techniques (Chapter 2, Section 2.2, Pages 41–46).
                • Rule/Method: Analyzing informational texts about time management, library usage, and exam preparation (Chapter 2, Section 2.2, Pages 42–45).
                • Distinction: Differentiating between passive reading and active, critical reading for comprehension (Chapter 2, Section 2.2, Page 46).
                """.trimIndent()
            )

            addNote(
                "Unit 2 - Study Skills",
                "Vocabulary Development (Section 2.3)",
                """
                • Focuses on academic vocabulary, dictionary usage (pronunciation symbols, parts of speech), and word roots (Chapter 2, Section 2.3, Pages 47–50).
                • Rule/Method: Identifying word formation patterns and prefixes/suffixes related to academic study and learning processes (Chapter 2, Section 2.3, Pages 48–49).
                • Application: Building a robust academic vocabulary toolkit (Chapter 2, Section 2.3, Page 50).
                """.trimIndent()
            )

            addNote(
                "Unit 2 - Study Skills",
                "Grammar (Section 2.4)",
                """
                • Focuses on tense review, modal verbs of advice and obligation (should, must, have to), and conditional sentences (Chapter 2, Section 2.4, Pages 51–56).
                • Rule/Method: Using modal verbs correctly to express necessity, advice, and recommendations regarding study habits (Chapter 2, Section 2.4, Pages 52–54).
                • Textbook Example: Writing sentences giving study advice to peers using "should" and "ought to" (Chapter 2, Section 2.4, Pages 55–56).
                """.trimIndent()
            )

            addNote(
                "Unit 2 - Study Skills",
                "Speaking Skills (Section 2.5)",
                """
                • Focuses on discussing study routines, asking for and giving academic advice, and presenting study plans orally (Chapter 2, Section 2.5, Pages 57–59).
                • Rule/Method: Utilizing polite functional expressions for suggestions and collaborative academic discussions (Chapter 2, Section 2.5, Pages 57–58).
                """.trimIndent()
            )

            addNote(
                "Unit 2 - Study Skills",
                "Writing Skills (Section 2.6)",
                """
                • Focuses on summarization, paraphrasing, and writing study timetables or reflective paragraphs on learning strategies (Chapter 2, Section 2.6, Pages 60–63).
                • Rule/Method: Condensing long paragraphs into concise summaries while retaining core meaning (Chapter 2, Section 2.6, Pages 60–62).
                """.trimIndent()
            )

            // Unit 3
            addNote(
                "Unit 3 - Traffic Accident",
                "Listening Skills (Section 3.1)",
                """
                • Focuses on listening comprehension regarding road safety, causes of traffic accidents, and preventive measures (Chapter 3, Section 3.1, Pages 64–67).
                • Rule/Method: Extracting factual information, statistics, and main arguments from safety announcements and reports (Chapter 3, Section 3.1, Pages 65–66).
                """.trimIndent()
            )

            addNote(
                "Unit 3 - Traffic Accident",
                "Reading Skills (Section 3.2)",
                """
                • Focuses on reading informational texts and narrative accounts about road accidents, traffic rules, and pedestrian safety (Chapter 3, Section 3.2, Pages 68–74).
                • Rule/Method: Identifying cause-and-effect relationships within expository texts (Chapter 3, Section 3.2, Pages 70–73).
                """.trimIndent()
            )

            addNote(
                "Unit 3 - Traffic Accident",
                "Vocabulary Development (Section 3.3)",
                """
                • Focuses on vocabulary related to transportation, driving, road signs, and emergency terminology (Chapter 3, Section 3.3, Pages 75–78).
                • Rule/Method: Matching technical terms with contextual definitions and using them in sentences (Chapter 3, Section 3.3, Pages 76–77).
                """.trimIndent()
            )

            addNote(
                "Unit 3 - Traffic Accident",
                "Grammar (Section 3.4)",
                """
                • Focuses on passive voice construction and past simple/past continuous tenses in descriptive and narrative contexts (Chapter 3, Section 3.4, Pages 79–85).
                • Rule/Method: Converting active voice sentences into passive voice (e.g., "The driver hit the car" → "The car was hit by the driver") especially when the agent is unknown or less important (Chapter 3, Section 3.4, Pages 80–83).
                """.trimIndent()
            )

            addNote(
                "Unit 3 - Traffic Accident",
                "Speaking Skills (Section 3.5)",
                """
                • Focuses on debating road safety measures, reporting accidents, and discussing preventative rules (Chapter 3, Section 3.5, Pages 86–88).
                • Rule/Method: Expressing concern, warning others, and offering recommendations orally (Chapter 3, Section 3.5, Pages 86–87).
                """.trimIndent()
            )

            addNote(
                "Unit 3 - Traffic Accident",
                "Writing Skills (Section 3.6)",
                """
                • Focuses on writing narrative reports of accidents, persuasive paragraphs on road safety, and letter writing (Chapter 3, Section 3.6, Pages 89–92).
                • Rule/Method: Applying chronological order and transition words (first, subsequently, finally) in narrative writing (Chapter 3, Section 3.6, Pages 90–91).
                """.trimIndent()
            )

            // Unit 4
            addNote(
                "Unit 4 - National Parks",
                "Listening Skills (Section 4.1)",
                """
                • Focuses on listening to descriptions of national parks in Ethiopia (such as Gambella, Simien Mountains, and Bale Mountains) and wildlife conservation (Chapter 4, Section 4.1, Pages 93–96).
                • Rule/Method: Identifying descriptive details, geographical features, and fauna/flora mentioned in spoken texts (Chapter 4, Section 4.1, Pages 94–95).
                """.trimIndent()
            )

            addNote(
                "Unit 4 - National Parks",
                "Reading Skills (Section 4.2)",
                """
                • Focuses on reading descriptive and informational passages about biodiversity, conservation efforts, and tourism (Chapter 4, Section 4.2, Pages 97–103).
                • Rule/Method: Analyzing descriptive text structures and identifying descriptive adjectives and spatial transitions (Chapter 4, Section 4.2, Pages 98–101).
                """.trimIndent()
            )

            addNote(
                "Unit 4 - National Parks",
                "Vocabulary Development (Section 4.3)",
                """
                • Focuses on vocabulary associated with wildlife, flora, fauna, geography, and ecotourism (Chapter 4, Section 4.3, Pages 104–107).
                • Rule/Method: Using contextual clues and semantic word maps to learn environment-related terminology (Chapter 4, Section 4.3, Pages 105–106).
                """.trimIndent()
            )

            addNote(
                "Unit 4 - National Parks",
                "Grammar (Section 4.4)",
                """
                • Focuses on comparative and superlative adjectives and adverbs for describing and contrasting natural wonders and wildlife (Chapter 4, Section 4.4, Pages 108–114).
                • Rule/Method: Forming comparatives and superlatives correctly (e.g., big → bigger → biggest; beautiful → more beautiful → most beautiful) (Chapter 4, Section 4.4, Pages 109–112).
                """.trimIndent()
            )

            addNote(
                "Unit 4 - National Parks",
                "Speaking Skills (Section 4.5)",
                """
                • Focuses on describing places, discussing conservation importance, and role-playing tourist-guide interactions (Chapter 4, Section 4.5, Pages 115–117).
                • Rule/Method: Using descriptive language and engaging listeners with vivid details (Chapter 4, Section 4.5, Pages 115–116).
                """.trimIndent()
            )

            addNote(
                "Unit 4 - National Parks",
                "Writing Skills (Section 4.6)",
                """
                • Focuses on writing descriptive essays and paragraphs about a chosen national park or natural attraction in Ethiopia (Chapter 4, Section 4.6, Pages 118–121).
                • Rule/Method: Structuring descriptive writing with spatial organization (e.g., from north to south, foreground to background) (Chapter 4, Section 4.6, Pages 119–120).
                """.trimIndent()
            )

            // Unit 5
            addNote(
                "Unit 5 - Horticulture",
                "Listening Skills (Section 5.1)",
                """
                • Focuses on listening to texts about gardening, plant cultivation, crop production, and agricultural practices (Chapter 5, Section 5.1, Pages 122–125).
                • Rule/Method: Extracting procedural instructions and factual agricultural information from spoken discourse (Chapter 5, Section 5.1, Pages 123–124).
                """.trimIndent()
            )

            addNote(
                "Unit 5 - Horticulture",
                "Reading Skills (Section 5.2)",
                """
                • Focuses on reading informational texts regarding the health and economic benefits of gardening and horticultural practices (Chapter 5, Section 5.2, Pages 126–131).
                • Rule/Method: Identifying main ideas, supporting details, and sequence of steps in instructional texts (Chapter 5, Section 5.2, Pages 127–130).
                """.trimIndent()
            )

            addNote(
                "Unit 5 - Horticulture",
                "Vocabulary Development (Section 5.3)",
                """
                • Focuses on agricultural and botanical vocabulary, tools, plant parts, and cultivation methods (Chapter 5, Section 5.3, Pages 132–135).
                • Rule/Method: Applying word roots and suffixes to build agricultural terminology (Chapter 5, Section 5.3, Pages 133–134).
                """.trimIndent()
            )

            addNote(
                "Unit 5 - Horticulture",
                "Grammar (Section 5.4)",
                """
                • Focuses on imperative sentences, instructional language, and present simple tense for general truths and routines (Chapter 5, Section 5.4, Pages 136–142).
                • Rule/Method: Constructing direct instructions and commands (e.g., "Water the plants daily") and expressing habitual actions (Chapter 5, Section 5.4, Pages 137–140).
                """.trimIndent()
            )

            addNote(
                "Unit 5 - Horticulture",
                "Speaking Skills (Section 5.5)",
                """
                • Focuses on giving and following instructions, discussing gardening projects, and sharing agricultural experiences (Chapter 5, Section 5.5, Pages 143–145).
                • Rule/Method: Using sequencing words (first, next, then, finally) when giving oral instructions (Chapter 5, Section 5.5, Pages 143–144).
                """.trimIndent()
            )

            addNote(
                "Unit 5 - Horticulture",
                "Writing Skills (Section 5.6)",
                """
                • Focuses on writing procedural texts (recipes, gardening guides) and instructional paragraphs (Chapter 5, Section 5.6, Pages 146–149).
                • Rule/Method: Organizing instructions in chronological order with clear imperative verbs (Chapter 5, Section 5.6, Pages 147–148).
                """.trimIndent()
            )

            // Unit 6
            addNote(
                "Unit 6 - Poverty and Development",
                "Listening Skills (Section 6.1)",
                """
                • Focuses on listening to discussions about poverty, economic challenges, community development, and poverty reduction strategies (Chapter 6, Section 6.1, Pages 150–153).
                • Rule/Method: Analyzing speaker viewpoints, identifying key socio-economic arguments, and summarizing spoken points (Chapter 6, Section 6.1, Pages 151–152).
                """.trimIndent()
            )

            addNote(
                "Unit 6 - Poverty and Development",
                "Reading Skills (Section 6.2)",
                """
                • Focuses on reading expository and analytical texts about the impact of poverty, self-reliance, and development initiatives (Chapter 6, Section 6.2, Pages 154–160).
                • Rule/Method: Evaluating arguments, distinguishing between fact and opinion, and drawing conclusions from expository texts (Chapter 6, Section 6.2, Pages 155–159).
                """.trimIndent()
            )

            addNote(
                "Unit 6 - Poverty and Development",
                "Vocabulary Development (Section 6.3)",
                """
                • Focuses on socio-economic vocabulary, financial terms, and abstract nouns related to development and poverty (Chapter 6, Section 6.3, Pages 161–164).
                • Rule/Method: Deriving noun forms from verbs and adjectives using suffixes (e.g., develop → development; poor → poverty) (Chapter 6, Section 6.3, Pages 162–163).
                """.trimIndent()
            )

            addNote(
                "Unit 6 - Poverty and Development",
                "Grammar (Section 6.4)",
                """
                • Focuses on modal verbs of possibility and probability (might, could, may) and expressing future intentions (Chapter 6, Section 6.4, Pages 165–171).
                • Rule/Method: Using modal auxiliaries to discuss hypothetical future developments and community projects (Chapter 6, Section 6.4, Pages 166–169).
                """.trimIndent()
            )

            addNote(
                "Unit 6 - Poverty and Development",
                "Speaking Skills (Section 6.5)",
                """
                • Focuses on discussing community development, brainstorming solutions to local economic problems, and debating poverty eradication (Chapter 6, Section 6.5, Pages 172–174).
                • Rule/Method: Constructing persuasive arguments and respectfully disagreeing in discussions (Chapter 6, Section 6.5, Pages 172–173).
                """.trimIndent()
            )

            addNote(
                "Unit 6 - Poverty and Development",
                "Writing Skills (Section 6.6)",
                """
                • Focuses on writing argumentative and cause-effect paragraphs and essays on development topics (Chapter 6, Section 6.6, Pages 175–178).
                • Rule/Method: Presenting a clear thesis statement supported by logical arguments and evidence (Chapter 6, Section 6.6, Pages 176–177).
                """.trimIndent()
            )

            // Unit 7
            addNote(
                "Unit 7 - Community Services",
                "Listening Skills (Section 7.1)",
                """
                • Focuses on listening to accounts of voluntary community services, charity work, and civic engagement (Chapter 7, Section 7.1, Pages 179–182).
                • Rule/Method: Identifying specific details of community projects and understanding speaker motivations (Chapter 7, Section 7.1, Pages 180–181).
                """.trimIndent()
            )

            addNote(
                "Unit 7 - Community Services",
                "Reading Skills (Section 7.2)",
                """
                • Focuses on reading texts about civic responsibilities, voluntary organizations, and community development programs (Chapter 7, Section 7.2, Pages 183–189).
                • Rule/Method: Analyzing informational narratives and extracting main ideas regarding social contribution (Chapter 7, Section 7.2, Pages 184–188).
                """.trimIndent()
            )

            addNote(
                "Unit 7 - Community Services",
                "Vocabulary Development (Section 7.3)",
                """
                • Focuses on vocabulary related to civic duty, volunteering, charity, and social work (Chapter 7, Section 7.3, Pages 190–193).
                • Rule/Method: Using semantic mapping and contextual clues to master civic vocabulary (Chapter 7, Section 7.3, Pages 191–192).
                """.trimIndent()
            )

            addNote(
                "Unit 7 - Community Services",
                "Grammar (Section 7.4)",
                """
                • Focuses on reported speech (indirect speech) for statements, questions, and commands (Chapter 7, Section 7.4, Pages 194–200).
                • Rule/Method: Backshifting tenses and changing pronouns and time markers when converting direct speech into reported speech (e.g., He said, "I help the community" → He said that he helped the community) (Chapter 7, Section 7.4, Pages 195–198).
                """.trimIndent()
            )

            addNote(
                "Unit 7 - Community Services",
                "Speaking Skills (Section 7.5)",
                """
                • Focuses on interviewing community workers, proposing volunteer projects, and discussing civic contributions (Chapter 7, Section 7.5, Pages 201–203).
                • Rule/Method: Formulating polite interview questions and reporting oral responses (Chapter 7, Section 7.5, Pages 201–202).
                """.trimIndent()
            )

            addNote(
                "Unit 7 - Community Services",
                "Writing Skills (Section 7.6)",
                """
                • Focuses on writing formal letters, reports on community service activities, and persuasive letters requesting volunteer support (Chapter 7, Section 7.6, Pages 204–207).
                • Rule/Method: Formatting formal letters correctly (sender address, recipient address, salutation, body paragraphs, sign-off) (Chapter 7, Section 7.6, Pages 205–206).
                """.trimIndent()
            )

            // Unit 8
            addNote(
                "Unit 8 - Communicable Diseases",
                "Listening Skills (Section 8.1)",
                """
                • Focuses on listening to health awareness messages, prevention of infectious diseases (such as HIV/AIDS, malaria, and tuberculosis), and public health guidelines (Chapter 8, Section 8.1, Pages 208–211).
                • Rule/Method: Extracting health advice, precautions, and statistical facts from spoken medical awareness texts (Chapter 8, Section 8.1, Pages 209–210).
                """.trimIndent()
            )

            addNote(
                "Unit 8 - Communicable Diseases",
                "Reading Skills (Section 8.2)",
                """
                • Focuses on reading health education passages, medical advice, and scientific explanations of disease transmission and prevention (Chapter 8, Section 8.2, Pages 212–218).
                • Rule/Method: Identifying medical terminology, analyzing informational text structures, and synthesizing health prevention steps (Chapter 8, Section 8.2, Pages 213–217).
                """.trimIndent()
            )

            addNote(
                "Unit 8 - Communicable Diseases",
                "Vocabulary Development (Section 8.3)",
                """
                • Focuses on medical and health-related vocabulary, symptoms, transmission terms, and preventive health concepts (Chapter 8, Section 8.3, Pages 219–222).
                • Rule/Method: Recognizing prefixes and suffixes in medical terms (e.g., anti-, -itis) (Chapter 8, Section 8.3, Pages 220–221).
                """.trimIndent()
            )

            addNote(
                "Unit 8 - Communicable Diseases",
                "Grammar (Section 8.4)",
                """
                • Focuses on conditional sentences (First and Second conditionals) for discussing health risks and hypothetical preventative scenarios (Chapter 8, Section 8.4, Pages 223–229).
                • Rule/Method: Formulating conditional structures (e.g., First conditional: If you wash your hands, you will prevent infection; Second conditional: If people had clean water, disease rates would drop) (Chapter 8, Section 8.4, Pages 224–227).
                """.trimIndent()
            )

            addNote(
                "Unit 8 - Communicable Diseases",
                "Speaking Skills (Section 8.5)",
                """
                • Focuses on conducting health awareness campaigns, giving health talks, and discussing disease prevention (Chapter 8, Section 8.5, Pages 230–232).
                • Rule/Method: Using persuasive language, warnings, and advisory phrases in public speaking contexts (Chapter 8, Section 8.5, Pages 230–231).
                """.trimIndent()
            )

            addNote(
                "Unit 8 - Communicable Diseases",
                "Writing Skills (Section 8.6)",
                """
                • Focuses on writing health awareness brochures, informative flyers, and explanatory paragraphs on disease prevention (Chapter 8, Section 8.6, Pages 233–236).
                • Rule/Method: Combining concise headings, bullet points, and explanatory sentences to create effective public health materials (Chapter 8, Section 8.6, Pages 234–235).
                """.trimIndent()
            )

            // Unit 9
            addNote(
                "Unit 9 - Fairness and Equity",
                "Listening Skills (Section 9.1)",
                """
                • Focuses on listening to discussions about human rights, equality, justice, gender parity, and inclusive governance (Chapter 9, Section 9.1, Pages 237–240).
                • Rule/Method: Analyzing speaker arguments regarding social justice and extracting key thematic points (Chapter 9, Section 9.1, Pages 238–239).
                """.trimIndent()
            )

            addNote(
                "Unit 9 - Fairness and Equity",
                "Reading Skills (Section 9.2)",
                """
                • Focuses on reading literary and expository texts touching upon equality, equity, human rights, and social justice (Chapter 9, Section 9.2, Pages 241–247).
                • Rule/Method: Analyzing author perspective, interpreting thematic messages, and evaluating equity concepts (Chapter 9, Section 9.2, Pages 242–246).
                """.trimIndent()
            )

            addNote(
                "Unit 9 - Fairness and Equity",
                "Vocabulary Development (Section 9.3)",
                """
                • Focuses on vocabulary related to justice, rights, equality, law, and societal fairness (Chapter 9, Section 9.3, Pages 248–251).
                • Rule/Method: Exploring synonyms, antonyms, and contextual word usages for justice-related terms (Chapter 9, Section 9.3, Pages 249–250).
                """.trimIndent()
            )

            addNote(
                "Unit 9 - Fairness and Equity",
                "Grammar (Section 9.4)",
                """
                • Focuses on relative clauses (defining and non-defining relative clauses using who, which, that, whose, where) (Chapter 9, Section 9.4, Pages 252–258).
                • Rule/Method: Joining sentences using relative pronouns; distinguishing between defining clauses (essential for meaning, no commas) and non-defining clauses (extra information, enclosed in commas) (Chapter 9, Section 9.4, Pages 253–256).
                """.trimIndent()
            )

            addNote(
                "Unit 9 - Fairness and Equity",
                "Speaking Skills (Section 9.5)",
                """
                • Focuses on debating human rights issues, role-playing advocacy scenarios, and discussing fairness in society (Chapter 9, Section 9.5, Pages 259–261).
                • Rule/Method: Expressing viewpoints, defending opinions with reasons, and listening to opposing arguments respectfully (Chapter 9, Section 9.5, Pages 259–260).
                """.trimIndent()
            )

            addNote(
                "Unit 9 - Fairness and Equity",
                "Writing Skills (Section 9.6)",
                """
                • Focuses on writing persuasive essays, opinion pieces, and reflective paragraphs on equity and human rights (Chapter 9, Section 9.6, Pages 262–265).
                • Rule/Method: Structuring persuasive writing with a strong thesis, supporting arguments, counterarguments, and a compelling conclusion (Chapter 9, Section 9.6, Pages 263–264).
                """.trimIndent()
            )

            // Unit 10
            addNote(
                "Unit 10 - The Internet",
                "Listening Skills (Section 10.1)",
                """
                • Focuses on listening to texts about modern technology, computer networks, internet usage, social media, and digital literacy (Chapter 10, Section 10.1, Pages 266–269).
                • Rule/Method: Extracting technical and informational details from spoken discourse on digital technology (Chapter 10, Section 10.1, Pages 267–268).
                """.trimIndent()
            )

            addNote(
                "Unit 10 - The Internet",
                "Reading Skills (Section 10.2)",
                """
                • Focuses on reading informational and technical articles about the role of computers, the internet, online safety, and cyber security (Chapter 10, Section 10.2, Pages 270–276).
                • Rule/Method: Identifying technical concepts, skimming for main ideas, and evaluating the pros and cons of internet usage (Chapter 10, Section 10.2, Pages 271–275).
                """.trimIndent()
            )

            addNote(
                "Unit 10 - The Internet",
                "Vocabulary Development (Section 10.3)",
                """
                • Focuses on digital and internet terminology (e.g., browser, download, firewall, cyberbullying, network, search engine) (Chapter 10, Section 10.3, Pages 277–280).
                • Rule/Method: Matching digital terms with definitions and using computer-related jargon correctly (Chapter 10, Section 10.3, Pages 278–279).
                """.trimIndent()
            )

            addNote(
                "Unit 10 - The Internet",
                "Grammar (Section 10.4)",
                """
                • Focuses on causative verbs (have/get something done) and modal verbs of ability, permission, and obligation in technological contexts (Chapter 10, Section 10.4, Pages 281–287).
                • Rule/Method: Constructing sentences using causatives (e.g., "She had her computer repaired") and advanced modal structures (Chapter 10, Section 10.4, Pages 282–285).
                """.trimIndent()
            )

            addNote(
                "Unit 10 - The Internet",
                "Speaking Skills (Section 10.5)",
                """
                • Focuses on discussing the advantages and disadvantages of social media, online communication, and digital etiquette (Chapter 10, Section 10.5, Pages 288–290).
                • Rule/Method: Presenting balanced arguments and participating in structured class discussions on technology (Chapter 10, Section 10.5, Pages 288–289).
                """.trimIndent()
            )

            addNote(
                "Unit 10 - The Internet",
                "Writing Skills (Section 10.6)",
                """
                • Focuses on writing discursive and analytical essays about the impact of the internet on modern society (Chapter 10, Section 10.6, Pages 291–294).
                • Rule/Method: Synthesizing ideas into cohesive multi-paragraph essays with clear introductory, body, and concluding sections (Chapter 10, Section 10.6, Pages 292–293).
                """.trimIndent()
            )
        }

        return notesList
    }
}
