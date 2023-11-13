package com.glitch.cybernexus.data.model.response

data class GetCategoryListResponse(
    val categories: List<Category>? = null
):BaseResponse()
