package com.example.productslist.Presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.productslist.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.getProductInList()
        viewModel.productList.observe(this) {
            Log.d("MainActivity", it.toString())
        }
    }
}