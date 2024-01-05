package com.pandacorp.togetheraichat.utils

import androidx.lifecycle.MutableLiveData
import androidx.preference.PreferenceManager
import com.pandacorp.togetheraichat.di.app.App

object Constants {
    object Preferences {
        object Key {
            const val THEME = "Themes"
            const val API = "TogetherAPIKey"
            const val TEMPERATURE = "Temperature"
            const val MAX_TOKENS = "MaxTokens"
            const val FREQUENCY_PENALTY = "FrequencyPenalty"
        }
    }

    object Dialogs {
        const val KEY = "DialogKey"
    }

    val apiKey = MutableLiveData<String>(
        PreferenceManager.getDefaultSharedPreferences(App.instance).getString(Preferences.Key.API, "")
    )
}