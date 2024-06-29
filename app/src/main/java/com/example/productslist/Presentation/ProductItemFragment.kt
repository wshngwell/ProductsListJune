package com.example.productslist.Presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.productslist.Domain.Product
import com.example.productslist.R
import com.example.productslist.databinding.ActivityProductItemBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class ProductItemFragment(
    private var screenMode: String = UNDEFINED_SCREEN_MODE,
    private var productId: Int = Product.UNDEFINED_ID
) : Fragment() {


    private lateinit var textInputLayoutName: TextInputLayout
    private lateinit var editTextName: TextInputEditText
    private lateinit var textInputLayoutCount: TextInputLayout
    private lateinit var editTextCount: TextInputEditText
    private lateinit var buttonConfirm: Button
    private lateinit var viewModelProductItemActivity: ProductItemViewModel

    private var enteredtext: String? = null
    private var enteredCount: String? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.product_item_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        parseIntent()
        initViews(view)
        viewModelProductItemActivity =
            ViewModelProvider(this)[ProductItemViewModel::class.java]
        addErrorListeners()
        closingActivity()
        when (screenMode) {
            EDIT_MODE -> editProductModeLogic()
            ADD_MODE -> addProductModeLogic()
            else -> throw RuntimeException("Unknown screen mode")
        }
    }

    fun addProductModeLogic() {

        buttonConfirm.setOnClickListener {

            enteredtext = editTextName.text.toString()
            enteredCount = editTextCount.text.toString()
            viewModelProductItemActivity.addProductToList(enteredtext, enteredCount)

        }
    }

    fun editProductModeLogic() {
        viewModelProductItemActivity.getOunProductFromList(productId)
        viewModelProductItemActivity.currentProduct.observe(viewLifecycleOwner) {
            editTextName.setText(it.name)
            editTextCount.setText(it.count.toString())

        }
        buttonConfirm.setOnClickListener {
            viewModelProductItemActivity.editProductToList(
                editTextName.text?.toString(),
                editTextCount.text?.toString()
            )
        }

    }

    fun closingActivity() {
        viewModelProductItemActivity.shouldActivityBeFinished.observe(viewLifecycleOwner) {
           activity?.onBackPressed()
        }
    }

    fun parseIntent() {
        if (screenMode != EDIT_MODE && screenMode != ADD_MODE) {
            throw RuntimeException("Unknown screenMode")
        }
        if (screenMode == EDIT_MODE && productId == 0) {
                throw RuntimeException("Unknown productId")
            }
        }

    fun initViews(view: View) {
        with(view) {
            textInputLayoutName = findViewById(R.id.tilName)
            editTextName = findViewById(R.id.tietName)
            textInputLayoutCount = findViewById(R.id.tilCount)
            editTextCount = findViewById(R.id.tietCount)
            buttonConfirm = findViewById(R.id.buttonAddProduct)
        }

    }

    fun addErrorListeners() {
        editTextName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModelProductItemActivity.resetNameError()
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
        editTextCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModelProductItemActivity.resetCountError()
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
        viewModelProductItemActivity.errorName.observe(viewLifecycleOwner) {
            if (it) {
                textInputLayoutName.error = getString(R.string.anotherName)
            } else {
                textInputLayoutName.error = null
            }
        }
        viewModelProductItemActivity.errorCount.observe(viewLifecycleOwner) {
            if (it) {
                textInputLayoutCount.error = getString(R.string.enotherCount)
            } else {
                textInputLayoutCount.error = null
            }
        }
    }

    companion object {

        private const val EXTRA_PRODUCT_ID = "Product_Id"
        private const val EXTRA_SCREEN_MODE = "EXTRA_EDIT_MODE"
        private const val EDIT_MODE = "EDIT_MODE"
        private const val ADD_MODE = "ADD_MODE"
         private const val UNDEFINED_SCREEN_MODE = ""

        fun addFragmentInAddMode():ProductItemFragment{
            return ProductItemFragment(ADD_MODE)
        }
        fun addFragmentInEditMode(productId: Int):ProductItemFragment{
            return ProductItemFragment(EDIT_MODE,productId)
        }
    }
}