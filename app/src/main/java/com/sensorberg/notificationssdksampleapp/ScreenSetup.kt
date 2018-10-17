package com.sensorberg.notificationssdksampleapp

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class ScreenSetup : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.screen_setup, container, false)
        root.findViewById<Button>(R.id.setupButton).setOnClickListener { verifyInputAndInit() }
        root.findViewById<EditText>(R.id.baseUrl).setText(BuildConfig.BASE_URL)
        root.findViewById<EditText>(R.id.apiKey).setText(BuildConfig.API_KEY)
        return root
    }

    private fun verifyInputAndInit() {
        var verified = true
        val baseUrlView = view!!.findViewById<EditText>(R.id.baseUrl)
        val baseUrl = baseUrlView.text.toString()
        if (baseUrl.isEmpty() || !baseUrl.startsWith("http")) {
            verified = false
            baseUrlView.error = "Invalid base URL"
        }
        val apiKeyView = view!!.findViewById<EditText>(R.id.apiKey)
        val apiKey = apiKeyView.text.toString()
        if (apiKey.isEmpty() || apiKey.length < 6) {
            verified = false
            apiKeyView.error = "Invalid API key"
        }

        if (verified) {
            activity?.let { activity ->
                App.initNotificationsSdk(baseUrl, apiKey)
                Toast.makeText(activity, "Notifications SDK initialized. To change API key or URL clear this app storage", Toast.LENGTH_LONG).show()
                val intent = Intent(activity, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or
                        Intent.FLAG_ACTIVITY_NEW_TASK or
                        Intent.FLAG_ACTIVITY_CLEAR_TASK)
                activity.startActivity(intent)
                activity.finishAffinity()
                activity.finish()
            }
        }
    }
}