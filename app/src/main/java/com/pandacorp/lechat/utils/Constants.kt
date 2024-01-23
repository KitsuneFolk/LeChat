package com.pandacorp.lechat.utils

import androidx.lifecycle.MutableLiveData
import androidx.preference.PreferenceManager
import com.pandacorp.lechat.R
import com.pandacorp.lechat.di.app.App
import com.pandacorp.lechat.domain.model.MessageItem

object Constants {
    object Preferences {
        object Key {
            const val THEME = "Themes"
            const val API = "TogetherAPIKey"
            const val TEMPERATURE = "Temperature"
            const val MAX_TOKENS = "MaxTokens"
            const val FREQUENCY_PENALTY = "FrequencyPenalty"
            const val TOP_P = "TopP"
            const val MODEL_VALUE = "ModelValue"
            const val SHOW_DRAWER_ANIMATION = "ShowDrawerAnimation"
        }

        object DefaultValues {
            val TEMPERATURE = getString(R.string.defaultTemperature).toFloat()
            val MAX_TOKENS = getString(R.string.defaultMaxTokens).toInt()
            val FREQUENCY_PENALTY = getString(R.string.defaultFrequencyPenalty).toFloat()
            val TOP_P = getString(R.string.defaultTopP).toFloat()
            val MODEL_VALUE = getString(R.string.defaultModelValue)
            val SHOW_DRAWER_ANIMATION = getString(R.string.defaultShowDrawerAnimation).toBoolean()
        }
    }

    object Dialogs {
        const val KEY = "DialogKey"
    }

    val defaultMessagesList =
        listOf(MessageItem(id = 0, role = MessageItem.SYSTEM, message = "You are a helpful assistant!"))

    val apiKey = MutableLiveData<String>(
        PreferenceManager.getDefaultSharedPreferences(App.instance).getString(Preferences.Key.API, "")
    )
}