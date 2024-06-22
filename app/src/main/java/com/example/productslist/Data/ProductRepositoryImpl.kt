package com.example.productslist.Data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.productslist.Domain.Product
import com.example.productslist.Domain.ProductsRepository

class ProductRepositoryImpl : ProductsRepository {

    private val listOfProducts: MutableSet<Product> = sortedSetOf(object : Comparator<Product> {
        override fun compare(o1: Product?, o2: Product?): Int {
            if (o1 != null && o2 != null) {
                return if (o1.id > o2.id) {
                    1
                } else if (o1.id < o2.id){
                    -1
                } else{
                    0
                }
            } else {
                throw RuntimeException()
            }
        }
    })

    init {
        for (i in 0..10){
           addProductToList(Product("Product $i", i,true))
        }
    }

    private var countId = 0
    override fun addProductToList(product: Product) {
        if (product.id == Product.UNDEFINED_ID) {
            product.id = countId++
        }
        listOfProducts.add(product)
    }

    override fun deleteProductInList(product: Product) {
        listOfProducts.remove(product)
    }

    override fun getProductList(): List<Product> {
        return listOfProducts.toList()
    }

    override fun editProductInList(product: Product) {
        val oldProduct: Product? = listOfProducts.find { it.id == product.id }
        oldProduct?.let {
            deleteProductInList(it)
            addProductToList(it)

        }

    }

    override fun getOneProductInList(productId: Int): Product {
        val productInCurrentList = listOfProducts.find { it.id == productId }
        productInCurrentList?.let { return it } ?: throw RuntimeException()
    }
}