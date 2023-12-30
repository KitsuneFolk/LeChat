package com.pandacorp.togetheraichat.presentation.ui.adapter.messages

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pandacorp.togetheraichat.R
import com.pandacorp.togetheraichat.databinding.ItemMessageBinding
import com.pandacorp.togetheraichat.domain.model.MessageItem

class MessagesAdapter : ListAdapter<MessageItem, MessagesAdapter.ViewHolder>(DiffCallback()) {
    class DiffCallback : DiffUtil.ItemCallback<MessageItem>() {
        override fun areItemsTheSame(oldItem: MessageItem, newItem: MessageItem): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: MessageItem, newItem: MessageItem): Boolean = oldItem == newItem
    }

    inner class ViewHolder(private val binding: ItemMessageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MessageItem) {
            val resources = binding.root.context.resources
            when (item.role) {
                MessageItem.AI -> {
                    binding.roleTextView.text = resources.getText(R.string.AI)
                }

                MessageItem.USER -> {
                    binding.roleTextView.text = resources.getText(R.string.you)
                }
            }
            binding.messageTextView.text = item.message
        }
    }

    override fun submitList(list: List<MessageItem>?) {
        super.submitList(list?.let { ArrayList(it) })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemMessageBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}