package com.example.ads

import android.app.Activity
import android.content.Context

/**
 * Delegating wrapper for [AdManager] for seamless backward compatibility across the app.
 */
object AdsManager {
    fun initialize(activity: Activity, onComplete: (() -> Unit)? = null) {
        AdManager.initialize(activity, onComplete)
    }

    fun loadInterstitial(context: Context) {
        AdManager.preloadInterstitial(context)
    }

    fun showInterstitialIfAllowed(activity: Activity, onAdDismissedOrSkipped: () -> Unit) {
        AdManager.showInterstitialIfAllowed(activity, onAdDismissedOrSkipped)
    }

    fun findActivity(context: Context): Activity? {
        return AdManager.findActivity(context)
    }
}

