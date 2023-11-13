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

/*
@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<HomeViewModel>()
*/
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

    /*override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }*/

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getProducts()
        viewModel.getSaleProducts()
        //getCategories()

        observeData()

        with(binding) {
            flashSaleRv.adapter = saleAdapter
            allProductsRv.adapter = productAdapter
        }

        /*ivLogOut.setOnClickListener {
            viewModel.logOut()
        }*/

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
                    allProductsRv.gone()
                    flashSaleRv.gone()
                    /*tvError.text = state.failMessage
                    tvError.visible()
                    ivError.visible()*/
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
                    flashSaleRv.gone()
                    /*tvError.text = it.failMessage
                    tvError.visible()
                    ivError.visible()*/
                }

                is SaleState.ShowPopUp -> {
                    progressBar.gone()
                    Snackbar.make(requireView(), it.errorMessage, 1000).show()
                }
            }
        }
    }


    private fun onProductClick(id: Int) {
        Toast.makeText(requireContext(), id, Toast.LENGTH_SHORT).show()
    }

    private fun onSaleClick(id: Int) {
        Toast.makeText(requireContext(), id, Toast.LENGTH_SHORT).show()
    }

    private fun onFavProductClick(product: ProductUI) {
        Toast.makeText(requireContext(), product.title, Toast.LENGTH_SHORT).show()
        //viewModel.setFavoriteState(product)
    }

    /*    override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }*/
}