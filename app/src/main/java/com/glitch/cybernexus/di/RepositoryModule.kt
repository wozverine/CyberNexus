package com.glitch.cybernexus.di

import com.glitch.cybernexus.data.repository.ProductRepository
import com.glitch.cybernexus.data.source.local.ProductDao
import com.glitch.cybernexus.data.source.remote.ProductService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideProductsRepository(
        productService: ProductService, productDao: ProductDao
    ) = ProductRepository(productService, productDao)
}