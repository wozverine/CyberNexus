package com.glitch.cybernexus.data.model.response

data class GetProductsResponse(
    val products: List<Product>?
) : BaseResponse()
