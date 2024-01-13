package com.pandacorp.togetheraichat.presentation.ui.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.fragula2.navigation.SwipeBackFragment
import com.google.android.material.snackbar.Snackbar
import com.pandacorp.togetheraichat.R
import com.pandacorp.togetheraichat.databinding.ScreenMainBinding
import com.pandacorp.togetheraichat.domain.model.MessageItem
import com.pandacorp.togetheraichat.presentation.ui.adapter.chat.ChatItem
import com.pandacorp.togetheraichat.presentation.ui.adapter.chat.ChatsAdapter
import com.pandacorp.togetheraichat.presentation.ui.adapter.messages.MessagesAdapter
import com.pandacorp.togetheraichat.presentation.ui.dialog.BottomDialogChatSettings
import com.pandacorp.togetheraichat.presentation.vm.ChatsViewModel
import com.pandacorp.togetheraichat.presentation.vm.MessagesViewModel
import com.pandacorp.togetheraichat.utils.Constants
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainScreen : Fragment() {
    private var _binding: ScreenMainBinding? = null
    private val binding get() = _binding!!

    private val navController by lazy { findNavController() }

    private val messagesViewModel: MessagesViewModel by viewModel()
    private val chatsViewModel: ChatsViewModel by viewModel()

    private val chatsAdapter = ChatsAdapter().apply {
        onChatDeleteListener = { chatItem ->
            chatsViewModel.deleteChat(chatItem)
        }
        onChatClickListener = {
            chatsViewModel.currentChat.postValue(it)
        }
    }
    private val messagesAdapter = MessagesAdapter()

    private val chatSettingsDialog by lazy {
        BottomDialogChatSettings(requireContext()).apply {
            setOnClearChatClickListener {
                chatsViewModel.clearCurrentChat()
            }
        }
    }

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
        binding.toolbarInclude.toolbar.title = getString(R.string.app_name)
        binding.toolbarInclude.toolbar.inflateMenu(R.menu.menu_main)
        binding.toolbarInclude.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_settings -> {
                    navController.navigate(R.id.nav_settings_screen)
                }
            }
            true
        }
        val drawerToggle = ActionBarDrawerToggle(
            requireActivity(),
            binding.drawerLayout,
            binding.toolbarInclude.toolbar,
            R.string.open,
            R.string.close
        )

        binding.drawerLayout.addDrawerListener(drawerToggle)
        binding.addChatButton.setOnClickListener {
            chatsViewModel.addChat()
        }
        drawerToggle.syncState()
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            if (binding.drawerLayout.isDrawerOpen(binding.navigationView)) {
                binding.drawerLayout.closeDrawer(binding.navigationView)
            } else {
                if (isEnabled) {
                    isEnabled = false
                    requireActivity().onBackPressedDispatcher.onBackPressed()
                }
            }
        }
        binding.chatsRecyclerView.adapter = chatsAdapter
        binding.recyclerView.adapter = messagesAdapter
        binding.chatSettingsButton.setOnClickListener {
            chatSettingsDialog.show()
        }
        binding.sendButton.setOnClickListener {
            val message = binding.editText.text.toString().trim()
            if (message.isNotBlank()) {
                var chat = chatsViewModel.currentChat.value
                // Add a chat if there's no one
                if (chat == null) {
                    val messageItem = MessageItem(
                        id = 1,
                        role = MessageItem.USER,
                        message = message
                    )
                    val messages = Constants.defaultMessagesList.plus(messageItem)
                    chat = ChatItem(title = "New Chat", messages = messages)
                    chatsViewModel.addChat(chat)
                }
                messagesViewModel.addMessage(MessageItem(message = message, role = MessageItem.USER))
                messagesViewModel.getResponse()
                binding.editText.setText("")
            }
        }

        lifecycleScope.launch {
            chatsViewModel.chatsFlow.collect {
                chatsAdapter.submitList(it)
                // If no chat is selected, select the newest first time user enters the app
                if (chatsViewModel.currentChat.value == null) {
                    chatsViewModel.currentChat.postValue(it.firstOrNull())
                }
            }
        }

        messagesViewModel.messagesList.observe(viewLifecycleOwner) {
            messagesAdapter.submitList(it)
        }
        messagesViewModel.errorCode.observe(viewLifecycleOwner) {
            if (it != null) {
                val errorMessage = when (it) {
                    500 -> "Internal Server Error"
                    429 -> "Credit limit exceeded. Please visit https://api.together.xyz to update your credit settings."
                    401 -> "Missing API key"
                    else -> "Error code $it"
                }
                Snackbar.make(
                    binding.messageInputLayout,
                    "Error: $errorMessage",
                    Snackbar.LENGTH_LONG
                )
                    .apply {
                        animationMode = Snackbar.ANIMATION_MODE_SLIDE
                        show()
                    }
                messagesViewModel.errorCode.value = null
            }
        }
        messagesViewModel.onResponseGenerated = {
            val chat = chatsViewModel.currentChat.value?.copy(messages = it)!!
            chatsViewModel.currentChat.postValue(chat)
            chatsViewModel.updateChat(chat)
        }
        chatsViewModel.currentChat.observe(viewLifecycleOwner) {
            if (it?.messages != null) {
                messagesViewModel.messagesList.postValue(it.messages.toMutableList())
            } else {
                messagesViewModel.messagesList.postValue(Constants.defaultMessagesList.toMutableList())
            }
        }
        binding.drawerLayout.apply {
            val swipeBackFragment by lazy { requireParentFragment() as SwipeBackFragment }
            addDrawerListener(
                object : DrawerLayout.DrawerListener {
                    override fun onDrawerOpened(drawerView: View) {
                        swipeBackFragment.setScrollingEnabled(false)
                    }

                    override fun onDrawerClosed(drawerView: View) {
                        swipeBackFragment.setScrollingEnabled(true)
                    }

                    override fun onDrawerSlide(drawerView: View, slideOffset: Float) {}

                    override fun onDrawerStateChanged(newState: Int) {
                        if (newState == DrawerLayout.STATE_DRAGGING) {
                            swipeBackFragment.setScrollingEnabled(false)
                        }
                    }
                }
            )
        }
    }
}