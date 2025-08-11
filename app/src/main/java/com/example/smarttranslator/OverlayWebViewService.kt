package com.example.smarttranslator

import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.os.IBinder
import android.view.WindowManager
import android.webkit.WebView
import android.webkit.WebViewClient
import android.view.ViewGroup
import android.widget.FrameLayout

class OverlayWebViewService : Service() {
    private var windowManager: WindowManager? = null
    private var container: FrameLayout? = null

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager

        container = FrameLayout(this)
        val webView = WebView(this).apply {
            settings.javaScriptEnabled = true
            settings.defaultTextEncodingName = "utf-8"
            webViewClient = WebViewClient()
            loadUrl("file:///android_asset/index.html")
            layoutParams = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }
        container?.addView(webView)

        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
            PixelFormat.TRANSLUCENT
        )
        windowManager?.addView(container, params)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (container != null) {
            windowManager?.removeView(container)
            container = null
        }
    }
}