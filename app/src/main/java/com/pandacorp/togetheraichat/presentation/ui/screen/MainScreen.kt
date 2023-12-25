package com.pandacorp.togetheraichat.presentation.ui.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.pandacorp.togetheraichat.R
import com.pandacorp.togetheraichat.databinding.ScreenMainBinding

class MainScreen : Fragment() {
    private var _binding: ScreenMainBinding? = null
    private val binding get() = _binding!!

    private val navController by lazy { findNavController() }

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
    }
}