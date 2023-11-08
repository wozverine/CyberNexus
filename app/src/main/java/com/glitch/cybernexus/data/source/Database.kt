package com.glitch.cybernexus.data.source

import com.glitch.cybernexus.data.model.Category
import com.glitch.cybernexus.data.model.Product

object Database {
    private val product = mutableListOf<Product>()
    private val category = mutableListOf<Category>()

    fun getProduct(): List<Product> {
        return product
    }

    fun addProduct(title: String, description: String, company: String) {
        val newProduct = Product(
            id = (product.lastOrNull()?.id ?: 0) + 1,
            title = title,
            description = description,
            company = company
        )
        product.add(newProduct)
    }

    fun getCategory(): List<Category> {
        return category
    }

    fun addCategory(title:String){
        val newCategory = Category(
            title = title
        )
        category.add(newCategory)
    }
}