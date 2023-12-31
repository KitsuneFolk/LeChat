package com.pandacorp.togetheraichat.presentation.ui.dialog

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.WindowManager
import androidx.annotation.CallSuper
import androidx.preference.PreferenceManager

abstract class CustomDialog(context: Context) : Dialog(context) {
    var onValueAppliedListener: (value: String) -> Unit = {}

    protected val sp: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(context)
    }

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.apply {
            // Remove the default background
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            // Remove the shadow
            clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        }
    }

}