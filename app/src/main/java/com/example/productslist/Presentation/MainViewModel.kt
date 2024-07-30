package com.example.productslist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productslist.domain.DeleteProductInListUseCase
import com.example.productslist.domain.EditProductInListUseCase
import com.example.productslist.domain.GetProductListUseCase
import com.example.productslist.domain.Product
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor
    (
    private val deleteProductInListUseCase: DeleteProductInListUseCase,
    private val getProductInListUseCase: GetProductListUseCase,
    private val editProductInListUseCase: EditProductInListUseCase,
) : ViewModel() {

    val productList = getProductInListUseCase.getProductList()

    fun deleteProduct(product: Product) {
        viewModelScope.launch {
            deleteProductInListUseCase.deleteProductInList(product)
        }
    }

    fun editProductInList(product: Product) {
        viewModelScope.launch {
            val newProduct = product.copy(enabled = !product.enabled)
            editProductInListUseCase.editProductInList(newProduct)
        }

    }

}