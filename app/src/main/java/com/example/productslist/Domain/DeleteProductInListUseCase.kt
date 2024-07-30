package com.example.productslist.domain

import javax.inject.Inject

class DeleteProductInListUseCase @Inject constructor(private val repository: ProductsRepository) {

   suspend fun deleteProductInList(product: Product){
       repository.deleteProductInList(product)
   }
}