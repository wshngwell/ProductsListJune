package com.example.productslist.Domain

import androidx.lifecycle.LiveData

interface ProductsRepository {

    fun addProductToList(product: Product)
    fun deleteProductInList(productId:Int)
    fun getProductList():LiveData<List<Product>>
    fun editProductInList(productId: Int)
    fun getOneProductInList(productId: Int):Product
}