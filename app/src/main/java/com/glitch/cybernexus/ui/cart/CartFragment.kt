package com.glitch.cybernexus.ui.cart

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.glitch.cybernexus.R
import com.glitch.cybernexus.common.gone
import com.glitch.cybernexus.common.viewBinding
import com.glitch.cybernexus.common.visible
import com.glitch.cybernexus.databinding.FragmentCartBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment(R.layout.fragment_cart) {
    private val binding by viewBinding(FragmentCartBinding::bind)

    private val viewModel by viewModels<CartViewHolder>()

    private val cartAdapter =
        CartAdapter(onProductClick = ::onProductClick, onDeleteClick = ::onDeleteClick)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getCartProducts()

        observeData()

        with(binding) {
            rvCart.adapter = cartAdapter

            btnClearCart.setOnClickListener{
                viewModel.clearCart()
            }

            /*ivBackCart.setOnClickListener{
                findNavController().navigateUp()
            }

            btnCheckOut.setOnClickListener{
                findNavController().navigate(CartFragmentDirections.cartToPayment())*/
        }
    }


    private fun observeData() = with(binding) {
        viewModel.cartState.observe(viewLifecycleOwner) {
            when (it) {
                CartState.Loading -> {
                    progressBar.visible()
                    tvTotalamountnumber.gone()
                    tvTotalamounttext.gone()
                    btnClearCart.gone()
                    tvCart.gone()
                    btnCheckOut.gone()
                }

                is CartState.SuccessState -> {
                    cartAdapter.submitList(it.products)
                    rvCart.visible()
                    tvTotalamountnumber.visible()
                    tvTotalamounttext.visible()
                    btnClearCart.visible()
                    btnCheckOut.visible()
                    tvCart.visible()
                    progressBar.gone()
                }

                is CartState.EmptyScreen -> {
                    rvCart.gone()
                    tvTotalamountnumber.gone()
                    tvTotalamounttext.gone()
                    btnClearCart.gone()
                    tvCart.gone()
                    btnCheckOut.gone()
                    progressBar.gone()
                    tvEmpty.text = it.failMessage
                    tvEmpty.visible()
                    ivEmpty.visible()
                }

                is CartState.ShowMessage -> {
                    progressBar.gone()
                    tvTotalamountnumber.gone()
                    tvTotalamounttext.gone()
                    Snackbar.make(requireView(), it.errorMessage, 1000).show()
                }
            }
        }

        viewModel.totalAmount.observe(viewLifecycleOwner) { amount ->
            tvTotalamountnumber.text = String.format("₺ %.2f", amount)
        }
    }

    private fun onProductClick(id: Int) {
        findNavController().navigate(CartFragmentDirections.actionCartFragmentToDetailsFragment(id))
    }

    private fun onDeleteClick(productId: Int) {
        viewModel.deleteFromCart(productId)
    }
}