package com.pandacorp.lechat.presentation.ui.adapter.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pandacorp.lechat.databinding.ItemChatBinding

class ChatsAdapter : ListAdapter<ChatItem, ChatsAdapter.ViewHolder>(DiffCallback()) {
    companion object {
        const val PAYLOAD_TITLE = "title"
    }

    private class DiffCallback : DiffUtil.ItemCallback<ChatItem>() {
        override fun areItemsTheSame(oldItem: ChatItem, newItem: ChatItem): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ChatItem, newItem: ChatItem): Boolean = oldItem == newItem

        override fun getChangePayload(oldItem: ChatItem, newItem: ChatItem): Bundle? {
            val diff = Bundle()
            if (newItem.title != oldItem.title) {
                diff.putString(PAYLOAD_TITLE, newItem.title)
            }
            return if (diff.size() == 0) null else diff
        }
    }

    inner class ViewHolder(private val binding: ItemChatBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(chatItem: ChatItem) {
            bindTitle(chatItem.title)
            bindButtons(chatItem)
            bindClick(chatItem)
        }

        fun bindTitle(title: String) {
            binding.title.text = title
            binding.editText.setText(title)
        }

        private fun bindButtons(chatItem: ChatItem) {
            fun toggleRenameMode(show: Boolean) {
                with(binding) {
                    editText.visibility = if (show) View.VISIBLE else View.GONE
                    title.visibility = if (show) View.GONE else View.VISIBLE
                    renameChatButton.visibility = if (show) View.GONE else View.VISIBLE
                    deleteChatButton.visibility = if (show) View.GONE else View.VISIBLE
                    applyRenameButton.visibility = if (show) View.VISIBLE else View.GONE
                    cancelRenameButton.visibility = if (show) View.VISIBLE else View.GONE
                }
            }

            binding.renameChatButton.setOnClickListener {
                toggleRenameMode(true)
            }

            binding.cancelRenameButton.setOnClickListener {
                toggleRenameMode(false)
            }

            binding.applyRenameButton.setOnClickListener {
                onChatRenameListener?.invoke(chatItem.copy(title = binding.editText.text.toString()))
                toggleRenameMode(false)
            }

            binding.deleteChatButton.setOnClickListener {
                onChatDeleteListener?.invoke(chatItem)
            }
        }

        private fun bindClick(chatItem: ChatItem) {
            binding.cardView.setOnClickListener {
                onChatClickListener?.invoke(chatItem)
            }
        }
    }

    var onChatClickListener: ((ChatItem) -> Unit)? = null
    var onChatRenameListener: ((ChatItem) -> Unit)? = null
    var onChatDeleteListener: ((ChatItem) -> Unit)? = null

    override fun submitList(list: List<ChatItem>?) {
        super.submitList(list?.let { ArrayList(it) })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemChatBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
            return
        } else {
            val bundle = payloads[0] as Bundle
            for (key in bundle.keySet()) {
                when (key) {
                    PAYLOAD_TITLE -> {
                        holder.bindTitle(bundle.getString(PAYLOAD_TITLE) ?: continue)
                    }
                }
            }
        }
    }
}