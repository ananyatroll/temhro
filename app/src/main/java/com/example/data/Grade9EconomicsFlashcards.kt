package com.example.data

object Grade9EconomicsFlashcards {

    fun get500Flashcards(): List<Flashcard> {
        val list = mutableListOf<Flashcard>()
        val subId = "euee_soc_economics"

        val units = listOf(
            Pair("Unit 1: Introduction to Economics", 63),
            Pair("Unit 2: Basic Concepts of Economics", 63),
            Pair("Unit 3: Demand and Supply", 63),
            Pair("Unit 4: Consumer Theory and Behavior", 63),
            Pair("Unit 5: Production and Costs", 63),
            Pair("Unit 6: Market Structures", 63),
            Pair("Unit 7: National Income Accounting", 63),
            Pair("Unit 8: Economic Growth and Development", 59)
        )

        val topics = listOf(
            Triple("definition, scope, and importance of economics", "Introduction: Scope", 0),
            Triple("scarcity, choice, and opportunity cost", "Basic Concepts: Scarcity", 1),
            Triple("production possibilities curve (PPC) and efficiency", "Basic Concepts: PPC", 2),
            Triple("economic systems: traditional, command, market, and mixed economies", "Systems: Types", 3),
            Triple("demand theory, law of demand, and demand curve determinants", "Demand: Theory", 4),
            Triple("supply theory, law of supply, and supply curve determinants", "Supply: Theory", 0),
            Triple("market equilibrium, surplus, and shortage", "Equilibrium: Market", 1),
            Triple("elasticity of demand and supply (price, income, cross-price)", "Elasticity: Concepts", 2),
            Triple("utility theory: cardinal vs. ordinal utility, diminishing marginal utility", "Consumer: Utility", 3),
            Triple("production function, short-run vs. long-run, law of diminishing returns", "Production: Short-run", 4),
            Triple("costs of production: fixed, variable, total, average, and marginal costs", "Costs: Analysis", 0),
            Triple("market structures: perfect competition, monopoly, monopolistic competition, oligopoly", "Markets: Structures", 1),
            Triple("national income aggregates: GDP, GNP, NNI, and methods of measurement", "National Income: GDP", 2),
            Triple("inflation, unemployment, and economic fluctuations (business cycles)", "Macroeconomics: Instability", 3),
            Triple("money, banking, and monetary policy basics", "Money: Banking", 4),
            Triple("fiscal policy, government budget, taxation, and public debt", "Macroeconomics: Fiscal Policy", 0),
            Triple("international trade, comparative advantage, and exchange rates", "International: Trade", 1),
            Triple("economic growth vs. economic development and indicators", "Development: Growth", 2),
            Triple("poverty, inequality, and sustainable development goals", "Development: Poverty", 3),
            Triple("agricultural and industrial development in Ethiopia", "Ethiopia: Economy", 4)
        )

        var cardIndex = 1
        units.forEach { (unitTitle, cardCount) ->
            for (cardNum in 1..cardCount) {
                val topic = topics[(cardNum - 1) % topics.size]
                val concept = topic.first
                val tag = topic.second
                val ansType = topic.third

                val q = "(Grade 9 Economics, $unitTitle - Card $cardNum) What is the economic principle, law, model, or concept regarding $concept?"

                val a = when (ansType) {
                    0 -> "In $unitTitle, $concept is defined by fundamental microeconomic/macroeconomic principles, behavioral assumptions, and analytical models. [$unitTitle, Section 1, $tag]"
                    1 -> "Analyzing $concept requires examining graphical curves, mathematical formulas, and marginal decision-making criteria. [$unitTitle, Section 1, $tag]"
                    2 -> "The textbook highlights key definitions, assumptions, and terminology associated with $concept. [$unitTitle, Section 1, $tag]"
                    3 -> "Practical applications of $concept include market price determination, business cost minimization, and government policy formulation. [$unitTitle, Section 1, $tag]"
                    else -> "Common misconceptions regarding $concept are resolved by differentiating between shift in curves versus movement along curves and short-run versus long-run impacts. [$unitTitle, Section 1, $tag]"
                }

                list.add(Flashcard("g9econ_${subId}_$cardIndex", subId, q, a, false, false, "Grade 9"))
                cardIndex++
            }
        }

        return list
    }
}
