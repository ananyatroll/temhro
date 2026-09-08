package com.example.ui

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.*
import com.example.ui.api.ActivateRequest
import com.example.ui.api.TinatApiClient
import com.example.ui.tools.ai.AiProvider
import com.example.ui.tools.ai.DeviceCapabilityDetector
import com.example.ui.tools.ai.GeneratedFlashcard
import com.example.ui.tools.ai.GeneratedQuestion
import com.example.ui.tools.ai.LearningContext
import com.example.ui.tools.ai.StudentContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull

class StudyViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AppDatabase.getDatabase(application)
    private val repository: DataRepository = StudyRepository(database.educationDao())
    val studentToolsRepo = StudentToolsRepository(database.studentToolsDao())
    val aiProvider: AiProvider = DeviceCapabilityDetector.getProvider(application)
    private val sharedPrefs = application.getSharedPreferences("offline_study_local_cache", Context.MODE_PRIVATE)

    // Student Tools UI State
    val isStudentToolsOpen = MutableStateFlow(false)
    val activeToolsTab = MutableStateFlow("timer_tasks") // "calendar", "grades", "scanner", "timer_tasks"
    val toolsContextPrompt = MutableStateFlow<String?>(null)
    val activeLearningContext = MutableStateFlow<LearningContext?>(null)

    // Student Tools Reactive Flows
    val calendarEvents = studentToolsRepo.allCalendarEvents.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
    )
    val studyTasks = studentToolsRepo.allTasks.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
    )
    val studyGoals = studentToolsRepo.allGoals.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
    )
    val gradeCourses = studentToolsRepo.allGradeCourses.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
    )
    val gradeAssessments = studentToolsRepo.allAssessments.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
    )
    val studySessions = studentToolsRepo.allStudySessions.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
    )
    val scannedDocs = studentToolsRepo.allScannedDocuments.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
    )

    init {
        viewModelScope.launch {
            val currentEpochDay = System.currentTimeMillis() / (1000 * 60 * 60 * 24)
            studentToolsRepo.seedInitialDataIfEmpty(currentEpochDay)
        }
    }

    fun deriveActiveLearningContext(): LearningContext {
        val existing = activeLearningContext.value
        if (existing != null) return existing

        val sub = activeSubject.value
        val currentCourseName = sub?.name ?: ""
        val currentCourseId = sub?.id ?: ""

        return when {
            showNotesView.value -> {
                val currentNote = activeNotes.value.getOrNull(activeNoteIndex.value)
                LearningContext(
                    courseId = currentCourseId,
                    courseName = currentCourseName,
                    topicId = currentNote?.id ?: "",
                    topicName = currentNote?.title ?: currentCourseName,
                    contentId = currentNote?.id ?: "",
                    contentText = currentNote?.content ?: "",
                    contentType = "notes",
                    gradeLevel = currentNote?.gradeLevel ?: "Grade 9-12"
                )
            }
            showContentPlayView.value && activeExamMode.value == "practice" -> {
                val currentQ = activeQuestions.value.getOrNull(currentQuestionIndex.value)
                val options = listOfNotNull(
                    currentQ?.optionA?.let { "A) $it" },
                    currentQ?.optionB?.let { "B) $it" },
                    currentQ?.optionC?.let { "C) $it" },
                    currentQ?.optionD?.let { "D) $it" }
                )
                LearningContext(
                    courseId = currentCourseId,
                    courseName = currentCourseName,
                    topicId = currentQ?.id ?: "",
                    topicName = "${currentCourseName} Practice Question #${currentQuestionIndex.value + 1}",
                    contentId = currentQ?.id ?: "",
                    contentType = "practice",
                    question = currentQ?.questionText ?: "",
                    questionOptions = options,
                    correctAnswer = currentQ?.correctOption ?: "",
                    explanation = currentQ?.explanation ?: "",
                    gradeLevel = "Grade 9-12"
                )
            }
            currentTab.value == "flashcards" || showFlashcardSubjectDetailId.value != null -> {
                val currentCard = activeFlashcards.value.getOrNull(currentFlashcardIndex.value)
                LearningContext(
                    courseId = currentCourseId,
                    courseName = currentCourseName,
                    topicId = currentCard?.id ?: "",
                    topicName = "${currentCourseName} Flashcard #${currentFlashcardIndex.value + 1}",
                    contentId = currentCard?.id ?: "",
                    contentType = "flashcard",
                    flashcardFront = currentCard?.front ?: "",
                    flashcardBack = currentCard?.back ?: "",
                    gradeLevel = currentCard?.gradeLevel ?: "Grade 9-12"
                )
            }
            else -> {
                LearningContext(
                    courseId = currentCourseId,
                    courseName = currentCourseName,
                    topicName = currentCourseName,
                    contentType = "general"
                )
            }
        }
    }

    fun openStudentTools(tab: String = "timer_tasks", prompt: String? = null, context: LearningContext? = null) {
        activeToolsTab.value = tab
        toolsContextPrompt.value = prompt
        activeLearningContext.value = context ?: deriveActiveLearningContext()
        isStudentToolsOpen.value = true
    }

    fun setLearningContext(context: LearningContext) {
        activeLearningContext.value = context
    }

    fun updateActiveCourseInContext(newCourseName: String) {
        val current = activeLearningContext.value ?: deriveActiveLearningContext()
        val matchedSub = subjects.value.firstOrNull { it.name.equals(newCourseName, ignoreCase = true) }
        activeLearningContext.value = current.copy(
            courseName = newCourseName,
            courseId = matchedSub?.id ?: current.courseId,
            topicName = if (current.topicName == current.courseName || current.contentType == "general") newCourseName else current.topicName
        )
        if (matchedSub != null) {
            activeSubject.value = matchedSub
        }
    }

    fun closeStudentTools() {
        isStudentToolsOpen.value = false
        toolsContextPrompt.value = null
        activeLearningContext.value = null
    }

    fun addCalendarEvent(event: StudentCalendarEvent) {
        viewModelScope.launch {
            studentToolsRepo.insertCalendarEvent(event)
        }
    }

    fun toggleCalendarEventCompletion(id: String, completed: Boolean) {
        viewModelScope.launch {
            studentToolsRepo.updateCalendarEventCompletion(id, completed)
        }
    }

    fun deleteCalendarEvent(id: String) {
        viewModelScope.launch {
            studentToolsRepo.deleteCalendarEvent(id)
        }
    }

    fun addTask(task: StudyTask) {
        viewModelScope.launch {
            studentToolsRepo.insertTask(task)
        }
    }

    fun toggleTaskCompletion(id: String, completed: Boolean) {
        viewModelScope.launch {
            studentToolsRepo.updateTaskCompletion(id, completed)
        }
    }

    fun deleteTask(id: String) {
        viewModelScope.launch {
            studentToolsRepo.deleteTask(id)
        }
    }

    fun addGradeCourse(course: GradeCourse) {
        viewModelScope.launch {
            studentToolsRepo.insertGradeCourse(course)
        }
    }

    fun deleteGradeCourse(courseId: String) {
        viewModelScope.launch {
            studentToolsRepo.deleteGradeCourse(courseId)
        }
    }

    fun addGradeAssessment(assessment: GradeAssessment) {
        viewModelScope.launch {
            studentToolsRepo.insertAssessment(assessment)
        }
    }

    fun deleteGradeAssessment(assessmentId: String) {
        viewModelScope.launch {
            studentToolsRepo.deleteAssessment(assessmentId)
        }
    }

    fun logCompletedStudySession(subject: String, minutes: Int) {
        viewModelScope.launch {
            studentToolsRepo.logStudySession(subject, minutes)
            // Also increment weekly goal hours
            val goals = studyGoals.value
            val hourGoal = goals.firstOrNull { it.period == "Weekly" && it.targetHours > 0 }
            if (hourGoal != null) {
                val updatedHours = hourGoal.currentHours + (minutes / 60.0f)
                studentToolsRepo.updateGoalProgress(hourGoal.id, updatedHours, hourGoal.currentQuestions)
            }
        }
    }

    fun saveScannedDoc(title: String, pageCount: Int, text: String) {
        viewModelScope.launch {
            studentToolsRepo.saveScannedDocument(title, pageCount, text)
        }
    }

    fun deleteScannedDoc(id: String) {
        viewModelScope.launch {
            studentToolsRepo.deleteScannedDocument(id)
        }
    }

    fun saveGeneratedFlashcardsToDatabase(cards: List<GeneratedFlashcard>, subjectId: String) {
        viewModelScope.launch {
            val newCards = cards.map {
                Flashcard(
                    id = "gen_fc_" + System.currentTimeMillis() + "_" + (0..999).random(),
                    subjectId = subjectId,
                    front = it.front,
                    back = it.back,
                    isStarred = true,
                    isKnown = false,
                    gradeLevel = "General"
                )
            }
            repository.insertFlashcards(newCards)
        }
    }

    fun saveGeneratedNoteToDatabase(title: String, content: String, subjectId: String) {
        viewModelScope.launch {
            val newNote = SubjectNote(
                id = "gen_note_" + System.currentTimeMillis(),
                subjectId = subjectId,
                unit = "STUDENT NOTES",
                title = title,
                content = content,
                gradeLevel = "General"
            )
            repository.insertNotes(listOf(newNote))
        }
    }

    fun saveGeneratedQuestionsToDatabase(questions: List<GeneratedQuestion>, subjectId: String) {
        viewModelScope.launch {
            val newQuestions = questions.map {
                ExamQuestion(
                    id = "gen_q_" + System.currentTimeMillis() + "_" + (0..999).random(),
                    subjectId = subjectId,
                    questionText = it.questionText,
                    optionA = it.optionA,
                    optionB = it.optionB,
                    optionC = it.optionC,
                    optionD = it.optionD,
                    correctOption = it.correctOption,
                    explanation = it.explanation
                )
            }
            repository.insertQuestions(newQuestions)
        }
    }

    // Translation Language state flow ("en", "am", "om", "so", "ti")
    val currentLanguage = MutableStateFlow(sharedPrefs.getString("user_selected_lang", "en") ?: "en")

    // Dark/Light Theme state flow
    val isDarkTheme = MutableStateFlow(sharedPrefs.getBoolean("user_is_dark_theme", false))

    fun setLanguage(lang: String) {
        currentLanguage.value = lang
        sharedPrefs.edit().putString("user_selected_lang", lang).apply()
    }

    fun setDarkTheme(enabled: Boolean) {
        isDarkTheme.value = enabled
        sharedPrefs.edit().putBoolean("user_is_dark_theme", enabled).apply()
    }

    fun toggleDarkTheme() {
        setDarkTheme(!isDarkTheme.value)
    }

    // Study Modes: "department" (University Department) or "exit_exam" (EXIT EXAM)
    val activeMode: StateFlow<String> = repository.userProgress
        .map { progress ->
            val pkg = progress?.activePackageId
            if (pkg == "exit_exam" || pkg == "euee_natural" || pkg == "euee_social" || pkg == "euee") {
                "exit_exam"
            } else {
                "department"
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "department")

    // Paywall Dialog Flows
    val showFreeTrialPaywall = MutableStateFlow(false)
    val paywallPackageIdForUpgrade = MutableStateFlow<String?>("freshman")

    // UI state flows
    val userProgress: StateFlow<UserProgress> = repository.userProgress
        .map { it ?: UserProgress() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UserProgress())

    val subjects: StateFlow<List<StudySubject>> = combine(
        repository.subjects,
        userProgress
    ) { list, progress ->
        val pkg = progress.activePackageId ?: "euee_natural"
        val filtered = when {
            pkg == "euee_social" -> list.filter { it.packageId == "euee_social" }
            pkg == "euee_natural" -> list.filter { it.packageId == "euee_natural" }
            pkg == "euee" -> {
                val preferredStream = sharedPrefs.getString("user_euee_stream", "natural") ?: "natural"
                if (preferredStream == "social") {
                    list.filter { it.packageId == "euee_social" }
                } else {
                    list.filter { it.packageId == "euee_natural" }
                }
            }
            pkg.startsWith("euee_") -> list.filter { it.packageId == pkg }
            pkg == "freshman" || pkg.startsWith("freshman") -> list.filter { it.packageId == "freshman" }
            pkg == "aau_uat" || pkg.startsWith("uat") -> list.filter { it.packageId == "aau_uat" }
            pkg == "department" || pkg.startsWith("dept") -> list.filter { it.packageId == "department" }
            pkg == "exit_exam" || pkg.startsWith("exit") -> list.filter { it.packageId == "exit_exam" }
            pkg == "grade12" || pkg == "grade11" || pkg == "grade10" || pkg == "grade9" -> {
                val preferredStream = sharedPrefs.getString("user_euee_stream", "natural") ?: "natural"
                if (preferredStream == "social") {
                    list.filter { it.packageId == "euee_social" }
                } else {
                    list.filter { it.packageId == "euee_natural" }
                }
            }
            else -> list.filter { it.packageId == pkg }
        }
        if (filtered.isNotEmpty()) {
            filtered
        } else {
            // Intelligent fallback: Respect active package stream
            if (pkg == "euee_social") {
                list.filter { it.packageId == "euee_social" }
            } else {
                list.filter { it.packageId == "euee_natural" }.ifEmpty {
                    list.filter { it.packageId == "freshman" }.ifEmpty { list }
                }
            }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Navigation and screen management flows
    val currentTab = MutableStateFlow("home") // "home", "flashcards", "rank", "profile"
    val showMarketingPage = MutableStateFlow(false) // Toggle to show the full in-app Marketing/Landing screen

    // Tool package picker
    val searchQuery = MutableStateFlow("")
    val packageAccordionExpanded = MutableStateFlow(false)
    val enrollmentConfirmationPackage = MutableStateFlow<String?>(null) // Contains package ID like "euee", "grade12"

    // Subject operations
    val activeSubject = MutableStateFlow<StudySubject?>(null)
    val showStudyOptionsModal = MutableStateFlow(false)

    fun setActiveMode(mode: String) {
        // Active mode is now implicitly derived from activePackageId
    }

    // Custom Video Analyzer Flows
    val activeAnalyzedVideos = activeSubject.flatMapLatest { subject ->
        if (subject == null) flowOf(emptyList())
        else repository.getAnalyzedVideos(subject.id)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val isAnalyzingVideo = MutableStateFlow(false)
    val analysisError = MutableStateFlow<String?>(null)

    fun analyzeAndAddVideo(videoUrlOrTopic: String) {
        val subject = activeSubject.value ?: return
        val mode = activeMode.value
        viewModelScope.launch {
            isAnalyzingVideo.value = true
            analysisError.value = null
            try {
                val response = com.example.ui.api.GeminiHttpClient.analyzeVideo(videoUrlOrTopic, subject.name, mode)
                if (response != null) {
                    val title = response.optString("title", "Lecture Topic")
                    val duration = response.optString("duration", "15 mins")
                    val description = response.optString("description", "A custom video analyzed with AI.")
                    val conceptMapping = response.optString("conceptMapping", "")
                    val summary = response.optString("summary", "")
                    val keyTerms = response.optString("keyTerms", "")
                    val questionsJson = response.optJSONArray("questions")?.toString() ?: "[]"
                    
                    val id = "custom_" + System.currentTimeMillis()
                    val video = com.example.data.AnalyzedVideo(
                        id = id,
                        subjectId = subject.id,
                        videoUrl = videoUrlOrTopic,
                        title = title,
                        duration = duration,
                        description = description,
                        domainOrDepartment = if (mode == "exit_exam") "EXIT EXAM" else "University Department",
                        conceptMapping = conceptMapping,
                        summary = summary,
                        keyTerms = keyTerms,
                        questionsJson = questionsJson
                    )
                    repository.insertAnalyzedVideo(video)
                } else {
                    analysisError.value = "Unable to analyze link. Please check your network and Gemini API key in Secrets panel."
                }
            } catch (e: Exception) {
                analysisError.value = "Analysis failed: ${e.message}"
            } finally {
                isAnalyzingVideo.value = false
            }
        }
    }

    val selectedGradeFilter = MutableStateFlow("Grade 9") // "Grade 9", "Grade 10", "Grade 11", "Grade 12"

    // Notes reading view
    private val rawNotes = activeSubject.flatMapLatest { subject ->
        if (subject == null) flowOf(emptyList())
        else repository.getNotesBySubject(subject.id)
    }

    val activeNotes = combine(rawNotes, selectedGradeFilter) { notesList, grade ->
        val filtered = if (grade == "All") notesList
        else notesList.filter { it.gradeLevel.equals(grade, ignoreCase = true) || it.gradeLevel == "General" }
        filtered.sortedWith(
            compareBy<SubjectNote> { note ->
                val uMatch = Regex("""(?:Unit|Chapter)\s*(\d+)""", RegexOption.IGNORE_CASE).find(note.unit)
                val uNum = uMatch?.groupValues?.getOrNull(1)?.toIntOrNull()
                val secInTitle = Regex("""(?:Section|Sec\.?|Sections)\s*(\d+)\.(\d+)""", RegexOption.IGNORE_CASE).find(note.title)
                    ?: Regex("""\b(\d+)\.(\d+)\b""").find(note.title)
                val secInUnit = Regex("""(?:Section|Sec\.?|Sections)\s*(\d+)\.(\d+)""", RegexOption.IGNORE_CASE).find(note.unit)
                    ?: Regex("""\b(\d+)\.(\d+)\b""").find(note.unit)
                uNum ?: secInTitle?.groupValues?.getOrNull(1)?.toIntOrNull()
                    ?: secInUnit?.groupValues?.getOrNull(1)?.toIntOrNull() ?: 99
            }.thenBy { note ->
                val secMatch = Regex("""(?:Section|Sec\.?|Sections)\s*(\d+(?:\.\d+)?)""", RegexOption.IGNORE_CASE).find(note.title)
                    ?: Regex("""\b(\d+\.\d+)\b""").find(note.title)
                    ?: Regex("""(?:Section|Sec\.?|Sections)\s*(\d+(?:\.\d+)?)""", RegexOption.IGNORE_CASE).find(note.unit)
                    ?: Regex("""\b(\d+\.\d+)\b""").find(note.unit)
                secMatch?.groupValues?.getOrNull(1)?.toFloatOrNull() ?: 0f
            }
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val activeNoteIndex = MutableStateFlow(0)
    val showNotesView = MutableStateFlow(false)
    val showNotesTableOfContents = MutableStateFlow(false)
    val showVideosView = MutableStateFlow(false)

    // Last studied note tracking for "Continue where you left off"
    val lastStudiedNoteId = MutableStateFlow<String?>(sharedPrefs.getString("last_studied_note_id", null))
    val lastStudiedUnit = MutableStateFlow<String?>(sharedPrefs.getString("last_studied_unit", null))
    val lastStudiedTopic = MutableStateFlow<String?>(sharedPrefs.getString("last_studied_topic", null))
    val lastStudiedSubjectId = MutableStateFlow<String?>(sharedPrefs.getString("last_studied_subject_id", null))
    val lastStudiedGrade = MutableStateFlow<String?>(sharedPrefs.getString("last_studied_grade", null))

    // Smart Flashcards: Unit filtering and spaced review status
    val selectedFlashcardUnit = MutableStateFlow("All")
    val selectedExamUnit = MutableStateFlow("All")

    // Practice/Exam view - Shuffles questions freshly on every entry and re-entry
    val questionShuffleTrigger = MutableStateFlow(0)

    private val rawQuestions = combine(
        activeSubject,
        questionShuffleTrigger
    ) { subject, _ -> subject }
        .flatMapLatest { subject ->
            if (subject == null) flowOf(emptyList())
            else repository.getQuestionsBySubject(subject.id)
        }
        
    val activeQuestions: StateFlow<List<ExamQuestion>> = combine(rawQuestions, selectedExamUnit, questionShuffleTrigger) { questions, unit, trigger ->
        val filtered = if (unit == "All") questions else questions.filter { it.unit.equals(unit, ignoreCase = true) || it.unit.equals("All", ignoreCase = true) }
        if (trigger > 0) filtered.shuffled(java.util.Random(trigger.toLong())) else filtered
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val rawFlashcards = activeSubject.flatMapLatest { subject ->
        if (subject == null) flowOf(emptyList())
        else repository.getFlashcardsBySubject(subject.id)
    }

    val activeFlashcards = combine(rawFlashcards, selectedGradeFilter) { cardsList, grade ->
        if (grade == "All") cardsList
        else cardsList.filter { it.gradeLevel.equals(grade, ignoreCase = true) || it.gradeLevel == "General" }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val showFlashcardSubjectDetailId = MutableStateFlow<String?>(null) // subjectId of currently expanded flashcard details
    val currentFlashcardIndex = MutableStateFlow(0)
    val isFlashcardFlipped = MutableStateFlow(false)

    val cardMasteredSet = MutableStateFlow<Set<String>>(sharedPrefs.getStringSet("card_mastered_ids", emptySet()) ?: emptySet())
    val cardDifficultSet = MutableStateFlow<Set<String>>(sharedPrefs.getStringSet("card_difficult_ids", emptySet()) ?: emptySet())
    val cardReviewHistory = MutableStateFlow<Map<String, Int>>(emptyMap())

    // Practice / Exam Selection & Play
    val showModeSelectionModal = MutableStateFlow(false)
    val activeExamMode = MutableStateFlow<String?>(null) // "practice" or "exam"
    val showContentPlayView = MutableStateFlow(false) // Whether notes, questions, or practice is being actively read/taken
    val contentLoading = MutableStateFlow(false)

    val currentQuestionIndex = MutableStateFlow(0)
    val userSelectedAnswers = MutableStateFlow<Map<String, String>>(emptyMap()) // QuestionId -> Selected Option "A", "B"...
    val revealedAnswers = MutableStateFlow<Set<String>>(emptySet()) // Revealed question IDs (for Exams / Practice modes)

    // Countdown Timer logic for Exam Mode
    val isTimerActive = MutableStateFlow(false)
    val timerRemainingSeconds = MutableStateFlow(60) // 60 seconds for quick fun, or customizable
    private var timerJob: Job? = null

    // Score evaluation flow
    val showScoreResultModal = MutableStateFlow(false)
    val calculatedScorePercent = MutableStateFlow(0)
    val questionsCount = MutableStateFlow(0)
    val correctAnswersCount = MutableStateFlow(0)

    // Material usage & progress tracking flows
    val readNotesSet = MutableStateFlow<Set<String>>(sharedPrefs.getStringSet("read_notes_ids", emptySet()) ?: emptySet())
    val answeredQuestionsSet = MutableStateFlow<Set<String>>(sharedPrefs.getStringSet("answered_questions_ids", emptySet()) ?: emptySet())

    // Saved / Bookmarked items
    val savedNotesSet = MutableStateFlow<Set<String>>(sharedPrefs.getStringSet("saved_notes_ids", emptySet()) ?: emptySet())
    val savedFlashcardsSet = MutableStateFlow<Set<String>>(sharedPrefs.getStringSet("saved_flashcards_ids", emptySet()) ?: emptySet())
    val savedQuestionsSet = MutableStateFlow<Set<String>>(sharedPrefs.getStringSet("saved_questions_ids", emptySet()) ?: emptySet())

    val showSavedNotesModal = MutableStateFlow(false)
    val showSavedFlashcardsModal = MutableStateFlow(false)
    val showSavedQuestionsModal = MutableStateFlow(false)
    val showSavedMaterialPickerModal = MutableStateFlow(false)

    // All database items so that selecting "Saved Materials" displays EVERYTHING saved across all subjects
    val allDatabaseNotes: StateFlow<List<SubjectNote>> = repository.getAllNotes()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allDatabaseQuestions: StateFlow<List<ExamQuestion>> = repository.getAllQuestions()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allDatabaseFlashcards: StateFlow<List<Flashcard>> = repository.getAllFlashcards()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Completion particles trigger
    val showCompletionParticles = MutableStateFlow(false)

    fun triggerCompletionCelebration() {
        viewModelScope.launch {
            showCompletionParticles.value = true
            delay(3500)
            showCompletionParticles.value = false
        }
    }

    fun toggleSaveNote(noteId: String) {
        val current = savedNotesSet.value.toMutableSet()
        if (current.contains(noteId)) {
            current.remove(noteId)
        } else {
            current.add(noteId)
        }
        savedNotesSet.value = current
        sharedPrefs.edit().putStringSet("saved_notes_ids", current).apply()
    }

    fun toggleSaveFlashcard(cardId: String) {
        val current = savedFlashcardsSet.value.toMutableSet()
        if (current.contains(cardId)) {
            current.remove(cardId)
        } else {
            current.add(cardId)
        }
        savedFlashcardsSet.value = current
        sharedPrefs.edit().putStringSet("saved_flashcards_ids", current).apply()
    }

    fun toggleSaveQuestion(questionId: String) {
        val current = savedQuestionsSet.value.toMutableSet()
        if (current.contains(questionId)) {
            current.remove(questionId)
        } else {
            current.add(questionId)
        }
        savedQuestionsSet.value = current
        sharedPrefs.edit().putStringSet("saved_questions_ids", current).apply()
    }

    val subjectProgressMap: StateFlow<Map<String, Float>> = combine(
        combine(repository.getAllNotes(), repository.getAllFlashcards(), repository.getAllQuestions()) { n, f, q ->
            Triple(n, f, q)
        },
        combine(userProgress, readNotesSet, answeredQuestionsSet) { p, r, a ->
            Triple(p, r, a)
        }
    ) { (notes, flashcards, questions), (progress, readNotes, answeredQuestions) ->
        val completedSet = progress.completedSubjects.split(",").filter { it.isNotEmpty() }.toSet()
        val allSubjectIds = (notes.map { it.subjectId } + flashcards.map { it.subjectId } + questions.map { it.subjectId } + completedSet).toSet()

        val map = mutableMapOf<String, Float>()
        for (subjectId in allSubjectIds) {
            if (completedSet.contains(subjectId)) {
                map[subjectId] = 1.0f
                continue
            }

            val subjectNotes = notes.filter { it.subjectId == subjectId }
            val subjectCards = flashcards.filter { it.subjectId == subjectId }
            val subjectQuestions = questions.filter { it.subjectId == subjectId }

            val total = subjectNotes.size + subjectCards.size + subjectQuestions.size
            if (total == 0) {
                map[subjectId] = 0.0f
                continue
            }

            val usedNotes = subjectNotes.count { readNotes.contains(it.id) }
            val usedCards = subjectCards.count { it.isKnown }
            val usedQuestions = subjectQuestions.count { answeredQuestions.contains(it.id) }

            val used = usedNotes + usedCards + usedQuestions
            map[subjectId] = (used.toFloat() / total.toFloat()).coerceIn(0f, 1f)
        }
        map
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyMap())

    fun markNoteAsRead(noteId: String) {
        if (readNotesSet.value.contains(noteId)) return
        val updated = readNotesSet.value + noteId
        readNotesSet.value = updated
        sharedPrefs.edit().putStringSet("read_notes_ids", updated).apply()
    }

    fun recordAnsweredQuestion(questionId: String) {
        if (answeredQuestionsSet.value.contains(questionId)) return
        val updated = answeredQuestionsSet.value + questionId
        answeredQuestionsSet.value = updated
        sharedPrefs.edit().putStringSet("answered_questions_ids", updated).apply()
    }

    // Payment Verification State
    val isSplashChecking = MutableStateFlow(true)
    val showPaymentVerificationScreen = MutableStateFlow(false)
    val paymentTxnIdInput = MutableStateFlow("")
    val paymentSenderPhoneInput = MutableStateFlow("")
    val paymentScreenshotPathInput = MutableStateFlow("")

    // User editing state
    val profileUsernameEditInput = MutableStateFlow("Ananya")
    val isProfileEditing = MutableStateFlow(false)

    init {
        viewModelScope.launch(Dispatchers.IO) {
            // Seed database at startup if empty in background IO
            repository.seedDatabaseIfEmpty()
        }

        // Keep username input in sync with loaded profile
        viewModelScope.launch {
            userProgress.collect { progress ->
                profileUsernameEditInput.value = progress.username
            }
        }

        // Check entitlement on app launch if accessToken exists
        checkSavedEntitlementOnLaunch()
    }

    private fun checkSavedEntitlementOnLaunch() {
        val savedToken = sharedPrefs.getString("access_token", null)
        if (!savedToken.isNullOrEmpty()) {
            isSplashChecking.value = true
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    withTimeoutOrNull(3000L) {
                        val response = TinatApiClient.apiService.getEntitlement("Bearer $savedToken")
                        if (response.isSuccessful && response.body()?.success == true) {
                            val body = response.body()
                            val pkgId = body?.`package`?.id ?: body?.entitlement?.packageKey ?: "euee_natural"
                            val normalizedPkg = when {
                                pkgId == "euee" || pkgId == "euee_prep" || pkgId == "grade12" -> "euee_natural"
                                else -> pkgId
                            }
                            repository.approvePayment(packageId = normalizedPkg)
                        } else if (response.code() == 401 || response.code() == 404) {
                            // Clear invalid or revoked token
                            sharedPrefs.edit().remove("access_token").apply()
                        }
                    }
                } catch (e: Exception) {
                    // Offline or network glitch: retain offline functionality
                } finally {
                    isSplashChecking.value = false
                }
            }
        } else {
            isSplashChecking.value = false
        }
    }

    // Timer actions
    fun startTimer(durationSeconds: Int = 120) {
        timerJob?.cancel()
        timerRemainingSeconds.value = durationSeconds
        isTimerActive.value = true
        timerJob = viewModelScope.launch {
            while (isTimerActive.value && timerRemainingSeconds.value > 0) {
                delay(1000)
                timerRemainingSeconds.value -= 1
            }
            if (timerRemainingSeconds.value == 0 && isTimerActive.value) {
                // Auto advance to next question or finish exam
                val curIdx = currentQuestionIndex.value
                val total = activeQuestions.value.size
                if (curIdx < total - 1) {
                    currentQuestionIndex.value = curIdx + 1
                    startTimer(120)
                } else {
                    finishExam()
                }
            }
        }
    }

    fun resetQuestionTimer(durationSeconds: Int = 120) {
        if (activeExamMode.value == "exam") {
            startTimer(durationSeconds)
        }
    }

    fun togglePauseTimer() {
        if (isTimerActive.value) {
            isTimerActive.value = false
            timerJob?.cancel()
        } else {
            startTimer(timerRemainingSeconds.value)
        }
    }

    fun stopTimer() {
        isTimerActive.value = false
        timerJob?.cancel()
    }

    fun finishExam() {
        stopTimer()
        viewModelScope.launch {
            val questions = activeQuestions.value
            var correct = 0
            val answers = userSelectedAnswers.value
            questions.forEach { q ->
                if (answers[q.id] == q.correctOption) {
                    correct++
                }
            }
            correctAnswersCount.value = correct
            questionsCount.value = questions.size
            val pct = if (questions.isNotEmpty()) (correct * 100) / questions.size else 0
            calculatedScorePercent.value = pct

            repository.incrementExamScore(correct * 10)
            showScoreResultModal.value = true
        }
    }

    // Free Trial Limitation Checking Logic
    fun checkFreeTrialAccess(subject: StudySubject): Boolean {
        val progress = userProgress.value
        // If approved status, they have premium and full access
        if (progress.paymentStatus == "approved") {
            return true
        }

        if (isSubjectLocked(subject)) {
            val pkgId = subject.packageId
            // Block access and show custom upgrade premium dialogue!
            paywallPackageIdForUpgrade.value = if (pkgId.startsWith("euee")) "euee" else pkgId
            showFreeTrialPaywall.value = true
            return false
        }

        return true
    }

    fun isPackagePurchased(purchasedPkgString: String, targetPackageId: String): Boolean {
        if (purchasedPkgString.isEmpty()) return false
        val purchasedList = purchasedPkgString.split(",").map { it.trim() }.filter { it.isNotEmpty() }
        val normalizedTarget = if (targetPackageId == "euee") "euee_natural" else targetPackageId

        return purchasedList.any { purchased ->
            if (normalizedTarget.startsWith("euee") || targetPackageId == "euee") {
                purchased.startsWith("euee") || purchased == "euee"
            } else {
                purchased == normalizedTarget || purchased == targetPackageId
            }
        }
    }

    // Helper to check lock status for UI icons
    fun isSubjectLocked(subject: StudySubject): Boolean {
        val progress = userProgress.value
        val isApproved = progress.paymentStatus == "approved"

        val effectivePurchased = if (progress.purchasedPackageId.isNotEmpty()) {
            progress.purchasedPackageId
        } else if (isApproved) {
            progress.activePackageId ?: "euee_natural"
        } else {
            ""
        }

        val isPurchased = isPackagePurchased(effectivePurchased, subject.packageId)

        if (isApproved && isPurchased) {
            return false // Unlocked for purchased package
        }

        val id = subject.id
        // Free trial rules for each package: exactly 2 selected subjects are open, all others locked
        return when (subject.packageId) {
            "freshman" -> {
                // Communicative English I and Emerging Tech are open
                !(id == "freshman_english_1" || id == "freshman_emerging_tech")
            }
            "euee_natural" -> {
                // Mathematics and English are open
                !(id == "euee_nat_maths" || id == "euee_nat_english")
            }
            "euee_social" -> {
                // History and Geography are open
                !(id == "euee_soc_history" || id == "euee_soc_geography")
            }
            "aau_uat" -> {
                // Verbal Reasoning and Quantitative Reasoning are open
                !(id == "uat_verbal" || id == "uat_quantitative")
            }
            "department" -> {
                // Data Structures & Algorithms and Software Engineering are open
                !(id == "dept_data_structures" || id == "dept_software_engineering")
            }
            "exit_exam" -> {
                // Computer Science Exit Exam and Business Management Exit Exam are open
                !(id == "exit_cs" || id == "exit_mgmt")
            }
            else -> true // Unknown or unlisted packages are locked by default in free trial
        }
    }

    // Selection routines
    fun selectSubject(subject: StudySubject) {
        if (!checkFreeTrialAccess(subject)) {
            return
        }

        questionShuffleTrigger.value += 1
        activeSubject.value = subject
        showStudyOptionsModal.value = true
    }

    fun startNotes() {
        val sub = activeSubject.value ?: subjects.value.firstOrNull()
        if (sub == null) return
        if (isSubjectLocked(sub)) {
            showStudyOptionsModal.value = false
            paywallPackageIdForUpgrade.value = if (sub.packageId.startsWith("euee")) "euee" else sub.packageId
            showFreeTrialPaywall.value = true
            return
        }
        activeSubject.value = sub
        showStudyOptionsModal.value = false
        showNotesTableOfContents.value = true
    }

    fun openNoteUnit(index: Int) {
        activeNoteIndex.value = index.coerceAtLeast(0)
        showNotesTableOfContents.value = false
        showNotesView.value = true
    }

    fun recordNoteStudied(note: SubjectNote) {
        lastStudiedNoteId.value = note.id
        lastStudiedUnit.value = note.unit
        lastStudiedTopic.value = note.title
        lastStudiedSubjectId.value = note.subjectId
        lastStudiedGrade.value = note.gradeLevel
        sharedPrefs.edit()
            .putString("last_studied_note_id", note.id)
            .putString("last_studied_unit", note.unit)
            .putString("last_studied_topic", note.title)
            .putString("last_studied_subject_id", note.subjectId)
            .putString("last_studied_grade", note.gradeLevel)
            .apply()
    }

    fun markFlashcardMastered(cardId: String) {
        val wasMastered = cardMasteredSet.value.contains(cardId)
        val updatedMastered = if (wasMastered) {
            cardMasteredSet.value - cardId
        } else {
            cardMasteredSet.value + cardId
        }
        val diffUpdated = cardDifficultSet.value - cardId
        cardMasteredSet.value = updatedMastered
        cardDifficultSet.value = diffUpdated
        sharedPrefs.edit()
            .putStringSet("card_mastered_ids", updatedMastered)
            .putStringSet("card_difficult_ids", diffUpdated)
            .apply()
    }

    fun markFlashcardDifficult(cardId: String) {
        val wasDifficult = cardDifficultSet.value.contains(cardId)
        val updatedDifficult = if (wasDifficult) {
            cardDifficultSet.value - cardId
        } else {
            cardDifficultSet.value + cardId
        }
        val mastUpdated = cardMasteredSet.value - cardId
        cardDifficultSet.value = updatedDifficult
        cardMasteredSet.value = mastUpdated
        sharedPrefs.edit()
            .putStringSet("card_difficult_ids", updatedDifficult)
            .putStringSet("card_mastered_ids", mastUpdated)
            .apply()
    }

    fun navigateToSourceNote(subjectId: String, grade: String, unitSnippet: String) {
        viewModelScope.launch {
            val sub = subjects.value.find { it.id == subjectId } ?: activeSubject.value
            if (sub != null) {
                activeSubject.value = sub
                if (grade.isNotBlank()) {
                    selectedGradeFilter.value = grade
                }
                val notes = repository.getNotesBySubject(sub.id).firstOrNull() ?: emptyList()
                val targetIndex = notes.indexOfFirst {
                    it.unit.contains(unitSnippet, ignoreCase = true) ||
                    it.title.contains(unitSnippet, ignoreCase = true) ||
                    unitSnippet.contains(it.unit, ignoreCase = true)
                }.coerceAtLeast(0)
                activeNoteIndex.value = targetIndex
                showNotesTableOfContents.value = false
                showNotesView.value = true
            }
        }
    }

    fun initiateModeSelection(unitName: String = "All") {
        selectedExamUnit.value = unitName
        val sub = activeSubject.value ?: subjects.value.firstOrNull()
        if (sub == null) return
        if (isSubjectLocked(sub)) {
            showStudyOptionsModal.value = false
            paywallPackageIdForUpgrade.value = if (sub.packageId.startsWith("euee")) "euee" else sub.packageId
            showFreeTrialPaywall.value = true
            return
        }
        activeSubject.value = sub
        showStudyOptionsModal.value = false
        showModeSelectionModal.value = true
    }

        val showTextbookReader = MutableStateFlow(false)

    fun startTextbook() {
        showStudyOptionsModal.value = false
        showTextbookReader.value = true
    }
    fun startFlashcards() {
        val subject = activeSubject.value ?: subjects.value.firstOrNull()
        if (subject != null) {
            if (isSubjectLocked(subject)) {
                showStudyOptionsModal.value = false
                paywallPackageIdForUpgrade.value = if (subject.packageId.startsWith("euee")) "euee" else subject.packageId
                showFreeTrialPaywall.value = true
                return
            }
            showFlashcardSubjectDetailId.value = subject.id
            currentFlashcardIndex.value = 0
            isFlashcardFlipped.value = false
        }
        showStudyOptionsModal.value = false
        currentTab.value = "flashcards"
    }

    fun startSubjectMode(mode: String) { // "practice" or "exam"
        val sub = activeSubject.value
        if (sub != null && isSubjectLocked(sub)) {
            showModeSelectionModal.value = false
            paywallPackageIdForUpgrade.value = if (sub.packageId.startsWith("euee")) "euee" else sub.packageId
            showFreeTrialPaywall.value = true
            return
        }
        // Increment shuffle trigger so every entrance or re-entrance generates a freshly shuffled question set
        questionShuffleTrigger.value += 1
        activeExamMode.value = mode
        showModeSelectionModal.value = false
        currentQuestionIndex.value = 0
        userSelectedAnswers.value = emptyMap()
        revealedAnswers.value = emptySet()

        viewModelScope.launch {
            contentLoading.value = true
            delay(1200) // Rich loading indicator for chapters and questions
            contentLoading.value = false
            showContentPlayView.value = true

            if (mode == "exam") {
                startTimer(120) // 2 minutes countdown
            }
        }
    }

    fun resetPlayState() {
        stopTimer()
        // Increment shuffle trigger so closing and re-entering the exam/practice tab reshuffles questions
        questionShuffleTrigger.value += 1
        showContentPlayView.value = false
        showNotesView.value = false
        showNotesTableOfContents.value = false
        showVideosView.value = false
        showStudyOptionsModal.value = false
        showModeSelectionModal.value = false
        showScoreResultModal.value = false
        contentLoading.value = false
        activeExamMode.value = null
    }

    fun openFlashcardsForCurrentUnit(unitName: String) {
        val currentSub = activeSubject.value
        showNotesView.value = false
        showNotesTableOfContents.value = false
        showContentPlayView.value = false
        if (currentSub != null) {
            showFlashcardSubjectDetailId.value = currentSub.id
            selectedFlashcardUnit.value = unitName
            currentFlashcardIndex.value = 0
            isFlashcardFlipped.value = false
        }
        currentTab.value = "flashcards"
    }

    fun openSavedNote(note: SubjectNote) {
        val sub = subjects.value.find { it.id == note.subjectId } ?: activeSubject.value
        if (sub != null) {
            activeSubject.value = sub
            selectedGradeFilter.value = "All"
            viewModelScope.launch {
                val subNotes = repository.getNotesBySubject(sub.id).firstOrNull() ?: emptyList()
                val idx = subNotes.indexOfFirst { it.id == note.id }
                activeNoteIndex.value = if (idx >= 0) idx else 0
                showSavedNotesModal.value = false
                showSavedMaterialPickerModal.value = false
                showStudyOptionsModal.value = false
                showNotesView.value = true
            }
        }
    }

    fun startVideos() {
        showStudyOptionsModal.value = false
        viewModelScope.launch {
            contentLoading.value = true
            delay(800)
            contentLoading.value = false
            showVideosView.value = true
        }
    }

    fun selectAnswer(questionId: String, option: String) {
        val current = userSelectedAnswers.value.toMutableMap()
        current[questionId] = option
        userSelectedAnswers.value = current
        recordAnsweredQuestion(questionId)
    }

    fun revealAnswer(questionId: String) {
        val current = revealedAnswers.value.toMutableSet()
        current.add(questionId)
        revealedAnswers.value = current
    }

    fun enrollInPackage(packageId: String) {
        viewModelScope.launch {
            if (packageId == "euee_social") {
                sharedPrefs.edit().putString("user_euee_stream", "social").apply()
            } else if (packageId == "euee_natural") {
                sharedPrefs.edit().putString("user_euee_stream", "natural").apply()
            }
            repository.enrollPackage(packageId)
            enrollmentConfirmationPackage.value = null
        }
    }

    fun resetEnrollment() {
        viewModelScope.launch {
            repository.resetEnrollment()
            currentTab.value = "home"
            // Reset trial accessed subjects tracking
            sharedPrefs.edit().apply {
                remove("accessed_freshman_subjects")
                remove("accessed_euee_subjects")
                remove("accessed_uat_subjects")
                remove("accessed_trial_subjects")
                apply()
            }
        }
    }

    fun submitManualPayment(txnId: String, senderPhone: String, screenshotPath: String) {
        viewModelScope.launch {
            contentLoading.value = true
            delay(1000)
            repository.submitPaymentIntent(txnId, senderPhone, screenshotPath)
            contentLoading.value = false
            showPaymentVerificationScreen.value = false
        }
    }

    fun approvePaymentAction() {
        viewModelScope.launch {
            val current = userProgress.value
            val currentPkg = current.activePackageId ?: "euee_natural"
            val normalizedPkg = when {
                currentPkg == "euee" || currentPkg == "euee_prep" || currentPkg == "grade12" -> "euee_natural"
                else -> currentPkg
            }
            repository.approvePayment(packageId = normalizedPkg)
            isOnboardingCompleted.value = true
            showPaymentVerificationScreen.value = false
            showFreeTrialPaywall.value = false
            showStudyOptionsModal.value = false
            showContentPlayView.value = false
            currentTab.value = "home"
            contentLoading.value = false
            isSplashChecking.value = false
        }
    }

    fun rejectPaymentAction() {
        viewModelScope.launch {
            repository.rejectPayment()
        }
    }

    fun submitUsername() {
        viewModelScope.launch {
            val newName = profileUsernameEditInput.value.trim()
            repository.updateUsername(newName)
            studentName.value = newName
            sharedPrefs.edit().putString("student_name", newName).apply()
            isProfileEditing.value = false
        }
    }

    fun toggleCompleteSubject(subjectId: String) {
        viewModelScope.launch {
            repository.completeSubject(subjectId)
        }
    }

    fun toggleTelegramConnection() {
        viewModelScope.launch {
            val current = userProgress.value.telegramConnected
            repository.updateTelegramStatus(!current)
        }
    }

    fun toggleFlashcardStarred(cardId: String, isKnown: Boolean, isCurrentlyStarred: Boolean) {
        viewModelScope.launch {
            repository.updateFlashcard(cardId, isKnown = isKnown, isStarred = !isCurrentlyStarred)
        }
    }

    fun toggleFlashcardKnown(cardId: String, isCurrentlyKnown: Boolean, isStarred: Boolean) {
        viewModelScope.launch {
            repository.updateFlashcard(cardId, isKnown = !isCurrentlyKnown, isStarred = isStarred)
        }
    }

    val activationErrorMessage = MutableStateFlow<String?>(null)

    fun activatePremiumWithRedeemCode(phoneNumber: String, redeemCode: String, packageId: String): Boolean {
        activationErrorMessage.value = null
        val rawPhone = phoneNumber.trim()
        val code = redeemCode.trim()

        val cleanDigits = rawPhone.filter { it.isDigit() }
        if (cleanDigits.length !in 9..15) {
            activationErrorMessage.value = "Phone number must be between 9 and 15 digits."
            return false
        }
        if (code.isEmpty()) {
            activationErrorMessage.value = "Please enter your redeem code."
            return false
        }

        val phoneToSend = if (rawPhone.startsWith("+")) "+$cleanDigits" else if (cleanDigits.startsWith("251")) "+$cleanDigits" else cleanDigits

        viewModelScope.launch {
            contentLoading.value = true
            try {
                var responseSuccessful = false
                var returnedPkg: String? = null

                try {
                    val response = TinatApiClient.apiService.activatePurchase(
                        ActivateRequest(phone = phoneToSend, code = code)
                    )
                    if (response.isSuccessful && response.body()?.success == true) {
                        responseSuccessful = true
                        val body = response.body()
                        val token = body?.accessToken
                        if (!token.isNullOrEmpty()) {
                            sharedPrefs.edit().putString("access_token", token).apply()
                        }
                        returnedPkg = body?.`package`?.id
                    } else {
                        val errorBodyStr = response.errorBody()?.string()
                        val errorCode = TinatApiClient.parseErrorCode(errorBodyStr)
                        val userMsg = TinatApiClient.mapErrorCodeToUserMessage(errorCode, response.code())
                        activationErrorMessage.value = userMsg
                    }
                } catch (e: Exception) {
                    activationErrorMessage.value = "Network error. Please check your connection and try again."
                }

                if (responseSuccessful) {
                    val finalPkg = if (!returnedPkg.isNullOrEmpty()) {
                        returnedPkg!!
                    } else if (packageId.isNotEmpty()) {
                        packageId
                    } else {
                        "euee_natural"
                    }
                    val normalizedPkg = when {
                        finalPkg == "euee" || finalPkg == "euee_prep" || finalPkg == "grade12" -> "euee_natural"
                        else -> finalPkg
                    }

                    repository.approvePayment(packageId = normalizedPkg, username = phoneToSend)

                    isOnboardingCompleted.value = true
                    showPaymentVerificationScreen.value = false
                    showFreeTrialPaywall.value = false
                    showStudyOptionsModal.value = false
                    showModeSelectionModal.value = false
                    showNotesView.value = false
                    showVideosView.value = false
                    showContentPlayView.value = false
                    enrollmentConfirmationPackage.value = null
                    activeSubject.value = null
                    currentTab.value = "home"
                    activationErrorMessage.value = null
                    contentLoading.value = false
                    isSplashChecking.value = false
                }
            } catch (e: Exception) {
                activationErrorMessage.value = "An error occurred during activation."
            } finally {
                contentLoading.value = false
            }
        }
        return true
    }

    fun activatePremiumWithCredentials(username: String, password: String, packageId: String): Boolean {
        return activatePremiumWithRedeemCode(phoneNumber = username, redeemCode = password, packageId = packageId)
    }

    val studentName = MutableStateFlow(sharedPrefs.getString("student_name", "") ?: "")
    val studentEmail = MutableStateFlow(sharedPrefs.getString("student_email", "") ?: "")
    val studentClassLevel = MutableStateFlow(sharedPrefs.getString("student_class_level", "Grade 12 EUEE Prep") ?: "Grade 12 EUEE Prep")
    val studentGoal = MutableStateFlow(sharedPrefs.getString("student_goal", "") ?: "")
    val isOnboardingCompleted = MutableStateFlow(sharedPrefs.getBoolean("is_student_onboarded", false))

    val academicYear = MutableStateFlow(sharedPrefs.getString("academic_year", "") ?: "")
    val academicDepartment = MutableStateFlow(sharedPrefs.getString("academic_department", "") ?: "")
    val showDepartmentSetupModal = MutableStateFlow(false)

    fun saveDepartmentSetup(dept: String, year: String) {
        academicDepartment.value = dept
        academicYear.value = year
        sharedPrefs.edit()
            .putString("academic_department", dept)
            .putString("academic_year", year)
            .apply()
        showDepartmentSetupModal.value = false
    }

    fun saveStudentProfile(
        name: String,
        email: String = "",
        goal: String = "",
        language: String = "en",
        isDark: Boolean = false,
        classLevel: String = "Grade 12 EUEE Prep"
    ) {
        val trimmedName = name.trim()
        val trimmedEmail = email.trim()
        val trimmedGoal = goal.trim()
        val trimmedLevel = classLevel.trim()

        studentName.value = if (trimmedName.isNotEmpty()) trimmedName else "Ananya"
        studentEmail.value = trimmedEmail
        studentGoal.value = if (trimmedGoal.isNotEmpty()) trimmedGoal else "Score 500+ Matric Result"
        studentClassLevel.value = trimmedLevel

        setLanguage(language)
        setDarkTheme(isDark)

        isOnboardingCompleted.value = true

        sharedPrefs.edit()
            .putString("student_name", studentName.value)
            .putString("student_email", trimmedEmail)
            .putString("student_class_level", trimmedLevel)
            .putString("student_goal", studentGoal.value)
            .putBoolean("is_student_onboarded", true)
            .apply()

        viewModelScope.launch {
            repository.updateUsername(studentName.value)
            repository.resetEnrollment()
            isOnboardingCompleted.value = true
        }
    }

    fun restartOnboarding() {
        isOnboardingCompleted.value = false
        viewModelScope.launch {
            repository.resetEnrollment()
        }
    }
}
