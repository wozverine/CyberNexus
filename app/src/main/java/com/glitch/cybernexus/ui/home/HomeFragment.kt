package com.glitch.cybernexus.ui.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.glitch.cybernexus.R
import com.glitch.cybernexus.common.gone
import com.glitch.cybernexus.common.viewBinding
import com.glitch.cybernexus.common.visible
import com.glitch.cybernexus.data.model.response.ProductUI
import com.glitch.cybernexus.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding by viewBinding(FragmentHomeBinding::bind)

    private val viewModel by viewModels<HomeViewModel>()

    private val saleAdapter = SaleAdapter(
        onSaleClick = ::onSaleClick,
        onFavProductClick = ::onFavProductClick
    )

    private val productAdapter = ProductAdapter(
        onAllProductClick = ::onProductClick,
        onFavProductClick = ::onFavProductClick
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getProducts()
        viewModel.getSaleProducts()

        observeData()

        with(binding) {
            rvFlashSale.adapter = saleAdapter
            rvAllProducts.adapter = productAdapter
        }

    }

    private fun observeData() = with(binding) {
        viewModel.homeState.observe(viewLifecycleOwner) { state ->
            when (state) {
                HomeState.Loading -> progressBar.visible()

                is HomeState.SuccessState -> {
                    progressBar.gone()
                    productAdapter.submitList(state.products)
                }

                is HomeState.EmptyScreen -> {
                    progressBar.gone()
                    rvAllProducts.gone()
                    rvFlashSale.gone()
                    tvEmpty.text = state.failMessage
                    tvEmpty.visible()
                    ivEmpty.visible()
                }

                is HomeState.ShowPopUp -> {
                    progressBar.gone()
                    Snackbar.make(requireView(), state.errorMessage, 1000).show()
                }

                HomeState.GoToSignIn -> {
                    findNavController().navigate(R.id.action_signInFragment_to_homeFragment)
                }
            }
        }

        viewModel.saleState.observe(viewLifecycleOwner) {
            when (it) {
                SaleState.Loading -> progressBar.visible()

                is SaleState.SuccessState -> {
                    progressBar.gone()
                    saleAdapter.submitList(it.products)
                }

                is SaleState.EmptyScreen -> {
                    progressBar.gone()
                    rvFlashSale.gone()
                    tvEmpty.text = it.failMessage
                    tvEmpty.visible()
                    ivEmpty.visible()
                }

                is SaleState.ShowPopUp -> {
                    progressBar.gone()
                    Snackbar.make(requireView(), it.errorMessage, 1000).show()
                }
            }
        }
    }


    private fun onProductClick(id: Int){
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailsFragment(id))
    }

    private fun onSaleClick(id: Int) {
        Toast.makeText(requireContext(), id, Toast.LENGTH_SHORT).show()
    }

    private fun onFavProductClick(product: ProductUI) {
        Toast.makeText(requireContext(), product.title, Toast.LENGTH_SHORT).show()
    }
}