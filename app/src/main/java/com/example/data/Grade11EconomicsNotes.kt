package com.example.data

object Grade11EconomicsNotes {

    fun getGrade11EconomicsNotes(): List<SubjectNote> {
        val notesList = mutableListOf<SubjectNote>()
        val subId = "euee_soc_eco"
        var idx = 1

        fun addNote(unit: String, title: String, content: String) {
            notesList.add(
                SubjectNote(
                    id = "g11_eco_note_${idx}",
                    subjectId = subId,
                    unit = unit,
                    title = title,
                    content = content,
                    gradeLevel = "Grade 11"
                )
            )
            idx++
        }

        // Unit 1
        addNote(
            "Unit 1 - Theory of Consumer Behavior and Demand",
            "Cardinal and Ordinal Utility Approaches (Sections 1.1 - 1.4)",
            """
            • Utility: The satisfaction or pleasure derived from the consumption of a good or service. The cardinal approach assumes utility is measurable in units called 'utils', while the ordinal approach assumes consumers can only rank preferences.
            • Law of Diminishing Marginal Utility: As a consumer consumes more of a good, the extra satisfaction (Marginal Utility) derived from each additional unit decreases.
            • Indifference Curve (IC): A curve showing combinations of two goods that give the consumer the same level of satisfaction. Properties include being downward sloping, convex to the origin, and non-intersecting.
            • Marginal Rate of Substitution (MRS): The rate at which a consumer is willing to substitute one good for another while maintaining the same utility level.
            • Budget Line: Represents all combinations of two goods a consumer can afford given their income and the prices of the goods. Its slope is the price ratio (-Px/Py).
            • Consumer Equilibrium: The point where the budget line is tangent to the highest possible indifference curve, maximizing satisfaction.
            """.trimIndent()
        )

        // Unit 2
        addNote(
            "Unit 2 - Market Structure and the Decision of Firms",
            "Perfect Competition, Monopoly, Monopolistic Competition, and Oligopoly (Sections 2.1 - 2.5)",
            """
            • Perfect Competition: Characterized by many small firms, homogeneous products, free entry/exit, and perfect information. Firms are 'price takers'.
            • Profit Maximization Rule: A firm maximizes profit where Marginal Revenue (MR) equals Marginal Cost (MC), and MC is rising.
            • Pure Monopoly: A market with a single seller of a product with no close substitutes. The firm is a 'price maker' and faces a downward-sloping demand curve.
            • Price Discrimination: The practice of selling the same product at different prices to different consumers.
            • Monopolistic Competition: Many firms selling differentiated products. Equilibrium occurs where MR=MC, but price is higher than MC.
            • Oligopoly: A market dominated by a few large firms. Behavior can be collusive (acting together like a monopoly) or non-collusive.
            """.trimIndent()
        )

        // Unit 3
        addNote(
            "Unit 3 - National Income Accounting",
            "GDP, GNP, and Measurement Approaches (Sections 3.1 - 3.7)",
            """
            • Gross Domestic Product (GDP): The total market value of all final goods and services produced within a country's borders in a given year.
            • Gross National Product (GNP): The total value of final goods and services produced by a country's residents, regardless of where the production takes place.
            • Measurement Approaches: 1) Expenditure Approach (C + I + G + (X-M)), 2) Income Approach (sum of all factor payments), 3) Product/Value-Added Approach (sum of value added at each stage).
            • Real vs. Nominal GDP: Nominal GDP uses current prices, while Real GDP uses constant base-year prices to adjust for inflation.
            • GDP Deflator: A measure of the price level calculated by dividing Nominal GDP by Real GDP and multiplying by 100.
            """.trimIndent()
        )

        // Unit 4
        addNote(
            "Unit 4 - Consumption, Saving and Investment",
            "Propensities, Determinants, and Economic Growth (Sections 4.1 - 4.4)",
            """
            • Consumption Function: The relationship between consumption and disposable income (C = a + bYd). 'a' is autonomous consumption, and 'b' is the Marginal Propensity to Consume (MPC).
            • Marginal Propensity to Consume (MPC): The change in consumption resulting from a one-unit change in income. MPC + MPS = 1.
            • Determinants of Saving: Income level, interest rates, wealth, and expectations about the future.
            • Investment: Expenditure on capital goods (machinery, buildings, inventories). Determinants include interest rates, expected returns, and technological progress.
            """.trimIndent()
        )

        // Unit 5
        addNote(
            "Unit 5 - Trade and Finance",
            "International Trade Theories, Balance of Payments, and Exchange Rates (Sections 5.1 - 5.7)",
            """
            • Absolute Advantage: Adam Smith's theory that a country should specialize in producing goods it can make more efficiently than others.
            • Comparative Advantage: David Ricardo's theory that trade is beneficial if countries specialize in goods for which they have a lower opportunity cost.
            • Balance of Payments (BOP): A record of all economic transactions between a country's residents and the rest of the world. Includes the Current Account and Capital Account.
            • Exchange Rate Systems: Fixed (pegged by government) and Floating (determined by market supply and demand).
            """.trimIndent()
        )

        // Unit 6
        addNote(
            "Unit 6 - Economic Development",
            "Growth vs. Development, HDI, and Sustainable Development (Sections 6.1 - 6.7)",
            """
            • Economic Growth vs. Development: Growth is a quantitative increase in output (GDP), while Development is a qualitative improvement in the standard of living and structural changes.
            • Human Development Index (HDI): A composite statistic of life expectancy, education, and per capita income indicators.
            • Sustainable Development: Development that meets present needs without compromising the ability of future generations to meet theirs.
            • Sustainable Development Goals (SDGs): A set of 17 global goals established by the UN in 2015 to be achieved by 2030, covering poverty, hunger, health, and environment.
            """.trimIndent()
        )

        // Unit 7
        addNote(
            "Unit 7 - Main Sectors, Sectorial Policies and Strategies of Ethiopia",
            "Agricultural, Industrial, and Service Sector Policies (Sections 7.1 - 7.5)",
            """
            • Agricultural Strategies: Uni-modal (focus on smallholders) and Bi-modal (smallholders plus large-scale commercial farms).
            • Problems of Agriculture in Ethiopia: Dependence on rainfall, land fragmentation, traditional technology, and limited access to credit.
            • Industrial Sector Regimes: Imperial (import substitution), Derg (state ownership), and Post-1991 (market-oriented, privatization).
            • Service Sector: Includes education, health, transport, and tourism, playing an increasing role in Ethiopia's GDP.
            """.trimIndent()
        )

        return notesList
    }
}