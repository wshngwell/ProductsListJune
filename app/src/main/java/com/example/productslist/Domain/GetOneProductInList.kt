package com.example.productslist.Domain

class GetOneProductInList(private val repository: ProductsRepository) {
    fun getOneProductInList(productId: Int) {
        repository.getOneProductInList(productId)
    }
}