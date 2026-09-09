package com.example.data

/**
 * Canonical product catalog for Tamhero.
 *
 * Every purchasable access scope is a [Product] with a stable machine-readable [id].
 * The client displays products and creates purchase requests; it NEVER grants paid
 * access locally. Entitlements are granted only after a server-approved redemption.
 */
data class Product(
    /** Stable machine-readable id, e.g. "freshman_natural_science_y1_sem1" */
    val id: String,
    /** Top-level category: "freshman", "university", "coc" */
    val category: String,
    /** Stream for freshman ("natural_science"/"social_science"); null otherwise */
    val stream: String? = null,
    /** Academic year (freshman=1); null for COC */
    val academicYear: Int? = null,
    /** Department key for university products ("computer_science", "law", ...); null otherwise */
    val department: String? = null,
    /** Plan: "sem1", "sem2", "full_year", or "field" (COC) */
    val plan: String,
    /** COC field ("medical"/"law"/"engineering"); null otherwise */
    val field: String? = null,
    /** Price in ETB. Null = price not configured; UI must not show a buy button. */
    val amount: Int? = null,
    val currency: String = "ETB",
    /** Human-readable short label, e.g. "Semester 1" / "Full Academic Year" */
    val shortLabel: String,
    /** Product ids this product grants (bundle expansion). Empty for atomic products. */
    val grants: List<String> = emptyList()
) {
    /** Entitlement ids this purchase grants. Bundles expand to their children. */
    fun entitlementIds(): List<String> = if (grants.isEmpty()) listOf(id) else grants

    /** True if this product is purchasable right now (price configured). */
    val isPurchasable: Boolean get() = amount != null
}

object ProductCatalog {

    const val PRICE_SEMESTER_ETB = 300
    const val PRICE_FULL_YEAR_ETB = 500
    const val FULL_YEAR_SAVINGS_ETB = (2 * PRICE_SEMESTER_ETB) - PRICE_FULL_YEAR_ETB // 100

    // ---------------------------------------------------------------------
    // Freshman products (streams fully isolated)
    // ---------------------------------------------------------------------
    private fun freshmanProducts(stream: String, streamPrefix: String): List<Product> {
        val sem1 = "${streamPrefix}_y1_sem1"
        val sem2 = "${streamPrefix}_y1_sem2"
        val full = "${streamPrefix}_y1_full_year"
        return listOf(
            Product(
                id = sem1, category = "freshman", stream = stream, academicYear = 1,
                plan = "sem1", amount = PRICE_SEMESTER_ETB, shortLabel = "Semester 1"
            ),
            Product(
                id = sem2, category = "freshman", stream = stream, academicYear = 1,
                plan = "sem2", amount = PRICE_SEMESTER_ETB, shortLabel = "Semester 2"
            ),
            Product(
                id = full, category = "freshman", stream = stream, academicYear = 1,
                plan = "full_year", amount = PRICE_FULL_YEAR_ETB,
                shortLabel = "Full Academic Year",
                grants = listOf(sem1, sem2)
            )
        )
    }

    val freshmanNaturalProducts = freshmanProducts("natural_science", "freshman_natural_science")
    val freshmanSocialProducts = freshmanProducts("social_science", "freshman_social_science")

    // ---------------------------------------------------------------------
    // University products: university_<department>_y<year>_<plan>
    // Departments mirror the existing department model used in DashboardScreen.
    // ---------------------------------------------------------------------
    val universityDepartments = listOf(
        "accounting", "economics", "computer_science", "electrical",
        "mechanical", "law", "management"
    )

    fun universityProducts(departmentKey: String, year: Int): List<Product> {
        val sem1 = "university_${departmentKey}_y${year}_sem1"
        val sem2 = "university_${departmentKey}_y${year}_sem2"
        val full = "university_${departmentKey}_y${year}_full_year"
        return listOf(
            Product(sem1, "university", department = departmentKey, academicYear = year,
                plan = "sem1", amount = PRICE_SEMESTER_ETB, shortLabel = "Semester 1"),
            Product(sem2, "university", department = departmentKey, academicYear = year,
                plan = "sem2", amount = PRICE_SEMESTER_ETB, shortLabel = "Semester 2"),
            Product(full, "university", department = departmentKey, academicYear = year,
                plan = "full_year", amount = PRICE_FULL_YEAR_ETB,
                shortLabel = "Full Academic Year", grants = listOf(sem1, sem2))
        )
    }

    private val allUniversityProducts: List<Product> =
        universityDepartments.flatMap { dept -> (2..4).flatMap { y -> universityProducts(dept, y) } }

