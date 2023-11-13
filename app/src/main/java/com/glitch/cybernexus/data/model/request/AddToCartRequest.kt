package com.glitch.cybernexus.data.model.request

data class AddToCartRequest(
    val userId: String? = null,
    val productId: Int? = null
)
