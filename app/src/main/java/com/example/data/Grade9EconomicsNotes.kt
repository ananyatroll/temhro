package com.example.data

object Grade9EconomicsNotes {

    fun getGrade9EconomicsNotes(): List<SubjectNote> {
        val notesList = mutableListOf<SubjectNote>()
        val subId = "euee_soc_economics"
        var idx = 1

        fun addNote(unit: String, title: String, content: String) {
            notesList.add(
                SubjectNote(
                    id = "g9_econ_note_${idx}",
                    subjectId = subId,
                    unit = unit,
                    title = title,
                    content = content,
                    gradeLevel = "Grade 9"
                )
            )
            idx++
        }

        addNote(
            "Unit 1 - Introducing Economics",
            "Meaning, Nature, and Branches of Economics (Sections 1.1 - 1.2)",
            """
            • Etymology: Derived from Greek *oikonomia* (management of household).
            • Definition: Social science studying efficient allocation of scarce resources to maximize fulfillment of unlimited human wants.
            • Major Branches:
              - Microeconomics: Focuses on individual decision-making units (households, firms, individual markets).
              - Macroeconomics: Studies economy-wide aggregates (GDP, inflation, unemployment, fiscal/monetary policies).
            • Methodology: Positive (facts, 'what is') vs. Normative (value judgments, 'what ought to be').
            """.trimIndent()
        )

        addNote(
            "Unit 2 - The Basic Economic Problems and Economic Systems",
            "Scarcity, Choice, Opportunity Cost, and Systems (Sections 2.1 - 2.3)",
            """
            • Fundamental Problem: Unlimited wants vs. limited resources (land, labor, capital, entrepreneurship).
            • Opportunity Cost: Value of the next best alternative foregone when a choice is made.
            • Central Questions: What to produce? How to produce? For whom to produce?
            • Economic Systems: Traditional, Command (centrally planned), Market (capitalism), and Mixed economies.
            """.trimIndent()
        )

        addNote(
            "Unit 3 - Economic Resources and Markets",
            "Factors of Production and Market Structures (Sections 3.1 - 3.2)",
            """
            • Factors of Production: Land (rent), Labor (wages), Capital (interest), Entrepreneurship (profit).
            • Market Structures: Perfect Competition, Monopoly, Monopolistic Competition, Oligopoly.
            """.trimIndent()
        )

        addNote(
            "Unit 4 - Demand, Supply, and Market Equilibrium",
            "Demand, Supply, and Price Determination (Sections 4.1 - 4.3)",
            """
            • Law of Demand: Price and quantity demanded inversely related (downward-sloping curve), ceteris paribus.
            • Law of Supply: Price and quantity supplied directly related (upward-sloping curve), ceteris paribus.
            • Equilibrium: Price where Qd = Qs, eliminating surpluses and shortages.
            """.trimIndent()
        )

        addNote(
            "Unit 5 - Production and Costs of Production",
            "Production Function and Costs (Sections 5.1 - 5.2)",
            """
            • Short-Run vs Long-Run: Short-run has at least one fixed input (Law of Diminishing Marginal Returns); Long-run all inputs variable.
            • Costs: Fixed Costs (FC), Variable Costs (VC), Total Cost (TC = FC + VC), Average Cost (AC = TC/Q), Marginal Cost (MC = ΔTC/ΔQ).
            """.trimIndent()
        )

        addNote(
            "Unit 6 - Money and Banking",
            "Functions of Money and Banking Systems (Sections 6.1 - 6.3)",
            """
            • Functions of Money: Medium of exchange, unit of account, store of value, standard of deferred payment.
            • Banking: Commercial banks and Central Bank (National Bank of Ethiopia) managing monetary policy and currency regulation.
            """.trimIndent()
        )

        addNote(
            "Unit 7 - Introduction to Macroeconomics",
            "GDP and Macroeconomic Indicators (Sections 7.1 - 7.3)",
            """
            • GDP: Total market value of all final goods and services produced within a country's borders in a given year.
            • Macroeconomic Goals & Problems: Growth, price stability (inflation control), full employment, balance of payments.
            """.trimIndent()
        )

        addNote(
            "Unit 8 - Basic Entrepreneurship",
            "Entrepreneurship, Innovation, and Business Creation (Sections 8.1 - 8.6)",
            """
            • Entrepreneur: An innovator who organizes resources, takes risks, and creates new commercial enterprises.
            • Key Traits: Risk tolerance, creativity, leadership, persistence, and financial planning.
            """.trimIndent()
        )

        return notesList
    }
}
