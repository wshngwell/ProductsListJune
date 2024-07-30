package com.example.productslist.presentation

import androidx.recyclerview.widget.DiffUtil
import com.example.productslist.domain.Product

class ProductsDiffUtils : DiffUtil.ItemCallback<Product>() {

    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
         return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }
}