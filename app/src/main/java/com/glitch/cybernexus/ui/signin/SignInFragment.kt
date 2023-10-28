package com.glitch.cybernexus.ui.signin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.glitch.cybernexus.R
import com.glitch.cybernexus.databinding.FragmentSigninBinding

class SignInFragment : Fragment() {
    private var _binding: FragmentSigninBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSigninBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            loginNextBtn.setOnClickListener(){
                findNavController().navigate(R.id.action_signInFragment_to_homeFragment)
            }
            signupTv.setOnClickListener(){
                findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}