    // ---------------------------------------------------------------------
    // COC products — prices intentionally NOT configured here.
    // Amount resolution order: backend config > local override. Until a price
    // exists, the product is not purchasable (isPurchasable == false).
    // ---------------------------------------------------------------------
    private val cocProducts = listOf(
        Product("coc_medical", "coc", plan = "field", field = "medical",
            amount = null, shortLabel = "Medical COC Exam"),
        Product("coc_law", "coc", plan = "field", field = "law",
            amount = null, shortLabel = "Law COC Exam"),
        Product("coc_engineering", "coc", plan = "field", field = "engineering",
            amount = null, shortLabel = "Engineering COC Exam")
    )

    private val all: List<Product> =
        freshmanNaturalProducts + freshmanSocialProducts + allUniversityProducts + cocProducts

    private val byId: Map<String, Product> = all.associateBy { it.id }

    fun get(id: String): Product? = byId[id]

    fun isKnown(productId: String): Boolean = byId.containsKey(productId)

    /**
     * Resolve a (possibly bundle) product id into the concrete entitlement ids it grants.
     * Unknown ids resolve to themselves so the backend can introduce new atomic products.
     */
    fun expandEntitlements(productId: String): List<String> =
        byId[productId]?.entitlementIds() ?: listOf(productId)

    /** True if [grantedId] (what the user owns) satisfies [requiredId] (what content needs). */
    fun grants(grantedId: String, requiredId: String): Boolean {
        if (grantedId == requiredId) return true
        return byId[grantedId]?.entitlementIds()?.contains(requiredId) == true
    }

    /**
     * The product ids required to unlock content belonging to a given subject.
     * Returns the atomic semester/field-level product id (never a bundle).
     */
    fun requiredProductForSubject(subjectId: String, subjectPackageId: String): String? {
        return when (subjectPackageId) {
            "freshman_natural" ->
                if (subjectId in NATURAL_SEM1_SUBJECTS) "freshman_natural_science_y1_sem1"
                else "freshman_natural_science_y1_sem2"
            "freshman_social" ->
                if (subjectId in SOCIAL_SEM1_SUBJECTS) "freshman_social_science_y1_sem1"
                else "freshman_social_science_y1_sem2"
            "coc_medical" -> "coc_medical"
            "coc_law" -> "coc_law"
            "coc_engineering" -> "coc_engineering"
            else -> null // university handled separately (needs dept+year context)
        }
    }

    /** University product required for a given course assignment. */
    fun universityProduct(departmentKey: String, year: Int, semester: Int): String =
        "university_${departmentKey}_y${year}_sem${if (semester == 1) 1 else 2}"

    fun universityFullYearProduct(departmentKey: String, year: Int): String =
        "university_${departmentKey}_y${year}_full_year"

    // Maps the existing academicDepartment display strings to stable keys.
    fun departmentKeyFor(displayName: String): String {
        val lower = displayName.lowercase()
        return when {
            lower.contains("account") -> "accounting"
            lower.contains("econ") -> "economics"
            lower.contains("computer") || lower.contains("software") -> "computer_science"
            lower.contains("electr") -> "electrical"
            lower.contains("mechanic") -> "mechanical"
            lower.contains("law") -> "law"
            lower.contains("manage") || lower.contains("business") -> "management"
            else -> lower.replace(Regex("[^a-z0-9]+"), "_").trim('_')
        }
    }

    fun yearNumberFor(yearLabel: String): Int {
        val digits = Regex("\\d+").find(yearLabel)?.value
        return digits?.toIntOrNull() ?: 2
    }

    // ---------------------------------------------------------------------
    // Freshman semester membership (derived from the seeded curriculum split)
    // ---------------------------------------------------------------------
    private val NATURAL_SEM1_SUBJECTS = setOf(
        "freshman_nat_english_1", "freshman_nat_psychology", "freshman_nat_geography",
        "freshman_nat_critical_thinking", "freshman_nat_physical_fitness",
        "freshman_nat_maths", "freshman_nat_physics", "freshman_nat_history"
    )
    private val SOCIAL_SEM1_SUBJECTS = setOf(
        "freshman_soc_civics", "freshman_soc_anthropology", "freshman_soc_english_1",
        "freshman_soc_global_trends", "freshman_soc_economics",
        "freshman_soc_emerging_tech", "freshman_soc_entrepreneurship"
    )

    // Backward compatibility ------------------------------------------------

    /**
     * Legacy package ids (from the pre-scoped entitlement model) map to the
     * product scopes the old whole-package purchases covered, so existing
     * purchases keep working.
     */
    fun legacyPackageToEntitlements(packageId: String): List<String> = when (packageId) {
        "freshman_natural", "freshman" -> expandEntitlements("freshman_natural_science_y1_full_year")
        "freshman_social" -> expandEntitlements("freshman_social_science_y1_full_year")
        else -> emptyList() // department/euee/etc. retain their own legacy handling
    }
}
