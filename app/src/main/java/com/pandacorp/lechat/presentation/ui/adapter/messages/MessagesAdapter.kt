package com.pandacorp.lechat.presentation.ui.adapter.messages

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pandacorp.lechat.R
import com.pandacorp.lechat.databinding.ItemMessageBinding
import com.pandacorp.lechat.domain.model.MessageItem
import com.pandacorp.lechat.utils.getString

class MessagesAdapter : ListAdapter<MessageItem, MessagesAdapter.ViewHolder>(DiffCallback()) {
    companion object {
        const val PAYLOAD_MESSAGE = "message"
    }

    private class DiffCallback : DiffUtil.ItemCallback<MessageItem>() {
        override fun areItemsTheSame(oldItem: MessageItem, newItem: MessageItem): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: MessageItem, newItem: MessageItem): Boolean = oldItem == newItem

        override fun getChangePayload(oldItem: MessageItem, newItem: MessageItem): Bundle? {
            val diff = Bundle()
            if (newItem.message != oldItem.message) {
                diff.putString(PAYLOAD_MESSAGE, newItem.message)
            }
            return if (diff.size() == 0) {
                null
            } else {
                diff
            }
        }
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
            bindMessage(item.message)
            bindButtons(item)
        }

        fun bindMessage(message: String) {
            binding.messageTextView.text = message
        }

        private fun bindButtons(
            item: MessageItem,
            show: Boolean = item.role == MessageItem.AI
        ) {
            binding.buttonsLayout.visibility = if (show) View.VISIBLE else View.GONE
            binding.regenerateButton.setOnClickListener {
                onRegenerateClickListener?.invoke(item)
            }
            binding.copyButton.setOnClickListener {
                val clipData = ClipData.newPlainText(getString(R.string.message), item.message)
                val clipboardManager =
                    getSystemService(binding.root.context, ClipboardManager::class.java) as ClipboardManager
                clipboardManager.setPrimaryClip(clipData)
            }
        }
    }

    var onRegenerateClickListener: ((MessageItem) -> Unit)? = null

    override fun submitList(list: List<MessageItem>?) {
        val newList = list?.toMutableList()
        // Don't show system messages in the RecyclerView
        newList?.removeAll { it.role == MessageItem.SYSTEM }
        super.submitList(newList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemMessageBinding.inflate(inflater, parent, false))
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
                    PAYLOAD_MESSAGE -> {
                        holder.bindMessage(bundle.getString(PAYLOAD_MESSAGE) ?: continue)
                    }
                }
            }
        }
    }
}