# Software Requirements Specification (SRS)
## Project Name: Tinat Educational Platform
**Version:** 1.0.0  
**Date:** August 11, 2026  
**Document Status:** Approved / Final Draft  

---

## 1. Introduction

### 1.1 Purpose
This Software Requirements Specification (SRS) document provides a detailed overview of the functional, non-functional, system, and structural requirements for the **Tinat** Android Application. It serves as the primary reference for software developers, UI/UX designers, quality assurance engineers, and stakeholders involved in the development, testing, and maintenance of the product.

### 1.2 Scope
**Tinat** is an offline-first, multi-lingual, AI-enhanced educational ecosystem designed for Ethiopian primary, secondary, and higher-education students. The system delivers localized study content, practice exams, flashcards, AI-driven study tools (via Google Gemini API), gamified ranking leaderboards, local payment gateway instruction support (CBE, Telebirr at 300 ETB rate), and activation code verification systems.

### 1.3 Definitions, Acronyms, and Abbreviations
- **SRS:** Software Requirements Specification
- **MVP:** Minimum Viable Product
- **PRD:** Product Requirements Document
- **CBE:** Commercial Bank of Ethiopia
- **Telebirr:** Ethio Telecom Mobile Financial Service
- **ETB:** Ethiopian Birr
- **AI:** Artificial Intelligence (Google Gemini API)
- **UI/UX:** User Interface / User Experience
- **DAO:** Data Access Object
- **Room:** Android Jetpack SQLite Object Mapping Library

### 1.4 System Overview
- **Platform:** Native Android (Kotlin + Jetpack Compose)
- **Architecture:** MVVM (Model-View-ViewModel) + Clean Architecture Layering
- **Local Persistence:** Room Database + Shared Preferences
- **Localization:** Multi-language engine (English, Amharic [አማርኛ], Afaan Oromoo, Somali [Soomaali], Tigrinya [ትግርኛ])

---

## 2. Overall Description

### 2.1 Product Perspective
Tinat operates as a standalone native Android application capable of full offline usage for local flashcards, practice quizzes, and cached study materials, while providing online enhancements for AI-powered explanations, live rankings, and manual payment code verification.

### 2.2 User Classes and Characteristics
1. **Free Tier Student:** Accesses basic subject content, standard flashcards, local practice questions, and multi-language support.
2. **Premium Tier Student (300 ETB):** Accesses advanced AI tool suites (summarizers, problem solvers, instant practice generators), full exam libraries, and priority community leaderboard badges.
3. **Administrator / Support Team:** Verifies bank payments (CBE / Telebirr) via Telegram support channels and issues unique activation/redeem codes.

### 2.3 Operating Environment
- **Operating System:** Android 7.0 (API Level 24) or higher
- **UI Framework:** Jetpack Compose with Material Design 3 (M3)
- **Networking:** Ktor / Retrofit HTTP clients for REST endpoints and Gemini API calls
- **Memory / Storage:** Minimum 2GB RAM, 150MB free storage space

---

## 3. Functional Requirements

### 3.1 Student Onboarding & Personalization
- **FR-1.1:** The app shall allow new users to select their grade level, preferred learning focus, and primary language.
- **FR-1.2:** The system shall persist user onboarding state locally using Room/SharedPreferences so onboarding is only presented on first run or upon explicit reset.

### 3.2 Localization & Multi-Language Support
- **FR-2.1:** The app shall support seamless dynamic switching between 5 Ethiopian languages:
  1. English
  2. Amharic (አማርኛ)
  3. Afaan Oromoo
  4. Somali (Soomaali)
  5. Tigrinya (ትግርኛ)
- **FR-2.2:** All static UI labels, buttons, dialogs, payment instructions, and onboarding flows shall update instantly upon changing the language setting.

### 3.3 Study Tools & Content Playback
- **FR-3.1:** **Flashcards:** The system shall provide interactive flip-card study modules with progress tracking.
- **FR-3.2:** **Content Play View:** Interactive reading and question practice view for subjects and past national exams.
- **FR-3.3:** **AI Tool Suite (Gemini Integrated):**
  - Instant concept simplification and translation.
  - Step-by-step problem solver for STEM subjects.
  - Custom quiz generation based on user notes.

### 3.4 Monetization & Payment Verification
- **FR-4.1:** **Pricing Model:** Standard Premium Subscription set at **300 ETB**.
- **FR-4.2:** **Payment Channels:** Instructions and payment destination details for:
  - Commercial Bank of Ethiopia (CBE) - Account: `1000721803477`
  - Telebirr Mobile Transfer - Phone: `+251932176773`
- **FR-4.3:** **One-Tap Copy Action:** Provide explicit clipboard copy buttons with visual checkmark feedback for bank account numbers and phone numbers.
- **FR-4.4:** **Manual Verification Flow:**
  - Step 1: Users submit payment proof screenshot via dedicated Telegram link.
  - Step 2: Input unique activation/redeem code in the verification screen to unlock Premium features instantly.

### 3.5 Gamification & Leaderboards
- **FR-5.1:** Track student study streaks, completed quizzes, and points earned.
- **FR-5.2:** Rank screen displaying national and regional top performers.

---

## 4. External Interface Requirements

### 4.1 User Interfaces
- **Material Design 3 Styling:** Glassmorphism accents, high-contrast dark/light surface modes, accessible touch targets (min 48dp).
- **Navigation:** Bottom Navigation Bar + Navigation Rail for tablet support.

### 4.2 Software Interfaces
- **Google Gemini API REST Integration:** Server-side / Client-side key handling for natural language processing.
- **Android Clipboard Manager:** System clipboard access for copy operations.
- **Android Intent System:** UriHandler launch for external Telegram support handle.

---

## 5. Non-Functional Requirements

### 5.1 Performance Requirements
- **NFR-1.1:** App cold launch time shall be less than 2.0 seconds on mid-tier Android devices.
- **NFR-1.2:** Screen transition latency shall remain under 100 milliseconds.

### 5.2 Security & Data Privacy Requirements
- **NFR-2.1:** User activation codes shall be validated securely against locally encrypted hashes or server validation rules.
- **NFR-2.2:** API keys shall be injected via `BuildConfig` environment variables and kept out of public repositories.

### 5.3 Reliability & Availability
- **NFR-3.1:** Core features (reading notes, flashcard revision, stored quizzes) shall operate 100% offline without internet access.
- **NFR-3.2:** Graceful error handling and retry mechanisms shall be implemented for network requests (Gemini AI, code verification).

### 5.4 Usability & Accessibility
- **NFR-4.1:** Minimum touch target size of 48x48 dp for all interactive buttons.
- **NFR-4.2:** High-contrast text elements adhering to WCAG 2.1 AA accessibility guidelines.

---

## 6. System Architecture & Data Schema

### 6.1 Database Entities (Room SQLite)
- `UserEntity`: User profile, points, premium status, language setting.
- `FlashcardEntity`: Question, answer, difficulty, review timestamp.
- `QuizResultEntity`: Subject, score, date, total questions.
- `RedeemCodeEntity`: Code string, usage status, timestamp.

---

## 7. Compliance & Future Roadmap
- **Phase 1 (Completed):** MVP Core, Offline Quizzes, 5-Language Localizer, CBE/Telebirr Integration with One-Tap Clipboard Copy, 300 ETB Premium Plan.
- **Phase 2 (Planned):** Peer-to-peer offline note sharing (Wi-Fi Direct), automated OCR receipt scanner for instant activation.
