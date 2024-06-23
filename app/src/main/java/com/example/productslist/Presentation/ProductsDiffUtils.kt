package com.example.productslist.Presentation

import androidx.recyclerview.widget.DiffUtil
import com.example.productslist.Domain.Product

class ProductsDiffUtils : DiffUtil.ItemCallback<Product>() {

    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
         return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }
}