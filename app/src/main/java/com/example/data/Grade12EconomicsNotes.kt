package com.example.data

object Grade12EconomicsNotes {

    fun getGrade12EconomicsNotes(): List<SubjectNote> {
        val notesList = mutableListOf<SubjectNote>()
        val subId = "euee_soc_economics"
        var idx = 1

        fun addNote(unit: String, title: String, content: String) {
            notesList.add(
                SubjectNote(
                    id = "g12_eco_note_${idx}",
                    subjectId = subId,
                    unit = unit,
                    title = title,
                    content = content,
                    gradeLevel = "Grade 12"
                )
            )
            idx++
        }

        // Unit 1
        addNote(
            "Unit 1 - The Fundamental Concepts of Macroeconomics",
            "Definition and Focus Areas of Macroeconomics / Key Challenges in Macroeconomics / Schools of Thought in Macroeconomic Analysis",
            """
            • Macroeconomics: The study of overall or aggregate behavior of the economy, such as the overall level of output, prices, and employment.
            • Microeconomics: Studies individual decision-making behavior of different economic units like households and firms at a disaggregated level.
            • Key Macroeconomic Variables: Include GDP, GNP, inflation, unemployment, and trade balance.
            • GDP (Gross Domestic Product): The market value of all final goods and services produced within a country in a given period.
            • GNP (Gross National Product): GNP = GDP + NFI (Net Factor Income from abroad).
            • Measuring GDP: Three approaches—Product/Value Added approach, Expenditure approach (C+I+G+NE), and Income approach.
            • Economic Growth Rate: The rate at which the real GDP of a country increases over a period of time.
            • Inflation: A regular and continuous rise in the general price level, which decreases the purchasing power of money.
            • Consumer Price Index (CPI): A price index reflecting changes in the prices of goods and services typically purchased by consumers.
            • Inflation Rate Formula: [(Current Date CPI - Past Date CPI) / Past Date CPI] x 100%.
            • Unemployment: Occurs when people are without work and are actively seeking employment.
            • Balance of Trade: The difference between the export and import of goods and services of a country.
            • Keynesian Economics: Proposes active fiscal policy to alleviate weak aggregate demand; emphasizes that wages and prices can be "sticky."
            • Monetarist View: Represented by Milton Friedman; argues that monetary policy, not fiscal policy, is the primary tool for addressing macroeconomic problems.
            """.trimIndent()
        )

        // Unit 2
        addNote(
            "Unit 2 - Aggregate Demand and Aggregate Supply Analysis",
            "Aggregate Demand (AD) / Aggregate Supply (AS)",
            """
            • Definition: The total amount that different sectors in the economy (households, firms, and governments) willingly spend in a given period.
            • AD Components: AD = C + I + G + (X - M).
            • Determinants: Factors like wealth, interest rates, expectations, and taxes affect AD components and shift the AD curve.
            • Aggregate Supply (AS): The total quantity of goods and services that firms are willing and able to produce and sell at various price levels.
            • Short-Run AS (SRAS): Upward sloping because of sticky wages and prices.
            • Long-Run AS (LRAS): Vertical at the level of natural real GDP (QN) when wages and prices have fully adjusted.
            """.trimIndent()
        )

        // Unit 3
        addNote(
            "Unit 3 - Market Failure and Consumer Protection",
            "Market Failure / Asymmetric Information / Consumer Protection",
            """
            • Definition: Occurs when individuals in a group end up worse off than if they had not acted in perfectly rational self-interest; leads to inefficient outcomes.
            • Types of Market Failures: Include externalities, public goods, monopoly, and asymmetric information.
            • Public Goods: Characterized by non-excludability (cannot limit consumption to paying customers) and non-rivalry (consumption by one doesn't limit others).
            • Asymmetric Information:
              - Moral Hazard: Occurs when one party takes risks because the costs are borne by another party.
              - Adverse Selection: Occurs when one party has more information than the other before a transaction.
              - Solutions: Signalling (agent conveys info) and Screening (principal gathers info).
            • Consumer Protection: Safeguarding the interests and rights of consumers from unethical malpractices like adulteration, spurious goods, and false weights.
            • Proclamation No. 813/2013: The Ethiopian legal framework providing rights to consumers.
            """.trimIndent()
        )

        // Unit 4
        addNote(
            "Unit 4 - Macroeconomic Policy Instruments",
            "Fiscal Policy / Monetary Policy / Foreign Exchange Policies",
            """
            • Fiscal Policy: The expenditure and revenue (tax) policy of the government to achieve desired objectives.
            • Tools: Taxation and Government Spending.
            • Components of Spending: Includes government spending (G), transfer payments, grants in aid, and net interest payments.
            • Monetary Policy: Management of money supply and interest rates by the National Bank to control inflation and growth.
            • Tools: Open Market Operations (OMO), modifying interest rates, and changing reserve requirements.
            • Foreign Exchange Policies:
              - Fixed Exchange Rate: Rates are pegged to a currency or gold; provides certainty but may be unable to respond to shocks.
              - Floating Exchange Rate: Rates are determined by market forces; protects from external shocks but can be unstable.
              - Ethiopia's System: Transitioned from a fixed rate (pegged to USD) to an auction-based system and currently an interbank foreign exchange market.
            """.trimIndent()
        )

        // Unit 5
        addNote(
            "Unit 5 - Tax Theory and Practice",
            "Taxes: Definition and Principles / Tax Schedules in Ethiopia",
            """
            • Direct Taxes: Burden falls on the same person who earns the income (e.g., income tax).
            • Indirect Taxes: Burden is shifted to a different person (e.g., VAT, excise tax).
            • Principles of a Good Tax System: Equity (horizontal and vertical), efficiency, non-distortive, and easy to understand.
            • Tax Schedules in Ethiopia:
              - Schedule A: Employment income tax (progressive rate 10-35%).
              - Schedule B: Rental income tax.
              - Schedule C: Business income tax.
              - Schedule D: Other income (royalties, dividends, etc.).
            """.trimIndent()
        )

        // Unit 6
        addNote(
            "Unit 6 - Poverty and Inequality",
            "Concept of Poverty / Indigenous Knowledge in Reducing Poverty",
            """
            • Absolute Poverty: Living below a fixed minimum level of income/consumption needed for basic necessities.
            • Relative Poverty: Receiving significantly less income than the average in a society (e.g., < 50% of average).
            • Capability Approach: Amartya Sen's view that poverty is the lack of "capability to function" in society.
            • Indigenous Knowledge in Reducing Poverty:
              - Iqub and Idir: Traditional financial and social associations in Ethiopia providing mutual benefits and lessening societal burdens.
              - Debo: A traditional work group where community members assist individuals on a rotational basis.
            """.trimIndent()
        )

        // Unit 7
        addNote(
            "Unit 7 - Macroeconomic Reforms in Ethiopia",
            "National Development Plans",
            """
            • GTP I (2010/11-2014/15): Focused on scaling up agricultural practices and creating structural transformation through industrialization.
            • GTP II (2015/16-2019/20): Aimed at accelerating human development, technological capacity, and building a climate-resilient green economy.
            • Ten-Year Development Plan: Vision to make Ethiopia an "African Beacon of Prosperity."
            """.trimIndent()
        )

        // Unit 8
        addNote(
            "Unit 8 - Economy, Environment and Climate Change",
            "Global Warming and Climate Change",
            """
            • Global Warming: Increasing average air and ocean temperatures attributed to human activities emitting greenhouse gases (GhG).
            • Primary Greenhouse Gases: CO₂, CH₄, N₂O, water vapour (H₂O), and ozone (O₃).
            • Climate Change Impacts: Include rising sea levels, extreme weather events, and vulnerability of rain-fed agriculture.
            """.trimIndent()
        )

        return notesList
    }
}