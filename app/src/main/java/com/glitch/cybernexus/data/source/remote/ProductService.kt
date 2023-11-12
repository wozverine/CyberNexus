package com.glitch.cybernexus.data.source.remote

import com.glitch.cybernexus.data.model.GetCategoryListResponse
import com.glitch.cybernexus.data.model.GetProductDetailResponse
import com.glitch.cybernexus.data.model.GetProductsResponse
import com.glitch.cybernexus.data.model.GetSalesProductsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductService {
    @GET("get_products.php")
    fun getProducts(): Call<GetProductsResponse>

    @GET("get_categories.php")
    fun getCategories(): Call<GetCategoryListResponse>

    @GET("get_sale_products.php")
    fun getSalesProducts(): Call<GetSalesProductsResponse>

    @GET("get_product_detail.php")
    fun getProductDetail(
        @Query("id") id: Int
    ): Call<GetProductDetailResponse>
}
