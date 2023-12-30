package com.pandacorp.togetheraichat.presentation.utils

import android.content.Context
import android.content.res.Configuration
import androidx.preference.PreferenceManager
import com.pandacorp.togetheraichat.R

object PreferenceHandler {
    private object Theme {
        const val FOLLOW_SYSTEM = "follow_system"
        const val BLUE = "blue"
        const val DARK = "dark"
        const val PURPLE = "purple"
        const val DEFAULT = FOLLOW_SYSTEM
    }

    private fun isDeviceDarkMode(context: Context): Boolean =
        (context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES

    fun setTheme(context: Context, theme: Int = getThemeRes(context)) {
        context.setTheme(theme)
    }

    private fun getThemeKey(context: Context): String {
        return PreferenceManager.getDefaultSharedPreferences(context)
            .getString(Constants.Preferences.Key.THEME, Theme.DEFAULT)!!
    }

    private fun getThemeRes(context: Context, themeKey: String = getThemeKey(context)): Int {
        return when (themeKey) {
            Theme.FOLLOW_SYSTEM -> if (isDeviceDarkMode(context)) R.style.DarkTheme else R.style.BlueTheme
            Theme.BLUE -> R.style.BlueTheme
            Theme.DARK -> R.style.DarkTheme
            Theme.PURPLE -> R.style.PurpleTheme
            else -> getThemeRes(context, Theme.FOLLOW_SYSTEM)
        }
    }
}