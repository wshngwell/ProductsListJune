package com.example.productslist.Domain

import androidx.lifecycle.LiveData

interface ProductsRepository {

    fun addProductToList(product: Product)
    fun deleteProductInList(product:Product)
    fun getProductList():List<Product>
    fun editProductInList(product:Product)
    fun getOneProductInList(productId: Int):Product
}