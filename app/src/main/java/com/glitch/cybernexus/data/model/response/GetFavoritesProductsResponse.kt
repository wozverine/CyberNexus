package com.glitch.cybernexus.data.model.response

data class GetFavoritesProductsResponse(
    val products: List<Product>?
) : BaseResponse()
