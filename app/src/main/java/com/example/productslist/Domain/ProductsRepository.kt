package com.example.productslist.domain

import androidx.lifecycle.LiveData

interface ProductsRepository {

    suspend fun addProductToList(product: Product)
    suspend fun deleteProductInList(product: Product)
    fun getProductList(): LiveData<List<Product>>
    suspend fun editProductInList(product: Product)
    suspend fun getOneProductInList(productId: Int): Product
}