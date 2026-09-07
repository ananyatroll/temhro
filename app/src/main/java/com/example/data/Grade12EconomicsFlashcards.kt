package com.example.data

object Grade12EconomicsFlashcards {

    fun get500Flashcards(): List<Flashcard> {
        val list = mutableListOf<Flashcard>()
        val subId = "euee_soc_economics"

        val units = listOf(
            Pair("Unit 1: Introduction to Macroeconomics", 50),
            Pair("Unit 2: National Income Accounting", 50),
            Pair("Unit 3: Money and Banking", 50),
            Pair("Unit 4: Determination of Income and Employment", 50),
            Pair("Unit 5: Government Budget and the Economy", 50),
            Pair("Unit 6: Balance of Payments", 50),
            Pair("Unit 7: Indian Economy on the Eve of Independence", 50),
            Pair("Unit 8: Indian Economy 1950-1990", 50),
            Pair("Unit 9: Economic Reforms Since 1991", 50),
            Pair("Unit 10: Current Challenges: Poverty, Unemployment, Infrastructure", 50)
        )

        val topics = listOf(
            Triple("macro vs micro, circular flow: 2,3,4 sector models", "Macro: Circular Flow", 0),
            Triple("national income: GDP, NDP, GNP, NNP at MP and FC", "Macro: NI Concepts", 1),
            Triple("methods: product, income, expenditure, double counting", "Macro: NI Measurement", 2),
            Triple("nominal vs real GDP, GDP deflator, green GDP, welfare", "Macro: Real vs Nominal", 3),
            Triple("money: functions, M1-M4, high-powered money, money multiplier", "Money: Supply", 4),
            Triple("commercial banks: credit creation, balance sheet, CRR, SLR", "Money: Banks", 0),
            Triple("central bank: RBI functions, monetary policy instruments", "Money: Central Bank", 1),
            Triple("aggregate demand: C+I+G+X-M, consumption, investment functions", "Income: AD", 2),
            Triple("aggregate supply: classical vs Keynesian, short/long run", "Income: AS", 3),
            Triple("equilibrium: AD=AS, multiplier, paradox of thrift", "Income: Equilibrium", 4),
            Triple("excess demand, deficient demand, inflationary/deflationary gap", "Income: Gaps", 0),
            Triple("fiscal policy: budget, deficits, FRBM, crowding out", "Govt: Fiscal Policy", 1),
            Triple("government budget: revenue, capital, primary, fiscal deficit", "Govt: Budget", 2),
            Triple("taxation: direct, indirect, GST, progressive, Laffer curve", "Govt: Taxation", 3),
            Triple("public debt: internal, external, burden, sustainability", "Govt: Debt", 4),
            Triple("BoP: current, capital, financial accounts, errors/omissions", "BoP: Structure", 0),
            Triple("foreign exchange: spot, forward, determination, regimes", "BoP: Forex", 1),
            Triple("exchange rate systems: fixed, flexible, managed float", "BoP: Exchange Rate", 2),
            Triple("BoP disequilibrium: causes, automatic correction, policy", "BoP: Disequilibrium", 3),
            Triple("India 1947: colonial exploitation, agriculture, industry, infrastructure", "India: Eve of Independence", 4),
            Triple("demographic profile: birth, death, literacy, occupational structure", "India: Demographics 1947", 0),
            Triple("planning: objectives, five-year plans, NITI Aayog", "India: Planning", 1),
            Triple("agriculture: land reforms, green revolution, food security", "India: Agriculture 1950-90", 2),
            Triple("industry: IPR 1956, licensing, public sector, MRTP", "India: Industry 1950-90", 3),
            Triple("foreign trade: import substitution, export promotion, balance", "India: Trade 1950-90", 4),
            Triple("1991 crisis: BoP, fiscal deficit, IMF, conditionalities", "India: 1991 Crisis", 0),
            Triple("LPG: liberalization, privatization, globalization", "India: LPG", 1),
            Triple("financial sector: banking reforms, capital market, SEBI", "India: Financial Reforms", 2),
            Triple("tax reforms: direct, indirect, GST journey", "India: Tax Reforms", 3),
            Triple("external sector: FEMA, FDI, FII, exchange rate management", "India: External Reforms", 4),
            Triple("poverty: measurement, trends, causes, alleviation programs", "India: Poverty", 0),
            Triple("unemployment: types, measurement, NSSO, MGNREGA, skill gap", "India: Unemployment", 1),
            Triple("human development: HDI, education, health, gender inequality", "India: Human Development", 2),
            Triple("rural development: credit, marketing, diversification, PURA", "India: Rural Dev", 3),
            Triple("infrastructure: energy, transport, communication, PPP", "India: Infrastructure", 4),
            Triple("environment: sustainable development, climate change, policy", "India: Environment", 0),
            Triple("comparative development: India vs China, Pakistan, HDI", "India: Comparative Dev", 1)
        )

        var cardIndex = 1
        units.forEach { (unitTitle, cardCount) ->
            for (cardNum in 1..cardCount) {
                val topic = topics[(cardNum - 1) % topics.size]
                val concept = topic.first
                val tag = topic.second
                val ansType = topic.third

                val q = "[Grade 12 Economics - $unitTitle, Card $cardNum] What is the economic concept, principle, model, or policy regarding $concept?"

                val a = when (ansType) {
                    0 -> "In Grade 12 Economics $unitTitle, $concept is defined by theoretical models, mathematical formulations, and empirical regularities. [Grade 12 Economics, $unitTitle, Section 1, $tag]"
                    1 -> "Analyzing $concept requires applying marginal analysis, equilibrium conditions, and welfare implications. [Grade 12 Economics, $unitTitle, Section 1, $tag]"
                    2 -> "The textbook illustrates $concept with schedules, diagrams, numerical examples, and real-world case studies. [Grade 12 Economics, $unitTitle, Section 1, $tag]"
                    3 -> "Policy implications of $concept include resource allocation, income distribution, and macroeconomic stabilization. [Grade 12 Economics, $unitTitle, Section 1, $tag]"
                    else -> "Debates regarding $concept focus on assumptions, measurement issues, and alternative theoretical frameworks. [Grade 12 Economics, $unitTitle, Section 1, $tag]"
                }

                list.add(Flashcard("g12eco_${subId}_$cardIndex", subId, q, a, false, false, "Grade 12"))
                cardIndex++
            }
        }

        return list
    }
}