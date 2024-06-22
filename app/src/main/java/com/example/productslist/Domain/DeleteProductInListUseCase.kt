package com.example.productslist.Domain

class DeleteProductInListUseCase(private val repository: ProductsRepository) {

   fun deleteProductInList(product: Product){
       repository.deleteProductInList(product)
   }
}