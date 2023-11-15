package com.glitch.cybernexus.ui.payment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.glitch.cybernexus.R
import com.glitch.cybernexus.common.gone
import com.glitch.cybernexus.common.viewBinding
import com.glitch.cybernexus.common.visible
import com.glitch.cybernexus.databinding.FragmentPaymentBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentFragment : Fragment(R.layout.fragment_payment) {

    private val binding by viewBinding(FragmentPaymentBinding::bind)

    private val viewModel by viewModels<PaymentViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            btnOrder.setOnClickListener {
                viewModel.orderPayment(
                    etNameCard.text.toString(),
                    etCardNum.text.toString(),
                    etExpireMonth.text.toString(),
                    etExpireYear.text.toString(),
                    etCvv.text.toString()
                )
            }

            btnBack.setOnClickListener {
                findNavController().navigateUp()
            }
        }

        observeData()
    }

    private fun observeData() = with(binding) {
        viewModel.paymentState.observe(viewLifecycleOwner) { state ->
            when (state) {
                PaymentState.Loading -> progressBar.visible()

                is PaymentState.GoSuccess -> {
                    progressBar.gone()
                    findNavController().navigate(PaymentFragmentDirections.actionPaymentFragmentToSuccessFragment())
                }

                is PaymentState.ShowPopUp -> {
                    progressBar.gone()
                    Snackbar.make(requireView(), state.errorMessage, 1000).show()
                }
            }
        }
    }
}