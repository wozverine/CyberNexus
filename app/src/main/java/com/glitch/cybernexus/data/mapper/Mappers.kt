package com.glitch.cybernexus.data.mapper

import com.glitch.cybernexus.data.model.response.Product
import com.glitch.cybernexus.data.model.response.ProductEntity
import com.glitch.cybernexus.data.model.response.ProductUI

fun Product.mapToProductUI(favorites: List<Int>) =
    ProductUI(
        id = id ?: 1,
        title = title.orEmpty(),
        price = price ?: 0.0,
        description = description.orEmpty(),
        category = category.orEmpty(),
        imageOne = imageOne.orEmpty(),
        imageTwo = imageTwo.orEmpty(),
        imageThree = imageThree.orEmpty(),
        rate = rate ?: 0.0,
        count = count ?: 0,
        saleState = saleState ?: false,
        salePrice = salePrice ?: 0.0,
        isFav = favorites.contains(id)
    )

fun List<Product>.mapProductToProductUI(favorites: List<Int>) =
    map {
        ProductUI(
            id = it.id ?: 1,
            title = it.title.orEmpty(),
            price = it.price ?: 0.0,
            description = it.description.orEmpty(),
            category = it.category.orEmpty(),
            imageOne = it.imageOne.orEmpty(),
            imageTwo = it.imageTwo.orEmpty(),
            imageThree = it.imageThree.orEmpty(),
            rate = it.rate ?: 0.0,
            count = it.count ?: 0,
            saleState = it.saleState ?: false,
            salePrice = it.salePrice ?: 0.0,
            isFav = favorites.contains(it.id)
        )
    }

fun ProductUI.mapToProductEntity() =
    ProductEntity(
        productId = id,
        title = title,
        price = price,
        imageOne = imageOne,
        imageTwo = imageTwo,
        imageThree = imageThree,
        description = description,
        category = category,
        rate = rate,
        count = count,
        salePrice = salePrice ?: 0.0,
        saleState = saleState
    )

fun List<ProductEntity>.mapProductEntityToProductUI() =
    map {
        ProductUI(
            id = it.productId ?: 1,
            title = it.title.orEmpty(),
            price = it.price ?: 0.0,
            imageOne = it.imageOne.orEmpty(),
            imageTwo = it.imageTwo.orEmpty(),
            imageThree = it.imageThree.orEmpty(),
            rate = it.rate ?: 0.0,
            count = it.count ?: 0,
            saleState = it.saleState ?: false,
            salePrice = it.salePrice ?: 0.0,
            description = it.description.orEmpty(),
            category = it.category.orEmpty()
        )
    }
