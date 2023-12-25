package com.pandacorp.togetheraichat.presentation.ui.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.pandacorp.togetheraichat.R
import com.pandacorp.togetheraichat.databinding.ScreenSettingsBinding
import com.pandacorp.togetheraichat.presentation.utils.Constants
import com.pandacorp.togetheraichat.presentation.utils.PreferenceHandler
import com.pandacorp.togetheraichat.presentation.utils.dialog.DialogListView
import com.pandacorp.togetheraichat.presentation.utils.getPackageInfoCompat

class SettingsScreen : Fragment() {
    private var _binding: ScreenSettingsBinding? = null
    private val binding get() = _binding!!

    private val sp by lazy {
        PreferenceManager.getDefaultSharedPreferences(requireContext())
    }

    private val languageDialog by lazy {
        DialogListView(requireContext(), Constants.Preferences.Key.LANGUAGE).apply {
            onValueAppliedListener = {
                PreferenceHandler.setLanguage(requireContext(), it)
            }
        }
    }
    private val themeDialog by lazy {
        DialogListView(requireContext(), Constants.Preferences.Key.THEME).apply {
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
        val dialogKey =
            when {
                themeDialog.isShowing -> Constants.Preferences.Key.THEME
                languageDialog.isShowing -> Constants.Preferences.Key.LANGUAGE
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
            Constants.Preferences.Key.LANGUAGE -> languageDialog.show()
        }
    }

    override fun onDestroy() {
        _binding = null
        themeDialog.dismiss()
        languageDialog.dismiss()
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
                    requireContext().resources.getString(R.string.settings_theme_default_value)
                )!!
                text = getThemeFromKey(themeKey)
            }
        }
        binding.languageLayout.apply {
            setOnClickListener {
                languageDialog.show()
            }
            binding.languageTextView.apply {
                val languageKey =
                    sp.getString(
                        Constants.Preferences.Key.LANGUAGE,
                        requireContext().resources.getString(R.string.settings_language_default_value)
                    )!!
                text = getLanguageFromKey(languageKey)
            }
        }

        // Retrieve the version from build.gradle and assign it to the TextView
        binding.versionTextView.apply {
            val version =
                requireContext().packageManager.getPackageInfoCompat(requireContext().packageName).versionName
            text = resources.getString(R.string.version, version)
        }
    }

    private fun getThemeFromKey(key: String): String {
        val themes = resources.getStringArray(R.array.Themes)
        val keys = resources.getStringArray(R.array.Themes_values)

        val index = keys.indexOf(key)
        return themes[index]
    }

    private fun getLanguageFromKey(key: String): String {
        val languages = resources.getStringArray(R.array.Languages)
        val keys = resources.getStringArray(R.array.Languages_values)

        val index = keys.indexOf(key)
        return languages[index]
    }
}