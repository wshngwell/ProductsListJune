package com.example.productslist.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "productsTable")
class ProductDbModel (
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val name:String,
    val count:Int,
    val enabled:Boolean
)