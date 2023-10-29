package com.glitch.cybernexus.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
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
                R.id.signInFragment -> binding.bottomNavigation.visibility = View.GONE
                R.id.signUpFragment -> binding.bottomNavigation.visibility = View.GONE
                //R.id.mineFragment -> showBottomNav()
                else -> binding.bottomNavigation.visibility = View.VISIBLE
            }
        }/*with(binding) {
            bottomNavigation.setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.home -> {
                        findNavController().navigate(R.id.home)
                        true
                    }

                    R.id.search -> {
                        findNavController().navigate(R.id.search)
                        true
                    }

                    R.id.favorites -> {
                        findNavController().navigate(R.id.favorites)
                        true
                    }

                    R.id.cart -> {
                        findNavController().navigate(R.id.cart)
                        true
                    }

                    else -> {
                        false
                    }
                }
            }
        }*/
    }
}