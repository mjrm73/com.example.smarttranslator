package com.example.smarttranslator

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // التحقق من إذن الظهور فوق التطبيقات الأخرى
        if (!Settings.canDrawOverlays(this)) {
            val intent = Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:$packageName")
            )
            startActivity(intent)
        } else {
            startOverlayService()
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        // إذا تم منح الإذن بعد الرجوع من الإعدادات
        if (Settings.canDrawOverlays(this)) {
            startOverlayService()
            finish()
        }
    }

    private fun startOverlayService() {
        val intent = Intent(this, OverlayWebViewService::class.java)
        startService(intent)
    }
}