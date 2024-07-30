package com.example.productslist.domain

data class Product(
    val name:String,
    val count:Int,
    val enabled:Boolean,
    var id:Int = UNDEFINED_ID)
{
    companion object{
        const val UNDEFINED_ID = 0
    }
}