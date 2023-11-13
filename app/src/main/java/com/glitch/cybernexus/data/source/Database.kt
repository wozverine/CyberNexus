package com.glitch.cybernexus.data.source

import com.glitch.cybernexus.data.model.response.Category
import com.glitch.cybernexus.data.model.response.Product

object Database {
    private val product = mutableListOf<Product>()
    private val category = mutableListOf<Category>()

    fun getProduct(): List<Product> {
        return product
    }

    fun addProduct(
        title: String,
        price: Double,
        description: String,
        category: String,
        imageOne: String,
        imageTwo: String,
        imageThree: String,
        rate: Double,
        count: Int,
        saleState: Boolean,
        salePrice: Double
    ) {
        val newProduct = Product(
            id = (product.lastOrNull()?.id ?: 0) + 1,
            title = title,
            price = price,
            description = description,
            category = category,
            imageOne = imageOne,
            imageTwo = imageTwo,
            imageThree = imageThree,
            rate = rate,
            count = count,
            saleState = saleState,
            salePrice = salePrice
        )
        product.add(newProduct)
    }

    fun getCategory(): List<Category> {
        return category
    }

    fun addCategory(title: String) {
        val newCategory = Category(
            title = title
        )
        category.add(newCategory)
    }
}