package com.example.productslist.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AppDataBaseDao {

    @Query("SELECT * FROM PRODUCTSTABLE")
    fun getProductsList():LiveData<List<ProductDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun addProductToList(productDbModel: ProductDbModel)

    @Query( "DELETE FROM PRODUCTSTABLE WHERE id =:productDbModelID")
    suspend fun deleteProductFromList(productDbModelID: Int)

    @Query("SELECT * FROM productsTable WHERE id =:productDbModelID")
    suspend fun getOneProduct(productDbModelID:Int): ProductDbModel
}