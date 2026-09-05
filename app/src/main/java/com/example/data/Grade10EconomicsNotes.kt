package com.example.data

object Grade10EconomicsNotes {

    fun getGrade10EconomicsNotes(): List<SubjectNote> {
        val notesList = mutableListOf<SubjectNote>()
        val subId = "euee_soc_economics"
        var idx = 1

        fun addNote(unit: String, title: String, content: String) {
            notesList.add(
                SubjectNote(
                    id = "g10_econ_note_${idx}",
                    subjectId = subId,
                    unit = unit,
                    title = title,
                    content = content,
                    gradeLevel = "Grade 10"
                )
            )
            idx++
        }

        addNote(
            "Unit 1 - Theory of Consumer Behaviour",
            "Utility, Cardinal, and Ordinal Utility Theory (Sections 1.1 - 1.4)",
            """
            • Utility: Total satisfaction from consuming goods/services. Subjective and relative.
            • Cardinal Utility: Utility measured in 'utils'. Total Utility (TU) vs. Marginal Utility (MU = ΔTU/ΔQ).
            • Law of Diminishing Marginal Utility: As consumption of a good increases, marginal utility decreases.
            • Consumer Equilibrium (Cardinal): MU_x / P_x = MU_y / P_y subject to budget constraint.
            • Ordinal Utility: Indifference curves (combinations giving equal satisfaction, convex to origin, negative slope). Consumer optimum where budget line is tangent to highest indifference curve.
            """.trimIndent()
        )

        addNote(
            "Unit 2 - Demand and Supply Analysis",
            "Demand, Supply, Market Equilibrium, and Elasticity (Sections 2.1 - 2.4)",
            """
            • Law of Demand (inverse price-quantity) & Law of Supply (direct price-quantity).
            • Market Equilibrium: Qd = Qs. Price controls: Price Floor (above equilibrium, surplus) vs. Price Ceiling (below equilibrium, shortage).
            • Elasticity: Price Elasticity of Demand E_d = (%ΔQd) / (%ΔP). Elastic (>1), Inelastic (<1), Unitary (=1).
            """.trimIndent()
        )

        addNote(
            "Unit 3 - Theories of Production and Cost",
            "Production Function and Short/Long Run Costs (Sections 3.1 - 3.2)",
            """
            • Production Function: Q = f(L, K). Short-run has fixed inputs; Law of Diminishing Marginal Returns applies.
            • Costs: Total Fixed Cost (TFC), Total Variable Cost (TVC), Total Cost (TC = TFC + TVC), Average Cost (AC = TC/Q), Marginal Cost (MC = ΔTC/ΔQ).
            """.trimIndent()
        )

        addNote(
            "Unit 4 - Market Structure",
            "Market Structures Comparison (Sections 4.1 - 4.4)",
            """
            • Perfect Competition: Many buyers/sellers, homogeneous products, price takers, free entry/exit.
            • Monopoly: Single seller, unique product, price maker, high barriers to entry.
            • Monopolistic Competition: Many sellers, differentiated products, easy entry/exit.
            • Oligopoly: Few large interdependent firms, high entry barriers.
            """.trimIndent()
        )

        addNote(
            "Unit 5 - Banking and Finance",
            "Financial Intermediaries, Ethiopian Banking, and Equb/Eddir (Sections 5.1 - 5.7)",
            """
            • Banking: National Bank of Ethiopia (central bank) regulating money supply vs. Commercial banks.
            • History: Bank of Abyssinia (1905), modern e-banking, microfinance.
            • Indigenous Financial Institutions: Equb (rotating savings/credit) and Eddir (traditional social insurance).
            """.trimIndent()
        )

        addNote(
            "Unit 6 - Economic Growth",
            "Macroeconomic Variables, Measurement, and Business Cycles (Sections 6.1 - 6.5)",
            """
            • Economic Growth: Annual percentage increase in real GDP or real GDP per capita.
            • Sources of Growth: Capital accumulation, technology, human capital, labor productivity.
            • Business Cycle Phases: Expansion (peak), contraction (recession), trough, recovery.
            """.trimIndent()
        )

        addNote(
            "Unit 7 - The Ethiopian Economy",
            "GDP Components, Real vs. Nominal, and Sectoral Analysis (Sections 7.1 - 7.6)",
            """
            • GDP Expenditure Approach: GDP = C + I + G + (X - M). Real GDP adjusts for inflation.
            • Sectoral Analysis: Agriculture (backbone, employment, exports), Industry (manufacturing, construction, mining), Services (trade, transport, finance).
            """.trimIndent()
        )

        addNote(
            "Unit 8 - Business Startups and Innovation",
            "Startups, Feasibility, and Business Organizations (Sections 8.1 - 8.4)",
            """
            • Startups & Innovation: Commercializing novel ideas to solve local problems.
            • Types of Organizations: Sole proprietorship, Partnership, Private Limited Company (PLC), Share Company.
            • Feasibility Analysis: Market, technical, financial, and organizational viability testing.
            """.trimIndent()
        )

        return notesList
    }
}
