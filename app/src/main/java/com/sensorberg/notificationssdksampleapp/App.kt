package com.sensorberg.notificationssdksampleapp

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.sensorberg.notifications.sdk.NotificationsSdk
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        app = this
        Timber.plant(Timber.DebugTree())

        if (isSdkInitialized()) {
            buildSdk()
        }
    }

    companion object {

        lateinit var sdk: NotificationsSdk

        private lateinit var app: Application
        private val prefs: SharedPreferences by lazy { app.getSharedPreferences("data", Context.MODE_PRIVATE) }
        private const val KEY_BASE_URL = "notifications.baseUrl"
        private const val KEY_API_KEY = "notifications.apiKey"

        fun isSdkInitialized(): Boolean {
            return prefs.getString(App.KEY_BASE_URL, null)?.isNotEmpty() == true &&
                    prefs.getString(App.KEY_API_KEY, null)?.isNotEmpty() == true
        }

        fun initNotificationsSdk(baseUrl: String, apiKey: String) {
            prefs.edit().apply {
                putString(KEY_BASE_URL, baseUrl)
                putString(KEY_API_KEY, apiKey)
            }.apply()
            buildSdk()
        }

        private fun buildSdk() {
            sdk = NotificationsSdk.with(app)
                    .enableHttpLogs()
                    .setBaseUrl(prefs.getString(KEY_BASE_URL, null))
                    .setApiKey(prefs.getString(KEY_API_KEY, null))
                    .build()
        }

    }
}