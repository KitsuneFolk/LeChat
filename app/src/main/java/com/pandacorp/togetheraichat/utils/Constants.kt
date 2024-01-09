package com.pandacorp.togetheraichat.utils

import androidx.lifecycle.MutableLiveData
import androidx.preference.PreferenceManager
import com.pandacorp.togetheraichat.R
import com.pandacorp.togetheraichat.di.app.App

object Constants {
    object Preferences {
        object Key {
            const val THEME = "Themes"
            const val API = "TogetherAPIKey"
            const val TEMPERATURE = "Temperature"
            const val MAX_TOKENS = "MaxTokens"
            const val FREQUENCY_PENALTY = "FrequencyPenalty"
            const val TOP_P = "TopP"
        }

        object DefaultValues {
            val TEMPERATURE = App.instance.getString(R.string.defaultTemperature).toFloat()
            val MAX_TOKENS = App.instance.getString(R.string.defaultMaxTokens).toInt()
            val FREQUENCY_PENALTY = App.instance.getString(R.string.defaultFrequencyPenalty).toFloat()
            val TOP_P = App.instance.getString(R.string.defaultTopP).toFloat()
        }
    }

    object Dialogs {
        const val KEY = "DialogKey"
    }

    val apiKey = MutableLiveData<String>(
        PreferenceManager.getDefaultSharedPreferences(App.instance).getString(Preferences.Key.API, "")
    )
}