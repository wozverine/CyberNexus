package com.glitch.cybernexus.ui.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.glitch.cybernexus.R
import com.glitch.cybernexus.common.viewBinding
import com.glitch.cybernexus.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : Fragment(R.layout.fragment_splash) {

    private val binding by viewBinding(FragmentSplashBinding::bind)

    private val viewModel by viewModels<SplashViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeData()
    }

    private fun observeData() {
        viewModel.splashState.observe(viewLifecycleOwner) { state ->
            when (state) {
                SplashState.GoSignIn -> {
                    findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToSignInFragment())
                }

                SplashState.GoHome -> {
                    findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToHomeFragment())
                }
            }
        }
    }
}