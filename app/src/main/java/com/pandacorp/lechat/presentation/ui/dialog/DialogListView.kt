package com.pandacorp.lechat.presentation.ui.dialog

import android.content.Context
import android.os.Bundle
import com.pandacorp.lechat.R
import com.pandacorp.lechat.databinding.DialogListViewBinding
import com.pandacorp.lechat.presentation.ui.adapter.settings.SettingsAdapter
import com.pandacorp.lechat.presentation.ui.adapter.settings.SettingsItem
import com.pandacorp.lechat.utils.Constants
import com.pandacorp.lechat.utils.Constants.sp
import com.pandacorp.lechat.utils.getArray

class DialogListView(private val context: Context) : CustomDialog(context) {
    private lateinit var binding: DialogListViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DialogListViewBinding.inflate(layoutInflater)

        setContentView(binding.root)

        initViews()
    }

    private fun initViews() {
        binding.title.setText(R.string.theme)

        binding.listView.adapter = SettingsAdapter(context, fillThemesList()).apply {
            setOnClickListener { listItem ->
                cancel()
                val value = listItem.value
                if (sp.getString(Constants.Preferences.Key.THEME, "") == value) return@setOnClickListener
                sp.edit().putString(Constants.Preferences.Key.THEME, value).apply()
                onValueAppliedListener(value)
            }
        }
    }

    private fun fillThemesList(): MutableList<SettingsItem> {
        val valuesList = getArray(R.array.Themes_values)
        val drawablesList = context.resources.obtainTypedArray(R.array.Themes_drawables)
        val titlesList = getArray(R.array.Themes)
        val themesList: MutableList<SettingsItem> = mutableListOf()
        repeat(valuesList.size) { i ->
            themesList.add(
                SettingsItem(
                    valuesList[i],
                    titlesList[i],
                    drawablesList.getDrawable(i)!!
                )
            )
        }
        drawablesList.recycle()
        return themesList
    }
}