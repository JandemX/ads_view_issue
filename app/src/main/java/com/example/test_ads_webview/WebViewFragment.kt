package com.example.test_ads_webview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment

/**
 * WebViewFragment, which shows only a web page inside a WebView.
 */
class WebViewFragment : Fragment() {

    private var webView: WebView? = null
    private var receiveErrorOccurred: Boolean = false

    private val webViewClient = object : WebViewClient() {
        override fun onPageFinished(inView: WebView, inUrl: String) {
            if (!receiveErrorOccurred) {
                webView?.isVisible = true
            }
        }

        override fun onReceivedError(
            view: WebView,
            errorCode: Int,
            description: String,
            failingUrl: String
        ) {
            webView?.isVisible = false
            receiveErrorOccurred = true
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_webview, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews(view)
        webView!!.settings.apply {
            builtInZoomControls = false
            displayZoomControls = false
            javaScriptEnabled = true
            loadWithOverviewMode = false
            useWideViewPort = true
        }
        webView!!.webViewClient = webViewClient
        webView!!.loadUrl( "www.google.com")
    }

    private fun initViews(view: View) {
        webView = view.findViewById(R.id.fragment_webview_content)
    }

    override fun onResume() {
        super.onResume()
        webView?.resumeTimers()
        webView?.onResume()
    }

    override fun onPause() {
        webView?.pauseTimers()
        webView?.onPause()
        super.onPause()
    }

    override fun onDestroyView() {
        (view as? ViewGroup)?.removeAllViews()
        webView?.removeAllViews()
        webView?.destroy()
        webView = null
        super.onDestroyView()
    }

    companion object {
        @JvmStatic
        operator fun invoke(): WebViewFragment = WebViewFragment()
    }
}

