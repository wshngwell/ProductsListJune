package com.example.productslist.Presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet.Layout
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.productslist.Domain.Product
import com.example.productslist.R

class ProductsListAdapter : ListAdapter<Product, ProductsViewHolder>(ProductsDiffUtils()) {


    var onEditClickListener:((Product)->Unit)? = null
    var onChangeStateClickListener:((Product)->Unit)? = null
    var count = 0


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val layout = when (viewType) {
            PRODUCT_DISABLED -> R.layout.product_item_disabled
            PRODUCT_ENABLED -> R.layout.product_item_enabled
            else->throw RuntimeException("Unknown layout")
        }

        val view = LayoutInflater.from(parent.context).inflate(layout,parent,false)
        return ProductsViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        val product =getItem(position)
        return if (product.enabled) {
            PRODUCT_DISABLED
        } else {
            PRODUCT_ENABLED
        }
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        val product = getItem(position)
        holder.nameOfProduct.text = product.name
        holder.countOfProducts.text = product.count.toString()
        holder.view.setOnClickListener{
            onEditClickListener?.invoke(product)
        }
        holder.view.setOnLongClickListener{
            onChangeStateClickListener?.invoke(product)
            true
        }

    }

    companion object {
        const val PRODUCT_DISABLED = 100
        const val PRODUCT_ENABLED = 200

        const val MAX_SIZE_VIEW_HOLDERS_IN_POOL = 15
    }
}