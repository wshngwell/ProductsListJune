package com.example.productslist.domain

import javax.inject.Inject

class AddProductToListUseCase @Inject constructor (private val repository: ProductsRepository) {

    suspend fun addProductToList(product: Product){
        repository.addProductToList(product)
    }
}