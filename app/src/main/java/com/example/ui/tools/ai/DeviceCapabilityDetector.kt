package com.example.ui.tools.ai

import android.content.Context
import android.os.Build

object DeviceCapabilityDetector {

    /**
     * Safely checks if on-device generative AI acceleration or AICore is present.
     * Never crashes on low-end or older Android devices.
     */
    fun isLocalAiSupported(context: Context): Boolean {
        return try {
            // Android 14+ AICore or on-device GenAI package detection
            if (Build.VERSION.SDK_INT >= 34) {
                val packageManager = context.packageManager
                val packages = packageManager.getInstalledPackages(0)
                packages.any {
                    it.packageName.contains("aicore", ignoreCase = true) ||
                    it.packageName.contains("ondevicepersonalization", ignoreCase = true)
                }
            } else {
                false
            }
        } catch (e: Throwable) {
            false
        }
    }

    /**
     * Returns the appropriate AiProvider.
     * Always returns TamheroSmartEngine for predictable, fast, 100% offline functionality.
     */
    fun getProvider(context: Context): AiProvider {
        // Transparent fallback to TamheroSmartEngine
        return TamheroSmartEngine()
    }
}
