package com.example.productslist.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.productslist.data.database.AppDataBaseDao
import com.example.productslist.domain.Product
import com.example.productslist.domain.ProductsRepository
import javax.inject.Inject

class ProductRepositoryImpl
    @Inject constructor(
    private val productsDao: AppDataBaseDao,
    private val mapper: Mapper
) : ProductsRepository {

    override suspend fun addProductToList(product: Product) {
        productsDao.addProductToList(mapper.mapEntityToDbModel(product))
    }

    override suspend fun deleteProductInList(product: Product) {
        productsDao.deleteProductFromList(mapper.mapEntityToDbModel(product).id)
    }

    override fun getProductList(): LiveData<List<Product>> =
        MediatorLiveData<List<Product>>().apply {
            addSource(productsDao.getProductsList()) {
                value = mapper.mapDbListToEntityList(it)
            }

        }

    override suspend fun editProductInList(product: Product) {
        productsDao.addProductToList(mapper.mapEntityToDbModel(product))

    }

    override suspend fun getOneProductInList(productId: Int): Product {
        val productDbModel = productsDao.getOneProduct(productId)
        return mapper.mapDbModelToEntity(productDbModel)
    }

}