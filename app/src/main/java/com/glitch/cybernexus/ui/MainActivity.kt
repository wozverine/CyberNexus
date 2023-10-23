package com.glitch.cybernexus.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.glitch.cybernexus.R
import com.glitch.cybernexus.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
        NavigationUI.setupWithNavController(binding.bottomNavigation, navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.loginFragment -> binding.bottomNavigation.visibility = View.VISIBLE
                //R.id.mineFragment -> showBottomNav()
                else -> binding.bottomNavigation.visibility = View.GONE
            }
        }
        with(binding) {
            bottomNavigation.setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.home -> {
                        //loadFragment(HomeFragment())
                        true
                    }

                    R.id.search -> {
                        //loadFragment(ChatFragment())
                        true
                    }

                    R.id.favorites -> {
                        //loadFragment(SettingFragment())
                        true
                    }

                    R.id.cart -> {
                        //loadFragment(SettingFragment())
                        true
                    }

                    else -> {
                        false
                    }
                }
            }
        }
    }
}