package com.example.productslist.domain

import javax.inject.Inject

class EditProductInListUseCase @Inject constructor(private val repository: ProductsRepository) {

   suspend fun editProductInList(product: Product) {
        repository.editProductInList(product)
    }
}