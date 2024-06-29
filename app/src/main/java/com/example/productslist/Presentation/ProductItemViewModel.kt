package com.example.productslist.Presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.productslist.Data.ProductRepositoryImpl
import com.example.productslist.Domain.AddProductToListUseCase
import com.example.productslist.Domain.EditProductInListUseCase
import com.example.productslist.Domain.GetOneProductInListUseCase
import com.example.productslist.Domain.Product

class ProductItemViewModel :ViewModel() {

    val repository = ProductRepositoryImpl

    private val addProductToListUseCase = AddProductToListUseCase(repository)
    private val editProductInListUseCase = EditProductInListUseCase(repository)
    private val getOneProductInListUseCase = GetOneProductInListUseCase(repository)

    private val _errorName = MutableLiveData<Boolean>()
    val errorName:LiveData<Boolean>
        get() {
            return _errorName
        }
    private val _errorCount = MutableLiveData<Boolean>()
    val errorCount:LiveData<Boolean>
        get(){
            return _errorCount
        }

    private val _currentProduct = MutableLiveData<Product>()
    val currentProduct:LiveData<Product>
        get() {
            return _currentProduct
        }

    private val _shouldActivityBeFinished = MutableLiveData<Unit>()
    val shouldActivityBeFinished:LiveData<Unit>
        get() {
            return _shouldActivityBeFinished
        }

    fun getOunProductFromList(productId:Int) {
         _currentProduct.value = getOneProductInListUseCase.getOneProductInList(productId)
    }
    fun addProductToList(name:String?, count:String?){
        val newName = parseName(name)
        val newCount = parseCount(count)
        if(isInputCorrect(newName, newCount)){
            addProductToListUseCase.addProductToList(Product(newName, newCount,  true))
            finishActivity()
        }
    }

    fun editProductToList(name:String?, count:String?){
        val newName = parseName(name)
        val newCount = parseCount(count)
        if(isInputCorrect(newName, newCount)){
           val editableProduct = _currentProduct.value
            editableProduct?.let {
                editProductInListUseCase.editProductInList(editableProduct.copy(
                    name = newName,
                    count = newCount
                ))
                finishActivity()
            }
        }

    }

    private fun isInputCorrect(name: String, count:Int):Boolean{
        var result = true

        if (name.isBlank()){
           _errorName.value = true
            result =false
        }
        if(count<=0){
            _errorCount.value = true
            result =false
        }
        return result
    }




    private fun parseName(name:String?):String{
        return name?.trim()?:""
    }
    private fun parseCount(count:String?):Int{
        try {
            if (count!=null){
                val result = count.trim().toInt()
                return result
            }
            else return 0
        }catch(ex:Exception){
            return 0
        }
    }
    fun resetNameError(){
        _errorName.value = false
    }
    fun resetCountError(){
        _errorCount.value = false
    }
    private fun finishActivity(){
        _shouldActivityBeFinished.value = Unit
    }
}