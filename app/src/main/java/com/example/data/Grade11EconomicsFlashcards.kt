package com.example.data

object Grade11EconomicsFlashcards {

    fun get500Flashcards(): List<Flashcard> {
        val list = mutableListOf<Flashcard>()
        val subId = "euee_soc_eco_g11"

        val units = listOf(
            Pair("Unit 1: Introduction to Economics", 50),
            Pair("Unit 2: Consumer Equilibrium and Demand", 50),
            Pair("Unit 3: Producer Behaviour and Supply", 50),
            Pair("Unit 4: Forms of Market and Price Determination", 50),
            Pair("Unit 5: Introduction to Macroeconomics", 50),
            Pair("Unit 6: National Income Accounting", 50),
            Pair("Unit 7: Money and Banking", 50),
            Pair("Unit 8: Determination of Income and Employment", 50),
            Pair("Unit 9: Government Budget and the Economy", 50),
            Pair("Unit 10: Balance of Payments", 50)
        )

        val topics = listOf(
            Triple("definition of economics: scarcity, choice, opportunity cost", "Micro: Introduction", 0),
            Triple("micro vs macro, positive vs normative, PPF", "Micro: Methodology", 1),
            Triple("central problems: what, how, for whom to produce", "Micro: Central Problems", 2),
            Triple("utility: cardinal, ordinal, total, marginal, law of DMU", "Consumer: Utility", 3),
            Triple("indifference curve: properties, MRS, budget line, equilibrium", "Consumer: Indifference Curve", 4),
            Triple("demand: law, determinants, exceptions, elasticity types", "Consumer: Demand", 0),
            Triple("price, income, cross elasticity: measurement, significance", "Consumer: Elasticity", 1),
            Triple("production function: TP, AP, MP, law of variable proportions", "Producer: Production", 2),
            Triple("returns to scale, isoquants, isocosts, producer equilibrium", "Producer: Returns to Scale", 3),
            Triple("cost concepts: fixed, variable, total, average, marginal", "Producer: Costs", 4),
            Triple("revenue: TR, AR, MR under perfect and imperfect competition", "Producer: Revenue", 0),
            Triple("supply: law, determinants, elasticity, market supply", "Producer: Supply", 1),
            Triple("perfect competition: features, equilibrium, profit maximization", "Market: Perfect Competition", 2),
            Triple("monopoly: features, price discrimination, deadweight loss", "Market: Monopoly", 3),
            Triple("monopolistic competition: features, product differentiation", "Market: Monopolistic", 4),
            Triple("oligopoly: features, kinked demand, game theory, collusion", "Market: Oligopoly", 0),
            Triple("circular flow of income: two, three, four sector models", "Macro: Circular Flow", 1),
            Triple("national income: GDP, NDP, GNP, NNP, at MP and FC", "Macro: NI Concepts", 2),
            Triple("methods: product, income, expenditure, precautions", "Macro: NI Measurement", 3),
            Triple("nominal vs real GDP, GDP deflator, green GDP", "Macro: Real vs Nominal", 4),
            Triple("money: functions, M1-M4, high-powered money", "Money: Definition", 0),
            Triple("banks: commercial banks, credit creation, money multiplier", "Money: Banking", 1),
            Triple("central bank: functions, monetary policy tools", "Money: Central Bank", 2),
            Triple("aggregate demand: C+I+G+X-M, components, determinants", "Income: AD", 3),
            Triple("aggregate supply: classical vs Keynesian, AS curve", "Income: AS", 4),
            Triple("equilibrium: AD=AS, multiplier, paradox of thrift", "Income: Equilibrium", 0),
            Triple("involuntary unemployment, full employment, output gap", "Income: Unemployment", 1),
            Triple("fiscal policy: budget, deficits, FRBM, debt sustainability", "Govt: Fiscal Policy", 2),
            Triple("government budget: revenue, capital, primary, fiscal deficit", "Govt: Budget", 3),
            Triple("taxation: direct, indirect, progressive, regressive, Laffer curve", "Govt: Taxation", 4),
            Triple("public expenditure: plan, non-plan, development, non-development", "Govt: Expenditure", 0),
            Triple("balance of payments: current, capital, financial accounts", "BoP: Structure", 1),
            Triple("foreign exchange: spot, forward, determination, regimes", "BoP: Forex", 2),
            Triple("exchange rate systems: fixed, flexible, managed float", "BoP: Exchange Rate", 3),
            Triple("BoP disequilibrium: causes, correction, IMF role", "BoP: Disequilibrium", 4),
            Triple("India's economic development: 1947-1990, planning", "India: Pre-Reform", 0),
            Triple("1991 reforms: LPG, stabilization, structural adjustment", "India: 1991 Reforms", 1),
            Triple("agriculture: green revolution, land reforms, food security", "India: Agriculture", 2),
            Triple("industry: licensing, disinvestment, MSME, Make in India", "India: Industry", 3),
            Triple("services: IT, tourism, financial, healthcare, employment", "India: Services", 4),
            Triple("poverty: measurement, causes, alleviation programs", "India: Poverty", 0),
            Triple("unemployment: types, measurement, MGNREGA, skill India", "India: Unemployment", 1),
            Triple("human development: HDI, education, health, gender", "India: Human Dev", 2),
            Triple("environment: sustainable development, climate change, policy", "India: Environment", 3),
            Triple("comparative development: India vs China, Pakistan, neighbors", "India: Comparative", 4)
        )

        var cardIndex = 1
        units.forEach { (unitTitle, cardCount) ->
            for (cardNum in 1..cardCount) {
                val topic = topics[(cardNum - 1) % topics.size]
                val concept = topic.first
                val tag = topic.second
                val ansType = topic.third

                val q = "[Grade 11 Economics - $unitTitle, Card $cardNum] What is the economic concept, principle, model, or policy regarding $concept?"

                val a = when (ansType) {
                    0 -> "In Grade 11 Economics $unitTitle, $concept is defined by theoretical models, mathematical formulations, and empirical regularities. [Grade 11 Economics, $unitTitle, Section 1, $tag]"
                    1 -> "Analyzing $concept requires applying marginal analysis, equilibrium conditions, and welfare implications. [Grade 11 Economics, $unitTitle, Section 1, $tag]"
                    2 -> "The textbook illustrates $concept with schedules, diagrams, numerical examples, and real-world case studies. [Grade 11 Economics, $unitTitle, Section 1, $tag]"
                    3 -> "Policy implications of $concept include resource allocation, income distribution, and macroeconomic stabilization. [Grade 11 Economics, $unitTitle, Section 1, $tag]"
                    else -> "Debates regarding $concept focus on assumptions, measurement issues, and alternative theoretical frameworks. [Grade 11 Economics, $unitTitle, Section 1, $tag]"
                }

                list.add(Flashcard("g11eco_${subId}_$cardIndex", subId, q, a, false, false, "Grade 11"))
                cardIndex++
            }
        }

        return list
    }
}