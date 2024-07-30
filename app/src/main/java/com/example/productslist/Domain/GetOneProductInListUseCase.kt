package com.example.productslist.domain

import com.example.productslist.domain.Product
import com.example.productslist.domain.ProductsRepository
import javax.inject.Inject

class GetOneProductInListUseCase @Inject constructor(private val repository: ProductsRepository) {

    suspend fun getOneProductInList(productId: Int): Product {
        return repository.getOneProductInList(productId)
    }

}