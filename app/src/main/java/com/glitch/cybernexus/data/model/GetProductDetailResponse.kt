package com.glitch.cybernexus.data.model

data class GetProductDetailResponse(
    val product: Product?,
    val status: Int?,
    val message: String?
)
