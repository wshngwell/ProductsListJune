package com.example.productslist.Domain

import androidx.lifecycle.LiveData

class GetProductListUseCase(private val repository: ProductsRepository) {

    fun getProductList():LiveData<List<Product>> {
        return repository.getProductList()
    }
}