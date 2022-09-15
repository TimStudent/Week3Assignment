package com.example.week3assignment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.week3assignment.databinding.ActivityMainBinding
//import okhttp3.OkHttpClient
//import okhttp3.Request
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()
        val navHost = supportFragmentManager.findFragmentById(R.id.container_fragment) as NavHostFragment
        val navController = navHost.navController
        val appConfig = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appConfig)
        binding.menuToolbar.setupWithNavController(navController)

    }
}