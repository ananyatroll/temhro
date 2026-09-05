package com.example.ads

/**
 * Centralized AdMob advertising configuration for the Tinat Android application.
 * Values are managed dynamically via [AdManager].
 */
object AdConfig {
    // Master switch to enable or disable all advertisements
    val ADS_ENABLED: Boolean
        get() = AdManager.ADS_ENABLED

    // Independent switches for specific ad formats
    val BANNER_ADS_ENABLED: Boolean
        get() = AdManager.BANNER_ADS_ENABLED

    val INTERSTITIAL_ADS_ENABLED: Boolean
        get() = AdManager.INTERSTITIAL_ADS_ENABLED

    // Real Production AdMob Ad Unit IDs
    const val BANNER_AD_UNIT_ID: String = AdManager.BANNER_AD_UNIT_ID
    const val INTERSTITIAL_AD_UNIT_ID: String = AdManager.INTERSTITIAL_AD_UNIT_ID

    // Test Ad Unit IDs
    const val TEST_BANNER_AD_UNIT_ID: String = AdManager.TEST_BANNER_AD_UNIT_ID
    const val TEST_INTERSTITIAL_AD_UNIT_ID: String = AdManager.TEST_INTERSTITIAL_AD_UNIT_ID

    // Interstitial display probability (25% chance)
    const val INTERSTITIAL_DISPLAY_PROBABILITY: Float = AdManager.INTERSTITIAL_DISPLAY_PROBABILITY

    // Frequency cap for interstitial advertisements (3 minutes = 180000 ms)
    const val MIN_INTERSTITIAL_INTERVAL_MS: Long = AdManager.MIN_INTERSTITIAL_INTERVAL_MS
}
