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
    var temperature: Double = PreferenceManager.getDefaultSharedPreferences(App.instance)
        .getFloat(
            Constants.Preferences.Key.TEMPERATURE,
            App.instance.getString(R.string.defaultTemperature).toFloat()
        ).toDouble()
        set(value) {
            field = value
            PreferenceManager.getDefaultSharedPreferences(App.instance)
                .edit()
                .putFloat(Constants.Preferences.Key.TEMPERATURE, value.toFloat())
                .apply()
        }

    var maxTokens: Int =
        PreferenceManager.getDefaultSharedPreferences(App.instance).getInt(Constants.Preferences.Key.MAX_TOKENS, 0)
        set(value) {
            field = value
            PreferenceManager.getDefaultSharedPreferences(App.instance)
                .edit()
                .putInt(Constants.Preferences.Key.MAX_TOKENS, value)
                .apply()
        }

    var frequencyPenalty: Double = PreferenceManager.getDefaultSharedPreferences(App.instance)
        .getFloat(
            Constants.Preferences.Key.FREQUENCY_PENALTY,
            App.instance.getString(R.string.defaultFrequencyPenalty).toFloat()
        ).toDouble()
        set(value) {
            field = value
            PreferenceManager.getDefaultSharedPreferences(App.instance)
                .edit()
                .putFloat(Constants.Preferences.Key.FREQUENCY_PENALTY, value.toFloat())
                .apply()
        }

    var topP: Double = PreferenceManager.getDefaultSharedPreferences(App.instance)
        .getFloat(
            Constants.Preferences.Key.TOP_P,
            App.instance.getString(R.string.defaultTopP).toFloat()
        ).toDouble()
        set(value) {
            field = value
            PreferenceManager.getDefaultSharedPreferences(App.instance)
                .edit()
                .putFloat(Constants.Preferences.Key.TOP_P, value.toFloat())
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