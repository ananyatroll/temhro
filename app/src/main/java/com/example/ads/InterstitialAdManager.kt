package com.example.ads

import android.app.Activity
import android.content.Context

/**
 * Interstitial manager delegating to [AdManager] singleton.
 */
object InterstitialAdManager {
    fun preload(context: Context) {
        AdManager.preloadInterstitial(context)
    }

    fun showIfAllowed(activity: Activity, onAdDismissedOrSkipped: () -> Unit) {
        AdManager.showInterstitialIfAllowed(activity, onAdDismissedOrSkipped)
    }
}

