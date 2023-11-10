package com.glitch.cybernexus.data.source.remote

import com.glitch.cybernexus.data.model.GetProductDetailResponse
import com.glitch.cybernexus.data.model.GetProductsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

//constant val
interface ProductService {
    @GET("get_products.php")
    fun getProducts(): Call<GetProductsResponse>

    @GET("get_product_detail.php")
    fun getProductDetail(
        @Query("id") id: Int
    ): Call<GetProductDetailResponse>
}
