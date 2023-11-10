package com.glitch.cybernexus.data.model

data class GetProductsResponse(
    val products: List<Product>?,
    val status: Int?,
    val message: String?
)
