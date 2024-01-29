package com.pandacorp.lechat.presentation.ui.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.WindowManager
import androidx.annotation.CallSuper

abstract class CustomDialog(context: Context) : Dialog(context) {
    var onValueAppliedListener: (value: String) -> Unit = {}

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