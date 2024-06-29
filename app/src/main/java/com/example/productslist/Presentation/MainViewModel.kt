package com.example.productslist.Presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.productslist.Data.ProductRepositoryImpl
import com.example.productslist.Domain.DeleteProductInListUseCase
import com.example.productslist.Domain.EditProductInListUseCase
import com.example.productslist.Domain.GetProductListUseCase
import com.example.productslist.Domain.Product

class MainViewModel :ViewModel(){
    private val repository = ProductRepositoryImpl

    private val deleteProductInListUseCase = DeleteProductInListUseCase(repository)
    private val getProductInListUseCase = GetProductListUseCase(repository)
    private val editProductInListUseCase = EditProductInListUseCase(repository)

    val productList= getProductInListUseCase.getProductList()

    fun deleteProduct(product:Product){
        deleteProductInListUseCase.deleteProductInList(product)

    }
    fun editProductInList(product: Product){
        val newProduct = product.copy(enabled = !product.enabled)
        editProductInListUseCase.editProductInList(newProduct)
    }


}