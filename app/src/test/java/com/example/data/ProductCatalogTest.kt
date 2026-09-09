package com.example.data

import org.junit.Assert.*
import org.junit.Test

class ProductCatalogTest {

    @Test
    fun testFreshmanProductExpansionAndIsolation() {
        // Natural Science Sem 1 & Sem 2
        val sem1Nat = ProductCatalog.get("freshman_natural_science_y1_sem1")
        assertNotNull(sem1Nat)
        assertEquals(300, sem1Nat?.amount)
        assertEquals(listOf("freshman_natural_science_y1_sem1"), sem1Nat?.entitlementIds())

        val sem2Nat = ProductCatalog.get("freshman_natural_science_y1_sem2")
        assertNotNull(sem2Nat)
        assertEquals(300, sem2Nat?.amount)

        // Natural Science Full Year Bundle
        val fullNat = ProductCatalog.get("freshman_natural_science_y1_full_year")
        assertNotNull(fullNat)
        assertEquals(500, fullNat?.amount)
        assertEquals(
            listOf("freshman_natural_science_y1_sem1", "freshman_natural_science_y1_sem2"),
            fullNat?.entitlementIds()
        )

        // Check grants logic
        assertTrue(ProductCatalog.grants("freshman_natural_science_y1_full_year", "freshman_natural_science_y1_sem1"))
        assertTrue(ProductCatalog.grants("freshman_natural_science_y1_full_year", "freshman_natural_science_y1_sem2"))
        assertFalse(ProductCatalog.grants("freshman_natural_science_y1_sem1", "freshman_natural_science_y1_sem2"))

        // Stream Isolation test: Natural Science purchase MUST NOT grant Social Science
        assertFalse(ProductCatalog.grants("freshman_natural_science_y1_full_year", "freshman_social_science_y1_sem1"))
    }

    @Test
    fun testCocProductsUnconfiguredPrice() {
        val cocMed = ProductCatalog.get("coc_medical")
        assertNotNull(cocMed)
        assertNull(cocMed?.amount)
        assertFalse(cocMed!!.isPurchasable)

        val cocLaw = ProductCatalog.get("coc_law")
        assertNotNull(cocLaw)
        assertNull(cocLaw?.amount)
        assertFalse(cocLaw!!.isPurchasable)

        val cocEng = ProductCatalog.get("coc_engineering")
        assertNotNull(cocEng)
        assertNull(cocEng?.amount)
        assertFalse(cocEng!!.isPurchasable)
    }

    @Test
    fun testRequiredProductForSubject() {
        // Natural Sem 1 subject
        val reqNatSem1 = ProductCatalog.requiredProductForSubject("freshman_nat_english_1", "freshman_natural")
        assertEquals("freshman_natural_science_y1_sem1", reqNatSem1)

        // Natural Sem 2 subject
        val reqNatSem2 = ProductCatalog.requiredProductForSubject("freshman_nat_applied_maths", "freshman_natural")
        assertEquals("freshman_natural_science_y1_sem2", reqNatSem2)

        // Social Sem 1 subject
        val reqSocSem1 = ProductCatalog.requiredProductForSubject("freshman_soc_civics", "freshman_social")
        assertEquals("freshman_social_science_y1_sem1", reqSocSem1)

        // COC subjects
        assertEquals("coc_medical", ProductCatalog.requiredProductForSubject("coc_med_1", "coc_medical"))
        assertEquals("coc_law", ProductCatalog.requiredProductForSubject("coc_law_1", "coc_law"))
    }

    @Test
    fun testLegacyCompatibilityMapping() {
        assertTrue(ProductCatalog.legacyMapsTo("freshman_natural", "freshman_natural_science_y1_sem1"))
        assertTrue(ProductCatalog.legacyMapsTo("freshman_natural", "freshman_natural_science_y1_sem2"))
        assertTrue(ProductCatalog.legacyMapsTo("freshman_social", "freshman_social_science_y1_sem1"))
        assertTrue(ProductCatalog.legacyMapsTo("euee_natural", "euee_natural"))
    }
}
