package com.glitch.cybernexus.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.glitch.cybernexus.MainApplication
import com.glitch.cybernexus.data.model.GetCategoryListResponse
import com.glitch.cybernexus.data.model.GetProductsResponse
import com.glitch.cybernexus.data.model.GetSalesProductsResponse
import com.glitch.cybernexus.databinding.FragmentHomeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val saleAdapter = SaleAdapter(
        onSaleClick = ::onSaleClick
    )

    private val productAdapter = ProductAdapter(
        onAllProductClick = ::onProductClick
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

        getProducts()
        getCategories()
        getSalesProducts()

        with(binding) {
            flashSaleRv.adapter = saleAdapter
            allProductsRv.adapter = productAdapter
            categoriesRv.adapter = categoryAdapter
        }
    }

    private fun getCategories() {
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
    }

    private fun onProductClick(id: Int) {
        Toast.makeText(requireContext(), id, Toast.LENGTH_SHORT).show()
    }

    private fun onSaleClick(id: Int) {
        Toast.makeText(requireContext(), id, Toast.LENGTH_SHORT).show()
    }

    private fun onCategoryClick(desc: String) {
        Toast.makeText(requireContext(), desc, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}