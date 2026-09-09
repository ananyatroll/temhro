package com.example.ads

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError

/**
 * Reusable Google AdMob Anchored Adaptive Bottom Banner Ad component.
 * Displays a small horizontal strip at the bottom of approved screens.
 * Automatically adapts to screen orientation and device width while reserving layout space.
 * Includes automated fallback to test unit if production ad unit is pending fill.
 */
@Composable
fun TinatBannerAd(
    modifier: Modifier = Modifier,
    adUnitId: String = AdManager.BANNER_AD_UNIT_ID,
    applyInsets: Boolean = true
) {
    if (!AdManager.ADS_ENABLED || !AdManager.BANNER_ADS_ENABLED) {
        return
    }

    val context = LocalContext.current
    val density = LocalDensity.current
    val lifecycleOwner = LocalLifecycleOwner.current

    var isAdLoaded by remember { mutableStateOf(false) }
    var currentAdUnit by remember { mutableStateOf(adUnitId) }
    var adViewInstance by remember { mutableStateOf<AdView?>(null) }
    var hasPermanentlyFailed by remember { mutableStateOf(false) }

    if (hasPermanentlyFailed) {
        return
    }

    // Manage AdView lifecycle across activity states
    DisposableEffect(lifecycleOwner, adViewInstance) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> adViewInstance?.resume()
                Lifecycle.Event.ON_PAUSE -> adViewInstance?.pause()
                Lifecycle.Event.ON_DESTROY -> {
                    adViewInstance?.destroy()
                    adViewInstance = null
                }
                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            adViewInstance?.destroy()
            adViewInstance = null
        }
    }

    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .then(if (applyInsets) Modifier.navigationBarsPadding() else Modifier)
            .padding(top = 2.dp, bottom = 2.dp),
        contentAlignment = Alignment.Center
    ) {
        val widthDp = maxWidth.value.toInt().coerceAtLeast(320)
        val adaptiveAdSize = remember(widthDp) {
            AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context, widthDp)
        }
        val bannerHeightDp = with(density) { adaptiveAdSize.getHeightInPixels(context).toDp() }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .then(
                    if (isAdLoaded) Modifier.height(bannerHeightDp)
                    else Modifier.wrapContentHeight()
                ),
            contentAlignment = Alignment.Center
        ) {
            key(currentAdUnit) {
                AndroidView(
                    modifier = Modifier.fillMaxWidth(),
                    factory = { ctx ->
                        AdView(ctx).apply {
                            setAdSize(adaptiveAdSize)
                            setAdUnitId(currentAdUnit)
                            adListener = object : AdListener() {
                                override fun onAdLoaded() {
                                    Log.d("TinatBannerAd", "Anchored banner loaded successfully ($currentAdUnit).")
                                    isAdLoaded = true
                                }

                                override fun onAdFailedToLoad(error: LoadAdError) {
                                    Log.w("TinatBannerAd", "Anchored banner failed on $currentAdUnit: ${error.message} (code: ${error.code})")
                                    isAdLoaded = false

                                    // If primary production ID fails (e.g. no fill during testing), switch state to test unit
                                    // Compose key(currentAdUnit) will safely create a new fresh AdView instance
                                    if (currentAdUnit != AdManager.TEST_BANNER_AD_UNIT_ID) {
                                        Log.d("TinatBannerAd", "Switching to standard test ad unit...")
                                        currentAdUnit = AdManager.TEST_BANNER_AD_UNIT_ID
                                    } else {
                                        Log.d("TinatBannerAd", "All banner ad units unavailable. Cleanly collapsing banner space.")
                                        hasPermanentlyFailed = true
                                    }
                                }
                            }
                            adViewInstance = this
                            val request = AdRequest.Builder().build()
                            loadAd(request)
                        }
                    },
                    update = { adView ->
                        adViewInstance = adView
                    }
                )
            }
        }
    }
}
