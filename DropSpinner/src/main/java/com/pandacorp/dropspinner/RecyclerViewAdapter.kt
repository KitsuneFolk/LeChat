package com.pandacorp.dropspinner

import android.content.res.ColorStateList
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pandacorp.lechat.dropspinner.databinding.DropsyItemDropDownBinding

internal class RecyclerViewAdapter : ListAdapter<DropDownItem, RecyclerViewAdapter.ViewHolder>(DiffCallback()) {
    var selectedIndex = -1
    private var textColor: Int? = null
    private var arrowColor: Int? = null

    var onItemClickListener: (position: Int) -> Unit = {}

    private class DiffCallback : DiffUtil.ItemCallback<DropDownItem>() {
        override fun areItemsTheSame(oldItem: DropDownItem, newItem: DropDownItem): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: DropDownItem, newItem: DropDownItem): Boolean = oldItem == newItem
    }

    inner class ViewHolder(private val binding: DropsyItemDropDownBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DropDownItem) {
            textColor?.let { binding.txtLabel.setTextColor(it) }
            arrowColor?.let { binding.imgCheck.imageTintList = ColorStateList.valueOf(it) }

            if (item.checked) {
                binding.txtLabel.typeface = Typeface.DEFAULT_BOLD
                binding.imgCheck.visibility = View.VISIBLE
            } else {
                binding.txtLabel.typeface = Typeface.DEFAULT
                binding.imgCheck.visibility = View.GONE
            }

            binding.root.setOnClickListener {
                onItemClickListener(bindingAdapterPosition)
            }

            bindText(item.text)
        }

        private fun bindText(text: String) {
            binding.txtLabel.text = text
        }
    }

    fun setSelection(index: Int, item: DropDownItem?) {
        if (selectedIndex != -1 && selectedIndex < itemCount) {
            getItem(selectedIndex)?.toggleState()
        }
        item?.toggleState()
        notifyItemChanged(index)
        notifyItemChanged(selectedIndex)
        selectedIndex = index
    }

    internal fun setTextColor(color: Int) {
        this.textColor = color
    }

    internal fun setArrowColor(arrowColor: Int) {
        this.arrowColor = arrowColor
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(DropsyItemDropDownBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind((getItem(position)))
    }
}