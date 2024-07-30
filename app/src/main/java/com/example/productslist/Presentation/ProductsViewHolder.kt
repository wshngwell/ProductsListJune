package com.example.productslist.presentation

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.productslist.R

class ProductsViewHolder( val view: View):ViewHolder(view) {
    val nameOfProduct = itemView.findViewById<TextView>(R.id.productNameId)
    val countOfProducts = itemView.findViewById<TextView>(R.id.countOfProducts)
}