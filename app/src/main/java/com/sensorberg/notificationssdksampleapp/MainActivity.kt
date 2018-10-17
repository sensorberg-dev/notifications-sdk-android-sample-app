package com.sensorberg.notificationssdksampleapp

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import com.sensorberg.notifications.sdk.Action
import com.sensorberg.notifications.sdk.Conversion
import com.sensorberg.permissionbitte.BitteBitte
import com.sensorberg.permissionbitte.PermissionBitte

class MainActivity : AppCompatActivity(), BitteBitte {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            val (fragment, tag) =
                    if (App.isSdkInitialized()) Pair(ScreenMain(), TAG_MAIN)
                    else Pair(ScreenSetup(), TAG_SETUP)
            supportFragmentManager
                    .beginTransaction()
                    .replace(android.R.id.content, fragment, tag)
                    .commit()
        }
        PermissionBitte.ask(this, this)
        onIntent(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        onIntent(intent)
    }

    private fun onIntent(i: Intent?) {
        i?.getParcelableExtra<Action>("action")?.let {
            App.sdk.setConversion(it, Conversion.Success)
        }
    }

    override fun yesYouCan() {}

    override fun askNicer() {
        AlertDialog.Builder(this)
                .setTitle("Do it")
                .setMessage("You really have to accept the permission")
                .setPositiveButton("Sure") { _, _ -> PermissionBitte.ask(this, this) }
                .setNegativeButton("OK") { _, _ -> PermissionBitte.ask(this, this) }
                .setCancelable(false)
                .show()
    }

    override fun noYouCant() {
        PermissionBitte.goToSettings(this)
    }

    companion object {
        const val TAG_MAIN = "screen_main"
        const val TAG_SETUP = "screen_setup"
        const val TAG_AD_ID = "screen_ad_id"
        const val TAG_ATTRS = "screen_attrs"
    }
}
