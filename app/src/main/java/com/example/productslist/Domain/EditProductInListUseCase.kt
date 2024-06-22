package com.example.productslist.Domain

class EditProductInListUseCase(private val repository: ProductsRepository) {

    fun editProductInList(product:Product) {
        repository.editProductInList(product)
    }
}