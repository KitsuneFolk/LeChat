package com.pandacorp.lechat.presentation.ui.adapter.suggestions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pandacorp.lechat.databinding.ItemSuggestionBinding

class SuggestionsAdapter : ListAdapter<SuggestionItem, SuggestionsAdapter.ViewHolder>(DiffCallback()) {
    private class DiffCallback : DiffUtil.ItemCallback<SuggestionItem>() {
        override fun areItemsTheSame(oldItem: SuggestionItem, newItem: SuggestionItem): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: SuggestionItem, newItem: SuggestionItem): Boolean =
            oldItem == newItem
    }

    inner class ViewHolder(private val binding: ItemSuggestionBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(suggestionItem: SuggestionItem) {
            binding.textView.text = suggestionItem.text
            binding.cardView.setOnClickListener {
                onSuggestionClickListener?.invoke(suggestionItem)
            }
        }
    }

    var onSuggestionClickListener: ((SuggestionItem) -> Unit)? = null

    override fun submitList(list: List<SuggestionItem>?) {
        super.submitList(list?.let { ArrayList(it) })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemSuggestionBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}