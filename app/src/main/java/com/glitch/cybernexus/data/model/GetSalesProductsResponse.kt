package com.glitch.cybernexus.data.model

data class GetSalesProductsResponse(
    val products: List<Product>?,
    val status: Int?,
    val message: String?
)
