package com.example.ads

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.ump.ConsentInformation
import com.google.android.ump.ConsentRequestParameters
import com.google.android.ump.UserMessagingPlatform
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.random.Random

/**
 * Centralized AdManager Singleton for the Tinat Android application.
 *
 * Provides:
 * 1. Production AdMob Ad Unit IDs with automated test fallback for reliable rendering.
 * 2. Non-blocking Google UMP Consent initialization.
 * 3. Robust Interstitial preloading with automatic retry on transition points.
 * 4. Frequency capping (3 minutes = 180,000 ms) and probability throttling (25%).
 * 5. Safe main-thread callback guarantees so UI operations are never blocked.
 */
object AdManager {
    private const val TAG = "AdManager"

    // Master feature flags
    var ADS_ENABLED: Boolean = true
    var BANNER_ADS_ENABLED: Boolean = true
    var INTERSTITIAL_ADS_ENABLED: Boolean = true

    // Real Production AdMob Ad Unit IDs
    const val BANNER_AD_UNIT_ID: String = "ca-app-pub-3415839336282886/4115800948"
    const val INTERSTITIAL_AD_UNIT_ID: String = "ca-app-pub-3415839336282886/1988331053"

    // Google AdMob standard sample test IDs (used as fallback when production account is pending verification or offline)
    const val TEST_BANNER_AD_UNIT_ID: String = "ca-app-pub-3940256099942544/6300978111"
    const val TEST_INTERSTITIAL_AD_UNIT_ID: String = "ca-app-pub-3940256099942544/1033173712"

    // Interstitial display probability (25% chance at natural transition points)
    const val INTERSTITIAL_DISPLAY_PROBABILITY: Float = 0.25f

    // Minimum interval between interstitials (3 minutes = 180,000 ms)
    const val MIN_INTERSTITIAL_INTERVAL_MS: Long = 180000L

    // Internal state management
    private val isMobileAdsInitialized = AtomicBoolean(false)
    private val isInitializing = AtomicBoolean(false)
    private val isInterstitialLoading = AtomicBoolean(false)

    private var interstitialAd: InterstitialAd? = null
    private var lastInterstitialShownTime: Long = 0L
    private val mainHandler = Handler(Looper.getMainLooper())

    /**
     * Initializes Google Mobile Ads SDK and Google User Messaging Platform (UMP) consent.
     */
    fun initialize(activity: Activity, onComplete: (() -> Unit)? = null) {
        if (!ADS_ENABLED) {
            Log.d(TAG, "Ads are disabled in configuration.")
            safeCallback(onComplete)
            return
        }

        if (isMobileAdsInitialized.get()) {
            safeCallback(onComplete)
            return
        }

        if (!isInitializing.compareAndSet(false, true)) {
            safeCallback(onComplete)
            return
        }

        try {
            val params = ConsentRequestParameters.Builder()
                .setTagForUnderAgeOfConsent(false)
                .build()

            val consentInformation: ConsentInformation = UserMessagingPlatform.getConsentInformation(activity)

            consentInformation.requestConsentInfoUpdate(
                activity,
                params,
                {
                    UserMessagingPlatform.loadAndShowConsentFormIfRequired(activity) { formError ->
                        if (formError != null) {
                            Log.w(TAG, "UMP Consent form note: ${formError.message} (code: ${formError.errorCode})")
                        }
                        startMobileAdsInitialization(activity, onComplete)
                    }
                },
                { requestConsentError ->
                    Log.w(TAG, "UMP Consent update failed (${requestConsentError.message}). Initializing Mobile Ads directly.")
                    startMobileAdsInitialization(activity, onComplete)
                }
            )

            // If consent was previously gathered or available, proceed immediately
            if (consentInformation.canRequestAds()) {
                startMobileAdsInitialization(activity, onComplete)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Exception during consent flow: ${e.message}", e)
            startMobileAdsInitialization(activity, onComplete)
        }
    }

    /**
     * Initializes the MobileAds SDK and configures global settings.
     */
    private fun startMobileAdsInitialization(context: Context, onComplete: (() -> Unit)?) {
        if (isMobileAdsInitialized.get()) {
            safeCallback(onComplete)
            return
        }

        val appContext = context.applicationContext
        try {
            // Configure general family-safe ad settings
            val requestConfig = RequestConfiguration.Builder()
                .setMaxAdContentRating(RequestConfiguration.MAX_AD_CONTENT_RATING_G)
                .build()
            MobileAds.setRequestConfiguration(requestConfig)

            MobileAds.initialize(appContext) { initStatus ->
                isMobileAdsInitialized.set(true)
                isInitializing.set(false)
                Log.d(TAG, "Google Mobile Ads SDK successfully initialized: ${initStatus.adapterStatusMap}")

                if (INTERSTITIAL_ADS_ENABLED) {
                    preloadInterstitial(appContext)
                }
                safeCallback(onComplete)
            }
        } catch (e: Exception) {
            Log.e(TAG, "MobileAds initialization error: ${e.message}", e)
            isInitializing.set(false)
            safeCallback(onComplete)
        }
    }

    /**
     * Preloads an interstitial advertisement with automatic fallback to test unit if production is pending.
     */
    fun preloadInterstitial(context: Context, isRetryWithFallback: Boolean = false) {
        if (!ADS_ENABLED || !INTERSTITIAL_ADS_ENABLED) {
            return
        }

        if (interstitialAd != null) {
            return
        }

        if (!isInterstitialLoading.compareAndSet(false, true)) {
            return
        }

        val targetAdUnitId = if (isRetryWithFallback) TEST_INTERSTITIAL_AD_UNIT_ID else INTERSTITIAL_AD_UNIT_ID
        val adRequest = AdRequest.Builder().build()

        mainHandler.post {
            InterstitialAd.load(
                context.applicationContext,
                targetAdUnitId,
                adRequest,
                object : InterstitialAdLoadCallback() {
                    override fun onAdLoaded(ad: InterstitialAd) {
                        Log.d(TAG, "Interstitial ad successfully preloaded (unit: $targetAdUnitId).")
                        interstitialAd = ad
                        isInterstitialLoading.set(false)
                    }

                    override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                        Log.w(TAG, "Interstitial failed to load on $targetAdUnitId: ${loadAdError.message} (code: ${loadAdError.code})")
                        interstitialAd = null
                        isInterstitialLoading.set(false)

                        // If primary production ID had no fill or error, try the test unit once
                        if (!isRetryWithFallback) {
                            Log.d(TAG, "Retrying interstitial preload with standard test unit...")
                            preloadInterstitial(context, isRetryWithFallback = true)
                        }
                    }
                }
            )
        }
    }

