package com.sensorberg.notificationssdksampleapp

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText

class ScreenAttrs : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.screen_attrs, container, false)
        root.findViewById<Button>(R.id.button).setOnClickListener {
            val map = mutableMapOf<String, String>()
            for (i in 0 until 3) getAttribute(root, i)?.let { map.put(it.first, it.second) }
            App.sdk.setAttributes(map)
            dismiss()
        }
        return root
    }

    private fun getAttribute(root: View, index: Int): Pair<String, String>? {
        val key = extractString(root, index, "key")
        val value = extractString(root, index, "value")
        return if (key != null && value != null) {
            Pair(key, value)
        } else null
    }

    private fun extractString(root: View, index: Int, type: String): String? {
        val res = root.context.resources
        val id = res.getIdentifier("${type}_$index", "id", "com.sensorberg.notificationssdksampleapp")
        val string = root.findViewById<EditText>(id).text.toString().trim()
        return if (string.isNotBlank()) string else null
    }
}