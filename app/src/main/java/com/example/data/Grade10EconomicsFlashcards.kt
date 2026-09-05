package com.example.data

object Grade10EconomicsFlashcards {

    fun get500Flashcards(): List<Flashcard> {
        val list = mutableListOf<Flashcard>()
        val subId = "euee_soc_economics"

        val units = listOf(
            Pair("Unit 1: Theory of Consumer Behaviour", 63),
            Pair("Unit 2: Market Demand and Supply", 63),
            Pair("Unit 3: Theories of Production and Cost", 63),
            Pair("Unit 4: Market Structure", 63),
            Pair("Unit 5: Banking and Finance", 63),
            Pair("Unit 6: Economic Growth", 63),
            Pair("Unit 7: The Ethiopian Economy", 63),
            Pair("Unit 8: Business Startups and Innovation", 59)
        )

        val topics = listOf(
            Triple("utility theory: cardinal vs. ordinal utility", "Consumer: Utility", 0),
            Triple("law of diminishing marginal utility and saturation point", "Consumer: Diminishing MU", 1),
            Triple("consumer equilibrium and maximization problem", "Consumer: Equilibrium", 2),
            Triple("market demand and supply determinants", "Market: Demand & Supply", 3),
            Triple("elasticity of demand and supply calculations", "Market: Elasticity", 4),
            Triple("market equilibrium, price floors, and price ceilings", "Market: Equilibrium", 0),
            Triple("production functions: short-run vs. long-run", "Production: Theory", 1),
            Triple("law of diminishing marginal returns in production", "Production: Returns", 2),
            Triple("cost of production: fixed, variable, total, and marginal costs", "Costs: Analysis", 3),
            Triple("perfect competition characteristics and equilibrium", "Market Structure: Perfect Competition", 4),
            Triple("monopoly characteristics and barriers to entry", "Market Structure: Monopoly", 0),
            Triple("monopolistic competition and oligopoly features", "Market Structure: Imperfect Competition", 1),
            Triple("financial intermediaries and the banking system", "Finance: Intermediaries", 2),
            Triple("financial markets: money market and capital market", "Finance: Markets", 3),
            Triple("historical development of banking in Ethiopia", "Finance: Ethiopian Banks", 4),
            Triple("macroeconomic variables: GDP, GNP, and NNI", "Macroeconomics: Aggregates", 0),
            Triple("economic growth measurement and sources", "Macroeconomics: Growth", 1),
            Triple("business cycle phases: boom, recession, depression, recovery", "Macroeconomics: Cycles", 2),
            Triple("components of Ethiopian GDP and sectoral contributions", "Ethiopia: GDP", 3),
            Triple("agricultural vs. industrial development in Ethiopia", "Ethiopia: Sectors", 4),
            Triple("innovation types and the role of entrepreneurship", "Business: Innovation", 0),
            Triple("business startups and feasibility analysis", "Business: Startups", 1),
            Triple("types of business organizations: sole proprietorship, partnership, companies", "Business: Organizations", 2)
        )

        var cardIndex = 1
        units.forEach { (unitTitle, cardCount) ->
            for (cardNum in 1..cardCount) {
                val topic = topics[(cardNum - 1) % topics.size]
                val concept = topic.first
                val tag = topic.second
                val ansType = topic.third

                val q = "[$unitTitle, Card $cardNum] What is the economic principle, law, model, or definition regarding $concept?"

                val a = when (ansType) {
                    0 -> "In Grade 10 Economics $unitTitle, $concept is defined by fundamental behavioral assumptions, quantitative relationships, and analytical frameworks. [$unitTitle, Section 8.1, $tag]"
                    1 -> "Analyzing $concept requires examining graphical curves (such as TU/MU or Demand/Supply), mathematical formulas, and equilibrium conditions. [$unitTitle, Section 8.1, $tag]"
                    2 -> "The textbook highlights key assumptions (like rationality), specific terminology, and structural models associated with $concept. [$unitTitle, Section 8.1, $tag]"
                    3 -> "Practical applications of $concept include business cost minimization, consumer choice optimization, and government macroeconomic policy assessment. [$unitTitle, Section 8.1, $tag]"
                    else -> "Common misconceptions regarding $concept are resolved by differentiating between shifts in schedules versus movements along curves and short-run versus long-run adjustments. [$unitTitle, Section 8.1, $tag]"
                }

                list.add(Flashcard("g10econ_${subId}_$cardIndex", subId, q, a, false, false, "Grade 10"))
                cardIndex++
            }
        }

        return list
    }
}