    /**
     * Shows an interstitial advertisement at an eligible transition if throttles pass.
     * Guaranteed to execute [onAdDismissedOrSkipped] safely without blocking the user.
     */
    fun showInterstitialIfAllowed(activity: Activity, onAdDismissedOrSkipped: () -> Unit) {
        val safeDone = {
            mainHandler.post { onAdDismissedOrSkipped() }
        }

        if (!ADS_ENABLED || !INTERSTITIAL_ADS_ENABLED) {
            safeDone()
            return
        }

        if (activity.isFinishing || activity.isDestroyed) {
            safeDone()
            return
        }

        // Condition 1: Minimum 3-minute interval throttle
        val currentTime = System.currentTimeMillis()
        val elapsedTime = currentTime - lastInterstitialShownTime
        if (elapsedTime < MIN_INTERSTITIAL_INTERVAL_MS) {
            Log.d(TAG, "Interstitial throttled by interval (${elapsedTime / 1000}s elapsed). Skipping.")
            safeDone()
            return
        }

        // Condition 2: 25% probability roll
        val roll = Random.nextFloat()
        if (roll >= INTERSTITIAL_DISPLAY_PROBABILITY) {
            Log.d(TAG, "Interstitial skipped by probability roll ($roll >= $INTERSTITIAL_DISPLAY_PROBABILITY).")
            safeDone()
            return
        }

        // Condition 3: Check ready ad
        val currentAd = interstitialAd
        if (currentAd == null) {
            Log.d(TAG, "No interstitial in memory. Requesting preload for next transition.")
            preloadInterstitial(activity.applicationContext)
            safeDone()
            return
        }

        currentAd.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                Log.d(TAG, "Interstitial ad dismissed by user.")
                lastInterstitialShownTime = System.currentTimeMillis()
                interstitialAd = null
                preloadInterstitial(activity.applicationContext)
                safeDone()
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                Log.w(TAG, "Failed to show interstitial: ${adError.message}")
                interstitialAd = null
                preloadInterstitial(activity.applicationContext)
                safeDone()
            }

            override fun onAdShowedFullScreenContent() {
                Log.d(TAG, "Interstitial ad presented.")
                interstitialAd = null
            }
        }

        try {
            currentAd.show(activity)
        } catch (e: Exception) {
            Log.e(TAG, "Error displaying interstitial: ${e.message}", e)
            interstitialAd = null
            safeDone()
        }
    }

    fun findActivity(context: Context): Activity? {
        var currentContext = context
        while (currentContext is android.content.ContextWrapper) {
            if (currentContext is Activity) {
                return currentContext
            }
            currentContext = currentContext.baseContext
        }
        return null
    }

    private fun safeCallback(action: (() -> Unit)?) {
        if (action != null) {
            mainHandler.post { action() }
        }
    }
}

object AdsManager {
    fun findActivity(context: Context): Activity? = AdManager.findActivity(context)
    fun showInterstitialIfAllowed(activity: Activity, onAdDismissedOrSkipped: () -> Unit) =
        AdManager.showInterstitialIfAllowed(activity, onAdDismissedOrSkipped)
}

object InterstitialAdManager {
    fun showIfAllowed(activity: Activity, onComplete: () -> Unit) =
        AdManager.showInterstitialIfAllowed(activity, onComplete)
}

object AdConfig {
    const val ADS_ENABLED = true
    const val BANNER_ADS_ENABLED = true
    const val INTERSTITIAL_ADS_ENABLED = true
}
