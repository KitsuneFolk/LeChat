package com.pandacorp.lechat.presentation.ui.dialog

import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.pandacorp.lechat.R
import com.pandacorp.lechat.databinding.DialogChatSettingsBinding
import com.pandacorp.lechat.utils.PreferenceHandler
import com.pandacorp.lechat.utils.getArray

class BottomDialogChatSettings(context: Context) : BottomSheetDialog(context) {
    private val binding: DialogChatSettingsBinding by lazy {
        DialogChatSettingsBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.maxTokensEditText.setText(PreferenceHandler.maxTokens.toString())
        binding.temperatureEditText.setText(PreferenceHandler.temperature.toString())
        binding.frequencyPenaltyEditText.setText(PreferenceHandler.frequencyPenalty.toString())
        binding.topPEditText.setText(PreferenceHandler.topP.toString())
        binding.modelSpinner.setSelectedItem(PreferenceHandler.modelValue)

        setOnDismissListener {
            val temperature = binding.temperatureEditText.text.toString().toFloatOrNull()
            if (temperature != null) {
                PreferenceHandler.temperature = temperature
            }

            var maxTokens = binding.maxTokensEditText.text.toString().toIntOrNull()
            maxTokens = maxOf(maxTokens ?: 0, 0)
            PreferenceHandler.maxTokens = maxTokens

            val frequencyPenalty = binding.frequencyPenaltyEditText.text.toString().toFloatOrNull()
            if (frequencyPenalty != null) {
                PreferenceHandler.frequencyPenalty = frequencyPenalty
            }
            val topP = binding.topPEditText.text.toString().toFloatOrNull()
            if (topP != null) {
                PreferenceHandler.topP = topP
            }

            var modelValue = binding.modelSpinner.getEditTextValue()
            // If the EditText is empty, then the model is picked from the spinner
            if (modelValue.isEmpty()) {
                modelValue = getArray(R.array.Models_values)[binding.modelSpinner.selectedIndex]
            }
            PreferenceHandler.modelValue = modelValue
        }
    }

    override fun show() {
        super.show()
        val view = findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        view!!.post {
            // Expand the dialog in the landscape mode
            val behavior = BottomSheetBehavior.from(view)
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
            // Set the background color to colorPrimary
            val tv = TypedValue()
            context.theme.resolveAttribute(android.R.attr.colorPrimaryDark, tv, true)
            view.backgroundTintList = ColorStateList.valueOf(tv.data)
        }
    }
}