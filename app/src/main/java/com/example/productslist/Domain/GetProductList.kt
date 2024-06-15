package com.example.productslist.Domain

import androidx.lifecycle.LiveData

class GetProductList(private val repository: ProductsRepository) {
    fun getProductList():LiveData<List<Product>> {
        return repository.getProductList()
    }
}