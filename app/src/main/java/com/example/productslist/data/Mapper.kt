package com.example.productslist.data

import com.example.productslist.data.database.ProductDbModel
import com.example.productslist.domain.Product
import javax.inject.Inject

class Mapper @Inject constructor(){

    fun mapEntityToDbModel(product: Product) = ProductDbModel(
        id = product.id,
        name = product.name,
        count = product.count,
        enabled = product.enabled
    )
    fun mapDbModelToEntity(productDbModel: ProductDbModel) = Product(
        id = productDbModel.id,
        name = productDbModel.name,
        count = productDbModel.count,
        enabled = productDbModel.enabled
    )

    fun mapDbListToEntityList(list:List<ProductDbModel>) = list.map {
        mapDbModelToEntity(it)
    }
}