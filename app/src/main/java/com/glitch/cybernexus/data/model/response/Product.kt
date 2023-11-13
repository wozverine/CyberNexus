package com.glitch.cybernexus.data.model.response

data class Product(
    val id: Int?,
    val title: String?,
    val price: Double?,
    val description: String?,
    val category: String?,
    val imageOne: String?,
    val imageTwo: String?,
    val imageThree: String?,
    val rate: Double?,
    val count: Int?,
    val saleState: Boolean?,
    val salePrice: Double,
    val isFav: Boolean = false
)
