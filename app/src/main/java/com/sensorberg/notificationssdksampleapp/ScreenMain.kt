package com.sensorberg.notificationssdksampleapp

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class ScreenMain : Fragment() {

    private lateinit var root: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        root = inflater.inflate(R.layout.screen_main, container, false)
        root.findViewById<Button>(R.id.btnEnable).setOnClickListener { enableDisable() }
        root.findViewById<Button>(R.id.btnChangeAttrs).setOnClickListener { changeAttrs() }
        root.findViewById<Button>(R.id.btnChangeAdId).setOnClickListener { changeAdId() }
        return root
    }

    override fun onResume() {
        super.onResume()
        updateTextEnable()
    }

    private fun enableDisable() {
        App.sdk.setEnabled(!App.sdk.isEnabled())
        updateTextEnable()
    }

    private fun changeAttrs() {
        ScreenAttrs().show(activity!!.supportFragmentManager, MainActivity.TAG_ATTRS)
    }

    private fun changeAdId() {
        ScreenAdId().show(activity!!.supportFragmentManager, MainActivity.TAG_AD_ID)
    }

    private fun updateTextEnable() {
        val enabled = if (App.sdk.isEnabled()) "enabled" else "disabled"
        root.findViewById<TextView>(R.id.txtEnable).text = "SDK is $enabled"
    }
}