package com.glitch.cybernexus.ui.success

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.glitch.cybernexus.R
import com.glitch.cybernexus.common.viewBinding
import com.glitch.cybernexus.databinding.FragmentPaymentsuccessBinding


class SuccessFragment : Fragment(R.layout.fragment_paymentsuccess) {

    private val binding by viewBinding(FragmentPaymentsuccessBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            btnContinueShopping.setOnClickListener {
                findNavController().navigate(R.id.action_successFragment_to_homeFragment)
            }
        }
    }
}