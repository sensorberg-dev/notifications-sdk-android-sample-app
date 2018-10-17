package com.sensorberg.notificationssdksampleapp

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.*
import android.widget.Button
import android.widget.EditText

class ScreenAdId : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.screen_adid, container, false)
        root.findViewById<Button>(R.id.button).setOnClickListener {
            var text: String? = root.findViewById<EditText>(R.id.adid).text.toString().trim()
            if (text?.length == 0) text = null
            App.sdk.setAdvertisementId(text)
            dismiss()
        }
        return root
    }
}