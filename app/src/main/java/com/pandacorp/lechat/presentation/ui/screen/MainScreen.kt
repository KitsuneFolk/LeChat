package com.pandacorp.lechat.presentation.ui.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.os.bundleOf
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.fragula2.navigation.SwipeBackFragment
import com.google.android.material.snackbar.Snackbar
import com.pandacorp.lechat.R
import com.pandacorp.lechat.databinding.ScreenMainBinding
import com.pandacorp.lechat.domain.model.MessageItem
import com.pandacorp.lechat.presentation.ui.adapter.chat.ChatItem
import com.pandacorp.lechat.presentation.ui.adapter.chat.ChatsAdapter
import com.pandacorp.lechat.presentation.ui.adapter.messages.MessagesAdapter
import com.pandacorp.lechat.presentation.ui.adapter.suggestions.SuggestionsAdapter
import com.pandacorp.lechat.presentation.ui.dialog.BottomDialogChatSettings
import com.pandacorp.lechat.presentation.vm.ChatsViewModel
import com.pandacorp.lechat.presentation.vm.MessagesViewModel
import com.pandacorp.lechat.utils.Constants
import com.pandacorp.lechat.utils.PreferenceHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainScreen : Fragment() {
    private var _binding: ScreenMainBinding? = null
    private val binding get() = _binding!!

    private val navController by lazy { findNavController() }

    private val messagesViewModel: MessagesViewModel by viewModel()
    private val chatsViewModel: ChatsViewModel by viewModel()

    private val chatsAdapter = ChatsAdapter().apply {
        onChatRenameListener = { chatItem ->
            chatsViewModel.updateChat(chatItem)
        }
        onChatDeleteListener = { chatItem ->
            chatsViewModel.deleteChat(chatItem)
            Snackbar.make(
                binding.messageInputLayout,
                resources.getString(R.string.deleted, "1"),
                Snackbar.LENGTH_LONG
            )
                .apply {
                    animationMode = Snackbar.ANIMATION_MODE_SLIDE
                    show()
                    setAction(R.string.undo) {
                        chatsViewModel.addChat(chatItem)
                    }
                }
        }
        onChatClickListener = { chatItem ->
            // TODO: Sometimes submitList() doesn't update the list, find item in viewmodel and update now
            chatsViewModel.currentChat.postValue(chatsViewModel.chatsFlow.value.first { it.id == chatItem.id })
            binding.drawerLayout.close()
        }
    }
    private val messagesAdapter = MessagesAdapter().apply {
        onRegenerateClickListener = {
            if (messagesViewModel.isResponseGenerating.value != true) {
                messagesViewModel.regenerateMessage(it.id)
            }
        }
    }

    private val suggestionsAdapter = SuggestionsAdapter().apply {
        onSuggestionClickListener = {

        }
    }

    private val chatSettingsDialog by lazy {
        BottomDialogChatSettings(requireContext())
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
        binding.motionDrawerLayout.post {
            binding.motionDrawerLayout.loadLayoutDescription(R.xml.drawer_layout_motion_scene)
        }
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
        binding.suggestionsRecyclerView.adapter = suggestionsAdapter
        binding.chatSettingsButton.setOnClickListener {
            chatSettingsDialog.show()
        }
        binding.sendStopButton.setOnClickListener {
            if (messagesViewModel.isResponseGenerating.value == true) {
                messagesViewModel.stopResponse()
            } else {
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
                        chat = ChatItem(title = ChatItem.defaultTitle, messages = messages)
                        chatsViewModel.addChat(chat)
                    }
                    // Hide the previous AI's message's buttons
                    messagesAdapter.notifyItemChanged(
                        messagesAdapter.currentList.indexOf(messagesAdapter.currentList.findLast { it.role == MessageItem.AI }),
                        bundleOf(Pair(MessagesAdapter.PAYLOAD_BUTTONS, false))
                    )
                    messagesViewModel.addMessage(MessageItem(message = message, role = MessageItem.USER))
                    messagesViewModel.getResponse()
                    binding.editText.setText("")
                }
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
        messagesViewModel.suggestionsList.observe(viewLifecycleOwner) {
            suggestionsAdapter.submitList(it)
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
        messagesViewModel.isResponseGenerating.observe(viewLifecycleOwner) {
            if (it) {
                binding.sendStopButton.setImageResource(R.drawable.ic_stop)
            } else {
                binding.sendStopButton.setImageResource(R.drawable.ic_send)
            }
        }
        messagesViewModel.onResponseGenerated = {
            CoroutineScope(Dispatchers.IO).launch {
                var title = chatsViewModel.currentChat.value?.title ?: ChatItem.defaultTitle
                // Summarize only once by checking if the title is default
                if (chatsViewModel.currentChat.value?.title == ChatItem.defaultTitle) {
                    if (PreferenceHandler.createTitleByAI) {
                        title = messagesViewModel.summarizeChat(it)
                    }
                }
                val chat = chatsViewModel.currentChat.value!!.copy(title = title, messages = it)
                chatsViewModel.currentChat.postValue(chat)
                chatsViewModel.updateChat(chat)
            }
        }
        chatsViewModel.currentChat.observe(viewLifecycleOwner) {
            binding.toolbarInclude.toolbar.title = it?.title ?: ChatItem.defaultTitle
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