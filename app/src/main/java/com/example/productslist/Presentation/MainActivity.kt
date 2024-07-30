package com.example.productslist.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.productslist.R
import com.example.productslist.databinding.ActivityMainBinding
import javax.inject.Inject

class MainActivity : AppCompatActivity(), OnEndingOfEditingFragment {

    private lateinit var binding: ActivityMainBinding
    private lateinit var productsAdapter: ProductsListAdapter

     @Inject
     lateinit var viewModelFactory:ViewModelFactory

    private lateinit var viewModel: MainViewModel

    private val component by lazy {
        (application as ProductApp).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            productsAdapter = ProductsListAdapter()
            recyclerViewListOfProducts.adapter = productsAdapter
            recyclerViewListOfProducts.recycledViewPool.setMaxRecycledViews(
                ProductsListAdapter.PRODUCT_ENABLED,
                ProductsListAdapter.MAX_SIZE_VIEW_HOLDERS_IN_POOL
            )
            recyclerViewListOfProducts.recycledViewPool.setMaxRecycledViews(
                ProductsListAdapter.PRODUCT_DISABLED,
                ProductsListAdapter.MAX_SIZE_VIEW_HOLDERS_IN_POOL
            )
        }
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]

        viewModel.productList.observe(this) {
            productsAdapter.submitList(it)
        }
        addOnClickListeners()
    }


    private fun addOnClickListeners() {
        productsAdapter.onChangeStateClickListener =
            { viewModel.editProductInList(it) }


        if (binding.fragmentMainContainer != null) {
            productsAdapter.onEditClickListener = {
                supportFragmentManager.beginTransaction()
                    .replace(
                        R.id.fragmentMainContainer,
                        ProductItemFragment.addFragmentInEditMode(it.id)
                    )
                    .addToBackStack(null)
                    .commit()
            }
            binding.floatingActionButtonOpenTheAddProductActivity.setOnClickListener {
                supportFragmentManager.beginTransaction()
                    .replace(
                        R.id.fragmentMainContainer,
                        ProductItemFragment.addFragmentInAddMode()
                    )
                    .addToBackStack(null)
                    .commit()
            }
        }else{
            productsAdapter.onEditClickListener = {
                val intent = ProductItemActivity.newIntentEditMode(this, it.id)
                startActivity(intent)
            }

            binding.floatingActionButtonOpenTheAddProductActivity.setOnClickListener {
                val intent = ProductItemActivity.newIntentAddingMode(this)
                startActivity(intent)
            }
        }



        val callback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
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
        itemTouchHelper.attachToRecyclerView(binding.recyclerViewListOfProducts)

    }

    override fun endingOfEditingFragment() {
        supportFragmentManager.popBackStack()
    }
}