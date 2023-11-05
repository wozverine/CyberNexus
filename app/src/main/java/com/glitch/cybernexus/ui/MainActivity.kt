package com.glitch.cybernexus.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
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

        }

        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> navController.navigate(R.id.homeFragment)
                //R.id.search -> navController.navigate(R.id.)
                R.id.favorites -> navController.navigate(R.id.favoritesFragment)
                R.id.cart -> navController.navigate(R.id.cartFragment)
            }
            true
        }/*with(binding) {
            bottomNavigation.setOnItemSelectedListener {
                when (it.itemId) {
                    com.glitch.cybernexus.R.id.home -> {
                        loadFragment(com.glitch.cybernexus.R.id.homeFragment)
                        true
                    }

                    com.glitch.cybernexus.R.id.search -> {
                        findNavController().navigate(com.glitch.cybernexus.R.id.search)
                        true
                    }

                    com.glitch.cybernexus.R.id.favorites -> {
                        findNavController().navigate(com.glitch.cybernexus.R.id.favorites)
                        true
                    }

                    com.glitch.cybernexus.R.id.cart -> {
                        findNavController().navigate(com.glitch.cybernexus.R.id.cart)
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