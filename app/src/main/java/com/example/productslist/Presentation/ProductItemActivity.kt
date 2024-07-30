package com.example.productslist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity


import com.example.productslist.R
import com.example.productslist.domain.Product


class ProductItemActivity : AppCompatActivity(), OnEndingOfEditingFragment {


    private var screenMode: String =UNDEFINED_SCREEN_MODE
    private var productId: Int = Product.UNDEFINED_ID


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_item)
        parseIntent()
        val fragment = when (screenMode) {
            EDIT_MODE -> ProductItemFragment.addFragmentInEditMode(productId)
            ADD_MODE -> ProductItemFragment.addFragmentInAddMode()
            else -> throw RuntimeException("Unknown screen mode")
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    override fun endingOfEditingFragment() {
        finish()
    }

    private fun parseIntent() {
        val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)
            ?: throw RuntimeException( "screenMode is null")
        screenMode = mode
        if (screenMode != EDIT_MODE && screenMode != ADD_MODE) {
            throw RuntimeException("Unknown screenMode")
        }
        if (screenMode == EDIT_MODE) {
            productId = intent.getIntExtra(EXTRA_PRODUCT_ID,0)
            if (productId==0){
                throw RuntimeException("Unknown productId")
            }

        }

    }


    companion object {

        private const val EXTRA_PRODUCT_ID = "Product_Id"
        private const val EXTRA_SCREEN_MODE = "EXTRA_EDIT_MODE"
        private const val EDIT_MODE = "EDIT_MODE"
        private const val ADD_MODE = "ADD_MODE"
        private const val UNDEFINED_SCREEN_MODE = ""

        fun newIntentAddingMode(context: Context): Intent {
            val intent = Intent(context, ProductItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, ADD_MODE)
            return intent
        }

        fun newIntentEditMode(context: Context, productId: Int): Intent {
            val intent = Intent(context, ProductItemActivity::class.java)
            intent.putExtra(EXTRA_PRODUCT_ID, productId)
            intent.putExtra(EXTRA_SCREEN_MODE, EDIT_MODE)
            return intent
        }
    }
}