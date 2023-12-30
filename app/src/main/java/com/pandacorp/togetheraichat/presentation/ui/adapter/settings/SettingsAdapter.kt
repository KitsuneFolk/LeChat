package com.pandacorp.togetheraichat.presentation.ui.adapter.settings

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.pandacorp.togetheraichat.R

class SettingsAdapter(
    context: Context,
    items: MutableList<SettingsItem>
) : ArrayAdapter<SettingsItem>(context, 0, items) {
    private var onListItemClickListener: OnListItemClickListener? = null

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        if (view == null) view = LayoutInflater.from(context).inflate(R.layout.item_settings, parent, false)!!
        val listItem = getItem(position)!!
        view.apply {
            findViewById<ImageView>(R.id.ListItemImageView).apply {
                setImageDrawable(listItem.drawable)
            }
            findViewById<ConstraintLayout>(R.id.ListItemLayout).apply {
                setOnClickListener {
                    onListItemClickListener?.onClick(listItem)
                }
            }
            findViewById<TextView>(R.id.ListItemTextView).apply {
                text = listItem.title
            }
        }
        return view
    }

    fun setOnClickListener(onListItemClickListener: OnListItemClickListener) {
        this.onListItemClickListener = onListItemClickListener
    }

    fun interface OnListItemClickListener {
        fun onClick(listItem: SettingsItem)
    }
}