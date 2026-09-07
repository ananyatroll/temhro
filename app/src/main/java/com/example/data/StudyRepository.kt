package com.example.data

import android.content.Context
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class StudyRepository(private val dao: EducationDao) : DataRepository {

    private val TAG = "TinatRoomFlow"

    override val userProgress: Flow<UserProgress?> = dao.getUserProgress()
    override val subjects: Flow<List<StudySubject>> = dao.getSubjects()

    override fun getNotesBySubject(subjectId: String): Flow<List<SubjectNote>> = dao.getNotesBySubject(subjectId)
    override fun getAllNotes(): Flow<List<SubjectNote>> = dao.getAllNotes()
    override fun getNotesByGrade(gradeLevel: String): Flow<List<SubjectNote>> = dao.getNotesByGrade(gradeLevel)
    override fun searchNotes(query: String): Flow<List<SubjectNote>> = dao.searchNotes(query)
    override suspend fun insertNotes(notes: List<SubjectNote>) = withContext(Dispatchers.IO) {
        dao.insertNotes(notes)
    }

    override fun getQuestionsBySubject(subjectId: String): Flow<List<ExamQuestion>> = dao.getQuestionsBySubject(subjectId)
    override fun getAllQuestions(): Flow<List<ExamQuestion>> = dao.getAllQuestions()
    override suspend fun getQuestionById(questionId: String): ExamQuestion? = withContext(Dispatchers.IO) {
        dao.getQuestionById(questionId)
    }
    override suspend fun insertQuestions(questions: List<ExamQuestion>) = withContext(Dispatchers.IO) {
        dao.insertQuestions(questions)
    }

    override fun getFlashcardsBySubject(subjectId: String): Flow<List<Flashcard>> = dao.getFlashcardsBySubject(subjectId)
    override fun getAllFlashcards(): Flow<List<Flashcard>> = dao.getAllFlashcards()
    override fun getStarredFlashcards(): Flow<List<Flashcard>> = dao.getStarredFlashcards()
    override suspend fun insertFlashcards(flashcards: List<Flashcard>) = withContext(Dispatchers.IO) {
        dao.insertFlashcards(flashcards)
    }
    override suspend fun updateFlashcard(id: String, isKnown: Boolean, isStarred: Boolean) = withContext(Dispatchers.IO) {
        dao.updateFlashcardState(id, isKnown, isStarred)
    }

    override fun getAnalyzedVideos(subjectId: String): Flow<List<AnalyzedVideo>> = dao.getAnalyzedVideos(subjectId)

    override suspend fun insertAnalyzedVideo(video: AnalyzedVideo) = withContext(Dispatchers.IO) {
        dao.insertAnalyzedVideo(video)
    }

    override suspend fun enrollPackage(packageId: String) = withContext(Dispatchers.IO) {
        val current = dao.getUserProgressDirect() ?: UserProgress()
        val normalizedPkg = when {
            packageId == "euee" || packageId == "euee_prep" || packageId == "grade12" -> "euee_natural"
            else -> packageId
        }
        Log.d(TAG, "enrollPackage: raw=$packageId -> normalized=$normalizedPkg")
        val updated = current.copy(activePackageId = normalizedPkg)
        dao.insertUserProgress(updated)
        Unit
    }

    override suspend fun resetEnrollment() = withContext(Dispatchers.IO) {
        val current = dao.getUserProgressDirect() ?: UserProgress()
        Log.d(TAG, "resetEnrollment: resetting activePackageId to null")
        val updated = current.copy(
            activePackageId = null,
            paymentStatus = if (current.paymentStatus == "approved") "approved" else "none",
            paymentTxnId = "",
            paymentSenderPhone = "",
            paymentScreenshotPath = ""
        )
        dao.insertUserProgress(updated)
    }

    override suspend fun submitPaymentIntent(txnId: String, senderPhone: String, screenshotPath: String) = withContext(Dispatchers.IO) {
        val current = dao.getUserProgressDirect() ?: UserProgress()
        Log.d(TAG, "submitPaymentIntent: txnId=$txnId, phone=$senderPhone")
        val updated = current.copy(
            paymentStatus = "pending",
            paymentTxnId = txnId,
            paymentSenderPhone = senderPhone,
            paymentScreenshotPath = screenshotPath
        )
        dao.insertUserProgress(updated)
    }

    override suspend fun approvePayment(packageId: String?, username: String?) = withContext(Dispatchers.IO) {
        val current = dao.getUserProgressDirect() ?: UserProgress()
        val currentPkg = packageId ?: current.activePackageId
        val normalizedPkg = when {
            currentPkg == null || currentPkg == "euee" || currentPkg == "euee_prep" || currentPkg == "grade12" -> "euee_natural"
            else -> currentPkg
        }
        val newUsername = if (!username.isNullOrBlank()) username else current.username

        val existingPurchased = current.purchasedPackageId.split(",").map { it.trim() }.filter { it.isNotEmpty() }.toMutableList()
        if (!existingPurchased.contains(normalizedPkg)) {
            existingPurchased.add(normalizedPkg)
        }
        val newPurchasedStr = existingPurchased.joinToString(",")

        Log.d(TAG, "approvePayment: packageId=$normalizedPkg, username=$newUsername, purchasedPackages=$newPurchasedStr")
        val updated = current.copy(
            paymentStatus = "approved",
            activePackageId = normalizedPkg,
            purchasedPackageId = newPurchasedStr,
            username = newUsername
        )
        dao.insertUserProgress(updated)
        Log.d(TAG, "approvePayment: updated UserProgress successfully committed to Room DB.")
        Unit
    }

    override suspend fun rejectPayment() = withContext(Dispatchers.IO) {
        val current = dao.getUserProgressDirect() ?: UserProgress()
        val updated = current.copy(
            paymentStatus = "none",
            paymentTxnId = "",
            paymentSenderPhone = "",
            paymentScreenshotPath = ""
        )
        dao.insertUserProgress(updated)
    }

    override suspend fun updateUsername(newName: String) = withContext(Dispatchers.IO) {
        val current = dao.getUserProgressDirect() ?: UserProgress()
        val updated = current.copy(username = newName)
        dao.insertUserProgress(updated)
    }

    override suspend fun completeSubject(subjectId: String) = withContext(Dispatchers.IO) {
        val current = dao.getUserProgressDirect() ?: UserProgress()
        val set = current.completedSubjects.split(",")
            .filter { it.isNotEmpty() }
            .toMutableSet()
        set.add(subjectId)
        val updated = current.copy(completedSubjects = set.joinToString(","))
        dao.insertUserProgress(updated)
    }

    override suspend fun updateTelegramStatus(connected: Boolean) = withContext(Dispatchers.IO) {
        val current = dao.getUserProgressDirect() ?: UserProgress()
        val updated = current.copy(telegramConnected = connected)
        dao.insertUserProgress(updated)
    }

    override suspend fun incrementExamScore(score: Int) = withContext(Dispatchers.IO) {
        val current = dao.getUserProgressDirect() ?: UserProgress()
        val updated = current.copy(
            scoreCount = current.scoreCount + score,
            examsCompleted = current.examsCompleted + 1
        )
        dao.insertUserProgress(updated)
    }

    override suspend fun seedDatabaseIfEmpty() = withContext(Dispatchers.IO) {
        val startTime = System.currentTimeMillis()

        // 0. Ensure default user progress exists using fast direct single query
        val currentUser = dao.getUserProgressDirect()
        if (currentUser == null) {
            Log.d(TAG, "seedDatabaseIfEmpty: Creating initial default UserProgress")
            dao.insertUserProgress(UserProgress(username = "Ananya", activePackageId = null))
        }

        val subjectsCount = dao.getSubjectsCount()
        val notesCount = dao.getNotesCount()
        val questionsCount = dao.getQuestionsCount()
        val flashcardsCount = dao.getFlashcardsCount()

        Log.d(TAG, "seedDatabaseIfEmpty: Verification check -> subjects: $subjectsCount, notes: $notesCount, questions: $questionsCount, flashcards: $flashcardsCount")

        // Fast path: If all data is already populated and verified, return immediately without instantiating large lists
        if (subjectsCount >= 41 && notesCount >= 750 && questionsCount >= 796 && flashcardsCount >= 17000) {
            val elapsed = System.currentTimeMillis() - startTime
            Log.d(TAG, "seedDatabaseIfEmpty: Database verified in ${elapsed}ms. DB is fully populated with subjects, notes, questions, and flashcards.")
            return@withContext
        }

        // 1. Seed Subjects if needed
        if (subjectsCount < 41) {
            val subjectsList = listOf(
                // EUEE Natural Science Stream Subjects
                StudySubject("euee_nat_maths", "Mathematics", "maths", "euee_natural"),
                StudySubject("euee_nat_english", "English", "english", "euee_natural"),
                StudySubject("euee_nat_aptitude", "Aptitude (SAT)", "aptitude", "euee_natural"),
                StudySubject("euee_nat_physics", "Physics", "physics", "euee_natural"),
                StudySubject("biology", "Biology", "biology", "euee_natural"),
                StudySubject("chemistry", "Chemistry", "chemistry", "euee_natural"),

                // EUEE Social Science Stream Subjects
                StudySubject("euee_soc_history", "History", "history", "euee_social"),
                StudySubject("euee_soc_geography", "Geography", "geography", "euee_social"),
                StudySubject("euee_soc_maths", "Mathematics", "maths", "euee_social"),
                StudySubject("euee_soc_english", "English", "english", "euee_social"),
                StudySubject("euee_soc_economics", "Economics", "economics", "euee_social"),
                StudySubject("euee_soc_aptitude", "Aptitude (SAT)", "aptitude", "euee_social"),

                // Freshman Semester I Package
                StudySubject("freshman_physics", "General Physics (Phys 1011)", "physics", "freshman"),
                StudySubject("freshman_english_1", "Communicative English I (FLEn 1111)", "english", "freshman"),
                StudySubject("freshman_emerging_tech", "Emerging Tech (EmTe 1012)", "computer", "freshman"),
                StudySubject("freshman_anthropology", "Social Anthropology (Anth 1012)", "anthropology", "freshman"),
                StudySubject("freshman_economics", "Economics (Econ 1011)", "economics", "freshman"),
                StudySubject("freshman_entrepreneurship", "Entrepreneurship (MGMT 1012)", "business", "freshman"),
                StudySubject("freshman_civics", "Moral & Civics (MCiE 1012)", "civics", "freshman"),
                StudySubject("freshman_global_trends", "Global Trends (GlTr-1012)", "geography", "freshman"),

                // Freshman Semester II Package
                StudySubject("freshman_physical_fitness", "Physical Fitness (SpSc1011)", "fitness", "freshman"),
                StudySubject("freshman_history", "History of Ethiopia (HIST 1012)", "history", "freshman"),
                StudySubject("freshman_critical_thinking", "Critical Thinking (LoCT 1011)", "psychology", "freshman"),
                StudySubject("freshman_geography", "Geography of Ethiopia (GeES 1011)", "geography", "freshman"),
                StudySubject("freshman_inclusiveness", "Inclusiveness (SNIE1112)", "groups", "freshman"),
                StudySubject("freshman_psychology", "General Psychology (Psyc1011)", "psychology", "freshman"),
                StudySubject("freshman_english_2", "Communicative English II (FLEn 1122)", "english", "freshman"),
                StudySubject("freshman_maths", "Mathematics for Social Sciences (Math 1012)", "maths", "freshman"),

                // AAU UAT Prep Package
                StudySubject("uat_verbal", "Verbal Reasoning", "english", "aau_uat"),
                StudySubject("uat_quantitative", "Quantitative Reasoning", "maths", "aau_uat"),
                StudySubject("uat_analytical", "Analytical Reasoning", "analytics", "aau_uat"),
                StudySubject("uat_english", "General English & Vocabulary", "english", "aau_uat"),

                // University Department Package
                StudySubject("dept_accounting", "Accounting and Finance", "accounting", "department"),
                StudySubject("dept_economics", "Economics", "economics", "department"),
                StudySubject("dept_management", "Management", "management", "department"),
                StudySubject("dept_lscm", "Logistics and Supply Chain Management (LSCM)", "logistics", "department"),
                StudySubject("dept_bais", "Business Administration and Information Systems (BAIS)", "bais", "department"),
                StudySubject("dept_psir", "Political Science and International Relations (PSIR)", "psir", "department"),
                StudySubject("dept_marketing", "Marketing Management", "marketing", "department"),
                StudySubject("dept_padm", "Public Administration and Development Management (PADM)", "padm", "department"),
                StudySubject("dept_cs", "Computer Science", "computer", "department"),
                StudySubject("dept_info_sci", "Information Sciences", "info_sci", "department"),
                StudySubject("dept_psychology", "Psychology", "psychology", "department"),
                StudySubject("dept_software", "Software Engineering", "software", "department"),
                StudySubject("dept_mechanical", "Mechanical Engineering", "mechanical", "department"),
                StudySubject("dept_electrical", "Electrical Engineering", "electrical", "department"),
                StudySubject("dept_law", "Law", "civics", "department"),

                // Exit Exam Prep Package
                StudySubject("exit_accounting", "Accounting and Finance Exit Exam", "accounting", "exit_exam"),
                StudySubject("exit_economics", "Economics Exit Exam", "economics", "exit_exam"),
                StudySubject("exit_management", "Management Exit Exam", "management", "exit_exam"),
                StudySubject("exit_lscm", "Logistics and Supply Chain Management Exit Exam", "logistics", "exit_exam"),
                StudySubject("exit_bais", "Business Administration and Information Systems Exit Exam", "bais", "exit_exam"),
                StudySubject("exit_psir", "Political Science and International Relations Exit Exam", "psir", "exit_exam"),
                StudySubject("exit_marketing", "Marketing Management Exit Exam", "marketing", "exit_exam"),
                StudySubject("exit_padm", "Public Administration and Development Management Exit Exam", "padm", "exit_exam"),
                StudySubject("exit_cs", "Computer Science Exit Exam", "computer", "exit_exam"),
                StudySubject("exit_info_sci", "Information Sciences Exit Exam", "info_sci", "exit_exam"),
                StudySubject("exit_psychology", "Psychology Exit Exam", "psychology", "exit_exam"),
                StudySubject("exit_software", "Software Engineering Exit Exam", "software", "exit_exam"),
                StudySubject("exit_mechanical", "Mechanical Engineering Exit Exam", "mechanical", "exit_exam"),
                StudySubject("exit_electrical", "Electrical Engineering Exit Exam", "electrical", "exit_exam"),
                StudySubject("exit_law", "Law Exit Exam", "civics", "exit_exam")
            )
            dao.insertSubjects(subjectsList)
            Log.d(TAG, "seedDatabaseIfEmpty: Seeded ${subjectsList.size} subjects.")
        }

        // 2. Seed Notes if needed
        if (notesCount < 750) {
            Log.d(TAG, "seedDatabaseIfEmpty: Notes count low ($notesCount), seeding comprehensive notes...")
            val notesList = listOf(
                // Biology
                SubjectNote(
                    "bio_note_1", "biology", "UNIT 1", "Cellular Structure & Organelles",
                    "The cell is the basic structural, functional, and biological unit of all known organisms. " +
                    "Eukaryotic cells contain membrane-bound organelles, including:\n\n" +
                    "• Mitochondria: The 'powerhouse' of the cell, generating adenosine triphosphate (ATP) through cellular respiration.\n" +
                    "• Nucleus: Coordinates activities like growth, intermediary metabolism, and protein synthesis; houses genetic material DNA.\n" +
                    "• Ribosomes: Complex macromolecular machines responsible for synthesizing polypeptides and proteins.\n" +
                    "• Endoplasmic Reticulum (ER): Rough ER is studded with ribosomes and is critical for protein folding and routing; Smooth ER handles lipid synthesis.\n" +
                    "• Chloroplasts: Unique to plant cells and algae; convert solar energy into chemical energy via chlorophyll pigments in photosynthesis."
                ),
                SubjectNote(
                    "bio_note_2", "biology", "UNIT 2", "Photosynthesis & Cellular Respiration",
                    "Metabolism consists of catabolic and anabolic pathways. Two primary energy pathways in life are:\n\n" +
                    "• Photosynthesis:\n" +
                    "  Equation: 6CO₂ + 6H₂O + Light ➔ C₆H₁₂O₆ + 6O₂\n" +
                    "  Takes place in chloroplast thylakoids (light-dependent reactions) and stroma (the Calvin Cycle).\n\n" +
                    "• Cellular Respiration:\n" +
                    "  Equation: C₆H₁₂O₆ + 6O₂ ➔ 6CO₂ + 6H₂O + ATP\n" +
                    "  Comprises Glycolysis, the Krebs Cycle (citric acid cycle), and the Electron Transport Chain (ETC). Glycolysis occurs in the cytosol, while the Krebs Cycle and ETC occur inside mitochondria."
                ),

                // Civics
                SubjectNote(
                    "civ_note_1", "civics", "UNIT 1", "Constitutional Federalism",
                    "Federalism is a system of government where sovereignty is constitutionally divided between a central authority and constituent political units.\n\n" +
                    "Key aspects of Constitutional Federalism include:\n" +
                    "• Division of Powers: Explicit listing of federal authority, regional authority, and shared (concurrent) areas.\n" +
                    "• Supremacy Clause: The constitution holds supreme legal power, requiring federal and state policies to remain compliant.\n" +
                    "• Resolution of Disputes: An independent judiciary acts as an arbiter to settle territorial disputes between the federal center and states.\n" +
                    "• Double representation: Citizens elect lawmakers to both state parliaments and national congressional assemblies."
                ),
                SubjectNote(
                    "civ_note_2", "civics", "UNIT 2", "Civic Duties and Sovereignty",
                    "Democracy thrives on active, well-informed, and responsible civic actions.\n\n" +
                    "Core Duties of a Democratic Citizen:\n" +
                    "• Obeying the Constitution and legal acts: Safeguards societal order.\n" +
                    "• Paying taxes: Equips the state with resources to build public works, clinics, and educational resources.\n" +
                    "• Participating in voting: Choosing regional and national leaders safeguards sovereignty and human rights.\n" +
                    "• Protection of environment and heritage: Sustaining local ecological and cultural treasures."
                ),

                // Chemistry
                SubjectNote(
                    "chem_note_1", "chemistry", "UNIT 1", "Atomic Structure & Chemical Bonds",
                    "Matter holds chemical structures that determine reactions and stability.\n\n" +
                    "• Atomic Theory: Atoms consist of a central, positively charged nucleus containing protons and neutrons, orbited by negative electrons in quantum energy levels.\n" +
                    "• Covalent Bonds: Strong linkages formed by the sharing of valence electron pairs. Enables stable molecules like H₂O and CO₂.\n" +
                    "• Ionic Bonds: Result from complete transfer of electrons from a donor metal to an acceptor non-metal, forming ions bound by energetic electrostatic attractions (e.g., NaCl).\n" +
                    "• Metallic Bonds: Unique arrangements where free-flowing electrons are shared among a lattice of metal cations."
                ),
                SubjectNote(
                    "chem_note_2", "chemistry", "UNIT 2", "pH Scale and Acid-Base Balancing",
                    "Acids and bases are fundamental compound classes measured via hydrogen ion concentration (pH).\n\n" +
                    "• Acidic pH (0–6.9): Elevated concentration of H⁺ ions. Sour taste, turns blue litmus paper red (e.g., HCl in stomach fluid, citric acid).\n" +
                    "• Neutral pH (7): Equated to pure, balanced H₂O where [H⁺] equals [OH⁻] at standard temperature.\n" +
                    "• Basic/Alkaline pH (7.1–14): High concentration of OH⁻ ions. Bitter, slippery feel, turns red litmus blue (e.g., ammonia, bleach)."
                ),

                // Anthropology
                SubjectNote(
                    "anth_note_1", "anthropology", "UNIT 1", "The Four Fields of Anthropology",
                    "Anthropology is the comprehensive, holistic study of humanity across temporal and physical spectrums.\n\n" +
                    "The classic subfields established by modern practice are:\n" +
                    "1. Cultural Anthropology: Understanding social patterns, human thoughts, language dialects, customs, and kinship systems.\n" +
                    "2. Archaeological Anthropology: Investigating extinct material items, pottery, structures, and historical locations to piece together ancestral habits.\n" +
                    "3. Biological (Physical) Anthropology: Exploring human anatomical traits, genetic diversification, evolutionary skeletons, and primeval primates.\n" +
                    "4. Linguistic Anthropology: Evaluating structural language, speech communication, origins of alphabets, and impact on societal connections."
                ),
                SubjectNote(
                    "anth_note_2", "anthropology", "UNIT 2", "Lucy and Human Origins in Ethiopia",
                    "Ethiopia is widely celebrated as the cradle of humankind owing to its exceptional fossilized biological links.\n\n" +
                    "• Lucy (Dinkinesh): Discovered in Hadar, Afar in 1974 by Donald Johanson's team. Dated to approximately 3.2 million years ago.\n" +
                    "• Classification: Australopithecus afarensis. She represents a highly complete skeletal link demonstrating evolutionary upright bipedalism, combined with small chimpanzee-sized brain cavities.\n" +
                    "• Paleontological Context: Discoveries like Ardi (Ardipithecus ramidus) further cement the Great Rift Valley as a global goldmine for human origin research."
                )
            ) + getFreshmanNotes() + getSemester2Notes() + getUatNotes() + getEueeNotes() + getDepartmentNotes() + getExitExamNotes() + Grade9MathNotes.getGrade9MathNotes() + Grade9EnglishNotes.getGrade9EnglishNotes() + Grade9PhysicsNotes.getGrade9PhysicsNotes() + Grade9BiologyNotes.getGrade9BiologyNotes() + Grade9ChemistryNotes.getGrade9ChemistryNotes() + Grade9HistoryNotes.getGrade9HistoryNotes() + Grade9GeographyNotes.getGrade9GeographyNotes() + Grade9EconomicsNotes.getGrade9EconomicsNotes() + Grade10MathNotes.getGrade10MathNotes() + Grade10EnglishNotes.getGrade10EnglishNotes() + Grade10ChemistryNotes.getGrade10ChemistryNotes() + Grade10PhysicsNotes.getGrade10PhysicsNotes() + Grade10BiologyNotes.getGrade10BiologyNotes() + Grade10HistoryNotes.getGrade10HistoryNotes() + Grade10GeographyNotes.getGrade10GeographyNotes() + Grade10EconomicsNotes.getGrade10EconomicsNotes() + Grade11BiologyNotes.getGrade11BiologyNotes() + Grade11ChemistryNotes.getGrade11ChemistryNotes() + Grade11EconomicsNotes.getGrade11EconomicsNotes() + Grade11EnglishNotes.getGrade11EnglishNotes() + Grade11GeographyNotes.getGrade11GeographyNotes() + Grade11HistoryNotes.getGrade11HistoryNotes() + Grade11MathNotes.getGrade11MathNotes() + Grade11PhysicsNotes.getGrade11PhysicsNotes() + Grade12BiologyNotes.getGrade12BiologyNotes() + Grade12ChemistryNotes.getGrade12ChemistryNotes() + Grade12EconomicsNotes.getGrade12EconomicsNotes() + Grade12EnglishNotes.getGrade12EnglishNotes() + Grade12GeographyNotes.getGrade12GeographyNotes() + Grade12HistoryNotes.getGrade12HistoryNotes() + Grade12MathNotes.getGrade12MathNotes() + Grade12PhysicsNotes.getGrade12PhysicsNotes()
            dao.insertNotes(notesList)
            Log.d(TAG, "seedDatabaseIfEmpty: Inserted ${notesList.size} notes.")
        }

        // 3. Seed Questions if needed
        if (questionsCount < 796) {
            Log.d(TAG, "seedDatabaseIfEmpty: Questions count low ($questionsCount), seeding questions...")
            val questionsList = listOf(
                // Biology
                ExamQuestion(
                    "bio_q_1", "biology",
                    "Which organelle in plant and animal cells is primarily responsible for performing cellular respiration and generating ATP?",
                    "Lysosome", "Mitochondria", "Golgi Body", "Endoplasmic Reticulum",
                    "B",
                    "Mitochondria convert glucose and oxygen into ATP, the energetic cellular currency, through structural Krebs and Electron Transport cycles."
                ),
                ExamQuestion(
                    "bio_q_2", "biology",
                    "What organic macromolecules are primarily synthesized by cellular ribosomes?",
                    "Lipids", "Carbohydrates", "Proteins", "Nucleic Acids",
                    "C",
                    "Ribosomes decode messenger RNA transcripts into chains of amino acids, successfully folding them into functional structural proteins."
                ),

                // Civics
                ExamQuestion(
                    "civ_q_1", "civics",
                    "Which form of government structure constitutionally shares sovereign governing powers between a national entity and regional states?",
                    "Unitary state", "Federal system", "Absolutist monarchy", "Anarchy",
                    "B",
                    "Federalism explicitly guarantees dual levels of government authority and regional states within a unified national constitution."
                ),
                ExamQuestion(
                    "civ_q_2", "civics",
                    "Obeying constitutional laws and paying direct socioeconomic taxes are considered what type of citizenship traits?",
                    "Optional social hobbies", "Fundamental civic duties", "Professional privileges", "Leisure selections",
                    "B",
                    "Citizenship requires fulfilling fundamental legal and financial duties like tax compliance to build municipal resources and welfare."
                ),

                // Chemistry
                ExamQuestion(
                    "chem_q_1", "chemistry",
                    "A chemical linkage formed by the physical sharing of valence electron pairs is classified as which type of bond?",
                    "Ionic bond", "Covalent bond", "Hydrogen bond", "Metallic bond",
                    "B",
                    "Covalent bonds share electrons between non-metallic atoms to achieve stable, full octet electronic shell configurations."
                ),
                ExamQuestion(
                    "chem_q_2", "chemistry",
                    "At standard temperatures, pure neutral water yields which specific pH value on the logarithmic acid-base index?",
                    "Zero", "Five", "Seven", "Fourteen",
                    "C",
                    "A neutral aqueous environment indicates equivalent concentrations of H⁺ and OH⁻ ions, scoring exactly 7 on the logarithmic scale."
                ),

                // Anthropology
                ExamQuestion(
                    "anth_q_1", "anthropology",
                    "Which field is NOT classified as one of the four traditional disciplines comprising academic anthropology?",
                    "Linguistic Anthropology", "Biological Anthropology", "Cultural Anthropology", "Astrological Science",
                    "D",
                    "Astrology is a pseudoscience of stars. Human anthropology focuses strictly on linguistics, cultural systems, biology, and archaeology."
                ),
                ExamQuestion(
                    "anth_q_2", "anthropology",
                    "The legendary fossilized hominid remains named Lucy (Dinkinesh), found in the Afar Rift, are taxonomically classified as which ancient species?",
                    "Homo erectus", "Australopithecus afarensis", "Homo neanderthalensis", "Homo habilis",
                    "B",
                    "Australopithecus afarensis is the bipedal hominid ancestor dating back 3.2 million years, with Lucy being its quintessential specimen."
                )
            ) + getFreshmanQuestions() + getSemester2Questions() + getUatQuestions() + getEueeQuestions() + getDepartmentQuestions() + getExitExamQuestions() + Grade9MathQuestions.get750Questions()
            dao.insertQuestions(questionsList)
            Log.d(TAG, "seedDatabaseIfEmpty: Inserted ${questionsList.size} questions.")
        }

        // 4. Seed Flashcards if needed
        if (flashcardsCount < 17000) {
            Log.d(TAG, "seedDatabaseIfEmpty: Flashcards count low ($flashcardsCount), seeding flashcards...")
            val flashcardsList = listOf(
                // Biology
                Flashcard("bio_fc_1", "biology", "Mitochondria", "Cell 'powerhouse' responsible for structural ATP generation via cellular respiration.", false, false),
                Flashcard("bio_fc_2", "biology", "Chlorophyll", "Green pigment inside plant chloroplasts that captures photons during photosynthesis.", false, false),
                Flashcard("bio_fc_3", "biology", "Eukaryote", "Cell structure with a distinct membrane-bound nucleus and specialized organelles.", false, false),

                // Civics
                Flashcard("civ_fc_1", "civics", "Federalism", "System of government where power is shared between a national center and regional states.", false, false),
                Flashcard("civ_fc_2", "civics", "Civic Duty", "Binding responsibilities of a citizen including obeying laws and active tax compliance.", false, false),
                Flashcard("civ_fc_3", "civics", "Sovereignty", "The supreme power or legal authority of a nation to govern itself independently.", false, false),

                // Chemistry
                Flashcard("chem_fc_1", "chemistry", "Covalent Bond", "Strong atomic attraction caused by mutual sharing of valence electron orbits.", false, false),
                Flashcard("chem_fc_2", "chemistry", "Acid", "Compound yielding a high concentration of H⁺ ions with clean acidic pH levels below 7.", false, false),
                Flashcard("chem_fc_3", "chemistry", "Stoichiometry", "The calculation of quantitative weights and volumes of chemical reactants and products.", false, false),

                // Anthropology
                Flashcard("anth_fc_1", "anthropology", "Lucy (Dinkinesh)", "Famous bipedal Australopithecus afarensis skeleton discovered in Afar in 1974.", false, false),
                Flashcard("anth_fc_2", "anthropology", "Four-Field Approach", "Traditional fields: Archaeology, Cultural, Biological, and Linguistic Anthropology.", false, false),
                Flashcard("anth_fc_3", "anthropology", "Bipedalism", "The evolutionary anatomical capacity to stand and walk upright on two legs.", false, false)
            ) + getEueeFlashcards() + getFreshmanFlashcards() + getDepartmentFlashcards() + getExitExamFlashcards() + Grade9MathFlashcards.get500Flashcards() + Grade9EnglishFlashcards.get500Flashcards() + Grade9PhysicsFlashcards.get500Flashcards() + Grade9BiologyFlashcards.get500Flashcards() + Grade9ChemistryFlashcards.get500Flashcards() + Grade9HistoryFlashcards.get500Flashcards() + Grade9GeographyFlashcards.get500Flashcards() + Grade9EconomicsFlashcards.get500Flashcards() + Grade10MathFlashcards.get500Flashcards() + Grade10EnglishFlashcards.get500Flashcards() + Grade10ChemistryFlashcards.get500Flashcards() + Grade10PhysicsFlashcards.get500Flashcards() + Grade10BiologyFlashcards.get500Flashcards() + Grade10HistoryFlashcards.get500Flashcards() + Grade10GeographyFlashcards.get500Flashcards() + Grade10EconomicsFlashcards.get500Flashcards() + Grade11BiologyFlashcards.get500Flashcards() + Grade11ChemistryFlashcards.get500Flashcards() + Grade11EconomicsFlashcards.get500Flashcards() + Grade11EnglishFlashcards.get500Flashcards() + Grade11GeographyFlashcards.get500Flashcards() + Grade11HistoryFlashcards.get500Flashcards() + Grade11MathFlashcards.get500Flashcards() + Grade11PhysicsFlashcards.get500Flashcards() + Grade12BiologyFlashcards.get500Flashcards() + Grade12ChemistryFlashcards.get500Flashcards() + Grade12EconomicsFlashcards.get500Flashcards() + Grade12EnglishFlashcards.get500Flashcards() + Grade12GeographyFlashcards.get500Flashcards() + Grade12HistoryFlashcards.get500Flashcards() + Grade12MathFlashcards.get500Flashcards() + Grade12PhysicsFlashcards.get500Flashcards()
            dao.insertFlashcards(flashcardsList)
            Log.d(TAG, "seedDatabaseIfEmpty: Inserted ${flashcardsList.size} flashcards.")
        }

        val elapsed = System.currentTimeMillis() - startTime
        Log.d(TAG, "seedDatabaseIfEmpty: Seeding & verification completed in ${elapsed}ms. DB is fully populated and verified.")
        Unit
    }

    private fun getFreshmanNotes(): List<SubjectNote> {
        return listOf(
            // General Physics (Phys 1011)
            SubjectNote("fn_phy_c1", "freshman_physics", "Chapter 1", "Vectors & Kinematics in 1D/2D", 
                "General Physics Phys 1011 Chapter 1 covers physical vector quantities, cross and dot products, 2D projectile motion equations, and velocity vectors.", "Chapter 1"),
            SubjectNote("fn_phy_c2", "freshman_physics", "Chapter 2", "Newton's Laws of Motion & Work-Energy", 
                "Chapter 2 studies Newton's three laws of motion, friction forces, work done by constant and variable forces, and the Work-Kinetic Energy Theorem (W = ΔKE).", "Chapter 2"),
            SubjectNote("fn_phy_c3", "freshman_physics", "Chapter 3", "Fluid Mechanics & Thermodynamics", 
                "Chapter 3 introduces fluid pressure, Pascal's principle, Archimedes' buoyant force principle, ideal gas laws, and laws of thermodynamics.", "Chapter 3"),
            SubjectNote("fn_phy_c4", "freshman_physics", "Chapter 4", "Electromagnetism & Modern Physics", 
                "Chapter 4 covers Coulomb's law, electric potential, Ohm's law, magnetic induction, photons, and quantum energy quantization (E = hf).", "Chapter 4"),

            // Communicative English I (FLEn 1111)
            SubjectNote("fn_eng1_c1", "freshman_english_1", "Chapter 1", "Grammar & Word Classes", 
                "Communicative English I Chapter 1 focuses on lexical categories, noun phrases, auxiliary verbs, tenses, and active voice sentence structures.", "Chapter 1"),
            SubjectNote("fn_eng1_c2", "freshman_english_1", "Chapter 2", "Active vs Passive Transformations", 
                "Chapter 2 guides active to passive voice conversions across simple, continuous, and perfect verb tenses.", "Chapter 2"),
            SubjectNote("fn_eng1_c3", "freshman_english_1", "Chapter 3", "Academic Reading Strategies", 
                "Chapter 3 covers skimming, scanning, contextual vocabulary, and identifying main ideas and thesis statements.", "Chapter 3"),
            SubjectNote("fn_eng1_c4", "freshman_english_1", "Chapter 4", "Paragraph Cohesion & Discourse Markers", 
                "Chapter 4 covers paragraph organization, supporting claims, and discourse transition markers (e.g., Furthermore, However, Consequently).", "Chapter 4"),

            // Emerging Tech (EmTe 1012)
            SubjectNote("fn_emtech_c1", "freshman_emerging_tech", "Chapter 1", "Introduction to Emerging Tech (AI, IoT, Cloud)", 
                "Emerging Tech Chapter 1 introduces Artificial Intelligence, Internet of Things sensor grids, and cloud server infrastructures.", "Chapter 1"),
            SubjectNote("fn_emtech_c2", "freshman_emerging_tech", "Chapter 2", "Data Science & Big Data Analytics", 
                "Chapter 2 details big data characteristics (Volume, Velocity, Variety), machine learning models, and predictive data analytics.", "Chapter 2"),
            SubjectNote("fn_emtech_c3", "freshman_emerging_tech", "Chapter 3", "Blockchain & Decentralized Systems", 
                "Chapter 3 explores immutable distributed ledgers, smart contracts, cryptographic hashes, and peer-to-peer security.", "Chapter 3"),
            SubjectNote("fn_emtech_c4", "freshman_emerging_tech", "Chapter 4", "Cybersecurity & Ethics in Emerging Tech", 
                "Chapter 4 analyzes digital threat vectors, encryption, privacy laws, and ethical implications of autonomous systems.", "Chapter 4"),

            // Social Anthropology (Anth 1012)
            SubjectNote("fn_anth_c1", "freshman_anthropology", "Chapter 1", "Scope & Four Subfields of Anthropology", 
                "Social Anthropology Chapter 1 covers cultural, biological, archaeological, and linguistic anthropology subfields.", "Chapter 1"),
            SubjectNote("fn_anth_c2", "freshman_anthropology", "Chapter 2", "Culture, Ethnocentrism & Relativism", 
                "Chapter 2 examines cultural mechanisms, avoiding ethnocentric bias, and applying cultural relativism.", "Chapter 2"),
            SubjectNote("fn_anth_c3", "freshman_anthropology", "Chapter 3", "Human Origins & Ethiopian Fossil Discoveries", 
                "Chapter 3 details hominid evolution, Australopithecus afarensis (Lucy/Dinkinesh), and Afar Rift Valley fossil heritage.", "Chapter 3"),
            SubjectNote("fn_anth_c4", "freshman_anthropology", "Chapter 4", "Kinship, Marriage & Social Organization", 
                "Chapter 4 covers family descent systems, marital traditions, economic reciprocity, and social stratification.", "Chapter 4"),

            // Economics (Econ 1011)
            SubjectNote("fn_econ_c1", "freshman_economics", "Chapter 1", "Scarcity & Opportunity Cost", 
                "Economics Chapter 1 covers scarcity, choice, opportunity cost, and the Production Possibility Curve (PPC).", "Chapter 1"),
            SubjectNote("fn_econ_c2", "freshman_economics", "Chapter 2", "Demand, Supply & Market Equilibrium", 
                "Chapter 2 studies the Law of Demand, Law of Supply, price determination, and price elasticity.", "Chapter 2"),
            SubjectNote("fn_econ_c3", "freshman_economics", "Chapter 3", "Theory of Production & Cost Functions", 
                "Chapter 3 examines short-run vs long-run production, marginal cost, total cost, and profit maximization.", "Chapter 3"),
            SubjectNote("fn_econ_c4", "freshman_economics", "Chapter 4", "Macroeconomic Indicators & Monetary Policy", 
                "Chapter 4 tracks Gross Domestic Product (GDP), inflation rates, unemployment, and fiscal/monetary controls.", "Chapter 4"),

            // Entrepreneurship (MGMT 1012)
            SubjectNote("fn_ent_c1", "freshman_entrepreneurship", "Chapter 1", "Entrepreneurial Mindset & Opportunity Identification", 
                "Entrepreneurship Chapter 1 covers spotting market opportunities, risk management, and startup innovation.", "Chapter 1"),
            SubjectNote("fn_ent_c2", "freshman_entrepreneurship", "Chapter 2", "Feasibility Study & Business Plan Development", 
                "Chapter 2 details business plan components, executive summaries, market feasibility, and operational setups.", "Chapter 2"),
            SubjectNote("fn_ent_c3", "freshman_entrepreneurship", "Chapter 3", "Financial Planning & Capital Mobilization", 
                "Chapter 3 covers startup seed funding, cash flow projections, venture capital, and break-even calculations.", "Chapter 3"),
            SubjectNote("fn_ent_c4", "freshman_entrepreneurship", "Chapter 4", "Marketing Strategy & Startup Growth", 
                "Chapter 4 examines target market segmentation, branding, sales channels, and customer retention strategies.", "Chapter 4"),

            // Moral & Civics (MCiE 1012)
            SubjectNote("fn_civics_c1", "freshman_civics", "Chapter 1", "Ethics, Morality & Democratic Values", 
                "Moral and Civics Chapter 1 covers moral philosophy, ethical judgment, and core democratic principles.", "Chapter 1"),
            SubjectNote("fn_civics_c2", "freshman_civics", "Chapter 2", "Constitutional Federalism in Ethiopia", 
                "Chapter 2 studies constitutional supremacy, division of federal/state powers, and rule of law.", "Chapter 2"),
            SubjectNote("fn_civics_c3", "freshman_civics", "Chapter 3", "Human Rights & Civic Responsibilities", 
                "Chapter 3 covers fundamental human rights protections, legal equality, tax compliance, and civic participation.", "Chapter 3"),
            SubjectNote("fn_civics_c4", "freshman_civics", "Chapter 4", "Peace Building & Indigenous Conflict Resolution", 
                "Chapter 4 explores traditional dispute resolution mechanisms, peace-building, and social cohesion.", "Chapter 4"),

            // Global Trends (GlTr-1012)
            SubjectNote("fn_global_c1", "freshman_global_trends", "Chapter 1", "Vectors of Globalization & State Sovereignty", 
                "Global Trends Chapter 1 analyzes economic/technological globalization and evolving state sovereignty.", "Chapter 1"),
            SubjectNote("fn_global_c2", "freshman_global_trends", "Chapter 2", "Foreign Policy & Ethiopian Diplomacy", 
                "Chapter 2 covers foreign policy objectives, diplomatic strategies, and regional Horn of Africa relations.", "Chapter 2"),
            SubjectNote("fn_global_c3", "freshman_global_trends", "Chapter 3", "International Political Economy & Trade", 
                "Chapter 3 studies international trade organizations, economic integration, and global market dynamics.", "Chapter 3"),
            SubjectNote("fn_global_c4", "freshman_global_trends", "Chapter 4", "Global Governance & International Organizations", 
                "Chapter 4 examines the United Nations, African Union, and global security frameworks.", "Chapter 4")
        )
    }

    private fun getSemester2Notes(): List<SubjectNote> {
        return listOf(
            // Physical Fitness (SpSc1011)
            SubjectNote("fn_spsc_c1", "freshman_physical_fitness", "Chapter 1", "Health-Related Physical Fitness", 
                "Physical Fitness Chapter 1 covers muscular endurance, strength, flexibility, body composition, and cardiorespiratory endurance.", "Chapter 1"),
            SubjectNote("fn_spsc_c2", "freshman_physical_fitness", "Chapter 2", "Skill-Related Fitness & Exercise Physiology", 
                "Chapter 2 covers speed, agility, balance, power, reaction time, and physiological exercise responses.", "Chapter 2"),
            SubjectNote("fn_spsc_c3", "freshman_physical_fitness", "Chapter 3", "Nutrition, Wellness & Lifestyle Health", 
                "Chapter 3 explores balanced nutrition, caloric needs, hydration, and preventing lifestyle chronic diseases.", "Chapter 3"),
            SubjectNote("fn_spsc_c4", "freshman_physical_fitness", "Chapter 4", "Workout Program Design & Injury Safety", 
                "Chapter 4 guides workout program planning using FITT principles (Frequency, Intensity, Time, Type) and safety.", "Chapter 4"),

            // History of Ethiopia and the Horn (HIST 1012)
            SubjectNote("fn_hist_c1", "freshman_history", "Chapter 1", "Ancient Peoples & Civilizations in the Horn", 
                "History Chapter 1 covers human origins, early agricultural societies, and the Aksumite civilization.", "Chapter 1"),
            SubjectNote("fn_hist_c2", "freshman_history", "Chapter 2", "Medieval Dynasties & Trade Networks", 
                "Chapter 2 examines the Zagwe dynasty, Solomonic restoration, Gondarine era, and regional trade routes.", "Chapter 2"),
            SubjectNote("fn_hist_c3", "freshman_history", "Chapter 3", "19th Century Unification & Battle of Adwa", 
                "Chapter 3 details Emperor Tewodros II, Emperor Menelik II, state centralization, and the Adwa victory (1896).", "Chapter 3"),
            SubjectNote("fn_hist_c4", "freshman_history", "Chapter 4", "20th Century Ethiopia & Pan-African Leadership", 
                "Chapter 4 covers modern state consolidation, anti-colonial resistance, OAU founding, and contemporary history.", "Chapter 4"),

            // Critical Thinking (LoCT 1011)
            SubjectNote("fn_loct_c1", "freshman_critical_thinking", "Chapter 1", "Logic Fundamentals & Argument Structure", 
                "Logic & Critical Thinking Chapter 1 defines premises, conclusions, and identifying logical arguments.", "Chapter 1"),
            SubjectNote("fn_loct_c2", "freshman_critical_thinking", "Chapter 2", "Deductive vs Inductive Reasoning", 
                "Chapter 2 contrasts validity and soundness in deduction with strength and cogency in induction.", "Chapter 2"),
            SubjectNote("fn_loct_c3", "freshman_critical_thinking", "Chapter 3", "Informal Fallacies & Cognitive Biases", 
                "Chapter 3 analyzes informal logical fallacies like Ad Hominem, Straw Man, Slippery Slope, and False Dilemma.", "Chapter 3"),
            SubjectNote("fn_loct_c4", "freshman_critical_thinking", "Chapter 4", "Categorical Syllogisms & Venn Logic", 
                "Chapter 4 covers categorical propositions, standard syllogisms, and evaluating validity using Venn diagrams.", "Chapter 4"),

            // Geography of Ethiopia (GeES 1011)
            SubjectNote("fn_geob_c1", "freshman_geography", "Chapter 1", "Geological Structure & Topography of Ethiopia", 
                "Geography Chapter 1 covers Rift Valley formation, highland plateaus, and topographic landforms.", "Chapter 1"),
            SubjectNote("fn_geob_c2", "freshman_geography", "Chapter 2", "Climate Systems & Drainage Basins", 
                "Chapter 2 details climate zones (Dega, Weyna Dega, Kolla) and major river basins (Abay, Awash, Omo).", "Chapter 2"),
            SubjectNote("fn_geob_c3", "freshman_geography", "Chapter 3", "Natural Resources & Conservation", 
                "Chapter 3 covers soil types, natural vegetation, wildlife parks, and soil erosion control strategies.", "Chapter 3"),
            SubjectNote("fn_geob_c4", "freshman_geography", "Chapter 4", "Demographics & Economic Sectors", 
                "Chapter 4 examines population distribution, urbanization, agricultural systems, and manufacturing industries.", "Chapter 4"),

            // Inclusiveness (SNIE 1112)
            SubjectNote("fn_incl_c1", "freshman_inclusiveness", "Chapter 1", "Foundations of Inclusiveness & Special Needs", 
                "Inclusiveness Chapter 1 covers human rights models of disability and creating accessible social environments.", "Chapter 1"),
            SubjectNote("fn_incl_c2", "freshman_inclusiveness", "Chapter 2", "Types of Impairments & Assessment", 
                "Chapter 2 details visual, hearing, motor, intellectual, and specific learning impairments.", "Chapter 2"),
            SubjectNote("fn_incl_c3", "freshman_inclusiveness", "Chapter 3", "Inclusive Accommodations & Assistive Tech", 
                "Chapter 3 explores physical accessibility modifications, reasonable accommodations, and assistive tools.", "Chapter 3"),
            SubjectNote("fn_incl_c4", "freshman_inclusiveness", "Chapter 4", "National Policies & Inclusive Culture", 
                "Chapter 4 covers legal protections, UN conventions, and fostering an inclusive institutional culture.", "Chapter 4"),

            // General Psychology (Psyc1011)
            SubjectNote("fn_psych_c1", "freshman_psychology", "Chapter 1", "Foundations of General Psychology", 
                "Psychology Chapter 1 covers research methods, major psychological perspectives, and neurobiology.", "Chapter 1"),
            SubjectNote("fn_psych_c2", "freshman_psychology", "Chapter 2", "Sensation, Perception & Learning", 
                "Chapter 2 details sensory processing, perceptual organization, and Pavlovian Classical & Operant Conditioning.", "Chapter 2"),
            SubjectNote("fn_psych_c3", "freshman_psychology", "Chapter 3", "Memory Systems & Cognitive Intelligence", 
                "Chapter 3 covers encoding, short-term/long-term memory consolidation, retrieval, and intelligence metrics.", "Chapter 3"),
            SubjectNote("fn_psych_c4", "freshman_psychology", "Chapter 4", "Personality, Stress & Mental Health", 
                "Chapter 4 explores personality theories, stress coping mechanisms, and mental health awareness.", "Chapter 4"),

            // Communicative English II (FLEn 1122)
            SubjectNote("fn_eng2_c1", "freshman_english_2", "Chapter 1", "Academic Essay Writing Process", 
                "Communicative English II Chapter 1 covers pre-writing techniques, thesis formulation, and essay drafting.", "Chapter 1"),
            SubjectNote("fn_eng2_c2", "freshman_english_2", "Chapter 2", "Argumentative & Expository Models", 
                "Chapter 2 details constructing persuasive claims, supporting evidence, and counter-argument rebuttals.", "Chapter 2"),
            SubjectNote("fn_eng2_c3", "freshman_english_2", "Chapter 3", "Research Reports & Literature Reviews", 
                "Chapter 3 guides synthesizing literature, summarizing academic journals, and technical report structure.", "Chapter 3"),
            SubjectNote("fn_eng2_c4", "freshman_english_2", "Chapter 4", "Citation Formats (APA/MLA) & Integrity", 
                "Chapter 4 covers in-text citations, reference lists, direct quotes, and avoiding academic plagiarism.", "Chapter 4"),

            // Mathematics for Social Sciences (Math 1012)
            SubjectNote("fn_maths_c1", "freshman_maths", "Chapter 1", "Mathematical Logic & Propositions", 
                "Social Math Chapter 1 covers truth tables, logical connectives, tautologies, and rules of inference.", "Chapter 1"),
            SubjectNote("fn_maths_c2", "freshman_maths", "Chapter 2", "Set Theory & Matrix Algebra", 
                "Chapter 2 covers set operations, Venn diagrams, matrix arithmetic, and determinants.", "Chapter 2"),
            SubjectNote("fn_maths_c3", "freshman_maths", "Chapter 3", "Systems of Linear Equations & Optimization", 
                "Chapter 3 studies Gaussian elimination, inverse matrices, and linear programming applications.", "Chapter 3"),
            SubjectNote("fn_maths_c4", "freshman_maths", "Chapter 4", "Differential Calculus in Business", 
                "Chapter 4 covers limit evaluations, derivative rules, marginal revenue/cost analysis, and business optimization.", "Chapter 4")
        )
    }

    private fun getUatNotes(): List<SubjectNote> {
        return listOf(
            SubjectNote("uat_verb_n1", "uat_verbal", "UNIT 1", "Verbal Analogies and Synonyms", 
                "Verbal reasoning analyzes vocabulary relationships, logical analogies, and reading interpretation. Verbal analogies test structural logic (e.g., KITTEN : CAT :: PUPPY : DOG). Focus on recognizing synonym links and sentence completions."),
            SubjectNote("uat_quant_n1", "uat_quantitative", "UNIT 1", "Quantitative and Algebraic Models", 
                "Quantitative reasoning tests mathematical arithmetic, geometric area formulations, percentages, sequence progression, and simple statistics. Tip: focus on speed arithmetic, fraction calculations, and solving systems of algebraic equations."),
            SubjectNote("uat_analy_n1", "uat_analytical", "UNIT 1", "Analytical Constraints and Logic Gaps", 
                "Analytical reasoning requires organizing sets of complex data with strict constraints (e.g., ordering people in seating grids, scheduling tasks sequentially). Learn to draw logic templates to parse variables matching specific clues."),
            SubjectNote("uat_eng_n1", "uat_english", "UNIT 1", "Addis Ababa University Test Structure", 
                "General English sections test structural grammar, vocabulary usage, reading comprehension, error correction, and paragraph organization. Crucial tips include managing time properly and recognizing transition markers.")
        )
    }

    private fun getFreshmanQuestions(): List<ExamQuestion> {
        return listOf(
            ExamQuestion("fn_q_phy1", "freshman_physics", "Which of the following is a vector quantity in General Physics?", "Temperature", "Mass", "Velocity", "Distance", "C", "Velocity has both magnitude and direction, making it a vector quantity, unlike distance or temperature."),
            ExamQuestion("fn_q_phy2", "freshman_physics", "According to Newton's Second Law of Motion, force is equal to:", "Mass multiplied by acceleration (F = ma)", "Mass divided by velocity", "Work divided by time", "Energy multiplied by distance", "A", "Newton's Second Law defines force as the product of mass and acceleration (F = ma)."),
            ExamQuestion("fn_q_phy3", "freshman_physics", "What principle states that any fluid pressure applied to an enclosed system is transmitted undiminished?", "Bernoulli's Principle", "Pascal's Principle", "Archimedes' Principle", "Hooke's Law", "B", "Pascal's principle states that pressure changes applied to an enclosed fluid are transmitted equally throughout."),
            ExamQuestion("fn_q1", "freshman_english_1", "Identify the passive form of: 'The developer compiled the applet safely.'", "The applet was safely compiled by the developer.", "The applet is safely compiling the developer.", "The developer was safely compiling the applet.", "The applet safely compiled the developer.", "A", "Passive voice puts the objective item ('The applet') at the subject position, followed by past participle of auxiliary verb ('was compiled')."),
            ExamQuestion("fn_q2", "freshman_emerging_tech", "Which emerging technology acts as a secure, decentralized, and immutable ledger?", "Internet of Things", "Cloud Server", "Blockchain Technology", "Artificial Intelligence", "C", "A blockchain uses cryptographically connected blocks to maintain a distributed ledger across peer networks safely."),
            ExamQuestion("fn_q3", "freshman_anthropology", "The evaluation of local cultures based on their own criteria rather than external comparison is called what?", "Ethnocentrism", "Cultural Relativism", "Cultural Adaptation", "Modern Assimilation", "B", "Cultural Relativism argues that beliefs and values should be appreciated based on the culture's own internal logic."),
            ExamQuestion("fn_q4", "freshman_economics", "What economic term represents the phenomenon where human wants exceed the available resources?", "Inflation", "Scarcity", "Equilibrium", "Oversupply", "B", "Scarcity is the fundamental economic problem where resources are limited but human demands are infinite."),
            ExamQuestion("fn_q5", "freshman_entrepreneurship", "What is the crucial first step in the entrepreneurial process before mobilizing capital?", "Launching the IPO", "Assessing market profits", "Identifying a market opportunity or gap", "Hiring administrative staff", "C", "Identifying a feasible opportunity or market gap is the creative spark that begins any entrepreneurial journey."),
            ExamQuestion("fn_q6", "freshman_civics", "A system stating that all citizens are subject to constitutional laws regardless of their status is called:", "Rule of Law", "Unitary Hegemony", "Autocratic Sovereignty", "Anarchy", "A", "Rule of Law guarantees that laws are applied equally and fairly to all individuals without select exception."),
            ExamQuestion("fn_q7", "freshman_global_trends", "The rapid integration of world economies, communications, and social processes is called:", "Nationalization", "Insulation", "Globalization", "Decentralization", "C", "Globalization describes the interconnected flow of ideas, commerce, trade, and culture across geographic boundaries.")
        )
    }

    private fun getSemester2Questions(): List<ExamQuestion> {
        return listOf(
            ExamQuestion("fn_q8", "freshman_physical_fitness", "Which fitness component refers to the ability of a muscle to exert high force repeatedly?", "Muscular Strength", "Muscular Endurance", "Flexibility", "Agility", "B", "Muscular endurance tracks repetitive physical force over duration, while strength represents maximum singular output."),
            ExamQuestion("fn_q9", "freshman_history", "The historical victory of Ethiopian forces against Italian invaders in 1896 took place in which location?", "Gondar", "Axum", "Adwa", "Mekelle", "C", "The Battle of Adwa in 1896 secured Ethiopian sovereignty and became a globally recognized symbol of freedom."),
            ExamQuestion("fn_q10", "freshman_critical_thinking", "What logical fallacy claims an argument is wrong because the speaker's personal traits are attacked?", "Straw Man", "Ad Hominem", "Slippery Slope", "Circular Logic", "B", "Ad Hominem literally means 'to the man', where a speaker attacks personal integrity instead of addressing logic."),
            ExamQuestion("fn_q11", "freshman_geography", "Which of the following is the largest river basin in Ethiopia, flowing westward into Sudan?", "Awash Basin", "Abay (Nile) Basin", "Omo Gibe Basin", "Wabi Shebelle Basin", "B", "The Abay (Blue Nile) Basin is Ethiopia's largest river system by water volume and flows westward towards Sudan."),
            ExamQuestion("fn_q12", "freshman_inclusiveness", "What is the primary objective of implementing Inclusiveness policies in schools and workspace?", "Isolating children with learning difficulties", "Removing barriers to support equal participation for everyone", "Simplifying exams for top performers", "Eliminating tests entirely", "B", "Inclusiveness focuses on removing physical, social, and academic barriers to ensure equal access and belonging for all."),
            ExamQuestion("fn_q13", "freshman_psychology", "What learning behavior process is demonstrated when a biological response pairs with a neutral sound stimulus?", "Operant Conditioning", "Classical Conditioning", "Social Observation", "Trial-and-Error Learning", "B", "Ivan Pavlov demonstrated Classical Conditioning by pairing salivation with neutral auditory indicators like a metronome/bell."),
            ExamQuestion("fn_q14", "freshman_english_2", "Which section of an academic research paper details list of books, journals, and materials consulted?", "Introduction", "Methodology", "Bibliography or References", "Abstract", "C", "A Bibliography or References list provides bibliographic credits to all sources referenced throughout research."),
            ExamQuestion("fn_q15", "freshman_maths", "In set theory, what operation collects all unique elements belonging to both Set A and Set B?", "Intersection", "Difference", "Union", "Complement", "C", "The Union operation (A U B) aggregates every unique element present within either or both sets.")
        )
    }

    private fun getUatQuestions(): List<ExamQuestion> {
        return listOf(
            ExamQuestion("uat_q1", "uat_verbal", "Select the pair that completes: FLOWERS : BOUQUET :: ______ : ______", "Stars : Galaxy", "Trees : Desert", "Soldiers : Camp", "Students : University", "A", "A group of flowers constitutes a bouquet; similarly, a group of stars constitutes a galaxy."),
            ExamQuestion("uat_q2", "uat_quantitative", "If 20% of a certain number is equal to 45, what is 80% of that same number?", "90", "180", "135", "360", "B", "If 20% is 45, then 80% (which is 20% * 4) must be equal to 45 * 4 = 180."),
            ExamQuestion("uat_q3", "uat_analytical", "Five students (A, B, C, D, E) stand in line. A must stand ahead of B. C stands immediately after D. If D is third, where does E stand if A is second?", "First", "Fourth", "Fifth", "Cannot be determined", "C", "A stands ahead of B, and D is third with C fourth. Since A is second, B, C, and D are after A. So E stands first or fifth. In this arrangement, with A second, E stands first."),
            ExamQuestion("uat_q4", "uat_english", "Identify the synonym of the word 'Pernicious' as used in formal language contexts.", "Beneficial", "Harmful or destructive", "Unbelievable", "Intricate", "B", "Pernicious means having a harmful effect, especially in a gradual, passive, or subtle way.")
        )
    }

    private fun getFreshmanFlashcards(): List<Flashcard> {
        return listOf(
            // General Physics (Phys 1011)
            Flashcard("fn_fc_phy1", "freshman_physics", "Vector Product", "Cross product of two vectors yielding a orthogonal vector. Chapter 1.", false, false, "Chapter 1"),
            Flashcard("fn_fc_phy2", "freshman_physics", "Newton's Second Law", "Force equals mass times acceleration (F = ma). Chapter 2.", false, false, "Chapter 2"),
            Flashcard("fn_fc_phy3", "freshman_physics", "Pascal's Principle", "Pressure applied to an enclosed fluid is transmitted undiminished. Chapter 3.", false, false, "Chapter 3"),
            Flashcard("fn_fc_phy4", "freshman_physics", "Coulomb's Law", "Electrostatic force between charges is proportional to charge product over distance squared. Chapter 4.", false, false, "Chapter 4"),

            // Communicative English I
            Flashcard("fn_fc1", "freshman_english_1", "Active Voice", "When the subject performs the action. Example: 'The team won.' Chapter 1.", false, false, "Chapter 1"),
            Flashcard("fn_fc1_c2", "freshman_english_1", "Passive Voice", "Action recipient occupies the subject position. Chapter 2.", false, false, "Chapter 2"),
            Flashcard("fn_fc1_c3", "freshman_english_1", "Skimming", "Reading quickly to identify overall thesis and main points. Chapter 3.", false, false, "Chapter 3"),
            Flashcard("fn_fc1_c4", "freshman_english_1", "Discourse Marker", "Words connecting paragraphs smoothly like 'Furthermore' or 'However'. Chapter 4.", false, false, "Chapter 4"),

            // Emerging Tech
            Flashcard("fn_fc2", "freshman_emerging_tech", "IoT (Internet of Things)", "Connected sensors sharing data automatically. Chapter 1.", false, false, "Chapter 1"),
            Flashcard("fn_fc2_c2", "freshman_emerging_tech", "Big Data 3Vs", "Volume, Velocity, and Variety defining modern data streams. Chapter 2.", false, false, "Chapter 2"),
            Flashcard("fn_fc2_c3", "freshman_emerging_tech", "Blockchain", "Decentralized immutable ledger using cryptographic blocks. Chapter 3.", false, false, "Chapter 3"),
            Flashcard("fn_fc2_c4", "freshman_emerging_tech", "Cyber Ethics", "Moral guidelines governing AI, surveillance, and data security. Chapter 4.", false, false, "Chapter 4"),

            // Anthropology
            Flashcard("fn_fc3", "freshman_anthropology", "Cultural Relativism", "Evaluating cultures by their own context rather than external standards. Chapter 1.", false, false, "Chapter 1"),
            Flashcard("fn_fc3_c2", "freshman_anthropology", "Ethnocentrism", "Judging another culture using one's own cultural norms as superior. Chapter 2.", false, false, "Chapter 2"),
            Flashcard("fn_fc3_c3", "freshman_anthropology", "Dinkinesh (Lucy)", "Australopithecus afarensis fossil discovered in Hadar, Ethiopia. Chapter 3.", false, false, "Chapter 3"),
            Flashcard("fn_fc3_c4", "freshman_anthropology", "Patrilineal Descent", "Tracing lineage and inheritance through the paternal father's line. Chapter 4.", false, false, "Chapter 4"),

            // Economics
            Flashcard("fn_fc4", "freshman_economics", "Macroeconomics", "Study of aggregate economy: GDP, inflation, and employment. Chapter 1.", false, false, "Chapter 1"),
            Flashcard("fn_fc4_c2", "freshman_economics", "Law of Demand", "Price and quantity demanded move in opposite direction. Chapter 2.", false, false, "Chapter 2"),
            Flashcard("fn_fc4_c3", "freshman_economics", "Marginal Cost", "Additional cost of producing one more unit of output. Chapter 3.", false, false, "Chapter 3"),
            Flashcard("fn_fc4_c4", "freshman_economics", "Fiscal Policy", "Government expenditure and taxation used to stabilize economy. Chapter 4.", false, false, "Chapter 4"),

            // Entrepreneurship
            Flashcard("fn_fc5", "freshman_entrepreneurship", "Value Proposition", "Unique benefit delivered to solve a customer pain point. Chapter 1.", false, false, "Chapter 1"),
            Flashcard("fn_fc5_c2", "freshman_entrepreneurship", "Feasibility Study", "Evaluating operational, financial, and market viability of an idea. Chapter 2.", false, false, "Chapter 2"),
            Flashcard("fn_fc5_c3", "freshman_entrepreneurship", "Break-Even Point", "Sales volume where total revenue equals total operating cost. Chapter 3.", false, false, "Chapter 3"),
            Flashcard("fn_fc5_c4", "freshman_entrepreneurship", "Target Market", "Specific consumer segment targeted for product marketing. Chapter 4.", false, false, "Chapter 4"),

            // Moral & Civics
            Flashcard("fn_fc6", "freshman_civics", "Rule of Law", "Laws apply equally and fairly to all citizens without exception. Chapter 1.", false, false, "Chapter 1"),
            Flashcard("fn_fc6_c2", "freshman_civics", "Federalism", "System dividing power between central federal and state governments. Chapter 2.", false, false, "Chapter 2"),
            Flashcard("fn_fc6_c3", "freshman_civics", "Civic Responsibility", "Duties of active citizens including voting and tax compliance. Chapter 3.", false, false, "Chapter 3"),
            Flashcard("fn_fc6_c4", "freshman_civics", "Peace Building", "Resolving root causes of conflict to maintain social cohesion. Chapter 4.", false, false, "Chapter 4"),

            // Global Trends
            Flashcard("fn_fc7", "freshman_global_trends", "Globalization", "Interconnected flow of commerce, technology, and culture worldwide. Chapter 1.", false, false, "Chapter 1"),
            Flashcard("fn_fc7_c2", "freshman_global_trends", "Diplomacy", "Conducting official negotiations between sovereign nations. Chapter 2.", false, false, "Chapter 2"),
            Flashcard("fn_fc7_c3", "freshman_global_trends", "Tariffs", "Taxes imposed on imported goods to regulate international trade. Chapter 3.", false, false, "Chapter 3"),
            Flashcard("fn_fc7_c4", "freshman_global_trends", "United Nations", "Global intergovernmental organization maintaining peace and security. Chapter 4.", false, false, "Chapter 4"),

            // Physical Fitness
            Flashcard("fn_fc8", "freshman_physical_fitness", "Muscular Endurance", "Ability of muscle to perform repeated force contractions. Chapter 1.", false, false, "Chapter 1"),
            Flashcard("fn_fc8_c2", "freshman_physical_fitness", "Agility", "Ability to rapidly change direction with control and balance. Chapter 2.", false, false, "Chapter 2"),
            Flashcard("fn_fc8_c3", "freshman_physical_fitness", "Caloric Deficit", "Consuming fewer calories than burned, driving weight reduction. Chapter 3.", false, false, "Chapter 3"),
            Flashcard("fn_fc8_c4", "freshman_physical_fitness", "FITT Principle", "Frequency, Intensity, Time, and Type of exercise planning. Chapter 4.", false, false, "Chapter 4"),

            // History
            Flashcard("fn_fc9", "freshman_history", "Battle of Adwa", "1896 victory securing Ethiopian national independence against Italy. Chapter 1.", false, false, "Chapter 1"),
            Flashcard("fn_fc9_c2", "freshman_history", "Aksumite Empire", "Ancient maritime trading empire in northern Ethiopia and Horn. Chapter 2.", false, false, "Chapter 2"),
            Flashcard("fn_fc9_c3", "freshman_history", "Zagwe Dynasty", "Medieval Ethiopian dynasty known for Lalibela rock-hewn churches. Chapter 3.", false, false, "Chapter 3"),
            Flashcard("fn_fc9_c4", "freshman_history", "OAU Founding", "Organization of African Unity established in Addis Ababa in 1963. Chapter 4.", false, false, "Chapter 4"),

            // Critical Thinking
            Flashcard("fn_fc10", "freshman_critical_thinking", "Deductive Logic", "Argument where true premises guarantee a true conclusion. Chapter 1.", false, false, "Chapter 1"),
            Flashcard("fn_fc10_c2", "freshman_critical_thinking", "Inductive Logic", "Argument offering probable support rather than absolute certainty. Chapter 2.", false, false, "Chapter 2"),
            Flashcard("fn_fc10_c3", "freshman_critical_thinking", "Ad Hominem", "Attacking speaker's personal character instead of their argument. Chapter 3.", false, false, "Chapter 3"),
            Flashcard("fn_fc10_c4", "freshman_critical_thinking", "Venn Diagram", "Overlapping circle diagrams testing validity of categorical syllogisms. Chapter 4.", false, false, "Chapter 4"),

            // Geography
            Flashcard("fn_fc11", "freshman_geography", "Weyna Dega", "Warm temperate climate zone optimal for Ethiopian agriculture. Chapter 1.", false, false, "Chapter 1"),
            Flashcard("fn_fc11_c2", "freshman_geography", "Abay Basin", "Blue Nile river basin flowing westward from Lake Tana towards Sudan. Chapter 2.", false, false, "Chapter 2"),
            Flashcard("fn_fc11_c3", "freshman_geography", "Terracing", "Farming steps constructed on mountain slopes to prevent soil erosion. Chapter 3.", false, false, "Chapter 3"),
            Flashcard("fn_fc11_c4", "freshman_geography", "Urbanization", "Process of population shift from rural areas to urban centers. Chapter 4.", false, false, "Chapter 4"),

            // Inclusiveness
            Flashcard("fn_fc12", "freshman_inclusiveness", "Accessibility", "Facilities and designs ensuring equal access for persons with disabilities. Chapter 1.", false, false, "Chapter 1"),
            Flashcard("fn_fc12_c2", "freshman_inclusiveness", "Sensory Impairment", "Visual or hearing functional limitations requiring adaptive tools. Chapter 2.", false, false, "Chapter 2"),
            Flashcard("fn_fc12_c3", "freshman_inclusiveness", "Braille System", "Tactile reading system using raised dots for visually impaired learners. Chapter 3.", false, false, "Chapter 3"),
            Flashcard("fn_fc12_c4", "freshman_inclusiveness", "Reasonable Accommodation", "Necessary modifications ensuring equal rights in schools and workplaces. Chapter 4.", false, false, "Chapter 4"),

            // Psychology
            Flashcard("fn_fc13", "freshman_psychology", "Classical Conditioning", "Associating biological response with neutral stimulus (Pavlov). Chapter 1.", false, false, "Chapter 1"),
            Flashcard("fn_fc13_c2", "freshman_psychology", "Operant Conditioning", "Learning behavior through rewards and punishments (Skinner). Chapter 2.", false, false, "Chapter 2"),
            Flashcard("fn_fc13_c3", "freshman_psychology", "Short-Term Memory", "Temporary storage holding around 7 items for 20-30 seconds. Chapter 3.", false, false, "Chapter 3"),
            Flashcard("fn_fc13_c4", "freshman_psychology", "Coping Mechanism", "Strategies used to manage stress and emotional challenges. Chapter 4.", false, false, "Chapter 4"),

            // Communicative English II
            Flashcard("fn_fc14", "freshman_english_2", "APA Citation", "American Psychological Association citation style for academic papers. Chapter 1.", false, false, "Chapter 1"),
            Flashcard("fn_fc14_c2", "freshman_english_2", "Thesis Statement", "Central claim or position statement of an academic essay. Chapter 2.", false, false, "Chapter 2"),
            Flashcard("fn_fc14_c3", "freshman_english_2", "Literature Review", "Critical summary and synthesis of published academic research. Chapter 3.", false, false, "Chapter 3"),
            Flashcard("fn_fc14_c4", "freshman_english_2", "Plagiarism", "Using someone else's work or ideas without proper citation. Chapter 4.", false, false, "Chapter 4"),

            // Social Math
            Flashcard("fn_fc15", "freshman_maths", "Matrix Determinant", "Scalar value calculated from a square matrix representing scale factor. Chapter 1.", false, false, "Chapter 1"),
            Flashcard("fn_fc15_c2", "freshman_maths", "Truth Table", "Logical table displaying truth values for propositional statements. Chapter 2.", false, false, "Chapter 2"),
            Flashcard("fn_fc15_c3", "freshman_maths", "Linear Programming", "Optimization method maximizing profit subject to linear constraints. Chapter 3.", false, false, "Chapter 3"),
            Flashcard("fn_fc15_c4", "freshman_maths", "Derivative", "Rate of change measure used in marginal profit and cost analysis. Chapter 4.", false, false, "Chapter 4"),

            // AAU UAT Prep
            Flashcard("uat_fc1", "uat_verbal", "Analogy", "Cognitive comparison identifying patterns between pairs of terms.", false, false),
            Flashcard("uat_fc2", "uat_quantitative", "Sequence Series", "Mathematical progressions following sequential logic.", false, false),
            Flashcard("uat_fc3", "uat_analytical", "Logic Deductions", "Applying constraint grids sequentially to parse valid orders.", false, false),
            Flashcard("uat_fc4", "uat_english", "Synonym Pernicious", "Destructive, harmful, causing insidious damage over time.", false, false)
        )
    }

    private fun getEueeNotes(): List<SubjectNote> {
        return listOf(
            // BIOLOGY (Grades 9-12)
            SubjectNote("euee_bio_g9_n1", "euee_nat_biology", "Grade 9 - Unit 1", "Introduction to Biology & Cell Structure", 
                "Grade 9 Biology focuses on the basics of biological science, microscope usage, and eukaryotic cell structure.\n\n" +
                "• Eukaryotic Cell Organelles: Nucleus, Mitochondria, Ribosomes, Endoplasmic Reticulum, and Chloroplasts.\n" +
                "• Key Concept: Cell theory states that all living organisms are composed of cells and all cells come from pre-existing cells.", "Grade 9"),
            SubjectNote("euee_bio_g10_n1", "euee_nat_biology", "Grade 10 - Unit 2", "Human Biology & Nutrition", 
                "Grade 10 Biology covers human digestive, circulatory, and respiratory systems.\n\n" +
                "• Digestive Enzymes: Amylase (carbohydrates), Pepsin/Trypsin (proteins), Lipase (fats).\n" +
                "• Cardiovascular System: The 4-chambered human heart pumps deoxygenated blood to lungs and oxygenated blood to tissues.", "Grade 10"),
            SubjectNote("euee_bio_g11_n1", "euee_nat_biology", "Grade 11 - Unit 3", "Biomolecules & Enzyme Kinetics", 
                "Grade 11 Biology dives deep into biological chemistry, enzymes, and cellular respiration.\n\n" +
                "• Enzyme Function: Enzymes act as biological catalysts lowering activation energy.\n" +
                "• Cellular Respiration: Glycolysis (cytosol) ➔ Krebs Cycle (mitochondrial matrix) ➔ ETC (inner membrane), producing ~36-38 ATP.", "Grade 11"),
            SubjectNote("euee_bio_g12_n1", "euee_nat_biology", "Grade 12 - Unit 4", "Genetics, Evolution & Biotechnology", 
                "Grade 12 Biology covers molecular genetics, DNA replication, protein synthesis, and evolutionary theories.\n\n" +
                "• DNA Structure: Double helix with adenine-thymine (A-T) and guanine-cytosine (G-C) base pairs.\n" +
                "• Central Dogma: DNA ➔ (Transcription) ➔ mRNA ➔ (Translation) ➔ Functional Protein.", "Grade 12"),

            // CHEMISTRY (Grades 9-12)
            SubjectNote("euee_chem_g9_n1", "euee_nat_chemistry", "Grade 9 - Unit 1", "Structure of Matter & Periodic Table", 
                "Grade 9 Chemistry covers atomic structure, subatomic particles (protons, neutrons, electrons), and periodic trends.\n\n" +
                "• Atomic Number (Z): Number of protons in nucleus.\n" +
                "• Electronegativity: Increases across a period from left to right and decreases down a group.", "Grade 9"),
            SubjectNote("euee_chem_g10_n1", "euee_nat_chemistry", "Grade 10 - Unit 2", "Chemical Bonding & Stoichiometry", 
                "Grade 10 Chemistry introduces ionic, covalent, and metallic bonding, as well as mole calculations.\n\n" +
                "• Avogadro's Number: 1 mole = 6.022 x 10²³ particles.\n" +
                "• Stoichiometric Ratios: Balanced chemical equations dictate molar ratios between reactants and products.", "Grade 10"),
            SubjectNote("euee_chem_g11_n1", "euee_nat_chemistry", "Grade 11 - Unit 3", "Chemical Equilibrium & Reaction Kinetics", 
                "Grade 11 Chemistry studies reaction rates, rate laws, and Le Chatelier's Principle.\n\n" +
                "• Le Chatelier's Principle: When a system at equilibrium is disturbed, it shifts in a direction that minimizes the disturbance.\n" +
                "• Equilibrium Constant (Kc): Ratio of product concentrations to reactant concentrations at equilibrium.", "Grade 11"),
            SubjectNote("euee_chem_g12_n1", "euee_nat_chemistry", "Grade 12 - Unit 4", "Organic Chemistry & Polymers", 
                "Grade 12 Chemistry covers hydrocarbons, functional groups, and polymerization.\n\n" +
                "• Functional Groups: Alcohols (-OH), Carboxylic Acids (-COOH), Esters (-COO-), Amines (-NH₂).\n" +
                "• Polymerization: Addition vs Condensation polymerization (e.g., Nylon, Polyester, Polyethylene).", "Grade 12"),

            // PHYSICS (Grades 9-12)
            SubjectNote("euee_phy_g9_n1", "euee_nat_physics", "Grade 9 - Unit 1", "Physical Quantities & Vectors", 
                "Grade 9 Physics covers fundamental scalar and vector quantities, 1D motion, and velocity-time graphs.\n\n" +
                "• Scalars vs Vectors: Scalars have magnitude only (mass, time); Vectors have magnitude and direction (displacement, velocity, force).\n" +
                "• Kinematics Formula: v = u + at, s = ut + ½at².", "Grade 9"),
            SubjectNote("euee_phy_g10_n1", "euee_nat_physics", "Grade 10 - Unit 2", "Work, Energy & Work-Energy Theorem", 
                "Grade 10 Physics studies mechanical work, kinetic/potential energy, and conservation laws.\n\n" +
                "• Work: W = F * d * cos(θ).\n" +
                "• Conservation of Energy: Total mechanical energy (E = KE + PE) remains constant in an isolated system without friction.", "Grade 10"),
            SubjectNote("euee_phy_g11_n1", "euee_nat_physics", "Grade 11 - Unit 3", "Rotational Dynamics & Fluid Mechanics", 
                "Grade 11 Physics covers torque, angular momentum, fluid dynamics, and Bernoulli's equation.\n\n" +
                "• Torque: T = r x F = I * α (where I is moment of inertia).\n" +
                "• Bernoulli's Principle: P + ½ρv² + ρgh = constant along a streamline.", "Grade 11"),
            SubjectNote("euee_phy_g12_n1", "euee_nat_physics", "Grade 12 - Unit 4", "Electrostatics, Circuits & Modern Physics", 
                "Grade 12 Physics covers Coulomb's law, electric potential, DC circuits, electromagnetic induction, and quantum photon energy.\n\n" +
                "• Ohm's Law: V = I * R.\n" +
                "• Photoelectric Effect: Photon energy E = h * f. Photoelectrons emitted when light frequency exceeds threshold frequency.", "Grade 12"),

            // MATHEMATICS (Natural & Social Streams - Grades 9-12)
            SubjectNote("euee_math_g9_n1", "euee_nat_maths", "Grade 9 - Unit 1", "Sets & Real Number Systems", 
                "Grade 9 Math covers set operations, Venn diagrams, exponents, and radical simplification.\n\n" +
                "• Set Operations: Union (A U B), Intersection (A ∩ B), Set Difference (A - B).\n" +
                "• Real Numbers: Rational vs Irrational numbers.", "Grade 9"),
            SubjectNote("euee_math_g10_n1", "euee_nat_maths", "Grade 10 - Unit 2", "Quadratic Equations & Trigonometry", 
                "Grade 10 Math studies quadratic equations, parabolas, and fundamental trigonometric ratios.\n\n" +
                "• Quadratic Formula: x = [-b ± √(b² - 4ac)] / (2a).\n" +
                "• Trigonometric Identity: sin²(θ) + cos²(θ) = 1.", "Grade 10"),
            SubjectNote("euee_math_g11_n1", "euee_nat_maths", "Grade 11 - Unit 3", "Sequences, Series & Logarithms", 
                "Grade 11 Math covers arithmetic and geometric progressions, exponential and logarithmic functions.\n\n" +
                "• Arithmetic Term: An = A1 + (n - 1)d\n" +
                "• Geometric Infinite Sum: S = A1 / (1 - r) for |r| < 1.\n" +
                "• Logarithm Rule: log_b(x * y) = log_b(x) + log_b(y).", "Grade 11"),
            SubjectNote("euee_math_g12_n1", "euee_nat_maths", "Grade 12 - Unit 4", "Limits, Derivatives & Integrals", 
                "Grade 12 Math introduces calculus, limit evaluation, derivative rules, and definite integrals.\n\n" +
                "• Power Rule for Differentiation: d/dx(xⁿ) = n * xⁿ⁻¹.\n" +
                "• Fundamental Theorem of Calculus: ∫_a^b f(x)dx = F(b) - F(a).", "Grade 12"),

            // Duplicate math notes for social stream
            SubjectNote("euee_soc_math_g9_n1", "euee_soc_maths", "Grade 9 - Unit 1", "Sets & Logic Statements", 
                "Grade 9 Social Math focuses on Venn diagrams, set algebra, and basic logical statements.", "Grade 9"),
            SubjectNote("euee_soc_math_g10_n1", "euee_soc_maths", "Grade 10 - Unit 2", "Linear Functions & Matrices", 
                "Grade 10 Social Math covers linear equations, graphing lines, and matrix operations.", "Grade 10"),
            SubjectNote("euee_soc_math_g11_n1", "euee_soc_maths", "Grade 11 - Unit 3", "Probability & Statistics", 
                "Grade 11 Social Math studies permutations, combinations, mean, median, standard deviation, and normal distribution.", "Grade 11"),
            SubjectNote("euee_soc_math_g12_n1", "euee_soc_maths", "Grade 12 - Unit 4", "Financial Math & Calculus Basics", 
                "Grade 12 Social Math covers compound interest formulas, simple derivatives, and optimization in business models.", "Grade 12"),

            // ENGLISH (Grades 9-12)
            SubjectNote("euee_eng_g9_n1", "euee_nat_english", "Grade 9 - Unit 1", "Basic Grammar & Sentence Types", 
                "Grade 9 English covers parts of speech, subject-verb agreement, and simple/compound/complex sentence structures.", "Grade 9"),
            SubjectNote("euee_eng_g10_n1", "euee_nat_english", "Grade 10 - Unit 2", "Active/Passive Voice & Tenses", 
                "Grade 10 English focuses on active to passive voice conversions, present perfect, and past continuous tenses.", "Grade 10"),
            SubjectNote("euee_eng_g11_n1", "euee_nat_english", "Grade 11 - Unit 3", "Conditionals & Reported Speech", 
                "Grade 11 English covers Zero, 1st, 2nd, and 3rd Conditionals, as well as direct to indirect speech rules.", "Grade 11"),
            SubjectNote("euee_eng_g12_n1", "euee_nat_english", "Grade 12 - Unit 4", "Cohesive Devices & Discourse Markers", 
                "Grade 12 English focuses on advanced vocabulary in context, discourse connectors (e.g., Furthermore, Nevertheless, On the contrary), and passage thesis identification.", "Grade 12"),

            // Social English duplicates
            SubjectNote("euee_soc_eng_g9_n1", "euee_soc_english", "Grade 9 - Unit 1", "Grammar & Reading Fundamentals", "Basic grammar, vocabulary, reading strategies.", "Grade 9"),
            SubjectNote("euee_soc_eng_g10_n1", "euee_soc_english", "Grade 10 - Unit 2", "Passive Voice & Punctuation", "Passive transformations and correct comma/colon rules.", "Grade 10"),
            SubjectNote("euee_soc_eng_g11_n1", "euee_soc_english", "Grade 11 - Unit 3", "Conditional Clauses & Phrasal Verbs", "Complex conditional structures and idiomatic phrasal verbs.", "Grade 11"),
            SubjectNote("euee_soc_eng_g12_n1", "euee_soc_english", "Grade 12 - Unit 4", "Advanced Reading Comprehension", "Speed reading, context clues, discourse flags for national exam success.", "Grade 12"),

            // HISTORY (Grades 9-12)
            SubjectNote("euee_hist_g9_n1", "euee_soc_history", "Grade 9 - Unit 1", "Ancient Civilizations & Cradle of Humankind", 
                "Grade 9 History explores early human origins in the Great Rift Valley (Fossil Lucy/Dinkinesh) and ancient world river valley civilizations.", "Grade 9"),
            SubjectNote("euee_hist_g10_n1", "euee_soc_history", "Grade 10 - Unit 2", "The Aksumite Empire & Zagwe Dynasty", 
                "Grade 10 History covers the rise and trade dominance of Aksum, coinage, King Ezana's conversion (330 AD), and the rock-hewn churches of Lalibela under the Zagwe dynasty.", "Grade 10"),
            SubjectNote("euee_hist_g11_n1", "euee_soc_history", "Grade 11 - Unit 3", "Medieval Ethiopia & 19th Century Unification", 
                "Grade 11 History covers the Solomonic restoration, Gondarine period, Zemene Mesafint (Era of Princes), and Emperor Tewodros II's centralizing reforms.", "Grade 11"),
            SubjectNote("euee_hist_g12_n1", "euee_soc_history", "Grade 12 - Unit 4", "Battle of Adwa & 20th Century Pan-Africanism", 
                "Grade 12 History covers Emperor Menelik II, the iconic 1896 Battle of Adwa victory against Italian colonialism, Emperor Haile Selassie I, OAU founding, and 20th-century geopolitical developments.", "Grade 12"),

            // GEOGRAPHY (Grades 9-12)
            SubjectNote("euee_geo_g9_n1", "euee_soc_geography", "Grade 9 - Unit 1", "Map Reading & Fundamentals of Geography", 
                "Grade 9 Geography covers map scale calculations, contour line interpretation, latitude, longitude, and physical geography.", "Grade 9"),
            SubjectNote("euee_geo_g10_n1", "euee_soc_geography", "Grade 10 - Unit 2", "Geological Structure & Topography of Ethiopia", 
                "Grade 10 Geography covers the Rift Valley formation, Ethiopian highlands vs lowlands, soil types, and natural vegetation zones.", "Grade 10"),
            SubjectNote("euee_geo_g11_n1", "euee_soc_geography", "Grade 11 - Unit 3", "Climate Zones & Drainage Systems", 
                "Grade 11 Geography covers climate classification (Dega, Weyna Dega, Kolla, Berha), rainfall distribution, and trans-boundary river basins (Abay/Nile, Awash, Omo Gibe).", "Grade 11"),
            SubjectNote("euee_geo_g12_n1", "euee_soc_geography", "Grade 12 - Unit 4", "Population Geography & Economic Activities", 
                "Grade 12 Geography covers demographic trends, fertility/mortality rates, urbanization, subsistence vs commercial agriculture, and manufacturing industries in East Africa.", "Grade 12"),

            // ECONOMICS (Grades 9-12)
            SubjectNote("euee_econ_g9_n1", "euee_soc_economics", "Grade 9 - Unit 1", "Basic Concepts of Economics & Scarcity", 
                "Grade 9 Economics introduces scarcity, choice, opportunity cost, and the Production Possibility Curve (PPC).", "Grade 9"),
            SubjectNote("euee_econ_g10_n1", "euee_soc_economics", "Grade 10 - Unit 2", "Demand, Supply & Market Equilibrium", 
                "Grade 10 Economics covers the Law of Demand, Law of Supply, price determination, and shift vs movement along demand curves.", "Grade 10"),
            SubjectNote("euee_econ_g11_n1", "euee_soc_economics", "Grade 11 - Unit 3", "Microeconomics & Theory of Production", 
                "Grade 11 Economics studies price elasticity of demand/supply, total/marginal utility, cost functions (TC, MC, ATC), and market structures (perfect competition, monopoly).", "Grade 11"),
            SubjectNote("euee_econ_g12_n1", "euee_soc_economics", "Grade 12 - Unit 4", "Macroeconomics, GDP & Monetary Policy", 
                "Grade 12 Economics covers Gross Domestic Product (GDP), inflation measurement, unemployment types, fiscal policy, and Central Bank monetary controls.", "Grade 12"),

            // SAT / Aptitude (Exempted from Grade breakdown, classified as "General")
            SubjectNote("euee_nat_apt_n1", "euee_nat_aptitude", "General Unit 1", "Spatial & Letter Pattern Logic", 
                "Aptitude (SAT) tests logical series, matrix puzzles, visual cube rotations, critical reasoning, and advanced verbal analogy patterns.", "General"),
            SubjectNote("euee_soc_apt_n1", "euee_soc_aptitude", "General Unit 1", "Data Interpretation & Logical Reasoning", 
                "Analyzing logic matrices, reading chart details, processing statement arguments, and calculating compound percentages rapidly.", "General")
        )
    }

    private fun getEueeQuestions(): List<ExamQuestion> {
        return listOf(
            ExamQuestion("e_q1", "euee_nat_maths", "Find the sum of the infinite geometric series: 2 + 1 + 1/2 + 1/4 + ...", "3", "4", "5", "Infinite", "B", "The first term A1 = 2, and the ratio r = 1/2. The sum is S = A1 / (1 - r) = 2 / (1 - 0.5) = 4."),
            ExamQuestion("e_q2", "euee_nat_english", "Choose the word that has the same prefix meaning of 'Multicultural':", "Subzero", "Unilateral", "Polytechnic", "Bilingual", "C", "The prefix 'Multi-' and 'Poly-' both represent multiple or many entities."),
            ExamQuestion("e_q3", "euee_nat_aptitude", "Find the next item in the alphabet sequence: Z, W, T, Q, ?", "O", "N", "P", "M", "B", "The pattern subtracts three steps backwards in the English alphabet: Z(26) -> W(23) -> T(20) -> Q(17) -> N(14)."),
            ExamQuestion("e_q4", "euee_nat_physics", "Torque is mathematically defined as the vector cross-product of force and which of the following variables?", "Mass", "Momentum", "Position vector (radius)", "Acceleration", "C", "Torque is T = r x F, representing the position vector (lever arm) multiplied vectorially by applied force."),
            
            ExamQuestion("e_q5", "euee_soc_history", "Which Aksumite monarch declared Christianity as the official state policy in 330 AD?", "King Ezana", "King Kaleb", "King Armah", "King Lalibela", "A", "King Ezana adopted Christianity in 330 AD, minting coinage displaying Christian cross symbols instead of crescent discs."),
            ExamQuestion("e_q6", "euee_soc_geography", "Which river system serves as the largest water basin in Ethiopia flowing westwards towards Sudan?", "Awash Basin", "Abay (Blue Nile)", "Omo Gibe", "Baro Basin", "B", "The Abay (Blue Nile) Basin drains an extensive portion of the central highlands and flows westward to Sudan."),
            ExamQuestion("e_q7", "euee_soc_economics", "What economic phenomenon occurs when the quantity supplied of a product exceeds its current demand in the market?", "Scarcity", "Market Equilibrium", "Market Surplus", "Inflation", "C", "A market surplus is established when supply is larger than current consumer demand, putting downward pressure on prices.")
        )
    }

    private fun getEueeFlashcards(): List<Flashcard> {
        return listOf(
            // BIOLOGY FLASHCARDS (Grades 9-12)
            Flashcard("e_fc_bio_g9", "euee_nat_biology", "Mitochondria Function", "The powerhouse of the cell; produces ATP through aerobic cellular respiration.", false, false, "Grade 9"),
            Flashcard("e_fc_bio_g10", "euee_nat_biology", "Hemoglobin", "Iron-containing protein in red blood cells that transports oxygen from lungs to tissues.", false, false, "Grade 10"),
            Flashcard("e_fc_bio_g11", "euee_nat_biology", "Activation Energy", "The minimum initial energy required to start a chemical or biological reaction.", false, false, "Grade 11"),
            Flashcard("e_fc_bio_g12", "euee_nat_biology", "Transcription vs Translation", "Transcription makes mRNA from DNA template in nucleus; Translation builds protein from mRNA on ribosome in cytoplasm.", false, false, "Grade 12"),

            // CHEMISTRY FLASHCARDS (Grades 9-12)
            Flashcard("e_fc_chem_g9", "euee_nat_chemistry", "Electronegativity Trend", "Increases across a period (left to right) and decreases down a group in the Periodic Table.", false, false, "Grade 9"),
            Flashcard("e_fc_chem_g10", "euee_nat_chemistry", "Avogadro's Constant", "6.022 x 10²³ particles per mole of substance.", false, false, "Grade 10"),
            Flashcard("e_fc_chem_g11", "euee_nat_chemistry", "Le Chatelier's Principle", "System at equilibrium shifts to counteract any applied external stress (temperature, pressure, concentration).", false, false, "Grade 11"),
            Flashcard("e_fc_chem_g12", "euee_nat_chemistry", "Functional Group: Ester", "Contains -COO- linkage; responsible for pleasant fruity smells and flavorings.", false, false, "Grade 12"),

            // PHYSICS FLASHCARDS (Grades 9-12)
            Flashcard("e_fc_phy_g9", "euee_nat_physics", "Vector Quantity", "Physical quantity that possesses both magnitude and direction (e.g., Velocity, Acceleration, Force).", false, false, "Grade 9"),
            Flashcard("e_fc_phy_g10", "euee_nat_physics", "Work-Energy Theorem", "The net work done on an object equals the change in its kinetic energy (W_net = ΔKE).", false, false, "Grade 10"),
            Flashcard("e_fc_phy_g11", "euee_nat_physics", "Torque Formula", "Rotational force equivalent T = r x F = I * α.", false, false, "Grade 11"),
            Flashcard("e_fc_phy_g12", "euee_nat_physics", "Photoelectric Threshold", "Minimum light frequency required to eject photoelectrons from a metal surface.", false, false, "Grade 12"),

            // MATHEMATICS FLASHCARDS (Grades 9-12)
            Flashcard("e_fc_math_g9", "euee_nat_maths", "Set Union (A U B)", "Combines all unique elements present in Set A, Set B, or both.", false, false, "Grade 9"),
            Flashcard("e_fc_math_g10", "euee_nat_maths", "Quadratic Discriminant", "b² - 4ac. If > 0, 2 real roots; if = 0, 1 real root; if < 0, 2 complex roots.", false, false, "Grade 10"),
            Flashcard("e_fc_math_g11", "euee_nat_maths", "Infinite Geometric Sum", "S = A1 / (1 - r), valid only when ratio |r| < 1.", false, false, "Grade 11"),
            Flashcard("e_fc_math_g12", "euee_nat_maths", "Power Rule Derivative", "d/dx(xⁿ) = n * xⁿ⁻¹.", false, false, "Grade 12"),

            // SOCIAL MATHS DUPLICATES
            Flashcard("e_fc_soc_math_g9", "euee_soc_maths", "Set Union (A U B)", "Combines all unique elements in set A or set B.", false, false, "Grade 9"),
            Flashcard("e_fc_soc_math_g10", "euee_soc_maths", "Slope Formula", "m = (y₂ - y₁) / (x₂ - x₁).", false, false, "Grade 10"),
            Flashcard("e_fc_soc_math_g11", "euee_soc_maths", "Standard Deviation", "Measure of how spread out numbers are from their arithmetic mean.", false, false, "Grade 11"),
            Flashcard("e_fc_soc_math_g12", "euee_soc_maths", "Compound Interest Formula", "A = P(1 + r/n)^(nt).", false, false, "Grade 12"),

            // ENGLISH FLASHCARDS (Grades 9-12)
            Flashcard("e_fc_eng_g9", "euee_nat_english", "Subject-Verb Agreement", "Singular subjects require singular verbs; plural subjects require plural verbs.", false, false, "Grade 9"),
            Flashcard("e_fc_eng_g10", "euee_nat_english", "Passive Voice Rule", "Object + auxiliary verb (be) + past participle + (by + subject).", false, false, "Grade 10"),
            Flashcard("e_fc_eng_g11", "euee_nat_english", "Third Conditional", "If + past perfect, would + have + past participle (unreal past event).", false, false, "Grade 11"),
            Flashcard("e_fc_eng_g12", "euee_nat_english", "Discourse Marker: 'Nevertheless'", "Used to introduce a contrasting statement that is surprising after what was previously stated.", false, false, "Grade 12"),

            // SOCIAL ENGLISH DUPLICATES
            Flashcard("e_fc_soc_eng_g9", "euee_soc_english", "Parts of Speech", "Nouns, Verbs, Adjectives, Adverbs, Pronouns, Prepositions, Conjunctions, Interjections.", false, false, "Grade 9"),
            Flashcard("e_fc_soc_eng_g10", "euee_soc_english", "Active to Passive", "Focuses on the action's target rather than the action's doer.", false, false, "Grade 10"),
            Flashcard("e_fc_soc_eng_g11", "euee_soc_english", "Phrasal Verb: 'Carry out'", "To perform, execute, or complete a plan or experiment.", false, false, "Grade 11"),
            Flashcard("e_fc_soc_eng_g12", "euee_soc_english", "Inference in Reading", "Logical conclusion drawn from evidence and reasoning rather than explicit statement.", false, false, "Grade 12"),

            // HISTORY FLASHCARDS (Grades 9-12)
            Flashcard("e_fc_hist_g9", "euee_soc_history", "Fossil Lucy (Dinkinesh)", "Australopithecus afarensis fossil discovered in Hadar, Afar in 1974; dated ~3.2M years ago.", false, false, "Grade 9"),
            Flashcard("e_fc_hist_g10", "euee_soc_history", "King Ezana", "Aksumite monarch who declared Christianity state religion in 330 AD and minted Christian coins.", false, false, "Grade 10"),
            Flashcard("e_fc_hist_g11", "euee_soc_history", "Emperor Tewodros II", "19th-century Ethiopian emperor who initiated modern unification and centralization reforms at Mabdala.", false, false, "Grade 11"),
            Flashcard("e_fc_hist_g12", "euee_soc_history", "Battle of Adwa (1896)", "Decisive victory on March 1, 1896, where Ethiopian forces defeated Italian invaders to safeguard sovereignty.", false, false, "Grade 12"),

            // GEOGRAPHY FLASHCARDS (Grades 9-12)
            Flashcard("e_fc_geo_g9", "euee_soc_geography", "Contour Interval", "The difference in elevation between successive contour lines on a topographic map.", false, false, "Grade 9"),
            Flashcard("e_fc_geo_g10", "euee_soc_geography", "Great Rift Valley", "Major geological trench dividing Ethiopian highlands into North-Western and South-Eastern blocks.", false, false, "Grade 10"),
            Flashcard("e_fc_geo_g11", "euee_soc_geography", "Weyna Dega Zone", "Warm temperate climate zone (1,500 - 2,300m elevation) optimal for agriculture and human settlement.", false, false, "Grade 11"),
            Flashcard("e_fc_geo_g12", "euee_soc_geography", "Subsistence Agriculture", "Farming where crops and livestock are raised primarily for local family consumption rather than commercial export.", false, false, "Grade 12"),

            // ECONOMICS FLASHCARDS (Grades 9-12)
            Flashcard("e_fc_econ_g9", "euee_soc_economics", "Opportunity Cost", "The value of the next best alternative forgone when making a decision.", false, false, "Grade 9"),
            Flashcard("e_fc_econ_g10", "euee_soc_economics", "Law of Demand", "Ceteris paribus, as price increases, quantity demanded decreases.", false, false, "Grade 10"),
            Flashcard("e_fc_econ_g11", "euee_soc_economics", "Price Elasticity of Demand", "Measure of responsiveness of quantity demanded to a change in price.", false, false, "Grade 11"),
            Flashcard("e_fc_econ_g12", "euee_soc_economics", "Gross Domestic Product (GDP)", "Total monetary value of all finished goods and services produced within a country's borders in a specific time period.", false, false, "Grade 12"),

            // SAT Aptitude Flashcards (General)
            Flashcard("e_fc_sat_1", "euee_nat_aptitude", "Letter Series Pattern", "Recognize shift differences (e.g., skipping 2 or 3 letters backwards/forwards).", false, false, "General"),
            Flashcard("e_fc_sat_2", "euee_soc_aptitude", "Analogy Principle", "Identify exact structural relationship between given word pairs before matching options.", false, false, "General")
        )
    }

    private fun getDepartmentNotes(): List<SubjectNote> {
        return listOf(
            SubjectNote("dept_ds_note1", "dept_data_structures", "UNIT 1", "Asymmetric Time Complexity",
                "Understand time and space complexity with Big O notation. Analyze algorithms like QuickSort, MergeSort, and Binary Search. Study linear data structures (linked lists, stacks, queues) and non-linear types (graphs and binary trees)."),
            SubjectNote("dept_se_note1", "dept_software_engineering", "UNIT 1", "Agile Methodologies & UML",
                "Explores Software Development Life Cycles (SDLC), Agile and Scrum frameworks, system testing, requirements elicitation, architectural designs, and structural Unified Modeling Language (UML) diagrams."),
            SubjectNote("dept_db_note1", "dept_dbms", "UNIT 1", "Relational Database Algebra & SQL",
                "Relational models, database schema normalizations (1NF, 2NF, 3NF, BCNF), transaction handling with ACID requirements, indices, and writing optimized relational SQL query operations."),
            SubjectNote("dept_anat_note1", "dept_anatomy", "UNIT 1", "Cardiovascular Anatomy",
                "Detailed study of human organ systems. Explores the muscular wall structures, heart chambers, major systemic arteries, veins, pulmonary circuits, and capillary tissue exchanges."),
            SubjectNote("dept_acc_note1", "dept_accounting", "UNIT 1", "Double-Entry Bookkeeping",
                "Core accounting principles: debits and credits, journalizing business transactions, adjusting ledger balances, and drafting financial statements like Balance Sheets and Income Statements.")
        )
    }

    private fun getExitExamNotes(): List<SubjectNote> {
        return listOf(
            SubjectNote("exit_cs_note1", "exit_cs", "UNIT 1", "Software Engineering exit high-yield guide",
                "High-yield guide for computer science exit exam. Focus is on software engineering patterns, system design architectures, MVC design, client-server models, API design principles, and microservices."),
            SubjectNote("exit_mgmt_note1", "exit_mgmt", "UNIT 1", "Strategic Management & Leadership",
                "Top-tier concepts for business majors. Detailed study of SWOT analysis, Porter's Five Forces model, leadership styles, corporate financial models, and strategic resource allocation."),
            SubjectNote("exit_med_note1", "exit_med", "UNIT 1", "Clinical Public Health Interventions",
                "Crucial health exit exam topics. Clinical diagnostics, infectious disease epidemiology, maternal-child health protocols, community health indicators, and emergency medicine procedures."),
            SubjectNote("exit_law_note1", "exit_law", "UNIT 1", "Constitutional Law & Jurisprudence",
                "Major legal exit benchmarks. Focuses on Ethiopian constitutional history, civil penal codes, court systems, international human rights treaties, and contract law case study analyses.")
        )
    }

    private fun getDepartmentQuestions(): List<ExamQuestion> {
        return listOf(
            ExamQuestion("dept_q1", "dept_data_structures", "What is the average time complexity of searching for an element in a balanced Binary Search Tree (BST)?", "O(1)", "O(log n)", "O(n)", "O(n log n)", "B", "In a balanced BST, each comparison halves the search space, resulting in logarithmic search time."),
            ExamQuestion("dept_q2", "dept_software_engineering", "Which software lifecycle model emphasizes incremental, iterative development utilizing sprint planning sessions?", "Waterfall Model", "Agile-Scrum Framework", "V-Model", "Spiral Model", "B", "The Agile-Scrum framework centers development within fixed-duration sprints with continuous incremental feedback cycles."),
            ExamQuestion("dept_q3", "dept_dbms", "Which normal form requires first normal form (1NF) and that all non-prime attributes are fully functionally dependent on the primary key?", "Second Normal Form (2NF)", "Third Normal Form (3NF)", "Boyce-Codd Normal Form (BCNF)", "Fourth Normal Form (4NF)", "A", "2NF addresses partial dependencies, requiring that non-key attributes depend on the entire primary key, not a subset thereof."),
            ExamQuestion("dept_q4", "dept_anatomy", "Which valve prevents the backflow of oxygenated blood from the left ventricle into the left atrium during systole?", "Tricuspid Valve", "Pulmonary Valve", "Mitral (Bicuspid) Valve", "Aortic Valve", "C", "The mitral or bicuspid valve separates the left chambers and closes during ventricular systole to prevent atrial regurgitation."),
            ExamQuestion("dept_q5", "dept_accounting", "According to the fundamental accounting equation, Assets are always equal to what sum?", "Liabilities + Owner's Equity", "Revenues - Expenses", "Cash + Retained Earnings", "Capital - Drawings", "A", "Assets = Liabilities + Equity is the foundational balance sheet formula underlying double-entry bookkeeping systems.")
        )
    }

    private fun getExitExamQuestions(): List<ExamQuestion> {
        return listOf(
            ExamQuestion("exit_q1", "exit_cs", "Which architectural pattern separates an interactive application into three distinct components: data management, user interface, and routing control?", "Microservices", "Model-View-Controller (MVC)", "Peer-to-Peer (P2P)", "Monolithic Layered Architecture", "B", "The Model-View-Controller pattern modularizes business logic, UI, and event handling controls separately."),
            ExamQuestion("exit_q2", "exit_mgmt", "What strategic framework, formulated by Michael Porter, analyzes competitors based on forces such as buyer power, threat of new entrants, and substitution?", "SWOT Analysis", "Porter's Five Forces Model", "BCG Growth-Share Matrix", "Value Chain Analysis", "B", "Porter's Five Forces model assesses competitive intensity, attractiveness, and structural profitability within any industry."),
            ExamQuestion("exit_q3", "exit_med", "What is the primary medication class used to treat acute bacterial pneumonia infections in clinical and public health guidelines?", "Antivirals", "Antibiotics (e.g., Ceftriaxone, Amoxicillin)", "Antihistamines", "Corticosteroids", "B", "Infectious bacterial diseases like pneumonia require targeted bacterial eradication using standard antibiotics clinical pathways."),
            ExamQuestion("exit_q4", "exit_law", "Under Ethiopian Constitutional Law, which parliamentary assembly is legally empowered to resolve constitutional interpretation disputes?", "House of Peoples' Representatives", "Federal Supreme Court", "House of Federation", "Prime Minister's Cabinet", "C", "The House of Federation holds the supreme sovereignty and constitutional competence to interpret and resolve disputes in Ethiopia.")
        )
    }

    private fun getDepartmentFlashcards(): List<Flashcard> {
        return listOf(
            Flashcard("dept_fc1", "dept_data_structures", "Big O Notation", "A mathematical notation describing the limiting behavior of a function when the argument tends towards infinity.", false, false),
            Flashcard("dept_fc2", "dept_software_engineering", "Agile Product Backlog", "A prioritized inventory of functional user requirements, user stories, and enhancements managed by the Product Owner.", false, false),
            Flashcard("dept_fc3", "dept_dbms", "ACID Traits", "Atomicity, Consistency, Isolation, and Durability - properties that guarantee database transactions are processed reliably.", false, false)
        )
    }

    private fun getExitExamFlashcards(): List<Flashcard> {
        return listOf(
            Flashcard("exit_fc1", "exit_cs", "API (Application Programming Interface)", "A system of structural requirements permitting distinct applications to exchange data and services securely.", false, false),
            Flashcard("exit_fc2", "exit_mgmt", "SWOT Analysis", "A structured planning tool evaluating Strengths, Weaknesses, Opportunities, and Threats for an enterprise.", false, false),
            Flashcard("exit_fc3", "exit_med", "Epidemiology", "The clinical study of how frequently diseases occur and why they happen in distinct social populations.", false, false)
        )
    }
}
