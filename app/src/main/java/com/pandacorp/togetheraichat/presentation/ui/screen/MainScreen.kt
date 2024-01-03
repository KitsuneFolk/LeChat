package com.pandacorp.togetheraichat.presentation.ui.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.pandacorp.togetheraichat.R
import com.pandacorp.togetheraichat.databinding.ScreenMainBinding
import com.pandacorp.togetheraichat.domain.model.MessageItem
import com.pandacorp.togetheraichat.presentation.ui.adapter.messages.MessagesAdapter
import com.pandacorp.togetheraichat.presentation.vm.MessagesViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainScreen : Fragment() {
    private var _binding: ScreenMainBinding? = null
    private val binding get() = _binding!!

    private val navController by lazy { findNavController() }

    private val vm: MessagesViewModel by viewModel()

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
                vm.addMessage(MessageItem(message = message, role = MessageItem.USER))
                vm.getResponse()
                binding.editText.setText("")
            }
        }

        vm.messagesList.observe(viewLifecycleOwner) {
            messagesAdapter.submitList(it)
        }
        lifecycleScope.launch {
            vm.errorCode.observe(viewLifecycleOwner) {
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
                    vm.errorCode.value = null
                }
            }
        }
    }
}