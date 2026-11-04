package com.example.gemini

import android.app.Application
import android.content.pm.PackageManager
import android.content.pm.Signature
import android.os.Build
import android.widget.Toast
import com.example.gemini.data.adb.AdbConnectManager
import com.example.gemini.data.updater.PluginUpdateCheckWorker
import com.example.gemini.data.updater.UpdateCheckWorker
import com.telemetrydeck.sdk.TelemetryDeck
import java.security.MessageDigest

class GravityPhoneApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        UpdateCheckWorker.schedule(this)
        PluginUpdateCheckWorker.schedule(this)
        // Reconnects adb when Wireless debugging turns back on
        AdbConnectManager.init(this)

        try {
            TelemetryDeck.start(
                this,
                TelemetryDeck.Builder()
                    .appID("D98008E6-EEE4-4BBC-97A2-E3790251AECF")
                    .namespace("com.gravityphone")
                    .defaultUser(SimpleTelemetryId.getUniqueUserId(this))
                    .testMode(BuildConfig.DEBUG)
                    .showDebugLogs(BuildConfig.DEBUG)
                    .sendNewSessionBeganSignal(true)
            )
        } catch (_: Exception) {
            // Analytics optional
        }
    }
}
