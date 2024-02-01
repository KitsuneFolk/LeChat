package com.pandacorp.lechat.utils

import android.app.Activity
import android.content.res.Configuration
import com.pandacorp.lechat.R
import com.pandacorp.lechat.di.app.App
import com.pandacorp.lechat.utils.Constants.sp

object PreferenceHandler {
    private object Theme {
        const val FOLLOW_SYSTEM = "follow_system"
        const val BLUE = "blue"
        const val DARK = "dark"
        const val PURPLE = "purple"
        const val DEFAULT = FOLLOW_SYSTEM
    }

    // Cached variables
    var temperature: Float = sp.getFloat(
        Constants.Preferences.Key.TEMPERATURE,
        Constants.Preferences.DefaultValues.TEMPERATURE
    )
        set(value) {
            field = value
            sp.put(Constants.Preferences.Key.TEMPERATURE, value)
        }

    var maxTokens: Int = sp.getInt(
        Constants.Preferences.Key.MAX_TOKENS,
        Constants.Preferences.DefaultValues.MAX_TOKENS
    )
        set(value) {
            field = value
            sp.put(Constants.Preferences.Key.MAX_TOKENS, value)
        }

    var frequencyPenalty: Float = sp.getFloat(
        Constants.Preferences.Key.FREQUENCY_PENALTY,
        Constants.Preferences.DefaultValues.FREQUENCY_PENALTY
    )
        set(value) {
            field = value
            sp.put(Constants.Preferences.Key.FREQUENCY_PENALTY, value)
        }

    var topP: Float = sp.getFloat(
        Constants.Preferences.Key.TOP_P,
        Constants.Preferences.DefaultValues.TOP_P
    )
        set(value) {
            field = value
            sp.put(Constants.Preferences.Key.TOP_P, value)
        }

    var modelValue: String =
        sp.getString(Constants.Preferences.Key.MODEL_VALUE, Constants.Preferences.DefaultValues.MODEL_VALUE)!!
        set(value) {
            field = value
            sp.put(Constants.Preferences.Key.MODEL_VALUE, value)
        }

    var showDrawerAnimation: Boolean = sp.getBoolean(
        Constants.Preferences.Key.SHOW_DRAWER_ANIMATION,
        Constants.Preferences.DefaultValues.SHOW_DRAWER_ANIMATION
    )
        set(value) {
            field = value
            sp.put(Constants.Preferences.Key.SHOW_DRAWER_ANIMATION, value)
        }

    var createTitleByAI: Boolean = sp.getBoolean(
        Constants.Preferences.Key.CREATE_TITLE_BY_AI,
        Constants.Preferences.DefaultValues.CREATE_TITLE_BY_AI
    )
        set(value) {
            field = value
            sp.put(Constants.Preferences.Key.CREATE_TITLE_BY_AI, value)
        }

    var createSuggestionsByAI: Boolean = sp.getBoolean(
        Constants.Preferences.Key.CREATE_SUGGESTIONS_BY_AI,
        Constants.Preferences.DefaultValues.CREATE_SUGGESTIONS_BY_AI
    )
        set(value) {
            field = value
            sp.put(Constants.Preferences.Key.CREATE_SUGGESTIONS_BY_AI, value)
        }

    fun setTheme(activity: Activity, theme: Int = getThemeRes()) {
        activity.setTheme(theme)
    }

    private fun isDeviceDarkMode(): Boolean =
        (App.instance.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES

    private fun getThemeKey(): String {
        return sp.getString(Constants.Preferences.Key.THEME, Theme.DEFAULT)!!
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