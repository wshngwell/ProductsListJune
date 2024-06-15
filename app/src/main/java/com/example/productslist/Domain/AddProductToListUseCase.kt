package com.example.productslist.Domain

class AddProductToListUseCase (private val repository: ProductsRepository ) {

    fun addProductToList(product: Product){
        repository.addProductToList(product)
    }
}