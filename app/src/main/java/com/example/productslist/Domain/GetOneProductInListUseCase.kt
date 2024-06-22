package com.example.productslist.Domain

class GetOneProductInListUseCase(private val repository: ProductsRepository) {
    fun getOneProductInList(productId: Int) {
        repository.getOneProductInList(productId)
    }
}