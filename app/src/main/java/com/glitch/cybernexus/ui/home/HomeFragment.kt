package com.glitch.cybernexus.ui.home

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
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

    private lateinit var sharedPref: SharedPreferences

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

        sharedPref = requireActivity().getSharedPreferences("AppSettings", Context.MODE_PRIVATE)

        val isLogin = sharedPref.getBoolean("isLogin", false)

        observeData()

        with(binding) {
            rvFlashSale.adapter = saleAdapter
            rvAllProducts.adapter = productAdapter

            btnLogout.setOnClickListener {
                viewModel.clearFavorites()
                sharedPref.edit().putBoolean("isLogin", false).apply()
                viewModel.logOut()
            }
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
                    tvEmpty.text = state.failMessage
                    tvEmpty.visible()
                    ivEmpty.visible()
                }

                is HomeState.ShowMessage -> {
                    progressBar.gone()
                    Snackbar.make(requireView(), state.errorMessage, 1000).show()
                }

                HomeState.GoToSignIn -> {
                    findNavController().navigate(R.id.action_homeFragment_to_signInFragment)
                    //findNavController().popBackStack()
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

                is SaleState.ShowMessage -> {
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
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailsFragment(id))
    }

    private fun onFavProductClick(product: ProductUI) {
        viewModel.setFavoriteState(product)
        Log.v("aaaaa",product.isFav.toString())
    }
}