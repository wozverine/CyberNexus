package com.glitch.cybernexus.data.model

data class GetCategoryListResponse(
    val categories: List<String>?,
    val status: Int?,
    val message: String?
)
