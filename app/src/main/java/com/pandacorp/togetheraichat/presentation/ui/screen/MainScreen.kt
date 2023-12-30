package com.pandacorp.togetheraichat.presentation.ui.screen

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import com.pandacorp.togetheraichat.R
import com.pandacorp.togetheraichat.databinding.ScreenMainBinding
import com.pandacorp.togetheraichat.domain.model.MessageItem
import com.pandacorp.togetheraichat.presentation.ui.adapter.messages.MessagesAdapter
import com.pandacorp.togetheraichat.presentation.utils.Constants
import com.pandacorp.togetheraichat.presentation.vm.MessagesViewModel
import com.pandacorp.togetheraichat.presentation.vm.ViewModelFactory

class MainScreen : Fragment() {
    private var _binding: ScreenMainBinding? = null
    private val binding get() = _binding!!

    private val navController by lazy { findNavController() }

    private val viewModel: MessagesViewModel by viewModels {
        val sp: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        ViewModelFactory(sp.getString(Constants.Preferences.Key.API, "") ?: "")
    }

    private val messagesAdapter = MessagesAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = ScreenMainBinding.inflate(inflater)

        initViews()

        return binding.root
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    private fun initViews() {
        binding.toolbarInclude.toolbar.title = requireContext().resources.getString(R.string.app_name)
        binding.toolbarInclude.toolbar.inflateMenu(R.menu.menu_main)
        binding.toolbarInclude.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_settings -> {
                    navController.navigate(R.id.nav_settings_screen)
                }
            }
            true
        }
        binding.recyclerView.adapter = messagesAdapter
        binding.sendButton.setOnClickListener {
            val message = binding.editText.text.toString().trim()
            if (message.isNotBlank()) {
                viewModel.addMessage(MessageItem(message = message, role = MessageItem.USER))
                binding.editText.setText("")
                val response = MessageItem(message = "", role = MessageItem.AI)
                viewModel.addMessage(response)
                val copiedResponse = response.copy()
                viewModel.getResponse {
                    copiedResponse.message += it
                    viewModel.replaceAt(response.id.toInt(), copiedResponse)
                    messagesAdapter.notifyItemChanged(response.id.toInt())
                }
            }
        }

        viewModel.messagesList.observe(viewLifecycleOwner) {
            messagesAdapter.submitList(it)
        }
    }
}