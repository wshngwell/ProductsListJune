package com.example.productslist.Domain

class EditProductInList(private val repository: ProductsRepository) {

    fun editProductInList(productId:Int) {
        repository.editProductInList(productId)
    }
}