package com.example.productslist.Presentation

import android.os.Bundle
import android.renderscript.ScriptGroup.Binding
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.customview.widget.ViewDragHelper.Callback
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.example.productslist.R
import com.example.productslist.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerViewProducts: RecyclerView
    private lateinit var productsAdapter: ProductsListAdapter
    private lateinit var viewModel: MainViewModel
    private lateinit var floatingButtonProductItemActivity: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            recyclerViewProducts = recyclerViewListOfProducts
            floatingButtonProductItemActivity=floatingActionButtonOpenTheAddProductActivity
            productsAdapter = ProductsListAdapter()
            recyclerViewProducts.adapter = productsAdapter
            recyclerViewProducts.recycledViewPool.setMaxRecycledViews(
                ProductsListAdapter.PRODUCT_ENABLED,
                ProductsListAdapter.MAX_SIZE_VIEW_HOLDERS_IN_POOL
            )
            recyclerViewProducts.recycledViewPool.setMaxRecycledViews(
                ProductsListAdapter.PRODUCT_DISABLED,
                ProductsListAdapter.MAX_SIZE_VIEW_HOLDERS_IN_POOL
            )
        }
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        viewModel.productList.observe(this) {
            Log.d("MainActivity", it.toString())
            productsAdapter.submitList(it)
        }
        addOnClickListeners()

    }

    private fun addOnClickListeners() {
        productsAdapter.onChangeStateClickListener =
            { viewModel.editProductInList(it) }

        productsAdapter.onEditClickListener = {
            val intent = ProductItemActivity.newIntentEditMode(this, it.id )
            startActivity(intent)
        }
        val callback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val productInList = productsAdapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteProduct(productInList)
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(recyclerViewProducts)
        floatingButtonProductItemActivity.setOnClickListener{
            val intent = ProductItemActivity.newIntentAddingMode(this)
            startActivity(intent)
        }
    }
}