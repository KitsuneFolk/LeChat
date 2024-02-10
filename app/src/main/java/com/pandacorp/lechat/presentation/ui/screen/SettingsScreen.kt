package com.pandacorp.lechat.presentation.ui.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.pandacorp.lechat.R
import com.pandacorp.lechat.databinding.ScreenSettingsBinding
import com.pandacorp.lechat.presentation.ui.dialog.DialogListView
import com.pandacorp.lechat.utils.Constants
import com.pandacorp.lechat.utils.Constants.sp
import com.pandacorp.lechat.utils.PreferenceHandler
import com.pandacorp.lechat.utils.getArray
import com.pandacorp.lechat.utils.getPackageInfoCompat

class SettingsScreen : Fragment() {
    private var _binding: ScreenSettingsBinding? = null
    private val binding get() = _binding!!

    private val themeDialog by lazy {
        DialogListView(requireContext()).apply {
            onValueAppliedListener = {
                requireActivity().recreate()
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = ScreenSettingsBinding.inflate(inflater)

        initViews()

        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val dialogKey = when {
            themeDialog.isShowing -> Constants.Preferences.Key.THEME
            else -> null
        }

        outState.apply {
            putString(Constants.Dialogs.KEY, dialogKey)
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        when (savedInstanceState?.getString(Constants.Dialogs.KEY, null)) {
            Constants.Preferences.Key.THEME -> themeDialog.show()
        }
    }

    override fun onDestroy() {
        _binding = null
        themeDialog.dismiss()
        super.onDestroy()
    }

    private fun initViews() {
        binding.toolbarInclude.toolbar.apply {
            binding.toolbarInclude.toolbar.setTitle(R.string.settings)
            setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material)
            setNavigationOnClickListener {
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
        }

        binding.themeLayout.apply {
            setOnClickListener {
                themeDialog.show()
            }
            binding.themeTextView.apply {
                val themeKey = sp.getString(
                    Constants.Preferences.Key.THEME,
                    getString(R.string.defaultTheme)
                )!!
                text = getThemeFromKey(themeKey)
            }
        }

        binding.apiEditText.setText(sp.getString(Constants.Preferences.Key.API, ""))
        binding.saveApiKeyButton.setOnClickListener {
            val key = binding.apiEditText.text.toString()
            sp.edit().putString(Constants.Preferences.Key.API, key).apply()
            Constants.apiKey.postValue(key)
        }

        binding.drawerAnimationSwitch.isChecked = PreferenceHandler.showDrawerAnimation
        binding.drawerAnimationSwitch.setOnCheckedChangeListener { _, isChecked ->
            PreferenceHandler.showDrawerAnimation = isChecked
        }

        binding.chatTitleSwitch.isChecked = PreferenceHandler.createTitleByAI
        binding.chatTitleSwitch.setOnCheckedChangeListener { _, isChecked ->
            PreferenceHandler.createTitleByAI = isChecked
        }

        binding.chatSuggestionsSwitch.isChecked = PreferenceHandler.createSuggestionsByAI
        binding.chatSuggestionsSwitch.setOnCheckedChangeListener { _, isChecked ->
            PreferenceHandler.createSuggestionsByAI = isChecked
        }

        // Retrieve the version from build.gradle and assign it to the TextView
        binding.versionTextView.apply {
            val version =
                requireContext().packageManager.getPackageInfoCompat(requireContext().packageName).versionName
            text = resources.getString(R.string.version, version)
        }
    }

    private fun getThemeFromKey(key: String): String {
        val themes = getArray(R.array.Themes)
        val keys = getArray(R.array.Themes_values)
        val index = keys.indexOf(key)
        return themes[index]
    }
}