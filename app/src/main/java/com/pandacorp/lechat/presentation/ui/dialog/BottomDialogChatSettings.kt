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
import com.pandacorp.lechat.presentation.ui.adapter.spinner.SpinnerAdapter
import com.pandacorp.lechat.utils.PreferenceHandler
import com.pandacorp.lechat.utils.getArray

class BottomDialogChatSettings(context: Context) : BottomSheetDialog(context) {
    private val binding: DialogChatSettingsBinding by lazy {
        // Use by lazy because setOnClearChatClickListener is called before onCreate for some reason
        DialogChatSettingsBinding.inflate(layoutInflater)
    }
    private val modelsAdapter = SpinnerAdapter(binding.modelSpinner).apply {
        setItems(getArray(R.array.Models_values).map { it })
    }

    fun setOnClearChatClickListener(onClickListener: View.OnClickListener) {
        binding.clearChatButton.setOnClickListener(onClickListener)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.maxTokensInputEditText.setText(PreferenceHandler.maxTokens.toString())
        binding.temperatureInputEditText.setText(PreferenceHandler.temperature.toString())
        binding.frequencyPenaltyInputEditText.setText(PreferenceHandler.frequencyPenalty.toString())
        binding.topPInputEditText.setText(PreferenceHandler.topP.toString())
        binding.modelSpinnerCardView.setOnClickListener {
            binding.modelSpinner.showOrDismiss()
        }
        binding.modelSpinner.isClickable = false
        binding.modelSpinner.setSpinnerAdapter(modelsAdapter)
        binding.modelSpinner.selectItemByIndex(getArray(R.array.Models_values).indexOf(PreferenceHandler.modelValue))
        binding.modelSpinner.lifecycleOwner = this

        setOnDismissListener {
            val temperature = binding.temperatureInputEditText.text.toString().toFloatOrNull()
            if (temperature != null) {
                PreferenceHandler.temperature = temperature
            }

            var maxTokens = binding.maxTokensInputEditText.text.toString().toIntOrNull()
            maxTokens = maxOf(maxTokens ?: 0, 0)
            PreferenceHandler.maxTokens = maxTokens

            val frequencyPenalty = binding.frequencyPenaltyInputEditText.text.toString().toFloatOrNull()
            if (frequencyPenalty != null) {
                PreferenceHandler.frequencyPenalty = frequencyPenalty
            }
            val topP = binding.topPInputEditText.text.toString().toFloatOrNull()
            if (topP != null) {
                PreferenceHandler.topP = topP
            }

            val modelIndex = binding.modelSpinner.selectedIndex
            val modelValue = getArray(R.array.Models_values)[modelIndex]
            PreferenceHandler.modelValue = modelValue
            binding.modelSpinner.dismiss()
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