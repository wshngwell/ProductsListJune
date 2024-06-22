package com.example.productslist.Presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.productslist.Data.ProductRepositoryImpl
import com.example.productslist.Domain.DeleteProductInListUseCase
import com.example.productslist.Domain.EditProductInListUseCase
import com.example.productslist.Domain.GetProductListUseCase
import com.example.productslist.Domain.Product

class MainViewModel :ViewModel(){
    val repository = ProductRepositoryImpl()

    val productList = MutableLiveData<List<Product>>()

    val deleteProductInListUseCase = DeleteProductInListUseCase(repository)
    val getProductInListUseCase = GetProductListUseCase(repository)
    val editProductInListUseCase = EditProductInListUseCase(repository)

    fun deleteProduct(product:Product){
        deleteProductInListUseCase.deleteProductInList(product)
        getProductInList()
    }
    fun editProductInList(product: Product){
        val newProduct = product.copy(enabled = !product.enabled)
        editProductInListUseCase.editProductInList(newProduct)
        getProductInList()
    }
     fun getProductInList(){
        productList.value = getProductInListUseCase.getProductList()
    }

}