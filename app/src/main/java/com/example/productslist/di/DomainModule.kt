package com.example.productslist.di

import com.example.productslist.data.ProductRepositoryImpl
import com.example.productslist.domain.ProductsRepository
import dagger.Binds
import dagger.Module

@Module
interface DomainModule {
    @ApplicationScope
    @Binds
    fun bindsProductRepository(productRepositoryImpl: ProductRepositoryImpl): ProductsRepository
}