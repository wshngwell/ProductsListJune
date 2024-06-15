package com.example.productslist.Domain

class DeleteProductInList(private val repository: ProductsRepository) {

   fun deleteProductInList(productId:Int){
       repository.deleteProductInList(productId)
   }
}