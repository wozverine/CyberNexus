package com.glitch.cybernexus.data.source

import com.glitch.cybernexus.data.model.Product

object Database {
    private val product = mutableListOf<Product>()

    fun getProduct(): List<Product> {
        return product
    }

    fun addProduct(title: String, description: String) {
        val newNote = Product(
            id = (product.lastOrNull()?.id ?: 0) + 1,
            title = title,
            description = description
        )
        product.add(newNote)
    }
}