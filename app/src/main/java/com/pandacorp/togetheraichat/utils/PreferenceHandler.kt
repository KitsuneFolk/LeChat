package com.pandacorp.togetheraichat.utils

import android.app.Activity
import android.content.res.Configuration
import androidx.preference.PreferenceManager
import com.pandacorp.togetheraichat.R
import com.pandacorp.togetheraichat.di.app.App

object PreferenceHandler {
    private object Theme {
        const val FOLLOW_SYSTEM = "follow_system"
        const val BLUE = "blue"
        const val DARK = "dark"
        const val PURPLE = "purple"
        const val DEFAULT = FOLLOW_SYSTEM
    }

    // Cached variables
    var temperature: Float = PreferenceManager.getDefaultSharedPreferences(App.instance)
        .getFloat(
            Constants.Preferences.Key.TEMPERATURE,
            Constants.Preferences.DefaultValues.TEMPERATURE
        )
        set(value) {
            field = value
            PreferenceManager.getDefaultSharedPreferences(App.instance)
                .edit()
                .putFloat(Constants.Preferences.Key.TEMPERATURE, value)
                .apply()
        }

    var maxTokens: Int = PreferenceManager.getDefaultSharedPreferences(App.instance)
        .getInt(
            Constants.Preferences.Key.MAX_TOKENS,
            Constants.Preferences.DefaultValues.MAX_TOKENS
        )
        set(value) {
            field = value
            PreferenceManager.getDefaultSharedPreferences(App.instance)
                .edit()
                .putInt(Constants.Preferences.Key.MAX_TOKENS, value)
                .apply()
        }

    var frequencyPenalty: Float = PreferenceManager.getDefaultSharedPreferences(App.instance)
        .getFloat(
            Constants.Preferences.Key.FREQUENCY_PENALTY,
            Constants.Preferences.DefaultValues.FREQUENCY_PENALTY
        )
        set(value) {
            field = value
            PreferenceManager.getDefaultSharedPreferences(App.instance)
                .edit()
                .putFloat(Constants.Preferences.Key.FREQUENCY_PENALTY, value)
                .apply()
        }

    var topP: Float = PreferenceManager.getDefaultSharedPreferences(App.instance)
        .getFloat(
            Constants.Preferences.Key.TOP_P,
            Constants.Preferences.DefaultValues.TOP_P
        )
        set(value) {
            field = value
            PreferenceManager.getDefaultSharedPreferences(App.instance)
                .edit()
                .putFloat(Constants.Preferences.Key.TOP_P, value)
                .apply()
        }

    private fun isDeviceDarkMode(): Boolean =
        (App.instance.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES

    fun setTheme(activity: Activity, theme: Int = getThemeRes()) {
        activity.setTheme(theme)
    }

    private fun getThemeKey(): String {
        return PreferenceManager.getDefaultSharedPreferences(App.instance)
            .getString(Constants.Preferences.Key.THEME, Theme.DEFAULT)!!
    }

    private fun getThemeRes(themeKey: String = getThemeKey()): Int {
        return when (themeKey) {
            Theme.FOLLOW_SYSTEM -> if (isDeviceDarkMode()) R.style.DarkTheme else R.style.BlueTheme
            Theme.BLUE -> R.style.BlueTheme
            Theme.DARK -> R.style.DarkTheme
            Theme.PURPLE -> R.style.PurpleTheme
            else -> getThemeRes(Theme.FOLLOW_SYSTEM)
        }
    }
}