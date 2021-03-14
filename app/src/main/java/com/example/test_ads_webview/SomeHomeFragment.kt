package com.example.test_ads_webview

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.admanager.AdManagerAdRequest
import com.google.android.gms.ads.admanager.AdManagerAdView

class SomeHomeFragment : Fragment() {

    private lateinit var nextLevelButton: Button
    private lateinit var levelTextView: TextView
    private lateinit var adContainer: RelativeLayout
    private var adView: AdManagerAdView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = LayoutInflater.from(context).inflate(R.layout.fragment_home, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adContainer = view.findViewById(R.id.fragment_details_ad_container)
        nextLevelButton = view.findViewById(R.id.next_level_button)
        levelTextView = view.findViewById(R.id.level)

        nextLevelButton.isEnabled = true

        nextLevelButton.setOnClickListener {
            CustomNavigator.navigateTo(WebViewFragment(), "webview")
        }

        loadAd(adContainer)
    }


    private fun loadAd(container: RelativeLayout) {
        val adRequest: AdManagerAdRequest = AdManagerAdRequest.Builder().build()

        adView = AdManagerAdView(container.context).apply {
            layoutParams = RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            setAdSizes(AdSize.SMART_BANNER)
            adUnitId = "ca-app-pub-3940256099942544/6300978111"
            adListener = testAdListener
        }


        adView?.loadAd(adRequest)
    }

    private val testAdListener = object : AdListener() {
        override fun onAdFailedToLoad(p0: LoadAdError?) {
            Log.d("BIER", "onAdFailedToLoad with p0 = [${p0.toString()}]")
        }

        override fun onAdLoaded() {
            super.onAdLoaded()
            adContainer.addView(adView)
            Log.d("BIER", "ad loaded")
        }
    }

    override fun onResume() {
        super.onResume()
        adView?.resume()
    }

    override fun onDestroyView() {
        adContainer.removeView(adView)
        adView?.pause()
        adView?.removeAllViews()
        adView?.destroy()
        adView = null
        super.onDestroyView()
    }

    companion object {
        // Remove the line below after defining your own ad unit ID.
        private const val TOAST_TEXT = "Test ads are being shown. " +
                "To show live ads, replace the ad unit ID in res/values/strings.xml " +
                "with your own ad unit ID."

        operator fun invoke(): WebViewFragment = WebViewFragment()
    }
}
