package com.glitch.cybernexus.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.glitch.cybernexus.common.gone
import com.glitch.cybernexus.common.visible
import com.glitch.cybernexus.data.model.response.ProductUI
import com.glitch.cybernexus.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<HomeViewModel>()

    private val saleAdapter = SaleAdapter(
        onSaleClick = ::onSaleClick
    )

    private val productAdapter = ProductAdapter(
        onAllProductClick = ::onProductClick,
        onFavProductClick = ::onFavProductClick
    )

    private val categoryAdapter = CategoryAdapter(
        onCategoryClick = ::onCategoryClick
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getProducts()
        //getCategories()
        //getSalesProducts()

        with(binding) {
            //flashSaleRv.adapter = saleAdapter
            allProductsRv.adapter = productAdapter
            //categoriesRv.adapter = categoryAdapter
        }
        observeData()
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
                    ivEmpty.visible()
                    tvEmpty.visible()
                    tvEmpty.text = state.failMessage
                }

                is HomeState.ShowPopUp -> {
                    progressBar.gone()
                    Snackbar.make(requireView(), state.errorMessage, 1000).show()
                }
            }
        }
    }
    /*private fun getCategories() {
        MainApplication.productService?.getCategories()
            ?.enqueue(object : Callback<GetCategoryListResponse> {

                override fun onResponse(
                    call: Call<GetCategoryListResponse>,
                    response: Response<GetCategoryListResponse>
                ) {
                    val result = response.body()

                    if (result?.status == 200) {
                        //Log.e("lister category", result.categories.toString())
                        categoryAdapter.submitList(result.categories.orEmpty())
                    } else {
                        Toast.makeText(requireContext(), result?.message, Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<GetCategoryListResponse>, t: Throwable) {
                    Log.e("CantGetCategories", t.message.orEmpty())
                }
            })
    }

    private fun getProducts() {
        MainApplication.productService?.getProducts()
            ?.enqueue(object : Callback<GetProductsResponse> {

                override fun onResponse(
                    call: Call<GetProductsResponse>,
                    response: Response<GetProductsResponse>
                ) {
                    val result = response.body()

                    if (result?.status == 200) {
                        //Log.e("lister product", result.products.toString())
                        productAdapter.submitList(result.products.orEmpty())
                    } else {
                        Toast.makeText(requireContext(), result?.message, Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<GetProductsResponse>, t: Throwable) {
                    Log.e("CantGetProducts", t.message.orEmpty())
                }
            })
    }

    private fun getSalesProducts() {
        MainApplication.productService?.getSalesProducts()
            ?.enqueue(object : Callback<GetSalesProductsResponse> {

                override fun onResponse(
                    call: Call<GetSalesProductsResponse>,
                    response: Response<GetSalesProductsResponse>
                ) {
                    val result = response.body()

                    if (result?.status == 200) {
                        //Log.e("lister product", result.products.toString())
                        saleAdapter.submitList(result.products.orEmpty())
                    } else {
                        Toast.makeText(requireContext(), result?.message, Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<GetSalesProductsResponse>, t: Throwable) {
                    Log.e("CantGetProducts", t.message.orEmpty())
                }
            })
    }*/

    private fun onProductClick(id: Int) {
        Toast.makeText(requireContext(), id, Toast.LENGTH_SHORT).show()
    }

    private fun onSaleClick(id: Int) {
        Toast.makeText(requireContext(), id, Toast.LENGTH_SHORT).show()
    }

    private fun onCategoryClick(desc: String) {
        Toast.makeText(requireContext(), desc, Toast.LENGTH_SHORT).show()
    }

    private fun onFavProductClick(product: ProductUI) {
        Toast.makeText(requireContext(), product.title, Toast.LENGTH_SHORT).show()
        //viewModel.setFavoriteState(product)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}