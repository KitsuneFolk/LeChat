package com.pandacorp.togetheraichat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pandacorp.splashscreen.SplashScreen.Companion.installSplashScreen
import com.pandacorp.togetheraichat.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setTheme(R.style.BlueTheme)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}