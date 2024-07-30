package com.example.productslist.domain

import androidx.lifecycle.LiveData
import javax.inject.Inject

class GetProductListUseCase @Inject constructor(private val repository: ProductsRepository) {
    fun getProductList():LiveData<List<Product>>{
        return repository.getProductList()
    }
}